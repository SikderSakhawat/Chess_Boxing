package ChessEngine.board.player;

import ChessEngine.Alliance;
import ChessEngine.board.Board;
import ChessEngine.board.Move;
import ChessEngine.board.Tile;
import ChessEngine.piece.Piece;
import ChessEngine.piece.Rook;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WhitePlayer extends Player {

    public WhitePlayer(final Board board,
                       final Collection<Move> whiteStandardLegalMoves,
                       final Collection<Move> blackStandardLegalMoves) {

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

    @Override
    protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegals,
                                                    final Collection<Move> opponentsLegals) {
        final List<Move> kingCastles = new ArrayList<>();
        if (this.playerKing.isFirstMove() && this.playerKing.getPiecePosition() == 60 && !this.isChecked()) {
            //whites kingside castle
            if (!this.board.getTile(61).isOccupied() && !this.board.getTile(62).isOccupied()) {
                final Tile rookTile = this.board.getTile(63);
                if (rookTile.isOccupied() && rookTile.getPiece().isFirstMove()) {
                    if (Player.calculateAttacksOnTile(61, opponentsLegals).isEmpty() &&
                            Player.calculateAttacksOnTile(62, opponentsLegals).isEmpty() &&
                            rookTile.getPiece().getPieceType().isRook()) {
                        kingCastles.add(new Move.KingsideCastleMove(this.board,
                                this.playerKing,
                                62,
                                (Rook)rookTile.getPiece(),
                                rookTile.getTileCoord(),
                                61));
                    }
                }
            }
            //whites queenside castle
            if (!this.board.getTile(59).isOccupied() &&
                    !this.board.getTile(58).isOccupied() &&
                    !this.board.getTile(57).isOccupied()) {

                final Tile rookTile = this.board.getTile(56);
                if (rookTile.isOccupied() && rookTile.getPiece().isFirstMove()) {
                    kingCastles.add(new Move.QueensideCastleMove(this.board,
                            this.playerKing,
                            58,
                            (Rook)rookTile.getPiece(),
                            rookTile.getTileCoord(),
                            59));
                }
            }
        }
        return ImmutableList.copyOf(kingCastles);
    }
}
