package ChessEngine.board.player;

import ChessEngine.Alliance;
import ChessEngine.board.Board;
import ChessEngine.board.Move;
import ChessEngine.piece.Piece;

import java.util.Collection;

public class BlackPlayer extends Player {

    public BlackPlayer(Board board,
                       Collection<Move> whiteStandardLegalMoves,
                       Collection<Move> blackStandardLegalMoves) {

        super(board,blackStandardLegalMoves,whiteStandardLegalMoves);
    }

    public Collection<Piece> getActivePieces(){
        return this.board.getBlackPiece();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.BLACK;
    }

    @Override
    public Player getOpponent() {
        return this.board.whitePlayer();
    }
}
