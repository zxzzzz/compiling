import java.util.ArrayList;
import java.util.Scanner;



	public class Main {
	
		public static	void 	main(String[] args){
			//获取输入
			Scanner scanner=new Scanner(System.in);
			StringBuilder builder=new StringBuilder();
			System.out.println("请输入待分析的字符串：");
			char mm='s';//结束符号   以 ‘#’号结束
			while (mm!='#') {
				String nextString=scanner.next();
				System.out.println(nextString);
				mm=nextString.charAt(nextString.length()-1);
				builder.append(nextString);
				
			}
			System.out.println("结束");
			String input=builder.toString();
			Parser parser=new Parser(input);
			parser.go();
		}
		}
	
