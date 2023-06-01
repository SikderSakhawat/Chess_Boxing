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

    public int getDestinationCoord(){
        return destinationCoord;
    }

    public abstract Board execute();

    public static final class MajorMove extends Move{
        public MajorMove(final Board board,
                         final Piece piece,
                         final int destinationCoord) {
            super(board, piece, destinationCoord);
        }

        // helps us find a new board to return
        // traverse through all the current players pieces and if it isn't the move piece you don't change that piece
        public Board execute(){
            final Board.Builder builder = new Board.Builder();
            // for loops just check if an active piece changed locations or not from a previous board
            for(final Piece piece : this.board.currentPlayer().getActivePieces()){
                // TODO hashcode for pieces to help make the search for pieces possible
                if(!this.movePiece.equals(piece)){
                    builder.setPiece(piece);
                }
            }
            for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()){
                builder.setPiece(piece);
            }
            // moves piece based on this call
            builder.setPiece(null);
            // sets whoever makes the move to the person whose turn it is next
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
            return builder.build();
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

        public Board execute(){
            return null;
        }
    }
}
