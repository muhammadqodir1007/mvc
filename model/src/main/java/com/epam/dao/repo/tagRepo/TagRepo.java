package com.epam.dao.repo.tagRepo;

import com.epam.entity.Tag;
import com.epam.exceptions.DaoException;

import java.util.List;

public interface TagRepo {

    List<Tag> getAll() throws DaoException;

    int insert(Tag tag) throws DaoException;

    Tag getById(int id) throws DaoException;

    int delete(int id) throws DaoException;

    boolean existByName(String name);

    List<Tag> getByName(String name) throws DaoException;

}
