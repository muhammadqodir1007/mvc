package com.epam.controller;

import com.epam.entity.GiftCertificate;
import com.epam.entity.Tag;
import com.epam.exceptions.DaoException;
import com.epam.exceptions.IncorrectParameterException;
import com.epam.service.impl.GiftServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Class {@code GiftController} is an endpoint of the API
 * which allows to perform CRUD operations on gift certificates.
 * Annotated by {@link RestController} with no parameters to provide an answer in application/json.
 * Annotated by {@link RequestMapping} with parameter value = "/gift".
 * So that {@code GiftCertificateController} is accessed by sending request to /gift.
 */
@RestController
@RequestMapping(value = "/api/gift", produces = "application/json") //gift
public class GiftController {
    final
    GiftServiceImpl giftService;

    @Autowired
    public GiftController(GiftServiceImpl giftService) {
        this.giftService = giftService;
    }

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
    public ResponseEntity add(@RequestBody GiftCertificate giftCertificate) throws DaoException, IncorrectParameterException {
        giftService.addGift(giftCertificate);
        return ResponseEntity.status(HttpStatus.CREATED).body("{\"response\" : \"created\"}");
    }

    /**
     * Method for removing gift certificate by ID.
     *
     * @param id ID of gift certificate to remove
     * @return NO_CONTENT HttpStatus
     * @throws DaoException an exception thrown in case gift certificate with such ID not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable int id) throws DaoException {
        int delete = giftService.delete(id);
        if (delete != 0) return ResponseEntity.ok("{\"response\" : \"deleted\"}");
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"response\" : \"not deleted\"}");
        }
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
        GiftCertificate byId = giftService.findById(id);
        return byId;
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
    public ResponseEntity update(@PathVariable int id, @RequestBody GiftCertificate giftCertificate) throws DaoException, IncorrectParameterException {
        giftService.update(id, giftCertificate);
        return ResponseEntity.status(HttpStatus.CREATED).body("{\"response\" : \"updated\"}");
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
    public ResponseEntity addAssociatedTags(@PathVariable int id, @RequestBody Tag tag) throws DaoException {
        giftService.addAssociatedTag(id, tag);
        return ResponseEntity.status(HttpStatus.CREATED).body("{\"response\" : \"created\"}");
    }

    @DeleteMapping("/{id}/tags")
    public ResponseEntity deleteAssociatedTags(@RequestBody List<Tag> tags,
                                               @PathVariable long id) throws DaoException, IncorrectParameterException {
        giftService.deleteAssociatedTags(id, tags);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Success");
    }

    @GetMapping("/search")
    public List<GiftCertificate> giftCertificatesByParameter(@RequestParam MultiValueMap<String, String> allRequestParams) throws DaoException {
        return giftService.doFilter(allRequestParams);
    }


}
