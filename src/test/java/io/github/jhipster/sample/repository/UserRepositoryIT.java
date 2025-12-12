package io.github.jhipster.sample.repository;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.Authority;
import io.github.jhipster.sample.domain.User;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

@IntegrationTest
@Transactional
class UserRepositoryIT {

    private static final String DEFAULT_LOGIN = "userrepo_test";
    private static final String DEFAULT_EMAIL = "userrepo_test@localhost";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    private User user;

    @BeforeEach
    void init() {
        // Eliminar cualquier rastro previo del usuario
        userRepository.findOneByLogin(DEFAULT_LOGIN).ifPresent(userRepository::delete);

        // Prevenir duplicación de authorities — JHipster ya creó ambas
        Authority admin = authorityRepository.findById("ROLE_ADMIN").orElseThrow();
        Authority userRole = authorityRepository.findById("ROLE_USER").orElseThrow();

        // Creamos un usuario activado normal
        user = new User();
        user.setLogin(DEFAULT_LOGIN);
        user.setEmail(DEFAULT_EMAIL);
        user.setActivated(true);
        user.setPassword(RandomStringUtils.randomAlphanumeric(60));
        user.setAuthorities(Set.of(userRole)); // asignamos ROLE_USER
        user = userRepository.saveAndFlush(user);
    }

    @Test
    void shouldFindUserByLogin() {
        User found = userRepository.findOneByLogin(DEFAULT_LOGIN).orElseThrow();
        assertThat(found.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    void shouldFindUserByEmailIgnoreCase() {
        User found = userRepository.findOneByEmailIgnoreCase(DEFAULT_EMAIL.toUpperCase()).orElseThrow();
        assertThat(found.getLogin()).isEqualTo(DEFAULT_LOGIN);
    }

    @Test
    void shouldFindOneWithAuthoritiesByLogin() {
        User found = userRepository.findOneWithAuthoritiesByLogin(DEFAULT_LOGIN).orElseThrow();

        assertThat(found.getAuthorities()).anyMatch(auth -> auth.getName().equals("ROLE_USER"));
    }

    @Test
    void shouldFindOneWithAuthoritiesByEmailIgnoreCase() {
        User found = userRepository.findOneWithAuthoritiesByEmailIgnoreCase(DEFAULT_EMAIL.toLowerCase()).orElseThrow();

        assertThat(found.getAuthorities()).anyMatch(auth -> auth.getName().equals("ROLE_USER"));
    }

    @Test
    void shouldFindAllNotActivatedBeforeDate() {
        // Creamos un usuario INACTIVO que cumpla los requisitos del método
        User inactive = new User();
        inactive.setLogin("inactive_test_u");
        inactive.setEmail("inactive_test_u@localhost");
        inactive.setActivated(false);
        inactive.setActivationKey("ABC123");
        inactive.setCreatedDate(Instant.now().minusSeconds(86400)); // hace 24h
        inactive.setPassword(RandomStringUtils.randomAlphanumeric(60));

        userRepository.saveAndFlush(inactive);

        List<User> results = userRepository.findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant.now());

        assertThat(results).isNotEmpty().anyMatch(u -> u.getLogin().equals("inactive_test_u"));
    }

    @Test
    void shouldFindAllActivatedUsersPageable() {
        var page = userRepository.findAllByIdNotNullAndActivatedIsTrue(PageRequest.of(0, 10));

        assertThat(page.getContent()).isNotEmpty();
        assertThat(page.getContent()).anyMatch(u -> u.getLogin().equals(DEFAULT_LOGIN));
    }

    @Test
    void shouldFindUserByActivationKey() {
        user.setActivated(false);
        user.setActivationKey("KEY123");

        //asegurar siempre colección MUTABLE antes de merge
        user.setAuthorities(new HashSet<>(user.getAuthorities()));

        userRepository.saveAndFlush(user);

        User found = userRepository.findOneByActivationKey("KEY123").orElseThrow();
        assertThat(found.getLogin()).isEqualTo(DEFAULT_LOGIN);
    }
}
