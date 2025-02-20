package com.fh.user.model.vo;
import java.sql.Date;
public class User {

	// 필드부
	private int userNo;
	private String userId;
	private String userPwd;
	private String userNickname;
	private String userName;
	private Date birthDate;
	private String phone;
	private String email;
	private String address;
	private Date blackListReg;
	private String blackListRfr;
	private Date regDate;
	private Date updateDate;
	private String status;
	
	public User() { }

	// 기본 생성자(14자)
	public User(int userNo, String userId, String userPwd, String userNickname, String userName, Date birthDate,
			String phone, String email, String address, Date blackListReg, String blackListRfr, Date regDate,
			Date updateDate, String status) {
		super();
		this.userNo = userNo;
		this.userId = userId;
		this.userPwd = userPwd;
		this.userNickname = userNickname;
		this.userName = userName;
		this.birthDate = birthDate;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.blackListReg = blackListReg;
		this.blackListRfr = blackListRfr;
		this.regDate = regDate;
		this.updateDate = updateDate;
		this.status = status;
	}

	// 회원가입용 생성자(8자)
	public User(String userId, String userPwd, String userNickname, String userName, Date birthDate, String phone,
			String email, String address) {
		super();
		this.userId = userId;
		this.userPwd = userPwd;
		this.userNickname = userNickname;
		this.userName = userName;
		this.birthDate = birthDate;
		this.phone = phone;
		this.email = email;
		this.address = address;
	}

	// 회원정보 수정용 생성자(6개)
	public User(String userId, String userPwd, String userNickname, String userName, String phone, String email, String address) {
		super();
		this.userId = userId;
		this.userPwd = userPwd;
		this.userNickname = userNickname;
		this.userName = userName;
		this.phone = phone;
		this.email = email;
		this.address = address;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserNickname() {
		return userNickname;
	}

	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getBlackListReg() {
		return blackListReg;
	}

	public void setBlackListReg(Date blackListReg) {
		this.blackListReg = blackListReg;
	}

	public String getBlackListRfr() {
		return blackListRfr;
	}

	public void setBlackListRfr(String blackListRfr) {
		this.blackListRfr = blackListRfr;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "User [userNo=" + userNo + ", userId=" + userId + ", userPwd=" + userPwd + ", userNickname="
				+ userNickname + ", userName=" + userName + ", birthDate=" + birthDate + ", phone=" + phone + ", email="
				+ email + ", address=" + address + ", blackListReg=" + blackListReg + ", blackListRfr=" + blackListRfr
				+ ", regDate=" + regDate + ", updateDate=" + updateDate + ", status=" + status + "]";
	}
	
	
}