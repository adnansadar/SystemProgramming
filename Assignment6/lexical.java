
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
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found!");
        }

    }

    private static void lexicalanalyser(Scanner sc1, FileReader file) {
        int whitespaces = 0, lines = 0;
        while (sc1.hasNextLine()) {
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
                    for (String var : tempchar) {
                        System.out.println("Identifiers "+var);
                }
                }
            }

            // Operator Check
            if(line.contains("=") || line.contains("+") || line.contains("-")|| line.contains("*")|| line.contains("/")|| line.contains("%")|| line.contains("<<")|| line.contains(">>")|| line.contains("<")|| line.contains(">")|| line.contains("--")|| line.contains("++"))
            {
                String words[] = line.split(" ");
                for (String word : words) {
                    if((word >= 65 && word <= 90) || (word >= 97 && word <= 122))
                }
            }

        }
    }

}