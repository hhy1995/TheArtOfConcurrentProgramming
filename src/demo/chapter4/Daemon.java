package demo.chapter4;
/*
 * Daemon线程是一种支持性线程，主要用于程序中后台调度和支持性工作
 */
public class Daemon {
	static class DaemonRunner implements Runnable{
		public void run() {
			try {
				SleepUtils.second(10);
			} finally{
				//finally块中的内容并没有执行，所以在构建Daemon线程时，
				//不能依靠finally块中的内容来确保执行关闭或清理资源的逻辑
				System.out.println("Daemon Thread Run...");
			}
		}
	}
	
	public static void main(String[] args) {
		Thread thread = new Thread(new DaemonRunner(),"DaemonRunner");
		//设置其为守护线程
		thread.setDaemon(true);
		thread.start();
	}
}
