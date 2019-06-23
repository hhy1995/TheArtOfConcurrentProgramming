package demo.chapter4;

import java.util.concurrent.TimeUnit;

/**
 * ����߳��ж�
 * 
 * @author hehaiyang
 *
 */
public class Interrupted {
	static class SleepRunner implements Runnable{
		public void run() {
			//���ϵĳ���˯��
			while(true){
				SleepUtils.second(10);
			}
		}
	}
	
	static class BusyRunner implements Runnable{
		public void run() {
			//���ϵس�������
			while(true){
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		//sleepThread�̲߳��ϵس���˯��
		Thread sleepThread = new Thread(new SleepRunner(),"SleepRunner");
		sleepThread.setDaemon(true);
		//busyThread���ϳ�������
		Thread busyThread = new Thread(new BusyRunner(),"BusyRunner");
		busyThread.setDaemon(true);
		sleepThread.start();
		busyThread.start();
		//����5s
		TimeUnit.SECONDS.sleep(5);
		sleepThread.interrupt();
		busyThread.interrupt();
		System.out.println("SleepThread interrupted is:"+sleepThread.isInterrupted());
		System.out.println("BusyThread interruoted is:"+busyThread.isInterrupted());
		TimeUnit.SECONDS.sleep(5);
	}
}
