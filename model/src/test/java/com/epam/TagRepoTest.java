//package com.epam;
//
//import com.epam.dao.repo.tagRepo.impl.TagRepoImpl;
//import com.epam.entity.Tag;
//import com.epam.exceptions.DaoException;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.jdbc.JdbcTestUtils;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Objects;
//import java.util.Random;
//
//@ContextConfiguration(classes = PostgreSqlConfigForTest.class)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//@ActiveProfiles("test")
//@ExtendWith(SpringExtension.class)
//public class TagRepoTest {
//    private static boolean filled;
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//    @Autowired
//    private TagRepoImpl tagRepo;
//
//    @AfterEach
//    void cleanUp() {
//        JdbcTestUtils.deleteFromTables(jdbcTemplate, "gift_certificates_tags", "gift_certificates", "tags");
//        filled = false;
//    }
//
//    @BeforeEach
//    void fill() {
//        if (filled) return;
//        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
//        populator.addScript(new ClassPathResource("/fillingTestTables.sql"));
//        populator.execute(Objects.requireNonNull(jdbcTemplate.getDataSource()));
//        filled = true;
//    }
//
//
//    @Test
//    void getByIdError() {
//        try {
//            tagRepo.getById(787);
//        } catch (DaoException e) {
//            Assertions.assertEquals("Incorrect result size: expected 1, actual 0", e.getMessage());
//        }
//    }
//
//
//    @Test
//    void listTest() throws DaoException {
//        List<Tag> expected = Arrays.asList(Tags.tag1, Tags.tag2, Tags.tag3, Tags.tag4, Tags.tag5);
//        List<Tag> all = tagRepo.getAll();
//        Assertions.assertEquals(expected, all);
//
//    }
//
//
//    @Test
//    void testPut() throws DaoException {
//
//        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
//        StringBuilder sb = new StringBuilder();
//        Random random = new Random();
//        int length = 7;
//        for (int i = 0; i < length; i++) {
//            int index = random.nextInt(alphabet.length());
//            char randomChar = alphabet.charAt(index);
//            sb.append(randomChar);
//        }
//
//        String randomString = sb.toString();
//        boolean b = tagRepo.existByName(randomString);
//        Assertions.assertFalse(b);
//
//
//    }
//
//
//    @Test
//    void testGetByNameError() {
//        try {
//            String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
//            StringBuilder sb = new StringBuilder();
//            Random random = new Random();
//            int length = 7;
//            for (int i = 0; i < length; i++) {
//                int index = random.nextInt(alphabet.length());
//                char randomChar = alphabet.charAt(index);
//                sb.append(randomChar);
//            }
//
//            String randomString = sb.toString();
//            tagRepo.getByName(randomString);
//        } catch (Exception e) {
//            Assertions.assertEquals("Incorrect result size: expected 1, actual 0", e.getMessage());
//        }
//
//
//    }
//
//}
//
//
