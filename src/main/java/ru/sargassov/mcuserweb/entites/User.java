package ru.sargassov.mcuserweb.entites;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sargassov.mcuserweb.dto.UserDto;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "birth_date")
    private String birthDate;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "info")
    private String info;

    @Column(name = "address")
    private String address;

    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    public User(UserDto userDto) {
        if(userDto.getId() != null)
            id = userDto.getId();
        name = userDto.getName();
        lastname = userDto.getLastname();
        birthDate = userDto.getBirthDate();
        username = userDto.getLogin();
        password = userDto.getPassword();
        info = userDto.getInfo();
        address = userDto.getAddress();
        roles = userDto.getRoles();
    }
}
