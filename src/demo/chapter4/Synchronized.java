package demo.chapter4;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;

import com.sun.management.ThreadMXBean;

public class Synchronized {
	public static void main(String[] args) {
		//��Synchronized.class���м�������
		synchronized (Synchronized.class) {
			
		}
		//��̬ͬ����������Synchronized.class������м���
		m();
		
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
	public static synchronized void m(){
		
	}
}
