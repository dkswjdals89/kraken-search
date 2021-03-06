package com.dkswjdals89.krakensearch.dto.search;

import com.dkswjdals89.krakensearch.dto.BasePagingListResponseDto;
import com.dkswjdals89.krakensearch.utils.PagingUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Builder
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
    @Builder
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
            final String htmlTagRemoveRegEx = "<[^>]*>";
            return BookDto.builder()
                    .title(this.title != null ? this.title.replaceAll(htmlTagRemoveRegEx, "") : null)
                    .content(this.description != null ? this.description.replaceAll(htmlTagRemoveRegEx, "") : null)
                    .url(this.link)
                    .thumbnail(this.image)
                    .salePrice(this.discount)
                    .publisher(this.publisher)
                    .price(this.price)
                    .isbn(this.isbn)
                    .datetime(this.pubdate)
                    .authors(this.author != null ? Arrays.stream(this.author.split("|")).collect(Collectors.toList()) : null)
                    .build();
        }
    }

    public BasePagingListResponseDto<BookDto> convertSearchBookResponse(SearchRequestDto requestDto) {
        return BasePagingListResponseDto.<BookDto>builder()
                .items(this.items.stream()
                        .map(Items::convertBookDto)
                        .collect(Collectors.toList()))
                .totalPage(PagingUtils.calculatorTotalPageCount(this.total, requestDto.getSize()))
                .page(requestDto.getPage())
                .size(requestDto.getSize())
                .count(this.display)
                .totalCount(this.total)
                .build();
    }
}
