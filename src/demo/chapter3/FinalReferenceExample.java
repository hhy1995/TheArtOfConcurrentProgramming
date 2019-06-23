package demo.chapter3;
/**
 * final������������
 * 
 * �����������ͣ�дfinal������������Ա������ʹ��������������µ�Լ����
 * �ڹ��캯���ڶ�һ��final���õĶ���ĳ�Ա���д�룬������ڹ��캯�������
 * ���������������ø�ֵ������һ�����ñ���������������֮�Ҳ��ܽ���������
 * 
 * @author hehaiyang
 *
 */
public class FinalReferenceExample {
	final int [] intArray;	//final��Ϊ��������
	static FinalReferenceExample obj;
	public FinalReferenceExample(){		//���캯��
		intArray = new int[1];	//1.��final���д��
		intArray[0] = 1;		//2.��final�����õĶ���ĳ�Ա���д��
	}
	
	public static void writerOne(){
		obj = new FinalReferenceExample(); //3.�ѹ����������ø�ֵ��ĳ�����ñ���   ��1.3  2.3���ܽ���������
	}
	
	public static void writerTwo(){
		obj.intArray[0] = 2;				//4.������Ԫ�ص�д�룬���߳̿��ܿ���������д�̺߳Ͷ��̴߳������ݾ��������⣩
	}
	
	public static void reader(){
		if (obj != null) {
			int temp = obj.intArray[0];
		}
		System.out.println(obj.intArray[0]);
	}
	public static void main(String[] args) {
		writerOne();
		System.out.println("д�߳�1ִ����֮��"+obj.intArray[0]);
		writerTwo();
		System.out.println("д�߳�2ִ����֮��"+obj.intArray[0]);
		reader();
	}
}
