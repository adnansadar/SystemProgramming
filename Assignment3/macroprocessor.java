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
        List<Object> MNTMDTIndexList = new ArrayList<Object>();

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
                int position = 1;
                for (String word : words) {
                    if (word.equals("MACRO")) {
                        MNTIndexList.add(MNTindex);
                        MNTMDTIndexList.add(MDTindex);
                        MNTindex++;
                        MacroNameList.add(words[2]);
                    } else if (word.startsWith("&")) {
                        FormalArgsList.add(word);
                        PositionalArgsList.add("#" + position);
                        ActualArgsList.add("");
                        position++;
                    }
                }
                while (!line.startsWith("MEND")) {
                    line = sc.nextLine();
                    // if(line.contains("&"))
                    // {
                    //     Object[] linesplit = line.split(" ");
                    //     for (int string=0; string<linesplit.length; string++) {
                    //         if(((String) linesplit[string]).contains("&"))
                    //         {
                    //             int index=FormalArgsList.indexOf(string);
                    //             Object positionalarg = PositionalArgsList.get(index);
                    //             linesplit[string] = positionalarg;
                    //         }
                    //     }
                    // }
                    MDTIndexList.add(MDTindex);
                    MDTindex++;
                    MDTCardList.add(line);
                }
            }
        } // End of First Pass
        MNT.put("Index", MNTIndexList);
        MNT.put("Macro Name", MacroNameList);
        MNT.put("MDT Index", MNTMDTIndexList);
        ALA.put("Macro Name", MacroNameList);
        ALA.put("Formal Args", FormalArgsList);
        ALA.put("Positional Args", PositionalArgsList);
        ALA.put("Actual Args", ActualArgsList);
        MDT.put("Index", MDTIndexList);
        MDT.put("Card", MDTCardList);
        System.out.println("MNT:\n" + MNT);
        System.out.println("ALA:\n" + ALA);
        System.out.println("MDT:\n" + MDT);

        // 2nd Pass of Macroprocessor
        FileReader fr2 = new FileReader("input.c");
        Scanner sc2 = new Scanner(fr2);
        while (sc2.hasNextLine()) {
            String line = sc2.nextLine();
            String[] words = line.split(" ");
            int word = 0;
            if(MacroNameList.contains(words[word]))
            {
                int startindex = words[word].length()+2;
                String Actualargs = line.substring(startindex);
                String [] ActualArgs = Actualargs.split(" ");
                int ActualArgsIndex = MacroNameList.indexOf(words[word]);
                for (String i : ActualArgs) {
                    ActualArgsList.set(ActualArgsIndex, i);
                    ActualArgsIndex++;
                }
            }
        }
        MNT.put("Index", MNTIndexList);
        MNT.put("Macro Name", MacroNameList);
        MNT.put("MDT Index", MNTMDTIndexList);
        ALA.put("Macro Name", MacroNameList);
        ALA.put("Formal Args", FormalArgsList);
        ALA.put("Positional Args", PositionalArgsList);
        ALA.put("Actual Args", ActualArgsList);
        MDT.put("Index", MDTIndexList);
        MDT.put("Card", MDTCardList);
        System.out.println("MNT:\n" + MNT);
        System.out.println("ALA:\n" + ALA);
        System.out.println("MDT:\n" + MDT);
    }
}
