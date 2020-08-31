MOT={"Mnemonic":["Add","Sub","Mult","Jmp","Jneg","Jpos","Jz","Load","Store","Read","Write","Stop"],"Opcode":[1,2,3,4,5,6,7,8,9,10,11,12],"No_operands":[1,1,1,1,1,1,1,1,1,1,1,0],"Length":[2,2,2,2,2,2,2,2,2,2,2,1]}
POT={"Pseudo":["Db","Dw","Org","ENDP","Const","End"],"No_operands":[2,2,1,1,1,0],"Length":[1,1,1,1,1,1]}
Symbol_Table={"Symbol":[],"Address":[]}
file=open("assembler_input","r")
loc_counter=0
temp=0
line=file.readline()
while line:
  strings=line.split()
  for word in strings:
    #******* CHECK IF WORD IS PRESENT IN MOT *******
    for var in MOT["Mnemonic"]:
      if var==word:
        index=MOT["Mnemonic"].index(word) # finding the index of word in Mnemonic
        loc_counter+=MOT["Length"][index] 
        flag=0
        break
    if flag==0: # word is found in Mnemonic
      flag=1
      continue
    elif flag==1 and word=="Loop:" or flag==1 and word=="Outer:":
      temp2=1
      for var in Symbol_Table["Symbol"]:
        if var==word: # checking if word is already present in symbol table
          temp2=0
          break
      if temp2==1:
          Symbol_Table["Symbol"].append(word)
          idx=Symbol_Table["Symbol"].index(word)
          Symbol_Table["Address"].append(loc_counter)
    elif flag==1 and word!="Endp" and word!="Outer" and word!="Loop": # to distinguish from labels
      temp1=1
      for var in Symbol_Table["Symbol"]:
        if var==word: # checking if word is already present in symbol table
          temp1=0
          break
      if temp1==1:
          Symbol_Table["Symbol"].append(word)
          Symbol_Table["Address"].append(0)
    elif word=="Endp":
      temp=1 # to indicate end of code
      address=loc_counter
      print ("********* AFTER FIRST PASS STRUCTURE OF SYMBOL TABLE *********\n")
      print (Symbol_Table)
      break
  if temp==1:
    line=file.readline()
    while line:
      strings=line.split()
      for word in strings:
        for var in Symbol_Table["Symbol"]:
          if word==var:
            temp3=0
            index=Symbol_Table["Symbol"].index(word)
            break
          else:
            temp3=1
        if temp3==0:
          continue   # word has been found in Mnemonic and we want to find length of next word in POT
        else:
          for var in POT["Pseudo"]:
            if word==var:
              idx=POT["Pseudo"].index(word)
              Symbol_Table["Address"][index]=address # first address is stored,then loc_counter++
              address+=POT["Length"][idx]
      line=file.readline()
    print ("********* AFTER SECOND PASS STRUCTURE OF SYMBOL TABLE *********\n")
    print (Symbol_Table)
    break
  else:  
    line=file.readline()
file.close()
print ("\n")
print ("********* TARGET PROGRAM *********\n")
file=open("assembler_input","r")
line=file.readline()
temp1=0
while line:
  strings=line.split()
  for word in strings:
    if word=="Endp":
      temp1=1
      break
    for var in MOT["Mnemonic"]: # check if word is present in Mnemonic
      if var==word:
        flag=1
        index=MOT["Mnemonic"].index(word)
        print (MOT["Opcode"][index])
        break
    if flag==1:
      flag=0 # word is found in Mnemonic and read next word
      continue
    elif flag==0:
      for var in Symbol_Table["Symbol"]:
        if word==var or word=="Outer" or word=="Loop":
          if word=="Outer":
            word=word.replace("Outer","Outer:")
          elif word=="Loop":
            word=word.replace("Loop","Loop:")
          elif word=="Outer:" or word=="Loop:":
            continue
          idx=Symbol_Table["Symbol"].index(word)
          print (Symbol_Table["Address"][idx])
          break
  if temp1==1:
    break
  print ("\n")  
  line=file.readline() 
