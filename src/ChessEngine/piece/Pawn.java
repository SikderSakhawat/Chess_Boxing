package ChessEngine.piece;

import ChessEngine.Alliance;
import ChessEngine.board.Board;
import ChessEngine.board.BoardUtility;
import ChessEngine.board.Move;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import static ChessEngine.board.Move.*;
public class Pawn extends Piece{
    private static final int[] CANDIDATE_MOVE_COORD = {8,16, 7 ,9};
    public Pawn(final int piecePos, final Alliance pieceAll) {
        super(piecePos, pieceAll);
    }

    @Override
    public Collection<Move> calcLegalMoves(Board board) {

        final List<Move> legalMoves = new ArrayList<>();
        for(final int offset : CANDIDATE_MOVE_COORD){
            final int candidateDestinationCoord = this.piecePosition + (this.piecedAlliance.getDirection() * offset);
            if(BoardUtility.isValidTileCoord(candidateDestinationCoord)){
                continue;
            }
            // handles basic non-attacking pawn movement
            if(offset == 8 && !board.getTile(offset).isOccupied()){
                // TODO more things here (work on pawn promotion)!
                legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoord));
            // handles the pawn jump from either 1 or 2 squares
            } else if(offset == 16 && this.isFirstMove() && (BoardUtility.SECOND_ROW[this.piecePosition]
                    && this.getPiecedAlliance().isBlack()) || (BoardUtility.SEVENTH_ROW[this.piecePosition]
                    && this.getPiecedAlliance().isWhite())){
                final int behindDestinationCoord = this.piecePosition + (this.piecedAlliance.getDirection() * 8);
                if(!board.getTile(behindDestinationCoord).isOccupied() && !board.getTile(candidateDestinationCoord).isOccupied()){
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoord));
                }
                // case where we attack the piece to our right diagonal from us
            } else if (offset == 7 &&
                   !((BoardUtility.EIGHTH_COLUMN[piecePosition] && this.piecedAlliance.isWhite() ||
                    (BoardUtility.FIRST_COLUMN[piecePosition] && this.piecedAlliance.isBlack())))) {
                if(board.getTile(candidateDestinationCoord).isOccupied()){
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoord).getPiece();
                    if(this.piecedAlliance !=  pieceOnCandidate.getPiecedAlliance()){
                        // TODO more (the case when we attack a piece and get into promotion)!
                        legalMoves.add(new MajorMove(board, this, candidateDestinationCoord));
                    }
                }
            } else if (offset == 9 &&
                    !((BoardUtility.FIRST_COLUMN[piecePosition] && this.piecedAlliance.isWhite() ||
                    (BoardUtility.EIGHTH_COLUMN[piecePosition] && this.piecedAlliance.isBlack())))){
                if(board.getTile(candidateDestinationCoord).isOccupied()){
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoord).getPiece();
                    if(this.piecedAlliance !=  pieceOnCandidate.getPiecedAlliance()){
                        // TODO more (the case when we attack a piece and get into promotion)!
                        legalMoves.add(new MajorMove(board, this, candidateDestinationCoord));
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    // TODO Case when En Passant happens

    public String toString(){
        return PieceType.PAWN.toString();
    }
}
