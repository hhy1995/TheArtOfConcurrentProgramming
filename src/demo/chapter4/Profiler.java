package demo.chapter4;

import java.util.concurrent.TimeUnit;
/**
 * ThreadLocal的使用场景：
 * 当需要把某个值与线程相关联的时候，并且线程在后面还会用到该值，我们就可以使用ThreadLocal
 * 将ThreadLocal作为键，需要绑定的对象作为值存储在一个Map中
 * @author hehaiyang
 *
 *
 *Profiler翻译为"探查"
 */
public class Profiler {
	//第一次使用get()方法的时候会进行初始化，每个线程会调用一次，
	/**
	 * 注意；ThreadLocal类的get和set方法
	 */
	private static final ThreadLocal<Long> TIME_THREADLOCAL = new ThreadLocal<Long>() {
		protected Long initialValue() {
			return System.currentTimeMillis();
		}
	};

	public static void begin() {
		//在使用的时候，将ThreadLocal的实例作为Key,将系统当前时间作为Value，放到运行的线程Thread中
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
