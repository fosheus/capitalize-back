package com.albanj.capitalize.capitalizeback.service;

import java.util.List;

import com.albanj.capitalize.capitalizeback.dto.TagDto;

public interface TagService {

	List<TagDto> getAll(String label, Integer limit);

	List<String> getExistingLabels(String label, Integer limit);

}
