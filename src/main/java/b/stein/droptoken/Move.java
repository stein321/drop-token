package b.stein.droptoken;


import java.util.Optional;

public class Move {
    private MoveType type;
    private String player;
    private int column;

    public Move(MoveType type, String player, Integer column) {
        this.type = type;
        this.player = player;
        this.column = column;
    }

    public Move(MoveType type, String player) {
        this.type = type;
        this.player = player;
    }

    public Move() {
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

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
