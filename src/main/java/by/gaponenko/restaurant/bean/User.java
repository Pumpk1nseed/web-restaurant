package by.gaponenko.restaurant.bean;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    private Integer idUser;
    private String login;
    private String password;
    private int idRole;
    private String status;

    public User() {
    }

    public User(Integer idUser, String login, String password, int idRole, String status) {
        this.idUser = idUser;
        this.login = login;
        this.password = password;
        this.idRole = idRole;
        this.status = status;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
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

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
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
        return getIdRole() == user.getIdRole() && getIdUser().equals(user.getIdUser()) && getLogin().equals(user.getLogin()) && getPassword().equals(user.getPassword()) && Objects.equals(getStatus(), user.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdUser(), getLogin(), getPassword(), getIdRole(), getStatus());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "idUser=" + idUser +
                ", password='" + password + '\'' +
                ", login='" + login + '\'' +
                ", idRole='" + idRole + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
