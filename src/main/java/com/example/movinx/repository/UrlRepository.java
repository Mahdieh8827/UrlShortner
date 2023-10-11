package com.example.movinx.repository;

import com.example.movinx.model.Url;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class UrlRepository {
    private static AtomicLong id = new AtomicLong(123456);
    private static final Set<Url> urlShortenerRepository = new HashSet<>();

    private UrlRepository() {
    }

    public static AtomicLong getId() {
        return id;
    }

    public static Optional<Url> getByShortenerUrl(String shortenerUrl) {
        return urlShortenerRepository.stream()
                .filter(url -> url.getShortenerUrl().equals(shortenerUrl))
                .findFirst();
    }

    public static Optional<Url> getByLongUrl(String longUrl) {
        return urlShortenerRepository.stream()
                .filter(url -> url.getLongUrl().equals(longUrl))
                .findFirst();
    }

    public static void addUrlShortener(Url url) {
        if (!urlShortenerRepository.contains(url)) {
            UrlRepository.urlShortenerRepository.add(url);
        }
    }
}
