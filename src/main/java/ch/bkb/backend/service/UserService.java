package ch.bkb.backend.service;

import ch.bkb.backend.entity.User;
import ch.bkb.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public boolean checkPassword(String plainPassword, String hashedPassword) {
        // Compare the provided plaintext password with the hashed password
        return passwordEncoder.matches(plainPassword, hashedPassword);
    }
}
