package com.epam.esm.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@RequiredArgsConstructor
public class TagDto extends RepresentationModel<TagDto> {
    private long id;
    private String name;


}

