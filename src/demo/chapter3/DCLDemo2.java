package demo.chapter3;

import java.util.concurrent.CountDownLatch;
/**
 * ���Ե���ģʽ�¶��̵߳��õİ�ȫ��
 * @author hehaiyang
 *
 */
public class DCLDemo2 {
	static int count = 10;
	private static CountDownLatch countDownLatch = new CountDownLatch(count);
	private static DCLTestBean instance;
	
	public synchronized static DCLTestBean getInstance(){
		if (instance == null) {
			instance = new DCLTestBean();
		}
		return instance;
	}
	/*
	 * ���ִ�м��λ����DCLTestBean����ʼ�����,����Υ���˵�����
	 * ��򵥵ĵĽ�ְ취����ֱ�����synchronized
	 */
	public static void main(String[] args) throws InterruptedException{
		for (int i = 0; i < count; i++) {
			new Thread(new Runnable(){

				public void run() {
					try {
						Thread.sleep(1000);
						countDownLatch.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}	
					getInstance();
				}
			}).start();
			countDownLatch.countDown();
		}
	}
}
