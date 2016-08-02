package cn.bonjour.test;

import java.util.Properties;

public class MyTest {

	public static void main(String[] args) throws Exception {
		Properties prop = new Properties();
		String path = MyTest.class.getClassLoader().getResource("cn/bonjour/test/combination.properties").getPath();
		System.out.println(path);
		prop.load(MyTest.class.getClassLoader().getResourceAsStream("cn/bonjour/test/combination.properties"));
		prop.size();
		System.out.println(prop.size());
		System.out.println(prop.getProperty(prop.size()+""));
	}

}
