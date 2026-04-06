package io.github.jhipster.sample.web.rest.errors;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import tech.jhipster.web.rest.errors.ProblemDetailWithCause;

class BadRequestAlertExceptionTest {

    @Test
    void shouldStoreEntityNameAndErrorKey() {
        BadRequestAlertException ex = new BadRequestAlertException("Test message", "user", "userexists");

        assertThat(ex.getEntityName()).isEqualTo("user");
        assertThat(ex.getErrorKey()).isEqualTo("userexists");
    }

    @Test
    void shouldCreateProblemDetailsWithCorrectStatusAndTitle() {
        BadRequestAlertException ex = new BadRequestAlertException("Something went wrong", "account", "invalid");

        ProblemDetailWithCause problem = ex.getProblemDetailWithCause();

        assertThat(problem).isNotNull();
        assertThat(problem.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(problem.getTitle()).isEqualTo("Something went wrong");
    }

    @Test
    void shouldContainCustomProperties() {
        BadRequestAlertException ex = new BadRequestAlertException("Invalid data", "entityName", "errorKey");

        ProblemDetailWithCause problem = ex.getProblemDetailWithCause();

        assertThat(problem.getProperties()).containsEntry("message", "error.errorKey");
        assertThat(problem.getProperties()).containsEntry("params", "entityName");
    }

    @Test
    void shouldSupportCustomTypeUri() {
        URI type = URI.create("https://example.com/problem");
        BadRequestAlertException ex = new BadRequestAlertException(type, "Custom type error", "entity", "custom");

        ProblemDetailWithCause problem = ex.getProblemDetailWithCause();

        assertThat(problem.getType()).isEqualTo(type);
        assertThat(problem.getTitle()).isEqualTo("Custom type error");
    }
}
