//抽象的词法分析器  输入 下一个字符 
public 	abstract class Lexer {
	public String input;//输入的字符流
	public char c;//向下遍历的下一个字符
	public int position=0;//向下遍历的字符下标
	public Lexer(String input ){
		//初始化  c是开头的字符
		this.input=input;
		c=input.charAt(position);
		System.out.println(this.input);
	}
	
	public static final	 char EOF=(char)-1;
	public	static	final  int EOF_TYPE=1;
	abstract public String getTokenName(int index);//得到类型
	abstract public boolean isLeftA();//是否为字母

}
