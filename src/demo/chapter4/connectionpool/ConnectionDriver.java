package demo.chapter4.connectionpool;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.concurrent.TimeUnit;

/**
 * (仅是一个连接池的实示例)使用动态代理构造一个Connection
 * 
 * @author hehaiyang
 * 
 */
public class ConnectionDriver {
	static class ConnectionHandler implements InvocationHandler {

		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			if (method.getName().equals("commit")) {
				//注意这边休眠的时间是毫秒值
				TimeUnit.MILLISECONDS.sleep(100);
			}
			return null;
		}

	}

	// 创建一个Connection的代理，在commit之后休眠100毫秒
	public static final Connection createConnection() {
		return (Connection) Proxy.newProxyInstance(
				ConnectionDriver.class.getClassLoader(),
				new Class<?>[] { Connection.class }, new ConnectionHandler());
	}

}
