package ChessEngine.AI_WIP;

import ChessEngine.board.Board;
import ChessEngine.board.Move;

public interface MoveStrat {

    long getNumBoardsEvaluated();

    Move execute(Board board);

}
