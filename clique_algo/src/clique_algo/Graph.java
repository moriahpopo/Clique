package clique_algo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Vector;


/**
 * this class represents an undirected 0/1 sparse Graph 
 * @author Boaz
 *
 */
 class Graph {
	 private String _file_name;
	 private Vector <VertexSet> _V;
	 private double _TH; // the threshold value
	 private int _E_size = 0;
	 private boolean _mat_flag=true;
	 Graph(String file, double th) {
		this._file_name = file;
		_TH = th;
		_V = new  Vector <VertexSet>();
		 init();
	 }
	 
	private void init() {
		FileReader fr=null;
		try {
			fr = new FileReader(this._file_name);
		} catch (FileNotFoundException e) {	e.printStackTrace();}
		BufferedReader is = new BufferedReader(fr);
		try {
			String s = is.readLine();
			StringTokenizer st = new StringTokenizer(s,", ");
			int len = st.countTokens();
			int line = 0;
			
			String ll = "0%   20%   40%   60%   80%   100%";
			int t = Math.max(1,len/ll.length());
			if(Clique_Tester.Debug){
				System.out.println("Reading a corrolation matrix of size: "+len+"*"+len+" this may take a while");
				System.out.println(ll);
			}
			_mat_flag = true;
			if (s.startsWith("A")) {
				if(Clique_Tester.Debug){
					System.out.println("Assumes compact representation! two line haeder!!!");
					System.out.println("Header Line1: "+s);
					s = is.readLine();
					System.out.println("Header Line2: "+s);
					s = is.readLine();
					st = new StringTokenizer(s,", ");
					_mat_flag = false;
				}
			}
	
			while(s!=null) {
				
				if(Clique_Tester.Debug){
					if(line%t==0) System.out.print(".");                                
				}
				VertexSet vs = new VertexSet();
				if(_mat_flag){
					for(int i=0;i<len;i++) {
						float v = new Double(st.nextToken()).floatValue();
						if(v>_TH & line< i) {
							vs.add(i);
							_E_size++;
						}
					}
				}
				else {
					st.nextToken();
					while(st.hasMoreTokens()) {
						int ind = new Integer(st.nextToken()).intValue();
						// bug fixed as for Ronens format.
						if(line<ind) vs.add(ind);
					}
				}
				this._V.add(vs);
				line++;
				s = is.readLine();
			if(s!=null)	st = new StringTokenizer(s,", ");
			}
			if(this._mat_flag & Clique_Tester.Convert) {write2file();}
			if(Clique_Tester.Debug){
				System.out.println("");
				System.out.print("done reading the graph! ");
				this.print();}
		} catch (IOException e) {e.printStackTrace();}
	 }
	public double sizeOfE(){
		return _E_size;
	}
	
	public VertexSet Ni(int i) {
		VertexSet ans = _V.elementAt(i);
		return  ans;
	}
	public void print() {
		System.out.println("Graph: |V|="+this._V.size()+" ,  |E|="+_E_size);
		
	}
	
	/*************** Clique Algorithms ******************/
	/*Vector<VertexSet>  All_Cliques(int Q_size) {
		Vector<VertexSet> ans = new Vector<VertexSet>();
		Vector<VertexSet>C0 = allEdges(); // all edges – all cliques of size 2/
		ans.addAll(C0);
		for(int i=3;i<=Q_size;i++) {
			Vector<VertexSet>C1 = allC(C0);
			ans.addAll(C1);
			C0 = C1;
		} // for
		return ans;
	}
	Vector<VertexSet>  All_Cliques(int min_Q_size, int max_Q_size) {
		Vector<VertexSet> ans = new Vector<VertexSet>();
		Vector<VertexSet> C0 = allEdges(), C1=null; // all edges – all cliques of size 2/
		for(int i=0;i<C0.size();i++) {
			VertexSet curr = C0.elementAt(i);
			C1 = All_Cliques_of_edge(curr, min_Q_size,  max_Q_size);
//			System.out.println("Edge: ["+curr.at(0)+","+curr.at(1)+"]");
			ans.addAll(C1);
		}
		return ans;
	}*/
	/**
	 * this method retuns all the Cliques of size between [min,max] which contains the subVertexSet e (usually an edge);
	 * @param min_Q_size
	 * @param max_Q_size
	 * @return
	 */
	/*
	Vector<VertexSet>  All_Cliques_of_edge(VertexSet e, int min_Q_size, int max_Q_size) {
		Vector<VertexSet> ans = new Vector<VertexSet>();
		ans.add(e);
		int i=0;
		int last_size = e.size();
		while(i<ans.size() & last_size <=max_Q_size) {
			VertexSet curr = ans.elementAt(i);
			VertexSet inter = intersection(curr);
			addbiggerCliQ(ans,curr,inter);
			last_size = ans.elementAt(ans.size()-1).size(); 
			i++;
		}
		int start = 0; i=0;
		while(i<ans.size() && start==0) {
			if(ans.elementAt(i).size()<min_Q_size) {ans.remove(0);}
			else start=1;
			i++;
		}
		return ans;
	}
	Vector<VertexSet> allC(Vector<VertexSet> C0) {
		Vector<VertexSet> ans = new Vector<VertexSet>();
		for(int i=0;i<C0.size();i++) {
			VertexSet curr = C0.elementAt(i);
			VertexSet inter = intersection(curr);
			if(inter.size()>0)  
				addbiggerCliQ(ans,curr,inter); // strange clique expqnding function
	}	
		return ans;	
	}
	VertexSet intersection(VertexSet C) {
		VertexSet ans = _V.elementAt(C.at(0));
		for(int i=0;ans.size()>0 & i<C.size();i++) 
			ans = ans.intersection(_V.elementAt(C.at(i)));
		return ans;
	}
	private void addbiggerCliQ(Vector<VertexSet> ans,VertexSet curr ,VertexSet inter) {
		int last = curr.at(curr.size()-1);
		for(int i=0;i<inter.size();i++) {
			int ind_inter = inter.at(i);
			if(last<ind_inter) {
				VertexSet c = new VertexSet(curr);
				c.add(ind_inter);
				ans.add(c);
			}
		}
	}
	private Vector<VertexSet> addbiggerCliQ(VertexSet curr ,VertexSet inter) {
		Vector<VertexSet> ans = new Vector<VertexSet>(inter.size());
		int last = curr.at(curr.size()-1); // last vertex in the current clique (ordered!)
		for(int i=0;i<inter.size();i++) {
			int ind_inter = inter.at(i);
			if(last<ind_inter) {
				VertexSet c = new VertexSet(curr);
				c.add(ind_inter);
				ans.add(c);
			}
		}
		return ans;
	}*/
	/**
	 * computes all the 2 cliques --> i.e. all the edges 
	 * @return
	 */
	private Vector<VertexSet> allEdges() { // all edges – all cliques of size 2/
		Vector<VertexSet> ans = new Vector<VertexSet>();
		for(int i=0;i<_V.size();i++) {
			VertexSet curr = _V.elementAt(i);
			for(int a=0;a<curr.size();a++) {
				if(i<curr.at(a)) {
					VertexSet tmp = new VertexSet();
					tmp.add(i) ; 
					tmp.add(curr.at(a));
					ans.add(tmp);
				}
			}
			
		}
		return ans;
	}
	/**
	 * This method computes all cliques of size [min,max] or less using a memory efficient DFS like algorithm.
	 * The implementation was written with CUDA in mind - as a based code for a possibly implementation of parallel cernal.
	 * 
	 */
	Vector<VertexSet>  All_Cliques_DFS(int min_size, int max_size) {
		Clique.init(this);
		Vector<VertexSet> ans = new Vector<VertexSet>();
		Vector<VertexSet>C0 = allEdges(); // all edges – all cliques of size 2/
	//	ans.addAll(C0);
		int len = C0.size();
		//System.out.println("|E|= "+len);
		int count = 0;
		for(int i=0;i<len;i++) {
			
			VertexSet curr_edge = C0.elementAt(i);
			Clique edge = new Clique(curr_edge.at(0),curr_edge.at(1) );
			Vector<Clique> C1 = allC_seed(edge, min_size, max_size);
			count+=C1.size();
			//System.out.println("alg2 "+i+") edge:["+curr_edge.at(0)+","+curr_edge.at(1)+"]"+C1.size() +"  total: "+count);
			addToSet(ans, C1);
		} // for
		return ans;
	}
	/**
	 * 
	 * @param min_size
	 * @param max_size
	 */
	 public void All_Cliques_DFS(String out_file, int min_size, int max_size) {
			Clique.init(this);
			Vector<VertexSet>C0 = allEdges(); // all edges – all cliques of size 2/
			int len = C0.size();
			System.out.println("|E|= "+len);
			int count = 0;
			
			FileWriter fw=null;
			try {fw = new FileWriter(out_file);} 
			catch (IOException e) {e.printStackTrace();}
			PrintWriter os = new PrintWriter(fw);
			//os.println("A");
			
			String ll = "0%   20%   40%   60%   80%   100%";
			int t = Math.max(1,len/ll.length());
			if(Clique_Tester.Debug){
				System.out.println("Computing all cliques of size["+min_size+","+max_size+"] based on "+len+" edges graph, this may take a while");
				System.out.println(ll);
			}
			os.println("All Cliques: file [min max] TH,"+this._file_name+","+min_size+", "+max_size+", "+this._TH);
			os.println("index, edge, clique size, c0, c1, c2, c3, c4,  c5, c6, c7, c8, c9");
			for(int i=0;i<len;i++) {
				
				VertexSet curr_edge = C0.elementAt(i);
				Clique edge = new Clique(curr_edge.at(0),curr_edge.at(1) );
				Vector<Clique> C1 = allC_seed(edge, min_size, max_size);
			
				
				for(int b=0;b<C1.size();b++) {
					Clique c = C1.elementAt(b);
					if (c.size()>=min_size) {
						os.println(count+", "+i+","+c.size()+", "+c.toFile());
						count++;
					}
				}
				if(count > Clique_Tester.MAX_CLIQUE) {
					os.println("ERROR: too many cliques! - cutting off at "+Clique_Tester.MAX_CLIQUE+" for larger files change the default Clique_Tester.MAX_CLIQUE param");
					i=len;
				}
				if(i%t==0) {
					System.out.print(".");
				}
			} // for
			System.out.println();
			
			os.close();
			try {
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	
	/**
	 * this function simply add the clique (with no added intersection data) to the set of cliques)
	 * @param ans
	 * @param C1
	 */
	private void addToSet(Vector<VertexSet> ans, Vector<Clique> C1) {
		for(int i=0;i<C1.size();i++) {
			ans.add(C1.elementAt(i).clique());
		}
	}
	Vector<Clique> allC_seed(Clique edge, int min_size, int max_size) {
		Vector<Clique> ans = new Vector<Clique>();
		ans.add(edge);
		int i=0;
	//	int size = 2;
		while (ans.size()>i) {
			Clique curr = ans.elementAt(i);
			if(curr.size()<max_size) {
				VertexSet Ni = curr.commonNi();
				if(curr.size()+Ni.size()<min_size){}
				else{
				for(int a=0;a<Ni.size();a++) {
					Clique c = new Clique(curr,Ni.at(a));
					ans.add(c);
				}
				}
			}
			else {i=ans.size();} // speedup trick 
			i++;
		}
		
		return ans;
	}

	public void write2file() {
		FileWriter fw=null;
		try {fw = new FileWriter(this._file_name+"_DG.txt");} 
		catch (IOException e) {e.printStackTrace();}
		PrintWriter os = new PrintWriter(fw);
		os.println("ALL_Cliques: of file: "+_file_name+",  TH:"+this._TH);
		os.println("");
		for(int i=0;i<this._V.size();i++) {
			VertexSet curr = _V.elementAt(i);
			os.println(i+", "+curr.toFile());
		}
		os.close();
		try {
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
//	Memory - insert only one EDGE every time to QUE.
//	insert e to q
//	max=2
//	{
//	while q is not empty
//	cl=q.pop
//	if (cl>max) max=cl
//	if(cl+ni.cl>max)
//	q.push(cl+n) ---(n is foreach of cl.ni)
//	}
//	return max
//	choose benchbark

	//show schema for spreading the work on different computers
	
	/**
	 * this function finds the max size clique from all cliques.
	 */
	public int MaxSizeAllClique(){
		Clique.init(this);
		int maxsize=2;
		int temp=2;
		Vector<VertexSet>C0 = allEdges(); // all edges – all cliques of size 2/
		Vector<VertexSet> ans = new Vector<VertexSet>();
		int len = C0.size();
		for(int i=0;i<len;i++) {
			VertexSet curr_edge = C0.elementAt(i);
			Clique edge = new Clique(curr_edge.at(0),curr_edge.at(1) );
			temp=checkSizeSpecCliqebyedge(edge);
			if(temp>maxsize)maxsize=temp;
			
			//System.out.println("alg2 "+i+") edge:["+curr_edge.at(0)+","+curr_edge.at(1)+"]"+C1.size() +"  total: "+count);
			//addToSet(ans, C1);
		} 
	ans=findAllCliqueswithMaxSize(maxsize);
	return maxsize;
	}
	/**
	 * this function checks  size of clique by edges.
	 */
	public int checkSizeSpecCliqebyedge(Clique e){
		int max = 2;
		Stack<Clique>st = new Stack<Clique>();
		st.push(e);
		while(!st.isEmpty()){
			Clique curr=st.pop();
			if(curr.size()>max)max=curr.size(); 
				VertexSet Ni = curr.commonNi();
				if(curr.size()+Ni.size()>max){
				for(int a=0;a<Ni.size();a++) {
					Clique c = new Clique(curr,Ni.at(a));
					st.push(c);
				}
				}
		}
		return max;
	}
	/**
	 * this function finds all cliques with max size
	 */
	
public Vector<VertexSet> findAllCliqueswithMaxSize(int max){
		Vector<VertexSet> V1;
	    V1 = All_Cliques_DFS(max, max);
	    return V1;
	
}




}
