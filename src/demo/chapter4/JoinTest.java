package demo.chapter4;

import java.util.concurrent.TimeUnit;

/**
 * 如果一个线程A执行了thread.join()语句，其含义是：
 * 当前线程A等待thread线程中止之后才能从thread.join()当中返回。
 * @author hehaiyang
 *
 */
public class JoinTest {
	static class Domino implements Runnable{
		private Thread thread;

		public Domino(Thread thread) {
			super();
			this.thread = thread;
		}
		public void run() {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + ",已经中止。。。");
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		Thread previous = Thread.currentThread();
		for (int i = 0; i < 10; i++) {
			Thread thread = new Thread(new Domino(previous),String.valueOf(i));
			thread.start();
			previous = thread;
		}
		
		TimeUnit.SECONDS.sleep(5);
		//每个线程终止的前提是前驱线程的中止，每个线程等待前驱线程中止之后，才能从join方法返回。
		System.out.println(Thread.currentThread().getName() + ",已经中止了。。。");
	}
}
