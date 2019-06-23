package demo.chapter4;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 等待/通知机制：
 * 两个线程通过对象O来完成交互，通过调用对象上的wait()/notify()或者notifyAll()方法来完成等待方和通知方之间的交互。
 * 
 * @author hehaiyang
 * 
 */
public class WaitNotify {
	static boolean flag = true;
	static Object lock = new Object();

	static class Wait implements Runnable {
		public void run() {
			// 加锁，拥有当前锁的Monitor
			synchronized (lock) {
				//当条件不满足的时候，继续wait(),同时释放掉已经获得的锁
				while (flag) {
					try {
						System.out.println(Thread.currentThread()
								+ "flag is true. wait time is :"
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
										.format(new Date())+"  "+Thread.currentThread().getState());
						//使用前要先对对象加锁
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			// 条件满足时继续向下执行
			System.out.println(Thread.currentThread()
					+ "flag is false. wait time is :"
					+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.format(new Date())+"  "+Thread.currentThread().getState());
		}
	}

	static class Notify implements Runnable {
		public void run() {
			// 加锁，拥有当前锁的Monitor
			synchronized (lock) {
				while (flag) {
					// 获取到锁，然后进行通知，通知时不会释放lock的锁
					// 只有当前线程释放掉lock之后，WaitThread才能从wait方法中返回
					System.out.println(Thread.currentThread()
							+ "hold Lock. notify time is :"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
									.format(new Date())+"  "+Thread.currentThread().getState());
					lock.notifyAll();
					//改变对应的条件
					flag = false;
					SleepUtils.second(5);
				}
			}
			//再次加锁
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
