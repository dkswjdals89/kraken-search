package com.dkswjdals89.krakensearch.exception;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {
    private final ServiceError serviceError;

    public ServiceException(ServiceError serviceError)
    {
        this.serviceError = serviceError;
    }

    public ServiceException(ServiceError serviceError, String message)
    {
        super(message);
        this.serviceError = serviceError;
    }

    public ServiceException(ServiceError serviceError, String message, Exception e)
    {
        super(message, e);
        this.serviceError = serviceError;
    }
}
