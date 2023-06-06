package ChessEngine.PGN_FENfiles;

public class GameFactory {

    public static Game createGame(final PGNTag tags,
                                  final String gameText,
                                  final String outcome) {
        try {
            return new ValidGame(tags, PGNUtil.processMoveText(gameText), outcome);
        } catch(final ParsingException e) {
            return new InvalidGame(tags, gameText, outcome);
        }
    }
}
