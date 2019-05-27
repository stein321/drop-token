package b.stein.droptoken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class TokenGameController {

    @Autowired
    private GameRepository repository;

    @RequestMapping(value = "/drop_token/", method = RequestMethod.GET)
    public Map getGames() {
        HashMap<String, List<String>> map = new HashMap<>();
        List<String> gameIds = repository.findAll().stream().map(Game::getId).collect(Collectors.toList());
        map.put("games", gameIds);
        return map;
    }

    @RequestMapping(value = "/drop_token/{gameId}", method = RequestMethod.GET)
    public Game getOneGame(@PathVariable("gameId") String id) {
        Game game;
        try {
            game = repository.findGameById(id);
        } catch (Exception exception) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Malformed request"
            );
        }

        if (game == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Game/moves not found"
            );
        }
        return game;
    }

    @RequestMapping(value = "/drop_token", method = RequestMethod.POST)
    public Map<String, String> createGame(@RequestBody Game game) {
        //TODO: validate data
        Game newGame = repository.insert(game);
        HashMap<String, String> map = new HashMap<>();
        map.put("gameId", newGame.getId());
        return map;
    }

}
