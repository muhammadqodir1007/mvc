package org.example.service;

import org.example.dao.repo.giftRepo.GiftRepo;
import org.example.dao.repo.tagRepo.TagRepo;
import org.example.entity.GiftCertificate;
import org.example.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GiftService {

    final GiftRepo giftRepo;

    final TagRepo tagRepo;

    @Autowired
    public GiftService(GiftRepo giftRepo, TagRepo tagRepo) {
        this.giftRepo = giftRepo;
        this.tagRepo = tagRepo;
    }


    public void addGift(GiftCertificate giftCertificate) {

        try {
            giftRepo.addGift(giftCertificate);
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }


    }

    public List<GiftCertificate> getAll() throws ClassNotFoundException {

        try {
            List<GiftCertificate> list = giftRepo.list();
            return list;
        } catch (Exception e) {
            throw new ClassNotFoundException();


        }

    }

    public int update(int id, GiftCertificate giftCertificate) {
        try {
            return giftRepo.update(id, giftCertificate);
        } catch (Exception e) {

            throw new IllegalArgumentException();
        }

    }

    public int delete(int id) {
        try {
            return giftRepo.delete(id);

        } catch (Exception e) {
            throw new IllegalArgumentException();
        }


    }

    public GiftCertificate findById(int id) throws ClassNotFoundException {

        try {
            return giftRepo.getById(id);
        } catch (Exception e) {
            throw new ClassNotFoundException();
        }
    }

    public void addAssociatedTag(int id, Tag tag) throws FieldNotFoundException {
        boolean exist = tagRepo.existByName(tag.getName());
        boolean existById = giftRepo.existById(id);
        System.out.println(existById);
        if (!existById) return;
        try {
            if (exist) {
                Tag byId = tagRepo.getByName(tag.getName());
                giftRepo.addAssociatedTags(id, byId.getId());
            } else {
                int insert = tagRepo.insert(tag);
                System.out.println(tagRepo.existByName(tag.getName()));
                Tag byName = tagRepo.getByName(tag.getName());
                giftRepo.addAssociatedTags(id, byName.getId());
            }
        } catch (Exception e) {
            throw new FieldNotFoundException("field not found");

        }

    }

    public List<Tag> list(int id) throws ClassNotFoundException {
        boolean b = giftRepo.existById(id);
        if (!b) return new ArrayList<>();


        try {

            return giftRepo.getAssociatedTags(id);


        } catch (Exception e) {
            throw new ClassNotFoundException();


        }

    }


    public int deleteAssociatedTag(int gifId, int tagId) throws FieldNotFoundException {

        try {
            int i = giftRepo.deleteAssociated(gifId, tagId);

            return i;
        } catch (Exception e) {
            throw new FieldNotFoundException("field not found with this id");
        }

    }


}

