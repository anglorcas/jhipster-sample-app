package io.github.jhipster.sample.web.rest.vm;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.service.dto.AdminUserDTO;
import jakarta.validation.constraints.Size;
import java.lang.reflect.Field;
import org.junit.jupiter.api.Test;

class ManagedUserVMTest {

    @Test
    void shouldExtendAdminUserDTO() {
        ManagedUserVM vm = new ManagedUserVM();
        assertThat(vm).isInstanceOf(AdminUserDTO.class);
    }

    @Test
    void passwordGetterAndSetterShouldWork() {
        ManagedUserVM vm = new ManagedUserVM();

        vm.setPassword("abcd1234");
        assertThat(vm.getPassword()).isEqualTo("abcd1234");

        vm.setPassword(null);
        assertThat(vm.getPassword()).isNull();
    }

    @Test
    void passwordFieldShouldHaveCorrectValidationAnnotations() throws Exception {
        Field passwordField = ManagedUserVM.class.getDeclaredField("password");

        assertThat(passwordField.isAnnotationPresent(Size.class)).isTrue();

        Size size = passwordField.getAnnotation(Size.class);
        assertThat(size.min()).isEqualTo(ManagedUserVM.PASSWORD_MIN_LENGTH);
        assertThat(size.max()).isEqualTo(ManagedUserVM.PASSWORD_MAX_LENGTH);
    }

    @Test
    void toStringShouldIncludeParentToStringButNotPassword() {
        ManagedUserVM vm = new ManagedUserVM();
        vm.setLogin("tester");
        vm.setEmail("tester@example.com");
        vm.setPassword("super-secret-password");

        String result = vm.toString();

        // Should contain ManagedUserVM
        assertThat(result).contains("ManagedUserVM");

        // Should include parent DTO info
        assertThat(result).contains("login='tester'");
        assertThat(result).contains("email='tester@example.com'");

        // Should NOT include password
        assertThat(result).doesNotContain("super-secret-password");
        assertThat(result).doesNotContain("password");
    }
}
