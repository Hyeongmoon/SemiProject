package com.fh.message.model.dao;

import static com.fh.common.JDBCTemplate.close;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import com.fh.accompanyBoard.model.vo.Accompany;
import com.fh.common.JDBCTemplate;
import com.fh.common.model.vo.PageInfo;
import com.fh.message.model.vo.Message;
import com.fh.notice.model.dao.NoticeDao;

public class MessageDao {
	
	private Properties prop = new Properties();

	// 공통코드 - 쿼리문들을 키 + 밸류 세트로 불러오기
	public MessageDao() {
		
		String path = "/sql/message/message-mapper.xml";
		
		String fileName = NoticeDao.class.getResource(path).getPath();
		
		try {
			prop.loadFromXML(new FileInputStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 받은 쪽지 갯수 총 조회용 쿼리문 실행 메소드
	 * @param conn
	 * @param userNo 받은사람이 되는 로그인 유져넘버
	 * @return 받은 쪽지 총 갯수
	 * 24.10.28 14시 윤홍문 작성
	 */
	public int selectReceivedMessageListCount(Connection conn, int userNo) {
		// SELECT 문 => ResultSet (단일행)
		// 1) 필요한 변수들 먼저 셋팅
		int listCount = 0; // 총 게시글의 갯수를 담을 변수
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		// 실행할 SQL 문
		String sql = prop.getProperty("selectReceivedMessageListCount");
		
		try {
			
			// 2) PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// 3) 미완성된 SQL 문 완성시키기
			pstmt.setInt(1, userNo);
			
			// 4, 5) 쿼리문 실행 후 결과 받기
			// select : executeQuery 메소드
			rset = pstmt.executeQuery();
			
			// 6) rset 에 담겨있는 결과를 변수로 옮겨 담기
			if(rset.next()) {
				
				listCount = rset.getInt("COUNT"); // > 별칭으로 뽑기
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 7) 다 쓴 JDBC 용 자원 반납 (역순)
			close(rset);
			close(pstmt);
		}
		
		// 8) Service 로 결과 리턴
		return listCount;
	}

	/**
	 * 받은 쪽지 리스트 조회용 쿼리문 실행 메소드
	 * @param conn
	 * @param pi 페이징 처리 변수들
	 * @param userNo 받은 사람 유저번호
	 * @return 조회한 받은 쪽지 내용
	 * 24.10.28.14시 윤홍문 작성
	 */
	public ArrayList<Message> selecReceivedMessagetList(Connection conn, 
														PageInfo pi, 
														int userNo) {
		// SELECT 문 => ResultSet (여러행)
		// => ArrayList
		
		// 1) 필요한 변수들 먼저 셋팅
		ArrayList<Message> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		// 실행할 SQL 문
		String sql = prop.getProperty("selecReceivedMessagetList");
		
		try {
			
			// 2) PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// 3) 미완성된 SQL 문 완성시키기	
			int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
			int endRow = startRow + pi.getBoardLimit() - 1;
			
			pstmt.setInt(1, userNo);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			// 4, 5) 쿼리문 실행 후 결과 받기
			// select : executeQuery 메소드
			rset = pstmt.executeQuery();
			
			// 6) rset 으로부터 조회된 결과를 VO 로 옮겨담기
			while(rset.next()) {
				
				Message msg = new Message();
				
				msg.setMsgNo(rset.getInt("MSG_NO"));
				msg.setMsgContent(rset.getString("MSG_CONTENT"));
				msg.setMsgDate(rset.getDate("MSG_DATE"));
				msg.setUserNickname(rset.getString("USER_NICKNAME")); // 보낸 사람 유저 닉네임
				
				list.add(msg);
								
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 7) 다 쓴 JDBC 용 자원 반납 (역순)
			close(rset);
			close(pstmt);
			
		}
		
		// 8) Service 로 결과 리턴
		return list;
	}

	/**
	 * 보낸 쪽지 총 갯수 조회용 쿼리문 실행 메소드
	 * @param conn 
	 * @param userNo 보낸 사람 유저 넘버
	 * @return 보낸 쪽지 총 갯수
	 * 24.10.28 16시 윤홍문 작성
	 */
	public int selectSentMessageListCount(Connection conn, int userNo) {
		// SELECT 문 => ResultSet (단일행)
		// 1) 필요한 변수들 먼저 셋팅
		int listCount = 0; // 총 게시글의 갯수를 담을 변수
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		// 실행할 SQL 문
		String sql = prop.getProperty("selectSentMessageListCount");
		
		try {
			
			// 2) PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// 3) 미완성된 SQL 문 완성시키기
			pstmt.setInt(1, userNo);
			
			// 4, 5) 쿼리문 실행 후 결과 받기
			// select : executeQuery 메소드
			rset = pstmt.executeQuery();
			
			// 6) rset 에 담겨있는 결과를 변수로 옮겨 담기
			if(rset.next()) {
				
				listCount = rset.getInt("COUNT"); // > 별칭으로 뽑기
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 7) 다 쓴 JDBC 용 자원 반납 (역순)
			close(rset);
			close(pstmt);
		}
		
		// 8) Service 로 결과 리턴
		return listCount;
	}

	/**
	 * 보낸 쪽지 목록조회용 쿼리문 실행 메소드
	 * @param conn
	 * @param pi 페이징 처리할 변수들
	 * @param userNo 보낸사람 유저번호
	 * @return 보낸쪽지 리스트
	 * 24.10.28 16시 윤홍문 작성
	 */
	public ArrayList<Message> selecSentMessagetList(Connection conn, 
													PageInfo pi, 
													int userNo) {
		// SELECT 문 => ResultSet (여러행)
		// => ArrayList
		
		// 1) 필요한 변수들 먼저 셋팅
		ArrayList<Message> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		// 실행할 SQL 문
		String sql = prop.getProperty("selecSentMessagetList");
		
		try {
			
			// 2) PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// 3) 미완성된 SQL 문 완성시키기	
			int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
			int endRow = startRow + pi.getBoardLimit() - 1;
			
			pstmt.setInt(1, userNo);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			// 4, 5) 쿼리문 실행 후 결과 받기
			// select : executeQuery 메소드
			rset = pstmt.executeQuery();
			
			// 6) rset 으로부터 조회된 결과를 VO 로 옮겨담기
			while(rset.next()) {
				
				Message msg = new Message();
				
				msg.setMsgNo(rset.getInt("MSG_NO"));
				msg.setMsgContent(rset.getString("MSG_CONTENT"));
				msg.setMsgDate(rset.getDate("MSG_DATE"));
				msg.setUserNickname(rset.getString("USER_NICKNAME")); // 받는 사람 유저닉네임
				
				list.add(msg);
								
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 7) 다 쓴 JDBC 용 자원 반납 (역순)
			close(rset);
			close(pstmt);
			
		}
		
		// 8) Service 로 결과 리턴
		return list;
	}

	/**
	 * 쪽지 발송 시 받는 사람의 닉네임이 유효한지 체크용 쿼리문 실행 메소드
	 * @param conn
	 * @param checkNickname 쪽지 작성자가 입력한 받는 사람의 닉네임
	 * @return 동일한 닉네임의 갯수
	 * 24.10.28 18시 윤홍문 작성
	 */
	public int nicknameCheck(Connection conn, 
							 String checkNickname) {
		// SELECT 문 => ResultSet (단일행) => int
		
		// 1) 필요한 변수들 먼저 셋팅
		int count = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		// 실행할 SQL 문
		String sql = prop.getProperty("nicknameCheck");
		
		try {
			
			// 2) PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// 3) 미완성된 SQL 문 완성시키기
			pstmt.setString(1, checkNickname);
			
			// 4, 5) 쿼리문 실행 후 결과 받기
			// select : executeQuery 메소드
			rset = pstmt.executeQuery();
			
			// 6) rset 으로 부터 조회된 데이터를 뽑아서
			//    변수에 옮기기
			if(rset.next()) {
				
				count = rset.getInt("COUNT(*)");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 7) 다 쓴 JDBC 용 자원 반납 (역순)
			close(rset);
			close(pstmt);
		}
		
		// 8) Service 로 결과 반환
		return count;
	}

	/**
	 * 쪽지 발신(DB 등록)용 쿼리문 실행 메소드
	 * @param conn
	 * @param me 등록할 쪽지 내용
	 * @return 처리된 행의 갯수
	 * 24.10.29 13시 윤홍문 작성
	 */
	public int insertMessage(Connection conn, Message me) {
		// INSERT 문	=> int (처리된 행의 갯수)
		// 1) 필요한 변수들 먼저 셋팅
		int result = 0;
		PreparedStatement pstmt = null;
		
		// 실행할 SQL 문 
		String sql = prop.getProperty("insertMessage");
		
		try {
			
			// 2) PreparedStatemenet 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// 3) 미완성된 SQL 문 완성시키기
			pstmt.setString(1, me.getMsgContent());
			pstmt.setInt(2, me.getSenderNo());
			pstmt.setString(3, me.getUserNickname());
			
			// 4, 5) 쿼리문 실행 후 결과 받기
			// insert : executeUpdate 메소드
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 6) 다 쓴 JDBC 용 자원 반납 (역순)
			JDBCTemplate.close(pstmt);
		}
		
		// 7) Service 로 결과 반환
		return result;
	}

	/**
	 * 받은 쪽지 상세조회 쿼리문 실행 메소드
	 * @param conn
	 * @param msgNo 상세조회할 쪽지 번호
	 * @return 상세조회한 쪽지 내용
	 * 24.10.29 16시 윤홍문 작성
	 */
	public Message selectReceivedMessage(Connection conn, int msgNo) {
		// SELECT 문 => ResultSet (단일행)
		// => Message
				
		// 1) 필요한 변수들 먼저 셋팅
		Message msg = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		// 실행할 SQL 문 
		String sql = prop.getProperty("selectReceivedMessage");
		
		try {
			
			// 2) PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// 3) 미완성된 SQL 문 완성시키기
			pstmt.setInt(1, msgNo);
			
			// 4, 5) 쿼리문 실행 후 결과 받기
			// select : executeQuery 메소드
			rset = pstmt.executeQuery();
			
			// 6) rset 으로 부터 조회된 데이터를 뽑아서
			//    VO 로 옮겨담기
			if(rset.next()) {
				msg = new Message();
				
				msg.setMsgNo(rset.getInt("MSG_NO"));
				msg.setMsgContent(rset.getString("MSG_CONTENT"));
				msg.setMsgDate(rset.getDate("MSG_DATE"));
				msg.setReceiverNo(rset.getInt("RECEIVER_NO"));
				msg.setUserNickname(rset.getString("USER_NICKNAME"));
				// 로그인한 유저받은 쪽지를 보낸 유저의 닉네임

			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 7) 다 쓴 JDBC 용 자원 반납 (역순)
			close(rset);
			close(pstmt);
		}
		
		// 8) Service 로 결과 반환
		return msg;
	}

	/**
	 * 보낸 쪽지 상세조회용 쿼리문 실행 메소드
	 * @param conn
	 * @param msgNo 상세조회할 쪽지 번호
	 * @return 상세조회한 쪽지 내용
	 * 24.10.29 17시 윤홍문 작성
	 */
	public Message selectSentMessage(Connection conn, int msgNo) {
		// SELECT 문 => ResultSet (단일행)
		// => Message
				
		// 1) 필요한 변수들 먼저 셋팅
		Message msg = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		// 실행할 SQL 문 
		String sql = prop.getProperty("selectSentMessage");
		
		try {
			
			// 2) PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// 3) 미완성된 SQL 문 완성시키기
			pstmt.setInt(1, msgNo);
			
			// 4, 5) 쿼리문 실행 후 결과 받기
			// select : executeQuery 메소드
			rset = pstmt.executeQuery();
			
			// 6) rset 으로 부터 조회된 데이터를 뽑아서
			//    VO 로 옮겨담기
			if(rset.next()) {
				msg = new Message();
				
				msg.setMsgNo(rset.getInt("MSG_NO"));
				msg.setMsgContent(rset.getString("MSG_CONTENT"));
				msg.setMsgDate(rset.getDate("MSG_DATE"));
				msg.setSenderNo(rset.getInt("SENDER_NO"));
				msg.setUserNickname(rset.getString("USER_NICKNAME"));
				// 로그인한 유저가 보낸 받는 유저의 닉네임

			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 7) 다 쓴 JDBC 용 자원 반납 (역순)
			close(rset);
			close(pstmt);
		}
		
		// 8) Service 로 결과 반환
		return msg;
	}

	/**
	 * 받은 쪽지 삭제용 쿼리문 실행 메소드
	 * @param conn
	 * @param msgNo 삭제할 쪽지 번호
	 * @return 처리된 행의 갯수
	 * 24.10.29 18시 윤홍문 작성
	 */
	public int deleteReceivedMessage(Connection conn, int msgNo) {
		// UPDATE 문 => int (처리된 행의 갯수)
		// 1) 필요한 변수들 먼저 셋팅
		int result = 0;
		PreparedStatement pstmt = null;
		
		// 실행할 SQL 문
		String sql = prop.getProperty("deleteReceivedMessage");
		
		try {
			
			// 2) PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// 3) 미완성된 SQL 문 완성시키기
			pstmt.setInt(1, msgNo);
			
			// 4, 5) 쿼리문 실행 후 결과 받기
			// update : executeUpdate 메소드
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 6) 다 쓴 JDBC 용 자원 반납 (역순)
			JDBCTemplate.close(pstmt);
		}
		
		// 7) Service 로 결과 리턴
		return result;
	}

	/**
	 * 보낸 쪽지 삭제용 쿼리문 실행 메소드
	 * @param conn
	 * @param msgNo 삭제할 쪽지 번호
	 * @return 처리된 행의 갯수
	 * 24.10.29 18시 윤홍문 작성
	 */
	public int deleteSentMessage(Connection conn, int msgNo) {
		// UPDATE 문 => int (처리된 행의 갯수)
		// 1) 필요한 변수들 먼저 셋팅
		int result = 0;
		PreparedStatement pstmt = null;
		
		// 실행할 SQL 문
		String sql = prop.getProperty("deleteSentMessage");
		
		try {
			
			// 2) PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// 3) 미완성된 SQL 문 완성시키기
			pstmt.setInt(1, msgNo);
			
			// 4, 5) 쿼리문 실행 후 결과 받기
			// update : executeUpdate 메소드
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 6) 다 쓴 JDBC 용 자원 반납 (역순)
			JDBCTemplate.close(pstmt);
		}
		
		// 7) Service 로 결과 리턴
		return result;
	}

