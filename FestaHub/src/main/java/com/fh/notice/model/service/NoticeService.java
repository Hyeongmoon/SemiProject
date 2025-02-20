package com.fh.notice.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import com.fh.common.model.vo.PageInfo;
import com.fh.notice.model.dao.NoticeDao;
import com.fh.notice.model.vo.Notice;

import static com.fh.common.JDBCTemplate.*;


public class NoticeService {

	/**
	 * 공지사항게시글 전체 갯수 조회용 서비스 메소드
	 * @return 공지사항 게시글의 총 갯수
	 * 24/10/22 18시 윤홍문 작업
	 */
	public int selectListCount() {
		// 1) Connection 객체 생성
		Connection conn = getConnection();
		
		// 2) Connection 객체와 전달값을 넘기면서
		//    DAO 로 요청 후 결과 받기
		int listCount 
			= new NoticeDao().selectListCount(conn);
		
		// 3) DML 문의 경우 트랜잭션 처리
		// > select 문 이므로 패스
		
		// 4) Connection 객체 반납
		close(conn);
		
		// 5) Controller 로 결과 반환
		return listCount;
	}

	/**
	 * 게시글 목록 조회용 서비스메소드
	 * @param pi 구간별로 끊어서 조회할 때 필요한 변수들
	 * @return 조회된 게시글들
	 * 24/10/23 10시 윤홍문 작업
	 */
	public ArrayList<Notice> selectList(PageInfo pi) {
		// 1) Connection 객체 생성
		Connection conn = getConnection();
		
		// 2) Connection 객체와 전달값을 넘기면서
		//    DAO 로 요청 후 결과 받기
		ArrayList<Notice> list 
			= new NoticeDao().selectList(conn, pi);
		
		// 3) DML 문의 경우 트랜잭션 처리
		// > select 문 이므로 패스
		
		// 4) Connection 객체 반납
		close(conn);
		
		// 5) Controller 로 결과 반환
		return list;
	}

	/**
	 * 일반게시글 조회수 증가용 서비스 메소드
	 * @param noticeNo 조회수를 증가시킬 게시글 번호
	 * @return 처리된 행의 갯수
	 * 24/10/23 11시 윤홍문 작업
	 */
	public int increaseCount(int noticeNo) {
		// 1) Connection 객체 생성
		Connection conn = getConnection();
		
		// 2) Connection 객체와 전달값을 넘기면서
		//    DAO 로 요청 후 결과 받기
		int result 
			= new NoticeDao().increaseCount(conn, noticeNo);
		
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
	 * 공지사항 게시글 상세조회 서비스용 메소드
	 * @param noticeNo 상세조회할 글 번호
	 * @return 상세조회된 게시글 한개의 내용
	 * 24/10/23 11시 윤홍문 작업
	 */
	public Notice selectNotice(int noticeNo) {
		// 1) Connection 객체 생성
		Connection conn = getConnection();
		
		// 2) Connection 객체와 전달값을 넘기면서
		//    DAO 로 요청 후 결과 받기
		Notice n = new NoticeDao().selectNotice(conn, noticeNo);
		
		// 3) DML 문의 경우 트랜잭션 처리
		// > 패스
		
		// 4) Connection 객체 반납
		close(conn);
		
		// 5) Controller 로 결과 반환
		return n;
	}

	/**
	 * 공지사항 작성용 서비스 메소드
	 * @param n 작성할 공지사항의 정보
	 * @return 처리된 행의 갯수
	 * 24/10/23 15시 윤홍문 작업
	 */
	public int insertNotice(Notice n) {
		// 1) Connection 객체 생성
		Connection conn = getConnection();
		
		// 2) Connection 객체와 전달값을 
		//	  DAO 로 넘기면서 요청 후 결과 받기
		int result 
			= new NoticeDao().insertNotice(conn, n);
		
		// 3) DML 문의 경우 트랜잭션 처리
		if(result > 0) { // 성공 (commit)
			
			commit(conn);
			
		} else { // 실패 (rollback)
			
			rollback(conn);
		}
		
		// 4) Connection 객체 반납
		close(conn);
		
		// 5) Controller 로 결과 리턴
		return result;
	}

	/**
	 * 공지사항 게시글 삭제 서비스용 메소드
	 * @param noticeNo 삭제할 공지사항 번호
	 * @return 처리된 행의 갯수
	 * 24.10.23 17시 윤홍문 작성
	 */
	public int deleteNotice(int noticeNo) {
		// 1) Connection 객체 생성
		Connection conn = getConnection();
		
		// 2) Connection 과 전달값을 넘기면서
		//    DAO 로 요청 후 결과 받기
		int result 
			= new NoticeDao().deleteNotice(conn, noticeNo);
		
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
	 * 공지사항 수정 서비스용 메소드
	 * @param n 수정할 공지사항 게시글 내용
	 * @return 처리된 행의 갯수
	 * 24.10.23 18시 윤홍문 작성
	 */
	public int updateNotice(Notice n) {
		// 1) Connection 객체 생성
		Connection conn = getConnection();
		
		// 2) Connection 객체와 전달값을 넘기면서
		//    DAO 로 요청 후 결과 받기
		int result 
			= new NoticeDao().updateNotice(conn, n);
		
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
	 * 이전 게시글 상세조회용 서비스 메소드
	 * @param noticeNo 기준 게시글 번호
	 * @return 이전게시글 정보
	 * 24.10.30 23시 윤홍문 작성
	 */
	public Notice selectPreviousNotice(int noticeNo) {
		// 1) Connection 객체 생성
		Connection conn = getConnection();
		
		// 2) Connection 객체와 전달값을 넘기면서
		//    DAO 로 요청 후 결과 받기
		Notice prevn = 
			new NoticeDao().selectPreviousNotice(conn, noticeNo);
		
		// 3) DML 문의 경우 트랜잭션 처리
		// > 패스
		
		// 4) Connection 객체 반납
		close(conn);
		
		// 5) Controller 로 결과 반환
		return prevn;
	}

	/**
	 * 다음게시글 상세조회용 서비스 메소드
	 * @param noticeNo 기준 게시글 번호
	 * @return 다음게시글정보
	 * 24.10.30 23시 윤홍문 작성
	 */
	public Notice selectNextNotice(int noticeNo) {
		// 1) Connection 객체 생성
		Connection conn = getConnection();
		
		// 2) Connection 객체와 전달값을 넘기면서
		//    DAO 로 요청 후 결과 받기
		Notice nextn = 
			new NoticeDao().selectNextNotice(conn, noticeNo);
		
		// 3) DML 문의 경우 트랜잭션 처리
		// > 패스
		
		// 4) Connection 객체 반납
		close(conn);
		
		// 5) Controller 로 결과 반환
		return nextn;
	}

}
