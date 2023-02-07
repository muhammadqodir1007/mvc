//package com.epam.controller;
//
//import com.epam.entity.Tag;
//import com.epam.exceptions.DaoException;
//import com.epam.exceptions.IncorrectParameterException;
//import com.epam.response.ApiResponse;
//import com.epam.service.TagService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.Collections;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
//public class TagControllerTest {
//
//    @Mock
//    TagService tagService;
//
//    @InjectMocks
//    TagController tagController;
//
//    private MockMvc mockMvc;
//
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(tagController).build();
//    }
//
//
//    @Test
//    void shouldReturnGift() throws DaoException, IncorrectParameterException {
//        when(tagService.getOne(anyInt())).thenReturn(new Tag(1, "name"));
//
//        try {
//            mockMvc.perform(MockMvcRequestBuilders.get("/api/tag/9")
//
//
//                    ).andExpect(status().isOk())
//                    .andExpect(jsonPath("$.id").value(1))
//                    .andExpect(jsonPath("$.name").value("name"))
//                    .andExpect(status().isOk());
//
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//
//    }
//
//    @Test
//    void shouldGetAll() throws Exception {
//        when(tagService.getAll()).thenReturn(Collections.singletonList(new Tag(1, "tag")));
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/tag"))
//                .andExpect(jsonPath("$[0].id").value(1))
//                .andExpect(jsonPath("$[0].name").value("tag"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void shouldReturn400() throws Exception {
//
//        when(tagService.insert(any(Tag.class))).thenThrow(new DaoException());
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/gift")
//                        .content(asJsonString(new Tag("new Tag"))))
//                .andExpect(status().isNotFound());
//
//    }
//
//    @Test
//    void shouldReturnDeleted() throws Exception {
//
//        when(tagService.delete(anyInt())).thenReturn(new ApiResponse("deleted", true)
//        );
//
//        mockMvc.perform(MockMvcRequestBuilders.delete("/api/tag/{id}", 6)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)).andDo(print())
//                .andExpect(jsonPath("$.success").value(true))
//                .andExpect(jsonPath("$.message").value("deleted"))
//
//                .andExpect(status().isAccepted());
//    }
//    static String asJsonString(final Object obj) {
//        try {
//            return new ObjectMapper().writeValueAsString(obj);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//
//}
