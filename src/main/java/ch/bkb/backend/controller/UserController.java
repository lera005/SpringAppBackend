package ch.bkb.backend.controller;

import ch.bkb.backend.entity.User;
import ch.bkb.backend.repository.UserRepository;
import ch.bkb.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/all")
    public Iterable<User> getUsers() {

        return userRepository.findAll();
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @Autowired
    private UserService userService;

    @PostMapping("/new")
    public void addUser(@RequestBody User user) {
        try {
            userService.saveUser(user);
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
            throw e; // Re-throw the exception to let it propagate to the client for now
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByName(user.getName()));

        if (optionalUser.isPresent() && userService.checkPassword(user.getPassword(), optionalUser.get().getPassword())) {

            return ResponseEntity.ok("Login successful");

        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }


}
