package com.epam.controller;

import com.epam.entity.Tag;
import com.epam.exceptions.DaoException;
import com.epam.response.ApiResponse;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@RequestMapping("/api/tag")
public class TagController {
    final
    TagService tagService;


    /**
     * Method for getting all tags from data source.
     * @return List of found tags
     * @throws DaoException an exception thrown in case tags not found
     */
    @GetMapping
    public List<Tag> list() throws DaoException {
        return tagService.getAll();
    }

    /**
     * Method for saving new tag.
     * @param tag tag for saving
     * @return CREATED HttpStatus
     * @throws DaoException                the exception thrown in case of saving error
     * @throws IncorrectParameterException an exception thrown in case of invalid tag name
     */

    @PostMapping
    public ResponseEntity<ApiResponse> addTag(@RequestBody Tag tag) throws DaoException, IncorrectParameterException {
        ApiResponse apiResponse = tagService.insert(tag);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    /**
     * Method for getting tag by ID.
     * @param id ID of tag to get
     * @return Found tag entity
     * @throws DaoException an exception thrown in case tag with such ID not found
     */


    @GetMapping(value = "/{id}")
    public Tag tag(@PathVariable int id) throws DaoException, IncorrectParameterException {
        return tagService.getOne(id);
    }

    /**
     * Method for removing tag by ID.
     * @param id ID of tag to remove
     * @return NO_CONTENT HttpStatus
     * @throws DaoException an exception thrown in case tag with such ID not found
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ApiResponse> deleteTags(@PathVariable int id) throws DaoException {
        ApiResponse apiResponse = tagService.delete(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(apiResponse);
    }


}
