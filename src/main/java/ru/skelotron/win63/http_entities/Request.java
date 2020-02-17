package ru.skelotron.win63.http_entities;

import lombok.*;

@Getter
@Builder
@ToString
public class Request {
    private final String uri;
    private final Integer quantity;
    private final String s;
    private final Integer city;
    private final String category;
    private final Integer page;

    public String formatParameters() {
        StringBuilder sb = new StringBuilder();
        if ( quantity != null ) {
            sb.append("q=").append(quantity).append("&");
        }
        if ( s != null ) {
            sb.append("s=").append(s).append("&");
        }
        if ( city != null ) {
            sb.append("c=").append(city).append("&");
        }
        if ( category != null ) {
            sb.append("cg=").append(category);
        }
        if (sb.charAt(sb.length()) == '&') {
            sb.deleteCharAt(sb.length());
        }
        return sb.toString();
    }
}
