package com.epam.service;

import com.epam.exceptions.IncorrectParameterException;
import com.epam.entity.Tag;
import com.epam.exceptions.DaoException;

import java.util.List;

public interface TagService {


    int delete(int i) throws DaoException;

    Tag getOne(int id) throws DaoException;

    int insert(Tag tag) throws DaoException, IncorrectParameterException;

    List<Tag> getAll() throws DaoException;
}
