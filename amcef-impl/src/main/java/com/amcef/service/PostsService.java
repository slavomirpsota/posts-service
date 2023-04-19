package com.amcef.service;

import com.amcef.client.ExtPostsDto;
import com.amcef.client.JsonPlaceholderApiClient;
import com.amcef.dto.PostDto;
import com.amcef.entity.Post;
import com.amcef.entity.QPost;
import com.amcef.mapper.Mapper;
import com.amcef.repository.PostsRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostsService {

    private final JsonPlaceholderApiClient client;
    private final PostsRepository repository;
    private final Mapper mapper;

    public PostsService(@NonNull JsonPlaceholderApiClient client,
                        @NonNull PostsRepository repository,
                        @NonNull Mapper mapper) {
        this.client = client;
        this.repository = repository;
        this.mapper = mapper;
    }

    public PostDto createPost(PostDto dto) {
        if (client.getUserById(dto.getUserId()).getStatusCode().is4xxClientError()) {
            throw new IllegalArgumentException(String.format("User with id: %s doesn't exist", dto.getUserId()));
        }
        if (checkPostExistInDb(dto.getId()).isPresent()) {
            throw new IllegalArgumentException(String.format("Post with id: %s already exists", dto.getId()));
        }
        ResponseEntity<ExtPostsDto> extPost = client.getPostsById(dto.getId());
        if (extPost.getStatusCode().is2xxSuccessful()) {
            repository.save(mapper.extPostsDtoToPostEntity(extPost.getBody()));
            throw new IllegalArgumentException(String.format("Post with id: %s already exists", dto.getId()));
        }

        repository.save(mapper.postDtoToPostEntity(dto));
        ResponseEntity<ExtPostsDto> response = client.createPost(mapper.postDtoToExtPostsDto(dto));
        checkResponseForError(response, dto.getId());
        return mapper.extPostDtoToPostsDto(response.getBody());
    }

    public PostDto getPostByPostId(int postId) {
        Optional<Post> post = checkPostExistInDb(postId);
        if(post.isPresent()) {
            return Mapper.postEntityToPostDto(post.get());
        } else {
            ResponseEntity<ExtPostsDto> extPost = client.getPostsById(postId);
            if (extPost.getStatusCode().is4xxClientError())
                throw new IllegalArgumentException(String.format("Post with id: %s doesn't exist", postId));
            return mapper.extPostDtoToPostsDto(extPost.getBody());
        }
    }

    public List<PostDto> getAllPostsByUserId(int userId) {
        BooleanBuilder expr = new BooleanBuilder();
        expr.and(QPost.post.userId.eq(userId));
        return repository.findAll(expr).stream().map(Mapper::postEntityToPostDto).collect(Collectors.toList());
    }

    public PostDto updatePost(PostDto dto) {
        if (client.getUserById(dto.getUserId()).getStatusCode().is4xxClientError()) {
            throw new IllegalArgumentException(String.format("User with id: %s doesn't exist", dto.getUserId()));
        }
        if (checkPostExistInDb(dto.getId()).isEmpty()) {
            throw new IllegalArgumentException(String.format("Post with id: %s doesn't exists", dto.getId()));
        }
        ResponseEntity<ExtPostsDto> extPost = client.getPostsById(dto.getId());
        if (!extPost.getStatusCode().is2xxSuccessful()) {
            throw new IllegalArgumentException(String.format("Post with id: %s doesn't exists", dto.getId()));
        }

        repository.save(mapper.postDtoToPostEntity(dto));
        return mapper.extPostDtoToPostsDto(client.createPost(mapper.postDtoToExtPostsDto(dto)).getBody());
    }

    public ResponseEntity<Void> deletePostById(int postId) {
        Optional<Post> post = checkPostExistInDb(postId);
        post.ifPresent(repository::delete);
        ResponseEntity<Void> response = client.deletePost(postId);
        if (response.getStatusCode().isError())
            throw new IllegalArgumentException(String.format("Error deleting post with id: %s", postId));
        return response;
    }

    private Optional<Post> checkPostExistInDb(int id) {
        BooleanBuilder expr = new BooleanBuilder();
        expr.and(QPost.post.id.eq(id));
        return repository.findOne(expr);
    }

    private <T> void checkResponseForError(ResponseEntity<T> response, int id) {
        if (response.getStatusCode().isError()) {
            throw new IllegalStateException(String.format("Error creating post with id: %s", id));
        }
    }
}
