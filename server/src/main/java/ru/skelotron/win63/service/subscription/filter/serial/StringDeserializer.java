package ru.skelotron.win63.service.subscription.filter.serial;


public class StringDeserializer implements Deserializer<String> {
    @Override
    public String deserialize(String value) {
        return value;
    }
}
