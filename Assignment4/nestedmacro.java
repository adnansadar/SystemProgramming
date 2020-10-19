import java.util.*;
import java.io.*;

/**
 * macroprocessor
 */
public class nestedmacro {
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
                // Initialising MNT,MDT
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
                // Initialising MDTCardList
                while (!line.startsWith("MEND")) {
                    line = sc.nextLine();
                    String[] linesplit = line.split(" ");
                    int wordindex = 0;
                    for (String word : linesplit) {
                        if (word.contains("&")) {
                            wordindex = FormalArgsList.indexOf(word);
                            Object posarg = PositionalArgsList.get(wordindex);
                            MDTCardList.add(posarg);
                        } else {
                            MDTCardList.add(word);
                        }
                    }

                    MDTIndexList.add(MDTindex);
                    MDTindex++;
                }
            }
        } // End of First Pass
        System.out.println("\n****************After 1st Pass:******************");
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
        MDTCardList.clear();
        while (sc2.hasNextLine()) {
            String line = sc2.nextLine();
            String[] words = line.split(" ");
            int word = 0;
            // Initialising Actual Args
            if (MacroNameList.contains(words[word])) {
                int startindex = words[word].length() + 2;
                String Actualargs = line.substring(startindex);
                String[] ActualArgs = Actualargs.split(" ");
                int ActualArgsIndex = MacroNameList.indexOf(words[word]);
                for (String i : ActualArgs) {
                    ActualArgsList.set(ActualArgsIndex, i);
                    ActualArgsIndex++;
                }
            }
        }
        FileReader fr3 = new FileReader("input.c");
        Scanner sc3 = new Scanner(fr3);
        while (sc3.hasNextLine()) {
            // Replacing Positional Args with Actual Args in CardList
            String line = sc3.nextLine();
            if (line.startsWith("MACRO")) {
                while (!line.startsWith("MEND")) {
                    line = sc3.nextLine();
                    String[] linesplit = line.split(" ");
                    int wordindex = 0;
                    for (String word2 : linesplit) {
                        if (word2.contains("&")) {
                            wordindex = FormalArgsList.indexOf(word2);
                            Object actualarg = ActualArgsList.get(wordindex);
                            // System.out.println(actualarg);
                            MDTCardList.add(actualarg);
                        } else {
                            MDTCardList.add(word2);
                        }
                    }
                }
            }
        } // End of Pass 2
        System.out.println("\n***************After 2nd Pass:***************");
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

        // Expanded Code
        System.out.println("\n\n*****************Expanded Code:*********************");
        FileReader frnew = new FileReader("input.c");
        Scanner scnew = new Scanner(frnew);
        int count = 0;
        while (scnew.hasNextLine()) {
            String line = scnew.nextLine();
            if (line.startsWith("MACRO")) {
                while (!line.startsWith("MEND")) {

                    line = scnew.nextLine();
                    String[] linesplit = line.split(" ");
                    for (int i = 0; i < linesplit.length; i++) {
                        if(MacroNameList.contains(linesplit[i]))
                        {
                            for (int j = 0; j < 6; j++) {
                                if(j==3)
                                    System.out.println();
                                System.out.print(MDTCardList.get(j)+" " );
                            }
                            count+=3;
                            i=linesplit.length;      
                            continue;
                        }
                        else if (linesplit[i].equals("MEND")) {
                            count += 1;
                            continue;
                        }
                        System.out.print(MDTCardList.get(count) + " ");
                        count += 1;
                    }
                    System.out.println();
                }
            }
        }
    }
}
