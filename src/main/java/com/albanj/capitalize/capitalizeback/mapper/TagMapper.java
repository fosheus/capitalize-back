package com.albanj.capitalize.capitalizeback.mapper;

import com.albanj.capitalize.capitalizeback.dto.TagDto;
import com.albanj.capitalize.capitalizeback.entity.Tag;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TagMapper {

    public static TagDto map(Tag tag) {
        if (tag==null) return null;
        TagDto dto = new TagDto();
        dto.setId(tag.getId());
        dto.setLabel(tag.getLabel());
        //dto.setType(TagTypeMapper.map(tag.getType()));
        return dto;
    }

    public static Tag map(TagDto dto) {
        if (dto==null) return null;
        Tag tag = new Tag();
        tag.setId(dto.getId());
        tag.setLabel(dto.getLabel());
        //tag.setType(TagTypeMapper.map(dto.getType()));
        return tag;
    }

    public static Set<Tag> map(List<TagDto> dtos) {
        if (CollectionUtils.isEmpty(dtos)) return Collections.emptySet();
        return dtos.stream().map(TagMapper::map).collect(Collectors.toSet());
    }
}
