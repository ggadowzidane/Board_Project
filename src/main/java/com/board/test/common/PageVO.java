package com.board.test.common;

public class PageVO {
	private int displayRowCount=10; //출력할 데이터 개수
	private int rowStart,rowEnd; //시작행 번호, 종료행 번호
	private int totPage , totRow = 0; // 전체 페이수, 전체 데이터수
	private int page,pageStart,pageEnd; //현재 페이지,시작페이지,종료페이지
	
	public PageVO(){}
	
	 /**
     * 전체 데이터 개수(total)를 이용하여 페이지수 계산. 
     */
    public void pageCalculate(Integer total) {
        getPage();
        this.totRow  = total;
        this.totPage    = (int) ( total / this.displayRowCount );
        
        if ( total % this.displayRowCount > 0 ) {
        	this.totPage++;
        }

        this.pageStart = (this.page - (this.page - 1) % 10) ;
        this.pageEnd = this.pageStart + 9;
        if (this.pageEnd > this.totPage) {
        	this.pageEnd = this.totPage;
        }
        this.rowStart = ((this.page - 1) * this.displayRowCount) + 1 ;
        this.rowEnd   = this.rowStart + this.displayRowCount - 1;
    } 
    

    /**
     * 현재 페이지 번호. 
     */
	public int getPage() {
        if (page == 0) 
            this.page = 1;
        return page;
    }

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
