import java.util.ArrayList;
import java.util.HashSet;



//递归下降语法解析器
public class Parser {
			static String contextString;
			
			public static int count=0;//移动的下标
			public static HashSet<String>  keyWord= new HashSet<String>();public static ArrayList<String> keyWordTable=new ArrayList<String>();//保留字数组
			public static ArrayList<Integer> digittable=new ArrayList<Integer>();//数字数组
			public static ArrayList<String> otherCharTable=new ArrayList<String>();//其他标识符数组
			public static 	ArrayList<String> idTable=new ArrayList<String>();//标识符数组
			public static ArrayList<String> noteTable=new ArrayList<String>();//注释数组
			public	static ArrayList<String> finalTable=new ArrayList<String>();//终结符数组
			public static int []finaTableInt=new int[100];
			public static char [] word=new char[20];
			public static int finalNum=0,flagError=0,final_num=0;
			public Parser(String inputStream){
				System.out.println("输入的字符流:"+inputStream);
				this.contextString=inputStream;
		//		{"if","for","else","while","do","float","int","break"};
			keyWord.add("if");
			keyWord.add("for");
			keyWord.add("else");
			keyWord.add("while");
			keyWord.add("do");
			keyWord.add("float");
			keyWord.add("float");
			keyWord.add("int");
			keyWord.add("break");
			
			}
		
			public static void go(){
				System.out.println("调用：go()");
					//判断是不是数字
			while(contextString.charAt(count)!='#'){
				char lookAhead=contextString.charAt(count);
				System.out.println("向前看字符："+lookAhead);
				if (isAlpha(lookAhead)) {
					alpha();
					init();
					continue;
				}
				else if (isDigital(lookAhead)) {
					digit();
					init();
					continue;
					//如果为空格或 tab键 或换行符
				}else if (lookAhead=='\t'||lookAhead==' '||lookAhead=='\n') {
					continue;
				}
				//判断是否为注释 /×
				else if (lookAhead=='/') {
					System.out.print("// count:"+count);
					lookAhead=contextString.charAt(++count);
					if (lookAhead=='*') {
						note();
						init();
						
						
					}
					//是 ‘/’
					else {
						System.out.println("/");
						count--;
						finalTable.add("/");
						otherCharTable.add("/");
						finaTableInt[final_num++]=33;   //  /33
						init();
						count++;
						
					}
					continue;
				}
					else {
					otherChar();
					init();
					continue;
				}
}
//无法访问的语句   ：死循环了
				if (flagError==0) {
					print();
					//开始语法分析...
					program();
					System.out.println("结束分析");
		//			System.out.println("分析过程....");	
				}
			}
			
			public static boolean isAlpha(char c){
				System.out.println("调用：isAlpha()");
				return (c>='a'&&c<='z')||(c>='A'&&c<='Z')?true:false;
			}
			public static boolean isDigital(char c){
				System.out.println("调用：isDigital()"+"count:"+count);
				 return (c>='0'&&c<='9');
			
			}
			public static void alpha(){
				System.out.println("调用：alpha()");
				int i=1;
				boolean tag=false;
				char c=contextString.charAt(count++);
				System.out.println("count:"+count+"c:"+c);
				
				word[0]=c;
				c=contextString.charAt(count++);
				//标识符以字母开头，以字母或数字组成
				while (isAlpha(c)||isDigital(c)) {
					word[i++]=c;
				//	System.out.println("word:"+c);
					c=contextString.charAt(count++);
				//	System.out.println("count:"+count);
				}
				String tokenString=String.valueOf(word);
			//	System.out.println(tokenString+"1");
				//System.out.println("count:"+count);
//为啥valueOf 后 调用 equals不起作用！！！！ ///空格的原因。。。。。。。。。。。。。。。。。。。。。。
				//删掉空格    
					if(keyWord.contains(tokenString.trim())){
						System.out.println("相等");
						tag=true;
					}
				count--;
				System.out.println("alpha:"+tokenString);
				//保留字
				if(tag){
					System.out.println("保留字："+tokenString);
					keyWordTable.add(tokenString);//将保留字加入到保留字列表中
					finalTable.add(tokenString);
					if(tokenString.equals("if"))
							finaTableInt[final_num++]=25;//if==1
					if(tokenString.equals("for"))
							finaTableInt[final_num++]=26;//for ==2;
					if(tokenString.equals("else"))
							finaTableInt[final_num++]=27;// else ==3
					if(tokenString.equals("while"))
						finaTableInt[final_num++]=28;//while==4
					if(tokenString.equals("do"))
						finaTableInt[final_num++]=29;//do ==5
					if (tokenString.equals("float"))
						finaTableInt[final_num++]=30;
					if(tokenString.equals("int"))
							finaTableInt[final_num++]=31;
							
					if (tokenString.equals("break"))
						finaTableInt[final_num++]=32;
				}
				//标识符
				else {
					System.out.println("标识符："+tokenString);
					idTable.add(tokenString);
					finalTable.add(tokenString);
					finaTableInt[final_num++]=1;//标识符序号为1
				}
					
						
							
					
					
				}
			
