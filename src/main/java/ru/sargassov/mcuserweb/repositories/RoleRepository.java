package ru.sargassov.mcuserweb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sargassov.mcuserweb.entites.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    //Репозиторий ролей
}
