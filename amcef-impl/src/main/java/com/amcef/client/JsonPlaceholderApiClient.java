package com.amcef.client;

import lombok.NonNull;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Component
public class JsonPlaceholderApiClient {
    /*
     * JsonPlaceholder api url resolver
     */
    private static final String jsonPlaceholderApi = "rest.placeholder.api-url";

    private final Environment environment;
    private final RestTemplate restTemplate;

    public JsonPlaceholderApiClient(@NonNull Environment environment) {
        this.environment = environment;
        this.restTemplate = new RestTemplate();
    }

    private String getClientUrl() { return this.environment.getRequiredProperty(jsonPlaceholderApi);}

    private String getEndpoint(String path) {return this.getClientUrl().concat(path);}

    public ResponseEntity<ExtPostsDto> getPostsById(int postId) {
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(getEndpoint("/posts/{postId}")).
                buildAndExpand(postId);
        return this.restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET, HttpEntity.EMPTY, ExtPostsDto.class);
    }

    public ResponseEntity<List<ExtPostsDto>> getPosts() {
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(getEndpoint("/posts"))
                .build();
        return this.restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<List<ExtPostsDto>>(){});
    }

    public ResponseEntity<ExtPostsDto> createPost(ExtPostsDto post) {
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(getEndpoint("/posts"))
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json; charset=UTF-8");
        HttpEntity<ExtPostsDto> request = new HttpEntity<>(post, headers);
        return this.restTemplate.exchange(uriComponents.toUriString(), HttpMethod.POST, request, ExtPostsDto.class);
    }

    public ResponseEntity<ExtPostsDto> updatePost(ExtPostsDto post) {
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(getEndpoint("/posts/{postId}"))
                .buildAndExpand(post.getId());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json; charset=UTF-8");
        HttpEntity<ExtPostsDto> request = new HttpEntity<>(post, headers);
        return this.restTemplate.exchange(uriComponents.toUriString(), HttpMethod.PUT, request, ExtPostsDto.class);
    }

    public ResponseEntity<Void> deletePost(int postId) {
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(getEndpoint("/posts/{postId}"))
                .buildAndExpand(postId);
        return this.restTemplate.exchange(uriComponents.toUriString(), HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);
    }

    public ResponseEntity<String> getUserById(int userId) {
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(getEndpoint("/users/{userId}"))
                .buildAndExpand(userId);
        return this.restTemplate.exchange(uriComponents.toUriString(), HttpMethod.GET, HttpEntity.EMPTY, String.class);
    }
}
