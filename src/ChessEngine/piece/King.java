package ChessEngine.piece;

import ChessEngine.Alliance;
import ChessEngine.board.Board;
import ChessEngine.board.BoardUtility;
import ChessEngine.board.Move;
import ChessEngine.board.Move.AttackMove;
import ChessEngine.board.Move.MajorMove;
import ChessEngine.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class King extends Piece {

    private static final int[] CANDIDATE_LEGAL_MOVES = {-9,-8,-7,-1,1,7,8,9};
    public King(final int piecePos, final Alliance pieceAll) {
        super(PieceType.KING, piecePos, pieceAll);
    }

    @Override
    public Collection<Move> calcLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for(final int currentCandidateOffset: CANDIDATE_LEGAL_MOVES){
            final int candidateDestinationCoords = this.piecePosition + currentCandidateOffset;
            if(isFirstColExclude(this.piecePosition, currentCandidateOffset) || isSecondColExclude(this.piecePosition, currentCandidateOffset)){
                continue;
            }
            if(BoardUtility.isValidTileCoord(candidateDestinationCoords)){
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoords);
                if(!candidateDestinationTile.isOccupied()){
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoords));
                } else {
                    final Piece pieceDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = pieceDestination.getPiecedAlliance();

                    if(this.piecedAlliance != pieceAlliance){
                        legalMoves.add(new AttackMove(board, this, candidateDestinationCoords, pieceDestination));
                    }
                }
            }

        }
        return ImmutableList.copyOf(legalMoves);
    }

    private static boolean isFirstColExclude(final int currentPos, final int candidateOffset){
        return BoardUtility.FIRST_COLUMN[currentPos] && ((candidateOffset == -9) ||
                (candidateOffset == -1) || (candidateOffset == 7));
    }

    private static boolean isSecondColExclude(final int currentPos, final int candidateOffset){
        return BoardUtility.EIGHTH_COLUMN[currentPos] && ((candidateOffset == 1) || (candidateOffset == 9) || (candidateOffset == -7));
    }

    public String toString(){
        return PieceType.KING.toString();
    }
}
