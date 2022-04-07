package ru.sargassov.mcuserweb.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sargassov.mcuserweb.entites.User;

import java.util.Calendar;
import java.util.Collection;


public class UserDto { //адаптивный класс для передачи иноформации на фронт
    private Long id;
    private String name;
    private String lastname;
    private String birthDate;
    private String login;
    private String password;
    private String info;
    private String address;
    private Collection roles;


    public UserDto(User user) {
        id = user.getId();
        name = user.getName();
        lastname = user.getLastname();
        birthDate = user.getBirthDate();
        login = user.getUsername();
        password = user.getPassword();
        info = user.getInfo();
        address = user.getAddress();
        roles = user.getRoles();
    }

    private UserDto(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Collection getRoles() {
        return roles;
    }
}
