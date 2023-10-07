import lexer.Lexer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Compiler {
    public static void main(String[] args) {
        createLexer("mycompiler/testfile.txt", "mycompiler/output.txt");
    }

    public static void createLexer(String input, String output){
        try{
            String fileName = input;
            byte[] bytes = Files.readAllBytes(Paths.get(fileName));
            String content = new String(bytes, StandardCharsets.UTF_8);
            Lexer lexer = Lexer.getInstance(content);
            lexer.next();
            lexer.display(output);
        } catch (IOException e){
            System.out.println("fail");
        }
    }
}
