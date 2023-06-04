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

public class Queen extends Piece{

    private final static int[] MOVE_VECTOR_COORDS = {-8,-1,1,8,-7,7,-9,9};
    public Queen(int piecePos, Alliance pieceAll){
        super(PieceType.BISHOP, piecePos, pieceAll, true);
    }
    public Queen(int piecePos, Alliance pieceAll, final boolean isFirstMove){
        super(PieceType.BISHOP, piecePos, pieceAll, isFirstMove);
    }

    @Override
    public Collection<Move> calcLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for(final int candidateCoordOffset : MOVE_VECTOR_COORDS){
            int candidateDestinationCoord = this.piecePosition;

            while (BoardUtility.isValidTileCoord(candidateDestinationCoord)){
                if(isFirstColExclude(candidateDestinationCoord, candidateCoordOffset) ||
                        isEighthColExclude(candidateDestinationCoord,candidateCoordOffset)){
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

    @Override
    public Queen movePiece(Move move) {
        return new Queen(move.getDestinationCoord(), move.getMovedPiece().getPiecedAlliance());
    }

    // edge cases
    private static boolean isFirstColExclude(final int currentPos, final int offset){
        return BoardUtility.FIRST_COLUMN[currentPos] && (offset == -9 || offset == 7 || offset == 1);
    }
    private static boolean isEighthColExclude(final int currentPos, final int offset){
        return BoardUtility.EIGHTH_COLUMN[currentPos] && (offset == 9 || offset == -7 || offset == -1);
    }

    public String toString(){
        return PieceType.QUEEN.toString();
    }
}
