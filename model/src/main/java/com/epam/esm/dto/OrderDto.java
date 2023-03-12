package com.epam.esm.dto;

import com.epam.esm.validation.number.ValidPrice;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto extends RepresentationModel<OrderDto> {
    private long id;
    @ValidPrice
    private BigDecimal price;
    private String createDate;
    private long userId;
    private List<GiftCertificateDto> giftCertificateDtos;

    public OrderDto(long id, BigDecimal price) {
        this.id = id;
        this.price = price;
    }

    public OrderDto(int i, List<GiftCertificateDto> giftCertificateDtos) {

        this.id=i;
        this.giftCertificateDtos =giftCertificateDtos;
    }
}
