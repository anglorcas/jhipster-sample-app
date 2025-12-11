package io.github.jhipster.sample.web.rest.vm;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class KeyAndPasswordVMTest {

    @Test
    void gettersAndSettersShouldWork() {
        KeyAndPasswordVM vm = new KeyAndPasswordVM();

        vm.setKey("activation-key");
        vm.setNewPassword("newPassword123");

        assertThat(vm.getKey()).isEqualTo("activation-key");
        assertThat(vm.getNewPassword()).isEqualTo("newPassword123");
    }

    @Test
    void shouldHandleNullValues() {
        KeyAndPasswordVM vm = new KeyAndPasswordVM();

        vm.setKey(null);
        vm.setNewPassword(null);

        assertThat(vm.getKey()).isNull();
        assertThat(vm.getNewPassword()).isNull();
    }

    @Test
    void fieldsShouldBeIndependent() {
        KeyAndPasswordVM vm = new KeyAndPasswordVM();

        vm.setKey("k1");
        vm.setNewPassword(null);

        assertThat(vm.getKey()).isEqualTo("k1");
        assertThat(vm.getNewPassword()).isNull();

        vm.setNewPassword("p2");

        assertThat(vm.getKey()).isEqualTo("k1");
        assertThat(vm.getNewPassword()).isEqualTo("p2");
    }
}
