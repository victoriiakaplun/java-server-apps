package ru.omsu.imit.exception;

public class AddressBookException extends Exception {
    private static final long serialVersionUID = 6049904777923589329L;
    private ErrorCode errorCode;
    private String param;

    public AddressBookException(ErrorCode errorCode) {
        this(errorCode, null);
    }

    public AddressBookException(ErrorCode errorCode, String param) {
        this.errorCode = errorCode;
        this.param = param;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
