package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GiftCertificateDto extends RepresentationModel<GiftCertificateDto> {
    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int duration;
    private String create_date;
    private String last_update_date;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<TagDto> tags;

    public GiftCertificateDto(long id, String name, String description, BigDecimal price, int duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
    }

    public GiftCertificateDto(long id) {
        this.id = id;
    }
}
