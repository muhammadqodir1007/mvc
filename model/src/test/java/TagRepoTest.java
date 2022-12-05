import org.example.dao.config.PostgreSqlConfigForTest;
import org.example.dao.repo.tagRepo.TagRepo;
import org.example.entity.Tag;
import org.example.exceptions.DaoException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@ContextConfiguration(classes = PostgreSqlConfigForTest.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class TagRepoTest {
    private static boolean filled;
    Tag tag1 = new Tag(1, "tagName1");
    Tag tag2 = new Tag(2, "tagName2");
    Tag tag3 = new Tag(3, "tagName3");
    Tag tag4 = new Tag(4, "tagName4");
    Tag tag5 = new Tag(5, "tagName5");
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private TagRepo tagRepo;

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
    void testGetById() throws DaoException { //done

        Tag actual = tagRepo.getById(1);
        assertEquals(tag1.getName(), actual.getName());

    }


    @Test
    void getByIdError() {
        try {
            tagRepo.getById(78);
        } catch (DaoException e) {
            assertEquals("404001", e.getMessage());
        }
    }


    @Test
    void listTest() throws DaoException {
        List<Tag> expected = Arrays.asList(tag1, tag2, tag3, tag4, tag5);
        List<Tag> all = tagRepo.getAll();
        assertEquals(expected, all);

    }


    @Test
    void testPut() {

        StringBuilder builder = new StringBuilder();
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int length = 7;
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            sb.append(randomChar);
        }

        String randomString = sb.toString();
        boolean b = tagRepo.existByName(randomString);
        assertTrue(!b);


    }


    @Test
    void testGetByNameError() {
        try {
            StringBuilder builder = new StringBuilder();
            String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            StringBuilder sb = new StringBuilder();
            Random random = new Random();
            int length = 7;
            for (int i = 0; i < length; i++) {
                int index = random.nextInt(alphabet.length());
                char randomChar = alphabet.charAt(index);
                sb.append(randomChar);
            }

            String randomString = sb.toString();
            List<Tag> byName = tagRepo.getByName(randomString);
        } catch (Exception e) {
            assertEquals("404003", e.getMessage());
        }


    }

}


