package demo.chapter3;

/**
 * final域重排序规则示例性代码
 * 
 * @author hehaiyang
 * 
 */
public class FinalExample {
	int i; // 普通变量
	final int j; // final变量
	static FinalExample obj;

	public FinalExample() { // 构造函数
		i = 1;
		j = 2;				//写final域的重排序规则禁止把final域的写重排序到构造函数之外，在重排序的过程中，
							//可能对于普通域的写操作重排到构造函数之外。与此同时，可能导致其他并发执行的线程读取到普通域变量的初始值
							//final域的读取操作是正确的，但是对于普通变量域的读取存在问题。
	}

	public static void writer() {
		obj = new FinalExample();
	}
	
	public static void reader(){
		FinalExample object = obj;		//读对象的引用
		int a = object.i;				//读普通域
		int b = object.j;				//读final域
	}
}
