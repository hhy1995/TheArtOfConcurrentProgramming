package demo.chapter4;

import java.util.concurrent.TimeUnit;

/**
 * ��ȫ����ֹ�̣߳� ʹ���жϲ���������boolean�����������Ƿ���Ҫֹͣ�������жϸ��߳�
 * ����ͨ����־λ�����жϵķ�ʽ�ܹ�ʹ���߳�����ֹ��ʱ���л���ȥ������Դ����������ϵؽ��߳�ֹͣ��
 * @author hehaiyang
 * 
 */
public class ShutDown {

	private static class Runner implements Runnable {
		private long i;
		private volatile boolean on = true;

		public void run() {
			// on����true,���ҵ�ǰ�߳�û�д����ж�״̬
			while (on == true && !Thread.currentThread().isInterrupted()) {
				i++;
			}
			System.out.println("Count i =" + i);
		}
		public void cancel(){
			on = false;
		}
	}
	
	public static void main(String[] args) throws Exception {
		Runner one = new Runner();
		Thread countThread = new Thread(one, "CountThread");
		countThread.start();
		//˯��1s��main�̶߳�CountThread�����жϣ�ʹ��CountThread�ܹ���֪�ж϶�����
		TimeUnit.SECONDS.sleep(1);
		countThread.interrupt();//�����жϲ���
		System.out.println("CountThread interrupted is:"+countThread.isInterrupted());
		Runner two = new Runner();
		countThread = new Thread(two, "CountThread");
		countThread.start();
		TimeUnit.SECONDS.sleep(1);
		two.cancel();//����cancel��������ʹ���߳���ֹ
	}
}
