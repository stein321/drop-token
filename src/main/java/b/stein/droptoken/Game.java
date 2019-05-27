package b.stein.droptoken;

import org.springframework.data.annotation.Id;

import java.util.List;

public class Game {
    @Id
    private String id;

    private List players;
    private int rows;
    private int columns;
    private GameState state;

    public Game(List players, int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.players = players;
        this.state = GameState.IN_PROGRESS;
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
