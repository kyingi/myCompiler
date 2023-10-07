package lexer.token;

import java.util.HashMap;

public class IntConst extends Token {

    public static final String PATTERN = "([1-9][0-9]*)|(0)";

    private static final HashMap<String, SynType> textType = new HashMap<>();
    private static final HashMap<String, String> textpattern = new HashMap<>();
    protected IntConst(int line, String content) {
        super(line, content);
        synType = SynType.INTCON;
    }

    static {
        textType.put(getText(), SynType.INTCON);
        textpattern.put(getText(),PATTERN);
    }
}
