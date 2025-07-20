package com.fagr;

public class User {
    private String name;
    private String email;
    private String address;
    private String phoneNumber;
    private String password;
    private String rol;

    // Constructors
    public User() {
        // Default constructor
    }
    public User(String name, String email, String address, String phoneNumer, String password, String rol){
        this.name = name;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumer;
        this.password = password;
        this.rol = rol;
    }

    //Methods
    

    // Getters
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getAddress() {
        return address;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getPassword() {
        return password;
    }
    public String getRol() {
        return rol;
    }

    // Setters

    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }
}
