package io.github.jhipster.sample.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.domain.Authority;
import io.github.jhipster.sample.domain.User;
import io.github.jhipster.sample.security.AuthoritiesConstants;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AdminUserDTOTest {

    @Test
    void constructorShouldCopyFieldsFromUser() {
        User user = new User();
        user.setId(1L);
        user.setLogin("tester");
        user.setEmail("tester@example.com");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setLangKey("en");
        user.setActivated(true);
        user.setCreatedDate(Instant.now());
        user.setLastModifiedDate(Instant.now());

        Set<Authority> authorities = new HashSet<>();
        Authority a = new Authority();
        a.setName(AuthoritiesConstants.USER);
        authorities.add(a);
        user.setAuthorities(authorities);

        AdminUserDTO dto = new AdminUserDTO(user);

        assertThat(dto.getId()).isEqualTo(user.getId());
        assertThat(dto.getLogin()).isEqualTo(user.getLogin());
        assertThat(dto.getEmail()).isEqualTo(user.getEmail());
        assertThat(dto.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(dto.getLastName()).isEqualTo(user.getLastName());
        assertThat(dto.getLangKey()).isEqualTo(user.getLangKey());
        assertThat(dto.isActivated()).isEqualTo(user.isActivated());
        assertThat(dto.getAuthorities()).containsExactly(AuthoritiesConstants.USER);
    }

    @Test
    void gettersAndSettersShouldWork() {
        AdminUserDTO dto = new AdminUserDTO();

        dto.setId(2L);
        dto.setLogin("builderlogin");
        dto.setEmail("builder@example.com");
        dto.setFirstName("Builder");
        dto.setLastName("User");
        dto.setLangKey("fr");
        dto.setActivated(false);

        assertThat(dto.getId()).isEqualTo(2L);
        assertThat(dto.getLogin()).isEqualTo("builderlogin");
        assertThat(dto.getEmail()).isEqualTo("builder@example.com");
        assertThat(dto.getFirstName()).isEqualTo("Builder");
        assertThat(dto.getLastName()).isEqualTo("User");
        assertThat(dto.getLangKey()).isEqualTo("fr");
        assertThat(dto.isActivated()).isFalse();
    }
}
