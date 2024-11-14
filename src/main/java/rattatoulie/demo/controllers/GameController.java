package rattatoulie.demo.controllers;

import rattatoulie.demo.models.Game;
import rattatoulie.demo.repository.GameRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
@CrossOrigin(origins="*")
public class GameController {

    private final GameRepository gameRepository;

    public GameController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    // Retrieve all games
    @GetMapping
    public List<Game> getAllGames() {
        System.out.println("aaaaaaaaaaaa");
        return gameRepository.findAll();
    }

    // Register a new game
    @PostMapping("/register")
    public ResponseEntity<Game> registerGame(@RequestBody Game game) {
        Game savedGame = gameRepository.save(game);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGame);
    }

    // Retrieve a game by ID
    @GetMapping("/{id}")
    public ResponseEntity<Game> getGameById(@PathVariable String id) {
        return gameRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
