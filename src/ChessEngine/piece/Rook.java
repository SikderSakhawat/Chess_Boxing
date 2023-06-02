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

public class Rook extends Piece{

    private final static int[] MOVE_VECTOR_COORDS = {-8,-1,1,8};

    public Rook(int piecePos, Alliance pieceAll) {
        super(PieceType.ROOK, piecePos, pieceAll);
    }

    @Override
    public Collection<Move> calcLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        int candidateDestinationCoords; // this is the offset we use for the Knight for a sorta local variable
        for(final int currentCandidate : MOVE_VECTOR_COORDS){
            candidateDestinationCoords = this.piecePosition + currentCandidate;
            if(BoardUtility.isValidTileCoord(candidateDestinationCoords)){
                if(isFirstColExclude(this.piecePosition, currentCandidate) || isEighthColExclude(this.piecePosition, currentCandidate)){
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
    public Rook movePiece(Move move) {
        return new Rook(move.getDestinationCoord(), move.getMovePiece().getPiecedAlliance());
    }

    private boolean isFirstColExclude(int currentPos, int candidateOffset) {
        return BoardUtility.FIRST_COLUMN[currentPos] && (candidateOffset == -1);
    }
    private static boolean isEighthColExclude(final int currentPos, final int candidateOffset){
        return BoardUtility.EIGHTH_COLUMN[currentPos] && (candidateOffset == 1);
    }

    public String toString(){
        return PieceType.ROOK.toString();
    }
}
