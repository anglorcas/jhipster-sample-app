package io.github.jhipster.sample.config;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.Executor;
import org.junit.jupiter.api.Test;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;
import tech.jhipster.async.ExceptionHandlingAsyncTaskExecutor;

class AsyncConfigurationTest {

    @Test
    void getAsyncExecutorShouldReturnExceptionHandlingAsyncTaskExecutor() {
        TaskExecutionProperties properties = new TaskExecutionProperties();
        AsyncConfiguration config = new AsyncConfiguration(properties);

        Executor executor = config.getAsyncExecutor();

        assertThat(executor).isInstanceOf(ExceptionHandlingAsyncTaskExecutor.class);
    }

    @Test
    void getAsyncUncaughtExceptionHandlerShouldReturnSimpleHandler() {
        AsyncConfiguration config = new AsyncConfiguration(new TaskExecutionProperties());

        AsyncUncaughtExceptionHandler handler = config.getAsyncUncaughtExceptionHandler();

        assertThat(handler).isInstanceOf(org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler.class);
    }
}
