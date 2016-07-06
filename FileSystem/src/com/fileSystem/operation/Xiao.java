package com.fileSystem.operation;

import java.util.Scanner;

/**
 * 
 * @author Xiao, hui. Liu, xinwei.
 *
 */
public class Xiao {
	/**
	 * 创建目录。
	 */
	private String in=null;
	
	public class catalog{
		private String name;
		private String ca[]=null;
	}
	
	
	public void mkdir() {
		System.out.println("输入目录名");
		Scanner in = new Scanner(System.in);
		catalog one =new catalog();
		one.name = in.next();
	}
	
	/**
	 * 改变目录。
	 */
	public void chdir() {
		
	}
	
	/**
	 * 列出文件目录。
	 */
	public void dir() {
		
	}
	
	public static void main(String[] args) {
		
	}
}
