package ru.skelotron.win63.http_entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import ru.skelotron.win63.serialization.BooleanJsonDeserializer;
import ru.skelotron.win63.serialization.DateJsonDeserializer;

import java.util.Date;

@Setter
@Getter
public class City {
    private String id;
    @JsonProperty("area_id")
    private String areaId;
    @JsonProperty("type_id")
    private String typeId;
    private String sort;
    private String title;
    private String alias;
    private String declension;
    private String metrika;
    private String webmaster;
    @JsonProperty("webmaster_https")
    private String webmasterHttps;
    private String robots;
    @JsonProperty("vk_group_id")
    private String vkGroupId;
    @JsonProperty("vk_group_link")
    private String vkGroupLink;
    @JsonProperty("ig_group_link")
    private String igGroupLink;
    private String priority;
    private String kladr;
    @JsonProperty("is_show")
    @JsonDeserialize(using = BooleanJsonDeserializer.class)
    private boolean show;
    @JsonProperty("insert_time")
    @JsonDeserialize(using = DateJsonDeserializer.class)
    private Date insertTime;
    @JsonProperty("update_time")
    @JsonDeserialize(using = DateJsonDeserializer.class)
    private Date updateTime;
    @JsonProperty("delete_time")
    @JsonDeserialize(using = DateJsonDeserializer.class)
    private Date deleteTime;
    @JsonProperty("is_delete")
    @JsonDeserialize(using = BooleanJsonDeserializer.class)
    private boolean delete;
    @JsonProperty("default_city")
    private String defaultCity;
    private String timezone;
    @JsonProperty("area_title")
    private String areaTitle;
    private Integer filials;
    private String landing;
    @JsonProperty("full_sub_domain")
    private String fullSubDomain;
    @JsonProperty("full_sub_domain_without_path")
    private String fullSubDomainWithoutPath;
    @JsonProperty("full_sub_domain_with_path")
    private String fullSubDomainWithPath;
    @JsonProperty("filials_declension")
    private String filialsDeclension;
    private Boolean active;
}
