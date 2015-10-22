import java.util.HashMap;
import java.util.Map;


public class HuffmanTree {
	//Nodes class
	private static class Node implements Comparable<Node>{
		private int freq;char ch;
		private String str;
		private Node left;
		private Node right;
		private boolean leaf;
		
		//leaf node
		public Node(char ch,int frequency){
			this.ch=ch;str="";
			freq=frequency;
			leaf=true;
			left=null;
			right=null;
		}
		//'T' node
		public Node(String name,int frequency, Node lc, Node rc){
			this.str=name;
			freq=frequency;
			left=lc;
			right=rc;
			leaf=false;
		}
		//compareTo method comparing nodes according to its frequency
		public int compareTo(Node no){
			return (this.freq-no.freq);
		}

	}
	
	private static final int DEFAULT_CAPACITY=256;
	private BinaryHeap<Node> bh;
	private Node root;
	private HashMap<Character,String> codeMap;
	int n=0;

	
	public HuffmanTree(char[] in){
		
		bh=new BinaryHeap<Node>(DEFAULT_CAPACITY);
		int[] frequency=new int[DEFAULT_CAPACITY];
		//store frequency of each character to the cell of its ASCII value in frequency array
		for(char ch:in)
			frequency[ch]++;
		
		//for each character appears in input, create a leaf Node of char and insert it to the binary heap with its frequency as key
		for(int i=0;i<256;i++){
			if (frequency[i]!=0)
				bh.insert(new Node((char)i,frequency[i]));
		}
		//a map that maps a character to its Huffman code
		codeMap=new HashMap<Character,String>(bh.getSize()*2);
		
		//int i keeps track of number of T Nodes
		int i=1;
		if(bh.getSize()==1){
			Node lc=bh.deleteMin();
			bh.insert(new Node("T1",lc.freq,lc,null));
			root=bh.findMin();
		}
		
		//build huffman tree
		while(bh.getSize()>=2){
			Node lc=bh.deleteMin();
			Node rc=bh.deleteMin();
			
		
			bh.insert(new Node("T"+i,lc.freq+rc.freq,lc,rc));
			i++;
		}
		root=bh.findMin();
	
	}
	
	//build a map of characters with corresponding codes
	public void buildCode(){
		if(root.right==null)
			codeMap.put(root.left.ch, "0");
		else
		buildCode(root,"");
	}
	
	private void buildCode(Node nd,String str){
		if(!nd.leaf)
		{
			buildCode(nd.left,str+'0');
			buildCode(nd.right,str+'1');
		}
		else
			{
			codeMap.put(nd.ch, str);
			if(str.length()>n)
				n=str.length();
			}
	}
	
	public HashMap<Character,String> getCode(){
		return codeMap;
	}
	
	//print out a table of characters along with their corresponding codes
	public void printTable(){
		for (Map.Entry<Character, String> entry:codeMap.entrySet()){
			if(entry.getKey()=='\n')
				System.out.println("\'NL\'"+"= "+entry.getValue());
			else if(entry.getKey()==32)
				System.out.println("\'SP\'"+"= "+entry.getValue());
			else
			System.out.println("\'"+entry.getKey()+"\'"+"= "+entry.getValue());
		}
	}
	

