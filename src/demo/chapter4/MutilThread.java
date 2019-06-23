package demo.chapter4;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import com.sun.management.ThreadMXBean;

/**
 * ʹ��JMX���鿴��ͨ��Java���������Щ�߳�
 * 
 * @author hehaiyang
 * 
 */
public class MutilThread {
	public static void main(String[] args) {
		//��ȡjava�̹߳���MXBean
		ThreadMXBean threadMXBean = (ThreadMXBean) ManagementFactory
				.getThreadMXBean();
		//ֻ��Ҫ��ȡ�̺߳��̶߳�ս��Ϣ
		ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
		//��ӡ��Ϣ
		for (ThreadInfo threadInfo : threadInfos) {
			System.out.println("[" + threadInfo.getThreadId() + "] "
					+ "thread name:" + threadInfo.getThreadName()
					+ " �� threadstate:" + threadInfo.getThreadState());
		}
	}
}
