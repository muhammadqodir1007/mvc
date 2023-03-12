package com.epam.esm.service.impl;

import com.epam.esm.config.MessageByLang;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.creteria.EntityPage;
import com.epam.esm.entity.creteria.GiftSearchCriteria;
import com.epam.esm.exception.DuplicateEntityException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.mapper.GiftConverter;
import com.epam.esm.pagination.Page;
import com.epam.esm.pagination.PaginationResult;
import com.epam.esm.repository.GiftCertificateDao;
import com.epam.esm.repository.TagDao;
import com.epam.esm.repository.search.GiftSearchDao;
import com.epam.esm.service.GiftCertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateDao giftDoa;
    private final TagDao tagDao;
    private final GiftSearchDao giftSearchDao;

    @Override
    public PaginationResult<GiftCertificateDto> getAll(EntityPage entityPage) {
        PaginationResult<GiftCertificate> giftList = giftDoa.findAll(entityPage);
        return getGiftCertificateDtoPaginationResult(entityPage, giftList);
    }

    @Override
    public GiftCertificateDto getById(long id) {
        Optional<GiftCertificate> g = giftDoa.findById(id);
        if (g.isEmpty()) {
            throw new ResourceNotFoundException(MessageByLang.getMessage("RESOURCE_NOT_FOUND_WITH_ID") + id);
        }
        return GiftConverter.toDto(g.get());
    }

    @Override
    public GiftCertificateDto insert(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate;
        giftCertificate = GiftConverter.toEntity(giftCertificateDto);

        Optional<GiftCertificate> savedGift = giftDoa.findByName(giftCertificateDto.getName());
        if (savedGift.isPresent()) {
            throw new DuplicateEntityException();
        }

        Set<Tag> requestTags = giftCertificate.getTags();
        Set<Tag> reqTagsWithIds = checkTags(requestTags);
        giftCertificate.setTags(reqTagsWithIds);
        return GiftConverter.toDto(giftDoa.insert(giftCertificate));
    }

    @Override
    public GiftCertificateDto update(long id, GiftCertificateDto newGiftDto) {

        Optional<GiftCertificate> oldGift = giftDoa.findById(id);
        if (oldGift.isEmpty() || Objects.isNull(newGiftDto)) {
            throw new ResourceNotFoundException(MessageByLang.getMessage("RESOURCE_NOT_FOUND_WITH_ID") + id);
        }
        Optional<GiftCertificate> savedGift = giftDoa.findByName(newGiftDto.getName());
        if (savedGift.isPresent()) {
            throw new DuplicateEntityException();
        }
        GiftCertificate reqGift = GiftConverter.toEntity(newGiftDto);

        Set<Tag> requestTags = reqGift.getTags();
        Set<Tag> reqTagWithIds = checkTags(requestTags);
        reqGift.setTags(reqTagWithIds);

        GiftCertificate newGift = checkGiftCertificate(reqGift, oldGift.get());
        return GiftConverter.toDto(giftDoa.update(newGift));
    }

    @Override
    public boolean deleteById(long id) {
        Optional<GiftCertificate> g = giftDoa.findById(id);
        if (g.isEmpty()) {
            throw new ResourceNotFoundException(MessageByLang.getMessage("RESOURCE_NOT_FOUND_WITH_ID") + id);
        }
        return giftDoa.remove(g.get());
    }

    @Override
    public PaginationResult<GiftCertificateDto> getWithFilter(GiftSearchCriteria searchCriteria, EntityPage entityPage) {
        PaginationResult<GiftCertificate> giftList = giftSearchDao.findAllWithParams(searchCriteria, entityPage);
        return getGiftCertificateDtoPaginationResult(entityPage, giftList);
    }

    private PaginationResult<GiftCertificateDto> getGiftCertificateDtoPaginationResult(EntityPage entityPage, PaginationResult<GiftCertificate> giftList) {
        if (entityPage.getPage() == 1 && giftList.getRecords().isEmpty()) {
            throw new ResourceNotFoundException(MessageByLang.getMessage("RESOURCE_NOT_FOUND"));
        }
        List<GiftCertificateDto> giftDtos = giftList.getRecords().stream().map(GiftConverter::toDto).collect(Collectors.toList());
        return new PaginationResult<>(new Page(giftList.getPage().getCurrentPageNumber(), giftList.getPage().getLastPageNumber(), giftList.getPage().getPageSize(), giftList.getPage().getTotalRecords()), giftDtos);
    }

    private Set<Tag> checkTags(Set<Tag> requestTags) {
        Set<Tag> reqTagWithIds = new HashSet<>();
        if (requestTags == null) {
            return null;
        }
        for (Tag requestTag : requestTags) {
            Optional<Tag> optionalTag = tagDao.findByName(requestTag.getName());
            if (optionalTag.isPresent()) {
                reqTagWithIds.add(optionalTag.get());
            } else reqTagWithIds.add(requestTag);
        }
        return reqTagWithIds;
    }

    private GiftCertificate checkGiftCertificate(GiftCertificate newGift, GiftCertificate oldGift) {
        if (newGift.getName() != null) {
            oldGift.setName(newGift.getName());
        }
        if (newGift.getDescription() != null) {
            oldGift.setDescription(newGift.getDescription());
        }
        if (newGift.getPrice() != null) {
            oldGift.setPrice(newGift.getPrice());
        }
        if (newGift.getDuration() != 0) {
            oldGift.setDuration(newGift.getDuration());
        }
        if (!newGift.getTags().isEmpty()) {
            oldGift.setTags(newGift.getTags());
        }
        return oldGift;
    }
}
