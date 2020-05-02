package ru.omsu.imit.rest.dto.request;

public class IdRequestDto {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public IdRequestDto(int id) {
        this.id = id;
    }
}
