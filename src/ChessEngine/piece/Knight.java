package ChessEngine.piece;

import ChessEngine.Alliance;
import ChessEngine.board.Board;
import ChessEngine.board.Move;
import ChessEngine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece{
    private final static int[] CANDIDATE_MOVE_COORDS = {-17,-15,-10,-6,6,10,15,17};
    public Knight(final int piecePos, final Alliance pieceAll) {
        super(piecePos, pieceAll);
    }

    @Override
    public List<Move> calcLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        int candidateDestinationCoords; // this is the offset we use for the Knight for a sorta local variable
        for(final int currentCandidate : CANDIDATE_MOVE_COORDS){
            candidateDestinationCoords = this.piecePosition + currentCandidate;
            if(true /* is a valid tileCoord */){
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoords);
                if(!candidateDestinationTile.isOccupied()){
                    legalMoves.add(new Move()); // if whatever we find is both a valid tile coordinate, and isn't occupied,
                    // we can add that as a legal move for Knight
                } else {
                    final Piece pieceDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = pieceDestination.getPiecedAlliance();

                    if(this.piecedAlliance != pieceAlliance){
                        legalMoves.add(new Move());
                    }
                }
            }
        }


        return ImmutableList.copyOf(legalMoves);
    }
}
