package lexer.token;

import java.util.HashMap;

public class StrCon extends Token{

    public static final String PATTERN;
    private static final HashMap<String, SynType> textType = new HashMap<>();
    private static final HashMap<String, String> textpattern = new HashMap<>();
    protected StrCon(int line, String content) {
        super(line, content);
        synType = SynType.STRCON;
    }
    static {
        PATTERN = "\"([\\x20\\x21\\x28-\\x5b\\x5d-\\x7e]|(\\\\n)|(%d))*?\"";
        textType.put(getText(),SynType.STRCON);
        textpattern.put(getText(),PATTERN);
    }

}
