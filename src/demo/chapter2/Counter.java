package demo.chapter2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Java当中可以通过锁和循环CAS实现原子操作
 * 
 * @author hehaiyang
 * 
 */
public class Counter {
	private AtomicInteger atomicInteger = new AtomicInteger(0);
	private volatile int i = 0;

	public static void main(String[] args) {
		final Counter cas = new Counter();
		List<Thread> tList = new ArrayList<Thread>(600);
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			Thread thread = new Thread(new Runnable() {
				public void run() {
					for (int j = 0; j < 10000; j++) {
						cas.safeCount();
						cas.count();
					}
				}
			});
			tList.add(thread);
		}
		// 开启线程
		for (Thread thread : tList) {
			thread.start();
		}

		// 等待所有线程执行完成
		for (Thread thread : tList) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(cas.i);//i每次的值都不一样，都小于1000000，vatiale只保证内存可见性，不保证原子性。
		System.out.println(cas.atomicInteger.get());
		System.out.println("执行时间：" + (System.currentTimeMillis() - start)+"ms");
	}

	/*
	 * 使用CAS实现线程安全计数器
	 */
	private void safeCount() {
		while(true) {
			int i = atomicInteger.get();
			boolean suc = atomicInteger.compareAndSet(i, ++i);
			if (suc) {
				break;
			}
		}
	}

	/*
	 * 非线程安全计数器
	 * 
	 * 添加synchronized可以实现原子性，但是每次对变量进行更改的时候，都需要进行加锁，性能比较低。
	 */
	private  void count() {
		i++;
	}
}
