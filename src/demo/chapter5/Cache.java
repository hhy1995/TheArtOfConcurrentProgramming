package demo.chapter5;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Cache {
	static Map<String, Object> map = new HashMap<String, Object>();
	static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	static Lock rLock = rwl.readLock();
	static Lock wLock = rwl.writeLock();
	
	//获取key对应的value
	public static final Object get(String key){
		rLock.lock();
		try {
			return map.get(key);
		} finally{
			rLock.unlock();
		}
	}
	
	//设置key对应的value,并且返回旧值
	public static final Object put(String key,Object value){
		wLock.lock();
		try {
			return map.put(key, value);
		} finally{
			wLock.unlock();
		}
	}
	
	//清除所有的内容
	public static final void clear(){
		wLock.lock();
		try {
			map.clear();
		} finally{
			wLock.unlock();
		}
	}
}
