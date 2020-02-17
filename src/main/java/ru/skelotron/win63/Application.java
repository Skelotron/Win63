package ru.skelotron.win63;

import com.google.common.util.concurrent.Futures;
import com.viber.bot.ViberSignatureValidator;
import com.viber.bot.api.ViberBot;
import com.viber.bot.message.TextMessage;
import com.viber.bot.profile.BotProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import ru.skelotron.win63.entity.CategoryEntity;
import ru.skelotron.win63.entity.Item;
import ru.skelotron.win63.entity.Settings;
import ru.skelotron.win63.repository.CategoryRepository;
import ru.skelotron.win63.repository.ItemRepository;
import ru.skelotron.win63.repository.SettingsRepository;
import ru.skelotron.win63.service.CategoryReader;
import ru.skelotron.win63.service.LoadCategoryService;
import ru.skelotron.win63.service.item.ItemService;

import javax.inject.Inject;
import java.util.Optional;

@SpringBootApplication
@EnableWebMvc
@Configuration
public class Application extends WebMvcConfigurerAdapter implements ApplicationListener<ApplicationReadyEvent> {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Inject
    private ViberBot bot;

    @Value("${application.viber-bot.webhook-url}")
    private String webhookUrl;

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasenames("settings", "application");
        source.setUseCodeAsDefaultMessage(true);

        return source;
    }

    @Bean
    public ViewResolver getViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".html");
        return resolver;
    }

    @Override
    public void configureDefaultServletHandling(
            DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public CommandLineRunner demo(CategoryReader categoryReader, ItemService itemService, SettingsRepository settingsRepository, CategoryRepository categoryRepository, ItemRepository itemRepository, LoadCategoryService loadCategoryService) {
        return (args -> {
            settingsRepository.save( new Settings( "pageSize", 20 ) );

//            loadCategoryService.load();

            categoryReader.read();

            for (CategoryEntity category : categoryRepository.findAll()) {
                log.info(category.toString());
            }

            for (Item item : itemRepository.findAll()) {
                log.info(item.toString());
            }

            itemService.load( categoryRepository.findByUrl("/catalog/telefony/") );
        });
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent appReadyEvent) {
        try {
//            bot.setWebhook(webhookUrl).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        bot.onMessageReceived((event, message, response) -> response.send(message)); // echos everything back
        bot.onConversationStarted(event -> Futures.immediateFuture(Optional.of( // send 'Hi UserName' when conversation is started
                new TextMessage("Hi " + event.getUser().getName()))));
    }

    @Configuration
    public class BotConfiguration {

        @Value("${application.viber-bot.auth-token}")
        private String authToken;

        @Value("${application.viber-bot.name}")
        private String name;

        @Value("${application.viber-bot.avatar:@null}")
        private String avatar;

        @Bean
        ViberBot viberBot() {
            return new ViberBot(new BotProfile(name, avatar), authToken);
        }

        @Bean
        ViberSignatureValidator signatureValidator() {
            return new ViberSignatureValidator(authToken);
        }
    }
}