			//初始化 清空数组
			public static  void init() {
				System.out.println("调用：init()");
				for (int i = 0; i < word.length; i++) {
					word[i]='\0';
					
				}
				
			}
			//数字
			public	static void digit(){
				System.out.println("调用：digit()");
				int i=1;
				boolean tag=false;
				char c=contextString.charAt(count++);
				word[0]=c;
				c=contextString.charAt(count++);
				while (isDigital(c)||isAlpha(c)) {
					if (count<contextString.length())
						word[i++]=c;
						c=contextString.charAt(count++);
					}
				count--;
				System.out.println("word:"+String.valueOf(word));
				for (int j = 0; j <i; j++) {
					//如果是标识符
					System.out.println("word:"+word[j]);
					if (!isDigital(word[j])) {
						tag=true;
						
					}
				}
				String s=String.valueOf(word);
				System.out.println("digial:"+s);
				
				//是标识符
				if (tag) {
					System.out.println("标识符："+s);
					idTable.add(s);
					finalTable.add(s);
					finaTableInt[final_num++]=1;
					
				}else {
					System.out.println("数字："+s);
					int t=Integer.parseInt(s.trim());
					digittable.add(t);
					finalTable.add(s);
					finaTableInt[final_num++]=2;//数字的序号为2
				}

		}
			
