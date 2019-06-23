package demo.chapter3;

import org.junit.Test;

import sun.security.jca.GetInstance.Instance;

/**
 * ˫�ؼ������
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
					// //˫�ؼ������������ĸ�Դ����������
				}
			}
		}
		return instance;
	}

}
