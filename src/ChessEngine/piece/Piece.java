package ChessEngine.piece;

import ChessEngine.Alliance;
import ChessEngine.board.Board;
import ChessEngine.board.Move;

import java.util.Collection;
import java.util.List;

public abstract class Piece {
    protected final PieceType pieceType;
    protected final int piecePosition;
    protected final Alliance piecedAlliance;
    protected final boolean isFirstMove;
    // Alliance used for pieces is going to be helpful for players as well
    public Piece(final PieceType pieceType, final int piecePos, final Alliance pieceAll){
        this.piecedAlliance = pieceAll;
        this.piecePosition = piecePos;
        // TODO more work!!
        this.isFirstMove = false;
        this.pieceType = pieceType;
    }

    public PieceType getPieceType(){
        return this.pieceType;
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

    public enum PieceType{
        PAWN("P") {
            public boolean isKing() {
                return false;
            }
        },
        KNIGHT("N") {
            public boolean isKing() {
                return false;
            }
        },
        BISHOP("B") {
            public boolean isKing() {
                return false;
            }
        },
        ROOK("R") {
            public boolean isKing() {
                return false;
            }
        },
        KING("K") {
            public boolean isKing() {
                return true;
            }
        },
        QUEEN("Q") {
            public boolean isKing() {
                return false;
            }
        };

        private String pieceName;
        PieceType(final String pieceName){
            this.pieceName = pieceName;
        }

        @Override
        public String toString(){
            return this.pieceName;
        }

        public abstract boolean isKing();
    }
}

