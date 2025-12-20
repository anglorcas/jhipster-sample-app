/* 
package io.github.jhipster.sample.config;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.IntegrationTest;
import java.util.concurrent.Executor;
import org.junit.jupiter.api.Test;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;
import tech.jhipster.async.ExceptionHandlingAsyncTaskExecutor;

@IntegrationTest
class AsyncConfigurationIT {

    @Autowired
    private AsyncConfiguration asyncConfiguration;

    @Autowired
    private TaskExecutionProperties taskExecutionProperties;

    @Test
    void asyncConfigurationShouldBeLoaded() {
        assertThat(asyncConfiguration).isNotNull();
    }

    @Test
    void asyncUncaughtExceptionHandlerShouldBeSimpleHandler() {
        AsyncUncaughtExceptionHandler handler = asyncConfiguration.getAsyncUncaughtExceptionHandler();

        assertThat(handler).isInstanceOf(SimpleAsyncUncaughtExceptionHandler.class);
    }

    @Test
    void asyncExecutorFactoryShouldCreateExceptionHandlingExecutor() {
        AsyncConfiguration configuration = new AsyncConfiguration(taskExecutionProperties);

        Executor executor = configuration.getAsyncExecutor();

        assertThat(executor).isInstanceOf(ExceptionHandlingAsyncTaskExecutor.class);
    }
}
*/
