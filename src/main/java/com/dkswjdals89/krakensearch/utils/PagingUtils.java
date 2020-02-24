package com.dkswjdals89.krakensearch.utils;

public class PagingUtils {
    /**
     * 총 페이지 수를 계산하여 반환한다.
     *
     * @param totalRecordSize 총 레코드 수
     * @param pageSize 페이지당 레코드 수
     * @return 총 페이지 갯수
     */
    public static int calculatorTotalPageCount(Integer totalRecordSize, Integer pageSize) {
        return (int)Math.ceil((float)totalRecordSize / (float)pageSize);
    }
}
