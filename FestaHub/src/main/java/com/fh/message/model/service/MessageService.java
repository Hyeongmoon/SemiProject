package com.fh.message.model.service;

import static com.fh.common.JDBCTemplate.close;
import static com.fh.common.JDBCTemplate.commit;
import static com.fh.common.JDBCTemplate.getConnection;
import static com.fh.common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.ArrayList;

import com.fh.accompanyBoard.model.dao.AccompanyDao;
import com.fh.accompanyBoard.model.vo.Accompany;
import com.fh.common.model.vo.PageInfo;
import com.fh.message.model.dao.MessageDao;
import com.fh.message.model.vo.Message;

public class MessageService {


	/**
	 * 받은쪽지 갯수 조회용 서비스메소드
	 * @param userNo 받는 사람 기준이될 로그인한 유저번호
	 * @return 받은쪽지 총 갯수
	 * 24.10.28 14시 윤홍문 작성
	 */
	public int selectReceivedMessageListCount(int userNo) {
		// 1) Connection 객체 생성
		Connection conn = getConnection();
		
		// 2) Connection 객체와 전달값을 넘기면서
		//    DAO 로 요청 후 결과 받기
		int listCount 
			= new MessageDao().selectReceivedMessageListCount(conn, userNo);
		
		// 3) DML 문의 경우 트랜잭션 처리
		// > select 문 이므로 패스
		
		// 4) Connection 객체 반납
		close(conn);
		
		// 5) Controller 로 결과 반환
		return listCount;
	}

	/**
	 * 받은 쪽지 목록조회용 서비스 메소드
	 * @param pi 페이징처리 변수들
	 * @param userNo 받은사람 유저번호
	 * @return 조회한 쪽지 내용들
	 * 24.10.28.14시 윤홍문 작성
	 */
	public ArrayList<Message> selecReceivedMessagetList(PageInfo pi, int userNo) {
		// 1) Connection 객체 생성
		Connection conn = getConnection();
		
		// 2) Connection 객체와 전달값을 넘기면서
		//    DAO 로 요청 후 결과 받기
		ArrayList<Message> list 
			= new MessageDao().selecReceivedMessagetList(conn, pi, userNo);
		
		// 3) DML 문의 경우 트랜잭션 처리
		// > select 문 이므로 패스
		
		// 4) Connection 객체 반납
		close(conn);
		
		// 5) Controller 로 결과 반환
		return list;
	}

	/**
	 * 보낸 쪽지 총 갯수를 구하는 서비스메소드
	 * @param userNo 보낸사람 유저번호
	 * @return 보낸 쪽지 총 갯수
	 * 24.10.28 16시 윤홍문 작성
	 */
	public int selectSentMessageListCount(int userNo) {
		// 1) Connection 객체 생성
		Connection conn = getConnection();
		
		// 2) Connection 객체와 전달값을 넘기면서
		//    DAO 로 요청 후 결과 받기
		int listCount 
			= new MessageDao().selectSentMessageListCount(conn, userNo);
		
		// 3) DML 문의 경우 트랜잭션 처리
		// > select 문 이므로 패스
		
		// 4) Connection 객체 반납
		close(conn);
		
		// 5) Controller 로 결과 반환
		return listCount;
	}

	/**
	 * 보낸 쪽지 리스트 조회용 서비스 메소드
	 * @param pi 페이징 처리할 변수들
	 * @param userNo 보낸사람 유저 넘버
	 * @return 보낸 쪽지 내용
	 * 24.10.28 16시 윤홍문 작성
	 */
	public ArrayList<Message> selecSentMessagetList(PageInfo pi, int userNo) {
		// 1) Connection 객체 생성
		Connection conn = getConnection();
		
		// 2) Connection 객체와 전달값을 넘기면서
		//    DAO 로 요청 후 결과 받기
		ArrayList<Message> list 
			= new MessageDao().selecSentMessagetList(conn, pi, userNo);
		
		// 3) DML 문의 경우 트랜잭션 처리
		// > select 문 이므로 패스
		
		// 4) Connection 객체 반납
		close(conn);
		
		// 5) Controller 로 결과 반환
		return list;
	}

