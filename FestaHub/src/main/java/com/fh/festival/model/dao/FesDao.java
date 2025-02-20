package com.fh.festival.model.dao;

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
import com.fh.festival.model.vo.FestivalComment;
import com.fh.festival.model.vo.FestivalImage;
import com.fh.festival.model.vo.FestivalLike;

public class FesDao {
	
	private Properties prop = new Properties();
	
	public FesDao() {
		
		String fileName = FesDao.class.getResource("/sql/festival/fes-mapper.xml").getPath();
		
		try {
			prop.loadFromXML(new FileInputStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 공연정보 총 갯수를 구하는 쿼리문 실행 메소드
	 * @param conn => DB 접속용 객체
	 * @return => 총 게시글 갯수
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
	 * 공연정보 목록 조회용 쿼리문 실행 메소드
	 * @param conn => DB 접속용 객체
	 * @param pi => 구간별로 끊을 때 필요한 변수
	 * @return => 조회된 게시글들
	 */
	public ArrayList<Festival> selectList(Connection conn, PageInfo pi, int userNo) {
		
		// SELECT 문 => ResultSet (여러행)
		// => ArrayList
		
		// 1) 필요한 변수들 먼저 셋팅
		ArrayList<Festival> list = new ArrayList<>();
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
			
			pstmt.setInt(1, userNo);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			// 4, 5) 쿼리문 실행 후 결과 받기
			// select : executeQuery 메소드
			rset = pstmt.executeQuery();
			
			// 6) rset 으로부터 조회된 결과를 VO 로 옮겨담기
			while (rset.next()) {
			    Festival f = new Festival();

			    f.setFesNo(rset.getInt("FES_NO"));
			    f.setFesTitle(rset.getString("FES_TITLE"));
			    f.setFesDay(rset.getTimestamp("FES_DAY"));
			    f.setFesAddress(rset.getString("FES_ADDRESS"));
			    f.setFesDate(rset.getTimestamp("FES_DATE"));
			    f.setFesCount(rset.getInt("FES_COUNT"));
			    f.setFesWriter(rset.getString("USER_NICKNAME"));
			    f.setTitleImg(rset.getString("TITLEIMG"));
			    f.setCommCount(rset.getInt("COMM_COUNT"));  // 댓글 수 설정
			    f.setLikeCount(rset.getInt("LIKE_COUNT"));
			    f.setLiked(rset.getInt("IS_LIKED") > 0);
			    
			    list.add(f);
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
	 * 공연정보 조회수 증가용 쿼리문 실행 메소드
	 * @param conn
	 * @param fesNo => 게시글 번호
	 * @return => 행의 갯수
	 */
	public int increaseCount(Connection conn, int fesNo) {
		
		// 1) 필요한 변수들 먼저 셋팅
		int result = 0;
		PreparedStatement pstmt = null;
		
		// 실행할 SQL 문
		String sql = prop.getProperty("increaseCount");
		
		try {
			
			// 2) PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// 3) 미완성된 SQL 문 완성시키기
			pstmt.setInt(1, fesNo);
			
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
	 * 공연정보 상세조회용 쿼리문 실행 메소드
	 * @param conn
	 * @param fesNo => 게시글 번호
	 * @return => 게시글 한개의 내용
	 */
	public Festival selectFes(Connection conn, int fesNo) {
		
		// SELECT 문 => ResultSet (단일행)
		// => Board
		
		// 1) 필요한 변수들 먼저 셋팅
		Festival f = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		// 실행할 SQL 문 
		String sql = prop.getProperty("selectFes");
		
		try {
			
			// 2) PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// 3) 미완성된 SQL 문 완성시키기
			pstmt.setInt(1, fesNo);
			
			// 4, 5) 쿼리문 실행 후 결과 받기
			// select : executeQuery 메소드
			rset = pstmt.executeQuery();
			
			// 6) rset 으로 부터 조회된 데이터를 뽑아서
			//    VO 로 옮겨담기
			if(rset.next()) {

				f = new Festival();
				
				f.setFesNo(rset.getInt("FES_NO"));
				f.setFesTitle(rset.getString("FES_TITLE"));
				f.setFesContent(rset.getString("FES_CONTENT"));
				f.setFesDay(rset.getTimestamp("FES_DAY"));
				f.setFesAddress(rset.getString("FES_ADDRESS"));
				f.setFesDate(rset.getTimestamp("FES_DATE"));
				f.setFesCount(rset.getInt("FES_COUNT"));
				f.setFesWriter(rset.getString("USER_NICKNAME"));
				f.setWriterId(rset.getString("USER_ID"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 7) 다 쓴 JDBC 용 자원 반납 (역순)
			close(rset);
			close(pstmt);
		}
		
		// 8) Service 로 결과 반환
		return f;
	}

	/**
	 * 공연정보에 첨부된 이미지들 조회용 쿼리문 실행 메소드
	 * @param conn
	 * @param fesNo
	 * @return
	 */
	public ArrayList<FestivalImage> selectImgList(Connection conn, int fesNo) {
		
		ArrayList<FestivalImage> fiList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectImg");
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, fesNo);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				
				FestivalImage fi = new FestivalImage();
				
				// 파일번호, 수정명, 경로
				fi.setFesImgNo(rset.getInt("FES_IMG_NO"));
				fi.setFesImgName(rset.getString("FES_IMG_NAME"));
				fi.setFesImgRename(rset.getString("FES_IMG_RENAME"));
				fi.setFesImgPath(rset.getString("FES_IMG_PATH"));
				fi.setFesImgThumb(rset.getString("FES_IMG_THUMB"));
				
				fiList.add(fi);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			close(rset);
			close(pstmt);
		}
		
		return fiList;
	}

	/**
	 * 공연정보 등록용 쿼리문 실행 메소드
	 * @param conn
	 * @param f => 공연정보 내용
	 * @return => 처리된 행의 갯수
	 */
	public int insertFes(Connection conn, Festival f) {
		
		// INSERT 문 => int (처리된 행의 갯수)
		
		// 1) 필요한 변수들 먼저 셋팅
		int result = 0;
		PreparedStatement pstmt = null;
		
		// 실행할 SQL 문
		String sql = prop.getProperty("insertFes");
		
		try {
			
			// 2) PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			// 3) 미완성된 SQL 문 완성시키기
			pstmt.setString(1, f.getFesTitle());
			pstmt.setString(2, f.getFesContent());
			pstmt.setTimestamp(3, f.getFesDay());
			pstmt.setString(4, f.getFesAddress());
			pstmt.setInt(5, Integer.parseInt(f.getFesWriter()));
			
			// 4, 5) 쿼리문 실행 후 결과 받기
			// insert : executeUpdate 메소드
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 6) 다 쓴 JDBC 용 자원 반납 (역순)
			close(pstmt);
		}
		
		// 7) Service 로 결과 반환
		return result;
	}

	/**
	 * 공연정보 첨부이미지 등록용 쿼리문 실행 메소드
	 * @param conn
	 * @param fiList => 첨부파일들
	 * @return => 처리된 행의 갯수
	 */
	public int insertImgList(Connection conn, ArrayList<FestivalImage> fiList) {

		// 넘어온 첨부파일의 갯수만큼 INSERT 여러번 실행
		
		// 1) 필요한 변수들 먼저 셋팅
		int result = 1;
		// > insert를 반복해서 진행 후 
		//   result에 처리된 행의 갯수를 계속 누적해서 곱하기
		
		PreparedStatement pstmt = null;
		
		// 실행할 SQL 문
		String sql = prop.getProperty("insertImgList");
		
		try {
			// 2 ~ 5) 를 반복해서 진행할 것
			// > list 의 size 만큼 반복
			for (FestivalImage fi : fiList) {
				
				// 2) PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				
				// 3) 미완성된 SQL 문 완성시키기
				pstmt.setString(1, fi.getFesImgName());
				pstmt.setString(2, fi.getFesImgRename());
				pstmt.setString(3, fi.getFesImgPath());
				pstmt.setString(4, fi.getFesImgThumb());
				
				// 4, 5) 쿼리문 실행 후 결과 받기
				// insert : executeUpdate 메소드
				// result = result * pstmt.executeUpdate();
				result *= pstmt.executeUpdate();
				// > 하나라도 실패할 경우 0
				
			}
					
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			// 6) 다 쓴 JDBC 용 자원 반납 (역순)
			close(pstmt);
			
		}
		
		// 7) Service 로 결과 반환
		return result;
	
	}

	/**
	 * 공연정보 삭제용 쿼리문 실행 메소드
	 * @param conn
	 * @param fesNo => 삭제할 게시글 번호
	 * @return => 처리된 행의 갯수
	 */
	public int deleteFes(Connection conn, int fesNo) {
		
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("deleteFes");
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, fesNo);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			close(pstmt);
		}
		
		return result;
	}

	/**
	 * 공연정보 수정용 쿼리문 실행 메소드
	 * @param conn
	 * @param f => 수정할 게시글 정보
	 * @return => 처리된 행의 갯수
	 */
	public int updateFes(Connection conn, Festival f) {
		
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("updateFes");
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, f.getFesTitle());
			pstmt.setString(2, f.getFesContent());
			pstmt.setTimestamp(3, f.getFesDay());
			pstmt.setString(4, f.getFesAddress());
			pstmt.setInt(5, f.getFesNo());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			close(pstmt);
		}
		
		return result;
		
	}

	/**
	 * 공연정보 첨부이미지 수정용 쿼리문 실행 메소드
	 * @param conn
	 * @param newList => 신규 추가 이미지들
	 * @return => 처리된 행의 갯수
	 */
	public int updateImgList(Connection conn, ArrayList<FestivalImage> newList) {
		
		int result = 1;
		
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("updateImgList");
		
		try {
			for (FestivalImage fi : newList) {
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, fi.getFesImgName());
				pstmt.setString(2, fi.getFesImgRename());
				pstmt.setString(3, fi.getFesImgPath());
				pstmt.setString(4, fi.getFesImgThumb());
				pstmt.setInt(5, fi.getFesNo());
				
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
	 * 공연정보 기존 첨부이미지 제거용 서비스 메소드 
	 * @param conn
	 * @param originList => 기존 첨부이미지들
	 * @return => 처리된 행의 갯수
	 */
	public int deleteImgList(Connection conn, ArrayList<FestivalImage> originList) {
		
		int result = 1;
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("deleteImgList");
		
		try {
			
			for (FestivalImage fi : originList) {
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, fi.getFesImgNo());
				
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
	 * 댓글 작성용 쿼리문 실행 메소드
	 * @param conn
	 * @param fc => 댓글 정보
	 * @return => 처리된 행의 갯수
	 */
	public int insertComm(Connection conn, FestivalComment fc) {
		
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("insertComm");
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, fc.getFesCommContent());
			pstmt.setInt(2, fc.getFesNo());
			pstmt.setInt(3, Integer.parseInt(fc.getFesCommWriter()));
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			close(pstmt);
		}
		
		return result;
	}

	/**
	 * 댓글리스트 조회용 쿼리문 실행 메소드
	 * @param conn
	 * @param fesNo => 댓글 조회할 게시글 번호
	 * @return => 조회된 댓글리스트
	 */
	public ArrayList<FestivalComment> selectCommList(Connection conn, int fesNo) {
		
		ArrayList<FestivalComment> fcList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectCommList");
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, fesNo);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				
				FestivalComment fc = new FestivalComment();
				fc.setFesCommNo(rset.getInt("FES_COMM_NO"));
				fc.setFesCommContent(rset.getString("FES_COMM_CONTENT"));
				fc.setFesCommDate(rset.getTimestamp("FES_COMM_DATE"));
				fc.setFesCommWriter(rset.getString("USER_NICKNAME"));
				fc.setCommWriterId(rset.getString("USER_ID"));
				
				fcList.add(fc);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			close(rset);
			close(pstmt);
		}
		
		return fcList;
	}

	/**
	 * 댓글 수정용 쿼리문 실행 메소드
	 * @param conn
	 * @param fesCommNo => 수정할 댓글 번호
	 * @param fesCommContent => 수정할 댓글 내용
	 * @return => 처리된 행의 갯수
	 */
	public int updateComm(Connection conn, int fesCommNo, String fesCommContent) {
		
        int result = 0;
		PreparedStatement pstmt = null;
        String sql = prop.getProperty("updateComm");
        
        try {
        	
        	pstmt = conn.prepareStatement(sql);
        			
            pstmt.setString(1, fesCommContent);
            pstmt.setInt(2, fesCommNo);
            
            result = pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	
			close(pstmt);
        }
        
        return result;
	}

	/**
	 * 댓글 삭제용 쿼리문 실행 메소드
	 * @param conn
	 * @param fesCommNo
	 * @return
	 */
	public int deleteComm(Connection conn, int fesCommNo) {
		
        int result = 0;
		PreparedStatement pstmt = null;
        String sql = prop.getProperty("deleteComm");
        
        try {
        	
        	pstmt = conn.prepareStatement(sql);
        			
            pstmt.setInt(1, fesCommNo);
            
            result = pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	
			close(pstmt);
        }
        
        return result;
		
	}

	/**
	 * 좋아요 정보 조회용 쿼리문 실행 메소드
	 * @param conn
	 * @param fesNo => 게시글 번호
	 * @param userNo => 유저번호
	 * @return => 조회된 좋아요 정보
	 */
	public FestivalLike selectLikeInfo(Connection conn, int fesNo, int userNo) {
		
	    FestivalLike fl = null;
	    PreparedStatement pstmt = null;
	    ResultSet rset = null;
        String sql = prop.getProperty("selectLikeInfo");
        
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userNo);
            pstmt.setInt(2, fesNo);
            rset = pstmt.executeQuery();

            if (rset.next()) {
                boolean isLiked = rset.getInt("IS_LIKED") > 0;
                int likeCount = rset.getInt("LIKE_COUNT");

                fl = new FestivalLike(fesNo, userNo, isLiked, likeCount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rset);
            close(pstmt);
        }
        return fl;
	}

	/**
	 * 좋아요 추가용 쿼리문 실행 메소드
	 * @param conn
	 * @param fesNo => 게시글번호
	 * @param userNo => 유저번호
	 * @return => 처리된 행의 갯수
	 */
	public int likeFes(Connection conn, int fesNo, int userNo) {
		
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("likeFes");

        try {
        	pstmt =  conn.prepareStatement(sql);
        	
            pstmt.setInt(1, fesNo);
            pstmt.setInt(2, userNo);
            
            result = pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
			
			close(pstmt);
		}
        
        return result;
		
	}
	
	
	/**
	 * 좋아요 취소용 쿼리문 실행 메소드
	 * @param conn 
	 * @param fesNo => 게시글번호
	 * @param userNo => 유저번호
	 * @return => 처리된 행의 갯수
	 */
	public int unlikeFes(Connection conn, int fesNo, int userNo) {
		
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("unlikeFes");

        try {
        	pstmt =  conn.prepareStatement(sql);
        	
            pstmt.setInt(1, fesNo);
            pstmt.setInt(2, userNo);
            
            result = pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
			
			close(pstmt);
		}
        
        return result;
	}

	/**
	 * 이전글 조회용 쿼리문 실행 메소드
	 * @param conn
	 * @param fesNo => 타겟 게시글 번호
	 * @return => 이전글 정보
	 */
	public Festival getPrevFes(Connection conn, int fesNo) {
		
		Festival prevFes = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("prevFes");
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, fesNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {

				prevFes = new Festival();
				
				prevFes.setFesNo(rset.getInt("FES_NO"));
				prevFes.setFesTitle(rset.getString("FES_TITLE"));
				prevFes.setFesDay(rset.getTimestamp("FES_DAY"));
				prevFes.setFesAddress(rset.getString("FES_ADDRESS"));
				prevFes.setFesDate(rset.getTimestamp("FES_DATE"));
				prevFes.setFesCount(rset.getInt("FES_COUNT"));
				prevFes.setFesWriter(rset.getString("USER_NICKNAME"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			close(rset);
			close(pstmt);
		}
		
		return prevFes;
	}

	/**
	 * 다음글 조회용 쿼리문 실행 메소드
	 * @param conn
	 * @param fesNo => 타겟 게시글 번호
	 * @return => 다음글 정보
	 */
	public Festival getNextFes(Connection conn, int fesNo) {
		
		Festival nextFes = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("nextFes");
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, fesNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {

				nextFes = new Festival();
				
				nextFes.setFesNo(rset.getInt("FES_NO"));
				nextFes.setFesTitle(rset.getString("FES_TITLE"));
				nextFes.setFesDay(rset.getTimestamp("FES_DAY"));
				nextFes.setFesAddress(rset.getString("FES_ADDRESS"));
				nextFes.setFesDate(rset.getTimestamp("FES_DATE"));
				nextFes.setFesCount(rset.getInt("FES_COUNT"));
				nextFes.setFesWriter(rset.getString("USER_NICKNAME"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			close(rset);
			close(pstmt);
		}
		
		return nextFes;
	}

	/**
	 * 게시글 검색용 쿼리문 실행 메소드
	 * @param conn
	 * @param category => 검색할 카테고리
	 * @param keyword => 검색할 키워드
	 * @param pi => 페이지 정보
	 * @return => 검색된 게시글 리스트
	 */
	public ArrayList<Festival> searchFes(Connection conn, String category, 
										 String keyword, PageInfo pi, int userNo) {
		
	    PreparedStatement pstmt = null;
	    ResultSet rset = null;
	    ArrayList<Festival> list = new ArrayList<>();
	    
	    String sql = "SELECT * FROM ("
	                 + "SELECT ROWNUM RNUM, A.* "
	                 + "FROM ("
	                 + "SELECT F.FES_NO, F.FES_TITLE, F.FES_DAY, F.FES_ADDRESS, F.FES_DATE, F.FES_COUNT, "
	                 + "UI.USER_NICKNAME, FI.FES_IMG_PATH || FI.FES_IMG_RENAME AS TITLEIMG, "
	                 + "(SELECT COUNT(*) FROM FESTIVAL_LIKE WHERE FES_NO = F.FES_NO) AS LIKE_COUNT, "
	                 + "(SELECT COUNT(*) FROM FESTIVAL_COMMENT WHERE FES_NO = F.FES_NO AND FES_COMM_STATUS = 'Y') AS COMM_COUNT, "
	                 + "(SELECT COUNT(*) FROM FESTIVAL_LIKE WHERE FES_NO = F.FES_NO AND USER_NO = ?) AS IS_LIKED "
	                 + "FROM FESTIVAL F "
	                 + "JOIN USER_INFO UI ON F.USER_NO = UI.USER_NO "
	                 + "LEFT JOIN FESTIVAL_IMAGE FI ON F.FES_NO = FI.FES_NO AND FI.FES_IMG_THUMB = 'Y' "
	                 + "WHERE F.FES_STATUS = 'Y' AND " + category + " LIKE ? " // 동적 컬럼명 삽입
	                 + "ORDER BY F.FES_NO DESC) A "
	                 + ") "
	                 + "WHERE RNUM BETWEEN ? AND ?";

	    try {
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, userNo);
	        pstmt.setString(2, "%" + keyword + "%");
	        pstmt.setInt(3, (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1);
	        pstmt.setInt(4, pi.getCurrentPage() * pi.getBoardLimit());

	        rset = pstmt.executeQuery();
	        
	        while (rset.next()) {
	            Festival f = new Festival();
	            f.setFesNo(rset.getInt("FES_NO"));
	            f.setFesTitle(rset.getString("FES_TITLE"));
	            f.setFesDay(rset.getTimestamp("FES_DAY"));
	            f.setFesAddress(rset.getString("FES_ADDRESS"));
	            f.setFesDate(rset.getTimestamp("FES_DATE"));
	            f.setFesCount(rset.getInt("FES_COUNT"));
	            f.setFesWriter(rset.getString("USER_NICKNAME"));
	            f.setTitleImg(rset.getString("TITLEIMG"));
	            f.setLikeCount(rset.getInt("LIKE_COUNT"));
	            f.setCommCount(rset.getInt("COMM_COUNT"));
	            f.setLiked(rset.getInt("IS_LIKED") > 0);  // 좋아요 여부 boolean 처리
	            list.add(f);
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
	 * 검색 게시글 카운트용 쿼리문 실행 메소드
	 * @param conn
	 * @param category => 검색할 카테고리
	 * @param keyword => 검색할 키워드
	 * @return => 검색된 게시글 수
	 */
	public int selectSearchCount(Connection conn, String category, String keyword) {
		
		PreparedStatement pstmt = null;
	    ResultSet rset = null;
	    int listCount = 0;

	    String sql = "SELECT COUNT(*) FROM FESTIVAL WHERE FES_STATUS = 'Y' AND " + category + " LIKE ?";

	    try {
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, "%" + keyword + "%");
	        rset = pstmt.executeQuery();

	        if (rset.next()) {
	            listCount = rset.getInt(1);
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
	 * 관리자페이지 전체글 조회용 쿼리문 실행 메소드
	 * @param conn
	 * @param userNo
	 * @return
	 */
	public ArrayList<Festival> adminSelectList(Connection conn, int userNo) {
		
		ArrayList<Festival> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("adminSelectList");
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, userNo);
			
			rset = pstmt.executeQuery();
			
			while (rset.next()) {
			    Festival f = new Festival();

			    f.setFesNo(rset.getInt("FES_NO"));
			    f.setFesTitle(rset.getString("FES_TITLE"));
			    f.setFesDay(rset.getTimestamp("FES_DAY"));
			    f.setFesAddress(rset.getString("FES_ADDRESS"));
			    f.setFesContent(rset.getString("FES_CONTENT"));
			    f.setFesDate(rset.getTimestamp("FES_DATE"));
			    f.setFesCount(rset.getInt("FES_COUNT"));
			    f.setFesStatus(rset.getString("FES_STATUS"));
			    f.setWriterId(rset.getString("USER_ID"));
			    f.setFesWriter(rset.getString("USER_NICKNAME"));
			    f.setTitleImg(rset.getString("TITLEIMG"));
			    f.setCommCount(rset.getInt("COMM_COUNT"));  // 댓글 수 설정
			    f.setLikeCount(rset.getInt("LIKE_COUNT"));
			    f.setLiked(rset.getInt("IS_LIKED") > 0);
			    
			    list.add(f);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}

	public ArrayList<FestivalImage> getAdminImgList(Connection conn, int fesNo) {
		
		ArrayList<FestivalImage> fiList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("getAdminImgList");
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, fesNo);
			
			rset = pstmt.executeQuery();
			
			while (rset.next()) {
			    FestivalImage fi = new FestivalImage();

			    fi.setFesImgNo(rset.getInt("FES_IMG_NO"));
			    fi.setFesImgName(rset.getString("FES_IMG_NAME"));
			    fi.setFesImgRename(rset.getString("FES_IMG_RENAME"));
			    fi.setFesImgPath(rset.getString("FES_IMG_PATH"));
			    fi.setFesImgThumb(rset.getString("FES_IMG_THUMB"));
			    fi.setFesImgStatus(rset.getString("FES_IMG_STATUS"));
			    
			    fiList.add(fi);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return fiList;
	}

	/**
	 * 관리자페이지 게시글 상태 전환용 쿼리문 실행 메소드
	 * @param conn
	 * @param fesNo => 전환할 게시글
	 * @param newStatus => 입력할 상태값
	 * @return => 처리된 행의 갯수
	 */
	public int toggleStatus(Connection conn, int fesNo, String newStatus) {
		
	    PreparedStatement pstmt = null;
	    int result = 0;
		String sql = prop.getProperty("toggleStatus");

	    try {
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, newStatus);
	        pstmt.setInt(2, fesNo);
	        result = pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close(pstmt);
	    }

	    return result;
	}

	/**
	 * 게시글 이미지 삭제용 쿼리문 실행 메소드
	 * @param conn
	 * @param fesImgNo
	 * @return
	 */
	public int deleteImg(Connection conn, int fesImgNo) {
	    PreparedStatement pstmt = null;
        int result = 0;
        String sql = prop.getProperty("deleteImg");
	    try {
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, fesImgNo);
	        result = pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close(pstmt);
	    }

        return result;
	}

	/**
	 * 관리자페이지 게시글 테이블 삭제용 쿼리문 실행 메소드
	 * @param conn
	 * @param fesNo
	 * @return
	 */
	public int deleteTable(Connection conn, int fesNo) {
		
	    PreparedStatement pstmt = null;
	    int result = 0;
		String sql = prop.getProperty("deleteTable");

	    try {
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, fesNo);
	        result = pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close(pstmt);
	    }

	    return result;
	}

}
