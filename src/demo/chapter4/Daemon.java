package demo.chapter4;
/*
 * Daemon�߳���һ��֧�����̣߳���Ҫ���ڳ����к�̨���Ⱥ�֧���Թ���
 */
public class Daemon {
	static class DaemonRunner implements Runnable{
		public void run() {
			try {
				SleepUtils.second(10);
			} finally{
				//finally���е����ݲ�û��ִ�У������ڹ���Daemon�߳�ʱ��
				//��������finally���е�������ȷ��ִ�йرջ�������Դ���߼�
				System.out.println("Daemon Thread Run...");
			}
		}
	}
	
	public static void main(String[] args) {
		Thread thread = new Thread(new DaemonRunner(),"DaemonRunner");
		//������Ϊ�ػ��߳�
		thread.setDaemon(true);
		thread.start();
	}
}
