package com.fileSystem.operation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

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
			
		if (files.isEmpty() || (files.get(name) == null) || files == null) {
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
		String name = file.getName();
		String path = file.getPath();
		HashMap<String, UFile> files = folders.get(path);
		file = files.get(name);
		if (file == null) {
			System.out.println("文件不存在");
		} else {
			if (file.isOpen()) {
				System.out.println(file.getContent());
			} else {
				System.out.println("文件未打开");
			}
		}
	}
	
	/**
	 * 文件写入。
	 */
	public void write(UFile file) {
		String name = file.getName();
		String path = file.getPath();
		HashMap<String, UFile> files = folders.get(path);
		file = files.get(name);
			if (file == null) {
				System.out.println("文件不存在");
			}else {
				if (file.isOpen()) {   //文件打开方可操作
					file.setSave(false);    //设置当前保存转态为未保存
					System.out.print("please input data that your want to write: ");
					String input = in.next();
					String buffer = file.getBuffer() + input;
					file.setBuffer(buffer);  //输入写入缓冲区内容
				}
		     else {
			System.out.println("文件未打开");}
	}}
	
	/**
	 * 文件打开。
	 */
	public UFile open(UFile file) {
		String name = file.getName(); //得到当前所操作文件的名字
		String path = file.getPath();  //得到当前所操作文件的路径
		HashMap<String, UFile> files = folders.get(path);  //从路径表中找到该路径对应的文件表
		file = files.get(name);	 //从该文件表中找到该文件名对应的文件
		if (file == null) {
			System.out.println("文件不存在");
			file = null;
		} else {
			file = files.get(name);
			file.setOpen(true);
			String content = file.getContent();
			file.setBuffer(content);  //将缓冲区内容设为与文本内容相同
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
		file = files.get(name);	
		if (file == null) {
			System.out.println("文件不存在");
			file = null;
			return;
		} 
		else if(file.isSave()){  //文件已经保存了则不用再保存
			System.out.println("文件已保存");
		}
		else {  //文件未保存
			System.out.println("保存文件？（y保存，n不保存,其他键取消）");
			String choose=in.next();
			if(choose.equals("y"))   //保存文件
			{
				String buff=file.getBuffer(); //将缓冲区内容写入文本内容
				file.setContent(buff);
				System.out.println("文件已保存");
			}
			else if(choose.equals("n")){   //不保存
				String content=file.getContent();
				file.setContent(content);  //缓冲区内容变为文本内容
				file.setBuffer(content);
			}else{
				System.out.println("已取消操作");
				return;
			}
			}
			file.setOpen(false);
			file.setSave(true);
			System.out.println("文件已经关闭");
		}
	
	
	/**
	 * 文件保存。
	 */
	public void save(UFile file) 
	{
		String name = file.getName();
		String path = file.getPath();
		HashMap<String, UFile> files = folders.get(path);
		file = files.get(name);	
		if (file == null) {
			System.out.println("文件不存在");
			file = null;
		} else {
			file.setContent(file.getBuffer());  //将缓冲区内容写入文本内容
			file.setSave(true);   //文件状态设置为已保存
			System.out.println("文件已经保存");
		}	
	}
	
	
	
	 /**创建目录 ，需要目标目录名
	  *
	  */
     public void mkdir(String newPath) {
		//对newPath进行系统名补充操作成为name
		String name = presentPath.getName() + newPath + "\\";
		//查找Pathmap ,有的话就退出，没有就写入。
		if (pathMap.containsKey(name)) {
			System.out.println("已经存在");
			return;
		} else {
			//创建空Path.修改目录表pathMap的内容
			Path path = new Path();
			path.setName(name);
			path.setParent(presentPath.getName());
			pathMap.put(name, path);
			//修改当前目录presentPath的chird节点.
			ArrayList<String> children = pathMap.get(presentPath.getName()).getChildren();
			children.add(name);
			folders.put(name, new HashMap<String, UFile>());
			pathMap.get(presentPath.getName()).setChildren(children);
			presentPath = pathMap.get(presentPath.getName());
		}
	}
	
	/**
	 * 改变目录.需提供目标目录
	 * (替换当前目录presentPath 用目标目录newPath)
	 */
	public void chdir(String newPath) {
		newPath = newPath + "\\";
		String name ;
		//只有一种操作： 对presentPath 进行替换， 从pathmap拿出替换。
		//查找Pathmap 
		if (newPath.indexOf(":")>0){
			//绝对路径查询
			if (pathMap.containsKey(newPath)) {
				presentPath = pathMap.get(newPath);
			} else {
				System.out.println("目录不存在");
			}
		}
		//回退上级目录
		else if (newPath.indexOf("...")<0 && newPath.indexOf("..")>=0){
				name =presentPath.getParent();
				presentPath =pathMap.get(name);
			}
		else{//相对路径查询
		  name = presentPath.getName() + newPath;
			if (pathMap.containsKey(name)){
				presentPath = pathMap.get(name);
			} else {
				System.out.println("目录不存在");
			}
		}
	}
	
	/**
	 * 列出文件目录。
	 * 需提供目标目录。
	 */
	public void dir(String newPath) {
		ArrayList<String> children = new ArrayList<String>();//目录列出
		Set<String> childrens;//文件列出
		//列出绝对路径下的子目录
		if (newPath.indexOf(":")>0){
			 children = pathMap.get(newPath).getChildren();
			 childrens = folders.get(newPath).keySet();
			 for (String string : childrens) {
				  String[] splits = string.split("\\\\");
				  System.out.print(splits[splits.length-1] + "    ");
				  System.out.println();
			}
		}
		else //列出当前路径下的子目录
		{
			 children = presentPath.getChildren();
			 childrens = folders.get(presentPath.getName()).keySet();
			 for (String string : childrens) {
				 String[] splits = string.split("\\\\");
					System.out.print(splits[splits.length-1] + "    ");
					System.out.println();
			}
		}
		for (String child : children) {
			if (child.length() == 0) {
				continue;
			}
			child = child.substring(0, child.length()-1);
			String[] splits = child.split("\\\\");
			System.out.print(splits[splits.length-1] + "    ");
			System.out.println();
		}
	}
	
	
	/**
	 * 格式化。
	 */
	public void format() {
		presentPath = new Path(username + ":\\");
		pathMap = new HashMap<String, Path>();
		folders = new HashMap<String, HashMap<String, UFile>>();
		fileMap = new HashMap<String, UFile>();
		pathMap.put(presentPath.getName(), presentPath);
		folders.put(presentPath.getName(), new HashMap<String, UFile>());
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
