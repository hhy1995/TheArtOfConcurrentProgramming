package demo.chapter3;

import java.util.concurrent.TimeUnit;
/**
 * 线程不安全的延迟初始化
 * 
 * @author hehaiyang
 *
 */
public class DCLDemo3 {
	private volatile static DCLTestBean instance;

	public static DCLTestBean getInstance() throws InterruptedException {
		System.out.println(Thread.currentThread().getName());
		if (instance == null) {
			synchronized (DCLDemo3.class) {
				if (instance == null) {
					instance = new DCLTestBean();
					System.out.println("对象已经被new了");
					TimeUnit.SECONDS.sleep(3); // 耗时的初始化
					instance.setId(666);
					instance.setName("hhy");
				}
			}
		}
		return instance;
	}

	public static void main(String[] args) throws InterruptedException {
		Runnable runnable = new Runnable() {

			public void run() {
				try {
					DCLTestBean instance = DCLDemo3.getInstance();
					System.out.println(instance.getName().toString());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		Thread thread1 = new Thread(runnable);
		Thread thread2 = new Thread(runnable);
		Thread thread3 = new Thread(runnable);
		thread1.start();
		thread2.start();
		thread1.sleep(2000);
		thread3.start();
	}
}
