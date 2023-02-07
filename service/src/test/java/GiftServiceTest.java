//import com.epam.entity.GiftCertificate;
//import com.epam.exceptions.DaoException;
//import com.epam.exceptions.IncorrectParameterException;
//import com.epam.service.impl.GiftServiceImpl;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//
//
//@ExtendWith(MockitoExtension.class)
// class GiftServiceTest {
//
//    @Mock
//  private   GiftRepoImpl giftRepo;
//
//
//    @InjectMocks
//    private GiftServiceImpl giftService;
//
//
//    @Test
//    public void shouldReturnGiftById() throws DaoException {
//        when(giftRepo.getById(GiftCertificates.giftCertificate1.getId())).thenReturn(GiftCertificates.giftCertificate1);
//        GiftCertificate actual = giftService.findById(GiftCertificates.giftCertificate1.getId());
//        assertEquals(GiftCertificates.giftCertificate1, actual);
//
//    }
//
//    @Test
//    public void shouldReturnAll() throws DaoException {
//        List<GiftCertificate> giftCertificates = Arrays.asList(GiftCertificates.giftCertificate4, GiftCertificates.giftCertificate3, GiftCertificates.giftCertificate2, GiftCertificates.giftCertificate1);
//        when(giftRepo.list()).thenReturn(giftCertificates);
//        assertEquals(giftCertificates, giftService.getAll());
//
//    }
//
//    @Test
//    public void shouldGetAssociatedTags() throws DaoException, IncorrectParameterException {
//
//        when(giftRepo.getAssociatedTags(GiftCertificates.giftCertificate2.getId())).thenReturn(Collections.singletonList(GiftCertificates.tag2));
//
//        assertEquals(Collections.singletonList(GiftCertificates.tag2), giftService.list(GiftCertificates.giftCertificate2.getId()));
//
//    }
//
//
//}
