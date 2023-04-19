package com.amcef.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ExtPostsDto implements Serializable {
    private Integer id;
    private Integer userId;
    private String title;
    private String body;
}
