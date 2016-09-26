package com.board.test2;

public class BoardReplyVO {
	private String brdNo,reNo,reWriter,reDeleteflag,reMemo,reDate;
	private String reParent,reDepth;
	private int reOrder;
	
	public BoardReplyVO(){}

	public String getReParent() {
		return reParent;
	}

	public void setReParent(String reParent) {
		this.reParent = reParent;
	}

	public String getReDepth() {
		return reDepth;
	}

	public void setReDepth(String reDepth) {
		this.reDepth = reDepth;
	}
	
	public int getReOrder() {
		return reOrder;
	}
	
	public void setReOrder(int reOrder) {
		this.reOrder = reOrder;
	}

	public String getBrdNo() {
		return brdNo;
	}

	public void setBrdNo(String brdNo) {
		this.brdNo = brdNo;
	}

	public String getReNo() {
		return reNo;
	}

	public void setReNo(String reNo) {
		this.reNo = reNo;
	}

	public String getReWriter() {
		return reWriter;
	}

	public void setReWriter(String reWriter) {
		this.reWriter = reWriter;
	}

	public String getReDeleteflag() {
		return reDeleteflag;
	}

	public void setReDeleteflag(String reDeleteflag) {
		this.reDeleteflag = reDeleteflag;
	}

	public String getReMemo() {
		return reMemo;
	}

	public void setReMemo(String reMemo) {
		this.reMemo = reMemo;
	}

	public String getReDate() {
		return reDate;
	}

	public void setReDate(String reDate) {
		this.reDate = reDate;
	}

	@Override
	public String toString() {
		return "BoardReplyVO [brdNo=" + brdNo + ", reNo=" + reNo
				+ ", reWriter=" + reWriter + ", reDeleteflag=" + reDeleteflag
				+ ", reMemo=" + reMemo + ", reDate=" + reDate + ", reParent="
				+ reParent + ", reDepth=" + reDepth + ", reOrder=" + reOrder
				+ "]";
	}
	
	
}
