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
					lock.lock();//申请锁
					try {
						SleepUtils.second(3);//获取锁之后睡眠1秒
						System.out.println(Thread.currentThread().getName());//打印线程名
						SleepUtils.second(3);//获取锁之后再次睡眠1秒钟，然后再释放锁
					} catch (Exception e) {
						e.printStackTrace();
					}finally{
						lock.unlock();//释放锁
					}
				}
			}
		}
		
		//开启10个线程
		for (int i = 0; i < 10; i++) {
			Worker worker = new Worker();
			worker.setDaemon(true);
			worker.start();
		}
		
		//每间隔1秒钟，换一次行
		for (int i = 0; i < 10; i++) {
			SleepUtils.second(3);
			System.out.println();
		}
	}
}
