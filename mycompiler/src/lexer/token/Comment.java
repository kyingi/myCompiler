package lexer.token;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Comment extends Token{
    private static final HashMap<String, SynType> textType = new LinkedHashMap<>();

    private static final HashMap<String, String> textpattern = new LinkedHashMap<>();

    protected Comment(int line, String content) {
        super(line, content);
    }

    static {
        textType.put("\\/\\/",SynType.LCOMMENT);
        textType.put("\\/\\*",SynType.BCOMMENTS);
        textType.put("\\*\\/",SynType.BCOMMENTE);

        textpattern.put("//","\\/\\/");
        textpattern.put("/*","\\/\\*");
        textpattern.put("*/","\\*\\/");
    }

    public static HashMap<String, SynType> getTextType() {
        return textType;
    }

    public static HashMap<String, String> getTextpattern() {
        return textpattern;
    }
}
