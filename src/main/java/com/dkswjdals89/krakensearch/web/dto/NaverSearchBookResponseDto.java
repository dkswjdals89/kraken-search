package com.dkswjdals89.krakensearch.web.dto;

import com.dkswjdals89.krakensearch.web.dto.search.SearchBookResponseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

        @DateTimeFormat(pattern = "yyyyMMdd")
        @JsonFormat(pattern = "yyyyMMdd")
        private LocalDate pubdate;

        private String publisher;

        private int discount;

        private int price;

        private String author;

        private String image;

        private String link;

        private String title;

        public BookDto convertBookDto() {
            return BookDto.builder()
                    .title(this.title)
                    .url(this.link)
                    .thumbnail(this.image)
                    .salePrice(this.discount)
                    .publisher(this.publisher)
                    .price(this.price)
                    .isbn(this.isbn)
                    .datetime(this.pubdate)
                    .content(this.description)
                    .authors(Arrays.stream(this.author.split("|")).collect(Collectors.toList()))
                    .build();
        }
    }

    public SearchBookResponseDto convertSearchBookResponse() {
        return SearchBookResponseDto.builder()
                .items(this.items.stream().map(documents -> documents.convertBookDto()).collect(Collectors.toList()))
                .page(0)
                .totalCount(this.total)
                .build();
    }
}
