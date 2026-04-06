package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.BankAccount;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

@IntegrationTest
@Transactional
class TestUtilIT {

    @PersistenceContext
    private EntityManager em;

    // -------------------------------------------------
    // createByteArray
    // -------------------------------------------------
    @Test
    void createByteArrayShouldCreateCorrectArray() {
        byte[] result = TestUtil.createByteArray(3, "1");

        assertThat(result).hasSize(3);
        assertThat(result[0]).isEqualTo((byte) 1);
        assertThat(result[1]).isEqualTo((byte) 1);
        assertThat(result[2]).isEqualTo((byte) 1);
    }

    // -------------------------------------------------
    // sameInstant (Hamcrest matcher)
    // -------------------------------------------------
    @Test
    void sameInstantShouldMatchSameZonedDateTime() {
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);

        MatcherAssert.assertThat(now.toString(), TestUtil.sameInstant(now));
    }

    // -------------------------------------------------
    // sameNumber (Hamcrest matcher)
    // -------------------------------------------------
    @Test
    void sameNumberShouldMatchBigDecimalAndNumber() {
        BigDecimal reference = new BigDecimal("10.00");

        MatcherAssert.assertThat(10, TestUtil.sameNumber(reference));

        MatcherAssert.assertThat(10.0, TestUtil.sameNumber(reference));
    }

    // -------------------------------------------------
    // equalsVerifier
    // -------------------------------------------------
    @Test
    void equalsVerifierShouldWorkForDomainEntity() throws Exception {
        TestUtil.equalsVerifier(BankAccount.class);
    }

    // -------------------------------------------------
    // createFormattingConversionService
    // -------------------------------------------------
    @Test
    void createFormattingConversionServiceShouldUseIsoDates() {
        var conversionService = TestUtil.createFormattingConversionService();

        assertThat(conversionService).isNotNull();
        assertThat(conversionService.canConvert(String.class, ZonedDateTime.class)).isTrue();
    }

    // -------------------------------------------------
    // findAll
    // -------------------------------------------------
    @Test
    void findAllShouldReturnEntitiesFromDatabase() {
        BankAccount account = new BankAccount().name("Test").balance(BigDecimal.ONE);

        em.persist(account);
        em.flush();

        List<BankAccount> result = TestUtil.findAll(em, BankAccount.class);

        assertThat(result).isNotEmpty();
        assertThat(result).anyMatch(acc -> "Test".equals(acc.getName()));
    }

    // -------------------------------------------------
    // createUpdateProxyForBean
    // -------------------------------------------------
    @Test
    void createUpdateProxyForBeanShouldFallbackToOriginalValues() {
        BankAccount original = new BankAccount().name("Original").balance(BigDecimal.TEN);

        BankAccount update = new BankAccount();
        update.setBalance(BigDecimal.ONE); // name queda null

        BankAccount proxy = TestUtil.createUpdateProxyForBean(update, original);

        assertThat(proxy.getName()).isEqualTo("Original"); // fallback
        assertThat(proxy.getBalance()).isEqualTo(BigDecimal.ONE); // override
    }
}
