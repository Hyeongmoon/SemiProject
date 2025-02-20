package com.fh.freeBoard.model.vo;

import java.sql.Date;

public class FreeBoardComment {

	//필드부
	private int freeCommNo;//	FREE_COMM_NO	NUMBER
	private String freeCommContent;//	FREE_COMM_CONTENT	VARCHAR2(3000 BYTE)
	private Date freeCommDate;//	FREE_DATE	DATE
	private String freeCommStatus;//	FREE_COMM_STATUS	VARCHAR2(1 BYTE)
	private int freeNo;//	FREE_NO	NUMBER
	//게시글 번호
	private int userNo;//	USER_NO	NUMBER
	
	
	private String userNickName;
	//생성자부
	public FreeBoardComment() {}
	
	//전채매개변수 생성자
	
	public FreeBoardComment(int freeCommNo, String freeCommContent, Date freeCommDate, String freeCommStatus,
			int freeNo, int userNo, String userNickName) {
		super();
		this.freeCommNo = freeCommNo;
		this.freeCommContent = freeCommContent;
		this.freeCommDate = freeCommDate;
		this.freeCommStatus = freeCommStatus;
		this.freeNo = freeNo;
		this.userNo = userNo;
		this.userNickName = userNickName;
	}

	
	//메소드부
	public int getFreeCommNo() {
		return freeCommNo;
	}


	public void setFreeCommNo(int freeCommNo) {
		this.freeCommNo = freeCommNo;
	}

	public String getFreeCommContent() {
		return freeCommContent;
	}

	public void setFreeCommContent(String freeCommContent) {
		this.freeCommContent = freeCommContent;
	}

	public Date getFreeCommDate() {
		return freeCommDate;
	}

	public void setFreeCommDate(Date freeCommDate) {
		this.freeCommDate = freeCommDate;
	}

	public String getFreeCommStatus() {
		return freeCommStatus;
	}

	public void setFreeCommStatus(String freeCommStatus) {
		this.freeCommStatus = freeCommStatus;
	}

	public int getFreeNo() {
		return freeNo;
	}

	public void setFreeNo(int freeNo) {
		this.freeNo = freeNo;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	

	public String getUserNickName() {
		return userNickName;
	}

	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}

	//toString
	@Override
	public String toString() {
		return "FreeBoardComment [freeCommNo=" + freeCommNo + ", freeCommContent=" + freeCommContent + ", FreeCommDate="
				+ freeCommDate + ", freeCommStatus=" + freeCommStatus + ", freeNo=" + freeNo + ", userNo=" + userNo
				+ "]";
	}
	
	
	
	
}
