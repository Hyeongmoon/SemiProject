package com.fh.freeBoard.model.vo;

import java.sql.Date;

/**
 * @author user1
 *
 */
public class FreeBoard {

	
	//필드부
	
	private int freeNo;//	FREE_NO	NUMBER
	private String freeTitle;//	FREE_TITLE	VARCHAR2(150 BYTE)
	private String freeContent;//	FREE_CONTENT	VARCHAR2(3900 BYTE)
	private Date freeDate;//	FREE_DATE	DATE
	private int freeCount;//	FREE_COUNT	NUMBER
	private String freeStatus;//	FREE_STATUS	VARCHAR2(1 BYTE)
	private int userNo;//	USER_NO	NUMBER  
	//userNo랑 user닉네임 둘다받기
	
	//추가
	private String userNickName;
	private int freeLikeCount;
	private int freeCommentCount;
	//글을 작성한 유저의 번호만
	//DB에는 없지만, 필요할것같아서 추가
	private int fileCount;
	//첨부파일수
	
	
	//생성자부

	public int getFileCount() {
		return fileCount;
	}

	public void setFileCount(int fileCount) {
		this.fileCount = fileCount;
	}

	//기본
	public FreeBoard() {}

	//매개변수생성자 (추가된거 없는)
	public FreeBoard(int freeNo, String freeTitle, String freeContent, Date freeDate, int freeCount, String freeStatus,
			int userNo) {
		super();
		this.freeNo = freeNo;
		this.freeTitle = freeTitle;
		this.freeContent = freeContent;
		this.freeDate = freeDate;
		this.freeCount = freeCount;
		this.freeStatus = freeStatus;
		this.userNo = userNo;
	}


	//메소드부
	public int getFreeNo() {
		return freeNo;
	}


	public void setFreeNo(int freeNo) {
		this.freeNo = freeNo;
	}


	public String getFreeTitle() {
		return freeTitle;
	}


	public void setFreeTitle(String freeTitle) {
		this.freeTitle = freeTitle;
	}


	public String getFreeContent() {
		return freeContent;
	}


	public void setFreeContent(String freeContent) {
		this.freeContent = freeContent;
	}


	public Date getFreeDate() {
		return freeDate;
	}


	public void setFreeDate(Date freeDate) {
		this.freeDate = freeDate;
	}


	public int getFreeCount() {
		return freeCount;
	}


	public void setFreeCount(int freeCount) {
		this.freeCount = freeCount;
	}


	public String getFreeStatus() {
		return freeStatus;
	}


	public void setFreeStatus(String freeStatus) {
		this.freeStatus = freeStatus;
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


	public int getFreeLikeCount() {
		return freeLikeCount;
	}


	public void setFreeLikeCount(int freeLikeCount) {
		this.freeLikeCount = freeLikeCount;
	}


	public int getFreeCommentCount() {
		return freeCommentCount;
	}


	public void setFreeCommentCount(int freeCommentCount) {
		this.freeCommentCount = freeCommentCount;
	}


	//toString
	@Override
	public String toString() {
		return "FreeBoard [freeNo=" + freeNo + ", freeTitle=" + freeTitle + ", freeContent=" + freeContent
				+ ", freeDate=" + freeDate + ", freeCount=" + freeCount + ", freeStatus=" + freeStatus + ", userNo="
				+ userNo + ", userNickName=" + userNickName + ", freeLikeCount=" + freeLikeCount + ", freeCommentCount="
				+ freeCommentCount + "]";
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
