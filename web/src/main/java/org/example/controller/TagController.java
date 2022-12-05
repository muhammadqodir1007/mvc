package org.example.controller;

import org.example.entity.Tag;
import org.example.exceptions.DaoException;
import org.example.exceptions.IncorrectParameterException;
import org.example.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Class {@code TagController} is an endpoint of the API which allows to perform CRD operations on tags.
 * Annotated by {@link RestController} with no parameters to provide an answer in application/json.
 * Annotated by {@link RequestMapping} with parameter value = "/tag".
 * So that {@code TagController} is accessed by sending request to /tag.
 */
@RestController
@RequestMapping("/api/tag")
public class TagController {
    @Autowired
    TagService tagService;
    /**
     * Method for getting all tags from data source.
     *
     * @return List of found tags
     * @throws DaoException an exception thrown in case tags not found
     */
    @GetMapping
    public List<Tag> list() throws DaoException {
        List<Tag> all = tagService.getAll();
        return all;
    }

    /**
     * Method for saving new tag.
     *
     * @param tag tag for saving
     * @return CREATED HttpStatus
     * @throws DaoException                the exception thrown in case of saving error
     * @throws IncorrectParameterException an exception thrown in case of invalid tag name
     */

    @PostMapping
    public ResponseEntity addTag(@RequestBody Tag tag) throws DaoException, IncorrectParameterException {
        tagService.insert(tag);
        return ResponseEntity.status(HttpStatus.CREATED).body("{\"response\" : \"created\"}");
    }

    /**
     * Method for getting tag by ID.
     *
     * @param id ID of tag to get
     * @return Found tag entity
     * @throws DaoException an exception thrown in case tag with such ID not found
     */


    @GetMapping(value = "/{id}", produces = "application/json")
    public Tag tag(@PathVariable int id) throws DaoException {
        Tag byId = tagService.getOne(id);
        return byId;
    }

    /**
     * Method for removing tag by ID.
     *
     * @param id ID of tag to remove
     * @return NO_CONTENT HttpStatus
     * @throws DaoException an exception thrown in case tag with such ID not found
     */
    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity deleteTags(@PathVariable int id) throws DaoException {
        int delete = tagService.delete(id);
        if (delete == 0)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("{\"response\" : \"something went wrong \"}");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("{\"response\" : \"deleted\"}");

    }


}
