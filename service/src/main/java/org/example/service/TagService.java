package org.example.service;

import org.example.dao.repo.tagRepo.TagRepo;
import org.example.entity.Tag;
import org.example.exceptions.DaoException;
import org.example.exceptions.IncorrectParameterException;
import org.example.validator.TagValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    final TagRepo tagRepo;

    public TagService(TagRepo tagRepo) {
        this.tagRepo = tagRepo;
    }


    public List<Tag> getAll() throws DaoException {

        try {
            return tagRepo.getAll();

        } catch (Exception e) {
            throw new DaoException("no entities");
        }
    }

    public int insert(Tag tag) throws DaoException, IncorrectParameterException {
        TagValidator.validateName(tag.getName());
        boolean exist = tagRepo.existByName(tag.getName());
        if (exist) throw new IncorrectParameterException("already exist");
        return tagRepo.insert(tag);
    }
    public Tag getOne(int id) throws DaoException {
        return tagRepo.getById(id);
    }
    public int delete(int i) throws DaoException {

        return tagRepo.delete(i);


    }


}
