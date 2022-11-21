package org.example.controller;

import org.example.dao.repo.tagRepo.TagRepo;
import org.example.entity.GiftCertificate;
import org.example.entity.Tag;
import org.example.service.FieldNotFoundException;
import org.example.service.GiftService;
import org.example.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/tag")
public class TagController {
    @Autowired
    TagService tagService;


    @GetMapping
    public List<Tag> list() throws ClassNotFoundException {
        List<Tag> all = tagService.getAll();
        return all;
    }

    @PostMapping
    public ResponseEntity addTag(@RequestBody Tag tag) {

        tagService.insert(tag);

        return ResponseEntity.ok("success");

    }


    @GetMapping("/{id}")
    public Tag tag(@PathVariable int id) throws FieldNotFoundException {


        Tag byId = tagService.getOne(id);
        return byId;

    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTags(@PathVariable int id) throws FieldNotFoundException {

        int delete = tagService.delete(id);

        if (delete == 0) return ResponseEntity.badRequest().body("something went wrong");

        return ResponseEntity.ok("success");


    }


}
