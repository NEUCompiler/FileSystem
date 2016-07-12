package com.fileSystem.operation;

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
	
	private String username = "";
	private String password = "";
	
	/**
	 * 扫描器。
	 */
	private Scanner in = new Scanner(System.in);
	
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
	private Path presentPath;
	
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
		file.setType(name.substring(name.indexOf(".")));
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
			content = content + in.nextLine();
			file.setContent(content);
			file.setLength(content.length());
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
	 * 创建目录,需提供创建目录名(newPath),在当前目录下创建.
	 */
	public void mkdir(String newPath) {
	
		//对空Path，进行name, patrent操作。
		String name = presentPath.getName() + newPath + "\\";
		//查找Pathmap ,有的话就退出，没有就写入。
		if (pathMap.containsKey(name)) {
			System.out.println("已经存在");
			return;
		} else {
			//创建空Path.
			Path path = new Path();
			path.setName(name);
			path.setParent(presentPath.getName());
			pathMap.put(name, path);
			//修改当前path的chird节点。
			ArrayList<String> children = pathMap.get(presentPath.getName()).getChildren();
			children.add(name);
			
			folders.put(name, new HashMap<String, UFile>());
			pathMap.get(presentPath.getName()).setChildren(children);
		}
	}
	
	/**
	 * 改变目录.需提供目标目录
	 * (替换当前目录presentPath 用目标目录newPath 如：cd C:/A/Java)
	 */
	public void chdir(String newPath) {
		newPath = newPath + "\\";
		//只有一种操作： 对presentPath 进行替换， 从pathmap拿出替换。
		//查找Pathmap ,有的话就退出，没有就写入.(绝对路径.)绝对路径 如：cd C:/A/Java
		if (newPath.indexOf(":")>0){
			//绝对路径查询
			if (pathMap.containsKey(newPath)) {
				presentPath = pathMap.get(newPath);
			} else {
				System.out.println("目录不存在");
			}
		}
		else{
			//相对路径查询
			String name = presentPath.getName() + newPath;
			if (pathMap.containsKey(name)){
				presentPath = pathMap.get(name);
			} else {
				System.out.println("目录不存在");
			}
		}
	}
	
	/**
	 * 列出文件目录。
	 */
	public void dir() {
		
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
	
	/**
	 * @return the isLogin
	 */
	public boolean isLogin() {
		return isLogin;
	}

	/**
	 * @param isLogin the isLogin to set
	 */
	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public Operation() {
		
	}

	/**
	 * @return the in
	 */
	public Scanner getIn() {
		return in;
	}

	/**
	 * @return the pathMap
	 */
	public Map<String, Path> getPathMap() {
		return pathMap;
	}

	/**
	 * @param pathMap the pathMap to set
	 */
	public void setPathMap(Map<String, Path> pathMap) {
		this.pathMap = pathMap;
	}

	/**
	 * @return the folders
	 */
	public Map<String, HashMap<String, UFile>> getFolders() {
		return folders;
	}

	/**
	 * @param folders the folders to set
	 */
	public void setFolders(Map<String, HashMap<String, UFile>> folders) {
		this.folders = folders;
	}

	/**
	 * @return the fileMap
	 */
	public Map<String, UFile> getFileMap() {
		return fileMap;
	}

	/**
	 * @param fileMap the fileMap to set
	 */
	public void setFileMap(Map<String, UFile> fileMap) {
		this.fileMap = fileMap;
	}

	/**
	 * @return the presentPath
	 */
	public Path getPresentPath() {
		return presentPath;
	}

	/**
	 * @param presentPath the presentPath to set
	 */
	public void setPresentPath(Path presentPath) {
		this.presentPath = presentPath;
	}

	public Operation(String username) {
		this.username = username;
		presentPath = new Path(username + ":\\");
		pathMap.put(presentPath.getName(), presentPath);
		folders.put(presentPath.getName(), new HashMap<String, UFile>());
	}
}
