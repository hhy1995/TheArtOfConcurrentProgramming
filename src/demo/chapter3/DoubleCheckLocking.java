package demo.chapter3;

import org.junit.Test;

import sun.security.jca.GetInstance.Instance;

/**
 * 双重检查锁定
 * 
 * @author hehaiyang
 * 
 */
public class DoubleCheckLocking {
	private volatile static Instance instance;
	public static Instance getInstance() {
		if (instance == null) {
			synchronized (DoubleCheckLocking.class) {
				if (instance == null) {
					// instance = new Instance(null, instance);
					// //双重检查锁定，问题的根源出现在这里
				}
			}
		}
		return instance;
	}

}
