package io.github.jhipster.sample.security;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.Test;

class AuthoritiesConstantsTest {

    @Test
    void constantsShouldHaveExpectedValues() {
        assertThat(AuthoritiesConstants.ADMIN).isEqualTo("ROLE_ADMIN");
        assertThat(AuthoritiesConstants.USER).isEqualTo("ROLE_USER");
        assertThat(AuthoritiesConstants.ANONYMOUS).isEqualTo("ROLE_ANONYMOUS");
    }

    @Test
    void constructorShouldBePrivate() throws Exception {
        Constructor<AuthoritiesConstants> constructor = AuthoritiesConstants.class.getDeclaredConstructor();

        assertThat(Modifier.isPrivate(constructor.getModifiers())).isTrue();

        // Make sure it can be invoked via reflection (coverage boost)
        constructor.setAccessible(true);
        constructor.newInstance();
    }
}
