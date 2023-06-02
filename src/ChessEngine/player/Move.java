package ChessEngine.player;

import ChessEngine.board.Board;
import ChessEngine.piece.Pawn;
import ChessEngine.piece.Piece;
import ChessEngine.piece.Rook;

public abstract class Move {
    final Board board;
    final Piece movePiece;
    final int destinationCoord;
    public static final Move NULL_MOVE = new CastleMove.NullMove();

    public Move(final Board board,
                final Piece piece,
                final int destinationCoord){
        this.board = board;
        this.movePiece = piece;
        this.destinationCoord = destinationCoord;
    }

    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        result = prime + this.movePiece.getPiecePosition();
        result = prime * result + this.movePiece.hashCode();
        return result;
    }

    @Override
    public boolean equals(final Object other){
        if(this == other){
            return true;
        }
        if(!(other instanceof Move)){
            return false;
        }
        final Move otherMove = (Move) other;
        return getDestinationCoord() == otherMove.getDestinationCoord() &&
                getMovePiece().equals(otherMove.getMovePiece());
    }

    public int getCurrentCoord(){
        return this.getMovePiece().getPiecePosition();
    }

    public int getDestinationCoord(){
        return destinationCoord;
    }

    public Piece getMovePiece(){
        return this.movePiece;
    }

    public boolean isAttack(){
        return false;
    }

    public boolean isCastled(){
        return false;
    }

    public Piece getAttackedPiece(){
        return null;
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
        builder.setPiece(this.movePiece.movePiece(this));
        // sets whoever makes the move to the person whose turn it is next
        builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
        return builder.build();
    }

    public static final class MajorMove extends Move{
        public MajorMove(final Board board,
                         final Piece piece,
                         final int destinationCoord) {
            super(board, piece, destinationCoord);
        }

    }

    public static class AttackMove extends Move{
        public final Piece attackedPiece;
        public AttackMove(final Board board,
                          final Piece piece,
                          final int destinationCoord, final Piece attackedPiece) {
            super(board, piece, destinationCoord);
            this.attackedPiece = attackedPiece;
        }

        @Override
        public boolean equals(final Object other){
            if(this == other){
                return true;
            }
            if(!(other instanceof AttackMove)){
                return false;
            }
            final AttackMove otherAttackMove = (AttackMove) other;
            return super.equals(otherAttackMove) && getAttackedPiece().equals(otherAttackMove.getAttackedPiece());
        }
        public Board execute(){
            return null;
        }
        @Override
        public boolean isAttack(){
            return true;
        }
        public Piece getAttackedPiece(){
            return this.attackedPiece;
        }

    }

    public static final class PawnMove extends Move{
        public PawnMove(final Board board, final Piece piece, final int destinationCoord) {
            super(board, piece, destinationCoord);
        }

    }

    public static class PawnAttackMove extends AttackMove{
        public PawnAttackMove(Board board, Piece piece, int destinationCoord, Piece attackedPiece) {
            super(board, piece, destinationCoord, attackedPiece);
        }
    }

    public static class PawnEnPassant extends PawnAttackMove{

        public PawnEnPassant(Board board, Piece piece, int destinationCoord, Piece attackedPiece) {
            super(board, piece, destinationCoord, attackedPiece);
        }
    }

    public static final class PawnJump extends Move{
        public PawnJump(Board board, Piece piece, int destinationCoord) {
            super(board, piece, destinationCoord);
        }

        @Override
        public Board execute(){
            final Board.Builder builder = new Board.Builder();
            for(final Piece piece : this.board.currentPlayer().getActivePieces()){
                if(!this.movePiece.equals(piece)){
                    builder.setPiece(piece);
                }
            }
            for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()){
                builder.setPiece(piece);
            }
            final Pawn movedPawn = (Pawn) this.movePiece.movePiece(this);
            builder.setPiece(movedPawn);
            builder.setEnPassantPawn(movedPawn);
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
            return builder.build();
        }
    }

    public static abstract class CastleMove extends Move{
        protected final Rook castleRook;
        protected final int castleRookStart;
        protected final int castleRookDestination;
        public CastleMove(final Board board,
                         final Piece piece,
                         final int destinationCoord,
                         final Rook castleRook,
                         final int castleRookStart,
                         final int castleRookDestination) {
            super(board, piece, destinationCoord);
            this.castleRook = castleRook;
            this.castleRookStart = castleRookStart;
            this.castleRookDestination = castleRookDestination;
        }

        public Rook getCastleRook(){
            return this.castleRook;
        }

        @Override
        public boolean isCastled(){
            return true;
        }

        @Override
        public Board execute() {
            final Board.Builder builder = new Board.Builder();
            for (final Piece piece : this.board.currentPlayer().getActivePieces()) {
                if (!this.movePiece.equals(piece) && !this.castleRook.equals(piece)) {
                    builder.setPiece(piece);
                }
            }
            for (final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
                builder.setPiece(piece);
            }
            builder.setPiece(this.movePiece.movePiece(this));
            builder.setPiece(new Rook(this.castleRookDestination, this.castleRook.getPiecedAlliance()));
            // todo look into first move boolean on normal pieces
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
            return builder.build();
        }
    }

    public static class KingsideCastleMove extends CastleMove{
        public KingsideCastleMove(final Board board,
                          final Piece piece,
                          final int destinationCoord,
                          final Rook castleRook,
                          final int castleRookStart,
                          final int castleRookDestination) {
            super(board, piece, destinationCoord, castleRook, castleRookStart, castleRookDestination);
        }

        public String toString(){
           return "O-O";
        }
    }
    public static class QueensideCastleMove extends CastleMove{
        public QueensideCastleMove(final Board board,
                          final Piece piece,
                          final int destinationCoord,
                          final Rook castleRook,
                          final int castleRookStart,
                          final int castleRookDestination) {
            super(board, piece, destinationCoord, castleRook, castleRookStart, castleRookDestination);
        }

        public String toString(){
            return "O-O-O";
        }
    }

    public static final class NullMove extends Move{
        public NullMove() {
            super(null, null, -1);
        }

        @Override
        public Board execute(){
            throw new RuntimeException("Can't execute null move");
        }
    }

    public static class MoveFactory{
        private MoveFactory(){
            throw new RuntimeException("Not instantiable");
        }

        public static Move createMove(final Board board, final int currentCoord, final int destinationCoord){
            for(final Move move: board.getAllLegalMoves()){
                if(move.getCurrentCoord() == currentCoord &&
                move.getDestinationCoord() == destinationCoord){
                    return move;
                }
            }
            return NULL_MOVE;
        }
    }
}
