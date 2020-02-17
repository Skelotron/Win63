package ru.skelotron.win63.schedule;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.skelotron.win63.service.CategoryReader;

@Component
@Log
public class CategorySynchronizationTask {

    private final CategoryReader categoryReader;

    @Autowired
    public CategorySynchronizationTask(CategoryReader categoryReader) {
        this.categoryReader = categoryReader;
    }

    @Scheduled(fixedRate = 24 * 60 * 60 * 1000) // 1 day
    public void synchronize() {
        log.info("Start Category Synchronization");
        categoryReader.read();
        log.info("End Category Synchronization");
    }
}
