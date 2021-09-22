package ru.skelotron.win63.service.response_reader;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.skelotron.win63.http_entities.Request;
import ru.skelotron.win63.http_entities.Response;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Component
@Qualifier("DummyResponseReader")
@Slf4j
public class DummyResponseReader implements ResponseReader {
    @Override
    public Response read(Request request) {
        log.info("Request: {}", request);
        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass().getResource("/sample.json").toURI()));
            String body = lines.stream().reduce("", (l1, l2) -> l1 + "\n" + l2);
            ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper.readValue(body, Response.class);
        } catch (Exception e) {
            return null;
        }
    }
}
