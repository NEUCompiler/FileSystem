package com.fileSystem.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.tools.DocumentationTool.Location;

import org.omg.PortableServer.ID_ASSIGNMENT_POLICY_ID;

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
	private String location;
	
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
		UFile file = null;
		
		while (!"exit".equals((order = in.nextLine()))) {
			
			lists = dealWithOrder(order);
			order = lists.get(0);
			file = new UFile(operation.getId(), order, location);
			
			switch (order) {
				case "create": operation.create(file); break;
				case "open": operation.open(); break;
				case "read": operation.read(); break;
				case "write": operation.write(); break;
				case "close": operation.close(); break;
				case "delete": operation.delete(); break;
				case "mkdir": operation.mkdir("12"); break;
				case "chdir": operation.chdir(); break;
				case "dir": operation.dir(); break;
				case "logout": operation.logout(); break;
				case "format": operation.format(); break;
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
