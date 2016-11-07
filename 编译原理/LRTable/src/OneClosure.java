import java.util.ArrayList;


// eg:E->E+T
//单个文法 
public class OneClosure {
	char one;
	ArrayList<Character>  list;
	public OneClosure(char one,ArrayList<Character> list){
		this.one=one;
		this.list=list;
	}
	public char getOne(){
		return one;
	}
	public ArrayList<Character> getList(){
		return list;
	}
	
}
