package demo.chapter5;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ͨ���н���е�ʾ�����˽�Condition��ʹ�÷�ʽ
 * @author hehaiyang
 *
 * @param <T>
 */
public class BoundedQueue<T> {
	private Object[] items;
	//��ӵ��±꣬ɾ�����±�����鵱ǰ����
	private int addIndex,removeIndex,count;
	private Lock lock = new ReentrantLock();
	private Condition notEmpty = lock.newCondition();
	private Condition notFull = lock.newCondition();
	
	//��ģ����е�������г�ʼ��
	public BoundedQueue(int size){
		items = new Object[size];
	}
	
	//���һ��Ԫ�أ�������в����Ļ���ֱ�Ӽ��룻������������Ļ���������߳̽���ȴ�״̬
	public void add(T t) throws InterruptedException {
		//���Ȼ��������֤������޸ĵĿɼ��Ժ�������
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
	
	//ɾ��һ��Ԫ�أ�������зǿյĻ���ֱ��ɾ������������ǿյĻ���������߳̽���ȴ�״̬,ֱ������µ�Ԫ��
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
