package com.dkswjdals89.krakensearch.dto.search;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SearchBookRequestDto {
    @NotEmpty(message = "keyword is required data field")
    private String keyword;

    @Builder.Default
    @Min(1)
    private Integer page = 1;

    @Builder.Default
    @Max(100)
    private Integer size = 10;
}
