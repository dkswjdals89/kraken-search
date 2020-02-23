package com.dkswjdals89.krakensearch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BasePagingListResponseDto<T> {
    private List<T> items;
    private Integer page;       //  요청 페이지 번호
    private Integer count;      //  조회된 아이템 수
    private Integer size;       //  요청 조회 아이템 수
    private Integer totalPage;  //  전체 페이지 수
    private Integer totalCount; //  전체 아이템 수

    public void setRequestPageInfo(Integer page, Integer size) {
        this.page = page;
        this.size = size;
    }

    public BasePagingListResponseDto setPageData(Page data) {
        return this;
    }
}
