package io.github.jhipster.sample.security;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.IntegrationTest;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

@IntegrationTest
@Transactional
class AuthoritiesConstantsIT {

    @Test
    void constantsShouldHaveExpectedValuesInSpringContext() {
        // Verificamos que las constantes existen y no han sido alteradas
        assertThat(AuthoritiesConstants.ADMIN).isEqualTo("ROLE_ADMIN");
        assertThat(AuthoritiesConstants.USER).isEqualTo("ROLE_USER");
        assertThat(AuthoritiesConstants.ANONYMOUS).isEqualTo("ROLE_ANONYMOUS");
    }

    @Test
    void constructorShouldBePrivateEvenInIntegrationContext() throws Exception {
        Constructor<AuthoritiesConstants> constructor = AuthoritiesConstants.class.getDeclaredConstructor();

        assertThat(Modifier.isPrivate(constructor.getModifiers())).isTrue();

        // Coverage extra: ejecución vía reflexión
        constructor.setAccessible(true);
        constructor.newInstance();
    }
}
