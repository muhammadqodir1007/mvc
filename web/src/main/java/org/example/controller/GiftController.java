package org.example.controller;

import org.example.dao.repo.giftRepo.GiftRepo;
import org.example.entity.GiftCertificate;
import org.example.entity.Tag;
import org.example.service.FieldNotFoundException;
import org.example.service.GiftService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GiftController {

    @Autowired
    GiftRepo giftRepo;
    @Autowired
    GiftService giftService;

    @GetMapping
    public List<GiftCertificate> list() {
        return giftRepo.list();

    }

    @PostMapping

    public ResponseEntity add(@RequestBody GiftCertificate giftCertificate) {
        giftService.addGift(giftCertificate); //done

        return ResponseEntity.ok().body("Success");


    }

    //GiftCertificate id
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable int id) {
        int delete = giftService.delete(id);
        if (delete != 0) return ResponseEntity.ok("success");
        return ResponseEntity.badRequest().body("not deleted");


    }

    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable int id) throws ClassNotFoundException {

        boolean existById = giftRepo.existById(id);
        if (!existById) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("GIT CERTIFICATE NOT FOUND");
        GiftCertificate byId = giftService.findById(id);
        System.out.println(byId.getName());
        return ResponseEntity.ok().body(byId);

    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable int id, @RequestBody GiftCertificate giftCertificate) throws ClassNotFoundException {

        boolean existById = giftRepo.existById(id);
        if (!existById) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("GIT CERTIFICATE NOT FOUND");

        int update = giftService.update(id, giftCertificate);
        if (update != 0) return ResponseEntity.status(HttpStatus.ACCEPTED).body(" updated!");
        return ResponseEntity.badRequest().body(" something went wrong !");


    }

    @GetMapping("/{id}/tags")
    public List<Tag> getAssociatedTags(@PathVariable int id) throws ClassNotFoundException {
        List<Tag> associatedTags = giftService.list(id);
        return associatedTags;

    }

    @PostMapping("/{id}/addTags")
    public ResponseEntity addAssociatedTags(@PathVariable int id, @RequestBody Tag tag) throws FieldNotFoundException {
        giftService.addAssociatedTag(id, tag);

        return ResponseEntity.ok("success");


    }

    @DeleteMapping("/{giftId}/{tagId}")
    public ResponseEntity deleteAssociatedTag(@PathVariable int giftId, @PathVariable int tagId) throws FieldNotFoundException {

        int i = giftService.deleteAssociatedTag(giftId, tagId);

        if (i == 0) return ResponseEntity.badRequest().body("something went wrong");
        return ResponseEntity.ok().body("success");
    }


}
