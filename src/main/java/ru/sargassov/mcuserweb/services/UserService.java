package ru.sargassov.mcuserweb.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sargassov.mcuserweb.dto.UserDto;
import ru.sargassov.mcuserweb.entites.Role;
import ru.sargassov.mcuserweb.entites.User;
import ru.sargassov.mcuserweb.exceptions.UserNotFoundException;
import ru.sargassov.mcuserweb.repositories.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public List<User> getAllusers() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public User update(UserDto userDto) {
        User user = userRepository.findById(userDto.getId()).orElseThrow(() ->
                new UserNotFoundException("Невозможно обновить пользователя, не надйен в базе, id: " + userDto.getId()));
        user.setName(userDto.getName());
        user.setLastname(userDto.getLastname());
        user.setBirthDate(userDto.getBirthDate());
        user.setUsername(userDto.getLogin());
        user.setPassword(userDto.getPassword());
        user.setInfo(userDto.getInfo());
        user.setAddress(userDto.getAddress());
        return user;
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
