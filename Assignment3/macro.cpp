/*
Assignment No.      :- 3
Problem Statement   :- Perform Pass-1 and Pass-2 for Macro Commands
Name                :- Pratik Fandade (TY-IT)
Roll no             :- 17
*/

#include <string.h>
#include <iostream>
#include <fstream>
#include <string>
#include <vector>

using namespace std;

/* Database */
class MNT {
    public:
        int INDX[100] = {};
        string NAME[100] = {};
        int MDTI[100] = {};
};

class MDT {
    public:
        int INDX[100] = {};
        string CARD[100] = {};
};

class ALA {
    public:
        int INDX[100] = {};
        string NAME[100] = {};
        string FARG[100] = {};
        string PARG[100] = {};
        string AARG[100] = {};
};

/* Driver Code */
int main() {
    // Input & Output File
    ifstream ifile ("input.txt");
    ifstream infile ("input.txt");
    ifstream ixfile ("input.txt");
    ofstream outfile ("output.txt");
    string iline;

    // Data Structures
    MNT MNT;
    MDT MDT;
    ALA ALA;

    // Pointers
    int MNTP = 0;
    int MDTP = 0;
    int ALAP = 0;

    /* Pass - 1 */
    if (ifile.is_open()) {
        getline(ifile,iline,' ');
        while (iline != "\nEND") {

            // if macro definition
            if (iline == "\nMACRO" || iline == "MACRO") {
                iline = "";
                getline(ifile,iline,' ');
                string mname = iline;

                // enter name in MNT
                MNT.INDX[MNTP] = MNTP+1;
                MNT.NAME[MNTP] = iline;
                MNT.MDTI[MNTP] = MDTP+1;
                MNTP++;
                iline = "";
                getline(ifile,iline);
                string word = "";

                // enter agruments in ALA
                for (auto x : iline) {
                    if (x == ' ') {
                        ALA.INDX[ALAP] = ALAP+1;
                        ALA.NAME[ALAP] = mname;
                        ALA.FARG[ALAP] = word;
                        ALA.PARG[ALAP] = "#"+to_string(ALAP+1);
                        ALA.AARG[ALAP] = "N/A";
                        ALAP++;
                        word = "";
                    }
                    else if (x == ',') {
                        continue;
                    }
                    else {
                        word = word + x;
                    }
                }

                // enter MACRO body in MDT
                getline(ifile,iline);
                while (iline != "MEND ") {
                    string xline = "";
                    string word = "";
                    for (auto x : iline) {
                        if (x == ' ') {
                            for (int i = 0; i < ALAP; i++) {
                                if (word == ALA.FARG[i]) word = ALA.PARG[i];
                            }
                            xline += (word + " ");
                            word = "";
                        }
                        else if (x == ',') {
                            continue;
                        }
                        else {
                            word = word + x;
                        }
                    }
                    MDT.INDX[MDTP] = MDTP+1;
                    MDT.CARD[MDTP] = xline;
                    MDTP++;

                    iline = "";
                    getline(ifile,iline);
                }
                MDT.INDX[MDTP] = MDTP+1;
                MDT.CARD[MDTP] = "MEND";
                MDTP++;
            }

            iline = "";
            getline(ifile,iline,' ');
        }
        ifile.close();
    } else cout << "ERROR" << "\n";

    cout << "******************* AFTER PASS 1 *******************" << "\n";

    cout << "\n\tPRINTING MNT\t" << "\n\n";
    cout << "Index\tName\tMDT Index\n";
    for (int i = 0; i < MNTP; i++) {
        cout << MNT.INDX[i] << "\t" << MNT.NAME[i] << "\t" << MNT.MDTI[i] << "\n";
    }

    cout << "\n\tPRINTING MDT\t" << "\n\n";
    cout << "Index\tCard\n";
    for (int i = 0; i < MDTP; i++) {
        cout << MDT.INDX[i] << "\t" << MDT.CARD[i] << "\n";
    }

    cout << "\n\tPRINTING ALA\t" << "\n\n";
    cout << "Index\tName\tF. Args\t\tP. Args\tA. Args\n";
    for (int i = 0; i < ALAP; i++) {
        cout << ALA.INDX[i] << "\t" << ALA.NAME[i] << "\t" << ALA.FARG[i] << "\t\t" << ALA.PARG[i] << "\t" << ALA.AARG[i] << "\n";
    }

    /* Pass - 2 */
    bool mflag = false;
    bool aflag = false;
    int idx = 0;
    if (infile.is_open()) {
        iline="";
        getline(infile,iline);
        while (iline != "END ") {
            int indx = 0;
            string word = "";
            for (auto x : iline) {
                if (x == ' ') {
                    if (word == "MACRO") mflag = true;
                    if (aflag == true) {
                        ALA.AARG[indx] = word;
                        indx++;
                    }
                    for (int i = 0; i < ALAP; i++) {
                        if (word == ALA.NAME[i] && mflag == false) {
                            indx = i;
                            aflag = true;
                            break;
                        }
                    }
                    word = "";
                }
                else if (x == ',') {
                    continue;
                }
                else {
                    word = word + x;
                }
            }
            aflag = false;
            mflag = false;
            iline = "";
            getline(infile,iline);
        }
        infile.close();
    } else cout << "ERROR" << "\n";

    cout << "\n******************* AFTER PASS 2 *******************" << "\n";

    cout << "\n\tPRINTING MNT\t" << "\n\n";
    cout << "Index\tName\tMDT Index\n";
    for (int i = 0; i < MNTP; i++) {
        cout << MNT.INDX[i] << "\t" << MNT.NAME[i] << "\t" << MNT.MDTI[i] << "\n";
    }

    cout << "\n\tPRINTING MDT\t" << "\n\n";
    cout << "Index\tCard\n";
    for (int i = 0; i < MDTP; i++) {
        cout << MDT.INDX[i] << "\t" << MDT.CARD[i] << "\n";
    }

    cout << "\n\tPRINTING ALA\t" << "\n\n";
    cout << "Index\tName\tF.Args\t\tP.Args\tA. Args\n";
    for (int i = 0; i < ALAP; i++) {
        cout << ALA.INDX[i] << "\t" << ALA.NAME[i] << "\t" << ALA.FARG[i] << "\t\t" << ALA.PARG[i] << "\t" << ALA.AARG[i] << "\n";
    }

    cout << "\n****************** EXPANDED CODE ******************" << "\n\n";

    if (ixfile.is_open()) {
        iline="";
        getline(ixfile,iline);
        while (iline != "END ") {
            int indx = 0;
            string word = "";
            for (auto x : iline) {
                if (x == ' ') {
                    if (word == "MACRO") mflag = true;
                    for (int i = 0; i < MNTP; i++) {
                        if (word == MNT.NAME[i] && mflag == false) {
                            word = "";
                            for(int j = MNT.MDTI[i] - 1; j < MDTP; j++) {
                                if(MDT.CARD[j] == "MEND") break;
                                string prints = MDT.CARD[j];
                                for (auto y : prints) {
                                    if (y == ' ') {
                                        for (int k = 0; k < ALAP; k++) {
                                            if(word == ALA.PARG[k]) word = ALA.AARG[k];
                                        }
                                        cout << word+" ";
                                        outfile << word+" ";
                                        word = "";
                                    }
                                    else if (y == ',') {
                                        continue;
                                    }
                                    else {
                                        word = word + y;
                                    }
                                }
                                cout << "\n";
                                outfile << "\n";
                            }
                            break;
                        }
                    }
                    word = "";
                }
                else if (x == ',') {
                    continue;
                }
                else {
                    word = word + x;
                }
            }
            aflag = false;
            mflag = false;
            iline = "";
            getline(ixfile,iline);
        }
        ixfile.close();
    } else cout << "ERROR" << "\n";

    return 0;
}