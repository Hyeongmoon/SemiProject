package com.fh.user.model.dao;

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
import com.fh.festival.model.vo.Festival;
import com.fh.festival.model.vo.FestivalComment;
import com.fh.freeBoard.model.vo.FreeBoard;
import com.fh.freeBoard.model.vo.FreeBoardComment;
import com.fh.reviewBoard.model.vo.Review;
import com.fh.reviewBoard.model.vo.ReviewComment;
import com.fh.user.model.vo.User;

public class UserDao {
	
	private Properties prop = new Properties();
	
	public UserDao() {
		String path = "/sql/user/user-mapper.xml";
		
		String fileName = UserDao.class
							.getResource(path).getPath();
		
		try {
			prop.loadFromXML(new FileInputStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 회원가입 쿼리문 실행용
	 * @param conn => DB 접속용 객체
	 * @param u => 회원가입할 회원의 정보
	 * @return => 처리된 행의 갯수
	 */
	public int insertUser(Connection conn, User u) {
		
		int result = 0;
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("insertUser");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, u.getUserId());
			pstmt.setString(2, u.getUserPwd());
			pstmt.setString(3, u.getUserNickname());
			pstmt.setString(4, u.getUserName());
			pstmt.setString(5, u.getEmail());
			pstmt.setDate(6, u.getBirthDate());
			pstmt.setString(7, u.getPhone());
			pstmt.setString(8, u.getAddress());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
		
	}

	// 로그인용 쿼리문 실행용
	public User loginUser(Connection conn, User u) {
		User loginUser = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("loginUser");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, u.getUserId());
			pstmt.setString(2, u.getUserPwd());
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				
				loginUser = new User(rset.getInt("USER_NO"),
								     rset.getString("USER_ID"),
								     rset.getString("USER_PWD"),
								     rset.getString("USER_NICKNAME"),
								     rset.getString("USER_NAME"),
								     rset.getDate("BIRTHDATE"),
								     rset.getString("PHONE"),
								     rset.getString("EMAIL"),
								     rset.getString("ADDRESS"),
								     rset.getDate("BLACKLIST_REG"),
								     rset.getString("BLACKLIST_RFR"),
								     rset.getDate("REGDATE"),
								     rset.getDate("UPDATE_DATE"),
								     rset.getString("STATUS"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return loginUser;
	}

	//아이디 중복 체크용 쿼리문 실행 메소드 
	public int idcheck(Connection conn, String checkId) {
		int count = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("idCheck");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, checkId);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				count = rset.getInt("COUNT(*)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return count;
	}

	// 비밀번호 확인용 쿼리문 실행 메소드
	public int pwdcheck(Connection conn, String checkPwd) {
		int count = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("pwdCheck");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, checkPwd);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				count = rset.getInt("COUNT(*)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return count;
	}

	// 회원 한명의 정보 조회용 쿼리문 실행 메소드
	public User selectUser(Connection conn, String userId) {
		User u = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectUser");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				u = new User();
				u.setUserNo(rset.getInt("USER_NO"));
				u.setUserId(rset.getString("USER_ID"));
				u.setUserPwd(rset.getString("USER_PWD"));
				u.setUserNickname(rset.getString("USER_NICKNAME"));
				u.setUserName(rset.getString("USER_NAME"));
				u.setEmail(rset.getString("EMAIL"));
				u.setBirthDate(rset.getDate("BIRTHDATE"));
				u.setPhone(rset.getString("PHONE"));
				u.setAddress(rset.getString("ADDRESS"));
				u.setUpdateDate(rset.getDate("BLACKLIST_REG"));
				u.setStatus(rset.getString("BLACKLIST_RFR"));
				u.setRegDate(rset.getDate("REGDATE"));
				u.setUpdateDate(rset.getDate("UPDATE_DATE"));
				u.setStatus(rset.getString("STATUS"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return u;
	}

	// 회원정보 수정용 쿼리문 실행 메소드
	public int updateUser(Connection conn, User u) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updateUser");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, u.getUserPwd());
			pstmt.setString(2, u.getUserNickname());
			pstmt.setString(3, u.getUserName());
			pstmt.setString(4, u.getPhone());
			pstmt.setString(5, u.getEmail());
			pstmt.setString(6, u.getAddress());
			pstmt.setString(7, u.getUserId());
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

	// 회원탈퇴용 쿼리문 실행 메소드
	public int deleteUser(Connection conn, User u) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("deleteUser");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, u.getUserId());
			pstmt.setString(2, u.getUserPwd());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}
	// 회원가입 필수입력사항 확인 쿼리문 실행 메소드 - 아이디
	public int fcIdcheck(Connection conn, String fcId) {
		int count = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("fcIdcheck");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, fcId);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				count = rset.getInt("COUNT(*)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return count;
	}
	// 회원가입 필수입력사항 확인 쿼리문 실행 메소드 - 비밀번호
	public int fcPwdcheck1(Connection conn, String fcPwd1) {
		int count = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("fcPwdcheck1");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, fcPwd1);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				count = rset.getInt("COUNT(*)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return count;
	}
	// 회원가입 필수입력사항 확인 쿼리문 실행 메소드 - 닉네임
	public int fcNicknamecheck(Connection conn, String fcNickname) {
		int count = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("fcNicknamecheck");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, fcNickname);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				count = rset.getInt("COUNT(*)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return count;
	}

	// 회원가입 필수입력사항 확인 쿼리문 실행 메소드 - 이름
	public int fcNamecheck(Connection conn, String fcName) {
		int count = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("fcNamecheck");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, fcName);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				count = rset.getInt("COUNT(*)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return count;
	}

	// 닉네임 중복 확인용 쿼리문 실행 메소드
	public int nicknameCheck(Connection conn, String checkNickname) {
	    int count = 0;
	    PreparedStatement pstmt = null;
	    ResultSet rset = null;

	    // 프로퍼티 파일에서 닉네임 체크 쿼리문을 가져옵니다.
	    String sql = prop.getProperty("nicknameCheck"); // 쿼리문 이름을 변경해야 함

	    try {
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, checkNickname); // 닉네임을 설정합니다.
	        rset = pstmt.executeQuery();

	        if (rset.next()) {
	            count = rset.getInt("COUNT(*)"); // COUNT 값을 가져옵니다.
	        }
	    } catch (SQLException e) {
	        e.printStackTrace(); // SQL 예외 처리
	    } finally {
	        close(rset); // ResultSet 정리
	        close(pstmt); // PreparedStatement 정리
	    }
	    return count; // 닉네임 중복 체크 결과 반환
	}

	// 마이페이지 내가 찜한 자유게시판 총 갯수 구하는 쿼리문 실행 메소드
	public int selectDibFreeCount(Connection conn, int userNo) {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectDibFreeCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userNo);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				listCount = rset.getInt("COUNT");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return listCount;
	}
	// 마이페이지 내가 찜한 게시물 목록 조회용 쿼리문 실행 메소드 (자유게시판)
	public ArrayList<FreeBoard> selectDibFree(Connection conn, PageInfo pi, int userNo) {
		ArrayList<FreeBoard> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectDibFree");
		
		try {
			pstmt = conn.prepareStatement(sql);
			int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
			int endRow = startRow + pi.getBoardLimit() - 1;
			
			pstmt.setInt(1, userNo);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			while(rset.next()) {
				FreeBoard f = new FreeBoard();
				f.setFreeNo(rset.getInt("FREE_NO")); 
				f.setFreeTitle(rset.getString("FREE_TITLE")); 
				f.setFreeDate(rset.getDate("FREE_DATE"));
									 
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
	
	// 마이페이지 내가 찜한 공연후기 게시판 총 갯수 구하는 쿼리문 실행 메소드
	public int selectDibRvCount(Connection conn, int userNo) {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectDibRvCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userNo);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				listCount = rset.getInt("COUNT");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return listCount;
	}

	// 마이페이지 내가 찜한 게시물 목록 조회용 쿼리문 실행 메소드 (공연후기 게시판)
	public ArrayList<Review> selectDibRv(Connection conn, PageInfo pi, int userNo) {
		ArrayList<Review> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectDibRv");
		
		try {
			pstmt = conn.prepareStatement(sql);
			int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
			int endRow = startRow + pi.getBoardLimit() - 1;
			
			pstmt.setInt(1, userNo);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			while(rset.next()) {
				Review rv = new Review(); 
				rv.setRvNo(rset.getInt("RV_NO"));
				rv.setRvTitle(rset.getString("RV_TITLE"));
				rv.setRvDate(rset.getDate("RV_DATE"));
				
				list.add(rv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}

	// 마이페이지 내가 찜한 동행구하기게시물 총 갯수 구하는 쿼리문 실행 메소드
	public int selectDibAccompanyCount(Connection conn,int userNo) {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectDibAccompanyCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userNo);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				listCount = rset.getInt("COUNT");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return listCount;
	}

	// 마이페이지 내가 찜한 게시물 목록 조회용 쿼리문 실행 메소드 (동행구하기 게시판)
	public ArrayList<Accompany> selectDibAccompany(Connection conn, PageInfo pi, int userNo) {
		ArrayList<Accompany> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectDibAccompany");
		
		try {
			pstmt = conn.prepareStatement(sql);
			int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
			int endRow = startRow + pi.getBoardLimit() - 1;
			
			pstmt.setInt(1, userNo);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			while(rset.next()) {
				Accompany ac = new Accompany();
				ac.setAccomNo(rset.getInt("ACCOM_NO"));
				ac.setAccomTitle(rset.getString("ACCOM_TITLE"));
				ac.setAccomDate(rset.getDate("ACCOM_DATE"));
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

	// 마이페이지 내가 찜한 공연정보 게시물 총 갯수 구하는 쿼리문 실행 메소드
	public int selectDibFesCount(Connection conn, int userNo) {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectDibFesCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userNo);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				listCount = rset.getInt("COUNT");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return listCount;
	}

	// 마이페이지 내가 찜한 게시물 목록 조회용 쿼리문 실행 메소드 (공연정보 게시판)
	public ArrayList<Festival> selectDibFes(Connection conn, PageInfo pi, int userNo) {
		ArrayList<Festival> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectDibFes");
		
		try {
			pstmt = conn.prepareStatement(sql);
			int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
			int endRow = startRow + pi.getBoardLimit() - 1;
			
			pstmt.setInt(1, userNo);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			while(rset.next()) {
				Festival fes = new Festival();
				fes.setFesNo(rset.getInt("FES_NO"));
				fes.setFesTitle(rset.getString("FES_TITLE"));
				fes.setFesDate(rset.getTimestamp("FES_DATE"));
				
				list.add(fes);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}

	// 마이페이지 내가 쓴 자유게시판 게시물 총 갯수 구하는 쿼리문 실행 메소드
	public int selectWrFreeCount(Connection conn, int userNo) {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectWrFreeCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userNo);
			
			rset = pstmt.executeQuery();
			if(rset.next()) {
				listCount = rset.getInt("COUNT");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return listCount;
	}

	// 마이페이지 내가 쓴 게시물 목록 조회용 쿼리문 실행 메소드 (자유게시판)
	public ArrayList<FreeBoard> selectWrFree(Connection conn, PageInfo pi, int userNo) {
		ArrayList<FreeBoard> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectWrFree");
		
		try {
			pstmt = conn.prepareStatement(sql);
			int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
			int endRow = startRow + pi.getBoardLimit() - 1;
			
			pstmt.setInt(1, userNo);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			while(rset.next()) {
				FreeBoard fb = new FreeBoard();
				fb.setFreeNo(rset.getInt("FREE_NO"));
				fb.setFreeTitle(rset.getString("FREE_TITLE"));					 
				fb.setFreeDate(rset.getDate("FREE_DATE")); 
			
				list.add(fb);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}

	// 마이페이지 내가 쓴 동행구하기 게시물 총 갯수 구하는 쿼리문 실행 메소드
	public int selectWrAcCount(Connection conn, int userNo) {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectWrAcCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userNo);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				listCount = rset.getInt("COUNT");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return listCount;
	}

	// 마이페이지 내가 쓴 동행구하기 게시물 목록 조회용 쿼리문 실행 메소드 (동행구하기)
	public ArrayList<Accompany> selectWrAc(Connection conn, PageInfo pi, int userNo) {
		ArrayList<Accompany> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectWrAc");
		
		try {
			pstmt = conn.prepareStatement(sql);
			int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
			int endRow = startRow + pi.getBoardLimit() - 1;
			
			pstmt.setInt(1, userNo);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			while(rset.next()) {
				Accompany ac = new Accompany();
				ac.setAccomNo(rset.getInt("ACCOM_NO"));
				ac.setAccomTitle(rset.getString("ACCOM_TITLE"));
				ac.setAccomDate(rset.getDate("ACCOM_DATE")); 
				
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

	// 마이페이지 내가 쓴 공연후기 게시물 총 갯수 구하는 쿼리문 실행 메소드
	public int selectWrRvCount(Connection conn, int userNo) {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectWrRvCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userNo);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				listCount = rset.getInt("COUNT");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return listCount;
	}
	
	// 마이페이지 내가 쓴 공연후기 게시물 목록 조회 쿼리문 실행 메소드
	public ArrayList<Review> selectWrRv(Connection conn, PageInfo pi, int userNo) {
		ArrayList<Review> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectWrRv");
		
		try {
			pstmt = conn.prepareStatement(sql);
			int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
			int endRow = startRow + pi.getBoardLimit() - 1;
			
			pstmt.setInt(1, userNo);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			while(rset.next()) {
				Review rv = new Review();
				rv.setRvNo(rset.getInt("RV_NO"));
				rv.setRvTitle(rset.getString("RV_TITLE"));
				rv.setRvDate(rset.getDate("RV_DATE"));
				list.add(rv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}

	// 마이페이지 내가 쓴 댓글 총 갯수 구하는 쿼리문 실행 메소드 (자유게시판)
	public int selectRpFreeCount(Connection conn, int userNo) {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectRpFreeCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userNo);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				listCount = rset.getInt("COUNT");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return listCount;
	}
	// 마이페이지 내가 쓴 댓글 목록 조회용 쿼리문 실행 메소드 (자유게시판)
	public ArrayList<FreeBoardComment> selectRpFree(Connection conn, PageInfo pi, int userNo) {
		ArrayList<FreeBoardComment> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectRpFree");
		
		try {
			pstmt = conn.prepareStatement(sql);
			int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
			int endRow = startRow + pi.getBoardLimit() - 1;
			
			pstmt.setInt(1, userNo);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			while(rset.next()) {
				FreeBoardComment rv = new FreeBoardComment();
				rv.setFreeNo(rset.getInt("FREE_NO"));
				rv.setFreeCommNo(rset.getInt("FREE_COMM_NO"));
				rv.setFreeCommContent(rset.getString("FREE_COMM_CONTENT"));
				rv.setFreeCommDate(rset.getDate("FREE_COMM_DATE"));
				list.add(rv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}

	// 마이페이지 내가 쓴 댓글 총 갯수 구하는 쿼리문 실행 메소드 (공연정보)
	public int selectRpFesCount(Connection conn, int userNo) {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectRpFesCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userNo);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				listCount = rset.getInt("COUNT");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return listCount;
	}

	// 마이페이지 내가 쓴 댓글 목록 조회용 쿼리문 실행 메소드 (공연정보)
	public ArrayList<FestivalComment> selectRpFes(Connection conn, PageInfo pi, int userNo) {
		ArrayList<FestivalComment> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectRpFes");
		
		try {
			pstmt = conn.prepareStatement(sql);
			int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
			int endRow = startRow + pi.getBoardLimit() - 1;
			
			pstmt.setInt(1, userNo);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			while(rset.next()) {
				FestivalComment rv = new FestivalComment();
				rv.setFesNo(rset.getInt("FES_NO"));
				rv.setFesCommNo(rset.getInt("FES_COMM_NO"));
				rv.setFesCommContent(rset.getString("FES_COMM_CONTENT"));
				rv.setFesCommDate(rset.getTimestamp("FES_COMM_DATE"));
				list.add(rv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}

	// 마이페이지 내가 쓴 댓글 총 갯수 구하는 쿼리문 실행 메소드 (공연후기)
	public int selectRpRvCount(Connection conn, int userNo) {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectRpRvCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userNo);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				listCount = rset.getInt("COUNT");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return listCount;
	}

	// 마이페이지 내가 쓴 댓글 목록 조회용 쿼리문 실행 메소드 (공연정보)
	public ArrayList<ReviewComment> selectRpRv(Connection conn, PageInfo pi, int userNo) {
		ArrayList<ReviewComment> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectRpRv");
		
		try {
			pstmt = conn.prepareStatement(sql);
			int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
			int endRow = startRow + pi.getBoardLimit() - 1;
			
			pstmt.setInt(1, userNo);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			while(rset.next()) {
				ReviewComment rv = new ReviewComment();
				rv.setRvCommNo(rset.getInt("RV_COMM_NO"));
				rv.setRvCommContent(rset.getString("RV_COMM_CONTENT"));
				rv.setRvCommDate(rset.getDate("RV_COMM_DATE"));
				list.add(rv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}

	// 모든 회원의 정보 조회 - 총 회원 수
	public int selectAllUserCount(Connection conn) {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectAllUserCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				listCount = rset.getInt("COUNT");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return listCount;
	}

	public ArrayList<User> selectAllUser(Connection conn, PageInfo pi) {
		ArrayList<User> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectAllUser");
		
		try {
			pstmt = conn.prepareStatement(sql);
			int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
			int endRow = startRow + pi.getBoardLimit() - 1;
			
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			while(rset.next()) {
				User u = new User();
				u.setUserNo(rset.getInt("USER_NO"));
				u.setUserId(rset.getString("USER_ID"));
				u.setUserPwd(rset.getString("USER_PWD"));
				u.setUserNickname(rset.getString("USER_NICKNAME"));
				u.setUserName(rset.getString("USER_NAME"));
				u.setBirthDate(rset.getDate("BIRTHDATE"));
				u.setPhone(rset.getString("PHONE"));
				u.setEmail(rset.getString("EMAIL"));
				u.setAddress(rset.getString("ADDRESS"));
				u.setBlackListReg(rset.getDate("BLACKLIST_REG"));
				u.setBlackListRfr(rset.getString("BLACKLIST_RFR"));
				u.setRegDate(rset.getDate("REGDATE"));
				u.setUpdateDate(rset.getDate("UPDATE_DATE"));
				u.setStatus(rset.getString("STATUS"));
				list.add(u);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}
	
	// 회원 블랙리스트 추가
	
	// 사용자 상태를 업데이트하는 메서드
    public int updateUserStatus(Connection conn, int userNo, String status) {
        PreparedStatement pstmt = null;
        int result = 0;
        
        String query = "UPDATE USER_INFO SET STATUS = ? WHERE USER_NO = ?";

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, status); // status 값을 설정
            pstmt.setInt(2, userNo);    // userNo 값을 설정
            
            result = pstmt.executeUpdate(); // 업데이트 수행
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return result;
    }

    

    public boolean registerBlackListReason(int userNo, String reason) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int result = 0;

        try {
            conn = JDBCTemplate.getConnection(); // 데이터베이스 연결
            String sql = "UPDATE USER_INFO SET BLACKLIST_RFR = ?, BLACKLIST_REG = SYSDATE WHERE USER_NO = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, reason); // 블랙리스트 사유 설정
            pstmt.setInt(2, userNo); // 사용자 번호 설정
            
            result = pstmt.executeUpdate(); // SQL 실행

            if (result > 0) {
                JDBCTemplate.commit(conn); // 성공 시 커밋
                return true;
            } else {
                JDBCTemplate.rollback(conn); // 실패 시 롤백
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JDBCTemplate.rollback(conn); // 예외 발생 시 롤백
            return false;
        } finally {
            JDBCTemplate.close(pstmt); // PreparedStatement 자원 해제
            JDBCTemplate.close(conn); // Connection 자원 해제
        }
    }
    
 // 블랙리스트 등록일 조회 메소드
    public String selectBlackListRegDate(int userNo) {
        String blackListRegDate = null;
        String sql = "SELECT BLACKLIST_REG FROM USER_INFO WHERE USER_NO = ?";
        try (Connection conn = JDBCTemplate.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userNo);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                blackListRegDate = rs.getString("BLACKLIST_REG");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return blackListRegDate; // 등록일 반환
    }

}
	

	
	




