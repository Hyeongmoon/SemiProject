package com.fh.freeBoard.model.dao;

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
import com.fh.freeBoard.model.vo.FreeBoard;
import com.fh.freeBoard.model.vo.FreeBoardComment;
import com.fh.freeBoard.model.vo.FreeBoardFile;
import com.fh.freeBoard.model.vo.FreeBoardLike;

/**
 * @author user1
 *
 */
/**
 * @author user1
 *
 */
public class FreeBoardDao {
	
	//필드부
	private Properties prop = new Properties();
	
	//생성자부
	public FreeBoardDao() {
		String fileName = FreeBoardDao.class.getResource("/sql/freeBoard/freeBoard-mapper.xml").getPath();
		
		
		try {
			prop.loadFromXML(new FileInputStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	

	/**
	 * 2024.10.22.15:00
	 * 자유게시판 전체 게시글수 조회용 쿼리문 실행 메소드
	 * @return=>자유게시판 전체 게시글 수
	 */
	public int selectListCount(Connection conn) {

		PreparedStatement pstmt =null;
		int result=0;
		ResultSet rset =null;
		String sql = prop.getProperty("selectListCount");
		
		try {
			pstmt=conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				
				result = rset.getInt("COUNT");
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return result;
	}


	
	/**
	 * 2024.10.23 01:42 김형문
	 * 제목으로 검색시 전체 게시글수 조회용 쿼리문 실행 메소드
	 * @param conn => db 접속용 객체
	 * @param inputValue => 검색할 제목
	 * @return => 제목으로 검색된 총 게시글 수
	 */
	public int selectListCountTitle(Connection conn, String inputValue) {
		PreparedStatement pstmt =null;
		int result=0;
		ResultSet rset =null;
		String sql = prop.getProperty("selectListCountTitle");
		
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, "%"+inputValue+"%");
			rset = pstmt.executeQuery();
			if(rset.next()) {
				
				result = rset.getInt("COUNT");
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return result;
	}





	/**
	 * 2024.10.23 01:42 김형문
	 * 내용으로 검색시 총 게시글 수
	 * @param conn
	 * @param inputValue
	 * @return
	 */
	public int selectListCountContent(Connection conn, String inputValue) {
		PreparedStatement pstmt =null;
		int result=0;
		ResultSet rset =null;
		String sql = prop.getProperty("selectListCountContent");
		
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, "%"+ inputValue+"%");
			rset = pstmt.executeQuery();
			if(rset.next()) {
				
				result = rset.getInt("COUNT");
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return result;
	}





	/**
	 * 2024.10.23 01:42 김형문
	 * 닉네임으로 검색시 총 게시글 수
	 * @param conn =>  db 접속용 객체
	 * @param inputValue => 검색할 닉네임
	 * @return => 닉네임으로 검색시 전체 게시글 수
	 */
	public int selectListCountWriter(Connection conn, String inputValue) {
		PreparedStatement pstmt =null;
		int result=0;
		ResultSet rset =null;
		String sql = prop.getProperty("selectListCountWriter");
		
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, "%"+inputValue+"%");
			rset = pstmt.executeQuery();
			if(rset.next()) {
				
				result = rset.getInt("COUNT");
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return result;
	}

		



	/**
	 * 2024.10.22 16:00
	 * 자유게시판 전체 조회용 쿼리문 실행 메소드
	 * @param conn=> DB 접속용 객체
	 * @param pi=>보여질 구간에 대한 정보
	 * @return => 보여질 구간의 자유게시판 목록
	 */
	public ArrayList<FreeBoard> selectList(Connection conn, PageInfo pi) {
		
		ArrayList<FreeBoard> list = new ArrayList<>();
		PreparedStatement pstmt =null;
		ResultSet rset = null;
		String sql=prop.getProperty("selectList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			int startRow = (pi.getCurrentPage()-1)*pi.getBoardLimit()+1;
			int endRow = startRow+pi.getBoardLimit()-1;
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				
				FreeBoard f = new FreeBoard();
				
				f.setFreeNo(rset.getInt("FREE_NO"));
				f.setFreeTitle(rset.getString("FREE_TITLE"));
				f.setFreeContent(rset.getString("FREE_CONTENT"));
				f.setFreeDate(rset.getDate("FREE_DATE"));
				f.setFreeCount(rset.getInt("FREE_COUNT"));
				f.setFreeStatus(rset.getString("FREE_STATUS"));
				f.setUserNo(rset.getInt("USER_NO"));
				f.setUserNickName(rset.getString("USER_NICKNAME"));
				f.setFreeCommentCount(rset.getInt("COMMENT_COUNT"));
				f.setFreeLikeCount(rset.getInt("LIKE_COUNT"));
				
				list.add(f);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}





	/**
	 * 2024.10.23 13:50 형문
	 * 자유게시판 게시글 작성 쿼리문 실행메소드 
	 * @param conn => DB접속용 객체
	 * @param f => 게시글 정보
	 * @return => 처리된 행의 개수
	 */
	public int insertFreeBoard(Connection conn, FreeBoard f) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = prop.getProperty("insertFreeBoard");
		
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,f.getFreeTitle());
			pstmt.setString(2,f.getFreeContent());
			pstmt.setInt(3, f.getUserNo());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}





	




	/**
	 * 2024.10.23.15:10 김형문
	 * 자유게시판 게시글 조회수 증가용 쿼리문 실행 메소드
	 * @param freeNo =>조회수를 증가시킬 게시글 번호
	 * @return => 처리된 행의 갯수
	 */
	public int increaseCount(Connection conn, int freeNo) {
		
		int result=0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("increaseCount");
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, freeNo);
			result = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
		
	}





	/**
	 * 2024.10.23 15:17 김형문
	 * 자유게시판 게시글 상제조회용 쿼리문 실행 메소드
	 * @param conn=>DB접속용 객체
	 * @param freeNo=>조회할 게시글의 번호
	 * @return=> 조회된 게시글1개의 정보
	 */
	public FreeBoard selectFreeBoard(Connection conn, int freeNo) {
		
		PreparedStatement pstmt = null;
		FreeBoard f = null;
		ResultSet rset=null;
		String sql = prop.getProperty("selectFreeBoard");
		
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, freeNo);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				f = new FreeBoard();
				f.setFreeNo(rset.getInt("FREE_NO"));
				f.setFreeTitle(rset.getString("FREE_TITLE"));
				f.setFreeContent(rset.getString("FREE_CONTENT"));
				f.setFreeDate(rset.getDate("FREE_DATE"));
				f.setFreeCount(rset.getInt("FREE_COUNT"));
				f.setUserNickName(rset.getString("USER_NICKNAME"));
				f.setUserNo(rset.getInt("USER_NO"));
				f.setFreeLikeCount(rset.getInt("LIKE_COUNT"));
				f.setFreeCommentCount(rset.getInt("COMMENT_COUNT"));
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return f;
		
		
	}





	/**
	 * 2024.10.23 15:53 김형문
	 * 자유게시판 첨부파일 조회용 쿼리문 실행 메소드
	 * @param freeNo=>조회할 게시글의 번호
	 * @return => 조회된 첨부파일 리스트
	 */
	public ArrayList<FreeBoardFile> selectFreeBoardFile(Connection conn,int freeNo) {
		
		
		PreparedStatement pstmt = null;
		
		ArrayList<FreeBoardFile> files = new ArrayList<>();
		
		ResultSet rset = null;
		String sql = prop.getProperty("selectFreeBoardFile");
	
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, freeNo);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				
				FreeBoardFile file = new FreeBoardFile();
				
				file.setFreeFileNo(rset.getInt("FREE_FILE_NO"));
				file.setFreeFileName(rset.getString("FREE_FILE_NAME"));
				file.setFreeFileRename(rset.getString("FREE_FILE_RENAME"));
				file.setFreeFilePath(rset.getString("FREE_FILE_PATH"));
				file.setFreeNo(rset.getInt("FREE_NO"));
				
				files.add(file);
				
				
			}
			
		
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
	
