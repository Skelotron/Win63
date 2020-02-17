package ru.skelotron.win63.http_entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.skelotron.win63.serialization.BooleanJsonDeserializer;
import ru.skelotron.win63.serialization.DateJsonDeserializer;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class GoodsEntry {
    private String title;
    private String alias;
    private String barcode;
    @JsonProperty("insert_time")
    @JsonDeserialize(using = DateJsonDeserializer.class)
    private Date insertTime;
    private String amount;
    @JsonProperty("amount_percent")
    private String amountPercent;
    private Boolean bron;
    @JsonProperty("bron_time")
    private String bronTime;
    private String status;
    @JsonProperty("fk_category")
    private String fkCategory;
    @JsonProperty("new")
    @JsonDeserialize(using = BooleanJsonDeserializer.class)
    private boolean isNew;
    @JsonProperty("flag_delivery")
    @JsonDeserialize(using = BooleanJsonDeserializer.class)
    private boolean flagDelivery;
    private String credit;
    @JsonProperty("fk_city")
    private List<String> fkCity;
    @JsonProperty("basket_visible")
    private Boolean basketVisible;
    private String url;
    @JsonProperty("city_title")
    private String cityTitle;
    private String alt;
    @JsonProperty("photo_list")
    private List<Photo> photos;
}
