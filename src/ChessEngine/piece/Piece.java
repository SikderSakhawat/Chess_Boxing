package ChessEngine.piece;

import ChessEngine.Alliance;
import ChessEngine.board.Board;
import ChessEngine.player.Move;

import java.util.Collection;

public abstract class Piece {
    protected final PieceType pieceType;
    protected final int piecePosition;
    protected final Alliance piecedAlliance;
    protected final boolean isFirstMove;
    private final int cachedHashCode;
    // Alliance used for pieces is going to be helpful for players as well
    public Piece(final PieceType pieceType, final int piecePos, final Alliance pieceAll){
        this.piecedAlliance = pieceAll;
        this.piecePosition = piecePos;
        // TODO more work!!
        this.isFirstMove = false;
        this.pieceType = pieceType;
        this.cachedHashCode = computeHashCode();
    }

    private int computeHashCode() {
        int result = pieceType.hashCode();
        result = 31 * result + piecedAlliance.hashCode();
        result = 31* result + piecePosition;
        result = 31 * result + (isFirstMove ? 1 : 0);
        return result;
    }

    @Override
    public boolean equals(final Object other){
        if(this == other){
            return true;
        }
        if(!(other instanceof Piece)){
            return false;
        }
        final Piece otherPiece = (Piece) other;
        return piecePosition == otherPiece.getPiecePosition() && pieceType == otherPiece.getPieceType() &&
                piecedAlliance == otherPiece.getPiecedAlliance() && isFirstMove == otherPiece.isFirstMove();
    }

    @Override
    public int hashCode(){
        return this.cachedHashCode;
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
    public abstract Piece movePiece(Move move);

    public enum PieceType{
        PAWN("P") {
            public boolean isKing() {
                return false;
            }
            public boolean isRook(){
                return false;
            }
        },
        KNIGHT("N") {
            public boolean isKing() {
                return false;
            }
            public boolean isRook(){
                return false;
            }
        },
        BISHOP("B") {
            public boolean isKing() {
                return false;
            }
            public boolean isRook(){
                return false;
            }
        },
        ROOK("R") {
            public boolean isKing() {
                return false;
            }
            public boolean isRook(){
                return true;
            }
        },
        KING("K") {
            public boolean isKing() {
                return true;
            }
            public boolean isRook(){
                return false;
            }
        },
        QUEEN("Q") {
            public boolean isKing() {
                return false;
            }
            public boolean isRook(){
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
        public abstract boolean isRook();
    }
}

