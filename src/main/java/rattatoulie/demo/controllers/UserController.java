package rattatoulie.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
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

    // public String cadastrarUsuario(){
    //     return "Usuário cadastro sucesso!";
    // }
    
    @PostMapping("/add-user")
    public String cadastrarUsuario(@RequestBody UserModel user){
        userRepository.save(user);
        return "User added successfully";
    }
}