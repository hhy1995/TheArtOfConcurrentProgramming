package demo.chapter4.connectionpool;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.concurrent.TimeUnit;

/**
 * (����һ�����ӳص�ʵʾ��)ʹ�ö�̬������һ��Connection
 * 
 * @author hehaiyang
 * 
 */
public class ConnectionDriver {
	static class ConnectionHandler implements InvocationHandler {

		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			if (method.getName().equals("commit")) {
				//ע��������ߵ�ʱ���Ǻ���ֵ
				TimeUnit.MILLISECONDS.sleep(100);
			}
			return null;
		}

	}

	// ����һ��Connection�Ĵ�����commit֮������100����
	public static final Connection createConnection() {
		return (Connection) Proxy.newProxyInstance(
				ConnectionDriver.class.getClassLoader(),
				new Class<?>[] { Connection.class }, new ConnectionHandler());
	}

}
