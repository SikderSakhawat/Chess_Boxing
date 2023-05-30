package ChessEngine.board.player;

import ChessEngine.Alliance;
import ChessEngine.board.Board;
import ChessEngine.board.Move;
import ChessEngine.piece.King;
import ChessEngine.piece.Piece;

import java.util.Collection;

public abstract class Player {
    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> legalMoves;

    Player(final Board board,
           final Collection<Move> legalMoves,
           final Collection<Move> opponentMoves) {
        this.board = board;
        this.legalMoves = legalMoves;
        this.playerKing = makeKing();
    }

    // makes sure that each side has a King to play with
    private King makeKing() {
        for(final Piece piece : this.getActivePieces()){
            if(piece.getPieceType().isKing()){
                return (King) piece;
            }
        }
        throw new RuntimeException("Shouldn't be here! Board does not exist!");
    }

    public boolean isMoveLegal(final Move move){
        return this.legalMoves.contains(move);
    }
    public abstract Collection<Piece> getActivePieces();
    public abstract Alliance getAlliance();
    public abstract Player getOpponent();

}
