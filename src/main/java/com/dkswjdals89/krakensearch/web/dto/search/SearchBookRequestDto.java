package com.dkswjdals89.krakensearch.web.dto.search;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class SearchBookRequestDto {
    @NotEmpty(message = "Required data")
    private String keyword;

    @Min(1)
    private int page = 1;

    @Max(100)
    private int size = 10;

    @Builder
    public SearchBookRequestDto(String keyword, int page, int size) {
        this.keyword = keyword;
        this.page = page;
        this.size = size;
    }
}
