package com.dkswjdals89.booksearch.web;

import com.dkswjdals89.booksearch.service.search.SearchService;
import com.dkswjdals89.booksearch.web.dto.SearchBookRequestDto;
import com.dkswjdals89.booksearch.web.dto.SearchBookResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SearchBookController {
    private final SearchService searchService;

    @GetMapping("/book")
    public SearchBookResponseDto searchBook(SearchBookRequestDto requestDto, Pageable pageable) {
        return searchService.searchBook(requestDto);
    }
}
