package rattatoulie.demo.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import rattatoulie.demo.models.UserModel;
import rattatoulie.demo.repository.UserRepository;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins="*")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PutMapping("/{id}/carrinho/add")
    public ResponseEntity<UserModel> addToCarrinho(@PathVariable String id, @RequestBody String jogoId) {
        String idWithoutQuotes = id.replace("\"", "");
        String jogoIdWithoutQuotes = jogoId.replace("\"", "");
        UserModel user = userRepository.findById(idWithoutQuotes).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        user.addToCarrinho(jogoIdWithoutQuotes);
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}/carrinho/remove")
    public ResponseEntity<UserModel> removeFromCarrinho(@PathVariable String id, @RequestBody String jogoId) {
        UserModel user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        user.removeFromCarrinho(jogoId);
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}/carrinho/finalizar")
    public ResponseEntity<UserModel> finalizarCompra(@PathVariable String id) {
        String idWithoutQuotes = id.replace("\"", "");
        UserModel user = userRepository.findById(idWithoutQuotes).orElse(null);
        System.out.println(idWithoutQuotes);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        user.finalizarCompra();
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<UserModel>> getAllUsers() {
        List<UserModel> users = userRepository.findAll()    ;
        return ResponseEntity.ok(users);
    }
}