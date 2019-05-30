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
    private String turn;
    private List<Move> moves;
    private String winner;

    public Game(List<String> players, int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.players = players;
        this.state = GameState.IN_PROGRESS;
        this.winner = null;
        this.moves = new ArrayList<>();
        this.turn = players.get(0);
    }
    public void makeMove(Move move) {
        moves.add(move);
//        return move;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
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
    public String getTurn() {
        return turn;
    }

    public void setTurn(String turn) {
        this.turn = turn;
    }
}
