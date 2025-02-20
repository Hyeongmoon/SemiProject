package com.fh.user.model.service;
import static com.fh.common.JDBCTemplate.close;
import static com.fh.common.JDBCTemplate.commit;
import static com.fh.common.JDBCTemplate.getConnection;
import static com.fh.common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.ArrayList;

import com.fh.accompanyBoard.model.vo.Accompany;
import com.fh.common.model.vo.PageInfo;
import com.fh.festival.model.vo.Festival;
import com.fh.festival.model.vo.FestivalComment;
import com.fh.freeBoard.model.vo.FreeBoard;
import com.fh.freeBoard.model.vo.FreeBoardComment;
import com.fh.reviewBoard.model.vo.Review;
import com.fh.reviewBoard.model.vo.ReviewComment;
import com.fh.user.model.dao.UserDao;
import com.fh.user.model.vo.User;

public class UserService {

	/**
	 * 로그인용 메소드
	 * @param u => 로그인할 사용자의 정보
	 * @return => 처리된 행의 갯수
	 */
	public User loginUser(User u) {
		
		Connection conn = getConnection();
		User loginUser = new UserDao().loginUser(conn, u);
		
		close(conn);
		return loginUser;
	}

	/**
	 * 회원가입정보 입력용 메소드
	 * @param u => 회원가입할 회원의 정보들
	 * @return => 처리된 행의 갯수
	 */
	public int insertUser(User u) {
		Connection conn = getConnection();
		
		int result = new UserDao().insertUser(conn,u);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		
		return result;
	}

	// 아이디 중복확인용 메소드
	public int idCheck(String checkId) {
		Connection conn = getConnection();
		int count = new UserDao().idcheck(conn, checkId);
		
		close(conn);
		
		return count;
	}

	// 비밀번호 일치 확인용 메소드
	public int pwdCheck(String checkPwd) {
		Connection conn = getConnection();
		int count = new UserDao().pwdcheck(conn, checkPwd);
		
		close(conn);
		
		return count;
	}
	
	// 회원정보 수정용 메소드
	public User updateUser(User u) {
		Connection conn = getConnection();
		int result = new UserDao().updateUser(conn, u);
		User afterUpdate = null;
		
		if(result > 0) {
			commit(conn);
			afterUpdate = new UserDao().selectUser(conn, u.getUserId());
		} else {
			rollback(conn);
		}
		close(conn);
		return afterUpdate;
	}

