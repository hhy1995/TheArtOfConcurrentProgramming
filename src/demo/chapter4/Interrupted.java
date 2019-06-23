package demo.chapter4;

import java.util.concurrent.TimeUnit;

/**
 * 理解线程中断
 * 
 * @author hehaiyang
 *
 */
public class Interrupted {
	static class SleepRunner implements Runnable{
		public void run() {
			//不断的尝试睡眠
			while(true){
				SleepUtils.second(10);
			}
		}
	}
	
	static class BusyRunner implements Runnable{
		public void run() {
			//不断地尝试运行
			while(true){
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		//sleepThread线程不断地尝试睡眠
		Thread sleepThread = new Thread(new SleepRunner(),"SleepRunner");
		sleepThread.setDaemon(true);
		//busyThread不断尝试运行
		Thread busyThread = new Thread(new BusyRunner(),"BusyRunner");
		busyThread.setDaemon(true);
		sleepThread.start();
		busyThread.start();
		//休眠5s
		TimeUnit.SECONDS.sleep(5);
		sleepThread.interrupt();
		busyThread.interrupt();
		System.out.println("SleepThread interrupted is:"+sleepThread.isInterrupted());
		System.out.println("BusyThread interruoted is:"+busyThread.isInterrupted());
		TimeUnit.SECONDS.sleep(5);
	}
}
