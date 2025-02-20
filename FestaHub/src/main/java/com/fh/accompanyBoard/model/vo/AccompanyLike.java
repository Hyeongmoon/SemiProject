package com.fh.accompanyBoard.model.vo;

public class AccompanyLike {

    private int accomNo;
    private int userNo;
    private boolean isLiked;
    private int likeCount;
    
    
	public AccompanyLike() {
		super();
	}
	public AccompanyLike(int accomNo, int userNo, boolean isLiked, int likeCount) {
		super();
		this.accomNo = accomNo;
		this.userNo = userNo;
		this.isLiked = isLiked;
		this.likeCount = likeCount;
	}
	
	
	public int getAccomNo() {
		return accomNo;
	}
	public void setAccomNo(int accomNo) {
		this.accomNo = accomNo;
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
		return "AccompanyLike [AccomNo=" + accomNo + ", userNo=" + userNo + ", isLiked=" + isLiked + ", likeCount="
				+ likeCount + "]";
	}
    
    
    
    
}
