package com.jh.sgs.core.exception;

public class SgsApiException extends RuntimeException{

    public static SgsApiException FFWSX =new SgsApiException("方法未实现");
    public SgsApiException(String message) {
        super(message);
    }
}
