package demo.chapter5;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 通过有界队列的示例来了解Condition的使用方式
 * @author hehaiyang
 *
 * @param <T>
 */
public class BoundedQueue<T> {
	private Object[] items;
	//添加的下标，删除的下标和数组当前计数
	private int addIndex,removeIndex,count;
	private Lock lock = new ReentrantLock();
	private Condition notEmpty = lock.newCondition();
	private Condition notFull = lock.newCondition();
	
	//对模拟队列的数组进行初始化
	public BoundedQueue(int size){
		items = new Object[size];
	}
	
	//添加一个元素，如果队列不满的话，直接加入；如果队列是满的话，则添加线程进入等待状态
	public void add(T t) throws InterruptedException {
		//首先获得锁，保证数组的修改的可见性和排它性
		lock.lock();
		try {
			while(count == items.length)
				notFull.await();
			items[addIndex] = t;
			if (++addIndex == items.length) {
				addIndex = 0;
			}
			++count;
			notEmpty.signal();
		} finally{
			lock.unlock();
		}
	}
	
	//删除一个元素，如果队列非空的话，直接删除；如果队列是空的话，则添加线程进入等待状态,直到添加新的元素
	public T remove() throws InterruptedException {
		lock.lock();
		try {
			while(count == 0)
				notEmpty.await();
			Object object = items[removeIndex];
			if (++removeIndex == items.length) {
				removeIndex = 0;
			}
			--count;
			notFull.signal();
			return (T) object;
		} finally{
			lock.unlock();
		}
	}
}
