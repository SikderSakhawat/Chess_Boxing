package ChessEngine.board;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

public class BoardUtility {

    public static final boolean[] FIRST_COLUMN = initColumn(0);
    public static final boolean[] SECOND_COLUMN = initColumn(1);
    public static final boolean[] SEVENTH_COLUMN = initColumn(6);
    public static final boolean[] EIGHTH_COLUMN = initColumn(7);

    public static final boolean[] EIGHTH_RANK = initRow(0);
    public static final boolean[] SEVENTH_RANK = initRow(8);
    public static final boolean[] SIXTH_RANK = initRow(16);
    public static final boolean[] FIFTH_RANK = initRow(24);
    public static final boolean[] FOURTH_RANK = initRow(32);
    public static final boolean[] THIRD_RANK = initRow(40);
    public static final boolean[] SECOND_RANK = initRow(48);
    public static final boolean[] FIRST_RANK = initRow(56);

    public static final String[] ALGEBRAIC_NOTATION = initAlgebraicNotation();
    public static final Map<String, Integer> POSITION_TO_COORD = initPosToCoordMap();

    public static final int NUM_TILES = 64;
    public static final int NUM_TILES_PER_ROW = 8;

    private BoardUtility(){
        throw new RuntimeException("Cannot instantiate!");
    }
    public static boolean isValidTileCoord(final int coord) {
        return coord >= 0 && coord < NUM_TILES;
    }

    private static boolean[] initColumn(int colNum){
        final boolean[] column = new boolean[NUM_TILES];
        do{
            column[colNum] = true;
            colNum += NUM_TILES_PER_ROW;
        } while (colNum < NUM_TILES);
        return column;
    }

    public static boolean[] initRow (int rowNum){
        final boolean[] row = new boolean[NUM_TILES];
        do{
            row[rowNum] = true;
            rowNum++;
        } while (rowNum % NUM_TILES_PER_ROW != 0);
        return row;
    }

    public static int getCoordAtPos(final String position){
       return POSITION_TO_COORD.get(position);
    }

    public static String getPosAtCoord(final int coordinate){
        return ALGEBRAIC_NOTATION[coordinate];
    }
    private static String[] initAlgebraicNotation() {
        String[] finalArray = new String[64];
        int[] numbers = {8, 7, 6, 5, 4, 3, 2, 1};
        String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h"};
        int count = 0;
        for(int x : numbers) {
            for (String y : letters) {
                finalArray[count] = y + x;
                count++;
            }
        }
        return finalArray;
    }

    public static Map<String, Integer> initPosToCoordMap(){
        final Map<String, Integer> posToCoord = new HashMap<>();
        for(int i = 0; i < NUM_TILES; i++){
            posToCoord.put(ALGEBRAIC_NOTATION[i],i);
        }
        return ImmutableMap.copyOf(posToCoord);
    }
}