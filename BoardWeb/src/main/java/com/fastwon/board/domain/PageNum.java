package com.fastwon.board.domain;

import lombok.Data;

@Data
public class PageNum {
	private int num = 1;
	
	private int cmtPage = 1;

	public void setNum(int num) {
		if(num == 0) num = 1;
		this.num = num;
	}

	public void setCmtPage(int cmtPage) {
		if(cmtPage == 0) cmtPage = 1;
		this.cmtPage = cmtPage;
	}
	
	
}
