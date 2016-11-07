//语法解析器  LL(1)
public class Parser {
	Lexer input;//输入的词法解析器
	Token lookHead;//向前看符号
	public Parser(Lexer lexer){
		input=lexer;
	}
}
