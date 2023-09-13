package fr.sauvageboris;

import fr.sauvageboris.repository.UserRepository;
import fr.sauvageboris.repository.entity.Role;
import fr.sauvageboris.repository.entity.RoleEnum;
import fr.sauvageboris.repository.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@SpringBootApplication
@RequiredArgsConstructor
public class DemoApplication {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            if (userRepository.findByUsername("boris").isEmpty()) {
                User user = User
                        .builder()
                        .username("sauvageb")
                        .firstName("Boris")
                        .lastName("Sauvage")
                        .password(passwordEncoder.encode("qwerty"))
                        .roleList(Arrays.asList(new Role(RoleEnum.ROLE_USER)))
                        .build();
                userRepository.save(user);
            }
        };
    }

}
