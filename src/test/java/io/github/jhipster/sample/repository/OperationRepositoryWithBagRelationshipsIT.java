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
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

@IntegrationTest
@Transactional
class OperationRepositoryWithBagRelationshipsIT {

    private static final Instant DEFAULT_DATE = Instant.now();
    private static final String DEFAULT_DESCRIPTION = "Interface bag test";
    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal("75.00");

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
        // User
        User user = new User();
        user.setLogin("bag_interface_user");
        user.setEmail("bag_interface@localhost");
        user.setActivated(true);
        user.setPassword(RandomStringUtils.randomAlphanumeric(60));
        userRepository.saveAndFlush(user);

        // BankAccount
        BankAccount bankAccount = new BankAccount().name("Interface Account").balance(new BigDecimal("300.00")).user(user);
        bankAccountRepository.saveAndFlush(bankAccount);

        // Labels
        Label label1 = new Label().label("interface-label-1");
        Label label2 = new Label().label("interface-label-2");
        labelRepository.saveAndFlush(label1);
        labelRepository.saveAndFlush(label2);

        // Operation
        operation = new Operation().date(DEFAULT_DATE).description(DEFAULT_DESCRIPTION).amount(DEFAULT_AMOUNT).bankAccount(bankAccount);

        operation.getLabels().add(label1);
        operation.getLabels().add(label2);

        operationRepository.saveAndFlush(operation);
    }

    @Test
    void shouldFetchBagRelationshipsForOptional() {
        Operation fetched = operationRepository.fetchBagRelationships(operationRepository.findById(operation.getId())).orElseThrow();

        assertThat(fetched.getLabels()).hasSize(2);
    }

    @Test
    void shouldFetchBagRelationshipsForList() {
        List<Operation> fetched = operationRepository.fetchBagRelationships(operationRepository.findAll());

        Operation result = fetched.stream().filter(op -> op.getId().equals(operation.getId())).findFirst().orElseThrow();

        assertThat(result.getLabels()).hasSize(2);
    }

    @Test
    void shouldFetchBagRelationshipsForPage() {
        var page = operationRepository.findAll(PageRequest.of(0, 10));

        var fetchedPage = operationRepository.fetchBagRelationships(page);

        Operation result = fetchedPage.getContent().stream().filter(op -> op.getId().equals(operation.getId())).findFirst().orElseThrow();

        assertThat(result.getLabels()).hasSize(2);
        assertThat(fetchedPage.getTotalElements()).isEqualTo(page.getTotalElements());
    }
}
