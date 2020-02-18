package ru.skelotron.win63.http_entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.skelotron.win63.serialization.BooleanJsonDeserializer;
import ru.skelotron.win63.serialization.DateJsonDeserializer;

import java.util.Date;

@Getter
@Setter
@ToString
public class Category {
    private String id;
    private String uid;
    private String code;
    private String pcode;
    private String pid;
    private String title;
    private String alias;
    @JsonProperty("title_one")
    private String titleOne;
    private String declension;
    @JsonProperty("declension_one")
    private String declensionOne;
    @JsonProperty("declension_title")
    private String declensionTitle;
    @JsonProperty("declension_title_one")
    private String declensionTitleOne;
    @JsonProperty("is_show")
    @JsonDeserialize(using = BooleanJsonDeserializer.class)
    private boolean show;
    private String sort;
    @JsonProperty("insert_time")
    @JsonDeserialize(using = DateJsonDeserializer.class)
    private Date insertTime;
    @JsonProperty("update_time")
    @JsonDeserialize(using = DateJsonDeserializer.class)
    private Date updateTime;
    private String size;
    private String weight;
    @JsonProperty("api_on")
    @JsonDeserialize(using = BooleanJsonDeserializer.class)
    private boolean apiOn;
    private String prefix;
    @JsonProperty("prefix_control")
    private String prefixControl;
    private String password;
    @JsonProperty("name_flag")
    @JsonDeserialize(using = BooleanJsonDeserializer.class)
    private boolean nameFlag;
    @JsonProperty("search_method")
    private String searchMethod;
    private String main;
    private Integer goods;
    private String url;
}
