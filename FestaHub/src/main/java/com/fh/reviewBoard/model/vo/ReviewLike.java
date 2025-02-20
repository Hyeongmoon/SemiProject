package com.fh.reviewBoard.model.vo;

public class ReviewLike {
	
	//필드부
	public int rvNo;
	
	public int userNo;
	
	//생성자부
	public ReviewLike() {}

	public ReviewLike(int rvNo, int userNo) {
		super();
		this.rvNo = rvNo;
		this.userNo = userNo;
	}

	//메소드부
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
		return "ReviewLike [rvNo=" + rvNo + ", userNo=" + userNo + "]";
	}
	
	
	
	
	
	
	

}
