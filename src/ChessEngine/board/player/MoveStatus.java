package ChessEngine.board.player;

public enum MoveStatus {
    DONE{
        boolean isDone(){
            return true;
        }
    },
    ILLEGAL_MOVE{
        boolean isDone(){
            return false;
        }
    },
    LEAVES_PLAYER_IN_CHECK{
        @Override
        boolean isDone() {
            return false;
        }
    };
    abstract boolean isDone();
}
