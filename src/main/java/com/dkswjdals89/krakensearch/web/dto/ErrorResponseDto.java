package com.dkswjdals89.krakensearch.web.dto;

import com.dkswjdals89.krakensearch.config.jackson.StatusCodeSerialize;
import com.dkswjdals89.krakensearch.constant.ServiceError;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErrorResponseDto {
    @JsonSerialize(using = StatusCodeSerialize.class)
    private ServiceError code;
    private String message;
}
