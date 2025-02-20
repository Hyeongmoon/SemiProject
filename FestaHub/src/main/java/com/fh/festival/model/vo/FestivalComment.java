package com.fh.festival.model.vo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class FestivalComment {
	
	// 필드부
	private int fesCommNo;			//	FES_COMM_NO	NUMBER
	private String fesCommContent;	//	FES_COMM_CONTENT	VARCHAR2(3000 BYTE)
	private String fesCommDate;	//	FES_COMM_DATE	DATE
	private String fesCommStatus;	//	FES_COMM_STATUS	VARCHAR2(1 BYTE)
	private int fesNo;			 	//	FES_NO	NUMBER
	private String fesCommWriter;	//	USER_NO	NUMBER => USER_NICKNAME
	private String CommWriterId;		//	USER_NO	NUMBER => USER_ID
	
	// 생성자부
	public FestivalComment() { }

	public FestivalComment(int fesCommNo, String fesCommContent, Timestamp fesCommDate, String fesCommStatus, int fesNo,
			String fesCommWriter, String commWriterId) {
		super();
		this.fesCommNo = fesCommNo;
		this.fesCommContent = fesCommContent;
		this.fesCommDate = formatTs(fesCommDate);
		this.fesCommStatus = fesCommStatus;
		this.fesNo = fesNo;
		this.fesCommWriter = fesCommWriter;
		this.CommWriterId = commWriterId;
	}
	
    private String formatTs(Timestamp timestamp) {
        SimpleDateFormat ymdhm = new SimpleDateFormat("yyyy.MM.dd. HH:mm");
        return ymdhm.format(timestamp);
    }

	public int getFesCommNo() {
		return fesCommNo;
	}

	public void setFesCommNo(int fesCommNo) {
		this.fesCommNo = fesCommNo;
	}

	public String getFesCommContent() {
		return fesCommContent;
	}

	public void setFesCommContent(String fesCommContent) {
		this.fesCommContent = fesCommContent;
	}

	public String getFesCommDate() {
		return fesCommDate;
	}

	public void setFesCommDate(Timestamp fesCommDate) {
		SimpleDateFormat ymdhm = new SimpleDateFormat("yyyy.MM.dd. HH:mm");
		this.fesCommDate = ymdhm.format(fesCommDate);
	}

	public String getFesCommStatus() {
		return fesCommStatus;
	}

	public void setFesCommStatus(String fesCommStatus) {
		this.fesCommStatus = fesCommStatus;
	}

	public int getFesNo() {
		return fesNo;
	}

	public void setFesNo(int fesNo) {
		this.fesNo = fesNo;
	}

	public String getFesCommWriter() {
		return fesCommWriter;
	}

	public void setFesCommWriter(String fesCommWriter) {
		this.fesCommWriter = fesCommWriter;
	}

	public String getCommWriterId() {
		return CommWriterId;
	}

	public void setCommWriterId(String commWriterId) {
		CommWriterId = commWriterId;
	}

	@Override
	public String toString() {
		return "FestivalComment [fesCommNo=" + fesCommNo + ", fesCommContent=" + fesCommContent + ", fesCommDate="
				+ fesCommDate + ", fesCommStatus=" + fesCommStatus + ", fesNo=" + fesNo + ", fesCommWriter="
				+ fesCommWriter + ", CommWriterId=" + CommWriterId + "]";
	}

}
