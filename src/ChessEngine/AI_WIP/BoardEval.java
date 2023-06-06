package ChessEngine.AI_WIP;

import ChessEngine.board.Board;

public interface BoardEval {

    int evaluate(Board board, int depth);

}