	/**
	 * 받은 쪽지 이전글 정보 조회 쿼리문 실행 메소드
	 * @param conn
	 * @param msgNo 기준 쪽지 번호
	 * @return 이전글 정보
	 * 24.10.31 10시 윤홍문 작성
	 */
	public Message selectPreviousReceivedMessage(Connection conn, int msgNo, int userNo) {
		// SELECT 문 => ResultSet (단일행)
		// => Message
				
		// 1) 필요한 변수들 먼저 셋팅
		Message prevMsg = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		// 실행할 SQL 문 
		String sql = prop.getProperty("selectPreviousReceivedMessage");
		
		try {
			
			// 2) PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// 3) 미완성된 SQL 문 완성시키기
			pstmt.setInt(1, msgNo);
			pstmt.setInt(2, userNo);
			
			// 4, 5) 쿼리문 실행 후 결과 받기
			// select : executeQuery 메소드
			rset = pstmt.executeQuery();
			
			// 6) rset 으로 부터 조회된 데이터를 뽑아서
			//    VO 로 옮겨담기
			if(rset.next()) {
				prevMsg = new Message();
				
				prevMsg.setMsgNo(rset.getInt("MSG_NO"));
				prevMsg.setMsgContent(rset.getString("MSG_CONTENT"));
				prevMsg.setMsgDate(rset.getDate("MSG_DATE"));
				prevMsg.setReceiverNo(rset.getInt("RECEIVER_NO"));
				prevMsg.setUserNickname(rset.getString("USER_NICKNAME"));
				// 로그인한 유저가 받은 보낸 유저의 닉네임

			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 7) 다 쓴 JDBC 용 자원 반납 (역순)
			close(rset);
			close(pstmt);
		}
		
		// 8) Service 로 결과 반환
		return prevMsg;
	}

