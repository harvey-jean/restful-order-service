package com.jean.ordering.shared.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Harvey's on 7/8/2023.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceAlreadyExistsException extends RuntimeException{

    public ResourceAlreadyExistsException(String exceptionMessage){
        super(exceptionMessage);
    }

}
