package Battle;

// File to run the actual battle between two chess pieces. Once one chess piece faints, the battle has won,
// and we go back to the GUI with it being the winners turn once more. If the battle is lost,
// The opposing team gets to play their turn twice
import java.io.*;
import java.util.*;

public class BattleArena {
    static Battle player1Piece;
    static Battle player2Piece;

    static Scanner scan = new Scanner(System.in);

    // checks if any choice made is valid
    public static int isValid(int range1, int range2, int input){
        if(input > range2){
            return 0;
        }
        if(input < range1){
            return 0;
        } else {
            return input;
        }
    }

    public static int start(){;
        /* This is a method for choosing randomly which piece gets to go first- 50% chance*/
        Random number = new Random();
        int moveChoice = number.nextInt(2);//if 0, user start, if 1, enemy starts
        return moveChoice;
    }


}
