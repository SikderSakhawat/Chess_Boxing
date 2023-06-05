package Battle;

import ChessEngine.player.BlackPlayer;
import ChessEngine.player.WhitePlayer;

import javax.swing.*;

public class BattleDisplay {
    public static void main(String[] args) {
        JFrame window = new JFrame();

        // whenever we establish the execute method within Move.java, we must call this class with the appropriate
        // chess pieces that can battle;
        // there must be both a black and white piece on the board
        /*public BattleDisplay(WhitePlayer whitePlayer, BlackPlayer blackPlayer){
        }*/
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Chess Battle!");
        window.setVisible(true);
    }


}
