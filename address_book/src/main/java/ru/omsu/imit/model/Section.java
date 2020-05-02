package ru.omsu.imit.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Section {
    private int id;
    private String title;
    private List<Contact> sectionContacts;

    public Section(){}

    public Section(int id, String title) {
        this(id, title, new ArrayList<>());
    }

    public Section(String title) {
        this(0, title, new ArrayList<>());
    }

    public Section(int id, String title, List<Contact> sectionContacts) {
        this.id = id;
        this.title = title;
        this.sectionContacts = sectionContacts;
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

    public List<Contact> getSectionContacts() {
        return sectionContacts;
    }

    public void setSectionContacts(List<Contact> sectionContacts) {
        this.sectionContacts = sectionContacts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Section)) return false;
        Section section = (Section) o;
        return getId() == section.getId() &&
                Objects.equals(getTitle(), section.getTitle()) &&
                Objects.equals(getSectionContacts(), section.getSectionContacts());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getSectionContacts());
    }


    @Override
    public String toString() {
        return "Section{" +
                "id=" + id +
                ", title='" + title + '\'' +
                //", sectionContacts=" + sectionContacts +
                '}';
    }
}
