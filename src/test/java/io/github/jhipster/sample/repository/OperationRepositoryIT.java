package io.github.jhipster.sample.repository;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.BankAccount;
import io.github.jhipster.sample.domain.Operation;
import io.github.jhipster.sample.domain.User;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

@IntegrationTest
@Transactional
class OperationRepositoryIT {

    private static final Instant DEFAULT_DATE = Instant.now();
    private static final String DEFAULT_DESCRIPTION = "Test operation";
    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal("99.99");

    private static final String USER_LOGIN = "op_test_user";
    private static final String USER_EMAIL = "op_test_user@localhost";

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;
    private BankAccount bankAccount;
    private Operation operation;

    @BeforeEach
    void init() {
        // Crear usuario
        user = new User();
        user.setLogin(USER_LOGIN);
        user.setEmail(USER_EMAIL);
        user.setActivated(true);
        user.setPassword(RandomStringUtils.randomAlphanumeric(60));
        userRepository.saveAndFlush(user);

        // Crear bank account
        bankAccount = new BankAccount().name("Test Bank").balance(new BigDecimal("1000.00")).user(user);
        bankAccountRepository.saveAndFlush(bankAccount);

        // Crear operación
        operation = new Operation().date(DEFAULT_DATE).description(DEFAULT_DESCRIPTION).amount(DEFAULT_AMOUNT).bankAccount(bankAccount);
        operationRepository.saveAndFlush(operation);
    }

    @Test
    void shouldFindOneWithEagerRelationships() {
        Operation op = operationRepository.findOneWithEagerRelationships(operation.getId()).orElseThrow();
        assertThat(op.getBankAccount()).isEqualTo(bankAccount);
    }

    @Test
    void shouldFindAllWithEagerRelationships() {
        List<Operation> results = operationRepository.findAllWithEagerRelationships();
        assertThat(results).isNotEmpty();
        assertThat(results).anyMatch(op -> op.getBankAccount().equals(bankAccount));
    }

    @Test
    void shouldFindAllWithEagerRelationshipsPageable() {
        var page = operationRepository.findAllWithEagerRelationships(PageRequest.of(0, 10));
        assertThat(page.getContent()).isNotEmpty();
        assertThat(page.getContent()).anyMatch(op -> op.getBankAccount().equals(bankAccount));
    }

    @Test
    void shouldSaveAndRetrieveOperation() {
        Operation found = operationRepository.findById(operation.getId()).orElseThrow();
        assertThat(found.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(found.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(found.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    void shouldDeleteOperation() {
        operationRepository.delete(operation);
        operationRepository.flush();

        // Usamos orElse para cumplir Modernizer
        boolean notFound = operationRepository.findById(operation.getId()).map(op -> false).orElse(true); // true si no existe
        assertThat(notFound).isTrue();
    }
}
