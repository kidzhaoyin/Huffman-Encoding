import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class HuffmanTest {

	public static void main(String[] args) throws FileNotFoundException {
		
		File f=new File(args[0]);
		Scanner scan=new Scanner(f);
		Scanner scan2=new Scanner(System.in);
		
		if(!scan.hasNextLine())
			System.out.println("Empty file.");
		else{
			String input=scan.nextLine();
			
			while(scan.hasNextLine()){
			input=input+"\n"+scan.nextLine();
			}
		
			char[] in=input.toCharArray();
		
			//build tree
			HuffmanTree ht=new HuffmanTree(in);
		
			//build code table
			ht.buildCode();
			//print code table
			ht.printTable();
			//display tree
			ht.disp();
		
			//decode
			System.out.println("please enter code to be decode, with no space in between: ");
			String code=scan2.nextLine();
			
			
			System.out.println(ht.decoder(code));
		
			//encode
			System.out.println("Please enter message to be encode: ");
		
			String msg=scan2.nextLine();
			System.out.println(ht.encoder(msg));
			
			}
	}
}