			//出错处理
			public static void error(String cause){
				System.out.println("调用：error()");
				flagError=1;
				System.out.print("出错:"+cause+" 中止！");
				
			}
			//其他标识符
			public static void otherChar(){
				System.out.println("调用：otherChar()");
				char c=contextString.charAt(count++);
				switch (c) {	
				case '!':{
					// !=
					c=contextString.charAt(count++);
					if (c=='=') {
						System.out.println("其他字符：!=");
						otherCharTable.add("!=");
						finalTable.add("!=");
						finaTableInt[final_num++]=3; //!= 3
						
					}else {
						System.out.println("其他字符：!");
						count--;
						otherCharTable.add("!");
						finalTable.add("!");
						finaTableInt[final_num++]=4;  //! 4
					}
					
				}
					
				break;
				case '=':
					c=contextString.charAt(count++);
					if (c=='=') {//==
						System.out.println("其他字符：==");
						otherCharTable.add("==");
						finalTable.add("==");
						finaTableInt[final_num++]=5; //== 5
					}else{
						System.out.println("其他字符：=");
						otherCharTable.add("=");
						finalTable.add("=");
						finaTableInt[final_num++]=6; //= 6
						count--;
					}
					break;
				case '('://7
					System.out.println("其他字符：(");
					otherCharTable.add("(");
					finalTable.add("(");
					finaTableInt[final_num++]=7;
					break;
				case ')'://8
					System.out.println("其他字符：)");
					otherCharTable.add(")");
					finalTable.add(")");
					finaTableInt[final_num++]=8;
					break;
				case ';'://9
					System.out.println("其他字符：;");
					otherCharTable.add(";");
					finalTable.add(";");
					finaTableInt[final_num++]=9;
					break;
				case '{'://10
					System.out.println("其他字符：{");
					otherCharTable.add("{");
					finalTable.add("{");
					finaTableInt[final_num++]=10;
					break;
				case '}'://11
					System.out.println("其他字符：}");
					otherCharTable.add("}");
					finalTable.add("}");
					finaTableInt[final_num++]=11;
					break;
				case '|'://12 13
					c=contextString.charAt(count++);
					if (c=='|') {
						System.out.println("其他字符：||");
						otherCharTable.add("||");
						finalTable.add("||");
						finaTableInt[final_num++]=12; //||==12
						
					}else {
						count--;
						System.out.println("其他字符：|");
						otherCharTable.add("|");
						finalTable.add("|");
						finaTableInt[final_num++]=13;  //|==13
					}
					
					break;
				case '&': //14 15
					c=contextString.charAt(count++);
					if (c=='&') {
						System.out.println("其他字符：&&");
						otherCharTable.add("&&");
						finalTable.add("&&");
						finaTableInt[final_num++]=14; //&&==14
						
					}else {
						System.out.println("其他字符：&");
						count--;
						otherCharTable.add("&");
						finalTable.add("&");
						finaTableInt[final_num++]=15;  //&==15
					}
					
					break;
				case '+'://16
					System.out.println("其他字符：+");
					otherCharTable.add("+");
					finalTable.add("+");
					finaTableInt[final_num++]=16;
					break;
				case '-'://17
					System.out.println("其他字符：-");
					otherCharTable.add("-");
					finalTable.add("-");
					finaTableInt[final_num++]=17;
					break;
				case '>'://18 19 20
				
					c=contextString.charAt(count++);
					if (c=='>') {
						System.out.println("其他字符：>>");
						otherCharTable.add(">>");
						finalTable.add(">>");
						finaTableInt[final_num++]=18; //>>==18
						
					}else if (c=='=') {
						System.out.println("其他字符：>=");
						otherCharTable.add(">=");
						finalTable.add(">=");
						finaTableInt[final_num++]=19; //>===19
					}
					else {
						System.out.println("其他字符：>");
						count--;
						otherCharTable.add(">");
						finalTable.add(">");
						finaTableInt[final_num++]=20;  //> 20
					}
					
					break;
				case '<'://21 22 23
					c=contextString.charAt(count++);
					if (c=='<') {
						System.out.println("其他字符：<<");
						otherCharTable.add("<<");
						finalTable.add("<<");
						finaTableInt[final_num++]=21; //<< 21
						
					}else if (c=='=') {
						System.out.println("其他字符：<=");
						otherCharTable.add("<=");
						finalTable.add("<=");
						finaTableInt[final_num++]=22; // <= 22
					}
						 
						else {
							System.out.println("其他字符：<");
						count--;
						otherCharTable.add("<");
						finalTable.add("<");
						finaTableInt[final_num++]=23;  //< 23
					}
					
					break;
				
				case '*'://24
					System.out.println("其他字符：*");
					otherCharTable.add("*");
					finalTable.add("*");
					finaTableInt[final_num++]=24;
					break;
				default:
					error("输入了不合法的符号，otherChar()！");
					break;
				}
			}
			//注释
			public static void note(){
				System.out.println("调用：note");
				char c=contextString.charAt(count++);
				int i=0;
				while (true) {
					if (c=='*') {
						c=contextString.charAt(count++);
						if (c=='/') {  /* */
	 						break;
							
						}else {
							count--;	//是   /**...
							word[i++]=c;
							
						}
					}else {
						word[i++]=c;  //   /*.....
				}
					c=contextString.charAt(count++);
					
			}
			String noteString=String.valueOf(word);
			System.out.println("注释:"+noteString);
			noteTable.add(noteString);
		}
			//输出语句
			public static void print(){
				System.out.println("语法分析结果：");
				System.out.println("终结符列表：");
				for (String s:finalTable){
					System.out.println(s);
				}
				System.out.println(" ---------------------");
				System.out.println("保留字列表：");
				for (String s:keyWordTable) {
					System.out.print(s+" ");
				}
				System.out.println(" ---------------------");
				System.out.println("标识符列表：");
				for(String s:idTable){
					System.out.print(s+" ");
				}
				System.out.println(" ---------------------");
				System.out.println("数字列表：");
				for(int s:digittable){
					System.out.print(s+" ");
				}
				System.out.println(" ---------------------");
				System.out.println("其他标识符列表：");
				for(String s:otherCharTable){
					System.out.print(s+" ");
				}
				System.out.println(" ---------------------");
				System.out.println("注释列表：");
				for(String s:noteTable){
					System.out.print(s+" ");
				}
				System.out.println(" ---------------------");
				System.out.println("finalTableInt：");
				for(int s:finaTableInt){
					System.out.print(s+" ");
				}
				System.out.println(" ---------------------");
				
				
				
			}
			/*
			 * 文法
			 */
			//
			public static void program(){
				System.out.println("program -->block");
				block();
				if (flagError==1) {
					error("program");
					return;
					
				}
			}
			public static void block(){
				if(flagError==1){
					error("block");
					return;
				}
				System.out.println("block-->{stmts}");
				match("{");
				stmts();
				match("}");
			}
			public static void stmts(){
				if (flagError==1) {
					error("stmts");
					return;
				}
				if(finaTableInt[finalNum]==11){ //follow集  }
					System.out.println("stmts-->null");
					return ;
					
				}
				System.out.println("stmts-->stm stmts");
				stms();
				stmts();
			}
			public static void stms(){
				if (flagError==1) {
					error("stms");
					return;
				}
				switch (finaTableInt[finalNum]) {
				case 1://标识符
					System.out.print("stmt-->id=expr;");
					match("id");
					match("=");
					expr();
					match(";");
					break;
				case 25://if
					match("if");
					match("(");
					bool();
					match(")");
					stms();
					if (finalTable.get(finalNum).equals("else")) {
						System.out.println("stms-->if(bool) stms else stms");
						match("else");
						stms();
						break;
						
					}else {
						System.out.print("stms-->{if(bool)stms");
						break;
					}
				case 28://while
					System.out.println("stmt-->while(bool) stms");
					match("while");
					match("(");
					bool();
					match(")");
					stms();
					break;
				case 29://do
					System.out.println("stms--do stmt while (bool)");
					match("do");
					stmts();
					match("while");
					match("(");
					bool();
					match(")");
					break;
				case 32:  //break
					System.out.println("sms-->break");
					match("break");
					break;

				default:
					System.out.println("stms-->block");
					block();
					break;
				}
			}
			public static void bool(){
				if (flagError==1) {
					error("bool");
					return;
					
				}
				expr();
				switch (finaTableInt[finalNum]) {
				case 23:
					System.out.println("bool-->expr<expr");// <
					match("<");
					expr();
					break;
				case 22:
					System.out.println("bool-->expr<=expr"); // <=
					match("<=");
					expr();
					break;
				case 20://>
					System.out.println("bool-->expr>expr");
					match(">");
					expr();
					break;
				case 19://>=
					System.out.print("bool-->expr>=expr");
					match(">=");
					expr();
					break;

				default:
					System.out.println("boo-->expr");
					expr();
					break;
				}
				
			}
			public static void expr(){
				
				if (flagError==1) {
					error("expr");
					return;
				}
				System.out.println("expr-->term expr1");
				term();
				expr1();
			}
			public static void expr1(){
				if (flagError==1) {
					error("expr1");
					return;
					}
				switch (finaTableInt[finalNum]) {
				case 16: //+
					System.out.println("expr1-->+term expr1");
					match("+");
					term();
					expr1();
					break;
				case 17://-
					System.out.println("expr1-->-term expr1");
					match("-");
					term();
					expr1();
					break;
				default:
					System.out.println("expr1-->null");
					break;
				}
			}
			public static void term(){
				if (flagError==1) {
						error("term");
						return;
					
				}
				System.out.println("term-->factor term1");
				factor();
				term1();
			}
			public static void term1(){
				if (flagError==1) {
					error("term1");
					return;
				}
				switch (finaTableInt[finalNum]) {
				case 24://*
					System.out.println("term1-->*factor term1");
					match("*");
					factor();
					term1();
					break;
				case 31:///    /
					System.out.println("term1-->/factor term1");
					match("/");
					factor();
					term1();
					break;
				default:
					System.out.println("term1-->null");
					break;
				}
				
			}
			public static void factor(){
				if (flagError==1) {
					error("factor");
					return;
					
				}
				switch (finaTableInt[finalNum]) {
				case 7:///   (
					System.out.println("factor-->(expr)");
					match("(");
					expr();
					match(")");
					break;
				case 1:////  id
					System.out.println("factor-->id");
					match("id");
					break;
				case 2://///  num
					System.out.println("factor-->num");
					match("num");
					break;

				default:
					flagError=1;
					break;
				}
				
			}
			/*
			 * 是否匹配   :检查终结符表中是否有相应的终结符
			 */
			public static void match(String m){
				System.out.println("调用：match()");
				System.out.println("m:"+m);
				boolean tag=false;
				for (String s:finalTable) {
					if (m.equals(s)) {
						tag=true;
					}
					
				}
				if (!tag) {
					flagError=1;
					return;
				}
				finalNum++;
				System.out.println("finalNum:"+finalNum);
			}
			
			
	

}
