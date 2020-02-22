package com.dkswjdals89.krakensearch.utils;

public class PagingUtils {
    public static int calculatorTotalPageCount(Integer totalRecordSize, Integer pageSize) {
        return (int)Math.ceil((float)totalRecordSize / (float)pageSize);
    }
}
