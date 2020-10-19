import java.io.*;
import java.util.*;

/**
 * linker
 */
public class linker {
    static HashMap<String, List<Object>> LVTFile1;
    static HashMap<String, List<Object>> LVTFile2;
    static HashMap<String, List<Object>> ExternVTFile1;
    static HashMap<String, List<Object>> ExternVTFile2;
    static HashMap<String, List<Object>> GVT;
    static List<Object> VarListLVTF1;
    static List<Object> SizeListLVTF1;
    static List<Object> AddressListLVTF1;
    static List<Object> VarListLVTF2;
    static List<Object> SizeListLVTF2;
    static List<Object> addressListLVTF2;
    static List<Object> VarListExternF1;
    static List<Object> SizeListExternF1;
    static List<Object> VarListExternF2;
    static List<Object> SizeListExternF2;
    static List<Object> VarListGVT;
    static List<Object> SizeListGVT;
    static List<Object> AddressListGVT;

    public static void main(String[] args) throws IOException {
        LVTFile1 = new HashMap<String, List<Object>>();
        LVTFile2 = new HashMap<String, List<Object>>();
        ExternVTFile1 = new HashMap<String, List<Object>>();
        ExternVTFile2 = new HashMap<String, List<Object>>();
        GVT = new HashMap<String, List<Object>>();
        VarListLVTF1 = new ArrayList<Object>();
        SizeListLVTF1 = new ArrayList<Object>();
        AddressListLVTF1 = new ArrayList<Object>();
        VarListLVTF2 = new ArrayList<Object>();
        SizeListLVTF2 = new ArrayList<Object>();
        addressListLVTF2 = new ArrayList<Object>();
        VarListExternF1 = new ArrayList<Object>();
        SizeListExternF1 = new ArrayList<Object>();
        VarListExternF2 = new ArrayList<Object>();
        SizeListExternF2 = new ArrayList<Object>();
        VarListGVT = new ArrayList<Object>();
        SizeListGVT = new ArrayList<Object>();
        AddressListGVT = new ArrayList<Object>();
        VarListLVTF1 = new ArrayList<Object>();
        int fileno = 1;
        FileReader file1 = new FileReader("file1.txt");
        Scanner sc1 = new Scanner(file1);
        filltable(sc1, fileno);
        fileno++;
        FileReader file2 = new FileReader("file2.txt");
        Scanner sc2 = new Scanner(file2);
        filltable(sc2, fileno);
        printtable();
    }

