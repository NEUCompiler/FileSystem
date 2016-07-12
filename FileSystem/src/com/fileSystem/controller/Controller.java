package com.fileSystem.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
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
	/**
	 * file.
	 */
	private UFile file;
	/**
	 * 当前用户名。
	 */
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
	 * 是否登录。
	 */
	private boolean islogin;

	/**
	 * 显示界面。
	 */
	public void showWindow() {
		System.out.print("\n" + operation.getPresentPath().getName() + ">");
	}

	/**
	 * 选择操作。
	 */
	public void chooseOperation() {
		String order;
		ArrayList<String> lists;

		while (!"exit".equals((order = in.nextLine()))) {

			lists = dealWithOrder(order);
			order = lists.get(0);

			if (!"".equals(lists.get(1))) {
				file = new UFile(operation.getId(), operation.getPresentPath()
						.getName() + lists.get(1), operation.getPresentPath()
						.getName());
			}

			switch (order) {
				case "create":
					operation.create(file);
					break;
				case "open":
					file = operation.open(file);
					break;
				case "read":
					operation.read(file);
					break;
				case "write":
					operation.write(file);
					break;
				case "close":
					operation.close(file);
					break;
				case "delete":
					operation.delete(file);
					break;
				case "mkdir":
					operation.mkdir(lists.get(1));
					break;
				case "chdir":
					operation.chdir(lists.get(1));
					break;
				case "dir":
					operation.dir(lists.get(1));
					break;
				case "logout":
					logout();
					break;
				case "format":
					operation.format();
					break;
				case "login":
					login();
					break;
				case "0":
					break;
				default: {
	
					System.out.println("Your demand is wrong, please input again:");
					if (islogin) {
						showWindow();
					} else {
						System.out.print("root:>");
					}
	
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
		login();
		chooseOperation();
	}

	/**
	 * 输入命令处理。
	 * 
	 * @param order
	 *            order.
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

		System.out
				.println("*************************Login**********************");
		System.out.println("username:");
		username = in.nextLine();
		System.out.println("password:");
		password = in.nextLine();

		if (users.containsKey(username)) {
			if (password.equals(users.get(username).getPassword())) {

				if (operation.isLogin() == true) {
					ExportUserdata();
				}

				System.out.println("Login success!");
				presentUser = username;
				operation = users.get(presentUser);
				operation.setLogin(true);
				importUserData();
				showWindow();
				islogin = true;
			} else {
				System.out.println("password wrong!");
				System.out.print("root:>");
			}
		} else {
			System.out.println("username is not exist!");
			if (islogin) {
				showWindow();
			} else {
				System.out.print("root:>");
			}

		}
	}

	/**
	 * 退出。
	 */
	public void logout() {
		ExportUserdata();
	}

	/**
	 * 保存用户数据。
	 */
	public void ExportUserdata() {
		Map<String, Path> pathMap = (HashMap<String, Path>) operation
				.getPathMap();
		Map<String, HashMap<String, UFile>> folders = (HashMap<String, HashMap<String, UFile>>) operation
				.getFolders();
		Map<String, UFile> fileMap;

		try {
			File file = new File("resource/" + presentUser + ".txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));

			deleteDir(new File("resource/" + presentUser));
			if (!file.exists()) {
				file.createNewFile();
			}

			writer.write("PresentPath:\n" + operation.getPresentPath() + "\n");
			writer.write("\nPathMap:\n");
			for (String pathName : pathMap.keySet()) {
				writer.write(pathMap.get(pathName).toString() + "\n");

				pathName = pathName.substring(0, pathName.length() - 1);
				pathName = pathName.replace(":", "");

				// 建立文件夹。
				String[] splits = pathName.split("\\\\");
				pathName = "";

				for (String item : splits) {
					pathName = pathName + item;
					File path = new File("resource/" + pathName);

					if (!path.exists() && !path.isDirectory()) {
						path.mkdir();
					}

					pathName = pathName + "\\";
				}

			}

			writer.write("\nFolders:\n");
			for (String pathName : folders.keySet()) {
				writer.write("\nFPN:\n");
				writer.write(pathName + "\n");
				fileMap = (HashMap<String, UFile>) folders.get(pathName);
				writer.write("FileMap:\n");
				for (String fileName : fileMap.keySet()) {
					writer.write(fileMap.get(fileName).toString() + "\n");

					File f = new File("resource/" + fileName.replace(":", ""));
					if (!f.exists()) {
						f.createNewFile();
						BufferedWriter bF = new BufferedWriter(
								new FileWriter(f));
						bF.write(fileMap.get(fileName).getContent());
						bF.close();
					}
				}
			}

			writer.write("End;");
			writer.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 导入用户数据。
	 */
	public void importUserData() {
		Map<String, Path> pathMap = new HashMap<>();
		Map<String, HashMap<String, UFile>> folders = new HashMap<>();
		HashMap<String, UFile> fileMap = new HashMap<>();
		String key = "";
		Path path = new Path();
		File file = new File("resource/" + presentUser + ".txt");

		if (!file.exists()) {
			return;
		}

		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;

			while ((line = reader.readLine()) != null) {
				if (line.indexOf("PresentPath") == 0) {
					line = reader.readLine();
					path.setName(line.substring(line.indexOf("name=") + 5,
							line.indexOf(", parent")));
					path.setParent(line.substring(line.indexOf("parent=") + 7,
							line.indexOf(", children")));
					line = line.substring(line.indexOf("children=[") + 10,
							line.indexOf("]"));
					String[] splits = line.split(", ");

					for (String string : splits) {
						path.getChildren().add(string);
					}

					operation.setPresentPath(path);
				} else if (line.indexOf("PathMap") == 0) {

					while ((line = reader.readLine()).indexOf("name=") == 0) {
						path = new Path();
						key = line.substring(line.indexOf("name=") + 5,
								line.indexOf(", parent"));
						path.setName(key);
						path.setParent(line.substring(
								line.indexOf("parent=") + 7,
								line.indexOf(", children")));
						line = line.substring(line.indexOf("children=[") + 10,
								line.indexOf("]"));
						String[] splits = line.split(", ");

						for (String string : splits) {
							path.getChildren().add(string);
						}
						pathMap.put(key, path);
					}
					operation.setPathMap(pathMap);
				} else if (line.indexOf("Folders") == 0) {

					while ((line = reader.readLine()) != null) {
						if (line.indexOf("FPN") == 0) {
							key = reader.readLine();
						} else if (line.indexOf("FileMap") == 0) {
							// fileMap = new HashMap<String, UFile>();
							while ((line = reader.readLine()).indexOf("id=") == 0) {
								UFile uFile = new UFile();
								uFile.setId(Integer.parseInt(line.substring(3,
										line.indexOf(", name="))));
								uFile.setName(line.substring(
										line.indexOf("name=") + 5,
										line.indexOf(", content=")));
								uFile.setContent(line.substring(
										line.indexOf("content=") + 8,
										line.indexOf(", path=")));
								uFile.setPath(line.substring(
										line.indexOf("path=") + 5,
										line.indexOf(", length=")));
								uFile.setId(Integer.parseInt(line.substring(
										line.indexOf("length=") + 7,
										line.indexOf(", type="))));
								uFile.setType(line.substring(
										line.indexOf("type=") + 5,
										line.indexOf(", isOpen=")));
								uFile.setOpen(Boolean.parseBoolean(line
										.substring(line.indexOf("isOpen=") + 7,
												line.length())));
								fileMap.put(uFile.getName(), uFile);
							}
							folders.put(key, fileMap);
						}
					}
					operation.setFolders(folders);
				}
			}

			reader.close();
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	/**
	 * 模拟删除文件夹。
	 * 
	 * @param dir
	 * @return
	 */
	private static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			// 递归删除目录中的子目录下
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
	}

	/**
	 * 构造器实现部分初始化。
	 */
	public Controller() {
		String password;

		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(
					USER_PATH)));

			String line;
			while ((line = reader.readLine()) != null) {
				if (line.indexOf("username:") >= 0) {
					operation = new Operation(line.substring(9, line.length()));
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

	public static void main(String[] args) {
		Controller controller = new Controller();
		controller.control();
	}
}
