package rattatoulie.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import rattatoulie.demo.models.UserModel;
import rattatoulie.demo.repository.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins="*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserModel user) {
        System.out.println("Received username: " + user.getUsername());

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("Failure", "User already exists"));
        }

        // commit sprint 1 - hash da senha antes de salvar
        String hashedPassword = hashPassword(user.getPassword());
        user.setPassword(hashedPassword);

        userRepository.save(user);
        return ResponseEntity.ok(Map.of("Success", "User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserModel user) {
        System.out.println("Received username: " + user.getUsername());

        UserModel existingUser = userRepository.findByUsername(user.getUsername()).orElse(null);
        if (existingUser == null) {
            return ResponseEntity.badRequest().body(Map.of("Failure", "Invalid credentials"));
        }

        //commit sprint 1 - hash da senha fornecida para comparação
        String providedPasswordHash = hashPassword(user.getPassword());

        //commit sprint 1 - comparação de hashes
        if (!existingUser.getPassword().equals(providedPasswordHash)) {
            return ResponseEntity.badRequest().body(Map.of("Failure", "Invalid credentials"));
        }

        Map<String, Object> response = new HashMap<>();
        String ID = existingUser.getId();
        String Carrinho = existingUser.getCarrinho().toString();
        String Jogos = existingUser.getJogos().toString();
        response.put("userId", ID);
        response.put("userCarrinho", Carrinho);
        response.put("userJogos", Jogos);
        System.out.println(Jogos);
        return ResponseEntity.ok(response);
    }

//commit sprint1 ph - implementação do calculo do hash da senha
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null; // Trate a exceção apropriadamente
        }
    }

    // commit ph - converter bytes em hexadecimal
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}