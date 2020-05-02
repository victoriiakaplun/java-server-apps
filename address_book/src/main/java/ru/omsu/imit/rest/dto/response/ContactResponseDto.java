package ru.omsu.imit.rest.dto.response;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class ContactResponseDto {
    private int id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String birthdate;
    private String phone;
    private String email;
    private String postcode;
    private String country;
    private String city;
    private String street;
    private String house;
    private String block;
    private String flat;
    List<SectionResponseDto> sections;

    public ContactResponseDto(int id, String firstName, String lastName, String patronymic,
                              String birthdate, String phone, String email, String postcode,
                              String country, String city, String street, String house, String block,
                              String flat, List<SectionResponseDto> sectionResponseList) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.birthdate = birthdate;
        this.phone = phone;
        this.email = email;
        this.postcode = postcode;
        this.country = country;
        this.city = city;
        this.street = street;
        this.house = house;
        this.block = block;
        this.flat = flat;
        this.sections = sectionResponseList;
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

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
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

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public List<SectionResponseDto> getSections() {
        return sections;
    }

    public void setSections(List<SectionResponseDto> sections) {
        this.sections = sections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContactResponseDto)) return false;
        ContactResponseDto that = (ContactResponseDto) o;
        return getId() == that.getId() &&
                Objects.equals(getFirstName(), that.getFirstName()) &&
                Objects.equals(getLastName(), that.getLastName()) &&
                Objects.equals(getPatronymic(), that.getPatronymic()) &&
                Objects.equals(getBirthdate(), that.getBirthdate()) &&
                Objects.equals(getPhone(), that.getPhone()) &&
                Objects.equals(getEmail(), that.getEmail()) &&
                Objects.equals(getPostcode(), that.getPostcode()) &&
                Objects.equals(getCountry(), that.getCountry()) &&
                Objects.equals(getCity(), that.getCity()) &&
                Objects.equals(getStreet(), that.getStreet()) &&
                Objects.equals(getHouse(), that.getHouse()) &&
                Objects.equals(getBlock(), that.getBlock()) &&
                Objects.equals(getFlat(), that.getFlat()) &&
                Objects.equals(getSections(), that.getSections());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getPatronymic(), getBirthdate(), getPhone(), getEmail(), getPostcode(), getCountry(), getCity(), getStreet(), getHouse(), getBlock(), getFlat(), getSections());
    }

    @Override
    public String toString() {
        return "ContactResponse{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", birthdate=" + birthdate +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", postcode='" + postcode + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", house='" + house + '\'' +
                ", block='" + block + '\'' +
                ", flat='" + flat + '\'' +
                ", sections=" + sections +
                '}';
    }
}
