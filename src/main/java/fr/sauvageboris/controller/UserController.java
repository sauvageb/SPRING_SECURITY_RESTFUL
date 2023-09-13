package fr.sauvageboris.demo.controller;

import fr.sauvageboris.demo.controller.dto.UserDto;
import fr.sauvageboris.demo.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> fetchAll() {
        List<UserDto> users = userRepository
                .findAll()
                .stream()
                .map(u -> u.toDto()).toList();

        return ResponseEntity.ok(users);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> fetchById(@PathVariable("id") Long userId) {
        Optional<UserDto> userDto = userRepository
                .findById(userId)
                .map(user -> user.toDto());

        if (userDto.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDto.get());
    }


}
