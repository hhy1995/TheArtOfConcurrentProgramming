package demo.chapter4;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * �ȴ�/֪ͨ���ƣ�
 * �����߳�ͨ������O����ɽ�����ͨ�����ö����ϵ�wait()/notify()����notifyAll()��������ɵȴ�����֪ͨ��֮��Ľ�����
 * 
 * @author hehaiyang
 * 
 */
public class WaitNotify {
	static boolean flag = true;
	static Object lock = new Object();

	static class Wait implements Runnable {
		public void run() {
			// ������ӵ�е�ǰ����Monitor
			synchronized (lock) {
				//�������������ʱ�򣬼���wait(),ͬʱ�ͷŵ��Ѿ���õ���
				while (flag) {
					try {
						System.out.println(Thread.currentThread()
								+ "flag is true. wait time is :"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
										.format(new Date())+"  "+Thread.currentThread().getState());
						//ʹ��ǰҪ�ȶԶ������
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			// ��������ʱ��������ִ��
			System.out.println(Thread.currentThread()
					+ "flag is false. wait time is :"
					+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.format(new Date())+"  "+Thread.currentThread().getState());
		}
	}

	static class Notify implements Runnable {
		public void run() {
			// ������ӵ�е�ǰ����Monitor
			synchronized (lock) {
				while (flag) {
					// ��ȡ������Ȼ�����֪ͨ��֪ͨʱ�����ͷ�lock����
					// ֻ�е�ǰ�߳��ͷŵ�lock֮��WaitThread���ܴ�wait�����з���
					System.out.println(Thread.currentThread()
							+ "hold Lock. notify time is :"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(new Date())+"  "+Thread.currentThread().getState());
					lock.notifyAll();
					//�ı��Ӧ������
					flag = false;
					SleepUtils.second(5);
				}
			}
			//�ٴμ���
			synchronized (lock) {
				System.out.println(Thread.currentThread()
						+ "hold Lock again. sleep time is :"
						+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format(new Date())+"  "+Thread.currentThread().getState());
				SleepUtils.second(5);
			}
		}
	}
	public static void main(String[] args) throws Exception{
		Thread waitThread = new Thread(new Wait(), "WaitThread");
		waitThread.start();
		TimeUnit.SECONDS.sleep(1);
		Thread notifyThread = new Thread(new Notify(), "NotifyThread");
		notifyThread.start();
	}
}
