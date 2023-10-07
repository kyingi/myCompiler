package lexer.token;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class ReserveWords extends Token {
    private static final HashMap<String, SynType> textType = new LinkedHashMap<>();
    private static final HashMap<String, String> textpattern = new LinkedHashMap<>();


    protected ReserveWords(int line, String content) {
        super(line, content);
        synType = textType.get(content);
    }

static{
    textType.put("main+(?![a-zA-Z0-9_])",SynType.MAINTK);
    textType.put("const+(?![a-zA-Z0-9_])",SynType.CONSTTK);
    textType.put("int+(?![a-zA-Z0-9_])",SynType.INTTK);
    textType.put("break+(?![a-zA-Z0-9_])",SynType.BREAKTK);
    textType.put("continue+(?![a-zA-Z0-9_])",SynType.CONTINUETK);
    textType.put("if+(?![a-zA-Z0-9_])",SynType.IFTK);
    textType.put("else+(?![a-zA-Z0-9_])",SynType.ELSETK);
    textType.put("for+(?![a-zA-Z0-9_])",SynType.FORTK);
    textType.put("getint+(?![a-zA-Z0-9_])",SynType.GETINTTK);
    textType.put("printf+(?![a-zA-Z0-9_])",SynType.PRINTFTK);
    textType.put("return+(?![a-zA-Z0-9_])",SynType.RETURNTK);
    textType.put("void+(?![a-zA-Z0-9_])",SynType.VOIDTK);

    textpattern.put("main+(?![a-zA-Z0-9_])","main+(?![a-zA-Z0-9_])");
    textpattern.put("const+(?![a-zA-Z0-9_])","const+(?![a-zA-Z0-9_])");
    textpattern.put("int+(?![a-zA-Z0-9_])","int+(?![a-zA-Z0-9_])");
    textpattern.put("break+(?![a-zA-Z0-9_])","break+(?![a-zA-Z0-9_])");
    textpattern.put("continue+(?![a-zA-Z0-9_])","continue+(?![a-zA-Z0-9_])");
    textpattern.put("if+(?![a-zA-Z0-9_])","if+(?![a-zA-Z0-9_])");
    textpattern.put("else+(?![a-zA-Z0-9_])","else+(?![a-zA-Z0-9_])");
    textpattern.put("for+(?![a-zA-Z0-9_])","for+(?![a-zA-Z0-9_])");
    textpattern.put("getint+(?![a-zA-Z0-9_])","getint+(?![a-zA-Z0-9_])");
    textpattern.put("printf+(?![a-zA-Z0-9_])","printf+(?![a-zA-Z0-9_])");
    textpattern.put("return+(?![a-zA-Z0-9_])","return+(?![a-zA-Z0-9_])");
    textpattern.put("void+(?![a-zA-Z0-9_])","void+(?![a-zA-Z0-9_])");




    }

    public static HashMap<String, String> getTextpattern() {
        return textpattern;
    }

    public static HashMap<String, SynType> getTextType() {
        return textType;
    }
}
