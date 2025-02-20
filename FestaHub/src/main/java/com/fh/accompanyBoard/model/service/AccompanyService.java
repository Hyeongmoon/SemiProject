package com.fh.accompanyBoard.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import com.fh.accompanyBoard.model.dao.AccompanyDao;
import com.fh.accompanyBoard.model.vo.Accompany;
import com.fh.accompanyBoard.model.vo.AccompanyLike;
import com.fh.common.model.vo.PageInfo;

import static com.fh.common.JDBCTemplate.*;

public class AccompanyService {

	/**
	 * 동행게시글 전체 갯수 조회용 서비스 메소드
	 * @return 동행게시글의 총 갯수
	 * 24.10.24 10시 윤홍문 작성
	 */
	public int selectListCount() {
		// 1) Connection 객체 생성
		Connection conn = getConnection();
		
		// 2) Connection 객체와 전달값을 넘기면서
		//    DAO 로 요청 후 결과 받기
		int listCount 
			= new AccompanyDao().selectListCount(conn);
		
		// 3) DML 문의 경우 트랜잭션 처리
		// > select 문 이므로 패스
		
		// 4) Connection 객체 반납
		close(conn);
		
		// 5) Controller 로 결과 반환
		return listCount;
	}

	/**
	 * 동행구하기 게스글 목록 조회용 서비스 메소드
	 * @param pi 구간별로 끊어서 조회할 때 필요한 변수들
	 * @return 조회된 게시글들
	 * 24.10.24 11시 윤홍문 작성
	 */
	public ArrayList<Accompany> selectList(PageInfo pi) {
		// 1) Connection 객체 생성
		Connection conn = getConnection();
		
		// 2) Connection 객체와 전달값을 넘기면서
		//    DAO 로 요청 후 결과 받기
		ArrayList<Accompany> list 
			= new AccompanyDao().selectList(conn, pi);
		
		// 3) DML 문의 경우 트랜잭션 처리
		// > select 문 이므로 패스
		
		// 4) Connection 객체 반납
		close(conn);
		
		// 5) Controller 로 결과 반환
		return list;
	}

