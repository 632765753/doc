package com.dong.core.apt;

public class BTraceTest {

	public static void main(String[] args) throws InterruptedException {
		Thread.sleep(1000*30);
		loop(1);
	}

	public static int loop(int jj) throws InterruptedException {
		for (int i = 0; i < 100; i++) {
			Thread.sleep(100);
			jj++;
		}
		return jj;
	}
	
	/**
	 * BTrace脚本 在方法输出方法的入参和返回
	 */
	/*@OnMethod(clazz = "com.dong.core.apt.BTraceTest", method = "loop", location = @Location(Kind.ENTRY))
	public static void func1(int str) {
		jstack();
		println("方法的参数" + str);
	}

	@OnMethod(clazz = "com.dong.core.apt.BTraceTest", method = "loop", location = @Location(Kind.RETURN))
	public static void func2(int str, @Return int result) {
		jstack();
		println("方法的返回" + result);
	}*/
}



