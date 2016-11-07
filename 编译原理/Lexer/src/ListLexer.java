import java.awt.geom.FlatteningPathIterator;
import java.nio.Buffer;
import java.util.logging.Logger;

import javax.print.attribute.standard.MediaSize.Other;
import javax.swing.plaf.metal.MetalBorders.Flush3DBorder;
import javax.print.attribute.standard.RequestingUserName;

public class ListLexer extends Lexer{
	public ListLexer(String input) {
		// TODO Auto-generated constructor stub
		super(input);
	}
	//判断括号是否有相应的匹配
	boolean bigLTrue=false;
	boolean bigRTrue=false;
	boolean middleL=false;
	boolean middleR=false;
	public static int ADD=2;//+
	public static int SUB=3;//-
	public static int MULTI=4;//*
	public static int DEVIDE=5;// /
	public static int BAIFEN=6;//%
	public static int SHARP=7;//<>
	public static int BIG=8;//{}
	public static int FENHAO=9;//;
	public static int	MIDDLE=10;//[]
	public static int YIWEIL=11;//<<
	public static int SAME=12;//=
	public static int NUM=13;//数字
	public static int SAVETOKEN=14;//保留字
	public static int	NAME=15;//标识符
	public static int EXPLAIN=16;//注释
	public	static	int SMALLAS=17;//<
	public	static	int BIGAS=18;//>
	public	static	int SAMETWO=19;//==
	public  static	int	YIWEIR=20;//>>
	public 	static	int BIGR=21;//}
	public 	static	int MIDDLER=22;//]
	public static String[] TOKENNAME={"n/a","结束符","加号","减号","乘号","除号","百分号","尖括号","左大括号","分号","左中括号","左移位符","等于号","数字","保留字","标识符","注释","小于号","大于号","比较运算符","右移位符","右大括号","右中括号"};
	public static String[] SaveToken={"if","then","else","end","procedure","repeat","while","read","write","int","unitil","char","const"};
	@Override
	public String getTokenName(int index) {
		// TODO Auto-generated method stub
		return TOKENNAME[index];
	}

	@Override
	public boolean isLeftA() {
	//	StringBuilder builder=new StringBuilder();
		// TODO Auto-generated method stub
		return c>='a'&&c<='z'||c>='A'&&c<='Z';//      ||为或
}
	//判断是不是‘/’
	public boolean isDelete() {
		return c=='/';
		
	}
	//判断是不是数字
	public boolean isNum() {
		return c=='1'||c=='2'||c=='3'||c=='4'||c=='5'||c=='6'||c=='7'||c=='8'||c=='9'||c=='0';
	}
	public Token nextToken(){
		while(c!=EOF){
		switch (c) {
		//case的写法可以多个条件连起来一起用 空格，横向跳格，换行，回车
		//continue跳到循环的首部
		case '\n':case '\t':case ' ':case '\r':jump();continue;//
		case '+':
			flagBuffer();
			return new Token(ADD, "+");
		case '-':
			flagBuffer();
			return new Token(SUB, "-");
		case '*':
			flagBuffer();
			return new Token(MULTI, "*");
		case '%':
			flagBuffer();
			return new Token(BAIFEN, "%");
		case '='://与==有关系
			return whichSame();
		case '<'://与<>和<< 有关系
			return sharpL();
		case '{':
			bigLTrue=true;
			flagBuffer();
			return new Token(BIG, "{");
		case '}':
			bigRTrue=true;
			if (bigLTrue) {
				flagBuffer();
				return new Token(BIGR, "}");
		}else {
			throw new Error("少了 ‘{’ 在第"+Main.count+"行为");
		}
		case '>':
			return sharpR();
		case ';':
			flagBuffer();
			return new Token(FENHAO, ";");
		case '[':
			middleL=true;
			flagBuffer();
			return new Token(MIDDLE, "[");
		case ']':
			middleR=true;
			if (middleL) {
				flagBuffer();
				return	new	Token(MIDDLER, "]");
		}else {
			throw new Error("少了 ‘[’在第"+Main.count+"行");
		}
		case '/'://与 除号有关系
			return explain();
		default:
			if (isNum()) {
				return num();
				
			}
			//识别标识符，如果想前看是字母，则收集
			else if (isLeftA()) {
				return name();
			}else {
				throw new Error("无效的字符"+c+"第"+Main.count+"行" );
			}
			
		}
		
		}
		
		//结束
		return new Token(EOF_TYPE,"<EOF>");
	}
	int byteSize=0;

