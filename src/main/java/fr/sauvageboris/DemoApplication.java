package fr.sauvageboris.demo;

import fr.sauvageboris.demo.repository.UserRepository;
import fr.sauvageboris.demo.repository.entity.Role;
import fr.sauvageboris.demo.repository.entity.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@SpringBootApplication
public class DemoApplication {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public DemoApplication(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            if (userRepository.findByUsername("boris").isEmpty()) {
                User user = new User("boris", passwordEncoder.encode("boris"));
                user.setRoleList(Arrays.asList(new Role("USER")));
                userRepository.save(user);
            }
        };
    }

}
