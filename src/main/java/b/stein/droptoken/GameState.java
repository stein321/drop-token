package b.stein.droptoken;

public enum GameState {
    Done("Done"),
    IN_PROGRESS("In Progress");

    private String state;

    GameState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

}
