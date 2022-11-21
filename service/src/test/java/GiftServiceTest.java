import org.example.dao.config.PostgreSqlConfig;
import org.example.dao.config.PostgreSqlConfigForTest;
import org.example.entity.GiftCertificate;
import org.example.entity.Tag;
import org.example.service.FieldNotFoundException;
import org.example.service.GiftService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;



import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = PostgreSqlConfigForTest.class)
public class GiftServiceTest {

    @Autowired
    private GiftService giftService;

    @Test
    public void getGift() throws ClassNotFoundException {

        GiftCertificate byId = giftService.findById(2);
        assertEquals("dff", byId.getName());
        System.out.println(byId.getName());
    }

    @Test
    public void getAssociatedTagsTest() throws ClassNotFoundException {
        List<Tag> associatedTag = giftService.list(2);

        assertEquals(Arrays.asList(new Tag(3, "jh"), new Tag(5, "nhjh"), new Tag(13, "new tag")), associatedTag);

    }

}
