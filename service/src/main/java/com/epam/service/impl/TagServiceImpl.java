package com.epam.service.impl;

import com.epam.dao.repo.tagRepo.TagRepo;
import com.epam.entity.Tag;
import com.epam.exceptions.DaoException;
import com.epam.exceptions.IncorrectParameterException;
import com.epam.response.ApiResponse;
import com.epam.service.TagService;
import com.epam.validator.IdentifiableValidator;
import com.epam.validator.TagValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TagServiceImpl implements TagService {

    final TagRepo tagRepo;

    @Override
    public List<Tag> getAll() throws DaoException {
        return tagRepo.getAll();
    }

    @Override
    public ApiResponse insert(Tag tag) throws DaoException, IncorrectParameterException {
        TagValidator.validateName(tag.getName());
        boolean exist = tagRepo.existByName(tag.getName());
        if (exist) throw new IncorrectParameterException("AllReady Exist");
        TagValidator.validateName(tag.getName());
        tagRepo.insert(tag);
        return new ApiResponse("added", true);
    }

    @Override
    public Tag getOne(int id) throws DaoException, IncorrectParameterException {
        IdentifiableValidator.validateId(id);
        return tagRepo.getById(id);
    }

    @Override
    public ApiResponse delete(int i) throws DaoException {
        tagRepo.delete(i);
        return new ApiResponse("deleted", true);

    }


}
