package com.example.movinx;

import com.example.movinx.dto.UrlDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.example.movinx.service.UrlShortenerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MovinxApplication.class)
@AutoConfigureMockMvc
class UrlShortenerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UrlShortenerService urlShortenerService;

    @Test
    void testEncode() throws Exception {
        String originalUrl = "http://example.com";
        String shortUrl = "http://short.example.com/abc123";

        Mockito.when(urlShortenerService.encodeToShortenerUrl(originalUrl))
                .thenReturn(shortUrl);

        UrlDto urlDto = new UrlDto();
        urlDto.setLongUrl(originalUrl);

        mockMvc.perform(MockMvcRequestBuilders.post("/encode")
                .content(asJsonString(urlDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(shortUrl));
    }

    @Test
    void testDecode() throws Exception {
        String shortUrl = "http://short.example.com/abc123";
        String originalUrl = "http://example.com";

        Mockito.when(urlShortenerService.decodeToOriginalUrl(shortUrl))
                .thenReturn(originalUrl);

        mockMvc.perform(MockMvcRequestBuilders.get("/decode")
                .param("shortUrl", shortUrl)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(originalUrl));
    }
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
