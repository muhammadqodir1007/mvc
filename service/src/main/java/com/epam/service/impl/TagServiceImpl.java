package com.epam.service.impl;

import com.epam.exceptions.IncorrectParameterException;
import com.epam.dao.repo.tagRepo.TagRepo;
import com.epam.dao.repo.tagRepo.impl.TagRepoImpl;
import com.epam.entity.Tag;
import com.epam.exceptions.DaoException;
import com.epam.service.TagService;
import com.epam.validator.TagValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    final TagRepo tagRepo;

    public TagServiceImpl(TagRepoImpl tagRepo) {
        this.tagRepo = tagRepo;
    }

    @Override
    public List<Tag> getAll() throws DaoException {

        try {
            return tagRepo.getAll();

        } catch (Exception e) {
            throw new DaoException("no entities");
        }
    }

    @Override
    public int insert(Tag tag) throws DaoException, IncorrectParameterException {
        TagValidator.validateName(tag.getName());
        boolean exist = tagRepo.existByName(tag.getName());
        if (exist) throw new IncorrectParameterException("already exist");
        return tagRepo.insert(tag);
    }

    @Override
    public Tag getOne(int id) throws DaoException {
        return tagRepo.getById(id);
    }

    @Override
    public int delete(int i) throws DaoException {

        return tagRepo.delete(i);


    }


}
