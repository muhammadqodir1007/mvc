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

import static com.epam.dao.repo.creator.FilterParameters.*;
import static org.junit.Assert.assertEquals;

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
        populator.execute(jdbcTemplate.getDataSource());
        filled = true;
    }

    @Test
    void testGetById() throws DaoException {
        GiftCertificate actual = giftRepo.getById(GiftCertificates.GIFT_CERTIFICATE_3.getId());
        GiftCertificate expected = GiftCertificates.GIFT_CERTIFICATE_4;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testGetAll() throws DaoException {
        List<GiftCertificate> actual = giftRepo.list();
        System.out.println(actual);

        List<GiftCertificate> expected = Arrays.asList(GiftCertificates.GIFT_CERTIFICATE_1, GiftCertificates.GIFT_CERTIFICATE_2, GiftCertificates.GIFT_CERTIFICATE_3);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testGetAssociatedTags() throws DaoException {
        List<Tag> actual = giftRepo.getAssociatedTags(2);
        List<Tag> expected = Collections.singletonList(GiftCertificates.TAG_2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testGetByIdError() {
        try {
            giftRepo.getById(34734);
        } catch (DaoException e) {
            assertEquals("404001", e.getMessage());
        }

    }

    @Test
    void testGetTagsErrorError() {
        try {
            giftRepo.getAssociatedTags(134);
        } catch (DaoException e) {
            assertEquals("404000", e.getMessage());
        }
    }

    @Test
    void saveError() {
        try {
            giftRepo.addGift(new GiftCertificate());
        } catch (DaoException e) {
            assertEquals("404004", e.getMessage());
        }
    }

    @Test
    public void testGetWithFilters() throws DaoException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(SORT_BY_NAME, GiftCertificates.SORT_PARAMETER);
        parameters.put(PART_OF_DESCRIPTION, null);
        parameters.put(PART_OF_TAG_NAME, null);
        parameters.put(TAG_NAME, GiftCertificates.TAG_2.getName());
        parameters.put(SORT_BY_TAG_NAME, null);
        parameters.put(SORT_BY_CREATE_DATE, null);
        parameters.put(NAME, null);
        parameters.put(PART_OF_NAME, null);
        List<GiftCertificate> actual = giftRepo.getWithFilters(parameters);
        List<GiftCertificate> expected = Arrays.asList(GiftCertificates.GIFT_CERTIFICATE_2, GiftCertificates.GIFT_CERTIFICATE_1);

        Assertions.assertEquals(expected, actual);
    }


}



