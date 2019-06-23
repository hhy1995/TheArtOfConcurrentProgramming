package demo.chapter4;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;

import com.sun.management.ThreadMXBean;

public class ThreadState {
	//该线程不断地进行睡眠
	static class TimeWaiting implements Runnable {
		public void run() {
			while(true){
				SleepUtils.second(100);
			}
		}
	}
	//该线程在Waiting.class实例上等待着
	static class Waiting implements Runnable{
		public void run() {
			while(true){
				synchronized (Waiting.class) {
					try {
						Waiting.class.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	//该线程在Blocked.class上加锁，不会释放该锁
	static class Blocked implements Runnable{
		public void run() {
			synchronized (Blocked.class) {
				while(true){
					SleepUtils.second(100);
				}
			}
		}
	}
	/*
	 * 键入jps查看当前的线程
	 * 
	 * 根据线程id，输入jstack id来查看具体的运行信息
	 * 
	 * waiting for monitor entry在等待获取锁
	 */
	public static void main(String[] args) {
		new Thread(new TimeWaiting(), "TimeWaiting").start();
		new Thread(new Waiting(), "Waiting").start();
		//创建两个Blocked线程，一个获取到锁，一个没获取到锁
		new Thread(new Blocked(), "Bolcked-1").start();
		new Thread(new Blocked(), "Bolcked-2").start();
		
		//获取java线程管理MXBean
		ThreadMXBean threadMXBean = (ThreadMXBean) ManagementFactory
				.getThreadMXBean();
		//只需要获取线程和线程对战信息
		ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
		//打印信息
		for (ThreadInfo threadInfo : threadInfos) {
			System.out.println("[" + threadInfo.getThreadId() + "] "
					+ "thread name:" + threadInfo.getThreadName()
					+ " ， threadstate:" + threadInfo.getThreadState());
		}
	}
}
