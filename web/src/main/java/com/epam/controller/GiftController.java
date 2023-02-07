package com.epam.controller;

import com.epam.entity.GiftCertificate;
import com.epam.entity.Tag;
import com.epam.exceptions.DaoException;
import com.epam.response.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Class {@code GiftController} is an endpoint of the API
 * which allows to perform CRUD operations on gift certificates.
 * Annotated by {@link RestController} with no parameters to provide an answer in application/json.
 * Annotated by {@link RequestMapping} with parameter value = "/gift".
 * So that {@code GiftCertificateController} is accessed by sending request to /gift.
 */
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/gift", produces = "application/json") //gift
public class GiftController {

    final GiftService giftService;

    /**
     * Method for getting all gift certificates from data source.
     *
     * @return List of found gift certificates
     * @throws DaoException an exception thrown in case gift certificates not found
     */
    @GetMapping
    public List<GiftCertificate> list() throws DaoException {
        return giftService.getAll();
    }

    /**
     * Method for adding new  gift certificate.
     *
     * @return CREATED HttpStatus
     * @throws DaoException the exception thrown in case of saving error
     */

    @PostMapping
    public ResponseEntity<ApiResponse> add(@RequestBody GiftCertificate giftCertificate) throws DaoException, IncorrectParameterException {
        ApiResponse apiResponse = giftService.addGift(giftCertificate);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    /**
     * Method for removing gift certificate by ID.
     *
     * @param id ID of gift certificate to remove
     * @return NO_CONTENT HttpStatus
     * @throws DaoException an exception thrown in case gift certificate with such ID not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable int id) throws DaoException, IncorrectParameterException {
        ApiResponse apiResponse = giftService.delete(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(apiResponse);
    }

    /**
     * Method for getting gift certificate by ID.
     *
     * @param id ID of gift certificate to get
     * @return Found gift certificate entity
     * @throws DaoException an exception thrown in case gift certificate with such ID not found
     */
    @GetMapping("/{id}")
    public GiftCertificate getById(@PathVariable int id) throws DaoException {
        return giftService.findById(id);
    }

    /**
     * Method for editing gift certificate.
     *
     * @param id ID of gift certificate
     * @return CREATED HttpStatus
     * @throws DaoException                the exception thrown in case of saving error
     * @throws IncorrectParameterException an exception thrown in case of invalid ID
     */

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable int id, @RequestBody GiftCertificate giftCertificate) throws DaoException, IncorrectParameterException {
        ApiResponse update = giftService.update(id, giftCertificate);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(update);
    }

    /**
     * Method for getting list of tags by gift certificate ID.
     *
     * @param id ID of gift certificate
     * @return List of found tags
     * @throws DaoException                an exception thrown in case tags not found
     * @throws IncorrectParameterException an exception thrown in case of invalid ID
     */

    @GetMapping("/{id}/tags")
    public List<Tag> getAssociatedTags(@PathVariable int id) throws DaoException, IncorrectParameterException {
        return giftService.list(id);
    }

    /**
     * Method for adding new tags to the gift certificate.
     *
     * @param id ID of gift certificate
     * @return CREATED HttpStatus
     * @throws DaoException the exception thrown in case of saving error
     */

    @PostMapping(value = "/{id}/tags")
    public ResponseEntity<ApiResponse> addAssociatedTags(@PathVariable int id, @RequestBody Tag tag) throws DaoException {
        ApiResponse apiResponse = giftService.addAssociatedTag(id, tag);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @DeleteMapping("/{id}/tags")
    public ResponseEntity<ApiResponse> deleteAssociatedTags(@RequestBody List<Tag> tags, @PathVariable long id) throws DaoException, IncorrectParameterException {
        ApiResponse apiResponse = giftService.deleteAssociatedTags(id, tags);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(apiResponse);
    }

    @GetMapping("/search")
    public List<GiftCertificate> giftCertificatesByParameter(@RequestParam Map<String, String> allRequestParams) throws DaoException {
        return giftService.doFilter(allRequestParams);
    }


}
