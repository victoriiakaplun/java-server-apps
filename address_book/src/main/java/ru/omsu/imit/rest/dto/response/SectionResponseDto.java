package ru.omsu.imit.rest.dto.response;

import java.util.Objects;

public class SectionResponseDto {
    private int id;
    private String title;

    public SectionResponseDto(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SectionResponseDto)) return false;
        SectionResponseDto that = (SectionResponseDto) o;
        return getId() == that.getId() &&
                Objects.equals(getTitle(), that.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle());
    }

    @Override
    public String toString() {
        return "SectionResponse{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
