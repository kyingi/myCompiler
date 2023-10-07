package lexer.token;

import java.util.HashMap;

public class Idenfr extends Token{
    public static final String PATTERN;
    private static final HashMap<String, SynType> textType = new HashMap<>();
    private static final HashMap<String, String> textpattern = new HashMap<>();


    protected Idenfr(int line, String content) {
        super(line, content);
        synType = SynType.IDENFR;
    }

    static {
        PATTERN = "[_A-Za-z][_A-Za-z0-9]*";
        textType.put(getText(),SynType.IDENFR);
        textpattern.put(getText(),PATTERN);
    }
}
