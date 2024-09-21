package prography.team5.server.common.config;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import prography.team5.server.auth.controller.AuthArgumentResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final AuthArgumentResolver authArgumentResolver;
    private final String[] domains;

    public WebMvcConfig(final AuthArgumentResolver authArgumentResolver, @Value("${cors.domains:http://localhost:8080}") final String... domains) {
        System.out.println(Arrays.toString(domains));
        this.authArgumentResolver = authArgumentResolver;
        this.domains = domains;
    }

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
        registry.addMapping("/**")
                .allowedOrigins(domains)  // Swagger UI 도메인
                .allowCredentials(true)  // 쿠키 허용
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");   // 모든 헤더 허용
    }
}
