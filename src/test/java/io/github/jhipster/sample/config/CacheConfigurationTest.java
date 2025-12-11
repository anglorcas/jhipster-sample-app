package io.github.jhipster.sample.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tech.jhipster.config.JHipsterProperties;

class CacheConfigurationTest {

    @Test
    void constructorShouldInitializeJcacheConfiguration() {
        JHipsterProperties properties = new JHipsterProperties();
        properties.getCache().getEhcache().setMaxEntries(100L);
        properties.getCache().getEhcache().setTimeToLiveSeconds(60);

        CacheConfiguration config = new CacheConfiguration(properties);

        // Use reflection to verify the private field was created
        var field = CacheConfiguration.class.getDeclaredFields();
        boolean found = false;

        for (var f : field) {
            if (f.getName().equals("jcacheConfiguration")) {
                f.setAccessible(true);
                try {
                    Object value = f.get(config);
                    assertThat(value).isNotNull();
                    found = true;
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        assertThat(found).isTrue();
    }

    @Test
    void hibernatePropertiesCustomizerShouldSetCacheManager() {
        CacheConfiguration config = new CacheConfiguration(new JHipsterProperties());
        var cacheManager = Mockito.mock(javax.cache.CacheManager.class);

        var customizer = config.hibernatePropertiesCustomizer(cacheManager);

        java.util.Map<String, Object> props = new java.util.HashMap<>();
        customizer.customize(props);

        assertThat(props).containsKey(org.hibernate.cache.jcache.ConfigSettings.CACHE_MANAGER);
        assertThat(props.get(org.hibernate.cache.jcache.ConfigSettings.CACHE_MANAGER)).isSameAs(cacheManager);
    }

    @Test
    void keyGeneratorShouldReturnPrefixedKeyGenerator() {
        CacheConfiguration config = new CacheConfiguration(new JHipsterProperties());
        var keyGenerator = config.keyGenerator();

        assertThat(keyGenerator).isInstanceOf(tech.jhipster.config.cache.PrefixedKeyGenerator.class);
    }
}