	/**
	 * 받은 쪽지 다음글 정보 조회 쿼리문 실행 메소드
	 * @param conn
	 * @param msgNo 기준 쪽지 번호
	 * @return 다음글 정보
	 * 24.10.31 10시 윤홍문 작성
	 */
	public Message selectNextReceivedMessage(Connection conn, int msgNo, int userNo) {
		// SELECT 문 => ResultSet (단일행)
		// => Message
				
		// 1) 필요한 변수들 먼저 셋팅
		Message nextMsg = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		// 실행할 SQL 문 
		String sql = prop.getProperty("selectNextReceivedMessage");
		
		try {
			
			// 2) PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// 3) 미완성된 SQL 문 완성시키기
			pstmt.setInt(1, msgNo);
			pstmt.setInt(2, userNo);
			
			// 4, 5) 쿼리문 실행 후 결과 받기
			// select : executeQuery 메소드
			rset = pstmt.executeQuery();
			
			// 6) rset 으로 부터 조회된 데이터를 뽑아서
			//    VO 로 옮겨담기
			if(rset.next()) {
				nextMsg = new Message();
				
				nextMsg.setMsgNo(rset.getInt("MSG_NO"));
				nextMsg.setMsgContent(rset.getString("MSG_CONTENT"));
				nextMsg.setMsgDate(rset.getDate("MSG_DATE"));
				nextMsg.setReceiverNo(rset.getInt("RECEIVER_NO"));
				nextMsg.setUserNickname(rset.getString("USER_NICKNAME"));
				// 로그인한 유저가 받은 보낸 유저의 닉네임

			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 7) 다 쓴 JDBC 용 자원 반납 (역순)
			close(rset);
			close(pstmt);
		}
		
		// 8) Service 로 결과 반환
		return nextMsg;
	}

