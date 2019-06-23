package demo.chapter5;

import java.util.concurrent.locks.Lock;

import org.junit.Test;

import demo.chapter4.SleepUtils;

public class TwinsLockTest {
	@Test
	public void test(){
		final Lock lock = new TwinsLock();
		class Worker extends Thread{
			@Override
			public void run() {
				while(true){
					lock.lock();//������
					try {
						SleepUtils.second(3);//��ȡ��֮��˯��1��
						System.out.println(Thread.currentThread().getName());//��ӡ�߳���
						SleepUtils.second(3);//��ȡ��֮���ٴ�˯��1���ӣ�Ȼ�����ͷ���
					} catch (Exception e) {
						e.printStackTrace();
					}finally{
						lock.unlock();//�ͷ���
					}
				}
			}
		}
		
		//����10���߳�
		for (int i = 0; i < 10; i++) {
			Worker worker = new Worker();
			worker.setDaemon(true);
			worker.start();
		}
		
		//ÿ���1���ӣ���һ����
		for (int i = 0; i < 10; i++) {
			SleepUtils.second(3);
			System.out.println();
		}
	}
}
