package com.albanj.capitalize.capitalizeback.dto;

import lombok.Data;

@Data
public class FileContentDto {

    private String data;

    public FileContentDto(String data) {
        this.data = data;
    }
}
