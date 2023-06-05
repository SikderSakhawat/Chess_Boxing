package ChessEngine.player;

import ChessEngine.board.Board;
import ChessEngine.board.BoardUtility;
import ChessEngine.piece.Pawn;
import ChessEngine.piece.Piece;
import ChessEngine.piece.Rook;

public abstract class Move {
    protected final Board board;
    protected final Piece movedPiece;
    protected final int destinationCoord;
    protected final boolean isFirstMove;
    public static final Move NULL_MOVE = new CastleMove.NullMove();

    public Move(final Board board,
                final Piece piece,
                final int destinationCoord){
        this.board = board;
        this.movedPiece = piece;
        this.destinationCoord = destinationCoord;
        this.isFirstMove = movedPiece.isFirstMove();
    }

    private Move(final Board board, final int destinationCoord){
        this.board = board;
        this.destinationCoord = destinationCoord;
        this.movedPiece = null;
        this.isFirstMove = false;
    }

    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        result = prime * result + this.destinationCoord;
        result = prime * result + this.movedPiece.hashCode();
        result = prime * result + this.movedPiece.getPiecePosition();
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
        return getCurrentCoord() == otherMove.getCurrentCoord() &&
                getDestinationCoord() == otherMove.getDestinationCoord() &&
                getMovedPiece().equals(otherMove.getMovedPiece());
    }

    public int getCurrentCoord(){
        return this.getMovedPiece().getPiecePosition();
    }

    public int getDestinationCoord(){
        return destinationCoord;
    }

    public Piece getMovedPiece(){
        return this.movedPiece;
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
            if(!this.movedPiece.equals(piece)){
                builder.setPiece(piece);
            }
        }
        for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()){
            builder.setPiece(piece);
        }
        // moves piece based on this call
        builder.setPiece(this.movedPiece.movePiece(this));
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

        public boolean equals(Object other){
            return this == other || other instanceof MajorMove && super.equals(other);
        }

        public String toString(){
            return movedPiece.getPieceType().toString() + BoardUtility.getPosAtCoord(this.destinationCoord);
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

        public int hashCode(){
            return this.attackedPiece.hashCode() + super.hashCode();
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
        @Override
        public boolean isAttack(){
            return true;
        }
        public Piece getAttackedPiece(){
            return this.attackedPiece;
        }
    }

    public static class MajorAttackMove extends AttackMove{
        public MajorAttackMove(final Board board, final Piece movePiece,
                               final int destinationCoord, final Piece pieceAttacked){
            super(board,movePiece,destinationCoord, pieceAttacked);
        }

        public boolean equals(final Object other){
            return this == other || other instanceof MajorAttackMove && super.equals(other);
        }

        public String toString(){
            return movedPiece.getPieceType() + BoardUtility.getPosAtCoord(this.destinationCoord);
        }
    }

    public static final class PawnMove extends Move{
        public PawnMove(final Board board, final Piece piece, final int destinationCoord) {
            super(board, piece, destinationCoord);
        }

        public boolean equals(final Object other){
            return this == other || other instanceof PawnMove && super.equals(other);
        }

        public String toString(){
            return BoardUtility.getPosAtCoord(this.destinationCoord);
        }
    }

    public static class PawnAttackMove extends AttackMove{
        public PawnAttackMove(Board board, Piece piece, int destinationCoord, Piece attackedPiece) {
            super(board, piece, destinationCoord, attackedPiece);
        }

        public boolean equals(final Object other){
            return this == other || other instanceof PawnAttackMove && super.equals(other);
        }

        public String toString(){
            return BoardUtility.getPosAtCoord(this.movedPiece.getPiecePosition()).substring(0,1) + "x" +
            BoardUtility.getPosAtCoord(this.destinationCoord);
        }
    }

    public static class PawnEnPassant extends PawnAttackMove{
        public PawnEnPassant(Board board, Piece piece, int destinationCoord, Piece attackedPiece) {
            super(board, piece, destinationCoord, attackedPiece);
        }

        public boolean equals(final Object other){
            return this == other || other instanceof PawnEnPassant && super.equals(other);
        }

        //almost like the pawn attack move, but it will be used so we can check the previous move made
        public Board execute(){
            final Board.Builder builder = new Board.Builder();
            for(final Piece piece : this.board.currentPlayer().getActivePieces()){
                if(!this.movedPiece.equals(piece)){
                    builder.setPiece(piece);
                }
            }
            for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()){
                if(!piece.equals(this.getAttackedPiece())){
                    builder.setPiece(piece);
                }
            }
            builder.setPiece(this.movedPiece.movePiece(this));
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
            return builder.build();
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
                if(!this.movedPiece.equals(piece)){
                    builder.setPiece(piece);
                }
            }
            for(final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()){
                builder.setPiece(piece);
            }
            final Pawn movedPawn = (Pawn) this.movedPiece.movePiece(this);
            builder.setPiece(movedPawn);
            builder.setEnPassantPawn(movedPawn);
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
            return builder.build();
        }

        @Override
        public String toString(){
            return BoardUtility.getPosAtCoord(this.destinationCoord);
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
                if (!this.movedPiece.equals(piece) && !this.castleRook.equals(piece)) {
                    builder.setPiece(piece);
                }
            }
            for (final Piece piece : this.board.currentPlayer().getOpponent().getActivePieces()) {
                builder.setPiece(piece);
            }
            builder.setPiece(this.movedPiece.movePiece(this));
            builder.setPiece(new Rook(this.castleRookDestination, this.castleRook.getPiecedAlliance()));
            // todo look into first move boolean on normal pieces
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
            return builder.build();
        }

        public int hashcode(){
            final int prime = 31;
            int result = super.hashCode();
            result = prime * result + this.castleRook.hashCode();
            result = prime * result + this.castleRookDestination;
            return result;
        }

        public boolean equals(final Object other){
            if(this == other) return true;
            if(!(other instanceof CastleMove)) return false;
            final CastleMove otherCastleMove = (CastleMove) other;
            return super.equals(otherCastleMove) && this.castleRook.equals(otherCastleMove.getCastleRook());
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

        public boolean equals(final Object other){
            return this == other || other instanceof KingsideCastleMove && super.equals(other);
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

        public boolean equals(final Object other){
            return this == other || other instanceof QueensideCastleMove && super.equals(other);
        }

        public String toString(){
            return "O-O-O";
        }
    }

    public static final class NullMove extends Move{
        public NullMove() {
            super(null, 65);
        }

        @Override
        public Board execute(){
            throw new RuntimeException("Can't execute null move");
        }
        public int getCurrentCoord(){
            return -1;
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