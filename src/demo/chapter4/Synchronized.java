package demo.chapter4;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;

import com.sun.management.ThreadMXBean;

public class Synchronized {
	public static void main(String[] args) {
		//对Synchronized.class进行加锁处理
		synchronized (Synchronized.class) {
			
		}
		//静态同步方法，对Synchronized.class对象进行加锁
		m();
		
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
	public static synchronized void m(){
		
	}
}
