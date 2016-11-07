import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

import javafx.scene.shape.Cylinder;



//LR(1)分析法
/*
 * 文法：
 * E'->#E#
 * E->E+T|T
 * T->T*F|F
 * F->(E)|i
 * 产生式序号如下：
 * 1.E->E+T
 * 2.E->T
 * 3.T->T*F
 * 4.T->F
 * 5.F->(E)
 * 6.F-i
 */
public class LR {
	//文法G
	Grammer[] grammers=new Grammer[6];
	LinkedList<Integer> stateStack=new LinkedList<Integer>();//状态栈
	LinkedList<Character>signStack=new LinkedList<Character>();//符号栈
	ArrayList<Character> inputList=new ArrayList<Character>();//输入字符流
	ArrayList<String> currentAction=new ArrayList<String>();//分析动作
		String inputString;//输入字符串
				//acc :100
				//		error:-100
				//s:0-11
				//r:-1- -6
				//		+      *   (   )  i  #    E T   F  

	int[][]  action={{-100,-100,4,-100,5,-100,1,2,3},
			{6,-100,-100,-100,-100,100,-100,-100,100},
			{-2,7,-100,-2,-100,-2,-100,-100,-100},
			{-4,-4,-100,-4,-100,-4,-100,-100,-100},
			{-100,-100,4,-100,5,-100,8,2,3},
			{-6,-6,-100,-6,-100,-6,-100,-100,-100},
			{-100,-100,4,-100,5,-100,-100,9,3},
			{-100,-100,4,-100,5,-100,-100,-100,10},
			{6,-100,-100,11,-100,-100,-100,-100,-100},
			{-1,7,-100,-1,-100,-1,-100,-100,-100},
			{-3,-3,-100,-3,-100,-3,-100,-100,-100},
			{-5,-5,-100,-5,-100,-5,-100,-100,-100}};
	//分析表
	public LR(String input){
		this.inputString=input;
		
	}
	//分析流程
	public void go(){
		init();
		inputList=convert(inputString);
		while(true){
		int peekState=stateStack.peek();//状态栈栈顶元素
		//char peekSIgn=;//符号栈栈顶元素
		for (char c:inputList) {
			System.out.print(c);
		}
		System.out.println("状态栈：");
		for (int i=0;i<stateStack.size();i++) {
			System.out.print(stateStack.get(i));
		}
		System.out.println("符号栈：");
		for (int i = 0; i < signStack.size(); i++) {
			System.out.print(signStack.get(i));
		}
		System.out.println("动作：");
		for (int i = 0; i < currentAction.size(); i++) {
				System.out.print(currentAction.get(i));
		}
		System.out.println("剩余字符串：");
		for (int i = 0; i < inputList.size(); i++) {
			System.out.print(inputList.get(i));
		}
		char peekInput=inputList.get(0);//要读取的字符 
		int next=getAction(peekState, peekInput);
		String current=matchAction(next);
		currentAction.add(current);
		if (next==100) {
			print();
			break;
			
		}else if (next==-100) {
			error("action表中不存在！");
			break;
		} 
		if (next>=0) {
			shiftIn(peekState,peekInput);
			continue;
			
		}else if (next<0) {
			normalize(next);
			continue;
			
		}
		
		}
	}
	//将输入的字符串转换为字符流
	public ArrayList<Character> convert(String input){
		System.out.println("调用 convert():"+input);
		ArrayList<Character> list=new ArrayList<Character>();
		for (int i = 0; i < input.length(); i++) {
			list.add(input.charAt(i));
		}
		return list;
	}
	
	//初始化分析栈符号栈状态栈 action表 goto表  产生式
	public void init(){
		System.out.println("调用 init(),初始化");
		stateStack.push(0);
		signStack.push('#');
		Grammer grammer1=new Grammer("E+T", 'E');
		Grammer grammer2=new Grammer("T", 'E');
		Grammer grammer3=new Grammer("T*F",'T' );
		Grammer grammer4=new Grammer("F", 'T');
		Grammer grammer5=new Grammer("(E)", 'F');
		Grammer grammer6=new Grammer("i", 'F');
		grammers[0]= grammer1;
		grammers[1]=grammer2;
		grammers[2]= grammer3;
		grammers[3] =grammer4;
		grammers[4] =grammer5;
		grammers[5]= grammer6;
		
	}
	/*移进   state:S n
	 * state入状态栈
	 * peekInput入符号栈
	 * inputList移除peekInput
	 */
	public void shiftIn(int state,char sign){
		System.out.println("调用 shiftIn(),移进。状态："+state+" 字符："+sign);
		int  i=getAction(state,sign );
		stateStack.push(i);
		signStack.push(sign);
		inputList.remove(0);
		
		
		
	}
	//归约  state r-state
	public void normalize(int state){
		System.out.println("调用 normalize(),状态："+state);
		state=-state;
		System.out.println("state:"+state);
		Grammer grammer=grammers[state-1];
		System.out.println("用产生式"+state+"归约");
		String vt=grammer.getSign();
		char vN=grammer.getVN();
		int l=vt.length();
		for (int i = 0; i < l; i++) {
			char c=signStack.pop();
			System.out.println("符号栈弹出:"+c);
			int  s=stateStack.pop();//栈顶元素弹出
			System.out.println("状态栈弹出："+s);
			
		}
		signStack.push(vN);
		System.out.println("符号栈压入："+vN);
		int row =stateStack.peek();
		int actionState=getAction(row, vN);
		if (actionState>=0) {
			
			stateStack.push(actionState);
			System.out.println("状态栈压入："+actionState);
			
		}
		
		else {
			error("goto表出错！");
		}
	}
	//得到分析表的动作  如 -1 代表 r1
	public int  getAction(int row,char c){
		System.out.println("调用 getAction(),行："+row+" 字符："+c);
		int  column=0;
		switch (c) {
		case '+':
			column=0;
			break;
		case '*':
			column=1;
			break;
		case '(':
			column=2;
			break;
		case ')':
			column=3;
			break;
		case 'i':
			column=4;
			break;
		case '#':
			column=5;
			break;
		case 'E':
			column=6;
			break;
		case 'T':
			column=7;
			break;
		case 'F':
			column=8;
			break;
		default:
			break;
		}
		int a=action[row][column];
		return a;
		
	}
	//分析表中数字相应的动作匹配
	public String matchAction(int i){
		System.out.println("调用 matchAction(), 状态："+i);
		String string=null;
		if (i==-100) {
			string="error";
		}
		if(i<0)
			string="r"+-i;
		if (i>=0) {
			string="s"+i;
			
		}
		if(i==100)
			string="acc";
		
		return string;
	}
	public void print(){
		System.out.println("分析结束");
		System.out.println("分析栈：");
		for (int i=0;i<currentAction.size();i++) {
			System.out.println(currentAction.get(i));
			
		}
		System.out.println("接受！");
	}
	public void error(String cause){
		System.out.println("error:"+cause);
	}
}
