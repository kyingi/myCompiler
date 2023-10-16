package parser;

import error.PansyException;
import lexer.token.SynType;
import lexer.token.Token;
import parser.node.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Parser {

    private Parser_scd parserScd;

    public Parser(ArrayList<Token> tokens) {
        this.parserScd = new Parser_scd(tokens);
    }

    public CommonNode parse() {
        return parseText();
    }

    public void display(String output) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(output));
        for(int i = 0; i < parserScd.getOuts().size(); i++){
            out.write(parserScd.getOuts().get(i)+"\n");
        }
        out.close();
    }


    private CommonNode parseText() {
        CompUnitNode compUnitNode = new CompUnitNode();
        while (!parserScd.isParseEnd()) {
            if (parserScd.isDeclFirst()) {
                compUnitNode.addCNodes(parseDecl());
            } else if (parserScd.isFuncDef()) {
                compUnitNode.addCNodes(parseFuncDef());
            } else if (parserScd.isMainFuncDef()) {
                compUnitNode.addCNodes(parseMainFuncDef());
            } else {
                System.out.println("wrong CompUnit parse");
                System.exit(1);
            }
        }
        parserScd.addOuts(parserScd.packNode("CompUnit"));
        return compUnitNode;
    }


    private CommonNode parseDecl() {
        CommonNode declNode = new DeclNode();
        if (parserScd.isConstDecl()) {
            declNode.addCNodes(parseConstDecl());
        } else {
            declNode.addCNodes(parseVarDecl());
        }
        return declNode;
    }



    private MainFuncDefNode parseMainFuncDef() {
        MainFuncDefNode mainFuncDefNode = new MainFuncDefNode();

        mainFuncDefNode.addCNodes(parserScd.checkToken(SynType.INTTK));
        mainFuncDefNode.addCNodes(parserScd.checkToken(SynType.MAINTK));
        mainFuncDefNode.addCNodes(parserScd.checkToken(SynType.LPARENT));
        mainFuncDefNode.addCNodes(parserScd.checkToken(SynType.RPARENT));
        mainFuncDefNode.addCNodes(parseBlock());

        parserScd.addOuts(parserScd.packNode("MainFuncDef"));
        return mainFuncDefNode;
    }

    private ConstDeclNode parseConstDecl() {
        ConstDeclNode constDeclNode = new ConstDeclNode();

        constDeclNode.addCNodes(parserScd.checkToken(SynType.CONSTTK));
        constDeclNode.addCNodes(parseBType());
        constDeclNode.addCNodes(parseConstDef());
        while (!parserScd.isParseEnd() && parserScd.isComma()) {
            constDeclNode.addCNodes(parserScd.checkToken(SynType.COMMA));
            constDeclNode.addCNodes(parseConstDef());
        }
        constDeclNode.addCNodes(parserScd.checkToken(SynType.SEMICN));

        parserScd.addOuts(parserScd.packNode("ConstDecl"));
        return constDeclNode;
    }

    private BTypeNode parseBType() {
        BTypeNode bTypeNode = new BTypeNode();
        bTypeNode.addCNodes(parserScd.checkToken(SynType.INTTK));

        return bTypeNode;
    }

    private ConstDefNode parseConstDef() {
        ConstDefNode constDefNode = new ConstDefNode();
        constDefNode.addCNodes(parserScd.checkToken(SynType.IDENFR));
        while (parserScd.isLBrack()) {
            constDefNode.addCNodes(parserScd.checkToken(SynType.LBRACK));
            constDefNode.addCNodes(parseConstExp());
            constDefNode.addCNodes(parserScd.checkToken(SynType.RBRACK));
        }
        constDefNode.addCNodes(parserScd.checkToken(SynType.ASSIGN));
        constDefNode.addCNodes(parseConstInitVal());

        parserScd.addOuts(parserScd.packNode("ConstDef"));
        return constDefNode;
    }


    private ConstInitValNode parseConstInitVal() {
        ConstInitValNode constInitValNode = new ConstInitValNode();
        if (parserScd.isLBrace()) {
            constInitValNode.addCNodes(parserScd.checkToken(SynType.LBRACE));
            if (!parserScd.lookAhead(0).isSameType(SynType.RBRACE)) {
                constInitValNode.addCNodes(parseConstInitVal());
                while (parserScd.isComma()) {
                    constInitValNode.addCNodes(parserScd.checkToken(SynType.COMMA));
                    constInitValNode.addCNodes(parseConstInitVal());
                }
            }
            constInitValNode.addCNodes(parserScd.checkToken(SynType.RBRACE));
        } else {
            constInitValNode.addCNodes(parseConstExp());
        }

        parserScd.addOuts(parserScd.packNode("ConstInitVal"));
        return constInitValNode;
    }

    private ConstExpNode parseConstExp() {
        ConstExpNode constExpNode = new ConstExpNode();
        constExpNode.addCNodes(parseAddExp());

        parserScd.addOuts(parserScd.packNode("ConstExp"));
        return constExpNode;
    }

    private VarDeclNode parseVarDecl() {
        VarDeclNode varDeclNode = new VarDeclNode();
        varDeclNode.addCNodes(parseBType());
        varDeclNode.addCNodes(parseVarDef());

        while (parserScd.isComma()) {
            varDeclNode.addCNodes(parserScd.checkToken(SynType.COMMA));
            varDeclNode.addCNodes(parseVarDef());
        }

        varDeclNode.addCNodes(parserScd.checkToken(SynType.SEMICN));

        parserScd.addOuts(parserScd.packNode("VarDecl"));
        return varDeclNode;
    }

    private VarDefNode parseVarDef() {
        VarDefNode varDefNode = new VarDefNode();
        varDefNode.addCNodes(parserScd.checkToken(SynType.IDENFR));

        while (parserScd.isLBrack()) {
            varDefNode.addCNodes(parserScd.checkToken(SynType.LBRACK));
            varDefNode.addCNodes(parseConstExp());
            varDefNode.addCNodes(parserScd.checkToken(SynType.RBRACK));
        }

        if (parserScd.isAssign()) {
            varDefNode.addCNodes(parserScd.checkToken(SynType.ASSIGN));
            varDefNode.addCNodes(parseInitVal());
        }

        parserScd.addOuts(parserScd.packNode("VarDef"));
        return varDefNode;
    }

    private InitValNode parseInitVal() {
        InitValNode initValNode = new InitValNode();

        if (parserScd.isLBrace()) {
            initValNode.addCNodes(parserScd.checkToken(SynType.LBRACE));
            if (!parserScd.lookAhead(0).isSameType(SynType.RBRACE)) {
                initValNode.addCNodes(parseInitVal());

                while (parserScd.isComma()) {
                    initValNode.addCNodes(parserScd.checkToken(SynType.COMMA));
                    initValNode.addCNodes(parseInitVal());
                }

                initValNode.addCNodes(parserScd.checkToken(SynType.RBRACE));
            }
        } else {
            initValNode.addCNodes(parseExp());
        }

        parserScd.addOuts(parserScd.packNode("InitVal"));
        return initValNode;
    }

    private FuncDefNode parseFuncDef() {
        FuncDefNode funcDefNode = new FuncDefNode();

        funcDefNode.addCNodes(parseFuncType());
        funcDefNode.addCNodes(parserScd.checkToken(SynType.IDENFR));
        funcDefNode.addCNodes(parserScd.checkToken(SynType.LPARENT));

        if (parserScd.isFuncFParamsFirst()) {
            funcDefNode.addCNodes(parseFuncFParams());
        }

        funcDefNode.addCNodes(parserScd.checkToken(SynType.RPARENT));
        funcDefNode.addCNodes(parseBlock());

        parserScd.addOuts(parserScd.packNode("FuncDef"));
        return funcDefNode;
    }

    public FuncTypeNode parseFuncType() {
        FuncTypeNode funcTypeNode = new FuncTypeNode();
        if (parserScd.isIntTk()) {
            funcTypeNode.addCNodes(parserScd.checkToken(SynType.INTTK));
        } else if (parserScd.isVoidTk()) {
            funcTypeNode.addCNodes(parserScd.checkToken(SynType.VOIDTK));
        }

        parserScd.addOuts(parserScd.packNode("FuncType"));
        return funcTypeNode;
    }

    private FuncFParamsNode parseFuncFParams() {
        FuncFParamsNode funcFParamsNode = new FuncFParamsNode();
        funcFParamsNode.addCNodes(parseFuncFParam());

        while (parserScd.isComma()) {
            funcFParamsNode.addCNodes(parserScd.checkToken(SynType.COMMA));
            funcFParamsNode.addCNodes(parseFuncFParam());
        }

        parserScd.addOuts(parserScd.packNode("FuncFParams"));
        return funcFParamsNode;
    }

    private FuncFParamNode parseFuncFParam() {
        FuncFParamNode funcFParamNode = new FuncFParamNode();
        funcFParamNode.addCNodes(parseBType());
        funcFParamNode.addCNodes(parserScd.checkToken(SynType.IDENFR));

        if (parserScd.isLBrack()) {
            funcFParamNode.addCNodes(parserScd.checkToken(SynType.LBRACK));
            funcFParamNode.addCNodes(parserScd.checkToken(SynType.RBRACK));

            while (parserScd.isLBrack()) {
                funcFParamNode.addCNodes(parserScd.checkToken(SynType.LBRACK));
                funcFParamNode.addCNodes(parseConstExp());
                funcFParamNode.addCNodes(parserScd.checkToken(SynType.RBRACK));
            }
        }

        parserScd.addOuts(parserScd.packNode("FuncFParam"));
        return funcFParamNode;
    }

    private BlockNode parseBlock() {
        BlockNode blockNode = new BlockNode();

        blockNode.addCNodes(parserScd.checkToken(SynType.LBRACE));

        while (!parserScd.lookAhead(0).isSameType(SynType.RBRACE)) {
            blockNode.addCNodes(parseBlockItem());
        }

        blockNode.addCNodes(parserScd.checkToken(SynType.RBRACE));

        parserScd.addOuts(parserScd.packNode("Block"));
        return blockNode;
    }

    private BlockItemNode parseBlockItem() {
        BlockItemNode blockItemNode = new BlockItemNode();

        if (parserScd.isDeclFirst()) {
            blockItemNode.addCNodes(parseDecl());
        } else {
            blockItemNode.addCNodes(parseStmt());
        }

        return blockItemNode;
    }


    private StmtNode parseStmt() {
        StmtNode stmtNode = new StmtNode();

        if (parserScd.isLBrace()) {
            stmtNode.addCNodes(parseBlock());
        } else if (parserScd.isIfTK()) {
            stmtNode.addCNodes(parseConditionStmt());
        } else if (parserScd.isForTk()) {
            stmtNode.addCNodes(parseForStmt());
        } else if (parserScd.isBreakTk()) {
            stmtNode.addCNodes(parseBreakStmt());
        } else if (parserScd.isContinueTk()) {
            stmtNode.addCNodes(parseContinueStmt());
        } else if (parserScd.isReturnTk()) {
            stmtNode.addCNodes(parseReturnStmt());
        } else if (parserScd.isPrintfTk()) {
            stmtNode.addCNodes(parseOutStmt());
        } else if (parserScd.isIdentifier()) {
            if(parserScd.lookAhead(1).isSameType(SynType.ASSIGN)){
                if(parserScd.lookAhead(2).isSameType(SynType.GETINTTK)){
                    stmtNode.addCNodes(parseInStmt());
                } else {
                    stmtNode.addCNodes(parseAssignStmt());
                }
            }
        } else {
            stmtNode.addCNodes(parseExpStmt());
        }

        parserScd.addOuts(parserScd.packNode("Stmt"));
        return stmtNode;
    }

    private AssignStmtNode parseAssignStmt() {
        AssignStmtNode assignStmtNode = new AssignStmtNode();

        assignStmtNode.addCNodes(parseLVal());
        assignStmtNode.addCNodes(parserScd.checkToken(SynType.ASSIGN));
        assignStmtNode.addCNodes(parseExp());
        assignStmtNode.addCNodes(parserScd.checkToken(SynType.SEMICN));

        return assignStmtNode;
    }

    private ExpStmtNode parseExpStmt() {
        ExpStmtNode expStmtNode = new ExpStmtNode();

        if (!parserScd.isSemicolon()) {
            expStmtNode.addCNodes(parseExp());
        }

        expStmtNode.addCNodes(parserScd.checkToken(SynType.SEMICN));

        return expStmtNode;
    }

    private InStmtNode parseInStmt() {
        InStmtNode inStmtNode = new InStmtNode();

        inStmtNode.addCNodes(parseLVal());
        inStmtNode.addCNodes(parserScd.checkToken(SynType.ASSIGN));
        inStmtNode.addCNodes(parserScd.checkToken(SynType.GETINTTK));
        inStmtNode.addCNodes(parserScd.checkToken(SynType.LPARENT));
        inStmtNode.addCNodes(parserScd.checkToken(SynType.RPARENT));
        inStmtNode.addCNodes(parserScd.checkToken(SynType.SEMICN));

        return inStmtNode;
    }

    private ConditionStmtNode parseConditionStmt() {
        ConditionStmtNode conditionStmtNode = new ConditionStmtNode();

        conditionStmtNode.addCNodes(parserScd.checkToken(SynType.IFTK));
        conditionStmtNode.addCNodes(parserScd.checkToken(SynType.LPARENT));
        conditionStmtNode.addCNodes(parseCond());
        conditionStmtNode.addCNodes(parserScd.checkToken(SynType.RPARENT));
        conditionStmtNode.addCNodes(parseStmt());

        if (parserScd.isElseTk()) {
            conditionStmtNode.addCNodes(parserScd.checkToken(SynType.ELSETK));
            conditionStmtNode.addCNodes(parseStmt());
        }

        return conditionStmtNode;
    }

    private BreakStmtNode parseBreakStmt() {
        BreakStmtNode breakStmtNode = new BreakStmtNode();

        breakStmtNode.addCNodes(parserScd.checkToken(SynType.BREAKTK));
        breakStmtNode.addCNodes(parserScd.checkToken(SynType.SEMICN));

        return breakStmtNode;
    }

    private ContinueStmtNode parseContinueStmt() {
        ContinueStmtNode continueStmtNode = new ContinueStmtNode();

        continueStmtNode.addCNodes(parserScd.checkToken(SynType.CONTINUETK));
        continueStmtNode.addCNodes(parserScd.checkToken(SynType.SEMICN));

        return continueStmtNode;
    }

    private ReturnStmtNode parseReturnStmt() {
        ReturnStmtNode returnStmtNode = new ReturnStmtNode();

        returnStmtNode.addCNodes(parserScd.checkToken(SynType.RETURNTK));
        if(!parserScd.lookAhead(0).isSameType(SynType.SEMICN)){
            returnStmtNode.addCNodes(parseExp());
        }
        returnStmtNode.addCNodes(parserScd.checkToken(SynType.SEMICN));

        return returnStmtNode;
    }

    private OutStmtNode parseOutStmt() {
        OutStmtNode outStmtNode = new OutStmtNode();

        outStmtNode.addCNodes(parserScd.checkToken(SynType.PRINTFTK));
        outStmtNode.addCNodes(parserScd.checkToken(SynType.LPARENT));
        outStmtNode.addCNodes(parserScd.checkToken(SynType.STRCON));

        while (parserScd.isComma()) {
            outStmtNode.addCNodes(parserScd.checkToken(SynType.COMMA));
            outStmtNode.addCNodes(parseExp());
        }

        outStmtNode.addCNodes(parserScd.checkToken(SynType.RPARENT));
        outStmtNode.addCNodes(parserScd.checkToken(SynType.SEMICN));

        return outStmtNode;
    }

    private ExpNode parseExp() {
        ExpNode expNode = new ExpNode();

        expNode.addCNodes(parseAddExp());

        parserScd.addOuts(parserScd.packNode("Exp"));
        return expNode;
    }

    private CondNode parseCond() {
        CondNode condNode = new CondNode();

        condNode.addCNodes(parseLOrExp());

        parserScd.addOuts(parserScd.packNode("Cond"));
        return condNode;
    }

    private LValNode parseLVal() {
        LValNode lValNode = new LValNode();

        lValNode.addCNodes(parserScd.checkToken(SynType.IDENFR));

        while (parserScd.isLBrack()) {
            lValNode.addCNodes(parserScd.checkToken(SynType.LBRACK));
            lValNode.addCNodes(parseExp());
            lValNode.addCNodes(parserScd.checkToken(SynType.RBRACK));
        }

        parserScd.addOuts(parserScd.packNode("LVal"));
        return lValNode;
    }

    private ForBlockNode parseForBlock(){
        ForBlockNode forBlockNode = new ForBlockNode();

        forBlockNode.addCNodes(parserScd.checkToken(SynType.FORTK));
        forBlockNode.addCNodes(parserScd.checkToken(SynType.LPARENT));
        if(!parserScd.lookAhead(0).isSameType(SynType.SEMICN)){
            forBlockNode.addCNodes(parseForStmt());
        }
        forBlockNode.addCNodes(parserScd.checkToken(SynType.SEMICN));
        if(!parserScd.lookAhead(0).isSameType(SynType.SEMICN)){
            forBlockNode.addCNodes(parseCond());
        }
        forBlockNode.addCNodes(parserScd.checkToken(SynType.SEMICN));
        if(!parserScd.lookAhead(0).isSameType(SynType.RPARENT)){
            forBlockNode.addCNodes(parseForStmt());
        }
        forBlockNode.addCNodes(parserScd.checkToken(SynType.RPARENT));
        forBlockNode.addCNodes(parseStmt());

        return forBlockNode;
    }

    private ForStmtNode parseForStmt(){
        ForStmtNode forStmtNode = new ForStmtNode();

        forStmtNode.addCNodes(parseLVal());
        forStmtNode.addCNodes(parserScd.checkToken(SynType.ASSIGN));
        forStmtNode.addCNodes(parseExp());

        return forStmtNode;
    }

    private LOrExpNode parseLOrExp() {
        LOrExpNode lOrExpNode = new LOrExpNode();
        lOrExpNode.addCNodes(parseLAndExp());
        parserScd.addOuts(parserScd.packNode("LOrExp"));

        while (parserScd.lookAhead(0).isSameType(SynType.OR)) {
            lOrExpNode.addCNodes(parserScd.checkToken(SynType.OR));
            lOrExpNode.addCNodes(parseLAndExp());
            parserScd.addOuts(parserScd.packNode("LOrExp"));
        }

        return lOrExpNode;
    }


    private LAndExpNode parseLAndExp() {
        LAndExpNode lAndExpNode = new LAndExpNode();

        lAndExpNode.addCNodes(parseEqExp());
        parserScd.addOuts(parserScd.packNode("LAndExp"));
        while (parserScd.lookAhead(0).isSameType(SynType.AND)) {
            lAndExpNode.addCNodes(parserScd.checkToken(SynType.AND));
            lAndExpNode.addCNodes(parseEqExp());
            parserScd.addOuts(parserScd.packNode("LAndExp"));
        }

        return lAndExpNode;
    }

    private EqExpNode parseEqExp() {
        EqExpNode eqExpNode = new EqExpNode();

        eqExpNode.addCNodes(parseRelExp());
        parserScd.addOuts(parserScd.packNode("EqExp"));

        while (parserScd.lookAhead(0).isSameType(SynType.EQL) ||
                parserScd.lookAhead(0).isSameType(SynType.NEQ)) {
            eqExpNode.addCNodes(parserScd.checkToken(parserScd.lookAhead(0).getSynType()));
            eqExpNode.addCNodes(parseRelExp());
            parserScd.addOuts(parserScd.packNode("EqExp"));
        }

        return eqExpNode;
    }

    private RelExpNode parseRelExp() {
        RelExpNode relExpNode = new RelExpNode();

        relExpNode.addCNodes(parseAddExp());
        parserScd.addOuts(parserScd.packNode("RelExp"));

        while (parserScd.isRelOp()) {
            relExpNode.addCNodes(parserScd.checkToken(parserScd.lookAhead(0).getSynType()));
            relExpNode.addCNodes(parseAddExp());
            parserScd.addOuts(parserScd.packNode("RelExp"));
        }

        return relExpNode;
    }

    private AddExpNode parseAddExp() {
        AddExpNode addExpNode = new AddExpNode();
        addExpNode.addCNodes(parseMulExp());
        parserScd.addOuts(parserScd.packNode("AddExp"));

        while (parserScd.isAddOp()) {
            addExpNode.addCNodes(parserScd.checkToken(parserScd.lookAhead(0).getSynType()));
            addExpNode.addCNodes(parseMulExp());
            parserScd.addOuts(parserScd.packNode("AddExp"));
        }

        return addExpNode;
    }

    private MulExpNode parseMulExp() {
        MulExpNode mulExpNode = new MulExpNode();
        mulExpNode.addCNodes(parseUnaryExp());
        parserScd.addOuts(parserScd.packNode("MulExp"));

        while (parserScd.isMulOp()) {
            mulExpNode.addCNodes(parserScd.checkToken(parserScd.lookAhead(0).getSynType()));
            mulExpNode.addCNodes(parseUnaryExp());
            parserScd.addOuts(parserScd.packNode("MulExp"));
        }

        return mulExpNode;
    }

    private UnaryExpNode parseUnaryExp() {
        UnaryExpNode unaryExpNode = new UnaryExpNode();

        if (parserScd.isUnaryOp()) {
            unaryExpNode.addCNodes(parseUnaryOp());
            unaryExpNode.addCNodes(parseUnaryExp());
        } else if (parserScd.isCallee()) {
            unaryExpNode.addCNodes(parseCallee());
        } else {
            unaryExpNode.addCNodes(parsePrimaryExp());
        }

        parserScd.addOuts(parserScd.packNode("UnaryExp"));
        return unaryExpNode;
    }

    private UnaryOpNode parseUnaryOp() {
        UnaryOpNode unaryOpNode = new UnaryOpNode();
        unaryOpNode.addCNodes(parserScd.checkToken(parserScd.lookAhead(0).getSynType()));

        parserScd.addOuts(parserScd.packNode("UnaryOp"));
        return unaryOpNode;
    }

    private CalleeNode parseCallee()
    {
        CalleeNode calleeNode = new CalleeNode();

        calleeNode.addCNodes(parserScd.checkToken(SynType.IDENFR));
        calleeNode.addCNodes(parserScd.checkToken(SynType.LPARENT));
        if(!parserScd.lookAhead(0).isSameType(SynType.RPARENT)){
            calleeNode.addCNodes(parseFuncRParams());
        }
        calleeNode.addCNodes(parserScd.checkToken(SynType.RPARENT));

        return calleeNode;
    }

    private FuncRParamsNode parseFuncRParams(){
        FuncRParamsNode funcRParamsNode = new FuncRParamsNode();

        funcRParamsNode.addCNodes(parseExp());

        while (parserScd.isComma())
        {
            funcRParamsNode.addCNodes(parserScd.checkToken(SynType.COMMA));
            funcRParamsNode.addCNodes(parseExp());
        }

        parserScd.addOuts(parserScd.packNode("FuncRParams"));
        return funcRParamsNode;
    }

    private PrimaryExpNode parsePrimaryExp(){
        PrimaryExpNode primaryExpNode = new PrimaryExpNode();

        if (parserScd.isLParent())
        {
            primaryExpNode.addCNodes(parserScd.checkToken(SynType.LPARENT));
            primaryExpNode.addCNodes(parseExp());
            primaryExpNode.addCNodes(parserScd.checkToken(SynType.RPARENT));
        }
        else if (parserScd.isIdentifier())
        {
            primaryExpNode.addCNodes(parseLVal());
        }
        else
        {
            primaryExpNode.addCNodes(parseNumber());
        }

        parserScd.addOuts(parserScd.packNode("PrimaryExp"));
        return primaryExpNode;
    }

    private NumberNode parseNumber() {
        NumberNode numberNode = new NumberNode();

        numberNode.addCNodes(parserScd.checkToken(SynType.INTCON));

        parserScd.addOuts(parserScd.packNode("Number"));
        return numberNode;
    }




}
