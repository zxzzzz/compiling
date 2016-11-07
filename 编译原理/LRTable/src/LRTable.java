import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import javax.jws.Oneway;

import com.sun.org.apache.bcel.internal.generic.IXOR;
import com.sun.xml.internal.bind.v2.TODO;

//BFS算法
public class LRTable {
	int current=0;
	Closure closure;
	public void go(){
		init();
		Queue queue=new LinkedList<Closure>();
		queue.push(closure);
		while (queue.size()!=0) {
			Closure closure=queue.pop();
			ArrayList<Character>  nextsArrayList=getNexts(closure);
			for (Character c:nextsArrayList) {
				Closure ccClosure=getClosure(closure, c);
				queue.push(ccClosure);
			}
			
		}
	}
	//将原文法集改写
	public void init(){
		closure=new Closure();
		ArrayList<Character>  lists=new ArrayList<Character>();
		lists.add('0');
		lists.add('E');
		lists.add('0');
		lists.add('+');
		lists.add('0');
		lists.add('T');
		OneClosure one=new OneClosure('E', lists);
		//todo
		closure.setCurrent(current);
		closure.setLast(0);
		
	}
	//得到closure中 c的项目
	public	Closure  getClosure(Closure closure,char c) {
		Closure closure2=new Closure();
		ArrayList<OneClosure> ocArrayList=new ArrayList<OneClosure> ();
		ArrayList<OneClosure> ones=closure.getClosuresArrayList();
		for(int i=0;i<ones.size();i++){
			char cc=ones.get(i).getOne();
			if(cc=c){
				OneClosure oneClosure=ones.get(i);
				ocArrayList.add(oneClosure);
			}
				
			}
		closure2.set
		
	
	}
	//得到closure的待分析字符  如果是 ‘0’ 未分析 
	public ArrayList<Character> getNexts(Closure closure){
		ArrayList<Character> list=new ArrayList<Character>();
		for (int i = 0; i < closure.getClosuresArrayList().size(); i++) {
			ArrayList<Character> chaArrayList=closure.getClosuresArrayList().get(i).getList();
			int count=0;
			char next;
			while (count<chaArrayList.size()) {
				char c=chaArrayList.get(count++);
				if (c=='0') {
					next=chaArrayList.get(count);
					list.add(next);
					break;
				}
			}
			
		}
		return list;
		}
}
