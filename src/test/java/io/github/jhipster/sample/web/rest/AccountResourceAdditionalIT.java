/* 
package io.github.jhipster.sample.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.User;
import io.github.jhipster.sample.repository.UserRepository;
import io.github.jhipster.sample.service.dto.PasswordChangeDTO;
import io.github.jhipster.sample.web.rest.vm.KeyAndPasswordVM;
import java.time.Instant;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureMockMvc
@IntegrationTest
class AccountResourceAdditionalIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Seguridad

    @Test
    void changePasswordWithoutAuthenticationMustFail() throws Exception {
        mockMvc
            .perform(
                post("/api/account/change-password")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(new PasswordChangeDTO("old", "new")))
            )
            .andExpect(status().isUnauthorized());
    }

    // Content-Type estricto

    @Test
    @WithMockUser("change-password-invalid-content")
    void changePasswordWithInvalidContentTypeMustFail() throws Exception {
        mockMvc
            .perform(post("/api/account/change-password").contentType(MediaType.TEXT_PLAIN).content("not-json"))
            .andExpect(status().isUnsupportedMediaType());
    }

    // Reset password expirado

    @Test
    @Transactional
    void finishPasswordResetWithExpiredKeyMustFail() throws Exception {
        User user = new User();
        user.setLogin("expired-user");
        user.setEmail("expired@example.com");
        String old_password = RandomStringUtils.randomAlphanumeric(60);
        user.setPassword(old_password);
        user.setResetDate(Instant.now().minusSeconds(3600 * 25));
        user.setResetKey("expired-key");
        user.setActivated(true);
        userRepository.saveAndFlush(user);

        KeyAndPasswordVM keyAndPassword = new KeyAndPasswordVM();
        keyAndPassword.setKey("expired-key");

        String new_password = RandomStringUtils.randomAlphanumeric(60);

        while (new_password.equals(old_password)) {
            new_password = RandomStringUtils.randomAlphanumeric(60);
        }
        keyAndPassword.setNewPassword(new_password);

        mockMvc
            .perform(
                post("/api/account/reset-password/finish")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(keyAndPassword))
            )
            .andExpect(status().is5xxServerError());

        User updatedUser = userRepository.findOneByLogin("expired-user").orElseThrow();
        assertThat(updatedUser.getPassword()).isEqualTo(old_password);

        userRepository.delete(updatedUser);
    }

    // Auditoría básica

    @Test
    @Transactional
    void passwordResetUpdatesLastModifiedFields() throws Exception {
        User user = new User();
        user.setLogin("audit-reset");
        user.setEmail("audit-reset@example.com");
        user.setPassword(passwordEncoder.encode("old"));
        user.setResetKey("audit-key");
        user.setResetDate(Instant.now().plusSeconds(300));
        userRepository.saveAndFlush(user);

        KeyAndPasswordVM vm = new KeyAndPasswordVM();
        vm.setKey("audit-key");
        vm.setNewPassword("new-password");

        mockMvc
            .perform(post("/api/account/reset-password/finish").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(vm)))
            .andExpect(status().isOk());

        User updated = userRepository.findOneByLogin("audit-reset").orElseThrow();
        assertThat(updated.getLastModifiedDate()).isNotNull();
    }
}

*/
