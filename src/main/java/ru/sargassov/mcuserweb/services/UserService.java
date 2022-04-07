package ru.sargassov.mcuserweb.services;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sargassov.mcuserweb.converters.UserConverter;
import ru.sargassov.mcuserweb.dto.UserDto;
import ru.sargassov.mcuserweb.entites.Role;
import ru.sargassov.mcuserweb.entites.User;
import ru.sargassov.mcuserweb.exceptions.UserNotFoundException;
import ru.sargassov.mcuserweb.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService { //класс, взаимодействующий с репозиторием юзеров
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder encoder;

;
    public List<User> getAllusers() {
        return userRepository.findAll();
    }


    public User save(User user) { //Сохраниение нового пользователя
        Collection<Role> roles = new ArrayList<>();
        roles.add(roleService.getRoleById(1L)); //даем ему роль ROLE_USER
        user.setRoles(roles);
        user.setPassword(encoder.encode(user.getPassword())); //криптуем пароль
        return userRepository.save(user);
    }

    @Transactional
    public boolean update(UserDto userDto) { //Обновление полей юзера. С фронта приходит дто, в котором часть полей null, но обязательно есть поле логина
        User user;
        try{ //по логину узнаем есть ли такой юзер в базе, иначе бросаем исключение. Если он есть, то загружаем сущность
            user = userRepository.findByUsername(userDto.getLogin()).orElseThrow(() ->
                    new UserNotFoundException("Невозможно обновить пользователя, не надйен в базе, id: " + userDto.getId()));
        } catch (UserNotFoundException e){
            return false;
        }

        //Далее из дто достаются все поля, которые не null и копируются в сущность и обновленная сущность сохраняется
        if(userDto.getName() != null) user.setName(userDto.getName());
        if(userDto.getLastname() != null) user.setLastname(userDto.getLastname());
        if(userDto.getAddress() != null) user.setAddress(userDto.getAddress());
        if(userDto.getPassword() != null){
            user.setPassword(encoder.encode(user.getPassword()));
        }
        if(userDto.getBirthDate() != null) user.setBirthDate(userDto.getBirthDate().substring(0, 10));
        if(userDto.getInfo() != null) user.setInfo(userDto.getInfo());
        userRepository.save(user);

        return true;
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional//
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) { //подргузка ролей пользователя во время авторизации
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    //проверка на нахождение в базе по логину
    public boolean isResident(String login) {
        return userRepository.findByUsername(login).isPresent();
    }
}