	/**
	 * 동행구하기 게시글 작성용 서비스 메소드
	 * @param ac 게시글 관련 내용
	 * @return 처리된 행의 갯수
	 * 24.10.24 14시 윤홍문 작성
	 */
	public int insertAccompany(Accompany ac) {
		// 1) Connection 객체 생성
		Connection conn = getConnection();
		
		// 2) Connection 객체와 전달값을 
		//	  DAO 로 넘기면서 요청 후 결과 받기
		int result 
			= new AccompanyDao().insertAccompany(conn, ac);
		
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
	 * 동행구하기 조회수 증가용 서비스메소드
	 * @param accomNo 조회수를 증가시킬 게시글 번호
	 * @return 처리된 행의 갯수
	 * 24.10.24 15시 윤홍문 작성
	 */
	public int increaseCount(int accomNo) {
		// 1) Connection 객체 생성
		Connection conn = getConnection();
		
		// 2) Connection 객체와 전달값을 넘기면서
		//    DAO 로 요청 후 결과 받기
		int result 
			= new AccompanyDao().increaseCount(conn, accomNo);
		
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
	 * 동행구하기 게시글 상세조회용 메소드
	 * @param accomNo 상세조회할 글 번호
	 * @return 상세조회된 게시글 한개의 내용
	 * 24.10.24 15시 윤홍문 작성
	 */
	public Accompany selectAccompany(int accomNo) {
		// 1) Connection 객체 생성
		Connection conn = getConnection();
		
		// 2) Connection 객체와 전달값을 넘기면서
		//    DAO 로 요청 후 결과 받기
		Accompany ac = new AccompanyDao().selectAccompany(conn, accomNo);
		
		// 3) DML 문의 경우 트랜잭션 처리
		// > 패스
		
		// 4) Connection 객체 반납
		close(conn);
		
		// 5) Controller 로 결과 반환
		return ac;
	}

	/**
	 * 동행구하기 게시글 삭제 서비스용 메소드
	 * @param accomNo 삭제할 게시글 번호
	 * @return 처리된 행의 갯수
	 * 24.10.24 17시 윤홍문 작성
	 */
	public int deleteAccompany(int accomNo) {
		// 1) Connection 객체 생성
		Connection conn = getConnection();
		
		// 2) Connection 과 전달값을 넘기면서
		//    DAO 로 요청 후 결과 받기
		int result 
			= new AccompanyDao().deleteAccompany(conn, accomNo);
		
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
	 * 동행구하기 게시글 수정 서비스용 메소드
	 * @param ac 수정할 게시글 내용
	 * @return 처리된 행의 갯수
	 * 24.10.25 12시 윤홍문 작성
	 */
	public int updateAccompany(Accompany ac) {
		// 1) Connection 객체 생성
		Connection conn = getConnection();
		
		// 2) Connection 객체와 전달값을 넘기면서
		//    DAO 로 요청 후 결과 받기
		int result 
			= new AccompanyDao().updateAccompany(conn, ac);
		
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
	 * 좋아요 토글 기능 실행용 서비스 메소드
	 * @param accomNo 게시글번호
	 * @param userNo 유저번호
	 * @return 기능 실행 후 재 조회된 좋아요 정보
	 * 24.10.30 16시 윤홍문 작성
	 */
	public AccompanyLike toggleLike(int accomNo, int userNo) {
		// 1) Connection 객체 생성
		Connection conn = getConnection();
		
		// 2) Connection 객체와 전달값을 넘기면서
		//    DAO 로 요청 후 결과 받기
		// 현재 좋아요 상태와 카운트를 조회
		AccompanyLike al 
			= new AccompanyDao().selectLikeInfo(conn, accomNo, userNo);
		
		// 좋아요 상태에따라 처리 분기
		int result = 0;
        
        if(!al.isLiked()) {
        	result = new AccompanyDao().likeAccompany(conn, accomNo, userNo);
        } else {
        	result = new AccompanyDao().unlikeAccompany(conn, accomNo, userNo);
        }
		
		// 3) DML 문의 경우 트랜잭션 처리
		if(result > 0) { // 성공 (커밋)
			commit(conn);
		} else { // 실패 (롤백)
			rollback(conn);
		}
		
        // 갱신된 좋아요 상태 및 카운트 다시 조회
		al = new AccompanyDao().selectLikeInfo(conn, accomNo, userNo);
		
		// 4) Connection 객체 반납
		close(conn);
		
		// 5) Controller 로 결과 반환
		return al;
	}

	/**
	 * 좋아요 정보를 가져오는 서비스 메소드
	 * @param accomNo 게시글 번호
	 * @param userNo 우저번호
	 * @return 조회된 좋아요 정보
	 * 24.10.30 16시 윤홍문 작성
	 */
	public AccompanyLike selectLikeInfo(int accomNo, int userNo) {
		// 1) Connection 객체 생성
		Connection conn = getConnection();
		
		// 2) Connection 객체와 전달값을 넘기면서
		//    DAO 로 요청 후 결과 받기
		AccompanyLike al = new AccompanyDao().selectLikeInfo(conn, accomNo, userNo);
		
		// 3) DML 문의 경우 트랜잭션 처리
		// > 패스
		
		// 4) Connection 객체 반납
		close(conn);
		
		// 5) Controller 로 결과 반환
		return al;
	}

	/**
	 * 이전글 조회용 서비스 메소드
	 * @param accomNo 기준 게시글 번호
	 * @return 조회한 게시글 정보
	 * 24.10.31 09시 윤홍문 작성
	 */
	public Accompany selectPreviousAccompany(int accomNo) {
		// 1) Connection 객체 생성
		Connection conn = getConnection();
		
		// 2) Connection 객체와 전달값을 넘기면서
		//    DAO 로 요청 후 결과 받기
		Accompany prevAc = 
			new AccompanyDao().selectPreviousAccompany(conn, accomNo);
		
		// 3) DML 문의 경우 트랜잭션 처리
		// > 패스
		
		// 4) Connection 객체 반납
		close(conn);
		
		// 5) Controller 로 결과 반환
		return prevAc;
	}

	/**
	 * 다음글 조회용 서비스메소드
	 * @param accomNo 기준 게시글 번호
	 * @return 조회한 게시글 정보
	 * 24.10.31 09시 윤홍문 작성
	 */
	public Accompany selectNextAccompany(int accomNo) {
		// 1) Connection 객체 생성
		Connection conn = getConnection();
		
		// 2) Connection 객체와 전달값을 넘기면서
		//    DAO 로 요청 후 결과 받기
		Accompany nextAc = 
			new AccompanyDao().selectNextAccompany(conn, accomNo);
		
		// 3) DML 문의 경우 트랜잭션 처리
		// > 패스
		
		// 4) Connection 객체 반납
		close(conn);
		
		// 5) Controller 로 결과 반환
		return nextAc;
	}

	/**
	 * 검색했을 떄 목록의 숫자를 조회하기위한 서비스 메소드
	 * @param category 검색의 종류(글제목, 글내용, 글작성자)
	 * @param keyword 사용자가 입력한 검색어
	 * @return 조회한 목록의 갯수
	 * 24.10.31 14시 윤홍문 작성
	 */
	public int selectSearchListCount(String category, String keyword) {
		// 1) Connection 객체 생성
		Connection conn = getConnection();
		
		// 2) Connection 객체와 전달값을 넘기면서
		//    DAO 로 요청 후 결과 받기
		int listCount 
			= new AccompanyDao().selectSearchListCount(conn, category, keyword);
		
		// 3) DML 문의 경우 트랜잭션 처리
		// > select 문 이므로 패스
		
		// 4) Connection 객체 반납
		close(conn);
		
		// 5) Controller 로 결과 반환
		return listCount;
	}

	/**
	 * 검색결과를 목록조회하는 서비스 메소드
	 * @param category 검색의 종류(글제목, 글내용, 글작성자)
	 * @param keyword 사용자가 입력한 검색어
	 * @param pi 페이징처리 변수들
	 * @param userNo ??
	 * @return 검색해서 목록조회한 내용
	 * 24.10.31 14시 윤홍문 작성
	 */
	public ArrayList<Accompany> selectSearchList(String category, 
												 String keyword, 
												 PageInfo pi,
												 int userNo) {
		// 1) Connection 객체 생성
		Connection conn = getConnection();
		
		// 2) Connection 객체와 전달값을 넘기면서
		//    DAO 로 요청 후 결과 받기
		ArrayList<Accompany> list 
			= new AccompanyDao().selectSearchList(conn, category, keyword, pi, userNo);
		
		// 3) DML 문의 경우 트랜잭션 처리
		// > select 문 이므로 패스
		
		// 4) Connection 객체 반납
		close(conn);
		
		// 5) Controller 로 결과 반환
		return list;
	}

	
	
}
