
public class Token {
	public int type;//类型
	public String text;//值
	public Token(int type,String text){
		this.text=text;
		this.type=type;
	}
	
	//
	public String toString(){
		String name=ListLexer.TOKENNAME[type];
		return name+"     "+text;
	}
	

}
