package ChessEngine.player;

import ChessEngine.board.Board;

import java.util.concurrent.CompletableFuture;

public class MoveTransition {
    private final Board transBoard;
    private final Move move;
    private final MoveStatus moveStatus;
    public CompletableFuture<Object> getMoveStatus;

    public MoveTransition(final Board transBoard, final Move move, final MoveStatus moveStatus){
        this.transBoard = transBoard;
        this.move = move;
        this.moveStatus = moveStatus;
    }

    public MoveStatus getMoveStatus(){
        return this.moveStatus;
    }

}
