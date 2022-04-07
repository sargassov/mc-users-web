package ru.sargassov.mcuserweb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sargassov.mcuserweb.entites.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> { //Репозиторий юзеров
    Optional<User> findByUsername(String username);
}