	// 회원탈퇴용 메소드
	public int deleteUser(User u) {
		Connection conn = getConnection();
		int result = new UserDao().deleteUser(conn, u);
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	// 회원가입 최종확인 메소드
	public int fcIdcheck(String fcId) {
		Connection conn = getConnection();
		int count = new UserDao().fcIdcheck(conn, fcId);
		
		close(conn);
		
		return count;
	}

	public int fcPwdcheck1(String fcPwd1) {
		Connection conn = getConnection();
		int result = new UserDao().fcPwdcheck1(conn, fcPwd1);
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public int fcNicknamecheck(String fcNickname) {
		Connection conn = getConnection();
		int result = new UserDao().fcNicknamecheck(conn, fcNickname);
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public int fcNamecheck(String fcName) {
		Connection conn = getConnection();
		int result = new UserDao().fcNamecheck(conn, fcName);
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	// 닉네임 중복 체크용 메소드
	public int nicknameCheck(String checkNickname) {
		Connection conn = getConnection();
		int count = new UserDao().nicknameCheck(conn, checkNickname);
		
		close(conn);
		
		return count;
	}

	// 찜 - 자유게시판 조회
	public int selectDibFreeCount(int userNo) {
		Connection conn = getConnection();
		int listCount = new UserDao().selectDibFreeCount(conn,userNo);
		close(conn);
		return listCount;
	}
	// 찜 - 자유게시판 조회
	public ArrayList<FreeBoard> selectDibFree(PageInfo pi, int userNo) {
		Connection conn = getConnection();
		ArrayList<FreeBoard> list = new UserDao().selectDibFree(conn, pi, userNo);
		
		close(conn);
		return list;
	}

	// 찜 - 공연후기 조회
	public int selectDibRvCount(int userNo) {
		Connection conn = getConnection();
		int listCount = new UserDao().selectDibRvCount(conn, userNo);
		close(conn);
		return listCount;
	}

	// 찜 - 공연후기 조회
	public ArrayList<Review> selectDibRv(PageInfo pi, int userNo) {
		Connection conn = getConnection();
		ArrayList<Review> list = new UserDao().selectDibRv(conn, pi, userNo);
		
		close(conn);
		return list;
	}
	
	// 찜 - 공연정보 조회
	public int selectDibFesCount(int userNo) {
		Connection conn = getConnection();
		int listCount = new UserDao().selectDibFesCount(conn,userNo);
		close(conn);
		return listCount;
	}
	
	// 찜 - 공연정보 조회
	public ArrayList<Festival> selectDibFes(PageInfo pi, int userNo) {
		Connection conn = getConnection();
		ArrayList<Festival> list = new UserDao().selectDibFes(conn, pi, userNo);
		
		close(conn);
		return list;
	}
	
	// 찜 - 동행구하기 조회
		public int selectDibAccompanyCount(int userNo) {
			Connection conn = getConnection();
			int listCount = new UserDao().selectDibAccompanyCount(conn, userNo);
			close(conn);
			return listCount;
	}
	// 찜 - 동행구하기 조회
	public ArrayList<Accompany> selectDibAccompany(PageInfo pi, int userNo) {
		Connection conn = getConnection();
		ArrayList<Accompany> list = new UserDao().selectDibAccompany(conn, pi, userNo);
		
		close(conn);
		return list;
	}

	// 내가 쓴 게시물 조회 - 자유게시판
	public int selectWrFreeCount(int userNo) {
		Connection conn = getConnection();
		int listCount = new UserDao().selectWrFreeCount(conn,userNo);
		close(conn);
		return listCount;
	}
	
	// 내가 쓴 게시물 조회 - 자유게시판
	public ArrayList<FreeBoard> selectWrFree(PageInfo pi, int userNo) {
		Connection conn = getConnection();
		ArrayList<FreeBoard> list = new UserDao().selectWrFree(conn, pi, userNo);
		
		close(conn);
		return list;
	}

	// 내가 쓴 게시물 조회 - 동행구하기
	public int selectWrAcCount(int userNo) {
		Connection conn = getConnection();
		int listCount = new UserDao().selectWrAcCount(conn, userNo);
		close(conn);
		return listCount;
	}

	// 내가 쓴 게시물 조회 - 동행구하기
	public ArrayList<Accompany> selectWrAc(PageInfo pi, int userNo) {
		Connection conn = getConnection();
		ArrayList<Accompany> list = new UserDao().selectWrAc(conn, pi, userNo);
		
		close(conn);
		return list;
	}

	// 내가 쓴 게시물 조회 - 공연후기
	public int selectWrRvCount(int userNo) {
		Connection conn = getConnection();
		int listCount = new UserDao().selectWrRvCount(conn, userNo);
		close(conn);
		return listCount;
	}

	// 내가 쓴 게시물 조회 - 공연후기
	public ArrayList<Review> selectWrRv(PageInfo pi, int userNo) {
		Connection conn = getConnection();
		ArrayList<Review> list = new UserDao().selectWrRv(conn, pi, userNo);
		
		close(conn);
		return list;
	}

	// 내가 쓴 댓글 조회 - 자유게시판
	public int selectRpFreeCount(int userNo) {
		Connection conn = getConnection();
		int listCount = new UserDao().selectRpFreeCount(conn, userNo);
		close(conn);
		return listCount;
	}

	// 내가 쓴 댓글 조회 - 자유게시판
	public ArrayList<FreeBoardComment> selectRpFree(PageInfo pi, int userNo) {
		Connection conn = getConnection();
		ArrayList<FreeBoardComment> list = new UserDao().selectRpFree(conn, pi, userNo);
		
		close(conn);
		return list;
	}

	// 내가 쓴 댓글 조회 - 공연정보
	public int selectRpFesCount(int userNo) {
		Connection conn = getConnection();
		int listCount = new UserDao().selectRpFesCount(conn, userNo);
		close(conn);
		return listCount;
	}

	// 내가 쓴 댓글 조회 - 공연정보
	public ArrayList<FestivalComment> selectRpFes(PageInfo pi, int userNo) {
		Connection conn = getConnection();
		ArrayList<FestivalComment> list = new UserDao().selectRpFes(conn, pi, userNo);
		
		close(conn);
		return list;
	}

	// 내가 쓴 댓글 조회 - 공연후기
	public int selectRpRvCount(int userNo) {
		Connection conn = getConnection();
		int listCount = new UserDao().selectRpRvCount(conn, userNo);
		close(conn);
		return listCount;
	}
	
	// 내가 쓴 댓글 조회 - 공연후기
	public ArrayList<ReviewComment> selectRpRv(PageInfo pi, int userNo) {
		Connection conn = getConnection();
		ArrayList<ReviewComment> list = new UserDao().selectRpRv(conn, pi, userNo);
		
		close(conn);
		return list;
	}

	// 모든 회원의 정보 조회 - 총 회원 수
	public int selectAllUserCount() {
		Connection conn = getConnection();
		int listCount = new UserDao().selectAllUserCount(conn);
		close(conn);
		return listCount;
	}

	// 모든 회원의 정보 조회 - 목록 조회
	public ArrayList<User> selectAllUser(PageInfo pi) {
		Connection conn = getConnection();
		ArrayList<User> list = new UserDao().selectAllUser(conn, pi);
		
		close(conn);
		return list;
	}
	
	// 회원 블랙리스트 추가
	
	 // 사용자 상태를 업데이트하는 메서드
    public boolean updateUserStatus(int userNo, String status) {
        // 1. DB 연결 생성
        Connection conn = getConnection();
        
        // 2. UserDao를 호출하여 상태 업데이트 수행
        int result = new UserDao().updateUserStatus(conn, userNo, status);
        
        // 3. 트랜잭션 처리 (성공 시 커밋, 실패 시 롤백)
        if (result > 0) {
            commit(conn);
            close(conn);
            return true;
        } else {
            rollback(conn);
            close(conn);
            return false;
        }
    }
	
    public boolean registerBlackListReason(int userNo, String reason) {
        // DAO 메서드를 호출하여 블랙리스트 사유 등록
        return new UserDao().registerBlackListReason(userNo, reason);
    }

    // 블랙리스트 등록일 조회 메소드
    public String getBlackListRegDate(int userNo) {
        return new UserDao().selectBlackListRegDate(userNo);
    }


		
}






