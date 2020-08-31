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
		FileReader fr = new FileReader("input.txt");
		int loc_counter = 0,temp=0,temp1=0,temp2=0,index=0,flag=0,idx=0;
		Scanner sc = new Scanner(fr);
		while (sc.hasNext()) {
			String word = sc.next();
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
			} 
			else if ((flag == 1 && word.equals("Loop:")) || (flag == 1 && word.equals("Outer"))) {
				temp2 = 1;
				for (Object var : SymbolList) {
					if (var == word) {
						temp2 = 0;
						break;
					}
				}
				if (temp2 == 1)
				{
					SymbolList.add(word);
					idx = SymbolList.indexOf(word);
					AddressList.add(loc_counter);
				}
			}
			else if(flag == 1 && word != "Endp" && word != "Outer" && word!="Loop")
			{
				temp1 = 1;
				
			}
		}
		sc.close();
	}
}
