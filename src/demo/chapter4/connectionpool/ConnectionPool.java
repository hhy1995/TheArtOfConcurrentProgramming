package demo.chapter4.connectionpool;

import java.sql.Connection;
import java.util.LinkedList;

public class ConnectionPool {
	private LinkedList<Connection> pool = new LinkedList<Connection>();

	// ��ʼ���������ӳأ����Ҽ��뵽pool����
	public ConnectionPool(int initialSize) {
		if (initialSize > 0) {
			pool.add(ConnectionDriver.createConnection());
		}
	}

	// �ͷ�����
	public void releaseConnection(Connection connection) {
		if (connection != null) {
			synchronized (pool) {
				// �ͷ�����֮��Ҫ�������߳̽���֪ͨ���������߳�֪�����ӳ��ж���һ������
				pool.add(connection);
				pool.notifyAll();
			}
		}
	}

	// ʹ�ó�ʱ�ȴ�ģʽ���ڹ涨��ʱ���ڣ��޷�������ӵĻ���ֱ�ӷ���null
	public Connection getConnection(long mills) throws Exception {
		// ���ȶ����ӳؼ���
		synchronized (pool) {
			//����ѭ����Ȼ���ٴ����߼�
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
