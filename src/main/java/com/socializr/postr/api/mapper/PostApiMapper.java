package com.socializr.postr.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.socializr.postr.api.model.PostCreationRequest;
import com.socializr.postr.api.model.PostCreationResponse;
import com.socializr.postr.api.model.PostResponse;
import com.socializr.postr.domain.model.Post;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PostApiMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationTimestamp", ignore = true)
    @Mapping(target = "lastUpdateTimestamp", ignore = true)
    Post mapPostCreationRequestToPost(PostCreationRequest postCreationRequest);

    PostCreationResponse mapPostToPostCreationResponse(Post post);

    PostResponse mapPostToPostResponse(Post post);
}
