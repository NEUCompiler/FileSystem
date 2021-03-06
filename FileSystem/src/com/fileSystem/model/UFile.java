package com.fileSystem.model;

public class UFile {
	/**
	 * 文件id.
	 */
	private int id;
	/**
	 * 文件名。
	 */
	private String name;
	/**
	 * 文件内容。
	 */
	private String content = "";
	/**
	 * 文件路径。
	 */
	private String path;
	/**
	 * 文件长度。
	 */
	private int length;
	/**
	 * 文件类型。
	 */
	private String type;
	/**
	 * 文件是否打开。
	 */
	private boolean isOpen;
	/**
	 * 文件是否保存。
	 */
	private boolean isSave;
	private String buffer = "";
	
	/**
	 * default construct.
	 */
	public UFile() {
		
	}
	
	/**
	 * @param id
	 * @param name
	 * @param path
	 */
	public UFile(int id, String name, String path) {
		super();
		this.id = id;
		this.name = name;
		this.path = path;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getBuffer() {
		return buffer;
	}
	
	public void setBuffer(String buffer) {
		this.buffer=buffer;
	}
	
	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}
	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * @return the length
	 */
	public int getLength() {
		return length;
	}
	/**
	 * @param length the length to set
	 */
	public void setLength(int length) {
		this.length = length;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * @return the isOpen
	 */
	public boolean isOpen() {
		return isOpen;
	}

	/**
	 * @param isOpen the isOpen to set
	 */
	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	/**
	 * @return the isSave
	 */
	public boolean isSave() {
		return isSave;
	}

	/**
	 * @param isSave the isSave to set
	 */
	public void setSave(boolean isSave) {
		this.isSave = isSave;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "id=" + id + ", name=" + name + ", content=" + content
				+ ", path=" + path + ", length=" + length + ", type=" + type
				+ ", isOpen=" + isOpen;
	}
	
}
