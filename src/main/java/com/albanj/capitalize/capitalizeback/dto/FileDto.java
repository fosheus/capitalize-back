package com.albanj.capitalize.capitalizeback.dto;

import lombok.Data;

@Data
public class FileDto {

    private Integer id;
    private String path;
    private String name;
    private String type;
    // private String content;

    public FileDto() {
    }

    public FileDto(Integer id, String path, String name, String type) {
        this.id = id;
        this.path = path;
        this.name = name;
        this.type = type;
    }
}
