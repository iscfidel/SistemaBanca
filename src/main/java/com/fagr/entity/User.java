package com.fagr.entity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
    private String name;
    private String email;
    private String address;
    private String phoneNumber;
    private String password;
    private String rol;

    private String[] columns = {
            "name",
            "email",
            "address",
            "phoneNumber",
            "password",
            "rol"
    };

    private String[] filtrarColumnas = {
            // "name",
            // "email",
            // "address",
            // "phoneNumber",
            // "password",
            "rol"
    };

    private String[] operadores = {
            // "=",
            // "=",
            // "=",
            // "=",
            // "=",
            "="
    };

    private String[] filtrarValores = {
            // "name",
            // "email",
            // "address",
            // "phoneNumber",
            // "password",
            "admin"
    };

    // Constructors
    public User() {
        // Default constructor
    }

    public User(String name, String email, String address, String phoneNumer, String password, String rol) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumer;
        this.password = password;
        this.rol = rol;
    }

    // Methods
    // Encriptación SHA-256 (puedes usar BCrypt para mayor seguridad)
    private String encriptarPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al encriptar contraseña", e);
        }
    }

    // Verifica si la contraseña coincide
    public boolean validarPassword(String password) {
        return this.password.equals(encriptarPassword(password));
    }

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

    public String[] getColumns() {
        return columns.clone();
    }

    public String[] getFiltrarColumnas() {
        return filtrarColumnas.clone();
    }

    public String[] getOperadores() {
        return operadores.clone();
    }

    public String[] getFiltrarValores() {
        return filtrarValores.clone();
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
