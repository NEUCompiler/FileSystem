package com.fileSystem.operation;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

/**
 * 基本操作。
 * 
 * @author Liu,xinwei. Gao,bayi. Xiao,hui. Gong,chen.
 *
 */
public class Operation {
	
	/**
	 * 扫描器。
	 */
	private Scanner in = new Scanner(System.in);
	
	/**
	 * 用户路径。
	 */
	private static final String USER_PATH = "resource/login.txt";
	
	/**
	 * 用户集合。
	 */
	private HashMap<String, String> users = new HashMap<>();
	
	/**
	 * 是否登录。
	 */
	private boolean isLogin;
	
	/**
	 * 文件创建。
	 */
	public void create() {
		
	}
	
	/**
	 * 文件删除。
	 */
	public void delete() {
		
	}
	
	/**
	 * 文件读取。
	 */
	public void read() {
		
	}
	
	/**
	 * 文件写入。
	 */
	public void write() {
		
	}
	
	/**
	 * 文件打开。
	 */
	public void open() {
		
	}
	
	/**
	 * 文件关闭。
	 */
	public void close() {
		
	}
	
	/**
	 * 创建目录。
	 */
	public void mkdir() {
		
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
	
	/**
	 * 登陆。
	 */
	public void login() {
		String username;
		String password;
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					new File(USER_PATH)));
			
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.indexOf("username:") >= 0) {
					username = line.substring(9, line.length());
					password = reader.readLine();
					password = password.substring(9, password.length());
					users.put(username, password);
				}
			}
			
			reader.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		while (!isLogin) {
			System.out.println("*************************Login**********************");
			System.out.println("username:");
			username = in.next();
			System.out.println("password:");
			password = in.next();
			
			if (users.containsKey(username)) {
				if (password.equals(users.get(username))) {
					System.out.println("Login success!");
					isLogin = true;
				} else {
					System.out.println("password wrong!");
				}
			} else {
				System.out.println("username is not exist!");
			}
		}
	}
	
	/**
	 * 退出。
	 */
	public void logout() {
		
	}
	
	/**
	 * 格式化。
	 */
	public void format() {
		
	}
}
