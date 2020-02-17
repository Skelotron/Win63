package ru.skelotron.win63.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateJsonDeserializer extends JsonDeserializer<Date> {
    public static final ThreadLocal<DateFormat> DATE_FORMAT = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd hh:MM:ss"));

    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String valueAsString = p.getValueAsString();
        try {
            return (valueAsString != null) ? DATE_FORMAT.get().parse(valueAsString) : null;
        } catch (ParseException e) {
            return null;
        }
    }
}
