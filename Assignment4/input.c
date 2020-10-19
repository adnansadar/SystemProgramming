STORE  C 
MACRO  EFG  &amp;ST 
L 1, &amp;ST 
A 1, &amp;ST 
MEND 
MACRO  WXY  &amp;X1 &amp;X2 &amp;X3 
LOAD &amp;X1  
LOAD &amp;X2 
EFG  10 
LOAD &amp;X3 
MEND 
WXY  2, 3, 4 
WXY  100, 150, 200 
END 