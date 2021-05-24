package com.albanj.capitalize.capitalizeback.resource;

import java.util.List;

import com.albanj.capitalize.capitalizeback.dto.TagDto;
import com.albanj.capitalize.capitalizeback.service.TagService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/tags")
@Validated
@Slf4j
public class TagController {

	private final TagService service;

	@Autowired
	public TagController(TagService service) {
		this.service = service;
	}

	@GetMapping("/labels")
	public List<String> list(@RequestParam String label, @RequestParam String limit) {
		Integer limitInt = Integer.parseInt(limit);
		return this.service.getExistingLabels(label, limitInt);
	}

}
