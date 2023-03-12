package com.epam.esm.dto;

import com.epam.esm.validation.number.ValidPrice;
import com.epam.esm.validation.text.ValidDescription;
import com.epam.esm.validation.text.ValidName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GiftCertificateDto extends RepresentationModel<GiftCertificateDto> {
    private long id;
    @ValidName
    private String name;
    @ValidDescription
    private String description;
    @ValidPrice
    private BigDecimal price;
    @Min(value = 1)
    private int duration;
    private String createDate;
    private String lastUpdateDate;
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
