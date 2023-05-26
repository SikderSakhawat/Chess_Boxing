package ChessEngine.board;

import ChessEngine.Alliance;
import ChessEngine.piece.*;
import com.google.common.collect.ImmutableList;

import java.util.*;

public class Board {
    private final List<Tile> gameBoard;
    private final Collection<Piece> whitePiece;
    private final Collection<Piece> blackPiece;

    private Board(Builder builder){
        this.gameBoard = createGameBoard(builder);
        this.whitePiece = calcActivePieces(this.gameBoard, Alliance.WHITE);
        this.blackPiece = calcActivePieces(this.gameBoard, Alliance.BLACK);
        // finds ALL the legal moves in chess for a given white or black piece
        final Collection<Move> whiteStandardLegalMoves = calcLegalMoves(this.whitePiece);
        final Collection<Move> blackStandardLegalMoves = calcLegalMoves(this.blackPiece);

    }

    // allows for the collection of all legal moves for each piece
    private Collection<Move> calcLegalMoves(final Collection<Piece> pieces){
        final List<Move> legalMoves = new ArrayList<>();

        for(final Piece piece : pieces){
            legalMoves.addAll(piece.calcLegalMoves(this));
        }
        return ImmutableList.copyOf(legalMoves);
    }

    public Collection<Piece> calcActivePieces(final List<Tile> gameBoard, final Alliance color){
        final List<Piece> activePieces = new ArrayList<>();
        for(final Tile tile : gameBoard){
            if(tile.isOccupied()){
                final Piece piece = tile.getPiece();
                if(piece.getPiecedAlliance() == color) {
                    activePieces.add(piece);
                }
            }
        }
        return ImmutableList.copyOf(activePieces);
    }
    public Tile getTile(int tileCoord){
        return gameBoard.get(tileCoord);
    }

    // Maps a piece onto a tile where needed, with a specific Tile ID of sorts
    private static List<Tile> createGameBoard(final Builder builder){
        final Tile[] tiles = new Tile[BoardUtility.NUM_TILES];
        for(int i = 0; i < BoardUtility.NUM_TILES; i++){
            tiles[i] = Tile.createTile(i, builder.boardConfig.get(i));
        }
        return ImmutableList.copyOf(tiles);
    }

    // create initial chess position
    public static Board createStandardBoard(){
        final Builder builder = new Builder();
        // Black Pieces
        builder.setPiece(new Rook(0, Alliance.BLACK));
        builder.setPiece(new Knight(1, Alliance.BLACK));
        builder.setPiece(new Bishop(2, Alliance.BLACK));
        builder.setPiece(new Queen(3, Alliance.BLACK));
        builder.setPiece(new King(4, Alliance.BLACK));
        builder.setPiece(new Bishop(5, Alliance.BLACK));
        builder.setPiece(new Knight(6, Alliance.BLACK));
        builder.setPiece(new Rook(7, Alliance.BLACK));
        builder.setPiece(new Pawn(8, Alliance.BLACK));
        builder.setPiece(new Pawn(9, Alliance.BLACK));
        builder.setPiece(new Pawn(10, Alliance.BLACK));
        builder.setPiece(new Pawn(11, Alliance.BLACK));
        builder.setPiece(new Pawn(12, Alliance.BLACK));
        builder.setPiece(new Pawn(13, Alliance.BLACK));
        builder.setPiece(new Pawn(14, Alliance.BLACK));
        builder.setPiece(new Pawn(15, Alliance.BLACK));
        // White Pieces
        builder.setPiece(new Pawn(48, Alliance.WHITE));
        builder.setPiece(new Pawn(49, Alliance.WHITE));
        builder.setPiece(new Pawn(50, Alliance.WHITE));
        builder.setPiece(new Pawn(51, Alliance.WHITE));
        builder.setPiece(new Pawn(52, Alliance.WHITE));
        builder.setPiece(new Pawn(53, Alliance.WHITE));
        builder.setPiece(new Pawn(54, Alliance.WHITE));
        builder.setPiece(new Pawn(55, Alliance.WHITE));
        builder.setPiece(new Rook(56, Alliance.WHITE));
        builder.setPiece(new Knight(57, Alliance.WHITE));
        builder.setPiece(new Bishop(58, Alliance.WHITE));
        builder.setPiece(new Queen(59, Alliance.WHITE));
        builder.setPiece(new King(60, Alliance.WHITE));
        builder.setPiece(new Bishop(61, Alliance.WHITE));
        builder.setPiece(new Knight(62, Alliance.WHITE));
        builder.setPiece(new Rook(63, Alliance.WHITE));
        // White always moves first
        builder.setMoveMaker(Alliance.WHITE);

        return builder.build(); // the board you see in chess initially
    }


    // this class will help us "build" an instance of the pieces onto our tiles, so our game board can have the correct pieces set up
    public static class Builder{

        //builds instance of Board that can be used
        Map<Integer, Piece> boardConfig;
        Alliance nextMoveMaker;

        public Builder(){
            this.boardConfig = new HashMap<>();
        }
        public Builder setPiece(final Piece piece){
            this.boardConfig.put(piece.getPiecePosition(), piece);
            return this;
        }
        public Builder setMoveMaker(final Alliance nextMoveMaker){
            this.nextMoveMaker = nextMoveMaker;
            return this;
        }
        public Board build(){
            return new Board(this);
        }
    }

    @Override
    public String toString(){
        final StringBuilder builder = new StringBuilder();
        for(int i = 0; i < BoardUtility.NUM_TILES; i++){
            final String tileText = this.gameBoard.get(i).toString();
            builder.append(String.format("%3s", tileText));
            if((i + 1) % BoardUtility.NUM_TILES_PER_ROW == 0){
                builder.append("\n");
            }
        }
        return builder.toString();
    }
}
