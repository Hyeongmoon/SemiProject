package com.fh.accompanyBoard.model.vo;

import java.sql.Date;

public class Accompany {
	
	private int accomNo;		//	ACCOM_NO	NUMBER
	private String accomTitle;	//	ACCOM_TITLE	VARCHAR2(150 BYTE)
	private String accomContent;//	ACCOM_CONTENT	VARCHAR2(3900 BYTE)
	private Date accomDate;		//	ACCOM_DATE	DATE
	private int accomCount;		//	ACCOM_COUNT	NUMBER
	private String accomStatus;	//	ACCOM_STATUS	VARCHAR2(1 BYTE)
	private String userNo;		//	USER_NO	NUMBER
	
	private String userNickname; //	USER_NICKNAME
	private int likeCount;		//	LIKE_COUNT 
	// 목록조회때, accompany 테이블에 없는
	// 좋아요 수와, 유저닉네임을 가져오기위한 필드 생성
	
	
	public Accompany() {}
	
	public Accompany(int accomNo, String accomTitle, String accomContent, Date accomDate, int accomCount,
			String accomStatus, String userNo) {
		super();
		this.accomNo = accomNo;
		this.accomTitle = accomTitle;
		this.accomContent = accomContent;
		this.accomDate = accomDate;
		this.accomCount = accomCount;
		this.accomStatus = accomStatus;
		this.userNo = userNo;
	}

	// 게시글 조회용 생성자
	public Accompany(int accomNo, String accomTitle, Date accomDate, int accomCount, String userNickname,
			int likeCount) {
		super();
		this.accomNo = accomNo;
		this.accomTitle = accomTitle;
		this.accomDate = accomDate;
		this.accomCount = accomCount;
		this.userNickname = userNickname;
		this.likeCount = likeCount;
	}
	
	
	

	public int getAccomNo() {
		return accomNo;
	}
	public void setAccomNo(int accomNo) {
		this.accomNo = accomNo;
	}
	public String getAccomTitle() {
		return accomTitle;
	}
	public void setAccomTitle(String accomTitle) {
		this.accomTitle = accomTitle;
	}
	public String getAccomContent() {
		return accomContent;
	}
	public void setAccomContent(String accomContent) {
		this.accomContent = accomContent;
	}
	public Date getAccomDate() {
		return accomDate;
	}
	public void setAccomDate(Date accomDate) {
		this.accomDate = accomDate;
	}
	public int getAccomCount() {
		return accomCount;
	}
	public void setAccomCount(int accomCount) {
		this.accomCount = accomCount;
	}
	public String getAccomStatus() {
		return accomStatus;
	}
	public void setAccomStatus(String accomStatus) {
		this.accomStatus = accomStatus;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	
	
	public String getUserNickname() {
		return userNickname;
	}

	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	
	@Override
	public String toString() {
		return "Accompany [accomNo=" + accomNo + ", accomTitle=" + accomTitle + ", accomContent=" + accomContent
				+ ", accomDate=" + accomDate + ", accomCount=" + accomCount + ", accomStatus=" + accomStatus
				+ ", userNo=" + userNo + ", userNickname=" + userNickname + ", likeCount=" + likeCount + "]";
	}
	
	
	
	
	
	
	
	
	

}
