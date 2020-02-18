package ru.skelotron.win63.service.response_reader;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.skelotron.win63.http_entities.Request;
import ru.skelotron.win63.http_entities.Response;
import ru.skelotron.win63.service.settings.SettingsService;

import java.io.IOException;

@Component
@Primary
public class HttpResponseReader implements ResponseReader {
    private final SettingsService settingsService;

    @Autowired
    public HttpResponseReader(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @Override
    public Response read(Request request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-requested-with", "XMLHttpRequest");
        headers.add(HttpHeaders.CONTENT_TYPE, "text/html; charset=UTF-8");

        String url = createUri(request);
        ResponseEntity<String> result = new RestTemplate().exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);

        try {
            String body = result.getBody();
            if (body == null) {
                return null;
            }
            ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper.readValue(body, Response.class);
        } catch (IOException e) {
            return null;
        }
    }

    private String createUri(Request request) {
        String path = settingsService.getHostUrl() + request.getUri();
        if (!path.endsWith("/")) {
            path += "/";
        }
        String uri = path + request.getPage() + "/";
        String parameters = request.formatParameters();
        if (!parameters.isEmpty()) {
            return uri + "?" + parameters;
        }
        return uri;
    }
}
