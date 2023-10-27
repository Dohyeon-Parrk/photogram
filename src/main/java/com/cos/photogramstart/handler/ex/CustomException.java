package com.cos.photogramstart.handler.ex;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    public CustomException(String message) {
        super(message);
    }
}
