package ChessEngine;

import ChessEngine.player.BlackPlayer;
import ChessEngine.player.Player;
import ChessEngine.player.WhitePlayer;

public enum Alliance {
    // there is only two instance that we need to keep in check: white and black, these can be modified with their behavior
    WHITE{
        public int getDirection(){
            return -1;
        }

        @Override
        public boolean isBlack() {
            return false;
        }

        @Override
        public boolean isWhite() {
            return true;
        }

        @Override
        public Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer) {
            return whitePlayer;
        }

    },
    BLACK{
        public int getDirection(){
            return 1;
        }

        @Override
        public boolean isBlack() {
            return true;
        }

        @Override
        public boolean isWhite() {
            return false;
        }

        @Override
        public Player choosePlayer(final WhitePlayer whitePlayer,
                                   final BlackPlayer blackPlayer) {
            return blackPlayer;
        }
    };

    public abstract int getDirection();
    public abstract boolean isBlack();
    public abstract boolean isWhite();
    public abstract Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer);
}
