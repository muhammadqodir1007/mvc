import org.example.dao.config.PostgreSqlConfigForTest;
import org.example.entity.Tag;
import org.example.service.FieldNotFoundException;
import org.example.service.TagService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = PostgreSqlConfigForTest.class)
public class TagServiceTest {

    @Autowired
    private TagService tagService;


    @Test
    public void getOne() throws FieldNotFoundException {
        Tag one = tagService.getOne(3);
        assertEquals("jh", one.getName());

    }





}
