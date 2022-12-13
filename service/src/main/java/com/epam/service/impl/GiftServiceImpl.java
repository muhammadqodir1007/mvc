package com.epam.service.impl;

import com.epam.dao.config.ForDate;
import com.epam.dao.repo.giftRepo.GiftRepo;
import com.epam.dao.repo.giftRepo.impl.GiftRepoImpl;
import com.epam.dao.repo.tagRepo.impl.TagRepoImpl;
import com.epam.entity.GiftCertificate;
import com.epam.entity.Tag;
import com.epam.exceptions.IncorrectParameterException;
import com.epam.service.GiftService;
import com.epam.validator.GiftCertificateValidator;
import com.epam.validator.IdentifiableValidator;
import com.epam.exceptions.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.epam.service.FilterParameters.*;

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
    public void addGift(GiftCertificate giftCertificate) throws DaoException, IncorrectParameterException {
        GiftCertificateValidator.validate(giftCertificate);

        try {
            giftRepo.addGift(giftCertificate);
        } catch (Exception e) {
            throw new DaoException("not saved");
        }

    }

    @Override
    public List<GiftCertificate> getAll() throws DaoException {

        List<GiftCertificate> list = giftRepo.list();
        return list;
    }

    @Override
    public void update(long id, GiftCertificate giftCertificate) throws DaoException, IncorrectParameterException {
        IdentifiableValidator.validateId(id);
        giftCertificate.setId((int) id);
        GiftCertificateValidator.validateForUpdate(giftCertificate);
        giftCertificate.setLastUpdateDate(ForDate.getDate().toString());
        List<Tag> requestTags = giftCertificate.getTags();
        List<Tag> createdTags = tagRepo.getAll();
        saveNewTags(requestTags, createdTags);
        giftRepo.update((int) id, giftCertificate);
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
    public int delete(int id) throws DaoException {
        return giftRepo.delete(id);
    }

    @Override
    public GiftCertificate findById(int id) throws DaoException {
        return giftRepo.getById(id);
    }

    @Override
    public void addAssociatedTag(int id, Tag tag) throws DaoException {
        boolean exist = tagRepo.existByName(tag.getName());
        if (exist) {
            List<Tag> tags = tagRepo.getByName(tag.getName());
            Tag byId = tags.get(0);
            giftRepo.addAssociatedTags(id, byId.getId());
        } else {
            tagRepo.insert(tag);
            List<Tag> tags = tagRepo.getByName(tag.getName());
            Tag byId = tags.get(0);
            giftRepo.addAssociatedTags(id, byId.getId());
        }


    }

    @Override
    public List<Tag> list(int id) throws DaoException, IncorrectParameterException {
        IdentifiableValidator.validateId(id);
        return giftRepo.getAssociatedTags(id);
    }

    @Override
    public void deleteAssociatedTags(long id, List<Tag> tags) throws DaoException, IncorrectParameterException {
        IdentifiableValidator.validateId(id);
        GiftCertificateValidator.validateListOfTags(tags);
        giftRepo.deleteTagsAssociation(id, tags);
    }

    @Override
    public List<GiftCertificate> doFilter(MultiValueMap<String, String> requestParams) throws DaoException {
        Map<String, String> map = new HashMap<>();
        map.put(NAME, getSingleRequestParameter(requestParams, NAME));
        map.put(TAG_NAME, getSingleRequestParameter(requestParams, TAG_NAME));
        map.put(PART_OF_NAME, getSingleRequestParameter(requestParams, PART_OF_NAME));
        map.put(PART_OF_DESCRIPTION, getSingleRequestParameter(requestParams, PART_OF_DESCRIPTION));
        map.put(PART_OF_TAG_NAME, getSingleRequestParameter(requestParams, PART_OF_TAG_NAME));
        map.put(SORT_BY_NAME, getSingleRequestParameter(requestParams, SORT_BY_NAME));
        map.put(SORT_BY_CREATE_DATE, getSingleRequestParameter(requestParams, SORT_BY_CREATE_DATE));
        map.put(SORT_BY_TAG_NAME, getSingleRequestParameter(requestParams, SORT_BY_TAG_NAME));
        return giftRepo.getWithFilters(map);
    }

    protected String getSingleRequestParameter(MultiValueMap<String, String> requestParams, String parameter) {
        if (requestParams.containsKey(parameter)) {
            return requestParams.get(parameter).get(0);
        } else {
            return null;
        }
    }


}

