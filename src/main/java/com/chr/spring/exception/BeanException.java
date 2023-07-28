package com.chr.spring.exception;

public class BeanException extends RuntimeException{
    private String message;

    public BeanException(){super();}

    public BeanException(String message){
        super(message);
        this.message = message;
    }

    public BeanException(String message,Throwable e){
        super(message,e);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
