package io.github.jhipster.sample.aop.logging;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.github.jhipster.sample.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@IntegrationTest
@Import(LoggingAspectIT.TestConfig.class)
@Transactional
class LoggingAspectIT {

    @Autowired
    private TestService testService;

    @Autowired
    private LoggingAspect loggingAspect;

    @Test
    void loggingAspectShouldBeLoadedInContext() {
        assertThat(loggingAspect).isNotNull();
    }

    @Test
    void aspectShouldInterceptServiceMethodAndReturnResult() {
        String result = testService.sayHello("JHipster");
        assertThat(result).isEqualTo("Hello JHipster");
    }

    @Test
    void aspectShouldRethrowIllegalArgumentException() {
        assertThatThrownBy(() -> testService.fail()).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Boom");
    }

    @TestConfiguration
    @EnableAspectJAutoProxy
    static class TestConfig {

        @Bean
        LoggingAspect loggingAspect(Environment environment) {
            return new LoggingAspect(environment);
        }

        @Bean
        TestService testService() {
            return new TestService();
        }
    }

    @Service
    static class TestService {

        public String sayHello(String name) {
            return "Hello " + name;
        }

        public void fail() {
            throw new IllegalArgumentException("Boom");
        }
    }
}
