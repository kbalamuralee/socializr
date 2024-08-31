package com.socializr.postr.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import com.socializr.postr.domain.model.Post;
import com.socializr.postr.persistence.entity.PostEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PostDomainMapper {
    PostEntity mapPostToPostEntity(Post post);

    Post mapPostEntityToPost(PostEntity postEntity);
}
