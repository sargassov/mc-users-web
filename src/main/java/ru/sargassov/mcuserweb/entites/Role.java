package ru.sargassov.mcuserweb.entites;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "roles")
public class Role {//Сущность Роль. Уровень возможной свободы в рамках приложения. Список ролей юзера маппится к основной сущности User
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;
}
