package com.dkswjdals89.krakensearch.dto.searchHistory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecentlySearchHistoryRequestDto {
    @Builder.Default
    @Min(1)
    private Integer page = 1;

    @Builder.Default
    @Max(value = 100, message = "page size max {1}")
    private Integer size = 10;
}
