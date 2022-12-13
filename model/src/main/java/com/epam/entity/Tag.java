package com.epam.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class Tag {

    private int id;
    private String name;


    public Tag(String name) {
        this.name = name;
    }
}
