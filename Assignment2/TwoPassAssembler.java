import java.io.*;
import java.util.*;

public class TwoPassAssembler {
	public static void main(String[] args) throws IOException {
		HashMap<String, String[]> MOT = new HashMap<String, String[]>();
		HashMap<String, Object[]> POT = new HashMap<String, Object[]>();
		HashMap<String, Object[]> ST = new HashMap<String, Object[]>();
		MOT.put("Mnemonic", new String[] { "Add", "Sub", "Mult", "Jmp", "Jneg", "Jpos", "Jz", "Load", "Store", "Read",
				"Write", "Stop" });
		POT.put("Pseudo", new Object[] { "Db", "Dw", "Org", "ENDP", "Const", "End" });
		POT.put("No_operands", new Object[] { 2, 2, 1, 1, 1, 0 });
		POT.put("Length", new Object[] { 1, 1, 1, 1, 1, 1 });
		ST.put("Symbol", new Object[] {});
		ST.put("Address", new Object[] {});
		FileReader fr = new FileReader("input.txt");
		int loc_counter = 0;
		int temp = 0;
		Scanner sc = new Scanner(fr);
		while (sc.hasNext()) {
			String word = sc.next();
			for (String i : MOT) {

			}
		}
	}
}