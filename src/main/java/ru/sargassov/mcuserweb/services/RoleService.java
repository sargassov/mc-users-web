package ru.sargassov.mcuserweb.services;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sargassov.mcuserweb.entites.Role;
import ru.sargassov.mcuserweb.repositories.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository; //класс, взаимодействующий с репозиторием ролей

    public Role getRoleById(long id) {
        return roleRepository.getById(id);
    }
}
