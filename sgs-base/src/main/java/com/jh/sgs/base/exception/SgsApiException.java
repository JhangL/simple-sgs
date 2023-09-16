package com.jh.sgs.base.exception;

public class SgsApiException extends RuntimeException{

    public static SgsApiException FFWSX =new SgsApiException("方法未实现");
    public SgsApiException(String message) {
        super(message);
    }
}
