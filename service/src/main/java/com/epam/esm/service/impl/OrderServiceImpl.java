package com.epam.esm.service.impl;

import com.epam.esm.config.MessageByLang;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.entity.creteria.EntityPage;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.OrderConvert;
import com.epam.esm.mapper.UserConvert;
import com.epam.esm.pagination.Page;
import com.epam.esm.pagination.PaginationResult;
import com.epam.esm.repository.GiftCertificateDao;
import com.epam.esm.repository.OrderDao;
import com.epam.esm.repository.UserDao;
import com.epam.esm.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final UserDao userDao;
    private final GiftCertificateDao giftDao;
    private final OrderDao orderDao;

    @Override
    public PaginationResult<OrderDto> getAll(EntityPage entityPage) {
        PaginationResult<Order> orderList = orderDao.findAll(entityPage);
        return converter(orderList, entityPage);
    }

    @Override
    public OrderDto getById(long id) {
        Optional<Order> optionalOrder = orderDao.findById(id);
        if (optionalOrder.isEmpty()) {
            throw new ResourceNotFoundException(MessageByLang.getMessage("RESOURCE_NOT_FOUND_WITH_ID") + id);
        }
        return OrderConvert.toDto(optionalOrder.get());
    }

    @Override
    public OrderDto insert(OrderDto orderDto) {
        if (orderDto.getGiftCertificateDtos() == null) {
            throw new IllegalArgumentException(MessageByLang.getMessage("GIFT_CERTIFICATES_NOT_ENTERED"));
        }
        Order order = new Order();
        Optional<User> savedUser = userDao.findById(orderDto.getUserId());
        if (savedUser.isEmpty()) {
            throw new ResourceNotFoundException(MessageByLang.getMessage("RESOURCE_NOT_FOUND_WITH_ID") + orderDto.getUserId());
        }
        List<GiftCertificate> reqGiftList = new ArrayList<>();
        BigDecimal price = BigDecimal.valueOf(0);
        for (GiftCertificateDto giftDto : orderDto.getGiftCertificateDtos()) {
            Optional<GiftCertificate> savedGift = giftDao.findById(giftDto.getId());
            if (savedGift.isEmpty()) {
                throw new ResourceNotFoundException(MessageByLang.getMessage("RESOURCE_NOT_FOUND_WITH_ID") + giftDto.getId());
            }
            reqGiftList.add(savedGift.get());
            price = price.add(savedGift.get().getPrice());
        }

        order.setGiftCertificates(new HashSet<>(reqGiftList));
        order.setPrice(price);
        order.setUser(savedUser.get());
        return OrderConvert.toDto(orderDao.insert(order));
    }

    @Override
    public boolean deleteById(long id) {
        throw new NotYetImplementedException();
    }

    @Override
    public UserDto saveByUser(long userId, List<GiftCertificateDto> giftDtos) {
        OrderDto orderDto = new OrderDto();
        orderDto.setUserId(userId);
        orderDto.setGiftCertificateDtos(giftDtos);
        insert(orderDto);
        Optional<User> optionalUser = userDao.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException(MessageByLang.getMessage("RESOURCE_NOT_FOUND_WITH_ID") + userId);
        }
        return UserConvert.toDto(optionalUser.get());
    }

    @Override
    public PaginationResult<OrderDto> getOrderByUser(long userId, EntityPage entityPage) {
        PaginationResult<Order> orderList = orderDao.findOrdersByUser(userId, entityPage);
        return converter(orderList, entityPage);
    }

    private PaginationResult<OrderDto> converter(PaginationResult<Order> orderList, EntityPage entityPage) {
        if (entityPage.getPage() == 1 && orderList.getRecords().isEmpty()) {
            throw new ResourceNotFoundException(MessageByLang.getMessage("RESOURCE_NOT_FOUND"));
        }
        List<OrderDto> orderDtos = orderList.getRecords().stream().map(OrderConvert::toDto).collect(Collectors.toList());
        return new PaginationResult<>(new Page(orderList.getPage().getCurrentPageNumber(), orderList.getPage().getLastPageNumber(), orderList.getPage().getPageSize(), orderList.getPage().getTotalRecords()), orderDtos);
    }
}
