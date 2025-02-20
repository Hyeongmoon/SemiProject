package com.fh.reviewBoard.model.vo;

import java.sql.Date;

public class ReviewComment {
	
	//필드부
	public int rvCommNo;
	
	public String rvCommContent;
	
	public Date rvCommDate;
	
	public String rvCommStatus;
	
	public int rvNo;
	
	public int userNo;
	
	//생성자부
	
	public ReviewComment () {}

	public ReviewComment(int rvCommNo, String rvCommContent, Date rvCommDate, String rvCommStatus, int rvNo,
			int userNo) {
		super();
		this.rvCommNo = rvCommNo;
		this.rvCommContent = rvCommContent;
		this.rvCommDate = rvCommDate;
		this.rvCommStatus = rvCommStatus;
		this.rvNo = rvNo;
		this.userNo = userNo;
	}
	
	//메소드부

	public int getRvCommNo() {
		return rvCommNo;
	}

	public void setRvCommNo(int rvCommNo) {
		this.rvCommNo = rvCommNo;
	}

	public String getRvCommContent() {
		return rvCommContent;
	}

	public void setRvCommContent(String rvCommContent) {
		this.rvCommContent = rvCommContent;
	}

	public Date getRvCommDate() {
		return rvCommDate;
	}

	public void setRvCommDate(Date rvCommDate) {
		this.rvCommDate = rvCommDate;
	}

	public String getRvCommStatus() {
		return rvCommStatus;
	}

	public void setRvCommStatus(String rvCommStatus) {
		this.rvCommStatus = rvCommStatus;
	}

	public int getRvNo() {
		return rvNo;
	}

	public void setRvNo(int rvNo) {
		this.rvNo = rvNo;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	@Override
	public String toString() {
		return "ReviewComment [rvCommNo=" + rvCommNo + ", rvCommContent=" + rvCommContent + ", rvCommDate=" + rvCommDate
				+ ", rvCommStatus=" + rvCommStatus + ", rvNo=" + rvNo + ", userNo=" + userNo + "]";
	}
	
	
	
	

}
