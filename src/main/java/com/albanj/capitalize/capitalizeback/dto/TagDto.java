package com.albanj.capitalize.capitalizeback.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TagDto implements Serializable {

    private Integer id;
    private String label;
    private String type;
}
