package by.gaponenko.restaurant.bean;

import java.util.Objects;

public class User {
    private String id_user;
    private String login;
    private String password;
    private String role;
    private String status;

    public User() {
    }

    public User(String id_user, String login, String password, String role) {
        this.id_user = id_user;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public User(String login, String password, String role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
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
        return getId_user().equals(user.getId_user()) && getPassword().equals(user.getPassword()) && getLogin().equals(user.getLogin()) && getRole().equals(user.getRole()) && Objects.equals(getStatus(), user.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPassword(), getLogin(), getRole());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "id_user=" + id_user +
                ", password='" + password + '\'' +
                ", login='" + login + '\'' +
                ", role='" + role + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
