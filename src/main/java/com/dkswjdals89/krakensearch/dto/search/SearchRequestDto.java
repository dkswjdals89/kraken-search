package com.dkswjdals89.krakensearch.dto.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SearchRequestDto {
    @NotEmpty(message = "keyword is required data field")
    private String keyword;

    @Builder.Default
    @Min(1)
    private Integer page = 1;

    @Builder.Default
    @Max(value = 100, message = "page size max {1}")
    private Integer size = 10;
}
