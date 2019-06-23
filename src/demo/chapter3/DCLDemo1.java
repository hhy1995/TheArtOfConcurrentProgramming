package demo.chapter3;
/**
 * 懒汉式单例
 * @author hehaiyang
 *
 */
public class DCLDemo1 {
	private static DCLTestBean instance;

	public static DCLTestBean getInstance() {
		
		/*//普通单例模式
		if (instance == null) {			//如果检查instance为空的话，则创建instance对象
			instance = new DCLTestBean();	
		}*/
		
		//双重检查锁定
		if (instance == null) {//第一次检查
			synchronized (DCLDemo1.class) {//加锁
				if (instance == null) {//第二次检查
					instance = new DCLTestBean();//可能出现有问题的地方
				}
			}
		}
		return instance;
	}
}
