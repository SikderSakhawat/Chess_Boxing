package ChessEngine.board.player;

import ChessEngine.Alliance;
import ChessEngine.board.Board;
import ChessEngine.board.Move;
import ChessEngine.piece.King;
import ChessEngine.piece.Piece;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Player {
    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> legalMoves;
    private final boolean isInCheck;

    Player(final Board board,
           final Collection<Move> legalMoves,
           final Collection<Move> opponentMoves) {
        this.board = board;
        this.legalMoves = ImmutableList.copyOf(Iterables.concat(legalMoves, calculateKingCastles(legalMoves,opponentMoves)));
        this.playerKing = makeKing();
        this.isInCheck = !Player.calculateAttacksOnTile(this.playerKing.getPiecePosition(), opponentMoves).isEmpty();
    }

    public King getPlayerKing(){
        return this.playerKing;
    }
    public Collection<Move> getLegalMoves(){
        return this.legalMoves;
    }
    // This will help us final all the moves that can attack the kings position so that the king won't be able to move there in
    // its set of legal moves
    protected static Collection<Move> calculateAttacksOnTile(int piecePosition, Collection<Move> moves) {
        final List<Move> attackMoves= new ArrayList<>();
        for(final Move move : moves){
            if(piecePosition == move.getDestinationCoord()){
                attackMoves.add(move);
            }
        }
        return ImmutableList.copyOf(attackMoves);
    }

    // makes sure that each side has a King to play with
    protected King makeKing() {
        for (final Piece piece : getActivePieces()) {
            if (piece.getPieceType().isKing()) {
                return (King) piece;
            }
        }
        throw new RuntimeException("Not a valid board");
    }

    public boolean isMoveLegal(final Move move){
        return this.legalMoves.contains(move);
    }
    public abstract Collection<Piece> getActivePieces();
    public abstract Alliance getAlliance();
    public abstract Player getOpponent();
    protected abstract Collection<Move> calculateKingCastles(Collection<Move> playerLegals, Collection<Move> opponentLegals);

    public boolean isChecked(){return this.isInCheck;}

    protected boolean hasEscapeMoves(){
        for(final Move move : this.legalMoves){
            final MoveTransition transition = makeMove(move);
            if(transition.getMoveStatus.isDone()){
                return true;
            }
        }
        return false;
    }
    public boolean isMated(){return this.isInCheck && !hasEscapeMoves();}
    public boolean isStaleMated(){return !this.isInCheck && !hasEscapeMoves();}

    // TODO implement these methods in the future
    public boolean isCastled(){return false;}
    public MoveTransition makeMove(final Move move) {
        if (!isMoveLegal(move)) {
            return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE);
        }
        final Board transBoard = move.execute();
        // Passes opponent king's position after we make a move to check the moves that attack our king
        // and save those values into a list
        final Collection<Move> kingAttacks = Player.calculateAttacksOnTile
                (transBoard
                        .currentPlayer().
                        getOpponent().
                        getPlayerKing().
                        getPiecePosition(), transBoard.currentPlayer().getLegalMoves());
        // returns a new board that can check if there are attacks on king, and if there are we can't make that move
        // this helps with a piece being pinned to a king.
        if (!kingAttacks.isEmpty())
            return new MoveTransition(this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);
        return new MoveTransition(transBoard, move, MoveStatus.DONE);
    }

}
