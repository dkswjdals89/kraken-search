package com.dkswjdals89.krakensearch.component;

public class PagingUtils {
    public static int calculatorTotalPageCount(Integer totalRecordSize, Integer pageSize) {
        return (int)Math.ceil((double)((float)totalRecordSize / (float)pageSize));
    }
}
