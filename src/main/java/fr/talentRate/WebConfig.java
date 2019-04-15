package fr.talentRate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Global CORS configuration.
 * @author Aymeric
 *
 */
@org.springframework.context.annotation.Configuration
public class WebConfig {

    /** Reference to configuration. */
    @Autowired
    private Configuration config;

    /**
     * Web component configuration.
     * @return custom web configuration
     * cf: https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-developing-web-applications.html
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            /**
             * spring doc link:
             * docs.spring.io/spring-boot/docs/current/reference/html/boot-features-developing-web-applications.html
             */
            @Override
            public void addCorsMappings(final CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins(config.getfrontEndCrossOrigin()).allowedMethods("*");
            }
        };
    }
}
