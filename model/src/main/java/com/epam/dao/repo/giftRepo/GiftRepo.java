package com.epam.dao.repo.giftRepo;

import com.epam.entity.GiftCertificate;
import com.epam.entity.Tag;
import com.epam.exceptions.DaoException;

import java.util.List;
import java.util.Map;


public interface GiftRepo {

    List<GiftCertificate> list() throws DaoException;

    void addGift(GiftCertificate gift) throws DaoException;

    int delete(int id) throws DaoException;

    GiftCertificate getById(int id) throws DaoException;

    List<Tag> getAssociatedTags(int id) throws DaoException;

    void addAssociatedTags(int giftId, int tagId) throws DaoException;

    void deleteTagsAssociation(long certificateId, List<Tag> tags) throws DaoException;

    List<GiftCertificate> getWithFilters(Map<String, String> fields) throws DaoException;

    void update(int id, GiftCertificate item) throws DaoException;


}
