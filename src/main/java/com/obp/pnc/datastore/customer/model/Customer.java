package com.obp.pnc.datastore.customer.model;

import java.io.Serializable;

public class Customer implements Serializable {


    private Integer id;

    private String firstName;

    private String lastName;

    private String username;

    private String password;

    private String phoneNumber;

    private String addressLine1;

    private String city;

    private String state;

    private String zip;

    private String ssn;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }


    @Override
    public String toString() {
        return "Customer{" + "id='" + id + '\'' + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\''
            + ", username='" + username + '\'' + ", password='" + password + '\'' + ", phoneNumber='" + phoneNumber
            + '\'' + ", addressLine1='" + addressLine1 + '\'' + ", city='" + city + '\'' + ", state='" + state + '\''
            + ", zip='" + zip + '\'' + ", ssn='" + ssn + '\'' + '}';
    }
}
