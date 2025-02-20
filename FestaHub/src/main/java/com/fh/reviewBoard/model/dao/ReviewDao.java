package com.fh.reviewBoard.model.dao;

import static com.fh.common.JDBCTemplate.close;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import com.fh.common.model.vo.PageInfo;
import com.fh.festival.model.vo.Festival;
import com.fh.reviewBoard.model.vo.Review;
import com.fh.reviewBoard.model.vo.ReviewFile;



public class ReviewDao {
	
	private Properties prop = new Properties();
	
	public ReviewDao() {
		
		String fileName = ReviewDao.class.getResource("/sql/review/review-mapper.xml").getPath();
		
		System.out.println(fileName);
		
		try {
			prop.loadFromXML(new FileInputStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	/**
	 * 총 게시글 갯수를 구하는 쿼리문용 실행 메소드
	 * @param conn => DB 접속용 객체
	 * @return => 총 게시글 갯수
	 */
	public int selectListCount(Connection conn) {
		
		// SELECT 문 => ResultSet (단일행)
		
		//1) 필요한 변수들 먼저 셋팅
		int listCount = 0; //총 게시글의 갯수를 담을 변수
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		//실행할 SQL 문
		String sql = prop.getProperty("selectListCount");
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				
				listCount = rset.getInt("RV_COUNT");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 7) 다 쓴 JDBC 용 자원 반납 (역순)
			close(rset);
			close(pstmt);
			
		}
	
		//8) Service 로 결과 리턴
		return listCount; // 총 게시글 갯수
	
	}

	/**
	 * 게시글 목록 조회용 퉈리문 실행 메소드
	 * @param conn =>DB 접속용 객체
	 * @param pi => 구간별로 끊을 때 필요한 변수
	 * @return => 조회된 일반게시글들
	 */
	public ArrayList<Review> selectList(Connection conn, PageInfo pi) {
		
		//SELECT 문 => ResultSet (여러행)
		// => ArrayList
		
		// 1) 필요한 변수들 먼저 셋팅
		ArrayList<Review> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		//실행할 SQL 문 
		String sql = prop.getProperty("selectList");
		
		try {
			
			//2) PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			//3) 미완성된 SQL 문 완성시키기
			
			int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
			int endRow = startRow + pi.getBoardLimit() - 1;
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
           while(rset.next()) {
				
				/*
				Board b = new Board(rset.getInt("BOARD_NO"),
									rset.getString("CATEGORY_NAME"),
									rset.getString("BOARD_TITLE"),
									rset.getString("USER_ID"),
									rset.getInt("COUNT"),
									rset.getDate("CREATE_DATE"));
				
				list.add(b);
				*/
				
        	    Review r = new Review();
        	    
        	    r.setRvNo(rset.getInt("RV_NO"));
				r.setRvTitle(rset.getString("RV_TITLE"));
				r.setUserNickname(rset.getString("USER_NICKNAME"));
				r.setRvDate(rset.getDate("RV_DATE"));
				r.setRvCount(rset.getInt("RV_COUNT"));
				r.setRvRating(rset.getInt("RV_RATING"));
				r.setReviewLike(rset.getInt("REVIEW_LIKE"));
				r.setRvCommentNo(rset.getInt("COMMENT_NO_COUNT")); // 댓글갯수
        	   
				list.add(r);
			
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}
	
	/**
	 * 일반게시글 작성 - Review 용 뭐리문 실행 메소드
	 * @param conn => DB 접속용 객체
	 * @param r => 작성 처리할 게시글의 정보
	 * @return => 처리된 행의 갯수
	 */
	public int insertReview(Connection conn, Review r) {
		
		// insert 문 => int (처리된 행의 갯수)
		
		//1) 필요한 변수들 먼저 셋팅
		int result = 0;
		PreparedStatement pstmt = null;
		
		//실행할 SQL 문
		String sql = prop.getProperty("insertReview");
		
		
		try {
			// 2) PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			   // 3) 미완성된 SQL문 완성시키기
	        pstmt.setString(1, r.getRvTitle());     // 첫 번째 ? -> 리뷰 제목
	        pstmt.setInt(2, r.getUserNo());       // 두 번째 ? -> 작성자 번호
	        pstmt.setString(3, r.getRvContent());   // 세 번째 ? -> 리뷰 내용

	        result = pstmt.executeUpdate();

	         
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			//6) 다 쓴 JDBC 용 자원 반납 (역순)
			close(pstmt);
		}
		
		//7) Service 로 결과 반환
		return result;
	}

   /**
    * 일반게시글 - RvFile 작성용 뭐리문 실행 메소드
    * @param conn => DB 접속용 객체
    * @param f => 등록할 첨부파일의 정보
    * @return => 처리된 행의 갯수
 */
public int insertReviewFile(Connection conn, ReviewFile rf) {
	
	// insert 문 => int (처리된 행의 갯수)
	
	//1)필요한 변수들 먼저 셋팅
	int result = 0;
	PreparedStatement pstmt = null;
	
	//실행할 SQL 문
	String sql = prop.getProperty("insertReviewFile");
	
	try {
		
		pstmt= conn.prepareStatement(sql);
		
		pstmt.setString(1, rf.getRvFileName());
		pstmt.setString(2, rf.getRvFileRename());
		pstmt.setString(3, rf.getRvFilePath());
		
		/*<entry key="insertReviewFile">
		INSERT INTO ATTACHMENT(RV_FILE_NO
		                     , RV_NO
		                     , RV_FILE_NAME
		                     , RV_FILE_RENAME
		                     , FILE_PATH)
		                VALUES(SEQ_RV_FILE_NO.NEXTVAL
		                     , SEQ_RV_NO.CURRVAL
		                     , ?
		                     , ?
		                     , ?)*/
		
		result = pstmt.executeUpdate();
		                     
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		//6) 다 쓴 JDBC 용 자원 반납 (역순)
		close(pstmt);
	}
	   
	return result;
   }
 
	/**
	 * 게시글 조뢰수 증가용 쿼리문 실행 메소드
	 * @param conn =>DB 접속용 객체
	 * @param RvNo =>조회수를 증가시킬 게시글 번호
	 * @return => 처리된 행의 갯수
	 */
	public int increaseRvCount(Connection conn, int rvNo ) {
		
		//UPDATE 문 =>int(처리된 행의 갯수)
		
		//1)필요한 변수들 먼저 셋팅
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("increaseRvCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, rvNo);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			
			close(pstmt);
		}
		
		return result;
	}
	/**
	 * 일반게시글 상세조회용 쿼리문 실행 메소드
	 * @param conn
	 * @param rvNo
	 * @return
	 */
	public Review selectReview(Connection conn, int rvNo) {
		
		// SELECT 문 => ResultSet(단일행)
		// =>Review
		
		//1) 필요한 변수들 먼저 셋팅
		Review r = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectReview");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, rvNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				
				r = new Review();
				
				
				r.setRvNo(rset.getInt("RV_NO"));
				r.setRvTitle(rset.getString("RV_TITLE"));
				r.setRvContent(rset.getString("RV_CONTENT"));
				r.setRvRating(rset.getInt("RV_RATING"));
				r.setRvDate(rset.getDate("RV_DATE"));
				r.setRvCount(rset.getInt("RV_COUNT"));
				r.setUserNickname(rset.getString("USER_NICKNAME"));
				r.setUserNo(rset.getInt("USER_NO"));

			}
			
		} catch (SQLException e) {
		e.printStackTrace();
		} finally {
			
			close(rset);
			close(pstmt);
		}
		
		return r;
	}
	
	/**
	 * 일반게시글 수정 쿼리문용 실행 메소드
	 * @param conn => DB 접속용 객체
	 * @param r => 수정할 게시글의 정보
	 * @return => 처리된 행의 갯수
	 */
	public int updateReview(Connection conn, Review r) {
		
		// UPDATE 문 => int(처리된 행의 갯수)
		
		//1) 필요한 변수들 먼저 셋팅
		int result = 0;
		PreparedStatement pstmt = null;
		
		//실행할 SQL 문
		String sql = prop.getProperty("updateReview");
		
		try {
			// 2) PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			//3) 미완성된 SQL 문 완성시키기
			pstmt.setInt(1, r.getRvNo());
			pstmt.setString(2, r.getRvTitle());
			pstmt.setString(3, r.getRvContent());
			pstmt.setInt(4, r.getRvRating());
			
			// 4, 5) 쿼리문 실행 후 결과 받기 
			// update : executeUpdate 메소드 
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			// 6) 다 쓴 JDBC용 자원 반납(역순) 
			close(pstmt);
		}
		// 7) Service로 결과 반환 
		return result;
	}

	/**
	 * 일반게시글 첨부파일 조회용 쿼리문 실행 메소드
	 * @param conn =>DB 접속용 객체
	 * @param rvNo => 첨부파일을 조회하고자 하는 게시글 번호
	 * @return =>조회된 첨부파일 정보
	 */
	public ReviewFile selectReviewFile(Connection conn, int rvNo) {
		// SELECT문 => ResultSet (단일행)
		// => ReviewFile
		
		//1) 필요한 변수들 먼저 셋팅
		ReviewFile rf = null; 
		PreparedStatement pstmt = null; 
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectReviewFile");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, rvNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				
				rf = new ReviewFile();
				rf.setRvFileNo(rset.getInt("FILE_NO"));
				rf.setRvFileName(rset.getString("RV_File_NAME"));
				rf.setRvFileRename(rset.getString("RV_File_RENAME"));
				rf.setRvFilePath(rset.getString("RV_FILE_PATH"));
				rf.setRvFileStatus(rset.getString("RV_STATUS"));
			}
			
			// > 이 시점 기준으로 해당 게시글에 딸린 첨부파일이 없을 경우 rf == null
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//7)다 쓴 JDBC용 자원 반납(역순)
			close(rset);
			close(pstmt);
		}
		
