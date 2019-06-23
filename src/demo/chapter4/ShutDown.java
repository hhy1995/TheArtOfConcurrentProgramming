package demo.chapter4;

import java.util.concurrent.TimeUnit;

/**
 * 安全的中止线程： 使用中断操作和利用boolean变量来控制是否需要停止任务并且中断该线程
 * 这种通过标志位或者中断的方式能够使得线程在中止的时候有机会去清理资源，而不是武断地将线程停止。
 * @author hehaiyang
 * 
 */
public class ShutDown {

	private static class Runner implements Runnable {
		private long i;
		private volatile boolean on = true;

		public void run() {
			// on等于true,并且当前线程没有处于中断状态
			while (on == true && !Thread.currentThread().isInterrupted()) {
				i++;
			}
			System.out.println("Count i =" + i);
		}
		public void cancel(){
			on = false;
		}
	}
	
	public static void main(String[] args) throws Exception {
		Runner one = new Runner();
		Thread countThread = new Thread(one, "CountThread");
		countThread.start();
		//睡眠1s，main线程对CountThread进行中断，使得CountThread能够感知中断而结束
		TimeUnit.SECONDS.sleep(1);
		countThread.interrupt();//调用中断操作
		System.out.println("CountThread interrupted is:"+countThread.isInterrupted());
		Runner two = new Runner();
		countThread = new Thread(two, "CountThread");
		countThread.start();
		TimeUnit.SECONDS.sleep(1);
		two.cancel();//调用cancel（）方法使得线程中止
	}
}
