import ChessEngine.board.Board;
import ChessEngine.gui.Table;

public class GameRunner {

    public static void main(final String[] args) throws Exception {
        System.out.println(Board.createStandardBoard());
        Table.get().show();
    }
}
