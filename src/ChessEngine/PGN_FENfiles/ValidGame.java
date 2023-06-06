package ChessEngine.PGN_FENfiles;

import java.util.List;

public class ValidGame
        extends Game {

    public ValidGame(final PGNTag tags,
                     List<String> moves,
                     final String outcome) {
        super(tags, moves, outcome);
    }

    @Override
    public boolean isValid() {
        return true;
    }

}
