import java.util.ArrayList;


//项目族规范集
public class Closure {
	ArrayList<OneClosure> closuresArrayList;
	int last;//推导出项目的上一个项目标号
	int current;//项目的标号
	ArrayList<Integer> nexts;//可以推导出的项目集
	public ArrayList<OneClosure> getClosuresArrayList() {
		return closuresArrayList;
	}
	public void setClosuresArrayList(ArrayList<OneClosure> closuresArrayList) {
		this.closuresArrayList = closuresArrayList;
	}
	public int getLast() {
		return last;
	}
	public void setLast(int last) {
		this.last = last;
	}
	public int getCurrent() {
		return current;
	}
	public void setCurrent(int current) {
		this.current = current;
	}
	public ArrayList<Integer> getNexts() {
		return nexts;
	}
	public void setNexts(ArrayList<Integer> nexts) {
		this.nexts = nexts;
	}
	

}
