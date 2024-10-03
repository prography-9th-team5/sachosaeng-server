package prography.team5.server.common.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import prography.team5.server.admin.AdminLoginInterceptor;
import prography.team5.server.auth.controller.AuthArgumentResolver;

@RequiredArgsConstructor
@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

    private final AuthArgumentResolver authArgumentResolver;
    private final AllowedHosts allowedHosts;
    private final AdminLoginInterceptor adminLoginInterceptor;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authArgumentResolver);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/*").addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/js/*").addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/icon/*").addResourceLocations("classpath:/static/icon/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        log.info("allowed-hosts : {}", allowedHosts.getDomains());
        registry.addMapping("/**")
                .allowedOrigins(allowedHosts.getDomains().toArray(new String[0]))  // Swagger UI 도메인
                .allowCredentials(true)  // 쿠키 허용
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");   // 모든 헤더 허용
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // /admin 경로에 대해 인터셉터 적용
        registry.addInterceptor(adminLoginInterceptor)
                .addPathPatterns("/admin/**", "/swagger-ui/**"); // /admin, swagger 경로에만 적용
    }
}
