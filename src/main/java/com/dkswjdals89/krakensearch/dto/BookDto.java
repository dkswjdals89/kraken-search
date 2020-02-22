package com.dkswjdals89.krakensearch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BookDto {
    private String title;
    private String content;
    private String url;
    private String isbn;
    private LocalDate datetime;
    private List<String> authors;
    private String publisher;
    private List<String> translators;
    private int price;
    private int salePrice;
    private String thumbnail;
    private String status;
}
