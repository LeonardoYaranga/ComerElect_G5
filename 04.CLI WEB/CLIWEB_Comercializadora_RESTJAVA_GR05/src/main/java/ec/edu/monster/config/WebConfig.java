package ec.edu.monster.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC Configuration for Spring Boot 3.x
 * Ensures REST API endpoints are properly handled
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        // This ensures that path matching works correctly for REST endpoints
        // In Spring Boot 3.x, we need to explicitly configure this
        configurer.setUseTrailingSlashMatch(false);
    }
}
