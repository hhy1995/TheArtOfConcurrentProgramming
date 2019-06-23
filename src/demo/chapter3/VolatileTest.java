package demo.chapter3;

/**
 * ��votiale���в��ԣ�
 * votiale���Ա�֤���������Ŀɼ��ԣ����ǲ��ܱ�֤����������ԭ���ԡ������³���ÿ��ִ�еĽ������һ�£�
 * 
 * @author hehaiyang
 * 
 */
public class VolatileTest {

	static volatile int i;

	public static class AddTask implements Runnable {
		public void run() {
			for (int j = 0; j < 10000; j++) {
				i++;
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		for (int j = 0; j < 100; j++) {
			Thread thread1 = new Thread(new AddTask());
			Thread thread2 = new Thread(new AddTask());
			thread1.start();
			thread2.start();
			thread1.join();
			thread2.join();
			System.out.println(i);
			i = 0;
		}
	}
}