	/**
	 * 이전 보낸메세지 조회용 쿼리문 실행 메소드
	 * @param conn
	 * @param msgNo 이전글 기준 메세지번호
	 * @param userNo 로그인햔유저번호 = 보낸유저번호
	 * @return 이전메세지 정보
	 * 24.10.31 11시 윤홍문 작성
	 */
	public Message selectPreviousSentMessage(Connection conn, int msgNo, int userNo) {
		// SELECT 문 => ResultSet (단일행)
		// => Message
				
		// 1) 필요한 변수들 먼저 셋팅
		Message prevMsg = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		// 실행할 SQL 문 
		String sql = prop.getProperty("selectPreviousSentMessage");
		
		try {
			
			// 2) PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// 3) 미완성된 SQL 문 완성시키기
			pstmt.setInt(1, msgNo);
			pstmt.setInt(2, userNo);
			
			// 4, 5) 쿼리문 실행 후 결과 받기
			// select : executeQuery 메소드
			rset = pstmt.executeQuery();
			
			// 6) rset 으로 부터 조회된 데이터를 뽑아서
			//    VO 로 옮겨담기
			if(rset.next()) {
				prevMsg = new Message();
				
				prevMsg.setMsgNo(rset.getInt("MSG_NO"));
				prevMsg.setMsgContent(rset.getString("MSG_CONTENT"));
				prevMsg.setMsgDate(rset.getDate("MSG_DATE"));
				prevMsg.setSenderNo(rset.getInt("SENDER_NO"));
				prevMsg.setUserNickname(rset.getString("USER_NICKNAME"));
				// 로그인한 유저가 보낸 받는 유저의 닉네임

			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 7) 다 쓴 JDBC 용 자원 반납 (역순)
			close(rset);
			close(pstmt);
		}
		
		// 8) Service 로 결과 반환
		return prevMsg;
	}

