import org.example.dao.repo.giftRepo.GiftRepo;
import org.example.entity.GiftCertificate;
import org.example.entity.Tag;
import org.example.exceptions.DaoException;
import org.example.exceptions.IncorrectParameterException;
import org.example.service.GiftService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class GiftServiceTest {

    @Mock
    GiftRepo giftRepo;


    @InjectMocks
    private GiftService giftService;

    private static final Tag tag2 = new Tag(1, "tag2");


    private static final GiftCertificate giftCertificate1 = new GiftCertificate(1, "name1", "description1", 56.9, 23, new Date(1669126392015L).toString(), new Date(1669126392015L).toString());
    private static final GiftCertificate giftCertificate2 = new GiftCertificate(2, "name2", "description2", 75, 33, new Date(1669126392015L).toString(), new Date(1669126392015L).toString(), List.of(tag2));
    private static final GiftCertificate giftCertificate3 = new GiftCertificate(3, "name3", "description3", 56, 67, new Date(1669126392015L).toString(), new Date(1669126392015L).toString());
    private static final GiftCertificate giftCertificate4 = new GiftCertificate(6, "name4", "description4", 75, 33, new Date(1669126392015L).toString(), new Date(1669126392015L).toString());


    @Test
    public void testGetById() throws DaoException {
        when(giftRepo.getById(giftCertificate1.getId())).thenReturn(giftCertificate1);
        GiftCertificate actual = giftService.findById(giftCertificate1.getId());
        assertEquals(giftCertificate1, actual);

    }

    @Test
    public void testGetAll() throws DaoException {
        List<GiftCertificate> giftCertificates = Arrays.asList(giftCertificate4, giftCertificate3, giftCertificate2, giftCertificate1);
        when(giftRepo.list()).thenReturn(giftCertificates);
        assertEquals(giftCertificates, giftService.getAll());

    }

    @Test
    public void testGetAssociatedTags() throws ClassNotFoundException, DaoException, IncorrectParameterException {

        when(giftRepo.getAssociatedTags(giftCertificate2.getId())).thenReturn(Collections.singletonList(tag2));

        assertEquals(Collections.singletonList(tag2), giftService.list(giftCertificate2.getId()));

    }


}
