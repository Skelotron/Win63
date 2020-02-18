package ru.skelotron.win63.schedule;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.skelotron.win63.service.item.ItemSynchronizationService;

@Component
@Log
public class ItemSynchronizationTask {
    private final ItemSynchronizationService itemSynchronizationService;

    @Autowired
    public ItemSynchronizationTask(ItemSynchronizationService itemSynchronizationService) {
        this.itemSynchronizationService = itemSynchronizationService;
    }

//    @Scheduled(fixedRate = 5 * 60 * 1000) // 5 minutes
    public void synchronizeItems() {
        itemSynchronizationService.synchronize();
    }
}
