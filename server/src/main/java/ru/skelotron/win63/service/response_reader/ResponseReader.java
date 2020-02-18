package ru.skelotron.win63.service.response_reader;

import ru.skelotron.win63.http_entities.Request;
import ru.skelotron.win63.http_entities.Response;

public interface ResponseReader {
    Response read(Request request);
}
