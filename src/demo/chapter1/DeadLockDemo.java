package demo.chapter1;
/**
 * �������ԣ���δ��������������ʹ���߳�t1,t2�����������
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
						System.out.println("�߳�t1");
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
						System.out.println("�߳�t2");
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
