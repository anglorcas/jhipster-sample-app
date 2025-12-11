package io.github.jhipster.sample.repository;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.BankAccount;
import io.github.jhipster.sample.domain.User;
import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

@IntegrationTest
@Transactional
class BankAccountRepositoryIT {

    private static final String DEFAULT_NAME = "Main Account";
    private static final BigDecimal DEFAULT_BALANCE = new BigDecimal("150.00");

    private static final String USER_LOGIN = "repo_test_user";
    private static final String USER_EMAIL = "repo_test_user@localhost";

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;
    private BankAccount bankAccount;

    @BeforeEach
    void init() {
        // Crear un usuario
        user = new User();
        user.setLogin(USER_LOGIN);
        user.setEmail(USER_EMAIL);
        user.setActivated(true);
        user.setPassword(RandomStringUtils.randomAlphanumeric(60));
        userRepository.saveAndFlush(user);

        // Crear un bank account
        bankAccount = new BankAccount().name(DEFAULT_NAME).balance(DEFAULT_BALANCE).user(user);

        bankAccountRepository.saveAndFlush(bankAccount);
    }

    @Test
    void shouldFindOneWithEagerRelationships() {
        BankAccount account = bankAccountRepository.findOneWithEagerRelationships(bankAccount.getId()).orElseThrow();
        assertThat(account.getUser()).isEqualTo(user);
    }

    @Test
    void shouldFindAllWithEagerRelationships() {
        List<BankAccount> results = bankAccountRepository.findAllWithEagerRelationships();
        assertThat(results).isNotEmpty();
        assertThat(results).anyMatch(acc -> acc.getUser().equals(user));
    }

    @Test
    void shouldFindAllWithEagerRelationshipsPageable() {
        var page = bankAccountRepository.findAllWithEagerRelationships(PageRequest.of(0, 10));
        assertThat(page.getContent()).isNotEmpty();
        assertThat(page.getContent()).anyMatch(acc -> acc.getUser().equals(user));
    }

    @Test
    void shouldSaveAndRetrieveBankAccount() {
        BankAccount found = bankAccountRepository.findById(bankAccount.getId()).orElseThrow();
        assertThat(found.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(found.getBalance()).isEqualTo(DEFAULT_BALANCE);
    }

    @Test
    void shouldDeleteBankAccount() {
        bankAccountRepository.delete(bankAccount);
        bankAccountRepository.flush();

        // Ahora usamos orElseThrow en negativo para Modernizer
        boolean notFound = bankAccountRepository.findById(bankAccount.getId()).map(acc -> false).orElse(true); // Si no existe, orElse ejecuta true
        assertThat(notFound).isTrue();
    }

    @Test
    @WithMockUser(username = USER_LOGIN)
    void shouldFindBankAccountsForCurrentUser() {
        List<BankAccount> results = bankAccountRepository.findByUserIsCurrentUser();
        assertThat(results).isNotEmpty();
        assertThat(results).anyMatch(acc -> acc.getUser().getLogin().equals(USER_LOGIN));
    }
}
