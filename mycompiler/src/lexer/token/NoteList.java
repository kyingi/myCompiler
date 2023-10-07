package lexer.token;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class NoteList extends Token{
    private static final HashMap<String, SynType> textType = new LinkedHashMap<>();
    private static final HashMap<String, String> textpattern = new LinkedHashMap<>();
    protected NoteList(int line, String content) {
        super(line, content);
        synType = textType.get(content);
    }

    static {
        textType.put(",",SynType.COMMA);
        textType.put(";",SynType.SEMICN);
        textType.put("\\(",SynType.LPARENT);
        textType.put("\\)",SynType.RPARENT);
        textType.put("\\[",SynType.LBRACK);
        textType.put("]",SynType.RBRACK);
        textType.put("\\{",SynType.LBRACE);
        textType.put("}",SynType.RBRACE);

        textpattern.put(",",",");
        textpattern.put(";",";");
        textpattern.put("(","\\(");
        textpattern.put(")","\\)");
        textpattern.put("[","\\[");
        textpattern.put("]","]");
        textpattern.put("{","\\{");
        textpattern.put("}","}");
    }

    public static HashMap<String, SynType> getTextType() {
        return textType;
    }

    public static HashMap<String, String> getTextpattern() {
        return textpattern;
    }
}
