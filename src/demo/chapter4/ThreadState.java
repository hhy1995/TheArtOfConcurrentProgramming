package demo.chapter4;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;

import com.sun.management.ThreadMXBean;

public class ThreadState {
	//���̲߳��ϵؽ���˯��
	static class TimeWaiting implements Runnable {
		public void run() {
			while(true){
				SleepUtils.second(100);
			}
		}
	}
	//���߳���Waiting.classʵ���ϵȴ���
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
	//���߳���Blocked.class�ϼ����������ͷŸ���
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
	 * ����jps�鿴��ǰ���߳�
	 * 
	 * �����߳�id������jstack id���鿴�����������Ϣ
	 * 
	 * waiting for monitor entry�ڵȴ���ȡ��
	 */
	public static void main(String[] args) {
		new Thread(new TimeWaiting(), "TimeWaiting").start();
		new Thread(new Waiting(), "Waiting").start();
		//��������Blocked�̣߳�һ����ȡ������һ��û��ȡ����
		new Thread(new Blocked(), "Bolcked-1").start();
		new Thread(new Blocked(), "Bolcked-2").start();
		
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
