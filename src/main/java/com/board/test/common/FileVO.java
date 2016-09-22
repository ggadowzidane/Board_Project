package com.board.test.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class FileVO {
	private int fileNo;			//파일번호
	private String parentPK;	//부모 글번호
	private String fileName;	//파일명
	private String realName;	//실제파일명
	private	long fileSize;		//파일크기
	
	public FileVO(){}
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	public int getFileNo() {
		return fileNo;
	}
	public void setFileNo(int fileNo) {
		this.fileNo = fileNo;
	}
	public String getParentPK() {
		return parentPK;
	}
	public void setParentPK(String parentPK) {
		this.parentPK = parentPK;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long filesize) {
		this.fileSize = filesize;
	}
	/*파일 크기 계산*/
	public String size2String(){
		int size = 1024;
		if(this.fileSize < size){
			return String.format("(%d B)",this.fileSize);
		}
		int exp = (int) (Math.log(this.fileSize)/ Math.log(size));
		return String.format("(%.0f %s)",this.fileSize/Math.pow(size,exp),"KMGTPE".charAt(exp - 1));
	}
}
