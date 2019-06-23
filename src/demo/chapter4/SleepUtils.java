package demo.chapter4;

import java.util.concurrent.TimeUnit;
/*
 * 线程sleep的工具类
 */
public class SleepUtils {
	public static final void second(long seconds){
		try {
			TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
