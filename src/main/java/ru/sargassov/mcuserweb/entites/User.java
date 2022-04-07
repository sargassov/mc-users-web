package ru.sargassov.mcuserweb.entites;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sargassov.mcuserweb.dto.UserDto;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Collection;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User { //Основная сущности проекта

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "birth_date")
    private String birthDate; //дата рождения и в базе и в Дто сохраняется как String

    @Column(name = "username")
    private String username;

    @Column(name = "password") //пароль сохраняется к виде закриптовонного хэша
    private String password;

    @Column(name = "info")
    private String info;

    @Column(name = "address")
    private String address;

    @ManyToMany
    @JoinTable(name = "users_roles", //список ролей пользователя
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    public User(UserDto userDto) {
        if(userDto.getId() != null) id = userDto.getId();
        if(userDto.getLogin() != null) username = userDto.getLogin();
        if(userDto.getPassword() != null) password = userDto.getPassword();
        if(userDto.getName() != null) name = userDto.getName();
        if(userDto.getBirthDate() != null) birthDate = userDto.getBirthDate().substring(0, 10);
        if(userDto.getLastname() != null) lastname = userDto.getLastname();
        if(userDto.getInfo() != null) info = userDto.getInfo();
        if(userDto.getAddress() != null) address = userDto.getAddress();
        if(userDto.getRoles() != null) roles = userDto.getRoles();
    }
}
