package ChessEngine.PGN_FENfiles;

import ChessEngine.board.Board;
import ChessEngine.board.Move;
import ChessEngine.player.Player;

public interface PGNpersist {

    void persistGame(Game game);

    Move getNextBestMove(Board board, Player player, String gameText);

}
