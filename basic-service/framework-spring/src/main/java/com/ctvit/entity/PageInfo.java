package com.ctvit.entity;

public class PageInfo {
	
	private int rows = 10;
	
	private int pageSum;
	
	private int total;
	
	private int curPage = 0;
	
	

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getPageSum() {
		pageSum = total/rows==0?total/rows:(total/rows+1);
		return pageSum;
	}


	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	
	

}
