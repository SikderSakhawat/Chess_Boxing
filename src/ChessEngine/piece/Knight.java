package ChessEngine.piece;
import ChessEngine.Alliance;
import ChessEngine.board.Board;
import ChessEngine.board.BoardUtility;
import ChessEngine.player.Move;
import ChessEngine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
public class Knight extends Piece{
    private final static int[] CANDIDATE_MOVE_COORDS = {-17,-15,-10,-6,6,10,15,17};
    public Knight(int piecePos, Alliance pieceAll){
        super(PieceType.BISHOP, piecePos, pieceAll, true);
    }
    public Knight(int piecePos, Alliance pieceAll, final boolean isFirstMove){
        super(PieceType.BISHOP, piecePos, pieceAll, isFirstMove);
    }

    @Override
    public Collection<Move> calcLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        int candidateDestinationCoords; // this is the offset we use for the Knight for a sorta local variable
        for(final int currentCandidate : CANDIDATE_MOVE_COORDS){
            candidateDestinationCoords = this.piecePosition + currentCandidate;
            if(BoardUtility.isValidTileCoord(candidateDestinationCoords)){
                if(isFirstColExclude(this.piecePosition, currentCandidate) ||
                isSecondColExclude(this.piecePosition, currentCandidate) ||
                isSeventhColExclude(this.piecePosition, currentCandidate) ||
                isEighthColExclude(this.piecePosition, currentCandidate)){
                    continue;
                }
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoords);
                if(!candidateDestinationTile.isOccupied()){
                    legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoords));
                    // if whatever we find is both a valid tile coordinate, and isn't occupied,
                    // we can add that as a legal move for Knight
                } else {
                    final Piece pieceDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = pieceDestination.getPiecedAlliance();

                    if(this.piecedAlliance != pieceAlliance){
                        legalMoves.add(new Move.AttackMove(board, this, candidateDestinationCoords, pieceDestination));
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public Knight movePiece(Move move) {
        return new Knight(move.getDestinationCoord(), move.getMovedPiece().getPiecedAlliance());
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

    private static boolean isEighthColExclude(final int currentPos, final int candidateOffset){
        return BoardUtility.EIGHTH_COLUMN[currentPos] && ((candidateOffset == -15) || (candidateOffset == -6) ||
                (candidateOffset == 10) || (candidateOffset == 17));
    }

    public String toString(){
        return PieceType.KNIGHT.toString();
    }
}
