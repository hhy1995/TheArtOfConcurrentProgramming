package demo.chapter3;

import java.util.concurrent.CountDownLatch;
/**
 * 测试单例模式下多线程调用的安全性
 * @author hehaiyang
 *
 */
public class DCLDemo2 {
	static int count = 10;
	private static CountDownLatch countDownLatch = new CountDownLatch(count);
	private static DCLTestBean instance;
	
	public synchronized static DCLTestBean getInstance(){
		if (instance == null) {
			instance = new DCLTestBean();
		}
		return instance;
	}
	/*
	 * 多次执行几次会出现DCLTestBean被初始化多次,明显违背了单例。
	 * 最简单的的结局办法就是直接添加synchronized
	 */
	public static void main(String[] args) throws InterruptedException{
		for (int i = 0; i < count; i++) {
			new Thread(new Runnable(){

				public void run() {
					try {
						Thread.sleep(1000);
						countDownLatch.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}	
					getInstance();
				}
			}).start();
			countDownLatch.countDown();
		}
	}
}
