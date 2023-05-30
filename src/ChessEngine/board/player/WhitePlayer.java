package ChessEngine.board.player;

import ChessEngine.Alliance;
import ChessEngine.board.Board;
import ChessEngine.board.Move;
import ChessEngine.piece.Piece;

import java.util.Collection;

public class WhitePlayer extends Player {

    public WhitePlayer(Board board,
                       Collection<Move> whiteStandardLegalMoves,
                       Collection<Move> blackStandardLegalMoves) {

        super(board, whiteStandardLegalMoves, blackStandardLegalMoves);
    }

    public Collection<Piece> getActivePieces(){
        return this.board.getWhitePiece();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.WHITE;
    }

    @Override
    public Player getOpponent() {
        return this.board.blackPlayer();
    }
}
