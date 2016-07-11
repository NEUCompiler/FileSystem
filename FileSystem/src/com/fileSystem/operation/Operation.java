package com.fileSystem.operation;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.fileSystem.model.Path;
import com.fileSystem.model.UFile;

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
	 * 路径集合。
	 */
	private Map<String, Path> pathMap = new HashMap<>();
	
	/**
	 * 路径文件匹配集合。
	 */
	private Map<String, HashMap<String, UFile>> folders = new HashMap<>();
	
	/**
	 * 文件集合。
	 */
	private Map<String, UFile> fileMap = new HashMap<>();
	
	/**
	 * 当前目录。
	 */
	private Path presentPath = new Path("root");
	
	/**
	 * 文件id,自增类型。
	 */
	private int id;
	
	/**
	 * 是否登录。
	 */
	private boolean isLogin;
	
	/**
	 * 文件创建。
	 */
	public void create(UFile file) {
		String name = file.getName();
		String path = file.getPath();
		HashMap<String, UFile> files = folders.get(path);
			
		if (files.isEmpty() || (files.get(name) == null)) {
			files.put(name, file);
			System.out.println("文件创建成功");
			addId();
		} else {
			System.out.println("文件已经存在");
			decId();
		}
//		System.out.println(folders);
	}
	
	/**
	 * 文件删除。
	 */
	public void delete(UFile file) {
		String name = file.getName();
		String path = file.getPath();
		HashMap<String, UFile> files = folders.get(path);
		
		if (files.get(name) == null) {
			System.out.println("文件不存在");
		} else {
			files.remove(name);
			System.out.println("文件删除成功");
		}
		
	}
	
	/**
	 * 文件读取。
	 */
	public void read(UFile file) {
		if (file.isOpen()) {
			System.out.println(file.getContent());
		} else {
			System.out.println("文件未打开");
		}
	}
	
	/**
	 * 文件写入。
	 */
	public void write(UFile file) {
		if (file.isOpen()) {
			System.out.print("please input data that your want to write: ");
			String content = file.getContent();
			content = content + in.next();
			file.setContent(content);
			System.out.println(folders);
		} else {
			System.out.println("文件未打开");
		}
	}
	
	/**
	 * 文件打开。
	 */
	public UFile open(UFile file) {
		String name = file.getName();
		String path = file.getPath();
		HashMap<String, UFile> files = folders.get(path);
			
		if (files.get(name) == null) {
			System.out.println("文件不存在");
			file = null;
		} else {
			file = files.get(name);
			file.setOpen(true);
			System.out.println("文件已经打开");
		}
	
		return file;
	}
	
	/**
	 * 文件关闭。
	 */
	public void close(UFile file) {
		String name = file.getName();
		String path = file.getPath();
		HashMap<String, UFile> files = folders.get(path);
			
		if (files.get(name) == null) {
			System.out.println("文件不存在");
			file = null;
		} else {
			files.get(name).setOpen(false);
			System.out.println("文件已经关闭");
		}
	}
	
	/**
	 * 创建目录。
	 */
	public void mkdir(String newPath) {
		
		//创建空Path。
		Path path = new Path();
		
		//对空Path，进行name, patrent操作。
		String name = presentPath.getName() + "/" + newPath;
		path.setName(name);
		path.setParent(presentPath.getName());
		
		//查找Pathmap ,有的话就退出，没有就写入。
		if (pathMap.containsKey(name)) {
			System.out.println("已经存在");
			return;
		} else {
			pathMap.put(name, path);
		}
		
		//修改当前path的chird节点。
		ArrayList<String> children = pathMap.get(presentPath.getName()).getChildren();
		children.add(name);
		pathMap.get(presentPath.getName()).setChildren(children);
	}
	
	/**
	 * 改变目录。
	 */
	public void chdir() {
		//只有一种操作： 对presentPath 进行替换， 从pathmap拿出替换。
		
		//唯一的目的就是获得绝对路径，查表。
		//相对当前目录。
		
		//绝对路径 如：cd C:/A/Java
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
	
	public int getId() {
		return id;
	}
	
	public void addId() {
		id = id + 1;
	}
	
	public void decId() {
		id = id - 1;
	}
	
	public Path getPath() {
		return presentPath;
	}
	
	public Operation() {
		pathMap.put(presentPath.getName(), presentPath);
		folders.put(presentPath.getName(), new HashMap<String, UFile>());
	}
}
