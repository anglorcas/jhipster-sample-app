package io.github.jhipster.sample.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import io.github.jhipster.sample.domain.Authority;
import io.github.jhipster.sample.domain.User;
import io.github.jhipster.sample.repository.AuthorityRepository;
import io.github.jhipster.sample.repository.UserRepository;
import io.github.jhipster.sample.security.AuthoritiesConstants;
import io.github.jhipster.sample.service.dto.AdminUserDTO;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthorityRepository authorityRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private CacheManager cacheManager;

    @Mock
    private Cache loginCache;

    @Mock
    private Cache emailCache;

    @InjectMocks
    private UserService userService;

    @Test
    void registerUserShouldCreateInactiveUserWithUserAuthority() {
        AdminUserDTO dto = new AdminUserDTO();
        dto.setLogin("TestUser");
        dto.setEmail("TEST@EMAIL.COM");
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setLangKey("en");

        Authority userAuthority = new Authority();
        userAuthority.setName(AuthoritiesConstants.USER);

        when(userRepository.findOneByLogin("testuser")).thenReturn(Optional.empty());
        when(userRepository.findOneByEmailIgnoreCase("TEST@EMAIL.COM")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("encoded");
        when(authorityRepository.findById(AuthoritiesConstants.USER)).thenReturn(Optional.of(userAuthority));
        when(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE)).thenReturn(loginCache);
        when(cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE)).thenReturn(emailCache);

        User created = userService.registerUser(dto, "password");

        assertThat(created.getLogin()).isEqualTo("testuser");
        assertThat(created.isActivated()).isFalse();
        assertThat(created.getActivationKey()).isNotNull();
        assertThat(created.getAuthorities()).containsExactly(userAuthority);

        verify(userRepository).save(any(User.class));
        verify(loginCache).evict("testuser");
        verify(emailCache).evict("test@email.com");
    }

    @Test
    void registerUserShouldFailIfActivatedLoginAlreadyExists() {
        User existingUser = new User();
        existingUser.setActivated(true);

        AdminUserDTO dto = new AdminUserDTO();
        dto.setLogin("existing");

        when(userRepository.findOneByLogin("existing")).thenReturn(Optional.of(existingUser));

        assertThatThrownBy(() -> userService.registerUser(dto, "password")).isInstanceOf(UsernameAlreadyUsedException.class);

        verify(userRepository, never()).save(any());
    }

    @Test
    void completePasswordResetShouldFailIfResetKeyExpired() {
        User user = new User();
        user.setResetKey("expired");
        user.setResetDate(Instant.now().minusSeconds(60 * 60 * 25));

        when(userRepository.findOneByResetKey("expired")).thenReturn(Optional.of(user));

        Optional<User> result = userService.completePasswordReset("new", "expired");

        assertThat(result).isEmpty();
    }

    @Test
    void requestPasswordResetShouldFailForNonActivatedUser() {
        User user = new User();
        user.setActivated(false);

        when(userRepository.findOneByEmailIgnoreCase("nope@test.com")).thenReturn(Optional.of(user));

        Optional<User> result = userService.requestPasswordReset("nope@test.com");

        assertThat(result).isEmpty();
    }

    @Test
    void changePasswordShouldThrowExceptionIfCurrentPasswordIsWrong() {
        User user = new User();
        user.setPassword("encoded");

        when(passwordEncoder.matches("wrong", "encoded")).thenReturn(false);
        when(userRepository.findOneByLogin("user")).thenReturn(Optional.of(user));

        try (var mocked = mockStatic(io.github.jhipster.sample.security.SecurityUtils.class)) {
            mocked.when(io.github.jhipster.sample.security.SecurityUtils::getCurrentUserLogin).thenReturn(Optional.of("user"));

            assertThatThrownBy(() -> userService.changePassword("wrong", "new")).isInstanceOf(InvalidPasswordException.class);
        }
    }
}
