package ru.omsu.imit.model;

import java.util.Objects;

public class Address {
    private Contact contact;
    private String postcode;
    private String country;
    private String city;
    private String street;
    private String house;
    private String block;
    private String flat;

    public Address() {
    }


    public Address(Contact contact, String postcode, String country, String city, String street, String house,
                   String block, String flat) {
        this.contact = contact;
        this.postcode = postcode;
        this.country = country;
        this.city = city;
        this.street = street;
        this.house = house;
        this.block = block;
        this.flat = flat;
    }

    public Address(String postcode, String country, String city, String street, String house,
                   String block, String flat) {
        this(null, postcode, country,city,street,house, block, flat);
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return Objects.equals(getContact(), address.getContact()) &&
                Objects.equals(getPostcode(), address.getPostcode()) &&
                Objects.equals(getCountry(), address.getCountry()) &&
                Objects.equals(getCity(), address.getCity()) &&
                Objects.equals(getStreet(), address.getStreet()) &&
                Objects.equals(getHouse(), address.getHouse()) &&
                Objects.equals(getBlock(), address.getBlock()) &&
                Objects.equals(getFlat(), address.getFlat());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getContact(), getPostcode(), getCountry(), getCity(), getStreet(), getHouse(), getBlock(), getFlat());
    }

    @Override
    public String toString() {
        return "Address{" +
                //"contact=" + contact +
                ", postcode='" + postcode + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", house='" + house + '\'' +
                ", block='" + block + '\'' +
                ", flat='" + flat + '\'' +
                '}';
    }
}
