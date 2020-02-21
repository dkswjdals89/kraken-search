package com.dkswjdals89.krakensearch.web.dto;

import com.dkswjdals89.krakensearch.component.PagingUtils;
import com.dkswjdals89.krakensearch.web.dto.search.SearchBookRequestDto;
import com.dkswjdals89.krakensearch.web.dto.search.SearchBookResponseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class KakaoSearchBookResponseDto {
    @JsonProperty("meta")
    private Meta meta;
    @JsonProperty("documents")
    private List<Documents> documents;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Documents {
        private String url;

        private List<String> translators;

        private String title;

        private String thumbnail;

        private String status;

        @JsonProperty("sale_price")
        private int salePrice;

        private String publisher;

        private int price;

        private String isbn;

        @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME)
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        private LocalDate datetime;

        private String contents;

        private List<String> authors;

        public BookDto convertBookDto() {
            return BookDto.builder()
                    .title(this.title)
                    .url(this.url)
                    .translators(this.translators)
                    .thumbnail(this.thumbnail)
                    .salePrice(this.salePrice)
                    .publisher(this.publisher)
                    .price(this.price)
                    .isbn(this.isbn)
//                    .datetime(LocalDateTime.parse(this.datetime))
                    .content(this.contents)
                    .authors(this.authors)
                    .build();
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Meta {
        @JsonProperty("total_count")
        private int totalCount;
        @JsonProperty("pageable_count")
        private int pageableCount;
        @JsonProperty("is_end")
        private boolean isEnd;
    }

    public SearchBookResponseDto convertSearchBookResponse(SearchBookRequestDto requestDto) {
        return SearchBookResponseDto.builder()
                .items(this.documents.stream()
                        .map(Documents::convertBookDto)
                        .collect(Collectors.toList()))
                .totalPage(PagingUtils.calculatorTotalPageCount(this.meta.totalCount, requestDto.getSize()))
                .totalCount(this.meta.totalCount)
                .page(requestDto.getPage())
                .size(requestDto.getSize())
                .count(documents.size())
                .build();
    }
}
