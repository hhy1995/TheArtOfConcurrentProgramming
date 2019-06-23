package demo.chapter4;

import java.util.concurrent.TimeUnit;
/**
 * ThreadLocal��ʹ�ó�����
 * ����Ҫ��ĳ��ֵ���߳��������ʱ�򣬲����߳��ں��滹���õ���ֵ�����ǾͿ���ʹ��ThreadLocal
 * ��ThreadLocal��Ϊ������Ҫ�󶨵Ķ�����Ϊֵ�洢��һ��Map��
 * @author hehaiyang
 *
 *
 *Profiler����Ϊ"̽��"
 */
public class Profiler {
	//��һ��ʹ��get()������ʱ�����г�ʼ����ÿ���̻߳����һ�Σ�
	/**
	 * ע�⣻ThreadLocal���get��set����
	 */
	private static final ThreadLocal<Long> TIME_THREADLOCAL = new ThreadLocal<Long>() {
		protected Long initialValue() {
			return System.currentTimeMillis();
		}
	};

	public static void begin() {
		//��ʹ�õ�ʱ�򣬽�ThreadLocal��ʵ����ΪKey,��ϵͳ��ǰʱ����ΪValue���ŵ����е��߳�Thread��
		TIME_THREADLOCAL.set(System.currentTimeMillis());
	}

	public static long end() {
		return System.currentTimeMillis() - TIME_THREADLOCAL.get();
	}

	public static void main(String[] args) throws InterruptedException {
		Profiler.begin();
		TimeUnit.SECONDS.sleep(1);
		System.out.println("cost:" + Profiler.end() + " millis");
	}
}
