package b.stein.droptoken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
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
        try {
            game = getGameAndValidate(gameId);
        } catch (Exception exception) {
            return new ResponseEntity("Game Not Found", HttpStatus.NOT_FOUND);
        }
        Map response = new HashMap();
        response.put("players", game.getPlayers());
        response.put("state", game.getState());
        if (game.getWinner() != null)
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

        //add to move List
        try {
            game.makeMove(new Move(MoveType.MOVE, playerId, column));
        } catch (Exception e) {
            return new ResponseEntity("Illegal move", HttpStatus.BAD_REQUEST);
        }
        repository.save(game);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/drop_token/{gameId}/moves", method = RequestMethod.GET)
    public ResponseEntity getListOfMoves(@PathVariable("gameId") String id, @PathParam("start") Integer start, @PathParam("until") Integer until) {
        List moves;
        try {
            moves = getMoves(id);
        } catch (Exception exception) {
            return new ResponseEntity("Game not found", HttpStatus.NOT_FOUND);
        }
        if( start == null ) {
            start = 0;
        }
        if( until == null) {
            until = moves.size();
        }
        if(until > moves.size() || until < start || start < 0)
            return new ResponseEntity("Malformed request", HttpStatus.BAD_REQUEST);
        return new ResponseEntity(moves.subList(start,until), HttpStatus.OK);
    }
    @RequestMapping(value = "/drop_token/{gameId}/moves/{move_number}", method = RequestMethod.GET)
    public ResponseEntity getASpecificMove(@PathVariable("gameId") String id, @PathVariable("move_number") int moveNumber) {
        if ( moveNumber == 0)
            return new ResponseEntity("Malformed input", HttpStatus.BAD_REQUEST);
        List moves;
        try {
            moves = getMoves(id);
        } catch (Exception exception) {
            return new ResponseEntity("Game not found", HttpStatus.NOT_FOUND);
        }
        if (moves.size() < moveNumber )
            return new ResponseEntity("Moves not found", HttpStatus.NOT_FOUND);
        return new ResponseEntity(moves.get(moveNumber-1), HttpStatus.OK);
    }

    @RequestMapping(value = "/drop_token", method = RequestMethod.POST)
    public Map<String, String> createGame(@Valid @RequestBody Game game) {
        //TODO: validate data
        Game newGame = repository.insert(game);
        HashMap<String, String> map = new HashMap<>();
        map.put("gameId", newGame.getId());
        return map;
    }
    @RequestMapping(value = "/drop_token/{gameId}/{playerId}", method = RequestMethod.DELETE)
    public ResponseEntity quitGame(@PathVariable("gameId") String gameId, @PathVariable("playerId") String playerId) {
        Game game;
        try {
            game = getGameAndValidate(gameId);
        } catch (Exception exception) {
            return new ResponseEntity("Game not found", HttpStatus.NOT_FOUND);
        }
        if( !game.getPlayers().contains(playerId) )
            return new ResponseEntity("Player is not a part of game", HttpStatus.NOT_FOUND);
        if( game.getState() == GameState.Done)
            return new ResponseEntity("Game is already in DONE state", HttpStatus.GONE);
        game.getMoves().add(new Move(MoveType.QUIT, playerId));
        game.getPlayers().remove(playerId);
        game.setWinner(game.getPlayers().get(0));
        game.setState(GameState.Done);
        repository.save(game);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    private List getMoves(String id) throws Exception {
        Game game = getGameAndValidate(id);
        return game.getMoves();
    }
    private Game getGameAndValidate(String id) throws Exception {
        Game game = repository.findGameById(id);
        if( game == null)
            throw new Exception("Game not found");
        else
            return game;
    }

}
