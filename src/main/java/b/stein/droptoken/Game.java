package b.stein.droptoken;

import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Game {
    @Id
    private String id;

    @NotNull
    private List players;
    @NotNull
    private int rows;
    @NotNull
    private int columns;
    @NotNull
    private GameState state;
    private List<Move> moves;
    private Optional<String> winner;

    public Game(List players, int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.players = players;
        this.state = GameState.IN_PROGRESS;
        this.winner = Optional.empty();
        this.moves = new ArrayList<Move>();
    }
    public Move makeMove(Move move) {
        moves.add(move);
        return move;
    }

    public Optional<String> getWinner() {
        return winner;
    }

    public void setWinner(Optional<String> winner) {
        this.winner = winner;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }
}
