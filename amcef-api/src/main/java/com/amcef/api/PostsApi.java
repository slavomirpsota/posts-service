package com.amcef.api;

import com.amcef.dto.PostDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "amcef-posts-api", description = "This interface is used to manage posts in system and in external system via REST API")
public interface PostsApi {

    @Operation(summary = "This method creates post",
            description = "This method creates post in system db as well as sends it to external API, request is always validated")
    ResponseEntity<PostDto> createPost(PostDto request);

    @Operation(summary = "This method returns post by post id",
            description = "This method returns post, if it's not found in system db we check external API, if not found return null")
    ResponseEntity<PostDto> getPostByPostId(int postId);

    @Operation(summary = "This method returns all posts by user id",
            description = "This method returns all posts, if it's not found in system db we check external API, if not found return empty list")
    ResponseEntity<List<PostDto>> getPostsByUserId(int userId);

    @Operation(summary = "This method updates existing post",
            description = "This method updates existing post, it updates in external system as well, if post is not present in system db, but found" +
                    " in external system, we update and save locally as well as in external api")
    ResponseEntity<PostDto> updatePost(PostDto request);

    @Operation(summary = "This method deletes existing post",
    description = "This method deletes existing post, if post is not found in system db, we check external api for this post id and delete in both systems" +
            " if post is not found, no operation is performed")
    ResponseEntity<Void> deletePostById(int postId);

}
