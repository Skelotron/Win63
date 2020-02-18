package ru.skelotron.win63;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.skelotron.win63.entity.entity.CategoryEntity;
import ru.skelotron.win63.entity.entity.Item;
import ru.skelotron.win63.entity.entity.Settings;
import ru.skelotron.win63.repository.CategoryRepository;
import ru.skelotron.win63.repository.ItemRepository;
import ru.skelotron.win63.repository.SettingsRepository;
import ru.skelotron.win63.service.item.ItemService;


@SpringBootApplication
@Configuration
@EnableScheduling
public class Application {
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
    public CommandLineRunner demo(ItemService itemService, SettingsRepository settingsRepository, CategoryRepository categoryRepository, ItemRepository itemRepository) {
        return (args -> {
            settingsRepository.save( new Settings( "pageSize", 20 ) );

//            loadCategoryService.load();

            for (CategoryEntity category : categoryRepository.findAll()) {
                log.info(category.toString());
            }

            for (Item item : itemRepository.findAll()) {
                log.info(item.toString());
            }

//            itemService.load( categoryRepository.findByUrl("/catalog/telefony/") );
        });
    }
}