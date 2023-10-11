package com.example.movinx.service;

import com.example.movinx.model.Url;
import com.example.movinx.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.movinx.repository.UrlRepository.*;

@Service
public class UrlShortenerService {

    @Value("${baseUrl}")
    private String baseUrl;

    public String encodeToShortenerUrl(String longUrl) {
        Optional<Url> existUrl = getByLongUrl(longUrl);
        if (existUrl.isPresent()) {
            return existUrl.get().getShortenerUrl();
        }

        Long id = getId().getAndIncrement();
        String uniqueID = IDConverter.INSTANCE.createUniqueID(id);
        var shortenedURL = new StringBuilder();
        shortenedURL.append(baseUrl).append(uniqueID);
        var url = new Url(uniqueID, longUrl, shortenedURL.toString());
        addUrlShortener(url);
        return shortenedURL.toString();
    }

    public String decodeToOriginalUrl(String shortenerUrl) throws Exception {
        return UrlRepository.getByShortenerUrl(shortenerUrl)
                .orElseThrow(() -> new Exception("No URL found!"))
                .getLongUrl();
    }
}
