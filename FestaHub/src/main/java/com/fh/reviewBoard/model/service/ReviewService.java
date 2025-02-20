package com.fh.reviewBoard.model.service;
import static com.fh.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.ArrayList;
import com.fh.common.model.vo.PageInfo;
import com.fh.festival.model.dao.FesDao;
import com.fh.festival.model.vo.Festival;
import com.fh.reviewBoard.model.dao.ReviewDao;
import com.fh.reviewBoard.model.vo.Review;
import com.fh.reviewBoard.model.vo.ReviewFile;


public class ReviewService {
	
	/**
	 * 일반게시글 전체 갯수 조회용 서비스 메소드
	 * @return => 일반게시글의 총 갯수
	 */
	public int selectListCount() {
		//1) Connection 객체 생성
		Connection conn = /*JDBCTemplate.*/getConnection();
		
		//2)Connection 객체와 전달값을 넘기면서 DAO 로 요청 후 결과 받기
		int listCount = new ReviewDao().selectListCount(conn);
		
		//3) DML 문의 경우 트랜젝션 처리 -> select 문이므로 패스
		//4) Connection 객체 반납
		close(conn);
		//5) Controller 로 결과 반환
		return listCount;
		
	}
	
	/**
	 * 게시글 목록 조회용 서비스 메소드
	 * @param pi => 구간별로 끊어서 조회할 때 필요한 변수
	 * @return => 조회한 게시물들
	 */
	public ArrayList<Review> selectList(PageInfo pi) {
		
		//1) Connection 객체 생성
		Connection conn = getConnection();
		//2) Connection 객체와 전달값을 넘기면서 DAO 로 요청 후 결과받기
		ArrayList<Review> list = new ReviewDao().selectList(conn, pi);
		//3)  DML 문의 경우 트랜젝션 처리 -> select문이므로 패스
		//4) Connection 객체 반납
		close(conn);
		//5) Controller 로 결과 반환
		return list;
	}
	
	/**
	 * 일반게시글 작성용 서비스 메소드
	 * @param r => 일반게시글 관련 내용
	 * @param f => 첨부파일 관련 내용
	 * @return => 처리된 행의 갯수
	 */
	public int insertReview(Review r) {
		//1) Connection 객체 생성
		Connection conn = getConnection();
		//2) Connection 객체와 전달값을 넘기면서  DAO 로 요청 후 결과 받기
		//2-1) Review  테이블에 Insert
		// >단, 해당 게시글에 딸린 첨부파일이 있을 경우에만!
		int result = new ReviewDao().insertReview(conn, r);
		//2-2) ReviewFile 테이블에 Insert
		// 단, 해당 게시글에 딸린 첨부파일이 있을 경우에만 !
		// (rf != null)
	
		commit(conn);		
		
		//5) Controller 로 결과 반환
		//return result1, reesult2; -> return 값은 최대 1개!
		return result;
		// 둘 다 성공했을 경우 1이 리턴됨, 하나라도 실패했을 경우 0이 리턴됨
		
	}
	
	/**
	 * 일반게시글 조회수 증가용 서비스 메소드
	 * @param rvNo => 조회수를 증가시킬 게시글 번호
	 * @return => 처리된 행의 갯수
	 */
	public int increaseRvCount(int rvNo) {
		// 1) Connection 객체 생성
		Connection conn = getConnection();
		//2)Connection 객체와 전달값을 넘기면서 DAO 로 요청 후 결과 받기
		int result = new ReviewDao().increaseRvCount(conn,rvNo);
		//3) DML 문의 경우 트랜젝션 처리
		// > 패스
		//4) Connection 객체 반납
		close(conn);
		//5) Controller 로 결과 반환
		return result;
		
		
	}// 여기까지가 리스트 조회
	
	/**
	 * 게시글 상세조회 서비스용 메소드
	 * @param rvNo => 상세조회할 글 번호
	 * @return => 상세조회된 게시글 한개의 내용
	 */
	public Review selectReview(int rvNo) {
		
		//1) Connection 객체 생성
		Connection conn = getConnection();
		//2) Connection 객체와 전달값을 넘기면서 DAO 로 요청 후 결과 받기
		Review r = new ReviewDao().selectReview(conn, rvNo);
		//3) DML 문의 경우 트랜젝션 처리
		// > 패스
		//4) Connection 객체 반납
		     close(conn);
		//5)Controller 로 결과 반환
		     return r;
		
	}

		/**
		 * 일반게시글 수정용 서비스 메소드
		 * @param r => 수정할 게시글 정보
		 * @param rf => 수정할 첨부파일 정보
		 * @return => 처리된 행의 갯수
		 */
		public int updateReview(Review r) {
			
			//1) Connection 객체 생성
			Connection conn = getConnection();
			//2) Connection 객체와 전달값을 넘기면서 DAO로 요청 후 결과 받기
			//2-1. Review 테이블에 update 하는 요청 후 결과
			int result1 = new ReviewDao().updateReview(conn, r);

			// 3) DML 문의 경우 트랜잭션 처리 
			if(result1>0) { // 성공(커밋)
				commit(conn);
			} else { // 실패(롤백)
				rollback(conn);
			}
			// 4) Connection 객체 반납 
			close(conn);
			// 5) Controller 로 결과 반환
			return result1;
	}
		
		/**
		 * 일반게시글 첨부파일 조회 서비스용 메소드
		 * @param rvNo => 첨부파일을 조회하고자 하는 게시글 번호
		 * @return => 조회된 첨부파일 번호
		 */
		public ReviewFile selectReviewFile(int rvNo) {
			
			//1) Connection 객체 생성
			Connection conn = getConnection();
			//2) Connection 객체와 전달값을 넘기면서 DAO로 요청 후 결과 받기
			ReviewFile rf = new ReviewDao().selectReviewFile(conn, rvNo);
			//3) DML 문의 경우 트랜젝션 처리
			//>패스
			// 4) Connection 객체 반납
			close(conn);
			//5) Controller 로 결과 반환
			return rf;
		}
		
		/**
		 * 게시글 삭제 처리 서비스 메소드
		 * @param rvNo
		 * @return
		 */
		public int deleteReview(int rvNo) {
			Connection conn = getConnection();
			
			int result = new ReviewDao().deleteReview(conn, rvNo);
			
			if(result>0) {// 성공
				commit(conn);
			} else { // 실패
				rollback(conn);
			}
			// 4) Connection 객체 반납 
			close(conn);
			// 5) Controller 로 결과 반환
			return result;
			
			
		}
		
	
}
