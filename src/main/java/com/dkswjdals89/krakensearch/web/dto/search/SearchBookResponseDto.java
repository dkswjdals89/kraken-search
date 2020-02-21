package com.dkswjdals89.krakensearch.web.dto.search;

import com.dkswjdals89.krakensearch.web.dto.BookDto;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class SearchBookResponseDto {
    private List<BookDto> items;
    private Integer totalPage;  //  전체 페이지 수
    private Integer page;       //  요청 페이지 번호
    private Integer size;       //  요청 조회 아이템 수
    private Integer count;      //  조회된 아이템 수
    private Integer totalCount; //  전체 아이템 수

    @Builder
    public SearchBookResponseDto(List<BookDto> items, Integer page, Integer totalPage, Integer size, Integer count, Integer totalCount) {
        this.items = items;
        this.page = page;
        this.totalPage = totalPage;
        this.size = size;
        this.count = count;
        this.totalCount = totalCount;
    }

    public void setRequestPageInfo(Integer page, Integer size) {
        this.page = page;
        this.size = size;
    }
}
