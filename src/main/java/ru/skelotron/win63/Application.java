package ru.skelotron.win63;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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


@SpringBootApplication
@EnableWebMvc
@Configuration
public class Application extends WebMvcConfigurerAdapter {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

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
}
