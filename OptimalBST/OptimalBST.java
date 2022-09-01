import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class OptimalBST <T extends Comparable<T>>{
	ArrayList<T> keys = new ArrayList<T>();
	ArrayList<Integer> freq = new ArrayList<Integer>();
	double[] p;
	double[][] a;
	int[][] root;
	Node<T> troot = null;
	private class Node<T> { 
	        T key; 
	        Node<T> left, right; 
	        public Node(T x) { 
	            key = x; 
	            left = right = null; 
	        } 
	    }
	private class key<T>{
		T key;
		int freq;
		double p;
		public key(T x, int fre) {
			key=x;
			freq=fre;
			p=0;
		}
	}
	
	public void addtoarrays(T x) {
		int j=-1;
		for(int i=0;i<keys.size();i++) {
			if(x.compareTo((T) keys.get(i))==0) {
				j=i;
				break;
			}
		}
		if(j==-1) {
			keys.add((T)x);
			freq.add(1);
		}
		else {
			freq.set(j, freq.get(j)+1);
		}
	
	
	}
    void printLevelbyLevel() { 
        if(troot == null) 
            return; 
        Queue<Node<T>> queue =new LinkedList<Node<T>>(); 
        queue.add(troot); 
          
          
        while(true) 
        { 
            int count = queue.size(); 
            if(count == 0) 
                break; 

            while(count > 0) 
            { 
                Node<T> z = queue.peek(); 
                System.out.print(z.key + " "); 
                queue.remove(); 
                if(z.left != null) 
                    queue.add(z.left); 
                if(z.right != null) 
                    queue.add(z.right); 
                count--; 
            } 
            System.out.println(); 
        } 
    } 
    void printlists() {
    	
    	for(int i=0;i<keys.size();i++) {
    		String s = String.format("%.3f", p[i+1]);
    		System.out.println("Word: "+keys.get(i)+"       Probability: "+s);		
    	}
    }
    void printbyp() {
    	key<T>[] list = new key[keys.size()];
    	double[] temp=new double[keys.size()];
    	for(int i = 0; i<keys.size();i++) {
    		list[i]=new key(keys.get(i),freq.get(i));
    		list[i].p=p[i+1];	
    		temp[i]=p[i+1];
    	}
    	Arrays.sort(temp);
    	for(int i=0;i<keys.size();i++) {
			for(int j=0;i<list.length;j++) {
			if(temp[i]==list[j].p) {
				String s = String.format("%.3f", temp[i]);
				System.out.println("Word: "+list[j].key+"       Probability: "+s);
				list[j].p=0;
				break;
			}
			}
    	}
    	
    }
	public void sortarrays() {
		key<T>[] list = new key[keys.size()];
		for(int i = 0;i<keys.size();i++) {
			list[i]=new key(keys.get(i),freq.get(i));
		}
		Collections.sort(keys);
		for(int i=0;i<keys.size();i++) {
			for(int j=0;i<list.length;j++) {
			if(keys.get(i).compareTo((T)list[j].key)==0) {
				freq.set(i, list[j].freq);
				break;
			}
			}
		}
	}
	public void computep() {
		double sum=0;
		p=new double[freq.size()+1];
		for(int i=0;i<freq.size();i++) {
			sum+=freq.get(i);
		}
		for(int i=1;i<p.length;i++) {
			p[i]=freq.get(i-1)/sum;
		}
	}
	public double psum(int i,int j) {
		double sum=0;
		for(int x=i;x<=j;x++) {
			sum+=p[x];
		}
		return sum;
		
	}
	public void optbst() {
		int size = keys.size();
		a = new double[size+2][size+1];
		root = new int[size+1][size+1];
		for(int i=1;i<=size;i++) {
			a[i][i-1]=0;
			a[i][i]=p[i];
			root[i][i]=i;
		}
		a[size+1][size]=0;
		int j;
		double q=0;
		for(int d=1;d<size;d++) {
			for(int i=1;i<=(size-d);i++) {
				j=d+i;
				double min=Double.MAX_VALUE;
				for(int l=i;l<=j;l++) {
					q = a[i][l-1]+a[l+1][j];
					if(q<min) {
						min=q;
						root[i][j]=l;
					}
					
				}
			a[i][j]=min+psum(i,j);
			}
		}
	}
	public void buildtree() {
		int l =root[1][keys.size()];
		troot = new Node(keys.get(l-1));
		buildtree2(troot,1,keys.size());
		
			
	}
	public void buildtree2(Node<T> x, int i, int j) {
		int l=root[i][j];
		if(l<j) {
			Node<T> v = new Node(keys.get(root[l+1][j]-1));
			x.right=v;
			buildtree2(v,l+1,j);			
		}
		if(i<l) {
			Node<T> v = new Node(keys.get(root[i][l-1]-1));
			x.left=v;
			buildtree2(v,i,l-1);			
		}
		
		
		
	}
	
	
	public OptimalBST() {
		try{
			File file = new File("test.txt");
			Scanner in = new Scanner(file);
		int type=in.nextInt();
		if(type==1) {
		while(in.hasNext()) {
			addtoarrays((T)in.next());
		}
		sortarrays();
		computep();
		System.out.println("Data sorted by word: ");
		System.out.println();
		printlists();
		System.out.println();
		System.out.println("Data sorted by probability: ");
		System.out.println();
		printbyp();
		System.out.println();
		optbst();
		buildtree();
		System.out.println("Optimal Binary Search Tree:\n");
		printLevelbyLevel();
		String str = String.format("%.3f",a[1][keys.size()]);
		System.out.println("The average cost of this tree is: " + str);
		in.close();
		}
		else if(type!=1) {
		while(in.hasNext()) {
		keys.add((T)in.next());
		freq.add(in.nextInt());
		}
		sortarrays();
		computep();
		System.out.println("Data sorted by word: ");
		System.out.println();
		printlists();
		System.out.println();
		System.out.println("Data sorted by probability: ");
		System.out.println();
		printbyp();
		System.out.println();
		optbst();
		buildtree();
		System.out.println("Optimal Binary Search Tree:\n");
		printLevelbyLevel();
		String str = String.format("%.3f",a[1][keys.size()]);
		System.out.println("The average cost of this tree is: " + str);
		in.close();
		
		}
	}catch(Exception e) {
			System.out.println(e);
		}
	
	}
	public static void main(String[] args) throws Exception {
		new OptimalBST();
	}
	

}
