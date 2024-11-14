package rattatoulie.demo.repository;

import rattatoulie.demo.models.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameRepository extends MongoRepository<Game, String> {
}
