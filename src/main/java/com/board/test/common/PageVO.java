package com.board.test.common;

public class PageVO {
	private int displayRowCount=10; //출력할 데이터 개수
	private int rowStart,rowEnd; //시작행 번호, 종료행 번호
	private int totPage , totRow = 0; // 전체 페이수, 전체 데이터수
	private int page,pageStart,pageEnd; //현재 페이지,시작페이지,종료페이지
	
	public PageVO(){}

	public int getDisplayRowCount() {
		return displayRowCount;
	}

	public void setDisplayRowCount(int displayRowCount) {
		this.displayRowCount = displayRowCount;
	}

	public int getRowStart() {
		return rowStart;
	}

	public void setRowStart(int rowStart) {
		this.rowStart = rowStart;
	}

	public int getRowEnd() {
		return rowEnd;
	}

	public void setRowEnd(int rowEnd) {
		this.rowEnd = rowEnd;
	}

	public int getTotPage() {
		return totPage;
	}

	public void setTotPage(int totPage) {
		this.totPage = totPage;
	}

	public int getTotRow() {
		return totRow;
	}

	public void setTotRow(int totRow) {
		this.totRow = totRow;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageStart() {
		return pageStart;
	}

	public void setPageStart(int pageStart) {
		this.pageStart = pageStart;
	}

	public int getPageEnd() {
		return pageEnd;
	}

	public void setPageEnd(int pageEnd) {
		this.pageEnd = pageEnd;
	}
	
	
}
