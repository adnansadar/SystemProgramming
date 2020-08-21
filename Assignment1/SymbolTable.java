import java.io.FileNotFoundException; 
import java.io.*;
import java.util.*;

public class SymbolTable
{ 
    public static void main(String[] args) throws IOException 
    { 
    	FileReader file = new FileReader("input.c");
    	// StringBuilder str 
     //        = new StringBuilder();
    	// int ch;
    	Scanner sc = new Scanner(file);
 		int address=0;
 		String stringnext=""; 
        // read from FileReader till the end of file 
        System.out.println("Literal\t\tSize\t\tAddress");
        while (sc.hasNext())
        {
        	String string = sc.next();
        	stringnext = sc.next();
        	if(string.equals("int")||string.equals("float"))
        	{
 	       		stringnext=stringnext.substring(0,stringnext.length()-1);//have to reassign as strings are immutable.
 	       		//It changes the string size.
        		for (String c:stringnext.split(",") ) //extracting individual variables of the data type
        		{
        			System.out.println(c+"\t\t4\t\t"+address);
        			address+=4;
        		}
        	}
        	if(string.equals("char"))
        	{
        		for (String c:stringnext.split(";") ) 
        		{
        			//stringnext=stringnext.substring(0,stringnext.length()-1);
        			System.out.println(c+"\t\t1\t\t"+address);
        			address+=1;
        		}
        	}
        }
        sc.close();
    }
} 