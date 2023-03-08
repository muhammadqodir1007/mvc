package com.epam.esm;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.TagDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = TestConfig.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class TagTest {

    @Autowired
    TagDao tagDao;


    @Test
    void tes() {

        try {
            Tag name = tagDao.insert(new Tag("namew"));

            assertEquals(name.getName(), "namew");
        }catch (Exception e){
            System.out.println(e.getMessage());
            assertEquals(e.getMessage(),"new");
        }

    }

}

