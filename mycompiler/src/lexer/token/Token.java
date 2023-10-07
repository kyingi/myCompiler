package lexer.token;

import java.util.HashMap;

public class Token {
    private int line;
    private static String text;
    protected SynType synType;
    private String name;

    protected Token(int line, String content) {
        this.line = line;
        this.text = content;
    }

    public Token(){

    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getLine() {
        return line;
    }

    public static String getText() {
        return text;
    }

    public SynType getSynType() {
        return synType;
    }

    public void setText(String text) {
        Token.text = text;
    }

    public void setSynType(SynType synType) {
        this.synType = synType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return synType.toString() + " " + text;
    }
}
