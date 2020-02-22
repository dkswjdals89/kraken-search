package com.dkswjdals89.krakensearch.dto;

import com.dkswjdals89.krakensearch.exception.ServiceError;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.dkswjdals89.krakensearch.exception.converter.StatusCodeSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErrorResponseDto {
    @JsonSerialize(using = StatusCodeSerialize.class)
    private ServiceError code;
    private String message;
}