	public	boolean isSame(){
		return c=='=';
	}
	public boolean isSharpL() {
		return c=='<';
	}
	public boolean isSharpR() {
		return c=='>';
	}
	
	//判断是‘=’还是 ==
	public Token whichSame(){
		StringBuilder builder=new	StringBuilder();
		while (isSame()){
		
			builder.append(c);
			flagBuffer();
	}
	//	System.out.println("whichSame"+builder.toString());
		if (builder.toString().equals("==")) {
			return new	Token(SAMETWO, "==");
			
		}else if(builder.toString().equals("=")){
			return new Token(SAME, "=");
			
		}return null;
	}
	//判断是否为左移位符
	public Token sharpL() {
		StringBuilder builder=new StringBuilder();
		while(isSharpL()){
			builder.append(c);
			flagBuffer();
		}
	//	System.out.println("sharpL"+builder.toString());
		if (builder.toString().equals("<<")) {
			return new Token(YIWEIL, "<<");
			//判断是否为小于号
		}else if (builder.toString().equals("<")) {
			return new Token(SMALLAS, "<");
		}else {
			//判断是否尖括号
			Token token=sharp();
			return token;
		}
	}

	
	public Token sharp() {
		StringBuilder builder =new StringBuilder();
		do {
			builder.append(c);
			System.out.println("~~");
			flagBuffer();
		} while (isSharpR());
		System.out.println("sharp     "+builder.toString());
		if (builder.toString().equals(">")) {
			//System.out.println("相等："+builder.toString());
			return new Token(SHARP, "<>");
		}
		return null;
	}
	//判断是否为右移位符
	public Token sharpR() {
		StringBuilder builder=new StringBuilder();
		do {
			builder.append(c);
		//	System.out.println("等于什么："+builder.toString());
			flagBuffer();
		} while (isSharpR());
		if (builder.toString().equals(">>")) {
			return new Token(YIWEIR, ">>");
		}else if (builder.toString().equals(">")) {
			return new	Token(BIGAS, ">");
		}
		return null;
}
		
		
	
	
	
//构成标识符  只由字母构成
	private Token num() {
		StringBuilder builder=new StringBuilder();
		do {
			builder.append(c);
			flagBuffer();
		} while (isNum());
		System.out.print(Main.NUMBER+++". ");
		return new Token(NUM, builder.toString());
	}
	private Token name() {
		
		// TODO Auto-generated method stub
		StringBuilder builder=new StringBuilder();
		//byte[] b=new byte[10000];
		do {
		//	b[byteSize++]=(byte)c;
			builder.append(c);
			flagBuffer();
		} while (isLeftA());
		//判断是不是关键字
		for(String save:SaveToken){
			if (save.equals(builder.toString())) {
				flagBuffer();
				System.out.print(Main.BAOLIUZI+++". ");
				return new Token(SAVETOKEN, save);
				}
			}
		System.out.print(Main.BAOLIUZI+++". ");
		return new	Token(NAME, builder.toString());
	}
	//判断是否为注释
	private Token explain() {
		int i=0;
		StringBuilder builder=new StringBuilder();
		boolean isExplain=false;
		do {
			builder.append(c);
			flagBuffer();
		} while (isDelete());
		if (builder.toString().equals("//")) {
			isExplain=true;
			Main.DELETE=Main.count;
//			builder.delete(0, builder.length()-1);
			do {
				builder.append(c);	
				flagBuffer();
			} while (c!=EOF);//不为换行符
			System.out.println("注释的文字："+builder.toString());
			return new Token(EXPLAIN, "//");
			//判断是否为除号
		}else if (builder.toString().equals("/")) {
			return	new	Token(DEVIDE, "/");
		}
		return null;
	}
	
//检测输入流是否已经读取完毕
	private void flagBuffer() {
		// TODO Auto-generated method stub
		position++;
		if (position>=input.length()) {
			c=EOF;
}else {
	c=input.charAt(position);
}
	}
	
//当遇到空格 。。跳过
	private void jump() {
		// TODO Auto-generated method stub
		System.out.println("遇到换行符～");
		while(c==' '||c=='\t'||c=='\n'||c=='\r')
			flagBuffer();
		
	}
	
}
