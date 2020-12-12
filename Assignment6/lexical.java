
/**
 * lexical
 */
import java.io.*;
import java.util.*;

public class lexical {

    public static void main(String[] args) throws IOException {
        FileReader file;
        Scanner sc1;
        try {
            file = new FileReader("input.c");
            sc1 = new Scanner(file);
            lexicalanalyser(sc1, file);
        } 
        catch (FileNotFoundException e) {
            System.out.println("File Not Found!");
        }

    }

    private static void lexicalanalyser(Scanner sc1, FileReader file) {
        int whitespaces = 0, lines = 0;
        while (sc1.hasNextLine()) {
            lines+=1;
            String line = sc1.nextLine();
            // Header File Check
            if (line.startsWith("#include")) {
                line = line.substring(1, line.length());
                System.out.println("Header File: " + line);
            }
            // Keyword Check
            else if (line.startsWith("int") || line.startsWith("void") || line.startsWith("float")
                    || line.startsWith("char") || line.startsWith("double")) {
                String[] words = line.split(" ");
                whitespaces+= words.length - 1;
                System.out.println("Keyword " + words[0]);
                
                String tempword = words[1];
                int flag = 0;
                // Function Check
                for (char var : tempword.toCharArray()) {
                    if ((var >= 65 && var <= 90) || (var >= 97 && var <= 122) || (var == '(') || (var == ')')) {
                        flag = 0;
                    }
                    else{
                        flag = 1;
                        break;
                    }
                }
                if (flag == 0)
                    System.out.println("Function " + words[1]);
                flag = 0;

                // Identifier Check
                if (line.endsWith(";")) {
                    words[1] = words[1].substring(0, words[1].length()-1);
                    String tempchar[] = words[1].split(",");
                    for (String word : tempchar) {
                        for (char var : word.toCharArray()) {
                            if(Character.isAlphabetic(var))
                                System.out.println("Identifiers "+word);
                        }
                    }
                }
            }

            // Operator/Number/Identifier Check
            if(line.contains("=") || line.contains("+") || line.contains("-")|| line.contains("*")|| line.contains("/")|| line.contains("%")|| line.contains("<<")|| line.contains(">>")|| line.contains("--")|| line.contains("++"))
            {
                String words[] = line.split(" ");
                whitespaces+= words.length - 1;
                
                for (String word : words) {
                    for (char var : word.toCharArray()) {
                        if(Character.isAlphabetic(var))
                            System.out.println("Identifier "+var);
                        if(var=='+' || var=='-' || var=='*' || var=='=' || var=='/' || var=='%' || var=='<' || var=='>' || var=='+')
                            System.out.println("Operator "+var);
                        if(Character.isDigit(var))
                            System.out.println("Number "+var);
                    }
                }
            }
        }
        System.out.println("No of white spaces = "+whitespaces);
        System.out.println("No of new lines = "+lines);
    }
}