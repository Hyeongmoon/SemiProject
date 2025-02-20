package com.fh.freeBoard.model.vo;

public class FreeBoardLike {

	
	//필드부
	private int userNo;// USER_NO	NUMBER
	private int freeNo;//	 FREE_NO	NUMBER
	

	//db에 없는 필드 추가
	private int freeLikeCount; 	//총좋아요수
	private int freeLikeStatus; //좋아요클릭여부 1:클릭 0:클릭x
	
	
	//생성자부
	//기본생성자
	public FreeBoardLike() {}

	//전체 매개변수 생성자
	public FreeBoardLike(int userNo, int freeNo) {
		super();
		this.userNo = userNo;
		this.freeNo = freeNo;
	}

	
	//메소드부
	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public int getFreeNo() {
		return freeNo;
	}

	public void setFreeNo(int freeNo) {
		this.freeNo = freeNo;
	}

	
	
	
	public int getFreeLikeCount() {
		return freeLikeCount;
	}

	public void setFreeLikeCount(int freeLikeCount) {
		this.freeLikeCount = freeLikeCount;
	}

	public int getFreeLikeStatus() {
		return freeLikeStatus;
	}

	public void setFreeLikeStatus(int freeLikeStatus) {
		this.freeLikeStatus = freeLikeStatus;
	}

	//tdString
	@Override
	public String toString() {
		return "FreeBoardLike [userNo=" + userNo + ", freeNo=" + freeNo + "]";
	}
	
	
	
	
	
}
