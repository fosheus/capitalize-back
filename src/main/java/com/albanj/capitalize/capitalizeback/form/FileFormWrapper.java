package com.albanj.capitalize.capitalizeback.form;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;

@Getter
public class FileFormWrapper {

	private MultipartFile file;
	private String text;
	private String id;
	private String path;
	private String name;
	private String type;

}
