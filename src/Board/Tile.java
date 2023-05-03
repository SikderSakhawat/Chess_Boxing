package Board;

public abstract class Tile {

    private int tileCoord;

    public Tile(int coord){
        tileCoord = coord;
    }

    // Methods will get defined in a subclass where these tiles have
    public abstract boolean isOccupied();
    public abstract Piece getPiece();

    // subclass of the empty tile
    public static class EmptyTile extends Tile{
        EmptyTile(int x){
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

}
