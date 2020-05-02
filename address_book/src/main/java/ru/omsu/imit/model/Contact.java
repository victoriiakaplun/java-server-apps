package ru.omsu.imit.model;

import java.time.LocalDate;
import java.util.*;

public class Contact {
    private int id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private LocalDate birthdate;
    private String phone;
    private String email;
    private Address address;
    private List<Section> sections;

    public Contact(){}

    public Contact(String firstName, String lastName, String patronymic, LocalDate birthdate, String phone, String email) {
        this(0, firstName, lastName, patronymic, birthdate, phone, email, null, new ArrayList<>());
    }

    public Contact(int id, String firstName, String lastName, String patronymic,
                   LocalDate birthdate, String phone, String email, Address address, List<Section> sections) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.birthdate = birthdate;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.sections = sections;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contact)) return false;
        Contact contact = (Contact) o;
        return getId() == contact.getId() &&
                Objects.equals(getFirstName(), contact.getFirstName()) &&
                Objects.equals(getLastName(), contact.getLastName()) &&
                Objects.equals(getPatronymic(), contact.getPatronymic()) &&
                Objects.equals(getBirthdate(), contact.getBirthdate()) &&
                Objects.equals(getPhone(), contact.getPhone()) &&
                Objects.equals(getEmail(), contact.getEmail()) &&
                Objects.equals(getAddress(), contact.getAddress()) &&
                Objects.equals(getSections(), contact.getSections());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getPatronymic(), getBirthdate(), getPhone(), getEmail(), getAddress(), getSections());
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", birthdate=" + birthdate +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
               // ", address=" + address +
               // ", sections=" + sections +
                '}';
    }
}
