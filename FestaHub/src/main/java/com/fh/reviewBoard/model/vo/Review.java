package com.fh.reviewBoard.model.vo;

import java.sql.Date;

public class Review {
	
	//필드부
	public int rvNo;
	public String rvTitle;
	public String rvContent;
	public int rvRating;
	public Date rvDate;
	public int rvCount;
	public String rvStatus;
	public int userNo;
	
	public String userNickname;
	public int reviewLike;
	public int rvCommentNo;
	
	public int commentCount;
	public int likeCount;
	
	
	//생성자부
	
	public Review() {}

    public Review(int rvNo, String rvTitle, String rvContent, int rvRating, Date rvDate, int rvCount, String rvStatus,
			int userNo, String userNickname, int reviewLike, int rvCommentNo, int commentCount, int likeCount) {
		super();
		this.rvNo = rvNo;
		this.rvTitle = rvTitle;
		this.rvContent = rvContent;
		this.rvRating = rvRating;
		this.rvDate = rvDate;
		this.rvCount = rvCount;
		this.rvStatus = rvStatus;
		this.userNo = userNo;
		this.userNickname = userNickname;
		this.reviewLike = reviewLike;
		this.rvCommentNo = rvCommentNo;
		this.commentCount = commentCount;
		this.likeCount = likeCount;
	}

    //메소드부
    public int getRvNo() {
    	return rvNo;
    }

    public void setRvNo(int rvNo) {
    	this.rvNo = rvNo;
    }

    public String getRvTitle() {
    	return rvTitle;
    }

    public void setRvTitle(String rvTitle) {
    	this.rvTitle = rvTitle;
    }

    public String getRvContent() {
    	return rvContent;
    }

    public void setRvContent(String rvContent) {
    	this.rvContent = rvContent;
    }

    public int getRvRating() {
    	return rvRating;
    }

    public void setRvRating(int rvRating) {
    	this.rvRating = rvRating;
    }

    public Date getRvDate() {
    	return rvDate;
    }

    public void setRvDate(Date rvDate) {
    	this.rvDate = rvDate;
    }

    public int getRvCount() {
    	return rvCount;
    }

    public void setRvCount(int rvCount) {
    	this.rvCount = rvCount;
    }

    public String getRvStatus() {
    	return rvStatus;
    }

    public void setRvStatus(String rvStatus) {
    	this.rvStatus = rvStatus;
    }

    public int getUserNo() {
    	return userNo;
    }

    public void setUserNo(int userNo) {
    	this.userNo = userNo;
    }

    public String getUserNickname() {
    	return userNickname;
    }

    public void setUserNickname(String userNickname) {
    	this.userNickname = userNickname;
    }

    public int getReviewLike() {
    	return reviewLike;
    }

    public void setReviewLike(int reviewLike) {
    	this.reviewLike = reviewLike;
    }

    public int getRvCommentNo() {
    	return rvCommentNo;
    }

    public void setRvCommentNo(int rvCommentNo) {
    	this.rvCommentNo = rvCommentNo;
    }

    public int getCommentCount() {
    	return commentCount;
    }

    public void setCommentCount(int commentCount) {
    	this.commentCount = commentCount;
    }

    public int getLikeCount() {
    	return likeCount;
    }

    public void setLikeCount(int likeCount) {
    	this.likeCount = likeCount;
    }

	@Override
	public String toString() {
		return "Review [rvNo=" + rvNo + ", rvTitle=" + rvTitle + ", rvContent=" + rvContent + ", rvRating=" + rvRating
				+ ", rvDate=" + rvDate + ", rvCount=" + rvCount + ", rvStatus=" + rvStatus + ", userNo=" + userNo
				+ ", userNickname=" + userNickname + ", reviewLike=" + reviewLike + ", rvCommentNo=" + rvCommentNo
				+ ", commentCount=" + commentCount + ", likeCount=" + likeCount + "]";
	}
    
}



	
	

	





	
	


