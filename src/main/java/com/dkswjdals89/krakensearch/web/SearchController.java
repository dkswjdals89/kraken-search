package com.dkswjdals89.krakensearch.web;

import com.dkswjdals89.krakensearch.dto.BasePagingListResponseDto;
import com.dkswjdals89.krakensearch.dto.BookDto;
import com.dkswjdals89.krakensearch.dto.search.SearchRequestDto;
import com.dkswjdals89.krakensearch.service.search.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 검색 API
 */
@RequiredArgsConstructor
@RequestMapping("/v1/search")
@RestController
public class SearchController {
    private final SearchService searchService;

    @GetMapping("/book")
    public BasePagingListResponseDto<BookDto> searchBook(@Valid SearchRequestDto requestDto) {
        return searchService.searchBook(requestDto);
    }
}
