package ru.skelotron.win63.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.skelotron.win63.service.CityReader;

@Component
@Slf4j
public class CitySynchronizationTask {
    private final CityReader cityReader;

    @Autowired
    public CitySynchronizationTask(CityReader cityReader) {
        this.cityReader = cityReader;
    }

    @Scheduled(initialDelay = 10 * 1000, fixedRate = 24 * 60 * 60 * 1000) // 1 day
    public void synchronize() {
        log.info("Start City Synchronization");
        cityReader.read();
        log.info("End City Synchronization");
    }
}
