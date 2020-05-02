package ru.omsu.imit.exception;

public enum ErrorCode {
    SUCCESS("", ""),
    NULL_REQUEST("json", "Null request"),
    JSON_PARSE_EXCEPTION("json", "Json parse exception :  %s"),
    WRONG_URL("url", "Wrong url"),
    METHOD_NOT_ALLOWED("url", "Method not allowed"),
    DATABASE_ERROR("database", "Database error"),
    CONTACT_NOT_FOUND("database", "Item not found %s"),
    SECTION_NOT_FOUND("database", "Section not found %s"),
    INVALID_PARAMS("json", "Invalid params");

    private String field;
    private String message;

    private ErrorCode(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
