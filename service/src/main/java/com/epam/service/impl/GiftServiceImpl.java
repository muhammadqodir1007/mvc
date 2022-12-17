package com.epam.service.impl;

import com.epam.dao.config.ForDate;
import com.epam.dao.repo.giftRepo.GiftRepo;
import com.epam.dao.repo.giftRepo.impl.GiftRepoImpl;
import com.epam.dao.repo.tagRepo.impl.TagRepoImpl;
import com.epam.entity.GiftCertificate;
import com.epam.entity.Tag;
import com.epam.exceptions.DaoException;
import com.epam.exceptions.IncorrectParameterException;
import com.epam.response.ApiResponse;
import com.epam.service.GiftService;
import com.epam.validator.GiftCertificateValidator;
import com.epam.validator.IdentifiableValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class GiftServiceImpl implements GiftService {
    final private GiftRepo giftRepo;
    final private TagRepoImpl tagRepo;

    @Autowired
    public GiftServiceImpl(GiftRepoImpl giftRepo, TagRepoImpl tagRepo) {
        this.giftRepo = giftRepo;
        this.tagRepo = tagRepo;
    }

    @Override
    public ApiResponse addGift(GiftCertificate giftCertificate) throws DaoException, IncorrectParameterException {
        GiftCertificateValidator.validate(giftCertificate);
        giftRepo.addGift(giftCertificate);
        return new ApiResponse("added", true);
    }

    @Override
    public List<GiftCertificate> getAll() throws DaoException {
        return giftRepo.list();
    }

    @Override
    public ApiResponse update(long id, GiftCertificate giftCertificate) throws DaoException, IncorrectParameterException {
        IdentifiableValidator.validateId(id);
        giftCertificate.setId((int) id);
        GiftCertificateValidator.validateForUpdate(giftCertificate);
        giftCertificate.setLastUpdateDate(ForDate.getDate().toString());
        List<Tag> requestTags = giftCertificate.getTags();
        List<Tag> createdTags = tagRepo.getAll();
        saveNewTags(requestTags, createdTags);
        giftRepo.update((int) id, giftCertificate);
        return new ApiResponse("edited", true);
    }

    private void saveNewTags(List<Tag> requestTags, List<Tag> createdTags) throws DaoException {
        if (requestTags == null) {
            return;
        }
        for (Tag requestTag : requestTags) {
            boolean isExist = false;
            for (Tag createdTag : createdTags) {
                if (Objects.equals(requestTag.getName(), createdTag.getName())) {
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                tagRepo.insert(requestTag);
            }
        }
    }

    @Override
    public ApiResponse delete(int id) throws DaoException, IncorrectParameterException {
        IdentifiableValidator.validateId(id);
        giftRepo.delete(id);
        return new ApiResponse("deleted", true);
    }

    @Override
    public GiftCertificate findById(int id) throws DaoException {
        return giftRepo.getById(id);
    }

    @Override
    public ApiResponse addAssociatedTag(int id, Tag tag) throws DaoException {
        boolean exist = tagRepo.existByName(tag.getName());

        if (!exist) {
            tagRepo.insert(tag);
        }
        Tag tag1 = tagRepo.getByName(tag.getName());

        giftRepo.addAssociatedTags(id, tag1.getId());
        return new ApiResponse("added", true);


    }

    @Override
    public List<Tag> list(int id) throws DaoException, IncorrectParameterException {
        IdentifiableValidator.validateId(id);
        return giftRepo.getAssociatedTags(id);
    }


    @Override
    public ApiResponse deleteAssociatedTags(long id, List<Tag> tags) throws DaoException, IncorrectParameterException {
        IdentifiableValidator.validateId(id);
        GiftCertificateValidator.validateListOfTags(tags);
        giftRepo.deleteTagsAssociation(id, tags);
        return new ApiResponse("deleted", true);
    }

    @Override
    public List<GiftCertificate> doFilter(Map<String, String> requestParams) throws DaoException {
        return giftRepo.getWithFilters(requestParams);
    }

}




