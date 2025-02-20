package com.fh.notice.model.vo;

import java.sql.Date;

public class Notice {
	
	//필드부
	private int noticeNo;			//	NOTICE_NO	NUMBER
	private String noticeTitle;		//	NOTICE_TITLE	VARCHAR2(150 BYTE)
	private String noticeContent;	//	NOTICE_CONTENT	VARCHAR2(3900 BYTE)
	private int noticeCount;		//	NOTICE_COUNT	NUMBER
	private Date noticeDate;		//	NOTICE_DATE	DATE
	private String noticeStatus;			//	NOTICE_STATUS	VARCHAR2(1 BYTE)
	
	// 생성자부
	public Notice() {}
	public Notice(int noticeNo, String noticeTitle, String noticeContent, 
				  int noticeCount, Date noticeDate, String noticeStatus) {
		super();
		this.noticeNo = noticeNo;
		this.noticeTitle = noticeTitle;
		this.noticeContent = noticeContent;
		this.noticeCount = noticeCount;
		this.noticeDate = noticeDate;
		this.noticeStatus = noticeStatus;
	}
	
	// 공지사항 목록 조회용 생성자 (매개변수 4개 짜리)
	public Notice(int noticeNo, String noticeTitle, 
				  int noticeCount, Date noticeDate) {
		super();
		this.noticeNo = noticeNo;
		this.noticeTitle = noticeTitle;
		this.noticeCount = noticeCount;
		this.noticeDate = noticeDate;
	}
	
	
	// 메소드부
	public int getNoticeNo() {
		return noticeNo;
	}
	public void setNoticeNo(int noticeNo) {
		this.noticeNo = noticeNo;
	}
	public String getNoticeTitle() {
		return noticeTitle;
	}
	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}
	public String getNoticeContent() {
		return noticeContent;
	}
	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}
	public int getNoticeCount() {
		return noticeCount;
	}
	public void setNoticeCount(int noticeCount) {
		this.noticeCount = noticeCount;
	}
	public Date getNoticeDate() {
		return noticeDate;
	}
	public void setNoticeDate(Date noticeDate) {
		this.noticeDate = noticeDate;
	}
	public String getNoticeStatus() {
		return noticeStatus;
	}
	public void setNoticeStatus(String noticeStatus) {
		this.noticeStatus = noticeStatus;
	}
	
	
	@Override
	public String toString() {
		return "Notice [noticeNo=" + noticeNo + ", noticeTitle=" + noticeTitle + ", noticeContent=" + noticeContent
				+ ", noticeCount=" + noticeCount + ", noticeDate=" + noticeDate + ", noticeStatus=" + noticeStatus + "]";
	}
	
	
}
