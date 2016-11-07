import java.util.Scanner;


public class Main {
	public static void main(String[] args){
		System.out.println("请输入要分析的字符串：");
		Scanner scanner=new Scanner(System.in);
		String input=null;
		for(int i=0;i<1;i++){
			input=scanner.next();
}
		System.out.println("输入的字符串："+input);
		Prior prior=new Prior(input);
		prior.go();
	}

}
