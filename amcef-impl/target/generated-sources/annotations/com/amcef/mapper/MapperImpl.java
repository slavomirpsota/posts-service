package com.amcef.mapper;

import com.amcef.client.ExtPostsDto;
import com.amcef.dto.PostDto;
import com.amcef.entity.Post;
import org.springframework.stereotype.Component;

/*
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-19T23:20:23+0200",
    comments = "version: 1.5.4.Final, compiler: javac, environment: Java 18.0.2 (IBM Corporation)"
)
*/
@Component
public class MapperImpl extends Mapper {

    @Override
    public ExtPostsDto postDtoToExtPostsDto(PostDto in) {
        if ( in == null ) {
            return null;
        }

        ExtPostsDto extPostsDto = new ExtPostsDto();

        extPostsDto.setId( in.getId() );
        extPostsDto.setUserId( in.getUserId() );
        extPostsDto.setTitle( in.getTitle() );
        extPostsDto.setBody( in.getBody() );

        return extPostsDto;
    }

    @Override
    public PostDto extPostDtoToPostsDto(ExtPostsDto in) {
        if ( in == null ) {
            return null;
        }

        PostDto postDto = new PostDto();

        postDto.setId( in.getId() );
        postDto.setUserId( in.getUserId() );
        postDto.setTitle( in.getTitle() );
        postDto.setBody( in.getBody() );

        return postDto;
    }

    @Override
    public Post postDtoToPostEntity(PostDto in) {
        if ( in == null ) {
            return null;
        }

        Post post = new Post();

        post.setId( in.getId() );
        post.setUserId( in.getUserId() );
        post.setTitle( in.getTitle() );
        post.setBody( in.getBody() );

        return post;
    }

    @Override
    public Post extPostsDtoToPostEntity(ExtPostsDto in) {
        if ( in == null ) {
            return null;
        }

        Post post = new Post();

        post.setId( in.getId() );
        post.setUserId( in.getUserId() );
        post.setTitle( in.getTitle() );
        post.setBody( in.getBody() );

        return post;
    }
}
