package io.github.jhipster.sample.domain;

import static io.github.jhipster.sample.domain.AuthorityTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AuthorityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Authority.class);
        Authority authority1 = getAuthoritySample1();
        Authority authority2 = new Authority();
        assertThat(authority1).isNotEqualTo(authority2);

        authority2.setName(authority1.getName());
        assertThat(authority1).isEqualTo(authority2);

        authority2 = getAuthoritySample2();
        assertThat(authority1).isNotEqualTo(authority2);
    }

    @Test
    void hashCodeVerifier() {
        Authority authority = new Authority();
        assertThat(authority.hashCode()).isZero();

        Authority authority1 = getAuthoritySample1();
        authority.setName(authority1.getName());
        assertThat(authority).hasSameHashCodeAs(authority1);
    }

    @Test
    void toStringShouldIncludeName() {
        var authority = getAuthoritySample1();
        assertThat(authority.toString()).contains(authority.getName());
    }

    @Test
    void getIdShouldReturnName() {
        Authority authority = getAuthoritySample1();
        assertThat(authority.getId()).isEqualTo(authority.getName());
    }

    @Test
    void nameBuilderShouldSetValue() {
        Authority authority = new Authority().name("ROLE_TEST1");
        assertThat(authority.getName()).isEqualTo("ROLE_TEST1");
    }

    @Test
    void isNewShouldBeTrueBeforePersistAndFalseAfter() {
        Authority authority = new Authority().name("ROLE_TEST2");

        // Antes de persistir
        assertThat(authority.isNew()).isTrue();

        // Simulación de persistencia
        authority.setIsPersisted();

        // Después de persistir
        assertThat(authority.isNew()).isFalse();
    }

    @Test
    void updateEntityStateShouldMarkAsPersisted() {
        Authority authority = new Authority().name("ROLE_TEST3");

        assertThat(authority.isNew()).isTrue();

        authority.updateEntityState();

        assertThat(authority.isNew()).isFalse();
    }
}
