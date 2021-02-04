package com.albanj.capitalize.capitalizeback.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class FileDto implements Serializable {

    private Integer id;
    private String path;
    private String name;
    private String type;
    //private String content;
}
