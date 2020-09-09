POT = {"Pseudo": ["Db", "Dw", "Org", "ENDP", "Const", "End"],
       "No_operands": [2, 2, 1, 1, 1, 0], "Length": [1, 1, 1, 1, 1, 1]}
MOT = {"Mnemonic": ["Add", "Sub", "Mult", "Jmp", "Jneg", "Jpos", "Jz", "Load", "Store", "Read", "Write", "Stop"], "Opcode": [
    1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12], "No_operands": [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0], "Length": [2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1]}
SymbolTable = {"Symbol": [], "Address": []}
fr = open("input", "r")
lc = 0
temp = 0
line = fr.readline()
while line:
    words = line.split()
    for word in words:
        # CHECK IF WORD IS PRESENT IN MOT OR NOT
        for var in MOT["Mnemonic"]:
            if var == word:
                index = MOT["Mnemonic"].index(word)
                lc += MOT["Length"][index]
                flag = 0
                break
        if flag == 0:  # Word is found in Mnemonic
            flag = 1
            continue
        elif flag == 1 and word == "Loop:" or flag == 1 and word == "Outer:":
            temp2 = 1
            for var in SymbolTable["Symbol"]:
                if var == word:  # Check if word is already present in symbol table
                    temp2 = 0
                    break
            if temp2 == 1:
                SymbolTable["Symbol"].append(word)
                idx = SymbolTable["Symbol"].index(word)
                SymbolTable["Address"].append(lc)
        elif flag == 1 and word != "Endp" and word != "Outer" and word != "Loop":  # to distinguish from labels
            temp1 = 1
            for var in SymbolTable["Symbol"]:
                if var == word:  # checking if word is already present in symbol table
                    temp1 = 0
                    break
            if temp1 == 1:
                SymbolTable["Symbol"].append(word)
                SymbolTable["Address"].append(0)
        elif word == "Endp":
            temp = 1  # to indicate end of code
            address = lc
            print("Symbol Table After First Pass: \n")
            print(SymbolTable)
            break
    if temp == 1:
        line = fr.readline()
        while line:
            words = line.split()
            for word in words:
                for var in SymbolTable["Symbol"]:
                    if word == var:
                        temp3 = 0
                        index = SymbolTable["Symbol"].index(word)
                        break
                    else:
                        temp3 = 1
                if temp3 == 0:
                    continue   # The Word has been found in Mnemonic and we want to find length of next word in POT
                else:
                    for var in POT["Pseudo"]:
                        if word == var:
                            idx = POT["Pseudo"].index(word)
                            # first address is stored,then lc++
                            SymbolTable["Address"][index] = address
                            address += POT["Length"][idx]
            line = fr.readline()
        print("Symbol Table After Second Pass: \n")
        print(SymbolTable)
        break
    else:
        line = fr.readline()
fr.close()
print("\n")
print("Target Program: \n")
fr = open("input", "r")
line = fr.readline()
temp1 = 0
while line:
    words = line.split()
    for word in words:
        if word == "Endp":
            temp1 = 1
            break
        for var in MOT["Mnemonic"]:  # check if word is present in Mnemonic
            if var == word:
                flag = 1
                index = MOT["Mnemonic"].index(word)
                print(MOT["Opcode"][index])
                break
        if flag == 1:
            flag = 0  # word is found in Mnemonic and read next word
            continue
        elif flag == 0:
            for var in SymbolTable["Symbol"]:
                if word == var or word == "Outer" or word == "Loop":
                    if word == "Outer":
                        word = word.replace("Outer", "Outer:")
                    elif word == "Loop":
                        word = word.replace("Loop", "Loop:")
                    elif word == "Outer:" or word == "Loop:":
                        continue
                    idx = SymbolTable["Symbol"].index(word)
                    print(SymbolTable["Address"][idx])
                    break
    if temp1 == 1:
        break
    print("\n")
    line = fr.readline()
