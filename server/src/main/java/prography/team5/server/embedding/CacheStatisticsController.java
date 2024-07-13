package prography.team5.server.embedding;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CacheStatisticsController {

    private final CacheManager cacheManager;

    @GetMapping("/cache/stats")
    public CacheStats getCacheStats() {
        Cache<Object, Object> cache = (Cache<Object, Object>) cacheManager.getCache("similarInformationCache").getNativeCache();
        return cache.stats();
    }
}
