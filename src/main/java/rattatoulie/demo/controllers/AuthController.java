package rattatoulie.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import rattatoulie.demo.models.UserModel;
import rattatoulie.demo.repository.*;
import java.util.Map;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins="*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    //private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserModel user) {
        System.out.println("Received username: " + user.getUsername());
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("Failure","User already exists"));
        }
        //user.setPassword(user.getPassword());
        userRepository.save(user);
        return ResponseEntity.ok(Map.of("Success","User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserModel user) {
        System.out.println("Received username: " + user.getUsername());
        UserModel existingUser = userRepository.findByUsername(user.getUsername()).orElse(null);
        if (existingUser == null) {
            return ResponseEntity.badRequest().body(Map.of("Failure","Invalid credentials"));
        }
        return ResponseEntity.ok(Map.of("Success","User logged in successfully"));
    }
}
