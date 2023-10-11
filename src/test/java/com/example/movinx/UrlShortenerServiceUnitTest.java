
package com.example.movinx;

import com.example.movinx.model.Url;
import com.example.movinx.repository.UrlRepository;
import com.example.movinx.service.UrlShortenerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UrlShortenerServiceUnitTest {

    @InjectMocks
    private UrlShortenerService service;

    @Test
    void testEncodeToShortenerUrl() {
        Url testUrl = new Url("uniqueID", "longUrl", "shortenedURL");
        try (MockedStatic<UrlRepository> utilities = Mockito.mockStatic(UrlRepository.class)) {
            utilities.when(() -> UrlRepository.getByLongUrl("longUrl"))
                    .thenReturn(Optional.of(testUrl));
            String result = service.encodeToShortenerUrl("longUrl");
            assertEquals("shortenedURL", result);
        }
    }
    @Test
    void testDecodeToOriginalUrl() throws Exception {
        Url testUrl = new Url("uniqueID", "longUrl", "shortenedURL");
        try (MockedStatic<UrlRepository> utilities = Mockito.mockStatic(UrlRepository.class)) {
            utilities.when(() -> UrlRepository.getByShortenerUrl("shortenedURL"))
                    .thenReturn(Optional.of(testUrl));
            String result = service.decodeToOriginalUrl("shortenedURL");
            assertEquals("longUrl", result);
        }
    }

}
