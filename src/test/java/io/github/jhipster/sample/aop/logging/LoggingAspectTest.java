package io.github.jhipster.sample.aop.logging;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;

class LoggingAspectTest {

    private Environment env;
    private LoggingAspect loggingAspect;

    @BeforeEach
    void setup() {
        env = mock(Environment.class);
        loggingAspect = new LoggingAspect(env);
    }

    private void mockLoggerDependencies(Signature signature) {
        when(signature.getDeclaringTypeName()).thenReturn("io.github.jhipster.sample.service.DummyService");
        when(signature.getName()).thenReturn("testMethod");
    }

    @Test
    void logAroundShouldReturnResult() throws Throwable {
        ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
        Signature signature = mock(Signature.class);

        when(joinPoint.getSignature()).thenReturn(signature);
        when(joinPoint.getArgs()).thenReturn(new Object[] { "arg1" });
        when(joinPoint.proceed()).thenReturn("result");

        mockLoggerDependencies(signature);

        Object result = loggingAspect.logAround(joinPoint);

        assertThat(result).isEqualTo("result");
    }

    @Test
    void logAroundShouldRethrowIllegalArgumentException() throws Throwable {
        ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
        Signature signature = mock(Signature.class);

        when(joinPoint.getSignature()).thenReturn(signature);
        when(joinPoint.getArgs()).thenReturn(new Object[] { "badArg" });
        when(joinPoint.proceed()).thenThrow(new IllegalArgumentException("Boom"));

        mockLoggerDependencies(signature);

        assertThatThrownBy(() -> loggingAspect.logAround(joinPoint)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void logAfterThrowingShouldWorkInDevProfile() {
        JoinPoint joinPoint = mock(JoinPoint.class);
        Signature signature = mock(Signature.class);

        when(env.acceptsProfiles(any(Profiles.class))).thenReturn(true);
        when(joinPoint.getSignature()).thenReturn(signature);

        mockLoggerDependencies(signature);

        Throwable ex = new RuntimeException("failure");

        loggingAspect.logAfterThrowing(joinPoint, ex);
    }

    @Test
    void logAfterThrowingShouldWorkInProdProfile() {
        JoinPoint joinPoint = mock(JoinPoint.class);
        Signature signature = mock(Signature.class);

        when(env.acceptsProfiles(any(Profiles.class))).thenReturn(false);
        when(joinPoint.getSignature()).thenReturn(signature);

        mockLoggerDependencies(signature);

        Throwable ex = new RuntimeException("failure");

        loggingAspect.logAfterThrowing(joinPoint, ex);
    }
}
