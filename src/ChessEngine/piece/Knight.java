package ChessEngine.piece;

import ChessEngine.Alliance;
import ChessEngine.board.Board;
import ChessEngine.board.BoardUtility;
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
            if(BoardUtility.isValidTileCoord(candidateDestinationCoords)){
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoords);
                if(isFirstColExclude(this.piecePosition, currentCandidate) ||
                isSecondColExclude(this.piecePosition, currentCandidate) ||
                isSeventhColExclude(this.piecePosition, currentCandidate) ||
                isEighthCOlExclude(this.piecePosition, currentCandidate)){
                    continue;
                }
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

    // edge cases where knight cannot move & would result in a wrong placement of the knight
    private static boolean isFirstColExclude(final int currentPos, final int candidateOffset){
        return BoardUtility.FIRST_COLUMN[currentPos] && ((candidateOffset == -17) ||
                (candidateOffset == -10) || (candidateOffset == 6) || (candidateOffset == 15));
    }

    private static boolean isSecondColExclude(final int currentPos, final int candidateOffset){
        return BoardUtility.SECOND_COLUMN[currentPos] && ((candidateOffset == -10) || (candidateOffset == 6));
    }

    private static boolean isSeventhColExclude(final int currentPos, final int candidateOffset){
        return BoardUtility.SEVENTH_COLUMN[currentPos] && ((candidateOffset == -6) || (candidateOffset == 10));
    }

    private static boolean isEighthCOlExclude(final int currentPos, final int candidateOffset){
        return BoardUtility.EIGHTH_COLUMN[currentPos] && ((candidateOffset == -15) || (candidateOffset == -6) ||
                (candidateOffset == 10) || (candidateOffset == 17));
    }
}