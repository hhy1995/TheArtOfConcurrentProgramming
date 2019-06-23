package demo.chapter4;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import com.sun.management.ThreadMXBean;

/**
 * 使用JMX来查看普通的Java程序包含哪些线程
 * 
 * @author hehaiyang
 * 
 */
public class MutilThread {
	public static void main(String[] args) {
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
