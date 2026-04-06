package io.github.jhipster.sample.web.rest.vm;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.lang.reflect.Field;
import org.junit.jupiter.api.Test;

class LoginVMTest {

    @Test
    void gettersAndSettersShouldWork() {
        LoginVM vm = new LoginVM();

        vm.setUsername("tester");
        vm.setPassword("secret123");
        vm.setRememberMe(true);

        assertThat(vm.getUsername()).isEqualTo("tester");
        assertThat(vm.getPassword()).isEqualTo("secret123");
        assertThat(vm.isRememberMe()).isTrue();
    }

    @Test
    void rememberMeShouldDefaultToFalse() {
        LoginVM vm = new LoginVM();
        assertThat(vm.isRememberMe()).isFalse();
    }

    @Test
    void toStringShouldNotContainPassword() {
        LoginVM vm = new LoginVM();
        vm.setUsername("john");
        vm.setPassword("super-secret");
        vm.setRememberMe(true);

        String str = vm.toString();

        assertThat(str).contains("LoginVM");
        assertThat(str).contains("john");
        assertThat(str).contains("rememberMe=true");

        // Critical: password MUST NOT appear
        assertThat(str).doesNotContain("super-secret");
    }

    @Test
    void usernameShouldHaveValidationAnnotations() throws Exception {
        Field username = LoginVM.class.getDeclaredField("username");

        assertThat(username.isAnnotationPresent(NotNull.class)).isTrue();
        Size size = username.getAnnotation(Size.class);
        assertThat(size.min()).isEqualTo(1);
        assertThat(size.max()).isEqualTo(50);
    }

    @Test
    void passwordShouldHaveValidationAnnotations() throws Exception {
        Field password = LoginVM.class.getDeclaredField("password");

        assertThat(password.isAnnotationPresent(NotNull.class)).isTrue();
        Size size = password.getAnnotation(Size.class);
        assertThat(size.min()).isEqualTo(4);
        assertThat(size.max()).isEqualTo(100);
    }
}
