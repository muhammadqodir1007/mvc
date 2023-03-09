package com.epam.esm.dto;

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
    private BigDecimal price;
    private String create_date;
    private long userId;
    private List<GiftCertificateDto> gift_certificates;

    public OrderDto(long id, BigDecimal price) {
        this.id = id;
        this.price = price;
    }

    public OrderDto(int i, List<GiftCertificateDto> giftCertificateDtos) {

        this.id=i;
        this.gift_certificates=giftCertificateDtos;
    }
}
