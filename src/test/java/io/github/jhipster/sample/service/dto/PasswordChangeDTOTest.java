package io.github.jhipster.sample.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PasswordChangeDTOTest {

    @Test
    void defaultConstructorShouldInitializeNullFields() {
        PasswordChangeDTO dto = new PasswordChangeDTO();

        assertThat(dto.getCurrentPassword()).isNull();
        assertThat(dto.getNewPassword()).isNull();
    }

    @Test
    void fullConstructorShouldSetFields() {
        PasswordChangeDTO dto = new PasswordChangeDTO("oldPass", "newPass");

        assertThat(dto.getCurrentPassword()).isEqualTo("oldPass");
        assertThat(dto.getNewPassword()).isEqualTo("newPass");
    }

    @Test
    void gettersAndSettersShouldWork() {
        PasswordChangeDTO dto = new PasswordChangeDTO();

        dto.setCurrentPassword("1234");
        dto.setNewPassword("5678");

        assertThat(dto.getCurrentPassword()).isEqualTo("1234");
        assertThat(dto.getNewPassword()).isEqualTo("5678");
    }

    @Test
    void shouldHandleNullValues() {
        PasswordChangeDTO dto = new PasswordChangeDTO();

        dto.setCurrentPassword(null);
        dto.setNewPassword(null);

        assertThat(dto.getCurrentPassword()).isNull();
        assertThat(dto.getNewPassword()).isNull();
    }
}
