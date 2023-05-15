package ChessEngine.piece;

import ChessEngine.Alliance;
import ChessEngine.board.Board;
import ChessEngine.board.Move;

import java.util.List;

public abstract class Piece {
    protected final int piecePosition;
    protected final Alliance piecedAlliance;
    // Alliance used for pieces is going to be helpful for players as well
    public Piece(final int piecePos, final Alliance pieceAll){
        this.piecedAlliance = pieceAll;
        this.piecePosition = piecePos;
    }

    public Alliance getPiecedAlliance(){
        return this.piecedAlliance;
    }

    public abstract List<Move> calcLegalMoves(final Board board); // All our chess pieces will override this method to have their own possible moves defined

}
