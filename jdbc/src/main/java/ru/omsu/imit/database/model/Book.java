package ru.omsu.imit.database.model;

import java.util.Objects;

public class Book {
    private int id;
    private String title;
    private int year;
    private int pages;
    private String publishingHouse;
    private String binding;

    public Book(int id, String title, int year, int pages, String publishingHouse, String binding) {
        super();
        this.id = id;
        this.title = title;
        this.year = year;
        this.pages = pages;
        this.publishingHouse = publishingHouse;
        this.binding = binding;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getPublishingHouse() {
        return publishingHouse;
    }

    public void setPublishingHouse(String publishingHouse) {
        this.publishingHouse = publishingHouse;
    }

    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id &&
                year == book.year &&
                pages == book.pages &&
                Objects.equals(title, book.title) &&
                Objects.equals(publishingHouse, book.publishingHouse) &&
                Objects.equals(binding, book.binding);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, year, pages, publishingHouse, binding);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", pages=" + pages +
                ", publishingHouse='" + publishingHouse + '\'' +
                ", binding='" + binding + '\'' +
                '}';
    }
}
