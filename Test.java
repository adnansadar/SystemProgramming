import java.io.*;
import java.util.*;

public class Test {
	public static void main(String[] args) throws IOException {
        FileReader fr = new FileReader("input.txt");
        Scanner sc = new Scanner(fr);
        while (sc.hasNextLine()) {
            System.out.println(sc.nextLine());
        }
    }
}