package by.gaponenko.restaurant.bean;

import java.util.Objects;

public class User {
    private Integer idUser;
    private String login;
    private String password;
    private String role;
    private String status;

    public User() {
    }

    public User(Integer idUser, String login, String password, String role) {
        this.idUser = idUser;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public User(String login, String password, String role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getIdUser().equals(user.getIdUser()) && getPassword().equals(user.getPassword()) && getLogin().equals(user.getLogin()) && getRole().equals(user.getRole()) && Objects.equals(getStatus(), user.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPassword(), getLogin(), getRole());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "idUser=" + idUser +
                ", password='" + password + '\'' +
                ", login='" + login + '\'' +
                ", role='" + role + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
