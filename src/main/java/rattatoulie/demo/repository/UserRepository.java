package rattatoulie.demo.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import rattatoulie.demo.models.UserModel;

public interface UserRepository extends MongoRepository<UserModel, String>{
    Optional<UserModel> findByUsername(String username);
}