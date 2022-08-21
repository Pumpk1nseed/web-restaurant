package by.gaponenko.restaurant.bean;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class RegistrationUserData {
    private String login;
    private char[] password;
    private String name;
    private String surname;
    private String lastName;
    private String dateOfBirth;
    private String telephoneNumber;
    private String email;
    private String address;
    private String role;
    private Integer id;

    public RegistrationUserData(){}

    public RegistrationUserData(String login, char[] password, String name, String surname, String lastName, String dateOfBirth, String telephoneNumber, String email, String address, String role) {
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegistrationUserData)) return false;
        RegistrationUserData that = (RegistrationUserData) o;
        return Objects.equals(getLogin(), that.getLogin())
                && Arrays.equals(getPassword(), that.getPassword())
                && Objects.equals(getName(), that.getName())
                && Objects.equals(getSurname(), that.getSurname())
                && Objects.equals(getLastName(), that.getLastName())
                && Objects.equals(getDateOfBirth(), that.getDateOfBirth())
                && Objects.equals(getTelephoneNumber(), that.getTelephoneNumber())
                && Objects.equals(getEmail(), that.getEmail())
                && Objects.equals(getAddress(), that.getAddress())
                && Objects.equals(getRole(), that.getRole())
                && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getLogin(), getName(), getSurname(), getLastName(), getDateOfBirth(), getTelephoneNumber(), getEmail(), getAddress(), getRole(), getId());
        result = 31 * result + Arrays.hashCode(getPassword());
        return result;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "login='" + login + '\'' +
                ", password=" + Arrays.toString(password) +
                ", firstName='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", telephoneNumber='" + telephoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", role=" + role +
                ", id=" + id +
                '}';
    }
}
