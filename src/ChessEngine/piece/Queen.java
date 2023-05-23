package ChessEngine.piece;

import ChessEngine.Alliance;
import ChessEngine.board.Board;
import ChessEngine.board.BoardUtility;
import ChessEngine.board.Move;
import ChessEngine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Queen extends Piece{

    private final static int[] MOVE_VECTOR_COORDS = {-8,-1,1,8,-7,7,-9,9};
    public Queen(int piecePos, Alliance pieceAll) {
        super(piecePos, pieceAll);
    }

    @Override
    public Collection<Move> calcLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for(final int candidateCoordOffset : MOVE_VECTOR_COORDS){
            int candidateDestinationCoord = this.piecePosition;

            while (BoardUtility.isValidTileCoord(candidateDestinationCoord)){
                if(isFirstColExclude(candidateDestinationCoord, candidateCoordOffset) ||
                        isEighthColExclude(candidateCoordOffset,candidateCoordOffset)){
                    break; // if true we don't want this move as it breaks our edge cases
                }
                candidateDestinationCoord += candidateCoordOffset;
                if(BoardUtility.isValidTileCoord(candidateDestinationCoord)){
                    final Tile candidateDestinationTile = board.getTile(candidateDestinationCoord);
                    if(!candidateDestinationTile.isOccupied()){
                        legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoord));
                        // if whatever we find is both a valid tile coordinate, and isn't occupied,
                        // we can add that as a legal move for Knight
                    } else {
                        final Piece pieceDestination = candidateDestinationTile.getPiece();
                        final Alliance pieceAlliance = pieceDestination.getPiecedAlliance();

                        if(this.piecedAlliance != pieceAlliance){
                            legalMoves.add(new Move.AttackMove(board, this, candidateDestinationCoord, pieceDestination));
                        }
                        break; // this is needed for bishop as the bishop cannot jump pieces like the knight can,
                        // so this accounts for it to see which legal moves exist for bishop
                    }
                }
            }
        }

        return ImmutableList.copyOf(legalMoves);
    }
}
