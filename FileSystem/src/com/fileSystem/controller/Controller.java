package com.fileSystem.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.management.RuntimeErrorException;
import javax.naming.InitialContext;

import com.fileSystem.model.Path;
import com.fileSystem.model.UFile;
import com.fileSystem.operation.Operation;

/**
 * 文件控制中心。
 * 
 * @author Liu,xinwei. Gao,baiyi. xiao,hui. Gong, chen.
 *
 */
public class Controller {
	
	private Path presentPath = new Path();
	private UFile file;
	private String presentUser = "";
	
	/**
	 * 扫描器。
	 */
	private Scanner in = new Scanner(System.in);
	
	/**
	 * 操作。
	 */
	private Operation operation;
	
	/**
	 * 用户路径。
	 */
	private static final String USER_PATH = "resource/login.txt";
	
	/**
	 * 用户集合。
	 */
	private HashMap<String, Operation> users = new HashMap<>();
	
	
	
	/**
	 * 显示界面。
	 */
	public void showWindow() {
		System.out.print("root:>");
	}
	
	/**
	 * 选择操作。
	 */
	public void chooseOperation() {
		String order;
		ArrayList<String> lists;
		login();
		
		presentPath = operation.getPath();
		
		while (!"exit".equals((order = in.nextLine()))) {
			
			lists = dealWithOrder(order);
			order = lists.get(0);
			
			if (!"".equals(lists.get(1))) { 
				file = new UFile(operation.getId(), lists.get(1), presentPath.getName());
			} 
			
			switch (order) {
				case "create": operation.create(file); break;
				case "open": file = operation.open(file); break;
				case "read": operation.read(file); break;
				case "write": operation.write(file); break;
				case "close": operation.close(file); break;
				case "delete": operation.delete(file); break;
				case "mkdir": operation.mkdir("12"); break;
				case "chdir": operation.chdir(); break;
				case "dir": operation.dir(); break;
				case "logout": logout(); break;
				case "format": operation.format(); break;
				case "login": login(); break;
				case "0": break;
				default : {
					showWindow();
					System.out.println("Your demand is wrong, please input again:");
					continue;
				}
			}
			showWindow();
		}
		
	}
	
	/**
	 * 控制中心。
	 */
	public void control() {
//		operation.login();
		showWindow();
		chooseOperation();
	}
	
	/**
	 * 输入命令处理。
	 * @param order order.
	 * @return 
	 */
	public ArrayList<String> dealWithOrder(String order) {
		ArrayList<String> lists = new ArrayList<String>();
		order = order.trim();
		
		if (order.indexOf(" ") < 0) {
			lists.add(order);
			lists.add("");
			return lists;
		}
		
		lists.add(order.substring(0, order.indexOf(" ")));
		order = order.substring(order.indexOf(" ") + 1);
		lists.add(order);
		return lists;
	}
	
	/**
	 * 登陆。
	 */
	public void login() {
		String username;
		String password;
		
		System.out.println("*************************Login**********************");
		System.out.println("username:");
		username = in.next();
		System.out.println("password:");
		password = in.next();
		
		if (users.containsKey(username)) {
			if (password.equals(users.get(username).getPassword())) {
				
				if (operation.isLogin() == true) {
					saveUserdata();
				}
				
				System.out.println("Login success!");
				presentUser = username;
				operation = users.get(presentUser);
				operation.setLogin(true);
			} else {
				System.out.println("password wrong!");
			}
		} else {
			System.out.println("username is not exist!");
		}
	}
	
	/**
	 * 退出。
	 */
	public void logout() {
		saveUserdata();
	}
	
	/**
	 * 保存用户数据。
	 */
	public void saveUserdata() {
		String presentPath = operation.getPresentPath().getName();
		Map<String, Path> pathMap = (HashMap<String, Path>)operation.getPathMap();
		Map<String, HashMap<String, UFile>> folders	 = (HashMap<String, HashMap<String, UFile>>)operation.getFolders();
		Map<String, UFile> fileMap = (HashMap<String, UFile>)operation.getFileMap();
		
		try {
			String filePath = this.getClass().getResource("/").toString();
			filePath = filePath.substring(0, filePath.length()-4) + "/resourse/";
			File file = new File(filePath, presentUser + ".txt");
			
			if (!file.exists()) {
				file.createNewFile();
			}
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			
			writer.write("presentPath:" + presentPath + "\n");
			writer.write("pathMap:\n");
			for (String pathName : pathMap.keySet()) {
				writer.write(pathMap.get(pathName).toString() + "\n");
			}
			
			writer.write("fileMap:\n");
			for (String fileName : fileMap.keySet()) {
				writer.write(fileMap.get(fileName).toString() + "\n");
			}
			
			writer.close();
			
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	public void initSystem() {
		String password;
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					new File(USER_PATH)));
			
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.indexOf("username:") >= 0) {
					operation = new Operation();
					operation.setUsername(line.substring(9, line.length()));
					password = reader.readLine();
					password = password.substring(9, password.length());
					operation.setPassword(password);
					users.put(operation.getUsername(), operation);
				}
			}
			
			reader.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public Controller() {
		initSystem();
	}
	
	public static void main(String[] args) {
		Controller controller = new Controller();
		controller.control();
	}
}
