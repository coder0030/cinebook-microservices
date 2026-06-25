package com.movie.movie_service.Redis;

import org.springframework.stereotype.Service;

@Service
public class CacheKeys {
    public static final String MOVIE = "movie:";
    public static final String THEATRE = "theatre:";
    public static final String SCREEN = "screen:";
    public static final String SHOW = "show:";
    public static final String SEAT = "seat:";
    public static final String SHOW_SEAT = "showSeat:";
}
