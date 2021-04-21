package ru.skelotron.win63;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.skelotron.win63.email_sender.EmailPublisher;
import ru.skelotron.win63.email_sender.EmailSender;
import ru.skelotron.win63.entity.CategoryEntity;
import ru.skelotron.win63.entity.Item;
import ru.skelotron.win63.entity.Settings;
import ru.skelotron.win63.repository.CategoryRepository;
import ru.skelotron.win63.repository.ItemRepository;
import ru.skelotron.win63.repository.SettingsRepository;
import ru.skelotron.win63.service.item.ItemService;
import ru.skelotron.win63.service.subscription.filter.checker.FilterCheckerFactory;
import ru.skelotron.win63.service.subscription.filter.checker.ItemFilterChecker;

@SpringBootApplication
@Configuration
@EnableScheduling
@SuppressWarnings("MethodMayBeStatic")
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
    public Queue queue() {
        return new Queue(EmailPublisher.QUEUE_NAME, false);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("email-exchange");
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with("");
    }

    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter messageListenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(EmailPublisher.QUEUE_NAME);
        container.setMessageListener(messageListenerAdapter);
        return container;
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(EmailSender emailSender) {
        return new MessageListenerAdapter(emailSender, "send");
    }

    @Bean
    public CommandLineRunner demo(
            ItemService itemService, SettingsRepository settingsRepository, CategoryRepository categoryRepository,
            ItemRepository itemRepository, FilterCheckerFactory filterCheckerFactory, ItemFilterChecker itemFilterChecker,
            DemoData demoData) {
        return (args -> {
            settingsRepository.save( new Settings( "pageSize", 20 ) );
            settingsRepository.save( new Settings( "target.url", "победа-63.рф" ) );

            filterCheckerFactory.register(Item.class, itemFilterChecker);

            demoData.prepare();

            for (CategoryEntity category : categoryRepository.findAll()) {
                log.debug(category.toString());
            }

            for (Item item : itemRepository.findAll()) {
                log.debug(item.toString());
            }
        });
    }
}
