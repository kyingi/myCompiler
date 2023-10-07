package lexer.token;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class MathNote extends Token{
    private static final HashMap<String, SynType> textType = new LinkedHashMap<>();
    private static final HashMap<String, String> textpattern = new LinkedHashMap<>();

    protected MathNote(int line, String content) {
        super(line, content);
        synType = textType.get(content);
    }

    static{
        textType.put("\\+", SynType.PLUS);
        textType.put("-", SynType.MINU);
        textType.put("\\*", SynType.MULT);
        textType.put("/", SynType.DIV);
        textType.put("%", SynType.MOD);
        textType.put("<=", SynType.LEQ);
        textType.put("<", SynType.LSS);
        textType.put(">=", SynType.GEQ);
        textType.put(">", SynType.GRE);
        textType.put("==", SynType.EQL);
        textType.put("!=", SynType.NEQ);
        textType.put("=", SynType.ASSIGN);

        textpattern.put("+", "\\+");
        textpattern.put("-", "-");
        textpattern.put("*", "\\*");
        textpattern.put("/", "/");
        textpattern.put("%", "%");
        textpattern.put("<=", "<=");
        textpattern.put("<", "<");
        textpattern.put(">=", ">=");
        textpattern.put(">", ">");
        textpattern.put("==", "==");
        textpattern.put("!=", "!=");
        textpattern.put("=", "=");
    }

    public static HashMap<String, SynType> getTextType() {
        return textType;
    }

    public static HashMap<String, String> getTextpattern() {
        return textpattern;
    }
}
