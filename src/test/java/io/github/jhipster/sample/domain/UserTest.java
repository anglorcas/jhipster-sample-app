package io.github.jhipster.sample.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(User.class);

        User user1 = new User();
        user1.setId(1L);

        User user2 = new User();
        user2.setId(1L);

        // Igualdad con mismo ID
        assertThat(user1).isEqualTo(user2);

        // Distinto ID
        user2.setId(2L);
        assertThat(user1).isNotEqualTo(user2);

        // ID null vs ID no null
        user1.setId(null);
        assertThat(user1).isNotEqualTo(user2);
    }

    @Test
    void hashCodeShouldDependOnId() {
        User user1 = new User();
        user1.setId(5L);

        User user2 = new User();
        user2.setId(5L);

        assertThat(user1.hashCode()).isEqualTo(user2.hashCode());
    }

    @Test
    void toStringShouldContainLogin() {
        User user = new User();
        user.setLogin("tester");

        String result = user.toString();

        assertThat(result).contains("tester");
    }

    @Test
    void loginSetterShouldNormalizeLoginToLowercase() {
        User user = new User();
        user.setLogin("BuilderLogin");

        assertThat(user.getLogin()).isEqualTo("builderlogin"); // JHipster behavior
    }

    @Test
    void activatedFlagShouldBeSettable() {
        User user = new User();
        user.setActivated(true);
        assertThat(user.isActivated()).isTrue();

        user.setActivated(false);
        assertThat(user.isActivated()).isFalse();
    }

    @Test
    void authoritiesShouldBeSetAndRetrievedCorrectly() {
        User user = new User();

        Authority a = new Authority().name("ROLE_TEST");
        Set<Authority> set = new HashSet<>();
        set.add(a);

        user.setAuthorities(set);

        assertThat(user.getAuthorities()).containsExactly(a);
    }

    @Test
    void datesShouldBeSettable() {
        User user = new User();
        Instant now = Instant.now();

        user.setCreatedDate(now);
        user.setLastModifiedDate(now);

        assertThat(user.getCreatedDate()).isEqualTo(now);
        assertThat(user.getLastModifiedDate()).isEqualTo(now);
    }

    @Test
    void getIdShouldReturnUserId() {
        User user = new User();
        user.setId(123L);

        assertThat(user.getId()).isEqualTo(123L);
    }

    @Test
    void emailShouldBeSettable() {
        User user = new User();
        user.setEmail("test@example.com");

        assertThat(user.getEmail()).isEqualTo("test@example.com");
    }
}
