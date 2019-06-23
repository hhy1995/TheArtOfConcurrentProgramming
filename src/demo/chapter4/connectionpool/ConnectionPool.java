package demo.chapter4.connectionpool;

import java.sql.Connection;
import java.util.LinkedList;

public class ConnectionPool {
	private LinkedList<Connection> pool = new LinkedList<Connection>();

	// 初始化创建连接池，并且加入到pool当中
	public ConnectionPool(int initialSize) {
		if (initialSize > 0) {
			pool.add(ConnectionDriver.createConnection());
		}
	}

	// 释放连接
	public void releaseConnection(Connection connection) {
		if (connection != null) {
			synchronized (pool) {
				// 释放连接之后，要对其他线程进行通知，让其他线程知道连接池中多了一个连接
				pool.add(connection);
				pool.notifyAll();
			}
		}
	}

	// 使用超时等待模式，在规定的时间内，无法获得连接的话，直接返回null
	public Connection getConnection(long mills) throws Exception {
		// 首先对连接池加锁
		synchronized (pool) {
			//条件循环，然后再处理逻辑
			if (mills < 0) {
				while (pool.isEmpty()) {
					pool.wait();
				}
				return pool.removeFirst();
			} else {
				long future = System.currentTimeMillis();
				long remaining = mills;
				while (pool.isEmpty() && remaining > 0) {
					pool.wait(remaining);
					remaining = future - System.currentTimeMillis();
				}
				Connection result = null;
				if (!pool.isEmpty()) {
					result = pool.removeFirst();
				}
				return result;
			}
		}
	}
}