		return rf;
	}
 
	 /**
	  * 첨부파일 수정용 쿼리문용 실행 메소드
	 * @param conn =>DB 접속용 객체
	 * @param rf => 첨부파일 수정 시 필요한 정보
	 * @return => 처리된 행의 갯수
	 */
	public int updateReviewFile(Connection conn, ReviewFile rf) {
		 
		// 1) 필요한 변수들 먼저 셋팅 
			int result = 0; 
			PreparedStatement pstmt = null; 
			
		// 실행할 SQL문 
			String sql = prop.getProperty("updateReviewFile");
			
			try {
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, rf.getRvFileName());
				pstmt.setString(2, rf.getRvFileRename());
				pstmt.setString(3, rf.getRvFileRename());
				pstmt.setString(4, rf.getRvFilePath());
				
				result = pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				//6) 다 쓴 JDBC 용 자원 반납(역순)
				close(pstmt);
			}
			//7)Service로 결과 반환
			return result;
	 }

	/**
	 * 첨부파일 수정용 메소드 - 기존 첨부파일이 없었을 경우
	 * @param conn
	 * @param rf
	 * @return
	 */
	public int insertNewReviewFile(Connection conn, ReviewFile rf) {
		
		// insert 문 => int(처리된 행의 갯수) 
		
				// 1) 필요한 변수들 먼저 셋팅 
				int result = 0; 
				PreparedStatement pstmt = null; 
				
				// 실행할 SQL문 
				String sql = prop.getProperty("insertNewReviewFile");
				
				try {
					pstmt = conn.prepareStatement(sql);
					
					// 미완성된 SQL문 완성시키기
					pstmt.setInt(1, rf.getRvFileNo());
					pstmt.setString(2, rf.getRvFileName());
					pstmt.setString(3, rf.getRvFileRename());
					pstmt.setString(4, rf.getRvFilePath());
					
					// 4,5)쿼리문 실행 후 결과 받기
					result = pstmt.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					//6) 다 쓴 JDBC 용 자원 반납(역순)
					close(pstmt);
				}
				// 7) Service 로 결과 반환
				return result;
	}
	/**
	 * 일반게시글 삭제용 쿼리문 실행 메소드
	 * @param conn
	 * @param rvNo
	 * @return
	 */
	public int deleteReview(Connection conn, int rvNo) {
		
		       // 1) 필요한 변수들 먼저 셋팅 
				int result = 0; 
				PreparedStatement pstmt= null; 
				
				// 실행할 SQL문 
				String sql = prop.getProperty("deleteReview");
				
				try {
					pstmt = conn.prepareStatement(sql);
					
					pstmt.setInt(1, rvNo);
					
					result = pstmt.executeUpdate();
					
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					//6) 다 쓴 JDBC용 자원반납(역순)
					close(pstmt);
				}
				//7) Service단으로 결과 리턴
				return result;
				
	}
	
	

	
}
