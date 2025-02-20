package com.fh.festival.model.vo;

import java.sql.Timestamp;

public class Festival {
	
	// 필드부
	private int fesNo; 			//	FES_NO	NUMBER
	private String fesTitle; 	//	FES_TITLE	VARCHAR2(150 BYTE)
	private String fesContent;  //	FES_CONTENT	VARCHAR2(3900 BYTE)
	private Timestamp fesDay; 		//	FES_DAY	DATE
	private String fesAddress;  //	FES_ADDRESS	VARCHAR2(300 BYTE)
	private Timestamp fesDate; 		//	FES_DATE	DATE
	private int fesCount; 		//	FES_COUNT	NUMBER
	private String fesStatus;	//	FES_STATUS	VARCHAR2(1 BYTE)
	// 작성자 닉네임
	private String fesWriter; 	//	USER_NO	NUMBER => USER_NICKNAME
	
	// 작성자ID와 접속유저ID 매칭용 필드 
	private String writerId;	//	USER_NO NUMBER => USER_ID
	
	// 썸네일 목록 조회용 필드
	private String titleImg;	// "resources/fesimg_upfiles/
	
    private int commCount; // 댓글 수 필드 추가
    
    private int likeCount; // 게시글의 좋아요 수
    private boolean isLiked; // 사용자가 좋아요를 눌렀는지 여부
	

	// 생성자부
	public Festival() { }
	
	// 전체필드 생성자
	public Festival(int fesNo, String fesTitle, String fesContent, Timestamp fesDay, String fesAddress, Timestamp fesDate,
			int fesCount, String fesStatus, String fesWriter, String writerId) {
		super();
		this.fesNo = fesNo;
		this.fesTitle = fesTitle;
		this.fesContent = fesContent;
		this.fesDay = fesDay;
		this.fesAddress = fesAddress;
		this.fesDate = fesDate;
		this.fesCount = fesCount;
		this.fesStatus = fesStatus;
		this.fesWriter = fesWriter;
		this.writerId = writerId;
	}

	// 목록조회 생성자 (미사용)
	public Festival(int fesNo, String fesTitle, Timestamp fesDay, String fesAddress, Timestamp fesDate, int fesCount,
			String fesWriter, String titleImg) {
		super();
		this.fesNo = fesNo;
		this.fesTitle = fesTitle;
		this.fesDay = fesDay;
		this.fesAddress = fesAddress;
		this.fesDate = fesDate;
		this.fesCount = fesCount;
		this.fesWriter = fesWriter;
		this.titleImg = titleImg;
	}
	
	// 메소드부
	public int getFesNo() {
		return fesNo;
	}

	public void setFesNo(int fesNo) {
		this.fesNo = fesNo;
	}

	public String getFesTitle() {
		return fesTitle;
	}

	public void setFesTitle(String fesTitle) {
		this.fesTitle = fesTitle;
	}

	public String getFesContent() {
		return fesContent;
	}

	public void setFesContent(String fesContent) {
		this.fesContent = fesContent;
	}

	public Timestamp getFesDay() {
		return fesDay;
	}

	public void setFesDay(Timestamp fesDay) {
		this.fesDay = fesDay;
	}

	public String getFesAddress() {
		return fesAddress;
	}

	public void setFesAddress(String fesAddress) {
		this.fesAddress = fesAddress;
	}

	public Timestamp getFesDate() {
		return fesDate;
	}

	public void setFesDate(Timestamp fesDate) {
		this.fesDate = fesDate;
	}

	public int getFesCount() {
		return fesCount;
	}

	public void setFesCount(int fesCount) {
		this.fesCount = fesCount;
	}

	public String getFesStatus() {
		return fesStatus;
	}

	public void setFesStatus(String fesStatus) {
		this.fesStatus = fesStatus;
	}

	public String getFesWriter() {
		return fesWriter;
	}

	public void setFesWriter(String fesWriter) {
		this.fesWriter = fesWriter;
	}
	
	public String getWriterId() {
		return writerId;
	}

	public void setWriterId(String writerId) {
		this.writerId = writerId;
	}

	public String getTitleImg() {
		return titleImg;
	}

	public void setTitleImg(String titleImg) {
		this.titleImg = titleImg;
	}
	
	public int getCommCount() {
		return commCount;
	}

	public void setCommCount(int commCount) {
		this.commCount = commCount;
	}
	

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public boolean isLiked() {
		return isLiked;
	}

	public void setLiked(boolean isLiked) {
		this.isLiked = isLiked;
	}
	
	@Override
	public String toString() {
		return "Festival [fesNo=" + fesNo + ", fesTitle=" + fesTitle + ", fesContent=" + fesContent + ", fesDay="
				+ fesDay + ", fesAddress=" + fesAddress + ", fesDate=" + fesDate + ", fesCount=" + fesCount
				+ ", fesStatus=" + fesStatus + ", fesWriter=" + fesWriter + ", writerId=" + writerId + "]";
	}
	
	
}