package ChessEngine.piece;

import ChessEngine.Alliance;
import ChessEngine.board.BoardUtility;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;

enum PieceUtility {

    INSTANCE;

    private final Table<Alliance, Integer, Queen> ALL_POSSIBLE_QUEENS = PieceUtility.createAllPossibleMovedQueens();
    private final Table<Alliance, Integer, Rook> ALL_POSSIBLE_ROOKS = PieceUtility.createAllPossibleMovedRooks();
    private final Table<Alliance, Integer, Knight> ALL_POSSIBLE_KNIGHTS = PieceUtility.createAllPossibleMovedKnights();
    private final Table<Alliance, Integer, Bishop> ALL_POSSIBLE_BISHOPS = PieceUtility.createAllPossibleMovedBishops();
    private final Table<Alliance, Integer, Pawn> ALL_POSSIBLE_PAWNS = PieceUtility.createAllPossibleMovedPawns();

    Pawn getMovedPawn(final Alliance alliance,
                      final int destinationCoordinate) {
        return ALL_POSSIBLE_PAWNS.get(alliance, destinationCoordinate);
    }

    Knight getMovedKnight(final Alliance alliance,
                          final int destinationCoordinate) {
        return ALL_POSSIBLE_KNIGHTS.get(alliance, destinationCoordinate);
    }

    Bishop getMovedBishop(final Alliance alliance,
                          final int destinationCoordinate) {
        return ALL_POSSIBLE_BISHOPS.get(alliance, destinationCoordinate);
    }

    Rook getMovedRook(final Alliance alliance,
                      final int destinationCoordinate) {
        return ALL_POSSIBLE_ROOKS.get(alliance, destinationCoordinate);
    }

    Queen getMovedQueen(final Alliance alliance,
                        final int destinationCoordinate) {
        return ALL_POSSIBLE_QUEENS.get(alliance, destinationCoordinate);
    }

    private static Table<Alliance, Integer, Pawn> createAllPossibleMovedPawns() {
        final ImmutableTable.Builder<Alliance, Integer, Pawn> pieces = ImmutableTable.builder();
        for(final Alliance alliance : Alliance.values()) {
            for(int i = 0; i < BoardUtility.NUM_TILES; i++) {
                pieces.put(alliance, i, new Pawn(alliance, i, false));
            }
        }
        return pieces.build();
    }

    private static Table<Alliance, Integer, Knight> createAllPossibleMovedKnights() {
        final ImmutableTable.Builder<Alliance, Integer, Knight> pieces = ImmutableTable.builder();
        for(final Alliance alliance : Alliance.values()) {
            for(int i = 0; i < BoardUtility.NUM_TILES; i++) {
                pieces.put(alliance, i, new Knight(alliance, i, false));
            }
        }
        return pieces.build();
    }

    private static Table<Alliance, Integer, Bishop> createAllPossibleMovedBishops() {
        final ImmutableTable.Builder<Alliance, Integer, Bishop> pieces = ImmutableTable.builder();
        for(final Alliance alliance : Alliance.values()) {
            for(int i = 0; i < BoardUtility.NUM_TILES; i++) {
                pieces.put(alliance, i, new Bishop(alliance, i, false));
            }
        }
        return pieces.build();
    }

    private static Table<Alliance, Integer, Rook> createAllPossibleMovedRooks() {
        final ImmutableTable.Builder<Alliance, Integer, Rook> pieces = ImmutableTable.builder();
        for(final Alliance alliance : Alliance.values()) {
            for(int i = 0; i < BoardUtility.NUM_TILES; i++) {
                pieces.put(alliance, i, new Rook(alliance, i, false));
            }
        }
        return pieces.build();
    }

    private static Table<Alliance, Integer, Queen> createAllPossibleMovedQueens() {
        final ImmutableTable.Builder<Alliance, Integer, Queen> pieces = ImmutableTable.builder();
        for(final Alliance alliance : Alliance.values()) {
            for(int i = 0; i < BoardUtility.NUM_TILES; i++) {
                pieces.put(alliance, i, new Queen(alliance, i, false));
            }
        }
        return pieces.build();
    }

}
