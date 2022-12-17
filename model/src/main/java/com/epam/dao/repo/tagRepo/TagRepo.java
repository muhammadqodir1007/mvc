package com.epam.dao.repo.tagRepo;

import com.epam.entity.Tag;
import com.epam.exceptions.DaoException;

import java.util.List;

public interface TagRepo {

    List<Tag> getAll() throws DaoException;

    void insert(Tag tag) throws DaoException;

    Tag getById(int id) throws DaoException;

    void delete(int id) throws DaoException;

    boolean existByName(String name) throws DaoException;

    Tag getByName(String name) throws DaoException;

}