		return files;
	}





	/**
	 * 2024.10.24 11:07 김형문
	 * 자유게시판 게시글 삭제(update)용 쿼리문 실행 메소드
	 * @param conn=>DB접속용객체
	 * @param userNo=>로그인유저번호
	 * @param freeNo=>삭제할 게시글 번호
	 * @return => 처리된 행의 갯수
	 */
	public int deleteFreeBoard(Connection conn, int userNo, int freeNo) {
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sql = prop.getProperty("deleteFreeBoard");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, freeNo);
			pstmt.setInt(2, userNo);
			result = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
		
	}









	



	/**
	 * 2024.10.25 17:40 김형문
	 * @param conn => DB접속용 객체
	 * @param fc=> 입력한 댓글 정보
	 * @return => 처리된 행의 갯수
	 */
	public int insertComment(Connection conn, FreeBoardComment fc) {
		
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("insertComment");
		
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,fc.getFreeCommContent());
			pstmt.setInt(2, fc.getFreeNo());
			pstmt.setInt(3, fc.getUserNo());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
		
	}





	/**
	 * 2024.10.25 18:05 김형문
	 * 자유게시판 댓글 조회용 쿼리문 실행 메소드
	 * @param conn => DB접속용객체
	 * @param freeNo => 댓글 조회할 게시글 번호
	 * @return => 댓글 리스트
	 */
	public ArrayList<FreeBoardComment> selectCommentList(Connection conn, int freeNo) {
		
		ArrayList<FreeBoardComment> list = new  ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectCommentList");
		
		
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1,freeNo );
			rset=pstmt.executeQuery();
			
			while(rset.next()) {
				FreeBoardComment fc = new FreeBoardComment();
				
				fc.setFreeCommNo(rset.getInt("FREE_COMM_NO"));
				fc.setFreeCommContent(rset.getString("FREE_COMM_CONTENT"));
				fc.setFreeCommDate(rset.getDate("FREE_COMM_DATE"));
				fc.setFreeNo(rset.getInt("FREE_NO"));
				fc.setUserNo(rset.getInt("USER_NO"));
				fc.setUserNickName(rset.getString("USER_NICKNAME"));
				list.add(fc);
				
				
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}

		return list;
		
		
		
	
	}




 
	/**2024.10.28 김형문
	 * 댓글 삭제용 쿼리문 실행 메소드
	 * @param conn=>DB접속용 객체
	 * @param freeCommNo => 삭제할 댓글 번호
	 * @return => 처리된 행의 갯수
	 */
	public int deleteComment(Connection conn, int freeCommNo) {
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sql = prop.getProperty("deleteComment");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1,freeCommNo);
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
		
		
		
	
	}





	public FreeBoardComment selectFreeBoardComment(Connection conn, int freeCommNo) {
		
		ResultSet rset= null;
		PreparedStatement pstmt = null;
		FreeBoardComment fc = null;
		String sql = prop.getProperty("selectFreeBoardComment");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, freeCommNo);
			rset=pstmt.executeQuery();
			
			if(rset.next()) {
				fc = new FreeBoardComment();
				fc.setFreeCommNo(rset.getInt("FREE_COMM_NO"));
				fc.setUserNo(rset.getInt("USER_NO"));
				fc.setFreeCommContent(rset.getString("FREE_COMM_CONTENT"));
			}
			
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return fc;
		
	}





	/**
	 * 2024.10.29 16:20 화요일 김형문
	 * 댓글 수정용 쿼리문 실행 메소드
	 * @param conn => DB접속용 객체
	 * @param fc=> 수정할 댓글의 내용, 댓글 번호
	 * @return => 처리된 행의 갯수
	 */
	public int updateFreeBoardComment(Connection conn, FreeBoardComment fc) {
		
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sql = prop.getProperty("updateFreeBoardComment");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, fc.getFreeCommContent());
			pstmt.setInt(2, fc.getFreeCommNo());
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
		
		
	}





	/**
	 *  2024.10.30.12:50 김형문
	 *  좋아요를 눌렀는지 안눌렀는지 확인 쿼리문 실행용 메소드
	 * @param conn => DB 접속용 객체
	 * @param fl => 게시글 번호, 로그인 유저번호
	 * @return => 1: 눌렀음 0:안누름
	 */
	public int selectLike(Connection conn, FreeBoardLike fl) {
		
		PreparedStatement pstmt = null;
		ResultSet rset= null;
		int result = 0;
		
		String sql = prop.getProperty("selectLike");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, fl.getFreeNo());
			pstmt.setInt(2, fl.getUserNo());
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				result = 1;
			}
			
			//눌렀을 경우 1, 누르지 않을 경우 0
		
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return result;
		
	}





	/**
	 * 2024.10.30 14:15 김형문
	 * 좋아요 설정 쿼리문 실행 메소드
	 * @param conn => DB 접속용 객체
	 * @param fl => 좋아요테이블에 추가할 정보
	 * @return => 처리된 행의 갯수
	 */
	public int insertLike(Connection conn, FreeBoardLike fl) {
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sql = prop.getProperty("insertLike");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, fl.getUserNo());
			pstmt.setInt(2, fl.getFreeNo());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}




	/**
	 * 2024.10.30 14:41 김형문
	 * 좋아요 삭제 쿼리문 실행 메소드
	 * @param conn => DB 접속용 객체
	 * @param fl => 좋아요테이블에서 삭제할 정보
	 * @return => 처리된 행의 갯수
	 */
	public int deleteLike(Connection conn, FreeBoardLike fl) {
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		String sql = prop.getProperty("deleteLike");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, fl.getFreeNo());
			pstmt.setInt(2, fl.getUserNo());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
		
	}





	/**
	 * 2024.10.30 15:40 김형문
	 * @param conn => DB 접속용 객체
	 * @param freeNo => 좋아요수를 조회할 게시글 번호
	 * @return => 총 좋아요 수
	 */
	public int selectLikeCount(Connection conn, int freeNo) {
		
		PreparedStatement pstmt = null;
		
		ResultSet rset = null;
		int result = 0;
		
		String sql = prop.getProperty("selectLikeCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, freeNo);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				
				result = rset.getInt("LIKE_COUNT");
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return result;
		
	}





	/**
	 * 2024.10.30 23:00 김형문
	 * 자유게시판 리스트 역순 조회 서비스 메소드
	 * @param conn => DB 접속용 객체
	 * @param pi => 페이징 처리 필요 정보
	 * @return => 역순으로 조회된 리스트 정보
	 */
	public ArrayList<FreeBoard> selectListAsc(Connection conn, PageInfo pi) {
		
		ArrayList<FreeBoard> list = new ArrayList<>();
		PreparedStatement pstmt =null;
		ResultSet rset = null;
		String sql=prop.getProperty("selectListAsc");
		
		try {
			pstmt = conn.prepareStatement(sql);
			int startRow = (pi.getCurrentPage()-1)*pi.getBoardLimit()+1;
			int endRow = startRow+pi.getBoardLimit()-1;
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				
				FreeBoard f = new FreeBoard();
				
				f.setFreeNo(rset.getInt("FREE_NO"));
				f.setFreeTitle(rset.getString("FREE_TITLE"));
				f.setFreeContent(rset.getString("FREE_CONTENT"));
				f.setFreeDate(rset.getDate("FREE_DATE"));
				f.setFreeCount(rset.getInt("FREE_COUNT"));
				f.setFreeStatus(rset.getString("FREE_STATUS"));
				f.setUserNo(rset.getInt("USER_NO"));
				f.setUserNickName(rset.getString("USER_NICKNAME"));
				f.setFreeCommentCount(rset.getInt("COMMENT_COUNT"));
				f.setFreeLikeCount(rset.getInt("LIKE_COUNT"));
				
				list.add(f);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
		
	}





	/**
	 * 2024.10.30 00:30 김형문
	 * 제목 검색용 쿼리문 실행 메소드
	 * @param conn => DB 접속용객체
	 * @param pi => 페이징바 처리 정보
	 * @return => 제목검색된 리스트
	 */
	public ArrayList<FreeBoard> selectListTitle(Connection conn, PageInfo pi, String inputValue) {
		ArrayList<FreeBoard> list = new ArrayList<>();
		PreparedStatement pstmt =null;
		ResultSet rset = null;
		String sql=prop.getProperty("selectListTitle");
		
		try {
			pstmt = conn.prepareStatement(sql);
			int startRow = (pi.getCurrentPage()-1)*pi.getBoardLimit()+1;
			int endRow = startRow+pi.getBoardLimit()-1;
			
			pstmt.setString(1, "%"+inputValue+"%");
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				
				FreeBoard f = new FreeBoard();
				
				f.setFreeNo(rset.getInt("FREE_NO"));
				f.setFreeTitle(rset.getString("FREE_TITLE"));
				f.setFreeContent(rset.getString("FREE_CONTENT"));
				f.setFreeDate(rset.getDate("FREE_DATE"));
				f.setFreeCount(rset.getInt("FREE_COUNT"));
				f.setFreeStatus(rset.getString("FREE_STATUS"));
				f.setUserNo(rset.getInt("USER_NO"));
				f.setUserNickName(rset.getString("USER_NICKNAME"));
				f.setFreeCommentCount(rset.getInt("COMMENT_COUNT"));
				f.setFreeLikeCount(rset.getInt("LIKE_COUNT"));
				
				list.add(f);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}





	/**
	 * 2024.10.30 00:30 김형문
	 * 내용 검색용 쿼리문 실행 메소드
	 * @param conn=>DB접속용 객체
	 * @param pi => 페이징바 처리 정보
	 * @return => 내용으로 검색된 리스트
	 */
	public ArrayList<FreeBoard> selectListContent(Connection conn, PageInfo pi, String inputValue) {
		ArrayList<FreeBoard> list = new ArrayList<>();
		PreparedStatement pstmt =null;
		ResultSet rset = null;
		String sql=prop.getProperty("selectListContent");
		
		try {
			pstmt = conn.prepareStatement(sql);
			int startRow = (pi.getCurrentPage()-1)*pi.getBoardLimit()+1;
			int endRow = startRow+pi.getBoardLimit()-1;
			
			
			pstmt.setString(1, "%"+inputValue+"%");
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				
				FreeBoard f = new FreeBoard();
				
				f.setFreeNo(rset.getInt("FREE_NO"));
				f.setFreeTitle(rset.getString("FREE_TITLE"));
				f.setFreeContent(rset.getString("FREE_CONTENT"));
				f.setFreeDate(rset.getDate("FREE_DATE"));
				f.setFreeCount(rset.getInt("FREE_COUNT"));
				f.setFreeStatus(rset.getString("FREE_STATUS"));
				f.setUserNo(rset.getInt("USER_NO"));
				f.setUserNickName(rset.getString("USER_NICKNAME"));
				f.setFreeCommentCount(rset.getInt("COMMENT_COUNT"));
				f.setFreeLikeCount(rset.getInt("LIKE_COUNT"));
				
				list.add(f);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}





	/**
	 * 2024.10.30 00:30 김형문
	 * 닉네임 검색용 쿼리문 실행 메소드
	 * @param conn => DB 접속용 객체
	 * @param pi => 페이징바 처리 정보
	 * @return => 닉네임으로 검색된 쿼리문 실행 메소드
	 */
	public ArrayList<FreeBoard> selectListWriter(Connection conn, PageInfo pi, String inputValue) {
		ArrayList<FreeBoard> list = new ArrayList<>();
		PreparedStatement pstmt =null;
		ResultSet rset = null;
		String sql=prop.getProperty("selectListWriter");
		
		try {
			pstmt = conn.prepareStatement(sql);
			int startRow = (pi.getCurrentPage()-1)*pi.getBoardLimit()+1;
			int endRow = startRow+pi.getBoardLimit()-1;
			
			pstmt.setString(1, "%"+inputValue+"%");
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				
				FreeBoard f = new FreeBoard();
				
				f.setFreeNo(rset.getInt("FREE_NO"));
				f.setFreeTitle(rset.getString("FREE_TITLE"));
				f.setFreeContent(rset.getString("FREE_CONTENT"));
				f.setFreeDate(rset.getDate("FREE_DATE"));
				f.setFreeCount(rset.getInt("FREE_COUNT"));
				f.setFreeStatus(rset.getString("FREE_STATUS"));
				f.setUserNo(rset.getInt("USER_NO"));
				f.setUserNickName(rset.getString("USER_NICKNAME"));
				f.setFreeCommentCount(rset.getInt("COMMENT_COUNT"));
				f.setFreeLikeCount(rset.getInt("LIKE_COUNT"));
				
				list.add(f);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}




	

	/**
	 * 2024.10.30 00:30 김형문
	 * 제목 검색용 쿼리문 실행 메소드
	 * @param conn => DB 접속용객체
	 * @param pi => 페이징바 처리 정보
	 * @return => 제목검색된 리스트
	 */
	public ArrayList<FreeBoard> selectListAscTitle(Connection conn, PageInfo pi, String inputValue) {
		ArrayList<FreeBoard> list = new ArrayList<>();
		PreparedStatement pstmt =null;
		ResultSet rset = null;
		String sql=prop.getProperty("selectListAscTitle");
		
		try {
			pstmt = conn.prepareStatement(sql);
			int startRow = (pi.getCurrentPage()-1)*pi.getBoardLimit()+1;
			int endRow = startRow+pi.getBoardLimit()-1;
			
			pstmt.setString(1, "%"+inputValue+"%");
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				
				FreeBoard f = new FreeBoard();
				
				f.setFreeNo(rset.getInt("FREE_NO"));
				f.setFreeTitle(rset.getString("FREE_TITLE"));
				f.setFreeContent(rset.getString("FREE_CONTENT"));
				f.setFreeDate(rset.getDate("FREE_DATE"));
				f.setFreeCount(rset.getInt("FREE_COUNT"));
				f.setFreeStatus(rset.getString("FREE_STATUS"));
				f.setUserNo(rset.getInt("USER_NO"));
				f.setUserNickName(rset.getString("USER_NICKNAME"));
				f.setFreeCommentCount(rset.getInt("COMMENT_COUNT"));
				f.setFreeLikeCount(rset.getInt("LIKE_COUNT"));
				
				list.add(f);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}





	/**
	 * 2024.10.30 00:30 김형문
	 * 내용 검색용 쿼리문 실행 메소드
	 * @param conn=>DB접속용 객체
	 * @param pi => 페이징바 처리 정보
	 * @return => 내용으로 검색된 리스트
	 */
	public ArrayList<FreeBoard> selectListAscContent(Connection conn, PageInfo pi, String inputValue) {
		ArrayList<FreeBoard> list = new ArrayList<>();
		PreparedStatement pstmt =null;
		ResultSet rset = null;
		String sql=prop.getProperty("selectListAscContent");
		
		try {
			pstmt = conn.prepareStatement(sql);
			int startRow = (pi.getCurrentPage()-1)*pi.getBoardLimit()+1;
			int endRow = startRow+pi.getBoardLimit()-1;
			
			
			pstmt.setString(1, "%"+inputValue+"%");
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				
				FreeBoard f = new FreeBoard();
				
				f.setFreeNo(rset.getInt("FREE_NO"));
				f.setFreeTitle(rset.getString("FREE_TITLE"));
				f.setFreeContent(rset.getString("FREE_CONTENT"));
				f.setFreeDate(rset.getDate("FREE_DATE"));
				f.setFreeCount(rset.getInt("FREE_COUNT"));
				f.setFreeStatus(rset.getString("FREE_STATUS"));
				f.setUserNo(rset.getInt("USER_NO"));
				f.setUserNickName(rset.getString("USER_NICKNAME"));
				f.setFreeCommentCount(rset.getInt("COMMENT_COUNT"));
				f.setFreeLikeCount(rset.getInt("LIKE_COUNT"));
				
				list.add(f);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}





	/**
	 * 2024.10.30 00:30 김형문
	 * 닉네임 검색용 쿼리문 실행 메소드
	 * @param conn => DB 접속용 객체
	 * @param pi => 페이징바 처리 정보
	 * @return => 닉네임으로 검색된 쿼리문 실행 메소드
	 */
	public ArrayList<FreeBoard> selectListAscWriter(Connection conn, PageInfo pi, String inputValue) {
		ArrayList<FreeBoard> list = new ArrayList<>();
		PreparedStatement pstmt =null;
		ResultSet rset = null;
		String sql=prop.getProperty("selectListAscWriter");
		
		try {
			pstmt = conn.prepareStatement(sql);
			int startRow = (pi.getCurrentPage()-1)*pi.getBoardLimit()+1;
			int endRow = startRow+pi.getBoardLimit()-1;
			
			pstmt.setString(1, "%"+inputValue+"%");
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				
				FreeBoard f = new FreeBoard();
				
				f.setFreeNo(rset.getInt("FREE_NO"));
				f.setFreeTitle(rset.getString("FREE_TITLE"));
				f.setFreeContent(rset.getString("FREE_CONTENT"));
				f.setFreeDate(rset.getDate("FREE_DATE"));
				f.setFreeCount(rset.getInt("FREE_COUNT"));
				f.setFreeStatus(rset.getString("FREE_STATUS"));
				f.setUserNo(rset.getInt("USER_NO"));
				f.setUserNickName(rset.getString("USER_NICKNAME"));
				f.setFreeCommentCount(rset.getInt("COMMENT_COUNT"));
				f.setFreeLikeCount(rset.getInt("LIKE_COUNT"));
				
				list.add(f);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}




	/**
	 * 2024.10.31 14:46 김형문
	 * 상세조회시 이전게시글 조회용 쿼리문 실행 메소드
	 * @param freeNo => 현재게시글
	 * @param searchOption => 검색된 결과인지 아닌지 체크
	 * @param inputValue => 리스트의 검색키워드
	 * @param sorting => 리스트의 정렬
	 * @return
	 */
	public FreeBoard selectPrevFile(Connection conn, int freeNo, String searchOption, String inputValue,
			String sorting) {
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		FreeBoard prevF = null;
		
		
					
		String sql ="SELECT * FROM ( "+
									"SELECT F.FREE_NO, " +
								             "FREE_TITLE, " +
								             "FREE_CONTENT, " +
								             "FREE_DATE, " +
								             "FREE_COUNT, " +
								             "FREE_STATUS, " +
								             "F.USER_NO, " +
								             "USER_NICKNAME, " +
								             "NVL(COMMENT_COUNT, 0) AS COMMENT_COUNT, " +
								             "NVL(LIKE_COUNT, 0) AS LIKE_COUNT " +
							             "FROM FREEBOARD F " +
							             "JOIN USER_INFO U ON (F.USER_NO = U.USER_NO) " +
							             "LEFT JOIN ( " +
								             "SELECT FREE_NO, COUNT(USER_NO) AS COMMENT_COUNT " +
								             "FROM FREEBOARD_COMMENT C " +
								             "WHERE FREE_COMM_STATUS = 'Y' " +
								             "GROUP BY FREE_NO " +
							             ") C ON (C.FREE_NO = F.FREE_NO) " +
							             "LEFT JOIN ( " +
								             "SELECT FREE_NO, COUNT(USER_NO) AS LIKE_COUNT " +
								             "FROM FREEBOARD_LIKE L " +
								             "GROUP BY FREE_NO " +
							             ") L ON (F.FREE_NO = L.FREE_NO) " +
							             "WHERE FREE_STATUS = 'Y' ";

		if (searchOption.equals("")) { // 검색리스트가 아닌 경우 
		    if (sorting.equals("desc")) { 
		        sql += " AND F.FREE_NO > ? " + // freeNo 자리
		        	   " ORDER BY FREE_NO ASC " +
		        		" ) "+
						" WHERE ROWNUM = 1 ";
					
		    } else {
		        sql += " AND F.FREE_NO < ? " + // freeNo 자리
		        		" ORDER BY FREE_NO DESC " +
		        		" ) "+
						" WHERE ROWNUM = 1 ";
		        
		    }
		} else { // 검색리스트인 경우 
		    if (sorting.equals("desc")) {
		        sql += " AND " + searchOption + " LIKE  ? " + 
		               " AND F.FREE_NO > ? " + 
		               " ORDER BY FREE_NO ASC "+
		               " ) "+
		               " WHERE ROWNUM = 1 ";
		    } else {
		        sql += " AND " + searchOption + " LIKE  ? " + 
		               " AND F.FREE_NO < ? " + 
		               " ORDER BY FREE_NO DESC "+
		        	   " ) "+
		               " WHERE ROWNUM = 1 " ;
		    }
		}
				
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			if (searchOption.equals("")) {
				pstmt.setInt(1, freeNo);
			} else {
				pstmt.setString(1, "%"+inputValue+"%");
				pstmt.setInt(2, freeNo);
			}
			rset = pstmt.executeQuery();
			
			if (rset.next()) {
				prevF = new FreeBoard();
				prevF.setFreeNo(rset.getInt("FREE_NO"));
				prevF.setFreeTitle(rset.getString("FREE_TITLE"));
				prevF.setFreeContent(rset.getString("FREE_CONTENT"));
				prevF.setFreeDate(rset.getDate("FREE_DATE"));
				prevF.setFreeCount(rset.getInt("FREE_COUNT"));
				prevF.setUserNickName(rset.getString("USER_NICKNAME"));
				prevF.setUserNo(rset.getInt("USER_NO"));
				prevF.setFreeLikeCount(rset.getInt("LIKE_COUNT"));
				prevF.setFreeCommentCount(rset.getInt("COMMENT_COUNT"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
				
		return prevF;
	}



	/**
	 * 2024.10.31 14:46 김형문
	 * 상세조회시 다음게시글 조회용 쿼리문 실행 메소드
	 * @param freeNo => 현재게시글
	 * @param searchOption => 검색된 결과인지 아닌지 체크
	 * @param inputValue => 리스트의 검색키워드
	 * @param sorting => 리스트의 정렬
	 * @return
	 */
	public FreeBoard selectNextFile(Connection conn, int freeNo, String searchOption, String inputValue,
			String sorting) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		FreeBoard nextF = null;
		
		String sql ="SELECT * FROM ( "+
								      "SELECT F.FREE_NO, " +
								             "FREE_TITLE, " +
								             "FREE_CONTENT, " +
								             "FREE_DATE, " +
								             "FREE_COUNT, " +
								             "FREE_STATUS, " +
								             "F.USER_NO, " +
								             "USER_NICKNAME, " +
								             "NVL(COMMENT_COUNT, 0) AS COMMENT_COUNT, " +
								             "NVL(LIKE_COUNT, 0) AS LIKE_COUNT " +
							             "FROM FREEBOARD F " +
							             "JOIN USER_INFO U ON (F.USER_NO = U.USER_NO) " +
							             "LEFT JOIN ( " +
								             "SELECT FREE_NO, COUNT(USER_NO) AS COMMENT_COUNT " +
								             "FROM FREEBOARD_COMMENT C " +
								             "WHERE FREE_COMM_STATUS = 'Y' " +
								             "GROUP BY FREE_NO " +
							             ") C ON (C.FREE_NO = F.FREE_NO) " +
							             "LEFT JOIN ( " +
								             "SELECT FREE_NO, COUNT(USER_NO) AS LIKE_COUNT " +
								             "FROM FREEBOARD_LIKE L " +
								             "GROUP BY FREE_NO " +
							             ") L ON (F.FREE_NO = L.FREE_NO) " +
							             "WHERE FREE_STATUS = 'Y' ";
		
		if (searchOption.equals("")) { // 검색리스트가 아닌 경우 
			if (sorting.equals("desc")) { 
			sql += " AND F.FREE_NO < ? " + // freeNo 자리
			   " ORDER BY FREE_NO DESC " +
				" ) "+
				" WHERE ROWNUM = 1 ";
			
			} else {
			sql += " AND F.FREE_NO > ? " + // freeNo 자리
				" ORDER BY FREE_NO ASC " +
				" ) "+
				" WHERE ROWNUM = 1 ";
			
			}
		} else { // 검색리스트인 경우 
			if (sorting.equals("desc")) {
			sql += " AND " + searchOption + " LIKE  ? " + 
			   " AND F.FREE_NO < ? " + 
			   " ORDER BY FREE_NO DESC "+
			   " ) "+
			   " WHERE ROWNUM = 1 ";
			} else {
			sql += " AND " + searchOption + " LIKE  ? " + 
			   " AND F.FREE_NO > ? " + 
			   " ORDER BY FREE_NO ASC "+
			   " ) "+
			   " WHERE ROWNUM = 1 " ;
			}
		}
		
		try {
			pstmt = conn.prepareStatement(sql);
			if (searchOption.equals("")) {
				pstmt.setInt(1, freeNo);
			} else {
				pstmt.setString(1, "%"+inputValue+"%");
				pstmt.setInt(2, freeNo);
			}
			rset = pstmt.executeQuery();
			
			if (rset.next()) {
				nextF = new FreeBoard();
				nextF.setFreeNo(rset.getInt("FREE_NO"));
				nextF.setFreeTitle(rset.getString("FREE_TITLE"));
				nextF.setFreeContent(rset.getString("FREE_CONTENT"));
				nextF.setFreeDate(rset.getDate("FREE_DATE"));
				nextF.setFreeCount(rset.getInt("FREE_COUNT"));
				nextF.setUserNickName(rset.getString("USER_NICKNAME"));
				nextF.setUserNo(rset.getInt("USER_NO"));
				nextF.setFreeLikeCount(rset.getInt("LIKE_COUNT"));
				nextF.setFreeCommentCount(rset.getInt("COMMENT_COUNT"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
				
		return nextF;
	}
	
	
	
	
////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	
	/**
	 * 자유게시판 파일 업로드 쿼리문 실행 메소드
	 * @param conn => DB접속용 객체
	 * @param list => 업로드할 파일들
	 * @return
	 */
	public int insertFreeBoardFileList(Connection conn, ArrayList<FreeBoardFile> list) {
		
		PreparedStatement pstmt = null;
		int result = 1;
		String sql = prop.getProperty("insertFreeBoardFileList");
		
		try {
			for(FreeBoardFile f : list) {
				
				
				
				pstmt=conn.prepareStatement(sql);
				
				pstmt.setString(1,f.getFreeFileName());
				pstmt.setString(2, f.getFreeFileRename());
				pstmt.setString(3, f.getFreeFilePath());
				
				result *= pstmt.executeUpdate(); 
			}
		
			
		
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	/**
	 * 2024.10.25 13:19 김형문
	 * @param conn=>DB접속용 객체
	 * @param f => 수정할 게시글의 정보
	 * @return
	 */
	public int updateFreeBoard(Connection conn, FreeBoard f) {

		int result = 0;
		
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("updateFreeBoard");
	
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, f.getFreeTitle());
			pstmt.setString(2, f.getFreeContent());
			pstmt.setInt(3, f.getFreeNo());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}



	
	
	
	
	




	public int updateFreeBoardFiles(Connection conn, ArrayList<FreeBoardFile> updateFiles) {
		
		
		int result = 1;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updateFreeBoardFileS");
		
		try {
			for(FreeBoardFile file : updateFiles) {
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, file.getFreeFileName());
				pstmt.setString(2, file.getFreeFileRename());
				pstmt.setInt(3,file.getFreeFileNo());
				
				
				result *= pstmt.executeUpdate();
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	/**
	 * 2024.10.25 14:10 김형문
	 * @param conn=> DB 접속용 객체
	 * @param files => 새로 첨부할 파일자료
	 * @return => 처리된 행의 갯수
	 */
	public int insertNewFreeBoardFiles(Connection conn, ArrayList<FreeBoardFile> insertFiles) {
		
		int result = 1;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertNewFreeBoardFiles");
		
		try {
			for(FreeBoardFile file : insertFiles) {
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, file.getFreeFileName());
				pstmt.setString(2, file.getFreeFileRename());
				pstmt.setString(3, file.getFreeFilePath());
				pstmt.setInt(4, file.getFreeNo());
					
				result *= pstmt.executeUpdate();
				//한번이라도 싪패하면 result=0
				
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
	}





	/**
	 * 2024.11.01 15:52 김형문
	 * 첨부파일 삭제용 쿼리문 실행 메소드 ( update)
	 * @param conn =>db 접속용 객체
	 * @param deleteFiles => 삭제할 첨부파일 번호
	 * @return => 처리 유뮤 (실패 0, 성공 1)
	 */
	public int deleteFreeBoardFiles(Connection conn, ArrayList<FreeBoardFile> deleteFiles) {

		PreparedStatement pstmt = null;
		
		int result = 1;
		
		String sql = prop.getProperty("deleteFreeBoardFiles");
		
		try {
			
			for(FreeBoardFile file : deleteFiles) {
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,file.getFreeFileNo());
				result *= pstmt.executeUpdate();
				
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
		
		
		
		
		
	}





	/**
	 * 관리자 페이지 전체 게시판 리스트 조회
	 * @param conn 
	 * @return => 전체 리스트
	 */
	public ArrayList<FreeBoard> adminSelctList(Connection conn, PageInfo pi) {

		ArrayList<FreeBoard> list = new ArrayList<>();
		
		String sql = prop.getProperty("adminSelctList");
		
		PreparedStatement pstmt = null;
		
		ResultSet rset = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			int startRow = (pi.getCurrentPage()-1)*pi.getBoardLimit()+1;
			int endRow = startRow+pi.getBoardLimit()-1;
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				FreeBoard f = new FreeBoard();
				
				f.setFreeNo(rset.getInt("FREE_NO"));
				f.setFreeTitle(rset.getString("FREE_TITLE"));
				f.setFreeContent(rset.getString("FREE_CONTENT"));
				f.setFreeDate(rset.getDate("FREE_DATE"));
				f.setFreeCount(rset.getInt("FREE_COUNT"));
				f.setFreeStatus(rset.getString("FREE_STATUS"));
				f.setUserNo(rset.getInt("USER_NO"));
				f.setFreeLikeCount(rset.getInt("LIKE_COUNT"));
				f.setFreeCommentCount(rset.getInt("COMMENT_COUNT"));
				f.setFileCount(rset.getInt("FILE_COUNT"));
				
				list.add(f);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return list;

	}




	

	/**
	 * 관리자 페이지 게시글 삭제 쿼리문 실행용 메소드
	 * @param conn=> DB접속용 객체
	 * @param freeNo => 삭제할 게시글 번호
	 * @return => 처리된 행의 갯수
	 */
	public int adminDeleteFreeBoard(Connection conn, int freeNo) {

		PreparedStatement pstmt = null;
		
		int result = 0;
		
		String sql = prop.getProperty("adminDeleteFreeBoard");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1,freeNo);
			
			result = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
		

		
	}




	/**
	 * 관리자 페이지 댓글 삭제 쿼리문 실행용 메소드
	 * @param conn=> DB접속용 객체
	 * @param freeNo => 삭제할 게시글 번호
	 * @return => 처리된 행의 갯수
	 */
	public int adminDeleteFreeBoardComment(Connection conn, int freeNo) {
		
		PreparedStatement pstmt = null;
		
		int result = 0;
		
		String sql = prop.getProperty("adminDeleteFreeBoardComment");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1,freeNo);
			
			result = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
		
	}




	/**
	 * 관리자 페이지 좋아요 삭제 쿼리문 실행용 메소드
	 * @param conn=> DB접속용 객체
	 * @param freeNo => 삭제할 게시글 번호
	 * @return => 처리된 행의 갯수
	 */
	public int adminDeleteFreeBoardLike(Connection conn, int freeNo) {
		PreparedStatement pstmt = null;
		
		int result = 0;
		
		String sql = prop.getProperty(" adminDeleteFreeBoardLike");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1,freeNo);
			
			result = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
		
	}
		





	/**
	 * 관리자 페이지 파일 삭제 쿼리문 실행용 메소드
	 * @param conn=> DB접속용 객체
	 * @param freeNo => 삭제할 게시글 번호
	 * @return => 처리된 행의 갯수
	 */
	public int adminDeleteFreeBoardFile(Connection conn, int freeNo) {
		PreparedStatement pstmt = null;
		
		int result = 0;
		
		String sql = prop.getProperty("adminDeleteFreeBoardFile");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1,freeNo);
			
			result = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return result;
		
	}
	

}





