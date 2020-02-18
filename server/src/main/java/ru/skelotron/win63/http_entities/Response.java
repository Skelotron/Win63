package ru.skelotron.win63.http_entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString(exclude = {"limits", "cities", "sort", "bannerImg", "bannerUrl"})
public class Response {
    private Map<Integer, Limit> limits;
    private Integer page;
    private Boolean min;
    private Boolean max;
    private Boolean search;
    private Boolean brand;
    private Boolean model;
    private Integer city;
    private Integer category;
    private Integer limit;
    private Boolean sale;
    private Boolean delivery;
    private Boolean n;
    private Boolean bl;
    private Boolean deliveryExist;
    private List<Sort> sort;
    @JsonProperty("banner_img")
    private String bannerImg;
    @JsonProperty("banner_url")
    private String bannerUrl;
    private List<Category> categories;
    private Map<String, City> cities;
    @JsonProperty("new")
    private String isNew;
    @JsonProperty("minprice")
    private Integer minPrice;
    @JsonProperty("maxprice")
    private Integer maxPrice;
    private Integer count;
    // black
    // params
    // start_minprice
    // start_maxprice
    // filter
    @JsonProperty("goods")
    private List<GoodsEntry> goods;
    // declension_count
    // declension_goods
    // minprice_v
    // maxprice_v
}
