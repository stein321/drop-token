package b.stein.droptoken;

import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

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

    private int[][] board;
    public static int WIN_LENGTH = 4;

    public Game(List<String> players, int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.players = players;
        this.state = GameState.IN_PROGRESS;
        this.winner = null;
        this.moves = new ArrayList<>();
        this.turn = players.get(0);
        this.board = new int[rows][columns];
    }

    public void makeMove(Move move) throws Exception {
        moves.add(move);
        boolean validMove = false;
        int i;
        for (i = 0; i < getRows(); i++) { // see if move is valid and get index
            if (board[i][move.getColumn()] == 0) {
                validMove = true;
                board[i][move.getColumn()] = getPlayers().indexOf(move.getPlayer()) + 1; // set move on board
                break;
            }
        }
        if (!validMove)
            throw new Exception("Invalid move");
        System.out.println(Arrays.deepToString(board));
        if (seeIfWinner(i, move)) {
            winner = move.getPlayer();
            state = GameState.Done;
        } else {
            setTurn(getPlayers().get(0).equals(move.getPlayer()) ? getPlayers().get(1) : getPlayers().get(0));
        }
    }

    private boolean seeIfWinner(int row, Move move) {

        if (checkListIfWinner(board[row - 1], getPlayers().indexOf(move.getPlayer())))
            return true;
        int[] columnArray = IntStream.range(0, row - 1).map(i -> board[i][move.getColumn()]).toArray();
        if (checkListIfWinner(columnArray, getPlayers().indexOf(move.getPlayer())))
            return true;

        //get left right diagnal
        //get right left diagnal


        return false;
    }

    private boolean checkListIfWinner(int[] moves, int index) {
        int count = 0;
        for (int move : moves) {
            if (move == index + 1) {
                count++;
                if (count == WIN_LENGTH)
                    return true;
            } else count = 0;
        }
        return false;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
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
