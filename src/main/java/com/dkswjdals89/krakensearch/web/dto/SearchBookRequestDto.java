package com.dkswjdals89.krakensearch.web.dto;

import jdk.internal.jline.internal.Nullable;
import lombok.Builder;
import lombok.Data;

@Data
public class SearchBookRequestDto {
    private String keyword;
    @Nullable
    private Integer page;
    @Nullable
    private Integer size;

    @Builder
    public SearchBookRequestDto(String keyword, int page, int size) {
        this.keyword = keyword;
        this.page = page;
        this.size = size;
    }
}
