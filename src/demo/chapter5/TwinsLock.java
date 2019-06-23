package demo.chapter5;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;


public class TwinsLock implements Lock {
	private final Sync sync = new Sync(2);
	
	/*
	 * �Զ���ͬ�����������̷߳��ʺ�ͬ��״̬��ȡ
	 */
	private static final class Sync extends AbstractQueuedSynchronizer{
		Sync(int count) {
			if (count <= 0) {
				throw new IllegalArgumentException("count must large than zero");
			}
			setState(count);
		}
		public int tryAcquireShared(int reduceCount){
			for(;;){//�������ķ�ʽ����ȡͬ��״̬
				int current = getState();
				int newCount = current - reduceCount;
				if (newCount < 0 || compareAndSetState(current, newCount)) {
					return newCount;
				}
			}
		}
		
		public boolean tryReleaseShared(int returnCount){
			for(;;){
				int current = getState();
				int newCount = current + returnCount;
				if (compareAndSetState(current, newCount)) {
					return true;
				}
			}
		}
	}

	/*
	 * ����
	 * (non-Javadoc)
	 * @see java.util.concurrent.locks.Lock#lock()
	 */
	public void lock() {
		sync.tryAcquireShared(1);
	}
	/*
	 * �ͷ���
	 * (non-Javadoc)
	 * @see java.util.concurrent.locks.Lock#unlock()
	 */
	public void unlock() {
		sync.tryReleaseShared(1);
	}

	public void lockInterruptibly() throws InterruptedException {
	}

	public boolean tryLock() {
		return false;
	}

	public boolean tryLock(long time, TimeUnit unit)
			throws InterruptedException {
		return false;
	}
	public Condition newCondition() {
		return null;
	}

}
