package bdo_demo.user;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    private String city;
    private Integer postalCode;
    private String streetName;
    private Integer houseNumber;
    public Address() {}
    public Address(String city, Integer postalCode, String streetName, Integer houseNumber) {
        this.city = city;
        this.postalCode = postalCode;
        this.streetName = streetName;
        this.houseNumber = houseNumber;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public Integer getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(Integer postalCode) {
        this.postalCode = postalCode;
    }
    public String getStreetName() {
        return streetName;
    }
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }
    public Integer getHouseNumber() {
        return houseNumber;
    }
    public void setHouseNumber(Integer houseNumber) {
        this.houseNumber = houseNumber;
    }
}
