package com.dkswjdals89.krakensearch.handler;

import com.dkswjdals89.krakensearch.exception.ServiceException;
import com.dkswjdals89.krakensearch.exception.ServiceError;
import com.dkswjdals89.krakensearch.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ControllerAdvice
public class ExceptionErrorHandler {
    /**
     * Field Error 항목을 단일 메시지로 반환
     * @param fieldErrors   필드 오류 리스트
     * @return 단일 오류 메시지
     */
    public static String makeFieldErrorsMessage(List<FieldError> fieldErrors) {
        StringBuilder sb = new StringBuilder();

        fieldErrors.stream().forEach(fieldError -> {
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
        });

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
        String errorMsg = makeFieldErrorsMessage(e.getBindingResult().getFieldErrors());
        return new ErrorResponseDto(ServiceError.REQUEST_VALIDATE_ERROR, errorMsg);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponseDto bindExceptionHandle(MethodArgumentNotValidException e) {
        String errorMsg = makeFieldErrorsMessage(e.getBindingResult().getFieldErrors());
        return new ErrorResponseDto(ServiceError.REQUEST_VALIDATE_ERROR, errorMsg);
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponseDto> serviceExceptionHandler(ServiceException e) {
        final ErrorResponseDto response = new ErrorResponseDto(e.getServiceError(), e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.valueOf(e.getServiceError().getStatusCode()));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDto> handle(Exception e, HttpServletRequest request)
    {
        final ErrorResponseDto response = new ErrorResponseDto(ServiceError.REQUEST_VALIDATE_ERROR, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.valueOf(HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Object exceptionHandler(Exception e) {
        return null;
    }
}
