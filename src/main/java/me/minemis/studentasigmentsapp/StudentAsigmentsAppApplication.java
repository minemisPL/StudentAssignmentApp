package me.minemis.studentasigmentsapp;

import me.minemis.studentasigmentsapp.repository.UserRepository;
import me.minemis.studentasigmentsapp.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class StudentAsigmentsAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentAsigmentsAppApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new UserDetailsService(this.userRepository);
//    }

}
