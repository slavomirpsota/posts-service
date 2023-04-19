package com.amcef.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostDto {
    @NotNull
    private Integer id;
    @NotNull
    private Integer userId;
    @NotNull
    @NotBlank
    @Size(max = 1024, message = "Maximum length exceeded")
    private String title;
    @NotNull
    @NotBlank
    @Size(max = 1024, message = "Maximum length exceeded")
    private String body;

}
