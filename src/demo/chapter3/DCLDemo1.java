package demo.chapter3;
/**
 * ����ʽ����
 * @author hehaiyang
 *
 */
public class DCLDemo1 {
	private static DCLTestBean instance;

	public static DCLTestBean getInstance() {
		
		/*//��ͨ����ģʽ
		if (instance == null) {			//������instanceΪ�յĻ����򴴽�instance����
			instance = new DCLTestBean();	
		}*/
		
		//˫�ؼ������
		if (instance == null) {//��һ�μ��
			synchronized (DCLDemo1.class) {//����
				if (instance == null) {//�ڶ��μ��
					instance = new DCLTestBean();//���ܳ���������ĵط�
				}
			}
		}
		return instance;
	}
}
