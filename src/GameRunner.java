import ChessEngine.board.Board;
import ChessEngine.gui.Table;

public class GameRunner {
    public static void main(String[] args) {
        Board board = Board.createStandardBoard();
        System.out.println(board);
        Table table = new Table();
    }
}
