package com.epam;

import com.epam.dao.config.PostgreSqlConfigForTest;
import com.epam.dao.repo.giftRepo.impl.GiftRepoImpl;
import com.epam.entity.GiftCertificate;
import com.epam.entity.Tag;
import com.epam.exceptions.DaoException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.util.*;

import static com.epam.dao.repo.creator.FilterParameters.sortByName;

@ContextConfiguration(classes = PostgreSqlConfigForTest.class)
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class GiftRepoTest {

    @Autowired
    GiftRepoImpl giftRepo;

    @Autowired
    JdbcTemplate jdbcTemplate;

    private static boolean filled;


    @AfterEach
    void cleanUp() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "gift_certificates_tags", "gift_certificates", "tags");
        filled = false;
    }

    @BeforeEach
    void fill() {
        if (filled) return;
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("/fillingTestTables.sql"));
        populator.execute(Objects.requireNonNull(jdbcTemplate.getDataSource()));
        filled = true;
    }

    @Test
    void shouldGetById() throws DaoException {
        GiftCertificate actual = giftRepo.getById(GiftCertificates.GIFT_CERTIFICATE_3.getId());
        GiftCertificate expected = GiftCertificate.builder().id(3)
                .name("giftCertificate2").description("description2").price(20.2).duration(2)
                .createDate("2012-12-12").lastUpdateDate("2012-12-12").tags(new ArrayList<>()).build();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldGetAll() throws DaoException {
        List<GiftCertificate> actual = giftRepo.list();
        List<GiftCertificate> expected = Arrays.asList(GiftCertificates.GIFT_CERTIFICATE_1, GiftCertificates.GIFT_CERTIFICATE_2, GiftCertificates.GIFT_CERTIFICATE_3);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldGetAssociatedTags() throws DaoException {
        List<Tag> actual = giftRepo.getAssociatedTags(2);
        List<Tag> expected = Collections.singletonList(GiftCertificates.TAG_2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void shouldGetByIdError() {
        try {
            giftRepo.getById(34734);
        } catch (DaoException e) {
            Assertions.assertEquals("404001", e.getMessage());
        }

    }

    @Test
    void shouldGetTagsError() {
        try {
            giftRepo.getAssociatedTags(134);
        } catch (DaoException e) {
            Assertions.assertEquals("404000", e.getMessage());
        }
    }

    @Test
    void shouldThrowSaveError() {
        try {
            giftRepo.addGift(new GiftCertificate());
        } catch (DaoException e) {
            Assertions.assertEquals("404004", e.getMessage());
        }
    }

    @Test

    public void shouldGetWithFilters() throws DaoException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(sortByName.toString(), GiftCertificates.SORT_PARAMETER);
        List<GiftCertificate> actual = giftRepo.getWithFilters(parameters);
        List<GiftCertificate> expected = Arrays.asList(GiftCertificates.GIFT_CERTIFICATE_2, GiftCertificates.GIFT_CERTIFICATE_3, GiftCertificates.GIFT_CERTIFICATE_1);

        Assertions.assertEquals(expected, actual);
    }


}



