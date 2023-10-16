package lexer;

import lexer.token.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.nio.file.Files;

public class Lexer {
    private static Lexer lexerEnt;
    private int pos;
    private String content;
    private ArrayList<SynType> tokenArrayList;

    private ArrayList<String> contentList;

    private HashMap<SynType,String> typeContentMap;
    private ArrayList<Token> tokens;

    private int curLine;
    private String lineContent;

    public Lexer(String content) {
        this.content = content;
        this.curLine = 1;
        this.pos = 0;
        this.tokenArrayList = new ArrayList<>();
        this.contentList = new ArrayList<>();
        this.typeContentMap = new LinkedHashMap<>();
        this.tokens = new ArrayList<>();
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    public void next() {
        while (pos < content.length()) {
            while ((pos < content.length()) && Character.isWhitespace(getPos(pos))) {
                if(getPos(pos) == '\n' && getPos(pos) != '\0'){
                    curLine += 1;
                }
                pos += 1;
            }
            Token token = getContent();
            if(token!=null){
                pos += token.getName().length();
                while ((pos < content.length()) && Character.isWhitespace(getPos(pos))) {
                    if(getPos(pos) == '\n' && getPos(pos) != '\0'){
                        curLine += 1;
                    }
                    pos += 1;
                }
                curLine += token.getName().chars().boxed().filter(c -> c == '\n').count();
            }
        }
    }


    public static Lexer getInstance(String string) {
        if (lexerEnt == null) {
            lexerEnt = new Lexer(string);
        }
        return lexerEnt;
    }

    public boolean ifComment() {
        Pattern pattern;
        Matcher matcher;

        Boolean skip = false;

        String lineComment = "//";
        String blockCommentSt = "/*";

        for (String string : Comment.getTextpattern().values()) {
            pattern = Pattern.compile(string);
            matcher = pattern.matcher(content);
            if (matcher.find(pos) && matcher.start() == pos) {
                if (matcher.group().equals(lineComment)) {
                    pos = MovetoNxtLn(pos + 2);
                    skip = true;
                    break;
                } else if (matcher.group().equals(blockCommentSt)) {
                    pos = MovetoEnd(pos + 2);
                    skip = true;
                    break;
                }
            }
        }
        return skip;
    }

    public Token getContent() {
        Pattern pattern;
        Matcher matcher;
        Token token = new Token();
        int flag = 0;

        outer : if (ifComment()) {
            return null;
        } else {
            //reserved word
            for (String string : ReserveWords.getTextpattern().values()) {
                pattern = Pattern.compile(string);
                matcher = pattern.matcher(content);
                if (matcher.find(pos) && matcher.start() == pos) {
                    this.tokenArrayList.add(ReserveWords.getTextType().get(string));
                    this.contentList.add(matcher.group());
                    this.typeContentMap.put(ReserveWords.getTextType().get(string),matcher.group());
                    token.setText(matcher.group());
                    token.setName(matcher.group());
                    token.setSynType(ReserveWords.getTextType().get(string));
                    token.setLine(curLine);
                    this.tokens.add(token);
                    flag = 1;
                    break;
                }
            }
            if(flag == 1) {
                break outer;
            }



            //Identifer
            pattern = Pattern.compile(Idenfr.PATTERN);
            matcher = pattern.matcher(content);
            if (matcher.find(pos) && matcher.start() == pos) {
                this.tokenArrayList.add(SynType.IDENFR);
                this.contentList.add(matcher.group());
                this.typeContentMap.put(SynType.IDENFR,matcher.group());
                token.setText(matcher.group());
                token.setName(matcher.group());
                token.setSynType(SynType.IDENFR);
                token.setLine(curLine);
                this.tokens.add(token);
                flag = 1;
            }
            if(flag == 1) {
                break outer;
            }



            //string const
            pattern = Pattern.compile(StrCon.PATTERN);
            matcher = pattern.matcher(content);
            if (matcher.find(pos) && matcher.start() == pos) {
                this.tokenArrayList.add(SynType.STRCON);
                this.contentList.add(matcher.group());
                this.typeContentMap.put(SynType.STRCON,matcher.group());
                token.setText(matcher.group());
                token.setName(matcher.group());
                token.setSynType(SynType.STRCON);
                token.setLine(curLine);
                this.tokens.add(token);
                flag = 1;
            }
            if(flag == 1) {
                break outer;
            }

            //int const
            pattern = Pattern.compile(IntConst.PATTERN);
            matcher = pattern.matcher(content);
            if (matcher.find(pos) && matcher.start() == pos) {
                this.tokenArrayList.add(SynType.INTCON);
                this.contentList.add(matcher.group());
                this.typeContentMap.put(SynType.INTCON,matcher.group());
                token.setText(matcher.group());
                token.setName(matcher.group());
                token.setSynType(SynType.INTCON);
                token.setLine(curLine);
                this.tokens.add(token);
                flag = 1;
            }
            if(flag == 1) {
                break outer;
            }
            //math note
            for (String string : MathNote.getTextpattern().values()) {
                pattern = Pattern.compile(string);
                matcher = pattern.matcher(content);
                if (matcher.find(pos) && matcher.start() == pos) {
                    this.tokenArrayList.add(MathNote.getTextType().get(string));
                    this.contentList.add(matcher.group());
                    this.typeContentMap.put(MathNote.getTextType().get(string),matcher.group());
                    token.setText(matcher.group());
                    token.setName(matcher.group());
                    token.setSynType(MathNote.getTextType().get(string));
                    token.setLine(curLine);
                    this.tokens.add(token);
                    flag = 1;
                    break;
                }
            }
            if(flag == 1) {
                break outer;
            }

            //relation note
            for (String string : RelNote.getTextpattern().values()) {
                pattern = Pattern.compile(string);
                matcher = pattern.matcher(content);
                if (matcher.find(pos) && matcher.start() == pos) {
                    this.tokenArrayList.add(RelNote.getTextType().get(string));
                    this.contentList.add(matcher.group());
                    this.typeContentMap.put(RelNote.getTextType().get(string),matcher.group());
                    token.setText(matcher.group());
                    token.setName(matcher.group());
                    token.setSynType(RelNote.getTextType().get(string));
                    token.setLine(curLine);
                    this.tokens.add(token);
                    flag = 1;
                    break;
                }
            }
            if(flag == 1) {
                break outer;
            }


            //Note list
            for (String string : NoteList.getTextpattern().values()) {
                pattern = Pattern.compile(string);
                matcher = pattern.matcher(content);
                if (matcher.find(pos) && matcher.start() == pos) {
                    this.tokenArrayList.add(NoteList.getTextType().get(string));
                    this.contentList.add(matcher.group());
                    this.typeContentMap.put(NoteList.getTextType().get(string),matcher.group());
                    token.setText(matcher.group());
                    token.setName(matcher.group());
                    token.setSynType(NoteList.getTextType().get(string));
                    token.setLine(curLine);
                    this.tokens.add(token);
                    flag = 1;
                    break;
                }
            }
            if(flag == 1) {
                break outer;
            }
        }
        return token;
    }


    public int MovetoNxtLn(int begin) {
        int i = begin;
        while (i < content.length()) {
            if (content.charAt(i) == '\n') {
                i = i + 1;
                return i;
            }
            i++;
        }
        curLine = curLine + 1;
        return i;
    }

    public int MovetoEnd(int begin) {
        int i = begin;
        while (i < content.length()) {
            if (i + 1 < content.length() && content.charAt(i) == '*' && content.charAt(i + 1) == '/') {
                i = i + 2;
                return i;
            } else if (content.charAt(i) == '\n') {
                curLine += 1;
            }
            i++;
        }
        return i + 2;
    }

    public void display(String output) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(output));
        for(int i = 0; i < tokens.size(); i++){
            out.write(tokens.get(i).getSynType().toString() + " " + tokens.get(i).getName()+"\n");
        }
        out.close();
    }

    public char getPos(int pos){
        return content.charAt(pos);
    }
}
