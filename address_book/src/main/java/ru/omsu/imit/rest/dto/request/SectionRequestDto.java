package ru.omsu.imit.rest.dto.request;

public class SectionRequestDto {
    private String title;

    public SectionRequestDto(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
