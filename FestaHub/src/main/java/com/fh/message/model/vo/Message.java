package com.fh.message.model.vo;

import java.sql.Date;

public class Message {
	//필드부
	private int msgNo;					//	MSG_NO	NUMBER
	private String msgContent;			//	MSG_CONTENT	VARCHAR2(1500 BYTE)
	private Date msgDate;				//	MSG_DATE	DATE
	private int senderNo;				//	SENDER_NO	NUMBER
	private int receiverNo;				//	RECEIVER_NO	NUMBER
	private String senderMsgStatus;		//	SENDER_MSG_STATUS	VARCHAR2(1 BYTE)
	private String receiverMsgStatus;	//	RECEIVER_MSG_STATUS	VARCHAR2(1 BYTE)
	
	private String userNickname; // 보낸사람 및 받는사람 유저닉네임 처리할 필드값
	
	// 생성자부
	public Message() {}
	public Message(int msgNo, String msgContent, Date msgDate, int senderNo, int receiverNo, String senderMsgStatus,
			String receiverMsgStatus) {
		super();
		this.msgNo = msgNo;
		this.msgContent = msgContent;
		this.msgDate = msgDate;
		this.senderNo = senderNo;
		this.receiverNo = receiverNo;
		this.senderMsgStatus = senderMsgStatus;
		this.receiverMsgStatus = receiverMsgStatus;
	}
	
	
	//메소드부
	public int getMsgNo() {
		return msgNo;
	}
	public void setMsgNo(int msgNo) {
		this.msgNo = msgNo;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public Date getMsgDate() {
		return msgDate;
	}
	public void setMsgDate(Date msgDate) {
		this.msgDate = msgDate;
	}
	public int getSenderNo() {
		return senderNo;
	}
	public void setSenderNo(int senderNo) {
		this.senderNo = senderNo;
	}
	public int getReceiverNo() {
		return receiverNo;
	}
	public void setReceiverNo(int receiverNo) {
		this.receiverNo = receiverNo;
	}
	public String getSenderMsgStatus() {
		return senderMsgStatus;
	}
	public void setSenderMsgStatus(String senderMsgStatus) {
		this.senderMsgStatus = senderMsgStatus;
	}
	public String getReceiverMsgStatus() {
		return receiverMsgStatus;
	}
	public void setReceiverMsgStatus(String receiverMsgStatus) {
		this.receiverMsgStatus = receiverMsgStatus;
	}
	
	
	public String getUserNickname() {
		return userNickname;
	}
	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}
	
	@Override
	public String toString() {
		return "Message [msgNo=" + msgNo + ", msgContent=" + msgContent + ", msgDate=" + msgDate + ", senderNo="
				+ senderNo + ", receiverNo=" + receiverNo + "]";
	}
	

}
