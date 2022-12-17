package com.epam.controller;

import com.epam.entity.GiftCertificate;
import com.epam.entity.Tag;
import com.epam.exceptions.DaoException;
import com.epam.response.ApiResponse;
import com.epam.service.GiftService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Date;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GiftControllerTest {


    @Mock
    GiftService giftService;

    @InjectMocks
    GiftController giftController;

    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(giftController).build();
    }

    @Test
    @DisplayName("should return 200 when getting all")
    void shouldReturnOk() throws DaoException {
        GiftCertificate giftCertificate3 = new GiftCertificate(3, "name3", "description3", 56, 67, new Date(1669126392015L).toString(), new Date(1669126392015L).toString(), Collections.singletonList(new Tag(0, null)));
        GiftCertificate giftCertificate4 = new GiftCertificate(6, "name4", "description4", 75, 33, new Date(1669126392015L).toString(), new Date(1669126392015L).toString(), Collections.singletonList(new Tag(0, null)));

        when(giftService.getAll()).thenReturn(Arrays.asList(giftCertificate3, giftCertificate4));

        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/gift")).andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }


    }

    @Test
    @DisplayName("should response content type is json ")
    void shouldContentTypeJson() throws Exception {
        when(giftService.findById(1)).thenReturn(new GiftCertificate());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/gift/1")).andReturn();
        assertEquals("application/json", mvcResult.getResponse().getContentType());
    }


    @Test
    @DisplayName("should return 400 when something went wrong with posting ")
    void shouldReturn400() throws Exception {
        when(giftService.addGift(any())).thenReturn(new ApiResponse("error", false));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/gift").param("name", "name").param("description", "description").param("price", "23").param("duration", "32")).andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("should return created response when new git certificate added ")
    void shouldReturnCreatedResponse() throws Exception {
        GiftCertificate gift = GiftCertificate.builder().name("name").description("description").price(23).duration(23).build();
        when(giftService.addGift(any(GiftCertificate.class))).thenReturn(new ApiResponse("created", true));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/gift")
                        .content(asJsonString(gift))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(jsonPath("$.message").value("created"))
                .andExpect(status().isCreated());


    }


    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}


