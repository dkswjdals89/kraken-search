package com.dkswjdals89.krakensearch.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class SearchBookResponseDto {
    private List<BookDto> items;
    private int page;
    private int size;
    private int totalCount;

    @Builder
    public SearchBookResponseDto(List<BookDto> items, int page, int size, int totalCount) {
        this.items = items;
        this.page = page;
        this.size = size;
        this.totalCount = totalCount;
    }
}
