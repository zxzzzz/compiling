import java.awt.ScrollPaneAdjustable;
import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.sound.sampled.Line;
//从控制台读取的字符串!=原串  换行符不识别......
//eclipse 输入的参数以空格分隔  空格不识别不跳过 当成结束符处理
public class Main {
	static	int	count=0;//行数
	static int ID=0;//标识符
	static int BAOLIUZI=0;//保留字
	static int TESHUZIFU=0;//特殊字符
	static int NUMBER=0;//数字
	static int DELETE=0;//需要删除的注释行数
	public static void main(String [] args){
		if (args.length==0) {
			return;
	}
//			
//		
//		int i=0;
//		System.out.println("请输入您的程序： ");
//		
//		String line=null;
//		try {
//			BufferedReader input=new BufferedReader(new InputStreamReader(System.in
//					));	
//			line=input.readLine();
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		
//		StringBuilder builder=new StringBuilder();
//		Console consolel=System.console();
//		System.out.println(consolel.readLine());
//		try {
//			do {
//				line=input.readLine() ;
//				System.out.println(" "+i++);
//				builder.append(line);
//			} while (line!="stop");
//				input.close();
//		} catch (Exception e) {
//			// TODO: handle exception
//			System.out.println("读取发生错误");
//			e.printStackTrace();
//		}
//			
//		line=builder.toString();
//		System.out.println("输入的为："+line);
////		
//		int j=0;
//		StringBuilder builder=new StringBuilder();
//		for(String i:args){
//			i=args[j++];
//			builder.append(i);
//		}
		//String inputContent=builder.toString();
		
		
		ArrayList<String> inputLine=new ArrayList<String>();
	//	BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(System.in));
	//	String line;
		int i=0;
		//StringBuilder builder=new StringBuilder();
//		try {
//			do {
//			line=bufferedReader.readLine();
//			inputLine.add(line);
//			}while(!line.equals("-1"));
//		}catch (IOException e) {
//			// TODO: handle exception
////			e.pr62772intStackTrace();
////		}
//		String cd;
//		Scanner scanner=new Scanner(System.in);
//		do{
//			 cd=scanner.nextLine();
//			 inputLine.add(cd);
//		}while(!cd.equals("-1"));
		System.out.println("源程序为：");
//		for(String m:inputLine){
//			System.out.println(i+++". "+m);
//			}
		ListLexer lexer;
		for(String nam:args){
			count++;
			System.out.println(count+". "+nam);
		}
		System.out.println("\n\n\n\n");
		count=0;
		System.out.println("解析程序：");
			for(String s:args){
		 lexer=new ListLexer(s);
		Token token=lexer.nextToken();
		System.out.println(token);
		while (token.type!=lexer.EOF_TYPE) {
			count++;
			token=lexer.nextToken();
			System.out.println(token);
			}
		}
	System.out.println("\n\n\n");
	System.out.println("删除注释后的程序：");
	for(int f=0;f<args.length;f++)
		if (f!=DELETE-2) {
			System.out.println(f+". "+args[f]);
		}
	

	
	}
	}

	
		
