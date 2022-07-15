package me.minemis.studentasigmentsapp.service;

import lombok.SneakyThrows;
import me.minemis.studentasigmentsapp.domain.Role;
import me.minemis.studentasigmentsapp.domain.User;
import me.minemis.studentasigmentsapp.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> userOpt = this.userRepository.findByUsername(username);


//        User user = new User();
//        user.setUserName(username);
//        user.setPassword(this.passwordEncoder.encode("Ryba1"));
//        user.setId(1L);
//        user.setRole(Role.ROLE_STUDENT);
        return userOpt.orElseThrow(() -> new UsernameNotFoundException("This bad boy doesn't exist bro"));
    }
}
