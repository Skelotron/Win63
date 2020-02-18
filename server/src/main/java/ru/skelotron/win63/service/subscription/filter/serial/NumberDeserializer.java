package ru.skelotron.win63.service.subscription.filter.serial;

public class NumberDeserializer implements Deserializer<Double> {
    @Override
    public Double deserialize(String value) {
        return Double.valueOf(value);
    }
}
