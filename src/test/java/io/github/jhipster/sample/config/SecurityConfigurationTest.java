package io.github.jhipster.sample.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.security.crypto.password.PasswordEncoder;
import tech.jhipster.config.JHipsterProperties;

class SecurityConfigurationTest {

    private MockEnvironment env;
    private JHipsterProperties props;
    private SecurityConfiguration securityConfiguration;

    @BeforeEach
    void setup() {
        env = new MockEnvironment();
        props = new JHipsterProperties();
        securityConfiguration = new SecurityConfiguration(env, props);
    }

    @Test
    void passwordEncoderShouldBeBCrypt() {
        PasswordEncoder encoder = securityConfiguration.passwordEncoder();

        assertThat(encoder).isNotNull();
        assertThat(encoder.getClass().getSimpleName()).isEqualTo("BCryptPasswordEncoder");
    }

    @Test
    void passwordEncoderShouldHashPasswords() {
        PasswordEncoder encoder = securityConfiguration.passwordEncoder();

        String raw = "password";
        String encoded = encoder.encode(raw);

        assertThat(encoded).isNotEqualTo(raw);
        assertThat(encoder.matches(raw, encoded)).isTrue();
    }

    @Test
    void shouldCreateSecurityConfigurationInstance() {
        assertThat(securityConfiguration).isNotNull();
    }
}
