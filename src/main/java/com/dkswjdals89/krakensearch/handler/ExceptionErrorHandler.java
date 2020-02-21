package com.dkswjdals89.krakensearch.handler;

import com.dkswjdals89.krakensearch.constant.StatusCode;
import com.dkswjdals89.krakensearch.web.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
public class ExceptionErrorHandler {
    public static String makeFieldErrorsMessage(List<FieldError> fieldErrors) {
        StringBuilder sb = new StringBuilder();

        for (FieldError fieldError : fieldErrors)
        {
            if (sb.length() != 0)
            {
                sb.append(", ");
            }

            String message = fieldError.getDefaultMessage();

            if (message.contains("java.util.Date"))
            {
                message = "Check Date Format";
            }
            else if (message.contains("NumberFormatException"))
            {
                message = "Number Only";
            }

            sb.append(fieldError.getField());
            sb.append("(").append(message).append(")");
        }

        return sb.toString();
    }

    /**
     * Validate Error Handle
     * @param e BindException
     * @return 기본 에러 메시지 객체 반환
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponseDto bindExceptionHandle(BindException e) {
        String errorMsg = makeFieldErrorsMessage(e.getFieldErrors());
        return new ErrorResponseDto(StatusCode.REQUEST_VALIDATE_ERROR, errorMsg);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Object exceptionHandler(Exception e) {
        return null;
    }
}
