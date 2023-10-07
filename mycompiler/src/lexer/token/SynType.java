package lexer.token;

public enum SynType {
    //reserved
    MAINTK,CONSTTK,INTTK,BREAKTK,CONTINUETK,IFTK,ELSETK,FORTK,GETINTTK,PRINTFTK,RETURNTK,VOIDTK,
    //var
    IDENFR,
    //math
    PLUS,MINU,MULT,DIV,MOD,
    LSS,LEQ,GRE,GEQ,
    EQL,NEQ,ASSIGN,
    //int
    INTCON,
    //string
    STRCON,
    //relation
    NOT,AND,OR,
    //
    SEMICN,COMMA,
    LPARENT,RPARENT,LBRACK,RBRACK,LBRACE,RBRACE,

    //comment
    LCOMMENT,BCOMMENTS,BCOMMENTE

}
