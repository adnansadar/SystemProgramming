import java.io.*;
import java.util.*;

public class SymbolTable {
	public static void main(String[] args) throws IOException {
		FileReader file = new FileReader("input.c");
		HashMap<String, List<Object>> ST = new HashMap<String, List<Object>>();
		List<Object> LiteralList = new ArrayList<Object>();
		List<Object> SizeList = new ArrayList<Object>();
		List<Object> AddressList = new ArrayList<Object>();
		Scanner sc = new Scanner(file);
		int address = 0;
		String stringnext = "";
		// read from FileReader till the end of file
		// Filling the Symbol Table
		while (sc.hasNext()) {
			String string = sc.next();
			stringnext = sc.next();
			if (string.equals("int")) {
				stringnext = stringnext.substring(0, stringnext.length() - 1);// have to reassign as strings are
																				// immutable.
				// It changes the string size.
				for (String c : stringnext.split(",")) // extracting individual variables of the data type
				{
					LiteralList.add(c);
					SizeList.add("2");
					AddressList.add(address);
					address += 2;
				}
			}
			if (string.equals("float")) {
				stringnext = stringnext.substring(0, stringnext.length() - 1);// have to reassign as strings are
																				// immutable.
				// It changes the string size.
				for (String c : stringnext.split(",")) // extracting individual variables of the data type
				{
					LiteralList.add(c);
					SizeList.add("2");
					AddressList.add(address);
					address += 4;
				}
			}
			if (string.equals("char")) {
				for (String c : stringnext.split(";")) {
					// stringnext=stringnext.substring(0,stringnext.length()-1);
					LiteralList.add(c);
					SizeList.add("2");
					AddressList.add(address);
					address += 1;
				}
			}
			if (string.endsWith(":")) {
				LiteralList.add(string.substring(0, string.length() - 1));
			}
		}

		// Printing the Symbol Table
		ST.put("Literal", LiteralList);
		ST.put("Size", SizeList);
		ST.put("Address", AddressList);
		System.out.println("Literal\t\tSize\t\tAddress");
		Iterator<Object> itr2 = LiteralList.iterator();
		Iterator<Object> itr3 = SizeList.iterator();
		Iterator<Object> itr4 = AddressList.iterator();
		while (itr2.hasNext() && itr3.hasNext() && itr4.hasNext()) {
			System.out.println(itr2.next() + "\t\t" + itr3.next() + "\t\t" + itr4.next());
		}
		while (itr2.hasNext()) {
			System.out.println(itr2.next());
		}
		sc.close();
	}
}