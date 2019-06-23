package demo.chapter4;

import java.util.concurrent.TimeUnit;

/**
 * ���һ���߳�Aִ����thread.join()��䣬�京���ǣ�
 * ��ǰ�߳�A�ȴ�thread�߳���ֹ֮����ܴ�thread.join()���з��ء�
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
			System.out.println(Thread.currentThread().getName() + ",�Ѿ���ֹ������");
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
		//ÿ���߳���ֹ��ǰ����ǰ���̵߳���ֹ��ÿ���̵߳ȴ�ǰ���߳���ֹ֮�󣬲��ܴ�join�������ء�
		System.out.println(Thread.currentThread().getName() + ",�Ѿ���ֹ�ˡ�����");
	}
}
