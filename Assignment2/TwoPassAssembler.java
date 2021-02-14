import java.io.*;
import java.util.*;

public class TwoPassAssembler {
	public static void main(String[] args) throws IOException {
		HashMap<String, List<Object>> MOT = new HashMap<String, List<Object>>();
		HashMap<String, List<Object>> POT = new HashMap<String, List<Object>>();
		HashMap<String, List<Object>> SymbolTable = new HashMap<String, List<Object>>();
		List<Object> MnemonicList = new ArrayList<Object>();
		List<Object> OpcodeList = new ArrayList<Object>();
		List<Object> OperandsMOTList = new ArrayList<Object>();
		List<Object> LengthMOTList = new ArrayList<Object>();
		List<Object> PseudoList = new ArrayList<Object>();
		List<Object> OperandsPOTList = new ArrayList<Object>();
		List<Object> LengthPOTList = new ArrayList<Object>();
		List<Object> SymbolList = new ArrayList<Object>();
		List<Object> AddressList = new ArrayList<Object>();
		String[] MnemonicArr = { "Add", "Sub", "Mult", "Jmp", "Jneg", "Jpos", "Jz", "Load", "Store", "Read", "Write",
				"Stop" };
		for (String string : MnemonicArr) {
			MnemonicList.add(string);
		}
		for (int i = 1; i <= 12; i++) {
			OpcodeList.add(i);
		}
		for (int i = 1; i <= 11; i++) {
			OperandsMOTList.add(1);
		}
		OperandsMOTList.add(0);
		for (int i = 1; i <= 11; i++) {
			LengthMOTList.add(2);
		}
		LengthMOTList.add(1);

		String[] PseudoArr = { "Db", "Dw", "Org", "ENDP", "Const", "End" };
		for (String string : PseudoArr) {
			PseudoList.add(string);
		}
		for (int i = 0; i < 5; i++) {
			if (i < 2)
				OperandsPOTList.add(2);
			else
				OperandsPOTList.add(1);
		}
		OperandsPOTList.add(0);
		for (int i = 0; i < 6; i++) {
			LengthPOTList.add(1);
		}
		MOT.put("Mnemonic", MnemonicList);
		MOT.put("Opcode", OpcodeList);
		MOT.put("No_operands", OperandsMOTList);
		MOT.put("Length", LengthMOTList);
		POT.put("Pseudo", PseudoList);
		POT.put("No_operands", OperandsPOTList);
		POT.put("Length", LengthPOTList);
		SymbolTable.put("Symbol", SymbolList);
		SymbolTable.put("Address", AddressList);
		// System.out.println("SymbolTable:");
		// System.out.println(SymbolTable);
		// System.out.println("MOT:");
		// System.out.println(MOT);
		// System.out.println("POT:");
		// System.out.println(POT);
		int loc_counter = 0, temp = 0, temp1 = 0, temp2 = 0, index = 0, flag = 0, idx = 0, address = 0, temp3 = 0;
		Scanner sc = new Scanner("input.txt");
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			String strings[] = line.split(" ");
			for (String word : strings) {
				for (Object var : MnemonicList) {
					if (var == word) {
						index = MnemonicList.indexOf(word);
						loc_counter += (int) POT.get("Length").get(index);
						flag = 0;
						break;
					}
				}
				if (flag == 0) {
					flag = 1;
					continue;
				} else if ((flag == 1 && word.equals("Loop:")) || (flag == 1 && word.equals("Outer"))) {
					temp2 = 1;
					for (Object var : SymbolList) {
						if (var == word) {
							temp2 = 0;
							break;
						}
					}
					if (temp2 == 1) {
						SymbolList.add(word);
						idx = SymbolList.indexOf(word);
						AddressList.add(loc_counter);
					}
				} else if (flag == 1 && word != "Endp" && word != "Outer" && word != "Loop") {
					temp1 = 1;
					for (Object var : SymbolList) {
						if (var == word) {
							temp1 = 0;
							break;
						}
					}
					if (temp1 == 1) {
						SymbolList.add(word);
						AddressList.add(0);
					}
				} else if (word.equals("Endp")) {
					temp = 1;
					address = loc_counter;
					System.out.println("After First Pass Structure of Symbol Table");
					SymbolTable.put("Symbol", SymbolList);
					SymbolTable.put("Address", AddressList);
					System.out.print(SymbolTable);
					break;
				}
			}
			if (temp == 1) {
				while (sc.hasNextLine()) {
					line = sc.nextLine();
					String strings2[] = line.split("");
					for (String word : strings2) {
						for (Object var : SymbolList) {
							if (word.equals(var)) {
								temp3 = 0;
								index = SymbolList.indexOf(word);
								break;
							} else {
								temp3 = 1;
							}
						}
						if (temp3 == 0) {
							continue;
						} else {
							for (Object var : PseudoList) {
								if (word.equals(var)) {
									idx = PseudoList.indexOf(word);
									AddressList.add(index, address);
									address += (int) LengthPOTList.get(idx);
								}
							}
						}
					}
					line = sc.nextLine();
				}
				System.out.println("After Second Pass Structure of Symbol Table:");
				System.out.println(SymbolTable);
				break;
			} else {
				line = sc.nextLine();
			}

		}
		sc.close();
		System.out.println("Target Program:");
		Scanner sc2 = new Scanner("input");
		temp1 = 0;
		while (sc2.hasNextLine()) {
			String line = sc2.nextLine();
			String[] strings = line.split(" ");
			for (String word : strings) {
				if (word.equals("Endp")) {
					temp1 = 1;
					break;
				}
				for (Object var : MnemonicList) {
					if (var.equals(word)) {
						flag = 1;
						index = MnemonicList.indexOf(word);
						System.out.println(OpcodeList.get(index));
						break;
					}
				}
				if (flag == 1) {
					flag = 0;
					continue;
				} else if (flag == 0) {
					for (Object var : SymbolList) {
						if (word.equals(var) || word.equals("Outer") || word.equals("Loop")) {
							if (word.equals("Outer")) {
								word = word.replace("Outer", "Outer:");
							}
						} else if (word.equals("Loop")) {
							word = word.replace("Loop", "Loop:");
						} else if (word.equals("Outer:") || word.equals("Loop:")) {
							continue;
						}
						idx = SymbolList.indexOf(word);
						System.out.println(AddressList.get(idx));
						break;
					}
				}
			}
			if (temp1 == 1) {
				break;
			}
			System.out.println();
			line = sc2.nextLine();
		}
		sc2.close();
	}
}
