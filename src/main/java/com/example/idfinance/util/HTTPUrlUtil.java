package com.example.idfinance.util;

import com.example.idfinance.dto.Post;
import org.springframework.web.client.RestTemplate;

public class HTTPUrlUtil {
    public static Post sendGet(String param) {
        final RestTemplate restTemplate = new RestTemplate();


        final Post[] posts = restTemplate.getForObject("https://api.coinlore.net/api/ticker/?id="+param, Post[].class);
        assert posts != null;
        return posts[0];

    }

}