package com.fh.freeBoard.model.vo;

public class FreeBoardFile {

	
	//필드부
	
	private int freeFileNo;//	FREE_FILE_NO	NUMBER
	private String freeFileName;//	FREE_FILE_NAME	VARCHAR2(60 BYTE)
	private String freeFileRename;//	FREE_FILE_RENAME	VARCHAR2(60 BYTE)
	private String freeFilePath;//	FREE_FILE_PATH	VARCHAR2(100 BYTE)
	private String freeFileStatus;//	FREE_IMG_STATUS	VARCHAR2(1 BYTE)
    private int freeNo;	//	FREE_NO	NUMBER
    //자유게시판게시글번호
    
    private String fileImg; //파일 이미지 경로
    
    
    //생성자부
    
    //기본생성자
    public FreeBoardFile() {}
    
    //전체매개변수 생성자
	public FreeBoardFile(int freeFileNo, String freeFileName, String freeFileRename, String freeFilePath,
			String freeFileStatus, int freeNo) {
		super();
		this.freeFileNo = freeFileNo;
		this.freeFileName = freeFileName;
		this.freeFileRename = freeFileRename;
		this.freeFilePath = freeFilePath;
		this.freeFileStatus = freeFileStatus;
		this.freeNo = freeNo;
	}
	
	
	//메소드부
	
	public int getFreeFileNo() {
		return freeFileNo;
	}
	public void setFreeFileNo(int freeFileNo) {
		this.freeFileNo = freeFileNo;
	}
	public String getFreeFileName() {
		return freeFileName;
	}
	public void setFreeFileName(String freeFileName) {
		this.freeFileName = freeFileName;
	}
	public String getFreeFileRename() {
		return freeFileRename;
	}
	public void setFreeFileRename(String freeFileRename) {
		this.freeFileRename = freeFileRename;
	}
	public String getFreeFilePath() {
		return freeFilePath;
	}
	public void setFreeFilePath(String freeFilePath) {
		this.freeFilePath = freeFilePath;
	}
	public String getfreeFileStatus() {
		return freeFileStatus;
	}
	public void setfreeFileStatus(String freeFileStatus) {
		this.freeFileStatus = freeFileStatus;
	}
	public int getFreeNo() {
		return freeNo;
	}
	public void setFreeNo(int freeNo) {
		this.freeNo = freeNo;
	}
	
	
	//오버라이딩
	@Override
	public String toString() {
		return "FreeBoardFile [freeFileNo=" + freeFileNo + ", freeFileName=" + freeFileName + ", freeFileRename="
				+ freeFileRename + ", freeFilePath=" + freeFilePath + ", freeFileStatus=" + freeFileStatus + ", freeNo="
				+ freeNo + "]";
	}
    
	
    
    
    
}
