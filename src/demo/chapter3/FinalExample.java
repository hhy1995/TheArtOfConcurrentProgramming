package demo.chapter3;

/**
 * final�����������ʾ���Դ���
 * 
 * @author hehaiyang
 * 
 */
public class FinalExample {
	int i; // ��ͨ����
	final int j; // final����
	static FinalExample obj;

	public FinalExample() { // ���캯��
		i = 1;
		j = 2;				//дfinal�������������ֹ��final���д�����򵽹��캯��֮�⣬��������Ĺ����У�
							//���ܶ�����ͨ���д�������ŵ����캯��֮�⡣���ͬʱ�����ܵ�����������ִ�е��̶߳�ȡ����ͨ������ĳ�ʼֵ
							//final��Ķ�ȡ��������ȷ�ģ����Ƕ�����ͨ������Ķ�ȡ�������⡣
	}

	public static void writer() {
		obj = new FinalExample();
	}
	
	public static void reader(){
		FinalExample object = obj;		//�����������
		int a = object.i;				//����ͨ��
		int b = object.j;				//��final��
	}
}
