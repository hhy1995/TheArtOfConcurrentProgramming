package demo.chapter4;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

public class Piped {
	//静态方法，实现对输入数据的原样打印
	static class Print implements Runnable{
		private PipedReader in;

		public Print(PipedReader in) {
			this.in = in;
		}
		
		public void run() {
			int receive = 0;
			try {
				while((receive = in.read()) != -1){
					System.out.print((char)receive);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		PipedWriter out = new  PipedWriter();
		PipedReader in = new PipedReader();
		//需要将输入流和输出流进行连接,否则会报以下的错误：Pipe not connected
		//out.connect(in);
		in.connect(out);
		Thread printThread = new Thread(new Print(in),"PrintThread");
		printThread.start();
		int receive = 0;
		try {
			while((receive = System.in.read())!= -1){
				out.write(receive);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			out.close();
		}
	}
}
