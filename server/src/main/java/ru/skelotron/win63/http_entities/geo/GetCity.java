package ru.skelotron.win63.http_entities.geo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class GetCity implements Serializable {
    private String alias;
    private String declension;
    private String filials;
    @JsonProperty("filials_declension")
    private String filialsDeclensions;
    @JsonProperty("full_sub_domain_without_path")
    private String fullSubDomainWithoutPath;
    private String id;
    private String latitude;
    private String longitude;
    private String sort;
    private String title;
}
