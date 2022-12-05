import org.example.dao.config.PostgreSqlConfigForTest;
import org.example.dao.repo.giftRepo.GiftRepo;
import org.example.entity.GiftCertificate;
import org.example.entity.Tag;
import org.example.exceptions.DaoException;
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

import static org.junit.Assert.assertEquals;

@ContextConfiguration(classes = PostgreSqlConfigForTest.class)
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class GiftRepoTest {

    @Autowired
    GiftRepo giftRepo;
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

    private static final Tag TAG_2 = new Tag(2, "tagName3");

    private static final GiftCertificate GIFT_CERTIFICATE_1 = new GiftCertificate(1, "giftCertificate1",
            "description1", 10.0, 1, "2012-12-12",
            "2012-12-12", Collections.singletonList(new Tag(2, "tagName3")));

    private static final GiftCertificate GIFT_CERTIFICATE_2 = new GiftCertificate(2, "giftCertificate3",
            "description3", 30.0, 3, "2012-12-12",
            "2012-12-12", Collections.singletonList(new Tag(2, "tagName3")));

    private static final GiftCertificate GIFT_CERTIFICATE_3 = new GiftCertificate(3, "giftCertificate2",
            "description2", 20.0, 2, "2012-12-12",
            "2012-12-12", Collections.singletonList(new Tag(0, null)));
    private static final GiftCertificate GIFT_CERTIFICATE_4 = new GiftCertificate(3, "giftCertificate2",
            "description2", 20.2, 2, "2012-12-12",
            "2012-12-12");

    private static final String SORT_PARAMETER = "DESC";

    @Test
    void testGetById() throws DaoException {
        GiftCertificate actual = giftRepo.getById(GIFT_CERTIFICATE_3.getId());
        GiftCertificate expected = GIFT_CERTIFICATE_4;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testGetAll() throws DaoException {
        List<GiftCertificate> actual = giftRepo.list();
        System.out.println(actual);

        List<GiftCertificate> expected = Arrays.asList(GIFT_CERTIFICATE_1, GIFT_CERTIFICATE_2, GIFT_CERTIFICATE_3);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testGetAssociatedTags() throws DaoException {
        List<Tag> actual = giftRepo.getAssociatedTags(2);
        List<Tag> expected = Collections.singletonList(TAG_2);
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
        parameters.put("sortByName", SORT_PARAMETER);
        parameters.put("partOfDescription", null);
        parameters.put("partOfTagName", null);
        parameters.put("tag_name", TAG_2.getName());
        parameters.put("sortByTagName", null);
        parameters.put("sortByCreateDate", null);
        parameters.put("name", null);
        parameters.put("partOfName", null);
        List<GiftCertificate> actual = giftRepo.getWithFilters(parameters);
        List<GiftCertificate> expected = Arrays.asList(GIFT_CERTIFICATE_2, GIFT_CERTIFICATE_1);

        Assertions.assertEquals(expected, actual);
    }


}



