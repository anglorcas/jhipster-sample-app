package io.github.jhipster.sample.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.domain.User;
import org.junit.jupiter.api.Test;

class UserDTOTest {

    @Test
    void constructorShouldCopyFieldsFromUser() {
        User user = new User();
        user.setId(10L);
        user.setLogin("tester");

        UserDTO dto = new UserDTO(user);

        assertThat(dto.getId()).isEqualTo(10L);
        assertThat(dto.getLogin()).isEqualTo("tester");
    }

    @Test
    void gettersAndSettersShouldWork() {
        UserDTO dto = new UserDTO();

        dto.setId(25L);
        dto.setLogin("login_test");

        assertThat(dto.getId()).isEqualTo(25L);
        assertThat(dto.getLogin()).isEqualTo("login_test");
    }

    @Test
    void equalsShouldReturnTrueForSameIdAndLogin() {
        UserDTO dto1 = new UserDTO();
        dto1.setId(5L);
        dto1.setLogin("same");

        UserDTO dto2 = new UserDTO();
        dto2.setId(5L);
        dto2.setLogin("same");

        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
    }

    @Test
    void equalsShouldReturnFalseForDifferentId() {
        UserDTO dto1 = new UserDTO();
        dto1.setId(1L);
        dto1.setLogin("test");

        UserDTO dto2 = new UserDTO();
        dto2.setId(2L);
        dto2.setLogin("test");

        assertThat(dto1).isNotEqualTo(dto2);
    }

    @Test
    void equalsShouldReturnFalseWhenIdIsNull() {
        UserDTO dto1 = new UserDTO();
        dto1.setId(null);
        dto1.setLogin("a");

        UserDTO dto2 = new UserDTO();
        dto2.setId(1L);
        dto2.setLogin("a");

        assertThat(dto1).isNotEqualTo(dto2);
    }

    @Test
    void toStringShouldContainIdAndLogin() {
        UserDTO dto = new UserDTO();
        dto.setId(88L);
        dto.setLogin("hello");

        String str = dto.toString();

        assertThat(str).contains("88");
        assertThat(str).contains("hello");
    }
}