	/**
	 * 쪽지 발송 시 받는 사람의 닉네임이 유효한지 체크하는 서비스용 메소드
	 * @param checkNickname 쪽지 작성자가 입력한 받는 사람의 닉네임
	 * @return 동일한 닉네임의 갯수
	 * 24.10.28 18시 윤홍문 작성
	 */
	public int nicknameCheck(String checkNickname) {
		// 1) Connection 객체 생성
		Connection conn = getConnection();
		
		// 2) Connection 객체와 전달값을 넘기면서
		//    DAO 로 요청 후 결과 받기
		int count = new MessageDao().nicknameCheck(conn, checkNickname);
		
		// 3) DML 문의 경우 트랜잭션 처리
		// > 패스
		
		// 4) Connection 객체 반납
		close(conn);
		
		// 5) Controller 로 결과 반환
		return count;
	}

	/**
	 * 쪽지 발신(DB 등록)용 서비스 메소드
	 * @param me
	 * @return 처리된 행의 갯수
	 * 24.10.29 13시 윤홍문 작성
	 */
	public int insertMessage(Message me) {
		// 1) Connection 객체 생성
		Connection conn = getConnection();
		
		// 2) Connection 객체와 전달값을 넘기면서
		//    DAO 로 요청 후 결과 받기
		int result 
			= new MessageDao().insertMessage(conn, me);
		
		// 3) DML 문의 경우 트랜잭션 처리
		if(result > 0) { // 성공 (커밋)
			
			commit(conn);
			
		} else { // 실패 (롤백)
			
			rollback(conn);
		}
		
		// 4) Connection 객체 반납
		close(conn);
		
		// 5) Controller 로 결과 반환
		return result;
	}

	/**
	 * 받은 쪽지 상세조회 서비스용 메소드
	 * @param msgNo 상세조회할 쪽지 번호
	 * @return 조회한 쪽지 내용
	 * 24.10.29 16시 윤홍문 작성
	 */
	public Message selectReceivedMessage(int msgNo) {
		// 1) Connection 객체 생성
		Connection conn = getConnection();
		
		// 2) Connection 객체와 전달값을 넘기면서
		//    DAO 로 요청 후 결과 받기
		Message msg = new MessageDao().selectReceivedMessage(conn, msgNo);
		
		// 3) DML 문의 경우 트랜잭션 처리
		// > 패스
		
		// 4) Connection 객체 반납
		close(conn);
		
		// 5) Controller 로 결과 반환
		return msg;
	}

	/**
	 * 보낸 쪽지 상세조회 서비스 메소드
	 * @param msgNo 상세조회할 쪽지 번호
	 * @return 조회한 쪽지 내용
	 * 24.10.29 17시 윤홍문 작성
	 */
	public Message selectSentMessage(int msgNo) {
		// 1) Connection 객체 생성
		Connection conn = getConnection();
		
		// 2) Connection 객체와 전달값을 넘기면서
		//    DAO 로 요청 후 결과 받기
		Message msg = new MessageDao().selectSentMessage(conn, msgNo);
		
		// 3) DML 문의 경우 트랜잭션 처리
		// > 패스
		
		// 4) Connection 객체 반납
		close(conn);
		
		// 5) Controller 로 결과 반환
		return msg;
	}

	/**
	 * 받는 쪽지 삭제 서비스 메소드
	 * @param msgNo 삭제할 쪽지 번호
	 * @return 처리된 행의 갯수
	 * 24.10.29 18시 윤홍문 작성
	 */
	public int deleteReceivedMessage(int msgNo) {
		// 1) Connection 객체 생성
		Connection conn = getConnection();
		
		// 2) Connection 객체와 전달값을 넘기면서
		//    DAO 로 요청 후 결과 받기
		int result 
			= new MessageDao().deleteReceivedMessage(conn, msgNo);
		
		// 3) DML 문의 경우 트랜잭션 처리
		if(result > 0) { // 성공 (커밋)
			commit(conn);
		} else { // 실패 (롤백)
			rollback(conn);
		}
		
		// 4) Connection 객체 반납
		close(conn);
		
		// 5) Controller 로 결과 반환
		return result;
	}

