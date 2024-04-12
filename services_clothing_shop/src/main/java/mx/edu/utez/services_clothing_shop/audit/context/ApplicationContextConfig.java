package mx.edu.utez.services_clothing_shop.audit.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationContextConfig {
    @Bean
    public ApplicationContext applicationContext(ApplicationContext applicationContext) {
        return applicationContext;
    }
}