    private static void filltable(Scanner sc, int fileno) {
        int address1 = 100;
        int address2 = 500;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.startsWith("extern")) // if extern
            {
                // System.out.println(line);
                String[] words = line.split(" ");
                String lastword = words[words.length - 1];
                lastword = lastword.substring(0, lastword.length() - 1);
                if (lastword.contains(",")) // if multiple variables present, extract each variable
                {
                    String[] vars = lastword.split(",");
                    for (String var : vars) {
                        if (words[1].equals("int")) // check datatype of extern variable
                        {
                            if (fileno == 1) // check if its file 1 or 2
                            {
                                VarListExternF1.add(var); // add each var to extern var list
                                SizeListExternF1.add(2); // set its size acc to datatype
                            } else {
                                VarListExternF2.add(var);
                                SizeListExternF2.add(2);
                            }
                        } else if (words[1].equals("float")) {
                            if (fileno == 1) {
                                VarListExternF1.add(var);
                                SizeListExternF1.add(4);
                            } else {
                                VarListExternF2.add(var);
                                SizeListExternF2.add(4);
                            }
                        } else if (words[1].equals("char")) {
                            if (fileno == 1) {
                                VarListExternF1.add(var);
                                SizeListExternF1.add(1);
                            } else {
                                VarListExternF2.add(var);
                                SizeListExternF2.add(1);
                            }
                        }
                        VarListGVT.add(var); // add the variable to global variable table
                    }
                } else {
                    System.out.println(lastword);
                    if (words[1].equals("int")) {
                        if (fileno == 1) {
                            VarListExternF1.add(lastword);
                            SizeListExternF1.add(2);
                        } else {
                            VarListExternF2.add(lastword);
                            SizeListExternF2.add(2);
                        }
                    } else if (words[1].equals("float")) {
                        if (fileno == 1) {
                            VarListExternF1.add(lastword);
                            SizeListExternF1.add(4);
                        } else {
                            VarListExternF2.add(lastword);
                            SizeListExternF2.add(4);
                        }
                    } else if (words[1].equals("char")) {
                        if (fileno == 1) {
                            VarListExternF1.add(lastword);
                            SizeListExternF1.add(1);
                        } else {
                            VarListExternF2.add(lastword);
                            SizeListExternF2.add(1);
                        }
                    }
                    VarListGVT.add(lastword);
                }
            } else if (line.startsWith("int")) {
                String[] words = line.split(" ");
                String lastword = words[words.length - 1];
                lastword = lastword.substring(0, lastword.length() - 1);
                if (lastword.contains(",")) {
                    String[] vars = lastword.split(",");
                    for (String var : vars) {
                        // System.out.println(var);
                        if (fileno == 1) {
                            VarListLVTF1.add(var);
                            SizeListLVTF1.add(2);
                            AddressListLVTF1.add(address1);
                            address1 += 2;
                            // System.out.println(address1);
                        } else {
                            VarListLVTF2.add(var);
                            SizeListLVTF2.add(2);
                            addressListLVTF2.add(address2);
                            address2 += 2;
                        }
                    }
                } else {
                    if (fileno == 1) {
                        VarListLVTF1.add(lastword);
                        SizeListLVTF1.add(2);
                        AddressListLVTF1.add(address1);
                        address1 += 2;
                        // System.out.println(address1);
                    } else {
                        VarListLVTF2.add(lastword);
                        SizeListLVTF2.add(2);
                        addressListLVTF2.add(address2);
                        address2 += 2;
                    }
                }
            } else if (line.startsWith("float")) {
                String[] words = line.split(" ");
                String lastword = words[words.length - 1];
                lastword = lastword.substring(0, lastword.length() - 1);
                if (lastword.contains(",")) {
                    String[] vars = lastword.split(",");
                    for (String var : vars) {
                        if (fileno == 1) {
                            // System.out.println(var);
                            VarListLVTF1.add(var);
                            SizeListLVTF1.add(4);
                            // System.out.println(address1);
                            AddressListLVTF1.add(address1);
                            address1 += 4;
                        } else {
                            VarListLVTF2.add(var);
                            SizeListLVTF2.add(4);
                            addressListLVTF2.add(address2);
                            address2 += 4;
                        }
                    }
                } else {
                    if (fileno == 1) {
                        VarListLVTF1.add(lastword);
                        SizeListLVTF1.add(4);
                        AddressListLVTF1.add(address1);
                        address1 += 4;
                        // System.out.println(address1);
                    } else {
                        VarListLVTF2.add(lastword);
                        SizeListLVTF2.add(4);
                        addressListLVTF2.add(address2);
                        address2 += 4;
                    }
                }
            } else if (line.startsWith("char")) {
                String[] words = line.split(" ");
                String lastword = words[words.length - 1];
                lastword = lastword.substring(0, lastword.length() - 1);
                if (lastword.contains(",")) {
                    String[] vars = lastword.split(",");
                    for (String var : vars) {
                        // System.out.println(var);
                        if (fileno == 1) {
                            VarListLVTF1.add(var);
                            SizeListLVTF1.add(1);
                            AddressListLVTF1.add(address1);
                            address1 += 1;
                        } else {
                            VarListLVTF2.add(var);
                            SizeListLVTF2.add(1);
                            addressListLVTF2.add(address2);
                            address2 += 1;
                        }
                    }
                } else {
                    if (fileno == 1) {
                        VarListLVTF1.add(lastword);
                        SizeListLVTF1.add(1);
                        AddressListLVTF1.add(address1);
                        address1 += 1;
                        // System.out.println(address1);
                    } else {
                        VarListLVTF2.add(lastword);
                        SizeListLVTF2.add(1);
                        addressListLVTF2.add(address2);
                        address2 += 1;
                    }
                }
            }
        }

        for (Object var : VarListGVT) {
            if (VarListLVTF1.contains(var)) {
                int index = VarListLVTF1.indexOf(var);
                Object size = SizeListLVTF1.get(index);
                Object address = AddressListLVTF1.get(index);
                SizeListGVT.add(size);
                AddressListGVT.add(address);
            } else if (VarListLVTF2.contains(var)) {
                int index = VarListLVTF2.indexOf(var);
                Object size = SizeListLVTF2.get(index);
                Object address = addressListLVTF2.get(index);
                SizeListGVT.add(size);
                AddressListGVT.add(address);
            }
        }
    }

    private static void printtable() {
        LVTFile1.put("Variable", VarListLVTF1);
        LVTFile1.put("Size", SizeListLVTF1);
        LVTFile1.put("Address", AddressListLVTF1);
        LVTFile2.put("Variable", VarListLVTF2);
        LVTFile2.put("Size", SizeListLVTF2);
        LVTFile2.put("Address", addressListLVTF2);
        ExternVTFile1.put("Variable", VarListExternF1);
        ExternVTFile1.put("Size", SizeListExternF1);
        ExternVTFile2.put("Variable", VarListExternF2);
        ExternVTFile2.put("Size", SizeListExternF2);
        GVT.put("Variable", VarListGVT);
        GVT.put("Size", SizeListGVT);
        GVT.put("Address", AddressListGVT);
        System.out.println("LVTFile1:\n" + LVTFile1);
        System.out.println("LVTFile2:\n" + LVTFile2);
        System.out.println("ExternVTFile1:\n" + ExternVTFile1);
        System.out.println("ExternVTFile2:\n" + ExternVTFile2);
        System.out.println("GVT:\n" + GVT);
    }
}
