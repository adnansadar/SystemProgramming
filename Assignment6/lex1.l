
%{
    #include<stdio.h>
    #include<string.h>
    int count=0,ncount=0;
%}


%%
[a-zA-Z]*\<[a-zA-Z|.]*\> {printf("\nHeader file : %s",yytext);}

"int"|"void"|"float"|"char"|"double"|"for" {printf("\nKeyword %s",yytext);}

[a-zA-Z]*\( {printf("\nFunction %s",strcat(yytext,")"));}

[a-zA-Z]* {printf("\nIdentifiers %s",yytext);}

"+"|"-"|"*"|"/"|"%"|"<<"|">>"|"++"|"<"|">"|"--"|"=" {printf("\nOperator %s",yytext);}

-?[0-9]* {printf("\nNumbers %s",yytext);}

" " {count++;}

"\n" {ncount++;}

" " {printf(" ");}
%%

main(int argc,char **argb)
{
    FILE *fp;
    fp=fopen(argb[1],"r");
    yyin=fp;
    yylex();
    printf("\nNo. of white spaces = %d",count);
    printf("\nNo. of new lines = %d",ncount);
}

int yywrap()
{
    return 1;
}

to run this file:

lex lexfile.l
cc lex.yy.c
./a.out