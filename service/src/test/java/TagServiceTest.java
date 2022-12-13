import com.epam.dao.repo.tagRepo.impl.TagRepoImpl;
import com.epam.exceptions.DaoException;
import com.epam.service.impl.TagServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {

    @Mock
    TagRepoImpl tagRepo;


    @InjectMocks
    TagServiceImpl tagService;


    @Test
    void testGetById() throws DaoException {

        when(tagRepo.getById(Tags.tag1.getId())).thenReturn(Tags.tag1);

        assertEquals(Tags.tag1, tagService.getOne(Tags.tag1.getId()));

    }


    @Test
    void testGetAll() throws DaoException {

        when(tagRepo.getAll()).thenReturn(Arrays.asList(Tags.tag1, Tags.tag2, Tags.tag3, Tags.tag4));

        assertEquals(Arrays.asList(Tags.tag1, Tags.tag2, Tags.tag3, Tags.tag4), tagService.getAll());

    }


}
