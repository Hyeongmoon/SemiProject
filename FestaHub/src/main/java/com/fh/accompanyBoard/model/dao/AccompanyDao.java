package com.fh.accompanyBoard.model.dao;

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
import com.fh.accompanyBoard.model.vo.AccompanyLike;
import com.fh.common.JDBCTemplate;
import com.fh.common.model.vo.PageInfo;
import com.fh.notice.model.dao.NoticeDao;

public class AccompanyDao {
	
	private Properties prop = new Properties();

	// 공통코드 - 쿼리문들을 키 + 밸류 세트로 불러오기
	public AccompanyDao() {
		
		String path = "/sql/accompanyBoard/accompany-mapper.xml";
		
		String fileName = NoticeDao.class.getResource(path).getPath();
		
		try {
			prop.loadFromXML(new FileInputStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * 동행 게시글 총 갯수를 구하는 쿼리문 실행 메소드
	 * @param conn
	 * @return 총게시글 갯수
	 * 24.10.24 10시 윤홍문 작성
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
		return listCount; // 총 게시글 갯수
	}


	/**
	 * 게시글 목록 죄회용 쿼리문 실행 메소드
	 * @param conn
	 * @param pi 구간별로 끊을 때 필요한 변수
	 * @return 조회된 일반게시글
	 * 24.10.24 11시 윤홍문 작성
	 */
	public ArrayList<Accompany> selectList(Connection conn, 
										   PageInfo pi) {
		// SELECT 문 => ResultSet (여러행)
		// => ArrayList
		
		// 1) 필요한 변수들 먼저 셋팅
		ArrayList<Accompany> list = new ArrayList<>();
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
				
				list.add(new Accompany(rset.getInt("ACCOM_NO"),
								    rset.getString("ACCOM_TITLE"),
								    rset.getDate("ACCOM_DATE"),
								    rset.getInt("ACCOM_COUNT"),
								    rset.getString("USER_NICKNAME"),
								    rset.getInt("LIKE_COUNT")));
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
	 * 동행게시글 작성용 쿼리문 실행 메소드
	 * @param conn
	 * @param ac 추가할 게시글의 내용
	 * @return 처리된 행의 갯수
	 * 24.10.24 14시 윤홍문 작성
	 */
	public int insertAccompany(Connection conn, Accompany ac) {
		// INSERT 문	=> int (처리된 행의 갯수)
		// 1) 필요한 변수들 먼저 셋팅
		int result = 0;
		PreparedStatement pstmt = null;
		
		// 실행할 SQL 문 
		String sql = prop.getProperty("insertAccompany");
		
		try {
			
			// 2) PreparedStatemenet 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// 3) 미완성된 SQL 문 완성시키기
			pstmt.setString(1, ac.getAccomTitle());
			pstmt.setString(2, ac.getAccomContent());
			pstmt.setInt(3, Integer.parseInt(ac.getUserNo()));
			
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
	 * 동행구하기 게시글 조회수 증가용 쿼리문 실행 메소드
	 * @param conn
	 * @param accomNo 조회수를 증가시킬 게시글 번호
	 * @return 처리된 행의 갯수
	 * 24.10.24 15시 윤홍문 작성
	 */
	public int increaseCount(Connection conn, int accomNo) {
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
			pstmt.setInt(1, accomNo);
			
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
	 * 동행구하기 게시글 상세조회용 쿼리문 실행 메소드
	 * @param conn 
	 * @param accomNo 상세조회할 게시글 번호
	 * @return 상세조회된 게시글 한개의 정보
	 * 24.10.24 15시 윤홍문 작성
	 */
	public Accompany selectAccompany(Connection conn, int accomNo) {
		// SELECT 문 => ResultSet (단일행)
		// => Accompany
				
		// 1) 필요한 변수들 먼저 셋팅
		Accompany ac = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		// 실행할 SQL 문 
		String sql = prop.getProperty("selectAccompany");
		
		try {
			
			// 2) PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// 3) 미완성된 SQL 문 완성시키기
			pstmt.setInt(1, accomNo);
			
			// 4, 5) 쿼리문 실행 후 결과 받기
			// select : executeQuery 메소드
			rset = pstmt.executeQuery();
			
			// 6) rset 으로 부터 조회된 데이터를 뽑아서
			//    VO 로 옮겨담기
			if(rset.next()) {
				ac = new Accompany();
				
				ac.setAccomNo(rset.getInt("ACCOM_NO"));
				ac.setAccomTitle(rset.getString("ACCOM_TITLE"));
				ac.setAccomContent(rset.getString("ACCOM_CONTENT"));
				ac.setAccomDate(rset.getDate("ACCOM_DATE"));
				ac.setAccomCount(rset.getInt("ACCOM_COUNT"));
				ac.setUserNo(rset.getString("USER_NO"));
				ac.setLikeCount(rset.getInt("LIKE_COUNT"));
				ac.setUserNickname(rset.getString("USER_NICKNAME"));
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 7) 다 쓴 JDBC 용 자원 반납 (역순)
			close(rset);
			close(pstmt);
		}
		
		// 8) Service 로 결과 반환
		return ac;
	}


	/**
	 * 동행구하기 게시글 삭제용 쿼리문 실행 메소드
	 * @param conn
	 * @param accomNo 삭제할 게시글 번호
	 * @return 처리된 행의 갯수
	 */
	public int deleteAccompany(Connection conn, int accomNo) {
		// UPDATE 문 => int (처리된 행의 갯수)
		// 1) 필요한 변수들 먼저 셋팅
		int result = 0;
		PreparedStatement pstmt = null;
		
		// 실행할 SQL 문
		String sql = prop.getProperty("deleteAccompany");
		
		try {
			
			// 2) PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// 3) 미완성된 SQL 문 완성시키기
			pstmt.setInt(1, accomNo);
			
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
	 * 동행구하기 게시글 수정 쿼리문 실행 메소드
	 * @param conn
	 * @param ac 수정할 게시글 내용
	 * @return 처리된행의 갯수
	 * 24.10.25 12시 윤홍문 작성
	 */
	public int updateAccompany(Connection conn, Accompany ac) {
		// UPDATE 문 => int (처리된 행의 갯수)
		// 1) 필요한 변수들 먼저 셋팅
		int result = 0;
		PreparedStatement pstmt = null;
		
		// 실행할 SQL 문
		String sql = prop.getProperty("updateAccompany");
		
		try {
			
			// 2) PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// 3) 미완성된 SQL 문 완성시키기
			pstmt.setString(1, ac.getAccomTitle());
			pstmt.setString(2, ac.getAccomContent());
			pstmt.setInt(3, ac.getAccomNo());
			
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
	 * 좋아요 정보 조회용 쿼리문 실행 메소드
	 * @param conn 
	 * @param accomNo 게시글 번호
	 * @param userNo 유저넘버
	 * @return 조회된 좋아요 정보
	 * 24.10.30 16시 윤홍문 작성
	 */
	public AccompanyLike selectLikeInfo(Connection conn, int accomNo, int userNo) {
		// SELECT 문 => ResultSet (단일행)
		// => AccompanyLike
				
		// 1) 필요한 변수들 먼저 셋팅
		AccompanyLike al = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		// 실행할 SQL 문 
		String sql = prop.getProperty("selectLikeInfo");
		
		try {
			
			// 2) PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// 3) 미완성된 SQL 문 완성시키기
			pstmt.setInt(1, userNo);
			pstmt.setInt(2, accomNo);
			
			// 4, 5) 쿼리문 실행 후 결과 받기
			// select : executeQuery 메소드
			rset = pstmt.executeQuery();
			
			// 6) rset 으로 부터 조회된 데이터를 뽑아서
			//    VO 로 옮겨담기
			if(rset.next()) {
				
				boolean isLiked = rset.getInt("IS_LIKED") > 0;
                int likeCount = rset.getInt("LIKE_COUNT");
				
				al = new AccompanyLike(accomNo, userNo, isLiked, likeCount);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 7) 다 쓴 JDBC 용 자원 반납 (역순)
			close(rset);
			close(pstmt);
		}
		
		// 8) Service 로 결과 반환
		return al;
	}


	/**
	 * 좋아요 추가용 쿼리문
	 * @param conn
	 * @param accomNo
	 * @param userNo
	 * @return 처리된 행의 갯수
	 * 24.10.30 16시 윤홍문 작성
	 */
	public int likeAccompany(Connection conn, int accomNo, int userNo) {
		// INSERT 문 => int (처리된 행의 갯수)
		// 1) 필요한 변수들 먼저 셋팅
		int result = 0;
		PreparedStatement pstmt = null;
		
		// 실행할 SQL 문
		String sql = prop.getProperty("likeAccompany");
		
		try {
			
			// 2) PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// 3) 미완성된 SQL 문 완성시키기
			pstmt.setInt(1, userNo);
			pstmt.setInt(2, accomNo);
			
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
	 * 좋아요 취소용 쿼리문
	 * @param conn
	 * @param accomNo
	 * @param userNo
	 * @return 처리된 행의 갯수
	 * 24.10.30 16시 윤홍문 작성
	 */
	public int unlikeAccompany(Connection conn, int accomNo, int userNo) {
		// DELETE 문 => int (처리된 행의 갯수)
		// 1) 필요한 변수들 먼저 셋팅
		int result = 0;
		PreparedStatement pstmt = null;
		
		// 실행할 SQL 문
		String sql = prop.getProperty("unlikeAccompany");
		
		try {
			
			// 2) PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// 3) 미완성된 SQL 문 완성시키기
			pstmt.setInt(1, userNo);
			pstmt.setInt(2, accomNo);
			
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
	 * 이전글 조회용 쿼리문 실행 메소드
	 * @param conn
	 * @param accomNo 이전글 기준이될 게시글번호
	 * @return 이전글 정보
	 * 24.10.31 09시 윤홍문 작성
	 */
	public Accompany selectPreviousAccompany(Connection conn, int accomNo) {
		// SELECT 문 => ResultSet (단일행)
		// => Accompany
				
		// 1) 필요한 변수들 먼저 셋팅
		Accompany prevAc = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		// 실행할 SQL 문 
		String sql = prop.getProperty("selectPreviousAccompany");
		
		try {
			
			// 2) PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// 3) 미완성된 SQL 문 완성시키기
			pstmt.setInt(1, accomNo);
			
			// 4, 5) 쿼리문 실행 후 결과 받기
			// select : executeQuery 메소드
			rset = pstmt.executeQuery();
			
			// 6) rset 으로 부터 조회된 데이터를 뽑아서
			//    VO 로 옮겨담기
			if(rset.next()) {
				prevAc = new Accompany();
				
				prevAc.setAccomNo(rset.getInt("ACCOM_NO"));
				prevAc.setAccomTitle(rset.getString("ACCOM_TITLE"));
				prevAc.setAccomContent(rset.getString("ACCOM_CONTENT"));
				prevAc.setAccomDate(rset.getDate("ACCOM_DATE"));
				prevAc.setAccomCount(rset.getInt("ACCOM_COUNT"));
				prevAc.setUserNo(rset.getString("USER_NO"));
				prevAc.setLikeCount(rset.getInt("LIKE_COUNT"));
				prevAc.setUserNickname(rset.getString("USER_NICKNAME"));
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 7) 다 쓴 JDBC 용 자원 반납 (역순)
			close(rset);
			close(pstmt);
		}
		
		// 8) Service 로 결과 반환
		return prevAc;
	}


	/**
	 * 다음글 조회용 쿼리문 실행 메소드
	 * @param conn
	 * @param accomNo 다음글 기준이될 게시글번호
	 * @return 다음글 정보
	 * 24.10.31 09시 윤홍문 작성
	 */
	public Accompany selectNextAccompany(Connection conn, int accomNo) {
		// SELECT 문 => ResultSet (단일행)
		// => Accompany
				
		// 1) 필요한 변수들 먼저 셋팅
		Accompany nextAc = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		// 실행할 SQL 문 
		String sql = prop.getProperty("selectNextAccompany");
		
		try {
			
			// 2) PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// 3) 미완성된 SQL 문 완성시키기
			pstmt.setInt(1, accomNo);
			
			// 4, 5) 쿼리문 실행 후 결과 받기
			// select : executeQuery 메소드
			rset = pstmt.executeQuery();
			
			// 6) rset 으로 부터 조회된 데이터를 뽑아서
			//    VO 로 옮겨담기
			if(rset.next()) {
				nextAc = new Accompany();
				
				nextAc.setAccomNo(rset.getInt("ACCOM_NO"));
				nextAc.setAccomTitle(rset.getString("ACCOM_TITLE"));
				nextAc.setAccomContent(rset.getString("ACCOM_CONTENT"));
				nextAc.setAccomDate(rset.getDate("ACCOM_DATE"));
				nextAc.setAccomCount(rset.getInt("ACCOM_COUNT"));
				nextAc.setUserNo(rset.getString("USER_NO"));
				nextAc.setLikeCount(rset.getInt("LIKE_COUNT"));
				nextAc.setUserNickname(rset.getString("USER_NICKNAME"));
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 7) 다 쓴 JDBC 용 자원 반납 (역순)
			close(rset);
			close(pstmt);
		}
		
		// 8) Service 로 결과 반환
		return nextAc;
	}


	/**
	 * 검색 후 목록 갯수 조회용 쿼리문 실행 메소드
	 * @param conn
	 * @param category 검잭기준 (글제목, 글내용, 작성자)
	 * @param keyword 유저가 입력한 검색어
	 * @return 목록 갯수
	 * 24.10.31 14시 윤홍문 작성
	 */
	public int selectSearchListCount(Connection conn, String category, String keyword) {
		PreparedStatement pstmt = null;
	    ResultSet rset = null;
	    int listCount = 0;

	    String sql = "SELECT COUNT(*) AS COUNT " 
	            + "FROM ACCOMPANY AC "
	            + "JOIN USER_INFO UI ON AC.USER_NO = UI.USER_NO "
	            + "WHERE AC.ACCOM_STATUS = 'Y' "
	            + "AND " + category + " LIKE ?";

	    try {
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, "%" + keyword + "%");
	        rset = pstmt.executeQuery();

	        if (rset.next()) {
	            listCount = rset.getInt("COUNT");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close(rset);
	        close(pstmt);
	    }

	    return listCount;
	}


	/**
	 * 검색한 목록을 조회하는 쿼리문 실행 메소드
	 * @param conn
	 * @param category 검잭기준 (글제목, 글내용, 작성자)
	 * @param keyword 유저가 입력한 검색어
	 * @param pi 페이징 처리 변수들
	 * @param userNo
	 * @return 검색한 목록들
	 * 24.10.31 14시 윤홍문 작성
	 */
	public ArrayList<Accompany> selectSearchList(Connection conn, String category, 
												 String keyword, PageInfo pi,
												 int userNo) {
		PreparedStatement pstmt = null;
	    ResultSet rset = null;
	    ArrayList<Accompany> list = new ArrayList<>();
	    
	    String sql = "SELECT * "
	    			 + "FROM ( "
	    			 + "    SELECT ROWNUM AS RNUM, A.* "
	    			 + "    FROM ( "
	    			 + "        SELECT AC.*, "
	    			 + "               UI.USER_NICKNAME, "
	    			 + "               (SELECT COUNT(*) "
	    			 + "                  FROM ACCOMPANY_LIKE "
	    			 + "                 WHERE ACCOM_NO = AC.ACCOM_NO) AS LIKE_COUNT "
	    			 + "          FROM ACCOMPANY AC "
	    			 + "          JOIN USER_INFO UI ON AC.USER_NO = UI.USER_NO "
	               	 + "         WHERE AC.ACCOM_STATUS = 'Y' "
	            	 + "           AND " + category + " LIKE ? " // 동적 컬럼명 삽입
	            	 + "         ORDER BY AC.ACCOM_NO DESC "
	            	 + "    ) A "
	            	 + ") "
	            	 + "WHERE RNUM BETWEEN ? AND ?";

	    try {
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, "%" + keyword + "%");
	        pstmt.setInt(2, (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1);
	        pstmt.setInt(3, pi.getCurrentPage() * pi.getBoardLimit());

	        rset = pstmt.executeQuery();
	        
	        while (rset.next()) {
	            Accompany ac = new Accompany();
	            ac.setAccomNo(rset.getInt("ACCOM_NO"));
	            ac.setAccomTitle(rset.getString("ACCOM_TITLE"));
	            ac.setAccomContent(rset.getString("ACCOM_CONTENT"));
	            ac.setAccomDate(rset.getDate("ACCOM_DATE"));
	            ac.setAccomCount(rset.getInt("ACCOM_COUNT"));
	            ac.setUserNickname(rset.getString("USER_NICKNAME"));
	            ac.setLikeCount(rset.getInt("LIKE_COUNT"));
	            
	            list.add(ac);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close(rset);
	        close(pstmt);
	    }
	    return list;
	}


	
	
	

}
