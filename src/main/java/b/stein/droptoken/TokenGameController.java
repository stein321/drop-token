package b.stein.droptoken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class TokenGameController {

    @Autowired
    private GameRepository repository;

    @RequestMapping(value = "/drop_token", method = RequestMethod.GET)
    public Map getGames() {
        HashMap<String, List<String>> map = new HashMap<>();
        List<String> gameIds = repository.findAll().stream().map(Game::getId).collect(Collectors.toList());
        map.put("games", gameIds);
        return map;
    }

    @RequestMapping(value = "/drop_token", method = RequestMethod.POST)
    public Map<String, String> createGame(@RequestBody Game game) {
        Game newGame = repository.insert(game);
        HashMap<String, String> map = new HashMap<>();
        map.put("gameId", newGame.getId());
        return map;
    }

}
