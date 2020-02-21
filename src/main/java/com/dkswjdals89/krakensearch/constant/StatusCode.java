package com.dkswjdals89.krakensearch.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusCode {
    SUCCESS(200, "Success"),
    REQUEST_VALIDATE_ERROR(400, "Request Data Validate Fail");

    private Integer code;
    private String message;
}