	/**
	 * 보낸 쪽지 삭제 서비스 메소드
	 * @param msgNo 삭제할 쪽지 번호
	 * @return 처리된 행의 갯수
	 * 24.10.29 18시 윤홍문 작성
	 */
	public int deleteSentMessage(int msgNo) {
		// 1) Connection 객체 생성
		Connection conn = getConnection();
		
		// 2) Connection 객체와 전달값을 넘기면서
		//    DAO 로 요청 후 결과 받기
		int result 
			= new MessageDao().deleteSentMessage(conn, msgNo);
		
		// 3) DML 문의 경우 트랜잭션 처리
		if(result > 0) { // 성공 (커밋)
			commit(conn);
		} else { // 실패 (롤백)
			rollback(conn);
		}
		
		// 4) Connection 객체 반납
		close(conn);
		
		// 5) Controller 로 결과 반환
		return result;
	}

	/**
	 * 받은 쪽지 이전글 정보 조회용 서비스 메소드
	 * @param msgNo 기준 쪽지 번호
	 * @return 이전 쪽지 정보
	 * 24.10.31 10시 윤홍문 작성
	 */
	public Message selectPreviousReceivedMessage(int msgNo, int userNo) {
		// 1) Connection 객체 생성
		Connection conn = getConnection();
		
		// 2) Connection 객체와 전달값을 넘기면서
		//    DAO 로 요청 후 결과 받기
		Message prevMsg = 
			new MessageDao().selectPreviousReceivedMessage(conn, msgNo, userNo);
		
		// 3) DML 문의 경우 트랜잭션 처리
		// > 패스
		
		// 4) Connection 객체 반납
		close(conn);
		
		// 5) Controller 로 결과 반환
		return prevMsg;
	}

	/**
	 * 받은 쪽지 다음글 정보 조회용 서비스 메소드
	 * @param msgNo 기준 쪽지 번호
	 * @return 다음 쪽지 정보
	 * 24.10.31 10시 윤홍문 작성
	 */
	public Message selectNextReceivedMessage(int msgNo, int userNo) {
		// 1) Connection 객체 생성
		Connection conn = getConnection();
		
		// 2) Connection 객체와 전달값을 넘기면서
		//    DAO 로 요청 후 결과 받기
		Message nextMsg = 
			new MessageDao().selectNextReceivedMessage(conn, msgNo, userNo);
		
		// 3) DML 문의 경우 트랜잭션 처리
		// > 패스
		
		// 4) Connection 객체 반납
		close(conn);
		
		// 5) Controller 로 결과 반환
		return nextMsg;
	}

	/**
	 * 이전 보낸메세지 조회용 서비스메소드
	 * @param msgNo 이전글 기준 메세지번호
	 * @param userNo 로그인햔유저번호 = 보낸유저번호
	 * @return 이전메세지 정보
	 * 24.10.31 11시 윤홍문 작성
	 */
	public Message selectPreviousSentMessage(int msgNo, int userNo) {
		// 1) Connection 객체 생성
		Connection conn = getConnection();
		
		// 2) Connection 객체와 전달값을 넘기면서
		//    DAO 로 요청 후 결과 받기
		Message prevMsg = 
			new MessageDao().selectPreviousSentMessage(conn, msgNo, userNo);
		
		// 3) DML 문의 경우 트랜잭션 처리
		// > 패스
		
		// 4) Connection 객체 반납
		close(conn);
		
		// 5) Controller 로 결과 반환
		return prevMsg;
	}

	/**
	 * 다음 보낸메세지 조회용 서비스메소드
	 * @param msgNo 다음글 기준 메세지번호
	 * @param userNo 로그인햔유저번호 = 보낸유저번호
	 * @return 다음메세지 정보
	 * 24.10.31 11시 윤홍문 작성
	 */
	public Message selectNextSentMessage(int msgNo, int userNo) {
		// 1) Connection 객체 생성
		Connection conn = getConnection();
		
		// 2) Connection 객체와 전달값을 넘기면서
		//    DAO 로 요청 후 결과 받기
		Message nextMsg = 
			new MessageDao().selectNextSentMessage(conn, msgNo, userNo);
		
		// 3) DML 문의 경우 트랜잭션 처리
		// > 패스
		
		// 4) Connection 객체 반납
		close(conn);
		
		// 5) Controller 로 결과 반환
		return nextMsg;
	}



}
