import java.util.*;
import java.io.*;

/**
 * macroprocessor
 */
public class macroprocessor {
    public static void main(String[] args) throws IOException {
        FileReader fr = new FileReader("input.c");
        Scanner sc = new Scanner(fr);
        HashMap<String, List<Object>> MNT = new HashMap<String, List<Object>>();
        HashMap<String, List<Object>> MDT = new HashMap<String, List<Object>>();
        HashMap<String, List<Object>> ALA = new HashMap<String, List<Object>>();
        List<Object> MNTIndexList = new ArrayList<Object>();
        List<Object> MacroNameList = new ArrayList<Object>();

        List<Object> FormalArgsList = new ArrayList<Object>();
        List<Object> PositionalArgsList = new ArrayList<Object>();
        List<Object> ActualArgsList = new ArrayList<Object>();

        List<Object> MDTIndexList = new ArrayList<Object>();
        List<Object> MDTCardList = new ArrayList<Object>();

        int MNTindex = 1;
        int MDTindex = 1;
        // First Pass of Macroprocessor
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] words = line.split(" ");
            if (line.startsWith("MACRO")) {
                MNTIndexList.add(MNTindex);
                MNTindex++;
                MacroNameList.add(sc.next());
                MDTIndexList.add(MDTindex);
                int position = 1;
                for (String word : words) {
                    if (word.equals("MACRO")) {
                        MNTIndexList.add(MNTindex);
                        MNTindex++;
                        MacroNameList.add(sc.next());
                    } else if (word.startsWith("&")) {
                        FormalArgsList.add(word);
                        PositionalArgsList.add("#" + position);
                        position++;
                    }
                }
                while (!line.startsWith("MEND")) {
                    line = sc.nextLine();
                    MDTIndexList.add(MDTindex);
                    MDTindex++;
                    MDTCardList.add(line);
                }
            } 
        }//End of First Pass
        MNT.put("Index", MNTIndexList);
        MNT.put("Macro Name", MacroNameList);
        MNT.put("MDT Index", MDTIndexList);
        ALA.put("Macro Name", MacroNameList);
        ALA.put("Formal Args", FormalArgsList);
        ALA.put("Positional Args", PositionalArgsList);
        ALA.put("Actual Args", ActualArgsList);
        MDT.put("Index", MDTIndexList);
        MDT.put("Card", MDTCardList);
        System.out.println(MNT);
        System.out.println(ALA);
        System.out.println(MDT);
        // Scanner sc2 = new Scanner(fr);
        // while (condition) {
            
        // }
    }
}
