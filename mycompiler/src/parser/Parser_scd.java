package parser;

import lexer.token.SynType;
import lexer.token.Token;
import parser.node.CommonNode;
import parser.node.TokenNode;

import java.util.ArrayList;

public class Parser_scd {

    private ArrayList<String> outs;
    private int pos;
    private ArrayList<Token> tokens;

    public Parser_scd(ArrayList<Token> tokens)
    {
        this.tokens = tokens;
        this.pos = 0;
        this.outs = new ArrayList<>();
    }

    public Parser_scd(Parser_scd parserScd)
    {
        this.tokens = new ArrayList<>(parserScd.tokens);
        this.pos = parserScd.pos;
        this.outs = new ArrayList<>(parserScd.outs);
    }




    public void addOuts(String s) {
        this.outs.add(s);
    }

    public String packNode(String s) {
        return "<" + s + ">";
    }

    public Token lookAhead(int step) {
        if (pos + step < tokens.size() && pos + step >= 0) {
            return tokens.get(pos + step);
        }
        return null;
    }

    public void advance(int step) {
        pos += step;
    }

    public ArrayList<String> getOuts() {
        return outs;
    }

    public CommonNode checkToken(SynType type) {
        Token token = lookAhead(0);
        if (!token.isSameType(type)) {
            System.out.println("wrong!");
        }
        // 前进 tokens 指针
        outs.add(token.getSynType().toString() + " " + token.getName());
        advance(1);
        return new TokenNode(token);
    }

    public boolean isDecl() {
        return isConstDecl() || isVarDecl();
    }

    public boolean isConstDecl() {
        return lookAhead(0).isSameType(SynType.CONSTTK);
    }

    public boolean isVarDecl() {
        return lookAhead(0).isSameType(SynType.INTTK) && !lookAhead(2).isSameType(SynType.LPARENT);
    }


    public boolean isParseEnd() {
        return pos >= tokens.size();
    }

    public boolean isDeclFirst() {
        return isConstDecl() || isVarDecl();
    }

    public boolean isFuncDef() {
        return isFuncType() && lookAhead(2).isSameType(SynType.LPARENT) && !lookAhead(1).isSameType(SynType.MAINTK);
    }

    public boolean isFuncType() {
        return lookAhead(0).isSameType(SynType.VOIDTK) || lookAhead(0).isSameType(SynType.INTTK);
    }

    public boolean isMainFuncDef() {
        return lookAhead(0).isSameType(SynType.INTTK) && lookAhead(1).isSameType(SynType.MAINTK) && lookAhead(2).isSameType(SynType.LPARENT);
    }


    public boolean isComma() {
        return lookAhead(0).isSameType(SynType.COMMA);
    }

    boolean isLBrack() {
        return lookAhead(0).isSameType(SynType.LBRACK);
    }

    public boolean isLBrace() {
        return lookAhead(0).isSameType(SynType.LBRACE);
    }

    public boolean isAssign() {
        return lookAhead(0).isSameType(SynType.ASSIGN);
    }

    public boolean isFuncFParamsFirst() {
        return lookAhead(0).isSameType(SynType.INTTK);
    }

    public boolean isIntTk() {
        return lookAhead(0).isSameType(SynType.INTTK);
    }

    public boolean isVoidTk() {
        return lookAhead(0).isSameType(SynType.VOIDTK);
    }

    public boolean isIfTK() {
        return lookAhead(0).isSameType(SynType.IFTK);
    }

    public boolean isForTk() {
        return lookAhead(0).isSameType(SynType.FORTK);
    }

    public boolean isBreakTk() {
        return lookAhead(0).isSameType(SynType.BREAKTK);
    }

    public boolean isContinueTk() {
        return lookAhead(0).isSameType(SynType.CONTINUETK);
    }

    public boolean isReturnTk() {
        return lookAhead(0).isSameType(SynType.RETURNTK);
    }

    public boolean isPrintfTk() {
        return lookAhead(0).isSameType(SynType.PRINTFTK);
    }

    public boolean isSemicolon() {
        return lookAhead(0).isSameType(SynType.SEMICN);
    }

    public boolean isElseTk() {
        return lookAhead(0).isSameType(SynType.ELSETK);
    }

    public boolean isRelOp() {
        return lookAhead(0).isSameType(SynType.LEQ) || lookAhead(0).isSameType(SynType.GEQ) ||
                lookAhead(0).isSameType(SynType.LSS) || lookAhead(0).isSameType(SynType.GRE);
    }

    public boolean isAddOp() {
        return lookAhead(0).isSameType(SynType.PLUS) || lookAhead(0).isSameType(SynType.MINU);
    }

    public boolean isMulOp() {
        return lookAhead(0).isSameType(SynType.MULT) ||
                lookAhead(0).isSameType(SynType.DIV) ||
                lookAhead(0).isSameType(SynType.MOD);
    }

    public boolean isUnaryOp() {
        return lookAhead(0).isSameType(SynType.PLUS) ||
                lookAhead(0).isSameType(SynType.MINU) ||
                lookAhead(0).isSameType(SynType.NOT);
    }

    public boolean isCallee() {
        return lookAhead(0).isSameType(SynType.IDENFR) && lookAhead(1).isSameType(SynType.LPARENT);
    }

    public boolean isIdentifier()
    {
        return lookAhead(0).isSameType(SynType.IDENFR);
    }

    public boolean isLParent()
    {
        return lookAhead(0).isSameType(SynType.LPARENT);
    }
}
