package com.fh.notice.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import com.fh.common.JDBCTemplate;
import com.fh.common.model.vo.PageInfo;
import com.fh.notice.model.vo.Notice;

import static com.fh.common.JDBCTemplate.*;


public class NoticeDao {
	
	private Properties prop = new Properties();

	// 공통코드 - 쿼리문들을 키 + 밸류 세트로 불러오기
	public NoticeDao() {
		
		String path = "/sql/notice/notice-mapper.xml";
		
		String fileName = NoticeDao.class.getResource(path).getPath();
		
		try {
			prop.loadFromXML(new FileInputStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 총 게시글 갯수를 구하는 쿼리문용 실행 메소드
	 * @param conn
	 * @return 총 게시글 갯수
	 * 24/10/22 18시 윤홍문 작업
	 */
	public int selectListCount(Connection conn) {
		// SELECT 문 => ResultSet (단일행)
		// 1) 필요한 변수들 먼저 셋팅
		int listCount = 0; // 총 게시글의 갯수를 담을 변수
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		// 실행할 SQL 문
		String sql = prop.getProperty("selectListCount");
		
		try {
			
			// 2) PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// 3) 미완성된 SQL 문 완성시키기
			// > 완성되어있으므로 패스
			
			// 4, 5) 쿼리문 실행 후 결과 받기
			// select : executeQuery 메소드
			rset = pstmt.executeQuery();
			
			// 6) rset 에 담겨있는 결과를 변수로 옮겨 담기
			if(rset.next()) {
				
				listCount = rset.getInt("COUNT");
				// > 별칭으로 뽑기
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 7) 다 쓴 JDBC 용 자원 반납 (역순)
			close(rset);
			close(pstmt);
		}
		
		// 8) Service 로 결과 리턴
		return listCount; // 총 게시글 갯수
	}

	/**
	 * 게시글 목록 조회용 쿼리문 실행 메소드
	 * @param conn
	 * @param pi 구간별로 끊을 때 필요한 변수
	 * @return 조회된 일반 게시글들
	 * 24/10/23 10시 윤홍문 작업
	 */
	public ArrayList<Notice> selectList(Connection conn, 
										PageInfo pi) {
		// SELECT 문 => ResultSet (여러행)
		// => ArrayList
		
		// 1) 필요한 변수들 먼저 셋팅
		ArrayList<Notice> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		// 실행할 SQL 문
		String sql = prop.getProperty("selectList");
		
		try {
			
			// 2) PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// 3) 미완성된 SQL 문 완성시키기	
			int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
			int endRow = startRow + pi.getBoardLimit() - 1;
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			// 4, 5) 쿼리문 실행 후 결과 받기
			// select : executeQuery 메소드
			rset = pstmt.executeQuery();
			
			// 6) rset 으로부터 조회된 결과를 VO 로 옮겨담기
			while(rset.next()) {
				
				list.add(new Notice(rset.getInt("NOTICE_NO"),
								    rset.getString("NOTICE_TITLE"),
								    rset.getInt("NOTICE_COUNT"),
								    rset.getDate("NOTICE_DATE")));
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
	 * 게시글 조회수 증가용 쿼리문 실행 메소드
	 * @param conn
	 * @param noticeNo 조회수를 증가시킬 게시글 번호
	 * @return 처리된 행의 갯수
	 *  24/10/23 11시 윤홍문 작업
	 */
	public int increaseCount(Connection conn, int noticeNo) {
		// UPDATE 문 => int (처리된 행의 갯수)
		// 1) 필요한 변수들 먼저 셋팅
		int result = 0;
		PreparedStatement pstmt = null;
		
		// 실행할 SQL 문
		String sql = prop.getProperty("increaseCount");
		
		try {
			
			// 2) PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// 3) 미완성된 SQL 문 완성시키기
			pstmt.setInt(1, noticeNo);
			
			// 4, 5) 쿼리문 실행 후 결과 받기
			// update : executeUpdate 메소드
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 6) 다 쓴 JDBC 용 자원 반납 (역순)
			close(pstmt);
		}
		// 7) Service 로 결과 리턴
		return result;
	}

	/**
	 * 일반게시글 상세조회용 쿼리문 실행 메소드
	 * @param conn
	 * @param noticeNo 상세조회할 게시글 번호
	 * @return 상세조회된 게시글의 정보
	 * 24/10/23 12시 윤홍문 작업
	 */
	public Notice selectNotice(Connection conn, 
							   int noticeNo) {
		// SELECT 문 => ResultSet (단일행)
		// => Notice
		
		// 1) 필요한 변수들 먼저 셋팅
		Notice n = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		// 실행할 SQL 문 
		String sql = prop.getProperty("selectNotice");
		
		try {
			
			// 2) PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// 3) 미완성된 SQL 문 완성시키기
			pstmt.setInt(1, noticeNo);
			
			// 4, 5) 쿼리문 실행 후 결과 받기
			// select : executeQuery 메소드
			rset = pstmt.executeQuery();
			
			// 6) rset 으로 부터 조회된 데이터를 뽑아서
			//    VO 로 옮겨담기
			if(rset.next()) {

				n = new Notice();
				n.setNoticeNo(rset.getInt("NOTICE_NO"));
				n.setNoticeTitle(rset.getString("NOTICE_TITLE"));
				n.setNoticeContent(rset.getString("NOTICE_CONTENT"));
				n.setNoticeCount(rset.getInt("NOTICE_COUNT"));
				n.setNoticeDate(rset.getDate("NOTICE_DATE"));
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 7) 다 쓴 JDBC 용 자원 반납 (역순)
			close(rset);
			close(pstmt);
		}
		
		// 8) Service 로 결과 반환
		return n;
	}

	/**
	 * 공지사항 작성용 쿼리문 실행 메소드
	 * @param conn
	 * @param n 추가할 공지사항의 내용
	 * @return 처리된 행의 갯수
	 * 24/10/23 15시 윤홍문 작업
	 */
	public int insertNotice(Connection conn, 
							Notice n) {
		// INSERT 문	=> int (처리된 행의 갯수)
		// 1) 필요한 변수들 먼저 셋팅
		int result = 0;
		PreparedStatement pstmt = null;
		
		// 실행할 SQL 문 
		String sql = prop.getProperty("insertNotice");
		
		try {
			
			// 2) PreparedStatemenet 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// 3) 미완성된 SQL 문 완성시키기
			pstmt.setString(1, n.getNoticeTitle());
			pstmt.setString(2, n.getNoticeContent());
			
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
	 * 공지사항 삭제용 쿼리문 실행 메소드
	 * @param conn
	 * @param noticeNo 삭제할 공지사항 번호
	 * @return 처리된 행의 갯수
	 * 24.10.23 17시 윤홍문 작성
	 */
	public int deleteNotice(Connection conn, int noticeNo) {
		// UPDATE 문 => int (처리된 행의 갯수)
		// 1) 필요한 변수들 먼저 셋팅
		int result = 0;
		PreparedStatement pstmt = null;
		
		// 실행할 SQL 문
		String sql = prop.getProperty("deleteNotice");
		
		try {
			
			// 2) PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// 3) 미완성된 SQL 문 완성시키기
			pstmt.setInt(1, noticeNo);
			
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
	 * 공지사항 수정용 쿼리문 실행 메소드
	 * @param conn
	 * @param n 수정할 공지사항 게시글 정보
	 * @return 처리된 행의 갯수
	 * 24.10.23 18시 윤홍문 작성
	 */
	public int updateNotice(Connection conn, Notice n) {
		// UPDATE 문 => int (처리된 행의 갯수)
		// 1) 필요한 변수들 먼저 셋팅
		int result = 0;
		PreparedStatement pstmt = null;
		
		// 실행할 SQL 문
		String sql = prop.getProperty("updateNotice");
		
		try {
			
			// 2) PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// 3) 미완성된 SQL 문 완성시키기
			pstmt.setString(1, n.getNoticeTitle());
			pstmt.setString(2, n.getNoticeContent());
			pstmt.setInt(3, n.getNoticeNo());
			
			// 4, 5) 쿼리문 실행 후 결과 받기
			// update : executeUpdate 메소드
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
	 * 이전 게시글 조회용 쿼리문 실행 메소드
	 * @param conn
	 * @param noticeNo 기준 게시글 번호
	 * @return 이전게시글정보
	 * 24.10.30 23시 윤홍문 작성
	 */
	public Notice selectPreviousNotice(Connection conn, int noticeNo) {
		// SELECT 문 => ResultSet (단일행)
		// => Notice
		
		// 1) 필요한 변수들 먼저 셋팅
		Notice prevn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		// 실행할 SQL 문 
		String sql = prop.getProperty("selectPreviousNotice");
		
		try {
			
			// 2) PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// 3) 미완성된 SQL 문 완성시키기
			pstmt.setInt(1, noticeNo);
			
			// 4, 5) 쿼리문 실행 후 결과 받기
			// select : executeQuery 메소드
			rset = pstmt.executeQuery();
			
			// 6) rset 으로 부터 조회된 데이터를 뽑아서
			//    VO 로 옮겨담기
			if(rset.next()) {

				prevn = new Notice();
				prevn.setNoticeNo(rset.getInt("NOTICE_NO"));
				prevn.setNoticeTitle(rset.getString("NOTICE_TITLE"));
				prevn.setNoticeContent(rset.getString("NOTICE_CONTENT"));
				prevn.setNoticeCount(rset.getInt("NOTICE_COUNT"));
				prevn.setNoticeDate(rset.getDate("NOTICE_DATE"));
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 7) 다 쓴 JDBC 용 자원 반납 (역순)
			close(rset);
			close(pstmt);
		}
		
		// 8) Service 로 결과 반환
		return prevn;
	}

	/**
	 * 다음게시글 조회용 쿼리문 실행 메소드
	 * @param conn
	 * @param noticeNo 기준게시글번호
	 * @return 다음게시글정보
	 * 24.10.30 23시 윤홍문 작성
	 */
	public Notice selectNextNotice(Connection conn, int noticeNo) {
		// SELECT 문 => ResultSet (단일행)
		// => Notice
		
		// 1) 필요한 변수들 먼저 셋팅
		Notice nextn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		// 실행할 SQL 문 
		String sql = prop.getProperty("selectNextNotice");
		
		try {
			
			// 2) PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// 3) 미완성된 SQL 문 완성시키기
			pstmt.setInt(1, noticeNo);
			
			// 4, 5) 쿼리문 실행 후 결과 받기
			// select : executeQuery 메소드
			rset = pstmt.executeQuery();
			
			// 6) rset 으로 부터 조회된 데이터를 뽑아서
			//    VO 로 옮겨담기
			if(rset.next()) {

				nextn = new Notice();
				nextn.setNoticeNo(rset.getInt("NOTICE_NO"));
				nextn.setNoticeTitle(rset.getString("NOTICE_TITLE"));
				nextn.setNoticeContent(rset.getString("NOTICE_CONTENT"));
				nextn.setNoticeDate(rset.getDate("NOTICE_DATE"));
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 7) 다 쓴 JDBC 용 자원 반납 (역순)
			close(rset);
			close(pstmt);
		}
		
		// 8) Service 로 결과 반환
		return nextn;
	}

}
