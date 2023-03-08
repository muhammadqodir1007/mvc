package com.epam.esm.entity.creteria;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GiftSearchCriteria {
    private String name;
    private String description;
    private String tagName;
    private String price;
    private String duration;
    private String createDate;
    private String lastUpdateDate;
}
