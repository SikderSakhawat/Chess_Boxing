package ChessEngine.board.player;

import ChessEngine.Alliance;
import ChessEngine.board.Board;
import ChessEngine.board.Move;
import ChessEngine.board.Tile;
import ChessEngine.piece.Piece;
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
    protected Collection<Move> calculateKingCastles(Collection<Move> playerLegals, Collection<Move> opponentLegals) {
        final List<Move> kingCastles = new ArrayList<>();
        if(this.playerKing.isFirstMove() && !this.isChecked()){
            // kingside castle for white
            if(!this.board.getTile(61).isOccupied() && !this.board.getTile(62).isOccupied()){
                final Tile rookTile = this.board.getTile(63); // checks if tiles between king and rook are clear
                if(rookTile.isOccupied() && rookTile.getPiece().isFirstMove()){ // checks if there is a rook that hasn't moved
                    if(Player.calculateAttacksOnTile(61, opponentLegals).isEmpty() &&
                       Player.calculateAttacksOnTile(62, opponentLegals).isEmpty() &&
                    rookTile.getPiece().getPieceType().isRook()){
                        // TODO add castle move
                        kingCastles.add(null);
                    }

                }
            }
            if(!this.board.getTile(59).isOccupied() &&
               !this.board.getTile(58).isOccupied() &&
               !this.board.getTile(57).isOccupied()){
                final Tile rookTile = this.board.getTile(56);
                if(rookTile.isOccupied() && rookTile.getPiece().isFirstMove()){
                    // TODO add castle move
                }
            }

        }
        return ImmutableList.copyOf(kingCastles);
    }
}
