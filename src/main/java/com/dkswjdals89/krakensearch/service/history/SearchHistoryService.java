package com.dkswjdals89.krakensearch.service.history;

import com.dkswjdals89.krakensearch.component.ContextHolderComponent;
import com.dkswjdals89.krakensearch.domain.account.Account;
import com.dkswjdals89.krakensearch.domain.history.SearchHistory;
import com.dkswjdals89.krakensearch.domain.history.SearchHistoryRepository;
import com.dkswjdals89.krakensearch.dto.BasePagingListResponseDto;
import com.dkswjdals89.krakensearch.dto.account.AccountDetailDto;
import com.dkswjdals89.krakensearch.dto.history.CreateSearchHistoryRequestDto;
import com.dkswjdals89.krakensearch.dto.history.RecentlySearchHistoryRequestDto;
import com.dkswjdals89.krakensearch.dto.history.SearchHistoryDetailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class SearchHistoryService {
    private final SearchHistoryRepository searchHistoryRepository;
    private final ContextHolderComponent ContextHolderComponent;

    /**
     * 검색 히스토리 생성
     *
     * @param requestDto
     * @return 생성된 검색 히스토리 데이터 반환
     */
    @Transactional()
    public SearchHistoryDetailDto createSearchHistory(CreateSearchHistoryRequestDto requestDto) {
        AccountDetailDto account = ContextHolderComponent.getCurrentAccountDetail();
        log.debug("Request Create Search History - request:" + requestDto + ", userId:" + account.getUserId());

        SearchHistory createdHistory = searchHistoryRepository.save(SearchHistory.builder()
                .searchKeyword(requestDto.getKeyword())
                .searchType(requestDto.getSearchType())
                .account(Account.builder().id(account.getId()).build())
                .build());

        log.debug("Created Search History - " + createdHistory);
        return new SearchHistoryDetailDto(createdHistory);
    }

    /**
     * 사용자 검색 히스토리 리스트 조회
     * - 최근 검색 데이터 순으로 정렬하여 반환한다.
     *
     * @param requestDto 검색 요청 Query
     * @return 기본 페이징 리스트 데이터 형식으로 반환
     */
    @Transactional(readOnly = true)
    public BasePagingListResponseDto<SearchHistoryDetailDto> findRecentlyByAccount(RecentlySearchHistoryRequestDto requestDto) {
        AccountDetailDto account = ContextHolderComponent.getCurrentAccountDetail();

        log.debug("Request Recently Search History - " + requestDto);

        Account requestAccount = Account.builder()
                .id(account.getId())
                .build();
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageRequest = PageRequest.of(requestDto.getPage() - 1, requestDto.getSize(), sort);

        Page<SearchHistory> searchHistories = searchHistoryRepository.findAllByAccount(requestAccount, pageRequest);

        log.debug("Find DB Result - " + searchHistories);

        return BasePagingListResponseDto.<SearchHistoryDetailDto>builder()
                .items(searchHistories.getContent().stream().map((SearchHistoryDetailDto::new))
                        .collect(Collectors.toList()))
                .totalCount((int) searchHistories.getTotalElements())
                .totalPage(searchHistories.getTotalPages())
                .count(searchHistories.getNumberOfElements())
                .page(requestDto.getPage())
                .size(searchHistories.getSize())
                .build();
    }
}
