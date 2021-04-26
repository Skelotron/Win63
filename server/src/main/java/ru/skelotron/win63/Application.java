package ru.skelotron.win63;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.skelotron.win63.config.EmailConfig;
import ru.skelotron.win63.config.TelegramConfig;

@SpringBootApplication
@EnableScheduling
@Import({TelegramConfig.class, EmailConfig.class})
@SuppressWarnings("MethodMayBeStatic")
@PropertySources({
        @PropertySource("application.properties"),
        @PropertySource("settings.properties")
})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasenames("messages");
        source.setUseCodeAsDefaultMessage(true);

        return source;
    }
}
