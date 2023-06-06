package ChessEngine.AI_WIP;

import ChessEngine.board.BoardUtility;
import ChessEngine.board.Move;
import ChessEngine.piece.Piece;
import ChessEngine.player.Player;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class KingAnalyze {

    private static final KingAnalyze INSTANCE = new KingAnalyze();
    private static final List<List<Boolean>> COLUMNS = initColumns();

    private KingAnalyze() {
    }

    public static KingAnalyze get() {
        return INSTANCE;
    }

    private static List<List<Boolean>> initColumns() {
        final List<List<Boolean>> columns = new ArrayList<>();
        columns.add(BoardUtility.INSTANCE.FIRST_COLUMN);
        columns.add(BoardUtility.INSTANCE.SECOND_COLUMN);
        columns.add(BoardUtility.INSTANCE.THIRD_COLUMN);
        columns.add(BoardUtility.INSTANCE.FOURTH_COLUMN);
        columns.add(BoardUtility.INSTANCE.FIFTH_COLUMN);
        columns.add(BoardUtility.INSTANCE.SIXTH_COLUMN);
        columns.add(BoardUtility.INSTANCE.SEVENTH_COLUMN);
        columns.add(BoardUtility.INSTANCE.EIGHTH_COLUMN);
        return ImmutableList.copyOf(columns);
    }

    public KingDistance calculateKingTropism(final Player player) {
        final int playerKingSquare = player.getPlayerKing().getPiecePosition();
        final Collection<Move> enemyMoves = player.getOpponent().getLegalMoves();
        Piece closestPiece = null;
        int closestDistance = Integer.MAX_VALUE;
        for(final Move move : enemyMoves) {
            final int currentDistance = calculateChebyshevDistance(playerKingSquare, move.getDestinationCoordinate());
            if(currentDistance < closestDistance) {
                closestDistance = currentDistance;
                closestPiece = move.getMovedPiece();
            }
        }
        return new KingDistance(closestPiece, closestDistance);
    }

    private static int calculateChebyshevDistance(final int kingTileId,
                                                  final int enemyAttackTileId) {
        final int rankDistance = Math.abs(getRank(enemyAttackTileId) - getRank(kingTileId));
        final int fileDistance = Math.abs(getFile(enemyAttackTileId) - getFile(kingTileId));
        return Math.max(rankDistance, fileDistance);
    }

    private static int getFile(final int coordinate) {
        if(BoardUtility.INSTANCE.FIRST_COLUMN.get(coordinate)) {
            return 1;
        } else if(BoardUtility.INSTANCE.SECOND_COLUMN.get(coordinate)) {
            return 2;
        } else if(BoardUtility.INSTANCE.THIRD_COLUMN.get(coordinate)) {
            return 3;
        } else if(BoardUtility.INSTANCE.FOURTH_COLUMN.get(coordinate)) {
            return 4;
        } else if(BoardUtility.INSTANCE.FIFTH_COLUMN.get(coordinate)) {
            return 5;
        } else if(BoardUtility.INSTANCE.SIXTH_COLUMN.get(coordinate)) {
            return 6;
        } else if(BoardUtility.INSTANCE.SEVENTH_COLUMN.get(coordinate)) {
            return 7;
        } else if(BoardUtility.INSTANCE.EIGHTH_COLUMN.get(coordinate)) {
            return 8;
        }
        throw new RuntimeException("should not reach here!");
    }

    private static int getRank(final int coordinate) {
        if(BoardUtility.INSTANCE.FIRST_ROW.get(coordinate)) {
            return 1;
        } else if(BoardUtility.INSTANCE.SECOND_ROW.get(coordinate)) {
            return 2;
        } else if(BoardUtility.INSTANCE.THIRD_ROW.get(coordinate)) {
            return 3;
        } else if(BoardUtility.INSTANCE.FOURTH_ROW.get(coordinate)) {
            return 4;
        } else if(BoardUtility.INSTANCE.FIFTH_ROW.get(coordinate)) {
            return 5;
        } else if(BoardUtility.INSTANCE.SIXTH_ROW.get(coordinate)) {
            return 6;
        } else if(BoardUtility.INSTANCE.SEVENTH_ROW.get(coordinate)) {
            return 7;
        } else if(BoardUtility.INSTANCE.EIGHTH_ROW.get(coordinate)) {
            return 8;
        }
        throw new RuntimeException("should not reach here!");
    }

    static class KingDistance {

        final Piece enemyPiece;
        final int distance;

        KingDistance(final Piece enemyDistance,
                     final int distance) {
            this.enemyPiece = enemyDistance;
            this.distance = distance;
        }

        public Piece getEnemyPiece() {
            return enemyPiece;
        }

        public int getDistance() {
            return distance;
        }

        public int tropismScore() {
            return (enemyPiece.getPieceValue()/10) * distance;
        }

    }

}
