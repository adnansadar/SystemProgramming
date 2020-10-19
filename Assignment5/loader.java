import java.io.*;
import java.util.*;
/**
 * loader
 */
public class loader {
    public static void main(String[] args)throws IOException {
        FileReader file1 = new FileReader("file1.txt");
        Scanner inputsc = new Scanner(System.in);
        System.out.println("Enter the memory size: ");
        int size = inputsc.nextInt();
        Scanner sc1 = new Scanner(file1);
        int count = 0;
        System.out.println("File 1 loaded from: "+count);
        while (sc1.hasNext()) {
            String word = sc1.next();
            for (int i = 0; i < word.length(); i++) 
                count++;
        }
        System.out.println("File 1 ends at: " + count);
        System.out.println("File 2 loaded from: " + (count+1));
        FileReader file2 = new FileReader("file2.txt");
        Scanner sc2 = new Scanner(file2);
        while (sc2.hasNext()) {
            String word = sc2.next();
            for (int i = 0; i < word.length(); i++)
                count++;
        }
        System.out.println("File 2 ends at: " + count);

        if(count>size)
            System.out.println("Insufficient memory!");
        else
            System.out.println("Program loaded successfully.\nRemaining Memory:"+(size-count));
            
    }  
}