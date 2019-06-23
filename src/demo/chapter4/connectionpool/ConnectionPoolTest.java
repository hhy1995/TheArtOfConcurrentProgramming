package demo.chapter4.connectionpool;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionPoolTest {
	static ConnectionPool pool = new ConnectionPool(10);
	// 使用CountDownLatch确保ConnectionRunnerThread能够同时开始执行
	static CountDownLatch start = new CountDownLatch(1);
	static CountDownLatch end;

	public static void main(String[] args) throws Exception {
		// 线程数量,可以调节线程的数量来观察未获取到连接的情况
		int threadCount = 40;
		end = new CountDownLatch(threadCount);
		int count = 20;
		AtomicInteger got = new AtomicInteger();
		AtomicInteger notGot = new AtomicInteger();
		for (int i = 0; i < threadCount; i++) {
			Thread thread = new Thread(
					new ConnectionRunner(count, got, notGot),
					"ConnectionRunnerThread");
			thread.start();
		}
		start.countDown();
		end.await();
		System.out.println("总共被调用的次数：" + (threadCount * count));
		System.out.println("获取到连接的次数：" + got);
		System.out.println("未获取到连接的次数：" + notGot);
	}

	static class ConnectionRunner implements Runnable {
		int count;
		AtomicInteger got; // 获取连接的数量
		AtomicInteger notGot; // 未获取连接的数量

		public ConnectionRunner(int count, AtomicInteger got,
				AtomicInteger notGot) {
			this.count = count;
			this.got = got;
			this.notGot = notGot;
		}

		public void run() {
			try {
				start.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			while (count > 0) {
				try {
					// 从线程池当中获取连接，如果1000毫秒之内还没有获取到，则直接返回null
					Connection connection = pool.getConnection(1000);
					if (connection != null) {
						try {
							connection.createStatement();
							connection.commit();
						} finally {
							pool.releaseConnection(connection);
							got.incrementAndGet();
						}
					} else {
						notGot.incrementAndGet();
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					count--;
				}
			}
			end.countDown();
		}
	}
}
