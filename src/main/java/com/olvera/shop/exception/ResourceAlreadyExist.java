package com.olvera.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ResourceAlreadyExist extends RuntimeException  {

    public ResourceAlreadyExist(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s already exists the given input data %s: '%s'", resourceName, fieldName, fieldValue));
    }

}