	//display the huffman tree
	public void disp(){
		if(root.right==null){
			System.out.println("  T1  ");
			System.out.println(" /");
			System.out.println(root.left.ch);
			return;
		}
		Node[][] lines=new Node[n+1][];
		Node curr=this.root;
		lines[0]=new Node[1];
		
		lines[0][0]=curr;
		for (int h=0;h<n;h++){
			int currs=(int)Math.pow(2, n-h)-1;
			int prevs=(int)Math.pow(2, n-h+1)-1;
			
			lines[h+1]=new Node[(int)Math.pow(2, h+1)];
			if(lines[h][0]==null)
				System.out.format("%1$#"+(currs+1)+"s", "");
			
			else{
			
				curr=lines[h][0];
				if(!curr.leaf){
					System.out.format("%1$#"+(currs-1)+"s", "");
					System.out.print(curr.str);
					lines[h+1][0]=curr.left;
					lines[h+1][1]=curr.right;
				}
				else{
				
					System.out.format("%1$#"+currs+"s", "");
					if (curr.ch==32)
						System.out.print("SP");
					else if(curr.ch=='\n')
						System.out.print("NL");
					else
					System.out.print(curr.ch);
				}
			}
			for(int j=1;j<Math.pow(2,h);j++){
				if (lines[h][j]==null)
					System.out.format("%1$#"+(prevs+1)+"s", "");
				else
				{
					curr=lines[h][j];
					if(!curr.leaf){
						System.out.format("%1$#"+(prevs-1)+"s", "");
						System.out.print(curr.str);
						lines[h+1][2*j]=curr.left;
						lines[h+1][2*j+1]=curr.right;
					}
					else{
						System.out.format("%1$#"+prevs+"s", "");
						if (curr.ch==32)
							System.out.print("SP");
						else if(curr.ch=='\n')
							System.out.print("NL");
						else
						System.out.print(curr.ch);
					}
				}
				
				
			}
			//iterate again to print "/ \"
			System.out.print("\n");
			if((lines[h][0]==null)||(lines[h][0].leaf))
				System.out.format("%1$#"+(currs+2)+"s", "");
			
			else{
			
				curr=lines[h][0];
					System.out.format("%1$#"+(currs-1)+"s", "");
					System.out.print("/ \\");
				}
			
			
			for(int j=1;j<Math.pow(2, h);j++){
				if ((lines[h][j]==null)||lines[h][j].leaf)
					System.out.format("%1$#"+(prevs+1)+"s", "");
				else
				{
					curr=lines[h][j];
					System.out.format("%1$#"+(prevs-2)+"s", "");
					System.out.print("/ \\");
				}
			
			}
			System.out.print("\n");
		}
		//print deepest nodes
		if(lines[n][0]!=null)
		System.out.print(lines[n][0].ch);
		else
			System.out.print(" ");
		for(int j=1;j<Math.pow(2, n);j++){
			if(lines[n][j]!=null){
				if (lines[n][j].ch==32)
					System.out.print("SP");
				else if(lines[n][j].ch=='\n')
					System.out.print("NL");
				else
				System.out.print(" "+lines[n][j].ch);
			}
				
			else
				System.out.print("  ");
		}
		System.out.print("\n");
	}

	//decode a string of '0' and '1'
	//skip spaces if any
	public String decoder(String code){
		String msg="";
		int i=0;
		if(code.length()==0)
			return("Empty input");
		while(i<code.length()){
			Node curr=this.root;
			while(!curr.leaf){
				if (i>=code.length())
					return ("Input error. No match found.");
				else if(code.charAt(i)=='0')
					curr=curr.left;
				else if(code.charAt(i)=='1')
					curr=curr.right;
				else if(code.charAt(i)==32);
				else
					return ("Invalid input.");
				i++;
					
			}
			if(curr.ch=='\n')
				msg+="NL";
			else if(curr.ch==32)
				msg+="SP";
			else
				msg+=curr.ch;
		}
		
		return msg;
		
		
		
	}
	
	//encode a string to huffman code
	public String encoder(String msg){
		String code="";
		if(msg.length()==0)
			return ("Empty input");
		for(int i=0;i<msg.length();i++){
			String c=codeMap.get(msg.charAt(i));
			if(c==null)
				System.out.println("Character "+msg.charAt(i)+" not recognized.");
			else
			code+=c;
		}
		return code;
	}
	
	//make huffman header indicating tree structure
	private String header="";

	public String makeHeader(){
		makeHeader(root);
		return header;
	}

	private void makeHeader(Node nd){
		if(!nd.leaf){
			header+='0';
			makeHeader(nd.left);
			makeHeader(nd.right);
			
		}
		else{
			header=header+'1'+nd.ch;
		}
	}
	
	
	
}
