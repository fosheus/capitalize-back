package com.albanj.capitalize.capitalizeback.mapper;

import com.albanj.capitalize.capitalizeback.dto.TagTypeDto;
import com.albanj.capitalize.capitalizeback.entity.RefTagType;

public class TagTypeMapper {

    public static TagTypeDto map(RefTagType tagType) {
        if (tagType == null) return null;
        TagTypeDto dto = new TagTypeDto();
        dto.setId(tagType.getId());
        dto.setLabel(tagType.getLabel());
        return dto;
    }

    public static RefTagType map(TagTypeDto dto) {
        if (dto == null) return null;
        RefTagType tagType = new RefTagType();
        tagType.setId(dto.getId());
        return tagType;
    }
}
