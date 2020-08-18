import java.io.FileNotFoundException; 
import java.io.FileReader; 
import java.io.IOException; 
class ReadFile 
{ 
    public static void main(String[] args) throws IOException 
    { 
    	StringBuilder str 
            = new StringBuilder();
    	int ch;
    	FileReader fr=new FileReader("InputC.txt") ; 
  
        // read from FileReader till the end of file 
        while ((ch=fr.read())!=-1)
        	str.append(ch);
        System.out.println("String1 = "
                           + str.toString()); 
        fr.close();
    }
} 