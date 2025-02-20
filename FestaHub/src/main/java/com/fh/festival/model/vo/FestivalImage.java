package com.fh.festival.model.vo;

public class FestivalImage {
	
	// 필드부
	private int fesImgNo;	//	FES_IMG_NO	NUMBER
	private String fesImgName;	//	FES_IMG_NAME	VARCHAR2(60 BYTE)
	private String fesImgRename;	//	FES_IMG_RENAME	VARCHAR2(60 BYTE)
	private String fesImgPath;	//	FES_IMG_PATH	VARCHAR2(100 BYTE)
	private String fesImgThumb;	//	FES_IMG_THUMB	VARCHAR2(1 BYTE)
	private String fesImgStatus;	//	FES_IMG_STATUS	VARCHAR2(1 BYTE)
	private int fesNo;	//	FES_NO	NUMBER
	
	// 생성자부
	public FestivalImage() { }

	public FestivalImage(int fesImgNo, String fesImgName, String fesImgRename, String fesImgPath, String fesImgThumb,
			String fesImgStatus, int fesNo) {
		super();
		this.fesImgNo = fesImgNo;
		this.fesImgName = fesImgName;
		this.fesImgRename = fesImgRename;
		this.fesImgPath = fesImgPath;
		this.fesImgThumb = fesImgThumb;
		this.fesImgStatus = fesImgStatus;
		this.fesNo = fesNo;
	}

	// 메소드부
	public int getFesImgNo() {
		return fesImgNo;
	}

	public void setFesImgNo(int fesImgNo) {
		this.fesImgNo = fesImgNo;
	}

	public String getFesImgName() {
		return fesImgName;
	}

	public void setFesImgName(String fesImgName) {
		this.fesImgName = fesImgName;
	}

	public String getFesImgRename() {
		return fesImgRename;
	}

	public void setFesImgRename(String fesImgRename) {
		this.fesImgRename = fesImgRename;
	}

	public String getFesImgPath() {
		return fesImgPath;
	}

	public void setFesImgPath(String fesImgPath) {
		this.fesImgPath = fesImgPath;
	}

	public String getFesImgThumb() {
		return fesImgThumb;
	}

	public void setFesImgThumb(String fesImgThumb) {
		this.fesImgThumb = fesImgThumb;
	}

	public String getFesImgStatus() {
		return fesImgStatus;
	}

	public void setFesImgStatus(String fesImgStatus) {
		this.fesImgStatus = fesImgStatus;
	}

	public int getFesNo() {
		return fesNo;
	}

	public void setFesNo(int fesNo) {
		this.fesNo = fesNo;
	}

	@Override
	public String toString() {
		return "FestivalImage [fesImgNo=" + fesImgNo + ", fesImgName=" + fesImgName + ", fesImgRename=" + fesImgRename
				+ ", fesImgPath=" + fesImgPath + ", fesImgThumb=" + fesImgThumb + ", fesImgStatus=" + fesImgStatus
				+ ", fesNo=" + fesNo + "]";
	}
	
	

}
