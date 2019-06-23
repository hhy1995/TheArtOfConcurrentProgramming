package demo.chapter3;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 测试测试双重检验锁定的Bean类
 * 
 * @author hehaiyang
 * 
 */
public class DCLTestBean {
	private Integer id;
	private String name;

	public DCLTestBean() {
		long current = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(new Date(Long.parseLong(String
				.valueOf(current))));
		
		System.out.println(date+"-->DCLTestBean被初始化了");
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
