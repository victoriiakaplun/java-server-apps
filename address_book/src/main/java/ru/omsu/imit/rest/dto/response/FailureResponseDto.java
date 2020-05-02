package ru.omsu.imit.rest.dto.response;

import ru.omsu.imit.exception.ErrorCode;

import java.util.Objects;

public class FailureResponseDto {

    private ErrorCode errorCode;
    private String message;

    public FailureResponseDto(ErrorCode errorCode, String message) {
        super();
        this.errorCode = errorCode;
        this.message = message;
    }

    public FailureResponseDto(ErrorCode errorCode) {
        this(errorCode, "");
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }


    public String getMessage() {
        return message;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FailureResponseDto)) return false;
        FailureResponseDto that = (FailureResponseDto) o;
        return getErrorCode() == that.getErrorCode() &&
                Objects.equals(getMessage(), that.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getErrorCode(), getMessage());
    }

    @Override
    public String toString() {
        return "FailureResponse{" +
                "errorCode=" + errorCode +
                ", message='" + message + '\'' +
                '}';
    }
}

