package demo.chapter5;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 独占锁，是一个自定义的同步组件
 * @author hehaiyang
 *
 */
public class Mutex implements Lock {
	//静态内部类，自定义同步器
	private static class Sync extends AbstractQueuedSynchronizer{
		//判断是否处于占用状态
		protected boolean isHeldExclusively(){
			return getState()==1;
		}
		//状态为0的时候，就可以获取锁
		public boolean tryAcquire(int acquire){
			if (compareAndSetState(0, 1)) {
				setExclusiveOwnerThread(Thread.currentThread());
				return true;
			}
			return false;
		}
		//释放锁，并且将锁的状态置为0
		protected boolean tryRelease(int release){
			if (getState() == 0) {
				throw new IllegalMonitorStateException();
			}
			setExclusiveOwnerThread(null);
			setState(0);
			return true;
		}
		//返回一个Condition，每个Condition都包含一个condition队列
		Condition newCondition(){
			return new ConditionObject();
		}
	}

	private static final long Timeout = 0;
	
	//需要将操作代理在Sync上
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