	/**
	 * 다음 보낸메세지 조회용 쿼리문 실행 메소드
	 * @param conn
	 * @param msgNo 다음글 기준 메세지번호
	 * @param userNo 로그인햔유저번호 = 보낸유저번호
	 * @return 다음메세지 정보
	 * 24.10.31 11시 윤홍문 작성
	 */
	public Message selectNextSentMessage(Connection conn, int msgNo, int userNo) {
		// SELECT 문 => ResultSet (단일행)
		// => Message
				
		// 1) 필요한 변수들 먼저 셋팅
		Message nextMsg = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		// 실행할 SQL 문 
		String sql = prop.getProperty("selectNextSentMessage");
		
		try {
			
			// 2) PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// 3) 미완성된 SQL 문 완성시키기
			pstmt.setInt(1, msgNo);
			pstmt.setInt(2, userNo);
			
			// 4, 5) 쿼리문 실행 후 결과 받기
			// select : executeQuery 메소드
			rset = pstmt.executeQuery();
			
			// 6) rset 으로 부터 조회된 데이터를 뽑아서
			//    VO 로 옮겨담기
			if(rset.next()) {
				nextMsg = new Message();
				
				nextMsg.setMsgNo(rset.getInt("MSG_NO"));
				nextMsg.setMsgContent(rset.getString("MSG_CONTENT"));
				nextMsg.setMsgDate(rset.getDate("MSG_DATE"));
				nextMsg.setSenderNo(rset.getInt("SENDER_NO"));
				nextMsg.setUserNickname(rset.getString("USER_NICKNAME"));
				// 로그인한 유저가 보낸 받는 유저의 닉네임

			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 7) 다 쓴 JDBC 용 자원 반납 (역순)
			close(rset);
			close(pstmt);
		}
		
		// 8) Service 로 결과 반환
		return nextMsg;
	}

}
