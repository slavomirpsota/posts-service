package com.amcef.mapper;

import com.amcef.client.ExtPostsDto;
import com.amcef.dto.PostDto;
import com.amcef.entity.Post;

@org.mapstruct.Mapper(componentModel = "spring")
public abstract class Mapper {

    public abstract ExtPostsDto postDtoToExtPostsDto(PostDto in);

    public abstract PostDto extPostDtoToPostsDto(ExtPostsDto in);

    public static PostDto postEntityToPostDto(Post in) {
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

    public abstract Post postDtoToPostEntity(PostDto in);
    public abstract Post extPostsDtoToPostEntity(ExtPostsDto in);

}
