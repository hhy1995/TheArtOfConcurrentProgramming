package demo.chapter2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Java���п���ͨ������ѭ��CASʵ��ԭ�Ӳ���
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
		// �����߳�
		for (Thread thread : tList) {
			thread.start();
		}

		// �ȴ������߳�ִ�����
		for (Thread thread : tList) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(cas.i);//iÿ�ε�ֵ����һ������С��1000000��vatialeֻ��֤�ڴ�ɼ��ԣ�����֤ԭ���ԡ�
		System.out.println(cas.atomicInteger.get());
		System.out.println("ִ��ʱ�䣺" + (System.currentTimeMillis() - start)+"ms");
	}

	/*
	 * ʹ��CASʵ���̰߳�ȫ������
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
	 * ���̰߳�ȫ������
	 * 
	 * ���synchronized����ʵ��ԭ���ԣ�����ÿ�ζԱ������и��ĵ�ʱ�򣬶���Ҫ���м��������ܱȽϵ͡�
	 */
	private  void count() {
		i++;
	}
}
