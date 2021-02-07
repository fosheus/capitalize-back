package com.albanj.capitalize.capitalizeback.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class FileContentDto implements Serializable {

    private String data;

    public FileContentDto(String data) {
        this.data=data;
    }
}
