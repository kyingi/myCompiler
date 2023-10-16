package parser.node;

import lexer.token.SynType;
import lexer.token.Token;

public class TokenNode extends CommonNode{
    private final Token token;


    public TokenNode(Token token) {
        this.token = token;
    }

    public int getLine()
    {
        return token.getLine();
    }

    public String getText()
    {
        return token.getText();
    }

    public boolean isSameType(SynType type)
    {
        return token.isSameType(type);
    }

    public Token getToken()
    {
        return token;
    }
}
