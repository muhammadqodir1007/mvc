import org.example.dao.repo.tagRepo.TagRepo;
import org.example.entity.Tag;
import org.example.exceptions.DaoException;
import org.example.service.TagService;
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
    TagRepo tagRepo;


    @InjectMocks
    TagService tagService;


    private static final Tag tag1 = new Tag(1, "first");
    private static final Tag tag2 = new Tag(2, "second");
    private static final Tag tag3 = new Tag(3, "third");
    private static final Tag tag4 = new Tag(4, "fourth");


    @Test
    void testGetById() throws DaoException {

        when(tagRepo.getById(tag1.getId())).thenReturn(tag1);

        assertEquals(tag1, tagService.getOne(tag1.getId()));

    }


    @Test
    void testGetAll() throws DaoException {

        when(tagRepo.getAll()).thenReturn(Arrays.asList(tag1, tag2, tag3, tag4));

        assertEquals(Arrays.asList(tag1, tag2, tag3, tag4), tagService.getAll());

    }


}
