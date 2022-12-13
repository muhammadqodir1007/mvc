package com.epam.service;

import com.epam.exceptions.IncorrectParameterException;
import com.epam.entity.GiftCertificate;
import com.epam.entity.Tag;
import com.epam.exceptions.DaoException;
import org.springframework.util.MultiValueMap;

import java.util.List;

public interface GiftService {

    List<GiftCertificate> doFilter(MultiValueMap<String, String> requestParams) throws DaoException;

    void deleteAssociatedTags(long id, List<Tag> tags) throws DaoException, IncorrectParameterException;

    List<Tag> list(int id) throws DaoException, IncorrectParameterException;

    void addAssociatedTag(int id, Tag tag) throws DaoException;

    GiftCertificate findById(int id) throws DaoException;

    int delete(int id) throws DaoException;


    void update(long id, GiftCertificate giftCertificate) throws DaoException, IncorrectParameterException;

    List<GiftCertificate> getAll() throws DaoException;

    void addGift(GiftCertificate giftCertificate) throws DaoException, IncorrectParameterException;

}
