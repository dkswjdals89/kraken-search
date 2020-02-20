package com.dkswjdals89.booksearch.web.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class BookDto {
    private String title;
    private String content;
    private String url;
    private String isbn;
    private LocalDateTime datetime;
    private List<String> authors;
    private String publisher;
    private List<String> translators;
    private int price;
    private int salePrice;
    private String thumbnail;
    private String status;

    @Builder
    public BookDto(String title, String content, String url, String isbn, LocalDateTime datetime,
                   List<String> authors, String publisher, List<String> translators,
                   int price, int salePrice, String thumbnail, String status) {
        this.title = title;
        this.content = content;
        this.url = url;
        this.isbn = isbn;
        this.datetime = datetime;
        this.authors = authors;
        this.publisher = publisher;
        this.translators = translators;
        this.price = price;
        this.salePrice = salePrice;
        this.thumbnail = thumbnail;
        this.status = status;
    }
}
