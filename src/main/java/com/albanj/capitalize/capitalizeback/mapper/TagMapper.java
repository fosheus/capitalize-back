package com.albanj.capitalize.capitalizeback.mapper;

import com.albanj.capitalize.capitalizeback.dto.TagDto;
import com.albanj.capitalize.capitalizeback.entity.Post;
import com.albanj.capitalize.capitalizeback.entity.Tag;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TagMapper {

    public static TagDto map(Tag tag) {
        if (tag == null)
            return null;
        TagDto dto = new TagDto();
        dto.setId(tag.getId());
        dto.setLabel(tag.getLabel());
        return dto;
    }

    public static Tag map(TagDto dto, Post post) {
        if (dto == null)
            return null;
        Tag tag = new Tag();
        tag.setId(dto.getId());
        tag.setLabel(dto.getLabel());
        tag.setPost(post);
        return tag;
    }

    public static Set<Tag> map(List<TagDto> dtos, Post post) {
        if (CollectionUtils.isEmpty(dtos))
            return Collections.emptySet();
        return dtos.stream().map(tag -> TagMapper.map(tag, post)).collect(Collectors.toSet());
    }

    public static List<TagDto> map(Set<Tag> entities) {
        if (CollectionUtils.isEmpty(entities))
            return Collections.emptyList();
        return entities.stream().map(TagMapper::map).collect(Collectors.toList());
    }

    public static List<TagDto> map(List<Tag> entities) {
        if (CollectionUtils.isEmpty(entities))
            return Collections.emptyList();
        return entities.stream().map(TagMapper::map).collect(Collectors.toList());
    }
}
