package ChessEngine.board;

import ChessEngine.piece.Piece;

public abstract class Move {
    final Board board;
    final Piece movePiece;
    final int destinationCoord;

    public Move(final Board board,
                final Piece piece,
                final int destinationCoord){
        this.board = board;
        this.movePiece = piece;
        this.destinationCoord = destinationCoord;
    }

    public static final class MajorMove extends Move{
        public MajorMove(final Board board,
                         final Piece piece,
                         final int destinationCoord) {
            super(board, piece, destinationCoord);
        }
    }

    public static final class AttackMove extends Move{
        public final Piece attackedPiece;
        public AttackMove(final Board board,
                          final Piece piece,
                          final int destinationCoord, final Piece attackedPiece) {
            super(board, piece, destinationCoord);
            this.attackedPiece = attackedPiece;
        }
    }
}
