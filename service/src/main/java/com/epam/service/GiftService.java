package com.epam.service;

import com.epam.entity.GiftCertificate;
import com.epam.entity.Tag;
import com.epam.exceptions.DaoException;
import com.epam.exceptions.IncorrectParameterException;
import com.epam.response.ApiResponse;

import java.util.List;
import java.util.Map;

public interface GiftService {


    ApiResponse deleteAssociatedTags(long id, List<Tag> tags) throws DaoException, IncorrectParameterException;

    List<Tag> list(int id) throws DaoException, IncorrectParameterException;

    ApiResponse addAssociatedTag(int id, Tag tag) throws DaoException;

    GiftCertificate findById(int id) throws DaoException;

    ApiResponse delete(int id) throws DaoException, IncorrectParameterException;


    ApiResponse update(long id, GiftCertificate giftCertificate) throws DaoException, IncorrectParameterException;

    List<GiftCertificate> getAll() throws DaoException;

    ApiResponse addGift(GiftCertificate giftCertificate) throws DaoException, IncorrectParameterException;

    List<GiftCertificate> doFilter(Map<String, String> requestParams) throws DaoException;
}
