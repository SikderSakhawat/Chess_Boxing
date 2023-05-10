package ChessEngine.board;
import ChessEngine.piece.Piece;

import java.util.HashMap;
import java.util.Map;

// abstract class cannot be instantiated, but can be in subclasses that aren't abstract
public abstract class Tile {

    protected final int tileCoord;

    private static final Map<Integer, EmptyTile> EMPTY_TILES = createAllPossibleEmptyTiles();

    private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();

        for(int i = 0; i < 64; i++){
            emptyTileMap.put(i, new EmptyTile(i));
        }
        return null;
    }

    public Tile(int coord){
        tileCoord = coord;
    }

    // Methods will get defined in a subclass where these tiles have
    public abstract boolean isOccupied();
    public abstract Piece getPiece();

    // subclass of the empty tile
    public static class EmptyTile extends Tile{
        EmptyTile(final int x){
            super(x);
        }

        // overridden methods
        public boolean isOccupied(){
            return false;
        }

        public Piece getPiece(){
            return null;
        }

    }

    // static class so there cannot be any inheritance state and so it can be on its own
    public static final class OccupiedTile extends Tile{
        private final Piece pieceOnTile;

        OccupiedTile(int tileCoord, Piece pieceOnTile){
            super(tileCoord);
            this.pieceOnTile = pieceOnTile;
        }

        public boolean isOccupied(){
            return true;
        }

        // overridden class from Tile
        public Piece getPiece() {
            return null;
        }
    }
}
