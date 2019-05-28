package b.stein.droptoken;


import java.util.Optional;

public class Move {
    private MoveType type;
    private String player;
    private Optional<Integer> column;

    public Move(MoveType type, String player, Integer column) {
        this.type = type;
        this.player = player;
        this.column = Optional.of(column);
    }

    public Move(MoveType type, String player) {
        this.type = type;
        this.player = player;
        this.column = Optional.empty();
    }

    public MoveType getType() {
        return type;
    }

    public void setType(MoveType type) {
        this.type = type;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public Optional<Integer> getColumn() {
        return column;
    }

    public void setColumn(Optional<Integer> column) {
        this.column = column;
    }
}
