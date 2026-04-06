package io.github.jhipster.sample.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import io.github.jhipster.sample.domain.Authority;
import io.github.jhipster.sample.domain.User;
import io.github.jhipster.sample.repository.UserRepository;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
class DomainUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DomainUserDetailsService domainUserDetailsService;

    private User activatedUser;

    @BeforeEach
    void setup() {
        Authority userAuthority = new Authority();
        userAuthority.setName("ROLE_USER");

        activatedUser = new User();
        activatedUser.setLogin("testuser");
        activatedUser.setEmail("testuser@test.com");
        activatedUser.setPassword("encrypted-password");
        activatedUser.setActivated(true);
        activatedUser.setAuthorities(Set.of(userAuthority));
    }

    @Test
    void shouldLoadUserByLogin() {
        when(userRepository.findOneWithAuthoritiesByLogin("testuser")).thenReturn(Optional.of(activatedUser));

        UserDetails userDetails = domainUserDetailsService.loadUserByUsername("testuser");

        assertThat(userDetails.getUsername()).isEqualTo("testuser");
        assertThat(userDetails.getPassword()).isEqualTo("encrypted-password");
        assertThat(userDetails.getAuthorities()).extracting("authority").containsExactly("ROLE_USER");
    }

    @Test
    void shouldLoadUserByEmail() {
        when(userRepository.findOneWithAuthoritiesByEmailIgnoreCase("testuser@test.com")).thenReturn(Optional.of(activatedUser));

        UserDetails userDetails = domainUserDetailsService.loadUserByUsername("testuser@test.com");

        assertThat(userDetails.getUsername()).isEqualTo("testuser");
    }

    @Test
    void shouldThrowExceptionWhenUserIsNotActivated() {
        activatedUser.setActivated(false);

        when(userRepository.findOneWithAuthoritiesByLogin("testuser")).thenReturn(Optional.of(activatedUser));

        assertThatThrownBy(() -> domainUserDetailsService.loadUserByUsername("testuser"))
            .isInstanceOf(UserNotActivatedException.class)
            .hasMessageContaining("not activated");
    }

    @Test
    void shouldThrowUsernameNotFoundExceptionWhenUserDoesNotExist() {
        when(userRepository.findOneWithAuthoritiesByLogin("unknown")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> domainUserDetailsService.loadUserByUsername("unknown")).isInstanceOf(UsernameNotFoundException.class);
    }
}
