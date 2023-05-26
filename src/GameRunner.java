import ChessEngine.board.Board;

public class GameRunner {
    public static void main(String[] args) {
        Board board = Board.createStandardBoard();

        System.out.println(board);
    }
}
