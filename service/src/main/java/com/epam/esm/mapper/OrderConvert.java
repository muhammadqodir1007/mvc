package com.epam.esm.mapper;


import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class OrderConvert {
    public static OrderDto toDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setPrice(order.getPrice());
        orderDto.setCreateDate(order.getCreateDate().toString());
        if (order.getGiftCertificates() != null) {
            List<GiftCertificateDto> giftDtos = order.getGiftCertificates()
                    .stream()
                    .map(GiftConverter::toDto)
                    .collect(Collectors.toList());
            orderDto.setGiftCertificateDtos(giftDtos);
        }
        if (order.getUser() != null) {
            orderDto.setUserId(order.getUser().getId());
        }
        return orderDto;
    }

    public static Order toEntity(OrderDto orderDto) {
        Order order = new Order();
        order.setId(orderDto.getId());
        order.setPrice(orderDto.getPrice());
        order.setCreateDate(LocalDateTime.parse(orderDto.getCreateDate()));
        if (orderDto.getGiftCertificateDtos() != null) {
            List<GiftCertificate> giftList = orderDto.getGiftCertificateDtos()
                    .stream()
                    .map(GiftConverter::toEntity)
                    .collect(Collectors.toList());
            order.setGiftCertificates(new HashSet<>(giftList));
        }
        return order;
    }
}
