package com.dkswjdals89.krakensearch;

import com.dkswjdals89.krakensearch.constant.SearchType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SearchHistoryType {
    SearchType value() default SearchType.BOOK;
}
