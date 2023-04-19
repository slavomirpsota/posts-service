package com.amcef.controller;

import com.amcef.api.PostsApi;
import com.amcef.dto.PostDto;
import com.amcef.service.PostsService;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostsController implements PostsApi {

    private final PostsService postsService;

    public PostsController(@NonNull PostsService postsService) {
        this.postsService = postsService;
    }

    @Override
    @PostMapping()
    public ResponseEntity<PostDto> createPost(@RequestBody @Valid PostDto request) {
        return new ResponseEntity<>(postsService.createPost(request), HttpStatus.OK);
    }

    @Override
    @GetMapping("/byPostId/{postId}")
    public ResponseEntity<PostDto> getPostByPostId(@PathVariable int postId) {
        return new ResponseEntity<>(postsService.getPostByPostId(postId), HttpStatus.OK);
    }

    @Override
    @GetMapping("/byUserId/{userId}")
    public ResponseEntity<List<PostDto>> getPostsByUserId(@PathVariable int userId) {
        return new ResponseEntity<>(postsService.getAllPostsByUserId(userId), HttpStatus.OK);
    }

    @Override
    @PutMapping()
    public ResponseEntity<PostDto> updatePost(@RequestBody @Valid PostDto request) {
        return new ResponseEntity<>(postsService.updatePost(request), HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePostById(@PathVariable int postId) {
        postsService.deletePostById(postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
