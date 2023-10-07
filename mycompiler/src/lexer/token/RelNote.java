package lexer.token;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class RelNote extends Token{

    private static final HashMap<String, SynType> textType = new LinkedHashMap<>();
    private static final HashMap<String, String> textpattern = new LinkedHashMap<>();
    protected RelNote(int line, String content) {
        super(line, content);
        synType = textType.get(content);
    }

    static {
        textType.put("!",SynType.NOT);
        textType.put("&&", SynType.AND);
        textType.put("\\|\\|",SynType.OR);

        textpattern.put("!","!");
        textpattern.put("&&","&&");
        textpattern.put("||","\\|\\|");
    }

    public static HashMap<String, String> getTextpattern() {
        return textpattern;
    }

    public static HashMap<String, SynType> getTextType() {
        return textType;
    }
}
