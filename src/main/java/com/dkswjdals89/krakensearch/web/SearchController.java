package com.dkswjdals89.krakensearch.web;

import com.dkswjdals89.krakensearch.service.search.SearchService;
import com.dkswjdals89.krakensearch.web.dto.search.SearchBookRequestDto;
import com.dkswjdals89.krakensearch.web.dto.search.SearchBookResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Open API 기반 검색 API
 */
@RequiredArgsConstructor
@RequestMapping("/search")
@RestController
public class SearchController {
    private final SearchService searchService;

    @GetMapping("/book")
    public SearchBookResponseDto searchBook(@Valid SearchBookRequestDto requestDto) {
        return searchService.searchBook(requestDto);
    }
}
