package com.dkswjdals89.booksearch.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NaverSearchBookResponseDto {
    private List<Items> items;

    private int display;

    private int start;

    private int total;

    private String lastbuilddate;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Items {
        private String description;

        private String isbn;

        private String pubdate;

        private String publisher;

        private String discount;

        private String price;

        private String author;

        private String image;

        private String link;

        private String title;
    }
}
