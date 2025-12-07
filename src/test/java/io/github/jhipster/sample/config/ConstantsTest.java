package io.github.jhipster.sample.config;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

class ConstantsTest {

    @Test
    void constantsShouldHaveExpectedValues() {
        assertThat(Constants.SYSTEM).isEqualTo("system");
        assertThat(Constants.DEFAULT_LANGUAGE).isEqualTo("en");
    }

    @Test
    void loginRegexShouldMatchValidLogins() {
        Pattern pattern = Pattern.compile(Constants.LOGIN_REGEX);

        assertThat(pattern.matcher("user").matches()).isTrue();
        assertThat(pattern.matcher("user.name").matches()).isTrue();
        assertThat(pattern.matcher("user-name").matches()).isTrue();
        assertThat(pattern.matcher("user@test.com").matches()).isTrue();
    }

    @Test
    void loginRegexShouldRejectClearlyInvalidLogins() {
        Pattern pattern = Pattern.compile(Constants.LOGIN_REGEX);

        // Only truly invalid cases for THIS regex
        assertThat(pattern.matcher("user name").matches()).isFalse();
        assertThat(pattern.matcher("").matches()).isFalse();
    }

    @Test
    void constructorShouldBePrivate() throws Exception {
        Constructor<Constants> constructor = Constants.class.getDeclaredConstructor();

        assertThat(Modifier.isPrivate(constructor.getModifiers())).isTrue();

        // for coverage
        constructor.setAccessible(true);
        constructor.newInstance();
    }
}
