package com.fh.reviewBoard.model.vo;

public class ReviewFile {
	
	//필드부
	public int rvFileNo;
	
	public String rvFileName;
	
	public String rvFileRename;
	
	public String rvFilePath;
	
	public String rvFileStatus;
	
	public int rvNo;
	
	//생성자부
	public ReviewFile () {}

	public ReviewFile(int rvFileNo, String rvFileName, String rvFileRename, String rvFilePath, String rvFileStatus,
			int rvNo) {
		super();
		this.rvFileNo = rvFileNo;
		this.rvFileName = rvFileName;
		this.rvFileRename = rvFileRename;
		this.rvFilePath = rvFilePath;
		this.rvFileStatus = rvFileStatus;
		this.rvNo = rvNo;
	}

	//메소드부
	
	public int getRvFileNo() {
		return rvFileNo;
	}

	public void setRvFileNo(int rvFileNo) {
		this.rvFileNo = rvFileNo;
	}

	public String getRvFileName() {
		return rvFileName;
	}

	public void setRvFileName(String rvFileName) {
		this.rvFileName = rvFileName;
	}

	public String getRvFileRename() {
		return rvFileRename;
	}

	public void setRvFileRename(String rvFileRename) {
		this.rvFileRename = rvFileRename;
	}

	public String getRvFilePath() {
		return rvFilePath;
	}

	public void setRvFilePath(String rvFilePath) {
		this.rvFilePath = rvFilePath;
	}

	public String getRvFileStatus() {
		return rvFileStatus;
	}

	public void setRvFileStatus(String rvFileStatus) {
		this.rvFileStatus = rvFileStatus;
	}

	public int getRvNo() {
		return rvNo;
	}

	public void setRvNo(int rvNo) {
		this.rvNo = rvNo;
	}

	@Override
	public String toString() {
		return "ReviewFile [rvFileNo=" + rvFileNo + ", rvFileName=" + rvFileName + ", rvFileRename=" + rvFileRename
				+ ", rvFilePath=" + rvFilePath + ", rvFileStatus=" + rvFileStatus + ", rvNo=" + rvNo + "]";
	}
	
	
	

}
