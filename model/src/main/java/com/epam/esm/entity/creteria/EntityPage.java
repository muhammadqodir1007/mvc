package com.epam.esm.entity.creteria;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EntityPage {

    private int page = 1;

    private int size = 5;

    private String sortDir = "ASC";

    private String sortBy = "id";


    public EntityPage(int page, int size) {
        this.page = page;
        this.size = size;
    }
}
