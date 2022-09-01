import java.util.Scanner;

public class Huffman {
	node root;
	node[] forest;
	public int countfreq(int[] freqs){
		int i, cnt=0;
		try {
		Scanner inf= new Scanner(System.in);
		while(inf.hasNext()) {
			String line = inf.nextLine();
			for(i=0;i<line.length();i++) freqs[(int)line.charAt(i)]++;	
		}
		inf.close();
		for(i=0;i<128;i++) if(freqs[i]>0) cnt++;
		}catch(Exception e) {
			System.out.println(e);
		}
		return cnt;		
	}
	public class node{
		char ch;
		int freq;
		node left,right;
		String code = "";	
		node(){}
		node(int wt, char c){
			freq = wt;
			ch = c;
			left = right = null;
		}		
	}
	public void buildforest(int[] freqs, int cnt) {
		forest = new node[cnt+1];
		int i, k=1;
		for(i=0;i<128;i++) if(freqs[i]>0) forest[k++]=new node(freqs[i],(char)i);
		minheapdn(cnt);	
	}
	public void huffman(int n) {
		while(n>1) {
			node ti = forest[1];
			forest[1]=forest[n--];
			minheapdn(n);
			node tj = forest[1];
			node tmp = new node();
			tmp.left=ti;
			tmp.right=tj;
			tmp.freq= ti.freq+tj.freq;
			forest[1]=tmp;
			minheapdn(n);
		}
		root=forest[1];		
	}
	public void genCode(node t,String[] codes) {
		if(t.left!=null) {
			t.left.code+=t.code+"0";
			genCode(t.left,codes);
			t.right.code=t.code+"1";
			genCode(t.right,codes);	
		}else {
			codes[(int)t.ch]=t.code;
		}	
	}
	public void printOutput(int[] freqs, String[] codes) {
		int sum=0,totalbits=0;
		double average=0,bytes;
		for (int i = 0; i < 128; i++) {
			if (freqs[i] != 0) {
				System.out.printf("\n\t%3d%5s%8d  %-10s", i, (char)i, freqs[i], codes[i]);
				sum+=freqs[i];
				totalbits+=codes[i].length()*freqs[i];
			}
		}
		if(sum!=0)average=(double)totalbits/(double)sum;
		bytes=(double)totalbits/8;
		String f = String.format("# of bytes: %.3f", bytes);
		System.out.println("\nNumber of input chars: "+sum);
		System.out.println("Sum of bits: "+totalbits+", "+f);
		System.out.printf("Average length of codes: %.3f", average);		
	}	
	protected void minheapdn(int n){
		for(int j = n/2; j >= 1; j--) heapdn(j,n);
		}
	protected void heapdn(int m,int max){
		node tmp = forest[m]; 
		int n = max;
		while (m * 2 <= n){          
			int minIndex = 2 * m; 
			if ((minIndex + 1) <= n) if (forest[minIndex+1].freq<forest[minIndex].freq)   minIndex ++;            
			if (forest[minIndex].freq<tmp.freq){
			forest[m] = forest[minIndex];
			m = minIndex; 
			}            
			else    break;
			}
		forest[m] = tmp; 
		}
	public Huffman() {
		int[] freqs = new int[128];
		String[] codes = new String[128];
		int cnt = countfreq(freqs);
		buildforest(freqs,cnt);	
		huffman(cnt);
		genCode(root,codes);
		printOutput(freqs,codes);	
	}
	public static void main(String[] args) {
		new Huffman();
	}
}
