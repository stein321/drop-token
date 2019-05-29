package b.stein.droptoken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.lang.annotation.Repeatable;
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
    public ResponseEntity getOneGame(@PathVariable("gameId") String gameId) {
        Game game;
        game = repository.findGameById(gameId);
        if (game == null) {
            return new ResponseEntity("Game Not Found", HttpStatus.NOT_FOUND);
        }
        Map response = new HashMap();
        response.put("players", game.getPlayers());
        response.put("state", game.getState());
        if (game.getWinner().isPresent())
            response.put("winner", game.getWinner());
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/drop_token/{gameId}/{playerId}", method = RequestMethod.POST)
    public ResponseEntity postAMove(@PathVariable("gameId") String gameId, @PathVariable("playerId") String playerId, @RequestBody Map request) {
        Game game = repository.findGameById(gameId);
        if (game == null || !game.getPlayers().contains(playerId))
            return new ResponseEntity(" Game not found or player is not a part of it", HttpStatus.NOT_FOUND);
        int column;
        try {
            column = (int) request.get("column");
        } catch (Exception e) {
            return new ResponseEntity("Malformed input", HttpStatus.BAD_REQUEST);
        }
        if (column < 0 || column > game.getColumns())
            return new ResponseEntity("Illegal move", HttpStatus.BAD_REQUEST);
        if (!playerId.equals(game.getTurn()))
            return new ResponseEntity("Player tried to post when it’s not their turn", HttpStatus.CONFLICT);
        //make move
        //TODO: add token to column
        //add to move List
        game.makeMove(new Move(MoveType.MOVE,playerId,column));
        //see if that wins
        //TODO:run algorithm on array
        game.setTurn(game.getPlayers().get(0).equals(playerId) ? game.getPlayers().get(1) : game.getPlayers().get(0));
        repository.save(game);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/drop_token/{gameId}/moves", method = RequestMethod.GET)
    public Map<String, List> getListOfMoves(@PathVariable("gameId") String id) {
        HashMap moves = new HashMap();
        Game game = repository.findGameById(id);
        if (game != null) {
            if (game.getMoves().size() > 0)
                moves.put("moves", game.getMoves());
            else
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Moves not found"
                );
        } else
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Game not found"
            );
        return moves;
    }

    @RequestMapping(value = "/drop_token", method = RequestMethod.POST)
    public Map<String, String> createGame(@Valid @RequestBody Game game) {
        //TODO: validate data
        Game newGame = repository.insert(game);
        HashMap<String, String> map = new HashMap<>();
        map.put("gameId", newGame.getId());
        return map;
    }

}
