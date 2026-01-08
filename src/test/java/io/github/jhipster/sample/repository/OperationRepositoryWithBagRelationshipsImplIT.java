package io.github.jhipster.sample.repository;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.BankAccount;
import io.github.jhipster.sample.domain.Label;
import io.github.jhipster.sample.domain.Operation;
import io.github.jhipster.sample.domain.User;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@IntegrationTest
@Transactional
class OperationRepositoryWithBagRelationshipsImplIT {

    private static final Instant DEFAULT_DATE = Instant.now();
    private static final String DEFAULT_DESCRIPTION = "Bag relationship test";
    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal("50.00");

    private static final String USER_LOGIN = "bag_user";
    private static final String USER_EMAIL = "bag_user@localhost";

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private UserRepository userRepository;

    private Operation operation;

    @BeforeEach
    void init() {
        // Usuario
        User user = new User();
        user.setLogin(USER_LOGIN);
        user.setEmail(USER_EMAIL);
        user.setActivated(true);
        user.setPassword(RandomStringUtils.randomAlphanumeric(60));
        userRepository.saveAndFlush(user);

        // BankAccount
        BankAccount bankAccount = new BankAccount().name("Bag Account").balance(new BigDecimal("500.00")).user(user);
        bankAccountRepository.saveAndFlush(bankAccount);

        // Labels
        Label label1 = new Label().label("label-one");
        Label label2 = new Label().label("label-two");
        labelRepository.saveAndFlush(label1);
        labelRepository.saveAndFlush(label2);

        // Operation con BAG (labels)
        operation = new Operation().date(DEFAULT_DATE).description(DEFAULT_DESCRIPTION).amount(DEFAULT_AMOUNT).bankAccount(bankAccount);

        operation.getLabels().add(label1);
        operation.getLabels().add(label2);

        operationRepository.saveAndFlush(operation);
    }

    @Test
    void shouldFetchBagRelationshipsForOptional() {
        Operation fetched = operationRepository.findOneWithEagerRelationships(operation.getId()).orElseThrow();

        assertThat(fetched.getLabels()).hasSize(2);
        assertThat(fetched.getLabels()).extracting(Label::getLabel).containsExactlyInAnyOrder("label-one", "label-two");
    }

    @Test
    void shouldFetchBagRelationshipsForList() {
        List<Operation> operations = operationRepository.findAllWithEagerRelationships();

        assertThat(operations).isNotEmpty();

        Operation fetched = operations.stream().filter(op -> op.getId().equals(operation.getId())).findFirst().orElseThrow();

        assertThat(fetched.getLabels()).hasSize(2);
    }

    @Test
    void shouldNotThrowMultipleBagFetchException() {
        // Si llegamos aquí sin excepción, el patrón BAG está funcionando correctamente
        List<Operation> operations = operationRepository.findAllWithEagerRelationships();
        assertThat(operations).isNotEmpty();
    }
}
