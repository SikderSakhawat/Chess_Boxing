package ChessEngine.piece;

import ChessEngine.Alliance;
import ChessEngine.board.Board;
import ChessEngine.board.Move;

import java.util.Collection;
import java.util.List;

public abstract class Piece {
    protected final int piecePosition;
    protected final Alliance piecedAlliance;
    protected final boolean isFirstMove;
    // Alliance used for pieces is going to be helpful for players as well
    public Piece(final int piecePos, final Alliance pieceAll){
        this.piecedAlliance = pieceAll;
        this.piecePosition = piecePos;
        // TODO more work!!
        this.isFirstMove = false;
    }

    public int getPiecePosition(){
        return this.piecePosition;
    }
    public boolean isFirstMove(){
        return this.isFirstMove;
    }

    public Alliance getPiecedAlliance(){
        return this.piecedAlliance;
    }

    // All our chess pieces will override this method to have their own possible moves defined
    public abstract Collection<Move> calcLegalMoves(final Board board);

}
