package com.fileSystem.controller;

import java.util.ArrayList;
import java.util.Scanner;
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
	
	private String command;
	private Path presentPath = new Path();
	private UFile file ;
	
	/**
	 * 扫描器。
	 */
	private Scanner in = new Scanner(System.in);
	
	/**
	 * 操作。
	 */
	private Operation operation = new Operation();
	
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
				case "logout": operation.logout(); break;
				case "format": operation.format(); break;
				case "login": operation.login(); break;
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
	
	public static void main(String[] args) {
		Controller controller = new Controller();
		controller.control();
	}
}
