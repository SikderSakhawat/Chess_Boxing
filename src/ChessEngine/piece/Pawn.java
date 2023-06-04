package ChessEngine.piece;

import ChessEngine.Alliance;
import ChessEngine.board.Board;
import ChessEngine.board.BoardUtility;
import ChessEngine.player.Move;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import static ChessEngine.player.Move.*;
public class Pawn extends Piece{
    private static final int[] CANDIDATE_MOVE_COORD = {8,16, 7 ,9};
    public Pawn(int piecePos, Alliance pieceAll){
        super(PieceType.BISHOP, piecePos, pieceAll, true);
    }
    public Pawn(int piecePos, Alliance pieceAll, final boolean isFirstMove){
        super(PieceType.BISHOP, piecePos, pieceAll, isFirstMove);
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
            } else if(offset == 16 && this.isFirstMove() && ((BoardUtility.SEVENTH_RANK[this.piecePosition]
                    && this.getPiecedAlliance().isBlack()) || (BoardUtility.SECOND_RANK[this.piecePosition]
                    && this.getPiecedAlliance().isWhite()))){
                final int behindDestinationCoord = this.piecePosition + (this.piecedAlliance.getDirection() * 8);
                if(!board.getTile(behindDestinationCoord).isOccupied() && !board.getTile(candidateDestinationCoord).isOccupied()){
                    legalMoves.add(new PawnJump(board, this, candidateDestinationCoord));
                }
                // case where we attack the piece to our right diagonal from us
            } else if (offset == 7 &&
                   !((BoardUtility.EIGHTH_COLUMN[piecePosition] && this.piecedAlliance.isWhite() ||
                    (BoardUtility.FIRST_COLUMN[piecePosition] && this.piecedAlliance.isBlack())))) {
                if(board.getTile(candidateDestinationCoord).isOccupied()){
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoord).getPiece();
                    if(this.piecedAlliance !=  pieceOnCandidate.getPiecedAlliance()){
                        // TODO more (the case when we attack a piece and get into promotion)!
                        legalMoves.add(new PawnAttackMove(board, this, candidateDestinationCoord, pieceOnCandidate));
                    }
                }
            } else if (offset == 9 &&
                    !((BoardUtility.FIRST_COLUMN[piecePosition] && this.piecedAlliance.isWhite() ||
                    (BoardUtility.EIGHTH_COLUMN[piecePosition] && this.piecedAlliance.isBlack())))){
                if(board.getTile(candidateDestinationCoord).isOccupied()){
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoord).getPiece();
                    if(this.piecedAlliance !=  pieceOnCandidate.getPiecedAlliance()){
                        // TODO more (the case when we attack a piece and get into promotion)!
                        legalMoves.add(new PawnAttackMove(board, this, candidateDestinationCoord, pieceOnCandidate));
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public Pawn movePiece(Move move) {
        return new Pawn(move.getDestinationCoord(), move.getMovePiece().getPiecedAlliance());
    }

    // TODO Case when En Passant happens

    public String toString(){
        return PieceType.PAWN.toString();
    }
}
