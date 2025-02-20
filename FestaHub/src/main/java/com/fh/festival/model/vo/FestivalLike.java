package com.fh.festival.model.vo;

public class FestivalLike {
	
    private int fesNo;
    private int userNo;
    private boolean isLiked;
    private int likeCount;
    
    public FestivalLike() { }

	public FestivalLike(int fesNo, int userNo, boolean isLiked, int likeCount) {
		super();
		this.fesNo = fesNo;
		this.userNo = userNo;
		this.isLiked = isLiked;
		this.likeCount = likeCount;
	}

	public int getFesNo() {
		return fesNo;
	}

	public void setFesNo(int fesNo) {
		this.fesNo = fesNo;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public boolean isLiked() {
		return isLiked;
	}

	public void setLiked(boolean isLiked) {
		this.isLiked = isLiked;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	@Override
	public String toString() {
		return "FestivalLike [fesNo=" + fesNo + ", userNo=" + userNo + ", isLiked=" + isLiked + ", likeCount="
				+ likeCount + "]";
	}
    
}
