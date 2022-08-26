package by.gaponenko.restaurant.bean;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class RegistrationUserData {
    private Integer id_user;
    private String login;
    private String password;
    private String name;
    private String surname;
    private String lastName;
    private String dateOfBirth;
    private String telephoneNumber;
    private String email;
    private String address;
    private String role;

    public RegistrationUserData(){}

    public RegistrationUserData(Integer id_user, String login, String password, String name, String surname, String lastName, String dateOfBirth, String telephoneNumber, String email, String address, String role) {
        this.id_user = id_user;
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.telephoneNumber = telephoneNumber;
        this.email = email;
        this.address = address;
        this.role = role;
    }

    public RegistrationUserData(String login, String password, String name, String surname, String lastName, String dateOfBirth, String telephoneNumber, String email, String address, String role) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.telephoneNumber = telephoneNumber;
        this.email = email;
        this.address = address;
        this.role = role;
    }

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String firstName) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegistrationUserData)) return false;
        RegistrationUserData that = (RegistrationUserData) o;
        return getId_user().equals(that.getId_user())
                && getLogin().equals(that.getLogin())
                && getPassword().equals(that.getPassword())
                && getName().equals(that.getName())
                && getSurname().equals(that.getSurname())
                && Objects.equals(getLastName(), that.getLastName())
                && Objects.equals(getDateOfBirth(), that.getDateOfBirth())
                && Objects.equals(getTelephoneNumber(), that.getTelephoneNumber())
                && Objects.equals(getEmail(), that.getEmail())
                && Objects.equals(getAddress(), that.getAddress())
                && Objects.equals(getRole(), that.getRole());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId_user(), getLogin(), getPassword(), getName(), getSurname(),
                getLastName(), getDateOfBirth(), getTelephoneNumber(), getEmail(), getAddress(), getRole());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "id_user='" + id_user + '\'' +
                ", login='" + login + '\'' +
                ", password=" + password +
                ", firstName='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", telephoneNumber='" + telephoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", role=" + role +
                '}';
    }
}
