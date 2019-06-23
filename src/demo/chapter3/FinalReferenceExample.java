package demo.chapter3;
/**
 * final域是引用类型
 * 
 * 对于引用类型，写final域的重排序规则对编译器和处理器增加了以下的约束：
 * 在构造函数内对一个final引用的对象的成员域的写入，与随后在构造函数外把这
 * 个被构造对象的引用赋值给另外一个引用变量，这两个操作之家不能进行重排序。
 * 
 * @author hehaiyang
 *
 */
public class FinalReferenceExample {
	final int [] intArray;	//final域为引用类型
	static FinalReferenceExample obj;
	public FinalReferenceExample(){		//构造函数
		intArray = new int[1];	//1.对final域的写入
		intArray[0] = 1;		//2.对final域引用的对象的成员域的写入
	}
	
	public static void writerOne(){
		obj = new FinalReferenceExample(); //3.把构造对象的引用赋值给某个引用变量   （1.3  2.3不能进行重排序）
	}
	
	public static void writerTwo(){
		obj.intArray[0] = 2;				//4.对数组元素的写入，读线程可能看不到。（写线程和读线程存在数据竞争的问题）
	}
	
	public static void reader(){
		if (obj != null) {
			int temp = obj.intArray[0];
		}
		System.out.println(obj.intArray[0]);
	}
	public static void main(String[] args) {
		writerOne();
		System.out.println("写线程1执行完之后："+obj.intArray[0]);
		writerTwo();
		System.out.println("写线程2执行完之后："+obj.intArray[0]);
		reader();
	}
}
