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

public class BlackPlayer extends Player {

    public BlackPlayer(final Board board,
                       final Collection<Move> whiteStandardLegalMoves,
                       final Collection<Move> blackStandardLegalMoves) {

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

    @Override
    protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegals, final Collection<Move> opponentLegals) {
        final List<Move> kingCastles = new ArrayList<>();
        if(this.playerKing.isFirstMove() && !this.isChecked()){
            // kingside castle for black
            if(!this.board.getTile(5).isOccupied() && !this.board.getTile(6).isOccupied()){
                final Tile rookTile = this.board.getTile(7); // checks if tiles between king and rook are clear
                if(rookTile.isOccupied() && rookTile.getPiece().isFirstMove()){ // checks if there is a rook that hasn't moved
                    if(Player.calculateAttacksOnTile(5, opponentLegals).isEmpty() &&
                            Player.calculateAttacksOnTile(6, opponentLegals).isEmpty() &&
                            rookTile.getPiece().getPieceType().isRook()){
                        kingCastles.add(new Move.KingsideCastleMove(this.board, this.playerKing, 6,(Rook) rookTile.getPiece(),rookTile.getTileCoord(), 5 ));
                    }
                }
            }
            // queenside castle for black
            if(!this.board.getTile(1).isOccupied() &&
                    !this.board.getTile(2).isOccupied() &&
                    !this.board.getTile(3).isOccupied()){
                final Tile rookTile = this.board.getTile(0);
                if(rookTile.isOccupied() && rookTile.getPiece().isFirstMove()){
                    kingCastles.add(new Move.QueensideCastleMove(this.board, this.playerKing, 2,(Rook) rookTile.getPiece(),rookTile.getTileCoord(), 3));
                }
            }
        }
        return ImmutableList.copyOf(kingCastles);
    }
}
