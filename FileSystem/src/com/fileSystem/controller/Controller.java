package com.fileSystem.controller;

import java.util.Scanner;

import com.fileSystem.operation.Operation;

/**
 * 文件控制中心。
 * 
 * @author Liu,xinwei. Gao,baiyi. xiao,hui. Gong, chen.
 *
 */
public class Controller {
	
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
		System.out.println("***********************************FiveStates**********************************************");
		System.out.println("1.Create   2.Open    3.Read   4.Write   5.Close  6.Delete  7.Mkdir 8.Chdir 9.dir 10.logout 11.format 0.Exit");
		System.out.println("********************************************************************************************");
	}
	
	/**
	 * 选择操作。
	 */
	public void chooseOperation() {
		String order;
		
		while (!"0".equals((order = in.next()))) {
			switch (order) {
				case "1": operation.create(); break;
				case "2": operation.open(); break;
				case "3": operation.read(); break;
				case "4": operation.write(); break;
				case "5": operation.close(); break;
				case "6": operation.delete(); break;
				case "7": operation.mkdir(); break;
				case "8": operation.chdir(); break;
				case "9": operation.dir(); break;
				case "10": operation.logout(); break;
				case "11": operation.format(); break;
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
		operation.login();
		showWindow();
		chooseOperation();
	}
	
	public static void main(String[] args) {
		Controller controller = new Controller();
		controller.control();
	}
}
