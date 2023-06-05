package ChessEngine.piece;
import ChessEngine.Alliance;
import ChessEngine.board.*;
import com.google.common.collect.ImmutableList;

import java.util.*;

public final class Bishop extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATES = {-9, -7, 7, 9};

    private final static Map<Integer, MoveUtility.Line[]> PRECOMPUTED_CANDIDATES = computeCandidates();


    public Bishop(final Alliance alliance,
                  final int piecePosition) {
        super(PieceType.BISHOP, alliance, piecePosition, true);
    }

    public Bishop(final Alliance alliance,
                  final int piecePosition,
                  final boolean isFirstMove) {
        super(PieceType.BISHOP, alliance, piecePosition, isFirstMove);
    }

    private static Map<Integer, MoveUtility.Line[]> computeCandidates() {
        Map<Integer, MoveUtility.Line[]> candidates = new HashMap<>();
        for (int position = 0; position < BoardUtility.NUM_TILES; position++) {
            List<MoveUtility.Line> lines = new ArrayList<>();
            for (int offset : CANDIDATE_MOVE_COORDINATES) {
                int destination = position;
                MoveUtility.Line line = new MoveUtility.Line();
                while (BoardUtility.isValidTileCoordinate(destination)) {
                    if (isFirstColumnExclusion(destination, offset) ||
                            isEighthColumnExclusion(destination, offset)) {
                        break;
                    }
                    destination += offset;
                    if (BoardUtility.isValidTileCoordinate(destination)) {
                        line.addCoordinate(destination);
                    }
                }
                if (!line.isEmpty()) {
                    lines.add(line);
                }
            }
            if (!lines.isEmpty()) {
                candidates.put(position, lines.toArray(new MoveUtility.Line[0]));
            }
        }
        return Collections.unmodifiableMap(candidates);
    }


    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final MoveUtility.Line line : PRECOMPUTED_CANDIDATES.get(this.piecePosition)) {
            for (final int candidateDestinationCoordinate : line.getLineCoordinates()) {
                final Piece pieceAtDestination = board.getPiece(candidateDestinationCoordinate);
                if (pieceAtDestination == null) {
                    legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                } else {
                    final Alliance pieceAlliance = pieceAtDestination.getPieceAllegiance();
                    if (this.pieceAlliance != pieceAlliance) {
                        legalMoves.add(new Move.MajorAttackMove(board, this, candidateDestinationCoordinate,
                                pieceAtDestination));
                    }
                    break;
                }
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }

    @Override
    public int locationBonus() {
        return this.pieceAlliance.bishopBonus(this.piecePosition);
    }

    @Override
    public Bishop movePiece(final Move move) {
        return PieceUtility.INSTANCE.getMovedBishop(move.getMovedPiece().getPieceAllegiance(), move.getDestinationCoordinate());
    }

    @Override
    public String toString() {
        return this.pieceType.toString();
    }

    private static boolean isFirstColumnExclusion(final int position,
                                                  final int offset) {
        return (BoardUtility.INSTANCE.FIRST_COLUMN.get(position) &&
                ((offset == -9) || (offset == 7)));
    }

    private static boolean isEighthColumnExclusion(final int position,
                                                   final int offset) {
        return BoardUtility.INSTANCE.EIGHTH_COLUMN.get(position) &&
                ((offset == -7) || (offset == 9));
    }

}