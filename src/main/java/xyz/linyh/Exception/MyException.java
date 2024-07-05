package xyz.linyh.Exception;

public class MyException extends RuntimeException{

    static final long serialVersionUID = -703480745766939L;

    public MyException(String message) {
        super(message);
    }
}
