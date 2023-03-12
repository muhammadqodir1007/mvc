package com.epam.esm.dto;

import com.epam.esm.validation.text.ValidName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserDto extends RepresentationModel<UserDto> {
    private long id;
    @ValidName
    private String name;
    private List<OrderDto> orders;

}
