package io.github.jhipster.sample.config;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.jhipster.sample.IntegrationTest;
import io.github.jhipster.sample.domain.Authority;
import io.github.jhipster.sample.domain.BankAccount;
import io.github.jhipster.sample.domain.Label;
import io.github.jhipster.sample.domain.Operation;
import io.github.jhipster.sample.domain.User;
import io.github.jhipster.sample.repository.UserRepository;
import javax.cache.CacheManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@IntegrationTest
@Transactional
class CacheConfigurationIT {

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private KeyGenerator keyGenerator;

    @Test
    void cacheManagerShouldBeInitialized() {
        assertThat(cacheManager).isNotNull();
    }

    @Test
    void keyGeneratorShouldBePrefixedKeyGenerator() {
        assertThat(keyGenerator).isNotNull().isInstanceOf(PrefixedKeyGenerator.class);
    }

    @Test
    void expectedCachesShouldExist() {
        assertThat(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE)).isNotNull();
        assertThat(cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE)).isNotNull();

        assertThat(cacheManager.getCache(User.class.getName())).isNotNull();
        assertThat(cacheManager.getCache(Authority.class.getName())).isNotNull();
        assertThat(cacheManager.getCache(User.class.getName() + ".authorities")).isNotNull();

        assertThat(cacheManager.getCache(BankAccount.class.getName())).isNotNull();
        assertThat(cacheManager.getCache(BankAccount.class.getName() + ".operations")).isNotNull();

        assertThat(cacheManager.getCache(Label.class.getName())).isNotNull();
        assertThat(cacheManager.getCache(Label.class.getName() + ".operations")).isNotNull();

        assertThat(cacheManager.getCache(Operation.class.getName())).isNotNull();
        assertThat(cacheManager.getCache(Operation.class.getName() + ".labels")).isNotNull();
    }
}
