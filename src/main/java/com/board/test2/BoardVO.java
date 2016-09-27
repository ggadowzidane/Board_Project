package com.board.test2;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class BoardVO {
	private String brdNo,brdTitle,brdWriter,brdMemo,brdDate,brdHit,brdDeleteFlag,fileCnt;
	private String bgNo;
	private List<MultipartFile> uploadfile;

	public BoardVO(){}
	
	public String getBgNo() {
		return bgNo;
	}
	
	public void setBgNo(String bgNo) {
		this.bgNo = bgNo;
	}

	public void setFileCnt(String fileCnt){
		this.fileCnt = fileCnt;
	}
	public String getFileCnt(){
		return this.fileCnt;
	}
	public void setUploadfile(List<MultipartFile> uploadfile){
		this.uploadfile = uploadfile;
	}
	public List<MultipartFile> getUploadfile(){
		return this.uploadfile;
		
	}
	public String getBrdNo() {
		return brdNo;
	}

	public void setBrdNo(String brdNo) {
		this.brdNo = brdNo;
	}

	public String getBrdTitle() {
		return brdTitle;
	}

	public void setBrdTitle(String brdTitle) {
		this.brdTitle = brdTitle;
	}

	public String getBrdWriter() {
		return brdWriter;
	}

	public void setBrdWriter(String brdWriter) {
		this.brdWriter = brdWriter;
	}

	public String getBrdMemo() {
		return brdMemo;
	}

	public void setBrdMemo(String brdMemo) {
		this.brdMemo = brdMemo;
	}

	public String getBrdDate() {
		return brdDate;
	}

	public void setBrdDate(String brdDate) {
		this.brdDate = brdDate;
	}

	public String getBrdHit() {
		return brdHit;
	}

	public void setBrdHit(String brdHit) {
		this.brdHit = brdHit;
	}

	public String getBrdDeleteFlag() {
		return brdDeleteFlag;
	}

	public void setBrdDeleteFlag(String brdDeleteFlag) {
		this.brdDeleteFlag = brdDeleteFlag;
	}
	
	
}
