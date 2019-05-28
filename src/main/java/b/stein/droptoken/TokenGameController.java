package b.stein.droptoken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @RequestMapping(value = "/drop_token", method = RequestMethod.GET)
    public Map<String, List<String>> getGames() {
        HashMap<String, List<String>> map = new HashMap<>();
        List<String> gameIds = repository.findAll().stream().map(Game::getId).collect(Collectors.toList());
        map.put("games", gameIds);
        return map;
    }

    @RequestMapping(value = "/drop_token/{gameId}", method = RequestMethod.GET)
    public Map getOneGame(@PathVariable("gameId") String id) {
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
                    HttpStatus.NOT_FOUND, "Game not found"
            );
        }
        Map response = new HashMap();
        response.put("players", game.getPlayers());
        response.put("state", game.getState());
        if (game.getWinner().isPresent())
            response.put("winner", game.getWinner());
        return response;
    }

    @RequestMapping(value = "/drop_token/{gameId}/{playerId}", method = RequestMethod.POST)
    public Map<String,Integer> postAMove(@PathVariable("gameId") String gameId, @PathVariable("playerId") String playerId, @RequestBody Map request) {
        //deal with gameId
        //check playerId exists in game
        //check column
        if ( (int)request.get("column") >= 0  ) { // this throws 500 if column is a string
            Map response = new HashMap();
            response.put("column", request.get("column"));
            return  response;
        }
        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Malformed input. Illegal move"
        );
    }

    @RequestMapping(value = "/drop_token/{gameId}/moves", method = RequestMethod.GET)
    public Map<String, List> getListOfMoves(@PathVariable("gameId") String id) {
        HashMap moves = new HashMap();
        Game game = repository.findGameById(id);
        if (game != null  ) {
            if ( game.getMoves().size() > 0)
                moves.put("moves", game.getMoves());
            else
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Moves not found"
                );
        }
        else
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Game not found"
            );
        return moves;
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
