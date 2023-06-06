package ChessEngine.PGN_FENfiles;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

public class PGNTag {

    private final Map<String,String> gameTags;

    private PGNTag(final TagsBuilder builder) {
        this.gameTags = ImmutableMap.copyOf(builder.gameTags);
    }

    @Override
    public String toString() {
        return this.gameTags.toString();
    }

    public static class TagsBuilder {

        final Map<String,String> gameTags;

        public TagsBuilder() {
            this.gameTags = new HashMap<>();
        }

        public TagsBuilder addTag(final String tagKey,
                                  final String tagValue) {
            this.gameTags.put(tagKey, tagValue);
            return this;
        }

        public PGNTag build() {
            return new PGNTag(this);
        }

    }

}
