package com.albanj.capitalize.capitalizeback.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import com.albanj.capitalize.capitalizeback.dto.TagDto;
import com.albanj.capitalize.capitalizeback.mapper.TagMapper;
import com.albanj.capitalize.capitalizeback.repository.TagRepository;
import com.albanj.capitalize.capitalizeback.service.TagService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TagServiceImpl implements TagService {

	private final TagRepository repo;

	@Autowired
	public TagServiceImpl(TagRepository repo) {
		this.repo = repo;
	}

	@Override
	public List<TagDto> getAll(String label, Integer limit) {
		if (label != null && label.length() > 0) {
			PageRequest limitPage = PageRequest.of(0, limit);
			return TagMapper.map(this.repo.findDistinctByLabelStartsWithIgnoreCaseOrderByLabel(label, limitPage));
		} else {
			return new ArrayList<>();
		}
	}

	@Override
	public List<String> getExistingLabels(String label, Integer limit) {
		if (label != null && label.length() > 0) {
			PageRequest limitPage = PageRequest.of(0, limit);
			return this.repo.findTagLabels(label, limitPage);
		} else {
			return new ArrayList<>();
		}
	}

}
