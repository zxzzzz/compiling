import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

//算符优先分析法
/*
 * 文法：
 * Z->aMb
 * M->(L|c
 * L->c
 */
public class Prior {
	String remain;//待分析的字串
	ArrayList<Character>  remainStringArrayList=new ArrayList<Character> ();//待分析的字符流
	LinkedList<Character> analyzeStackLinkedList=new LinkedList<Character>();//分析栈
	char sign;//待分析的字符
	char peekStack;//栈顶元素
	char  c;//优先级
	static boolean errorFlag=true;//是否出错 
	HashMap<String,Character> storeRules=new HashMap<String,Character>();//文法
	//优先级表
	char[][] priorTable=
		/*
		 * !:不存在
		 * <:优先级低
		 * >:优先级高
		 * =:优先级相等
		 * $:结束
		 *
		 * a   b    c    (   )   #
		 */
			{{'!','=','<','<','!','!'},//a
			{'!','!','!','!','!','>'},//b
			{'!','>','!','!','=','!'},//c
			{'!','>','<','!','!','!'},//(
			{'!','>','!','!','!','='},//)
			{'<','!','!','!','!','$'}};//#
	public Prior(String input){
		this.remain=input;
		storeRules.put("aMb",'Z');
		storeRules.put("(L",'M');
		storeRules.put("c",'M');
		storeRules.put("c)",'L');
		
	}
	//将待分析的字符串转换成字符流
	public void convert(String s){
		System.out.println("字符串转换为字符流：convert()");
		for (int i = 0; i < s.length(); i++) {
			remainStringArrayList.add(s.charAt(i));
			
		}
		remainStringArrayList.add('#');
		
	}
	public void go(){
		System.out.println("开始分析：go()");
		convert(remain);
		System.out.println("待分析的字串："+remain);
		analyzeStackLinkedList.push('#');
		while (c!='$') {
			if(errorFlag==false)
				return;
			sign=remainStringArrayList.get(0);
			peekStack=analyzeStackLinkedList.peek();
			if(peekStack!='a'&&peekStack!='b'&&peekStack!='c'&&peekStack!='('&&peekStack!=')'&&peekStack!='#')
				 peekStack=analyzeStackLinkedList.get(1);
				
			
		if (sign!='a'&&sign!='b'&&sign!='c'&&sign!='('&&sign!=')'&&sign!='#') {
			error("输入的字符不属于文法中！");
			break;
		}
		int row=analyzer(peekStack);
		int column=analyzer(sign);
		System.out.println("行："+row+" 列： "+column+"sign:"+sign+"peekStack:"+peekStack);
		if (row<0||row>=6||column<0||column>=6) {
			error("不属于优先级表！");
			break;
			
		}
		 c=getPrior(row,column);
		System.out.println("优先级："+c);
		if (c=='>') {
			normalize();
			continue;
			
		}else if(c=='<'||c=='='){
			shiftIn();
			continue;
			


		}else if (c=='$') {
			print();
			break;
			
		}else {
			error("其他错误！");
			break;
		}
}
	}
//行列
	public int analyzer(char c){
		System.out.println("求出行列：analyzer()");
		switch (c) {
		case 'a':
			return 0;
		case 'b':
			return 1;
		case 'c':
			return 2;
		case '(':
			return 3;
		case ')':
			return 4 ;
		case '#':
			return 5;

		default:
			return -1;
		}
		
		
	}
//比较优先级
	public char getPrior(int row ,int column){
		System.out.println("优先级：getPrior()");
		return priorTable[row][column];
	}
//移进
	public void shiftIn(){
		System.out.println("移进：shiftIn()");
		remainStringArrayList.remove(0);
		System.out.print("分析栈：");
		for (char  ccc:analyzeStackLinkedList) {
			System.out.print(ccc);
		}
		analyzeStackLinkedList.push(sign);
		System.out.println("要移进的字符："+sign);
		
		
}
//归约  这里有问题
	public void normalize(){
		System.out.println("归约：normalize()");
		char y='=';
		int i=0;
		StringBuilder builder=new StringBuilder();
		System.out.println("分析栈的元素：");
		for(char c:analyzeStackLinkedList)
			System.out.println(c);
		while (true){
			char one=analyzeStackLinkedList.get(i);
		System.out.println("one:"+one+"i:"+i);
		while(one!='a'&&one!='b'&&one!='c'&&one!='('&&one!=')'&&one!='#')
				 one=analyzeStackLinkedList.get(++i);
			
			char two=analyzeStackLinkedList.get(++i);
		while(two!='a'&&two!='b'&&two!='c'&&two!='('&&two!=')'&&two!='#')
				 two=analyzeStackLinkedList.get(++i);
		System.out.println("two:"+two+"i:"+i);
			int column=analyzer(one);
			int row=analyzer(two);
			System.out.println("one: "+one+"two: "+two+"行： "+row+"列： "+column+"优先级：");
		//	System.out.println("row"+row+"column"+column);
			y=getPrior(row, column);
			System.out.println("prior:"+y);
				
			//if (y=='=') {
				//i=i+1;
			//	continue;
			
		//	}
			if(y=='<') {
				i=i-1;
				break;
				}
			
		}
		for(int j=i;j>=0;j--){
			builder.append(String.valueOf(analyzeStackLinkedList.get(j)));
//			analyzeStackLinkedList.pop();
}
		for(int m=0;m<=i;m++)
			analyzeStackLinkedList.pop();
		String ssString=builder.toString();
		System.out.println("ssString:"+ssString);
		if(!storeRules.containsKey(ssString)){
			error("输入的句子不符合文法！");
			return ;
			}
		char cc=storeRules.get(ssString);
		System.out.println("要归约的串："+ssString+" 归约成： "+cc);
		analyzeStackLinkedList.push(cc);
		System.out.println("分析栈的元素：");
		for(char c:analyzeStackLinkedList)
			System.out.println(c);
		
	}
	public void error(String cause){
		errorFlag=false;
		System.out.println("出错:"+cause+"  ,中止分析");
	}
	public void print(){
		System.out.println("结束分析");
	}

}
