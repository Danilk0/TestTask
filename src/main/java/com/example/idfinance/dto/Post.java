package com.example.idfinance.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Post {
    private Long id;
    private String symbol;
    private BigDecimal price_usd;

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", symbol='" + symbol + '\'' +
                ", price=" + price_usd +
                '}';
    }
}
