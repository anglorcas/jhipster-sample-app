/* 
package io.github.jhipster.sample.security;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class SecurityUtilsIT {

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldReturnCurrentUserLoginFromUserDetails() {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            "test-user",
            "password",
            List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        assertThat(SecurityUtils.getCurrentUserLogin()).contains("test-user");
    }

    @Test
    void shouldReturnCurrentUserLoginFromJwt() {
        Jwt jwt = Jwt.withTokenValue("token")
            .header("alg", "HS512")
            .subject("jwt-user")
            .claim(SecurityUtils.AUTHORITIES_KEY, List.of("ROLE_USER"))
            .build();

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(jwt, "token", List.of());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        assertThat(SecurityUtils.getCurrentUserLogin()).contains("jwt-user");
    }

    @Test
    void shouldReturnCurrentUserJWT() {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            "user",
            "jwt-token-value",
            List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        assertThat(SecurityUtils.getCurrentUserJWT()).contains("jwt-token-value");
    }

    @Test
    void shouldReturnEmptyJWTWhenNoAuthentication() {
        SecurityContextHolder.clearContext();

        assertThat(SecurityUtils.getCurrentUserJWT()).isEmpty();
    }

    @Test
    @WithMockUser(username = "user", authorities = "ROLE_USER")
    void shouldBeAuthenticated() {
        assertThat(SecurityUtils.isAuthenticated()).isTrue();
    }

    @Test
    void shouldNotBeAuthenticatedForAnonymousUser() {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            "anonymousUser",
            "anonymous",
            List.of(new SimpleGrantedAuthority(AuthoritiesConstants.ANONYMOUS))
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        assertThat(SecurityUtils.isAuthenticated()).isFalse();
    }

    @Test
    @WithMockUser(authorities = { "ROLE_ADMIN", "ROLE_USER" })
    void shouldHaveAnyOfAuthorities() {
        assertThat(SecurityUtils.hasCurrentUserAnyOfAuthorities("ROLE_ADMIN")).isTrue();
        assertThat(SecurityUtils.hasCurrentUserAnyOfAuthorities("ROLE_MANAGER")).isFalse();
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    void shouldHaveNoneOfAuthorities() {
        assertThat(SecurityUtils.hasCurrentUserNoneOfAuthorities("ROLE_ADMIN")).isTrue();
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void shouldHaveSpecificAuthority() {
        assertThat(SecurityUtils.hasCurrentUserThisAuthority("ROLE_ADMIN")).isTrue();
        assertThat(SecurityUtils.hasCurrentUserThisAuthority("ROLE_USER")).isFalse();
    }
}
*/
