package demo.chapter1;
/**
 * 死锁测试：这段代码会引起死锁，使得线程t1,t2会产生死锁。
 * 
 * @author hehaiyang
 *
 */
public class DeadLockDemo {
	private static String A = "A";
	private static String B = "B";
	
	private void deadLock(){
		Thread t1 = new Thread(new Runnable(){
			public void run() {
				synchronized (A) {
					try {
						Thread.currentThread();
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					synchronized (B) {
						System.out.println("线程t1");
					}
				}
			}
		});
		
		Thread t2 = new Thread(new Runnable(){
			public void run() {
				synchronized (B) {
					try {
						Thread.currentThread();
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					synchronized (A) {
						System.out.println("线程t2");
					}
				}
			}
		});
		t1.start();
		t2.start();
	}
	public static void main(String[] args) {
		new DeadLockDemo().deadLock();
	}
}
