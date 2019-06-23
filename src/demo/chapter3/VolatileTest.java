package demo.chapter3;

/**
 * 对votiale进行测试：
 * votiale可以保证变量操作的可见性，但是不能保证变量操作的原子性。（以下程序每次执行的结果都不一致）
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
