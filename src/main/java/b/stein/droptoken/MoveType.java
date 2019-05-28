package b.stein.droptoken;

public enum MoveType {
    MOVE("MOVE"),
    QUIT("QUIT");

    private String moveType;

    MoveType(String moveType) {
        this.moveType = moveType;
    }

    public String getMoveType() {
        return this.moveType;
    }
}
