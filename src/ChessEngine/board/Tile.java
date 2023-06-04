package ChessEngine.board;
import ChessEngine.piece.Piece;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

// abstract class cannot be instantiated, but can be in subclasses that aren't abstract
public abstract class Tile {

    protected final int tileCoord;

    private static final Map<Integer, EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTiles();

    private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();

        for(int i = 0; i < BoardUtility.NUM_TILES; i++){
            emptyTileMap.put(i, new EmptyTile(i));
        }

        return ImmutableMap.copyOf(emptyTileMap); // made so any new emptyTile doesnt get changed, so ImmutableMap keeps it as some "container"
    }

    public static Tile createTile(final int tileCoordinate, final Piece piece){
        return piece != null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_TILES_CACHE.get(tileCoordinate);
        // allows you to create a tile or create a new occupied tile if there is an empty tile there
    }
    private Tile(final int coord){
        tileCoord = coord;
    }

    // Methods will get defined in a subclass where these tiles have
    public abstract boolean isOccupied();
    public abstract Piece getPiece();
    public int getTileCoord(){
        return this.tileCoord;
    }
    public String toString(){
        return "-";
    }

    // subclass of the empty tile
    public static class EmptyTile extends Tile{
        private EmptyTile(final int x){
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

        private OccupiedTile(int tileCoord, Piece pieceOnTile){
            super(tileCoord);
            this.pieceOnTile = pieceOnTile;
        }

        public boolean isOccupied(){
            return true;
        }

        // overridden class from Tile
        public Piece getPiece() {
            return pieceOnTile;
        }

        // If a tile has an occupied piece on it, it adds that piece onto the string, else it appends a "-"
        // Black pieces will show a lowercase, white pieces show an uppercase
        public String toString(){
            return getPiece().getPiecedAlliance().isBlack() ? getPiece().toString().toLowerCase() : getPiece().toString();
        }
    }
}