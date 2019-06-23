package demo.chapter5;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * ��ռ������һ���Զ����ͬ�����
 * @author hehaiyang
 *
 */
public class Mutex implements Lock {
	//��̬�ڲ��࣬�Զ���ͬ����
	private static class Sync extends AbstractQueuedSynchronizer{
		//�ж��Ƿ���ռ��״̬
		protected boolean isHeldExclusively(){
			return getState()==1;
		}
		//״̬Ϊ0��ʱ�򣬾Ϳ��Ի�ȡ��
		public boolean tryAcquire(int acquire){
			if (compareAndSetState(0, 1)) {
				setExclusiveOwnerThread(Thread.currentThread());
				return true;
			}
			return false;
		}
		//�ͷ��������ҽ�����״̬��Ϊ0
		protected boolean tryRelease(int release){
			if (getState() == 0) {
				throw new IllegalMonitorStateException();
			}
			setExclusiveOwnerThread(null);
			setState(0);
			return true;
		}
		//����һ��Condition��ÿ��Condition������һ��condition����
		Condition newCondition(){
			return new ConditionObject();
		}
	}

	private static final long Timeout = 0;
	
	//��Ҫ������������Sync��
	private final Sync sync = new Sync();
	
	public void lock() {
		sync.acquire(1);
	}

	public void lockInterruptibly() throws InterruptedException {
		sync.acquireInterruptibly(1);
	}

	public boolean tryLock() {
		return sync.tryAcquire(1);
	}

	public boolean tryLock(long time, TimeUnit unit)
			throws InterruptedException {
		return sync.tryAcquireNanos(1, unit.toNanos(Timeout));
	}

	public void unlock() {
		sync.tryRelease(0);
	}

	public Condition newCondition() {
		return newCondition();
	}

}
