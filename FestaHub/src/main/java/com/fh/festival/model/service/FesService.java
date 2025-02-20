package com.fh.festival.model.service;

import static com.fh.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.ArrayList;

import com.fh.common.model.vo.PageInfo;
import com.fh.festival.model.dao.FesDao;
import com.fh.festival.model.vo.Festival;
import com.fh.festival.model.vo.FestivalComment;
import com.fh.festival.model.vo.FestivalImage;
import com.fh.festival.model.vo.FestivalLike;

public class FesService {
	
	/**
	 * 공연정보 전체 갯수 조회용 서비스 메소드
	 * @return => 공연정보 총 갯수
	 */
	public int selectListCount() {
		
		// 1) Connection 객체 생성
		Connection conn = getConnection();
		
		// 2) Connection 객체와 전달값을 넘기면서
		//    DAO 로 요청 후 결과 받기
		int listCount 
			= new FesDao().selectListCount(conn);
		
		// 3) DML 문의 경우 트랜잭션 처리
		// > select 문 이므로 패스
		
		// 4) Connection 객체 반납
		close(conn);
		
		// 5) Controller 로 결과 반환
		return listCount;
	}

	/**
	 * 공연정보 목록 조회용 서비스 메소드
	 * @param pi => 페이지 정보 변수
	 * @return => 조회된 게시글들
	 */
	public ArrayList<Festival> selectList(PageInfo pi, int userNo) {
		
		Connection conn = getConnection();
		
		ArrayList<Festival> list 
			= new FesDao().selectList(conn, pi, userNo);
		
		close(conn);
		
		return list;
	}

	/**
	 * 공연정보 조회수 증가용 서비스 메소드
	 * @param fesNo => 게시글 번호
	 * @return => 행의 갯수
	 */
	public int increaseCount(int fesNo) {
		
		// 1) Connection 객체 생성
		Connection conn = getConnection();
		
		// 2) Connection 객체와 전달값을 넘기면서
		//    DAO 로 요청 후 결과 받기
		int result 
			= new FesDao().increaseCount(conn, fesNo);
		
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
	 * 공연정보 상세조회용 서비스 메소드
	 * @param fesNo => 게시글 번호
	 * @return => 게시글 한개의 내용
	 */
	public Festival selectFes(int fesNo) {
		
		Connection conn = getConnection();
		
		Festival f = new FesDao().selectFes(conn, fesNo);
		
		close(conn);
		
		return f;
	}

	/**
	 * 공연정보에 첨부된 이미지 조회용 서비스 메소드
	 * @param fesNo => 게시글번호
	 * @return => 조회된 이미지들
	 */
	public ArrayList<FestivalImage> selectImgList(int fesNo) {
		
		Connection conn = getConnection();
		
		ArrayList<FestivalImage> fiList
			= new FesDao().selectImgList(conn, fesNo);
		
		close(conn);
		
		return fiList;
	}

	/**
	 * 공연정보 등록용 서비스 메소드
	 * @param f => 공연정보 내용
	 * @param fiList => 첨부된 이미지 리스트
	 * @return => 행의 갯수
	 */
	public int insertFes(Festival f, ArrayList<FestivalImage> fiList) {
		
		// 1) Connection 객체 생성
		Connection conn = getConnection();
		
		// 2) Connection 객체와 전달값을 넘기면서
		//    DAO 로 요청 후 결과 받기
		// 2_1) Board 테이블에 Insert 요청
		int result1 = new FesDao().insertFes(conn, f);
		
		// 2_2) Attachment 테이블에 Insert 요청
		// > 적어도 1개 이상의 첨부파일이 무조건 넘어오기 때문에
		//   기존처럼 별도의 조건 검사 없이 바로 요청 후 결과 받기!!
		int result2 = new FesDao().insertImgList(conn, fiList);
		
		// 3) DML 문의 경우 트랜잭션 처리
		if(result1 > 0 && result2 > 0) { // 성공 (커밋)
			
			commit(conn);
			
		} else { // 실패 (롤백)
			
			rollback(conn);
		}
		
		// 4) Connection 객체 반납
		close(conn);
		
		// 5) Controller 로 결과 반환
		return result1 * result2;
	}

	/**
	 * 공연정보 삭제용 서비스 메소드
	 * @param fesNo => 게시글 번호
	 * @return => 행의 갯수
	 */
	public int deleteFes(int fesNo) {
		
		Connection conn = getConnection();
		
		int result 
			= new FesDao().deleteFes(conn, fesNo);
		
		if(result > 0) { // 성공 (커밋)
			
			commit(conn);
			
		} else { // 실패 (롤백)
			
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}

	/**
	 * 공연정보 수정용 서비스 메소드
	 * @param f => 수정할 게시글 정보
	 * @param newList => 신규 첨부파일 리스트
	 * @param originList => 기존 첨부파일 리스트
	 * @return => 처리된 행의 갯수
	 */
	public int updateFes(Festival f, 
						 ArrayList<FestivalImage> newList, 
						 ArrayList<FestivalImage> originList) {
		
		Connection conn = getConnection();
		
		int result1 = new FesDao().updateFes(conn, f);
		
		int result2 = 1;
		
		int result3 = 1;
		
		if(!newList.isEmpty()) { 
			
			result2 = new FesDao().updateImgList(conn, newList);
			
			if(!originList.isEmpty()) {
				
				result3 = new FesDao().deleteImgList(conn, originList);
			}
		}
		
		if(result1 > 0 && result2 > 0 && result3 > 0) { // 성공 (커밋)
			
			commit(conn);
			
		} else { // 실패 (롤백)
			
			rollback(conn);
		}
		
		close(conn);
		
		return result1 * result2 * result3;
		
	}

	/**
	 * 댓글 작성용 서비스 메소드
	 * @param fc => 댓글 정보
	 * @return => 처리된 행의 갯수
	 */
	public int insertComm(FestivalComment fc) {
		
		Connection conn = getConnection();
		
		int result 
			= new FesDao().insertComm(conn, fc);
		
		if(result > 0) {
			
			commit(conn);
			
		} else { 
			
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}

	/**
	 * 댓글 리스트 조회용 서비스 메소드
	 * @param fesNo => 댓글 조회할 게시글 번호
	 * @return => 조회된 댓글 리스트
	 */
	public ArrayList<FestivalComment> selectCommList(int fesNo) {

		Connection conn = getConnection();
		
		ArrayList<FestivalComment> fcList
			= new FesDao().selectCommList(conn, fesNo);
		
		close(conn);
		
		return fcList;
	}

	/**
	 * 댓글 수정용 서비스 메소드
	 * @param fesCommNo => 수정할 댓글 번호
	 * @param fesCommContent => 수정할 댓글 내용
	 * @return => 처리된 행의 갯수
	 */
	public int updateComm(int fesCommNo, String fesCommContent) {
		
        Connection conn = getConnection();
        
        int result = new FesDao().updateComm(conn, fesCommNo, fesCommContent);
        
		if(result > 0) {
			
			commit(conn);
			
		} else { 
			
			rollback(conn);
		}
		
		close(conn);
        
        return result;
	}

	/**
	 * 댓글 삭제용 서비스 메소드
	 * @param fesCommNo => 삭제할 댓글 번호
	 * @return => 처리된 행의 갯수
	 */
	public int deleteComm(int fesCommNo) {
		
        Connection conn = getConnection();
        
        int result = new FesDao().deleteComm(conn, fesCommNo);
        
		if(result > 0) {
			
			commit(conn);
			
		} else { 
			
			rollback(conn);
		}
		
		close(conn);
		
        return result;
	}

	/**
	 * 좋아요 정보를 가져오는 서비스 메소드
	 * @param fesNo => 게시글 번호
	 * @param userNo => 유저 번호
	 * @return => 조회된 좋아요 정보
	 */
	public FestivalLike selectLikeInfo(int fesNo, int userNo) {
		
        Connection conn = getConnection();
        
        FestivalLike fl = new FesDao().selectLikeInfo(conn, fesNo, userNo);
        
        close(conn);
        
        return fl;
	}

	/**
	 * 좋아요 토글 기능 실행용 서비스 메소드
	 * @param fesNo => 게시글번호
	 * @param userNo => 유저번호
	 * @return => 기능실행 후 재조회된 좋아요 정보
	 */
	public FestivalLike toggleLike(int fesNo, int userNo) {
		
        Connection conn = getConnection();

        // 현재 좋아요 상태와 카운트를 조회
        FestivalLike fl = new FesDao().selectLikeInfo(conn, fesNo, userNo);

        // 좋아요 상태에 따라 처리 분기
        int result = 0;
        
        if(!fl.isLiked()) {
        	result = new FesDao().likeFes(conn, fesNo, userNo);
        } else {
        	result = new FesDao().unlikeFes(conn, fesNo, userNo);
        }
        
        if (result > 0) {
        	
            commit(conn);
            
        } else {
        	
            rollback(conn);
        }

        // 갱신된 좋아요 상태 및 카운트 다시 조회
        fl = new FesDao().selectLikeInfo(conn, fesNo, userNo);
        
        close(conn);
        
        return fl;
	}

	/**
	 * 이전글 조회용 서비스 메소드
	 * @param fesNo => 타겟 게시글 번호
	 * @return => 이전글 정보
	 */
	public Festival getPrevFes(int fesNo) {
		
		Connection conn = getConnection();
				
		Festival prevFes = new FesDao().getPrevFes(conn, fesNo);

		close(conn);
		
		return prevFes;
	}

	/**
	 * 다음글 조회용 서비스 메소드
	 * @param fesNo => 타겟 게시글 번호
	 * @return => 이전글 정보
	 */
	public Festival getNextFes(int fesNo) {

		Connection conn = getConnection();
		
		Festival nextFes = new FesDao().getNextFes(conn, fesNo);

		close(conn);
		
		return nextFes;
	}

	/**
	 * 게시글 검색용 서비스 메소드
	 * @param category => 검색할 카테고리
	 * @param keyword => 검색 키워드
	 * @param pi => 페이지정보
	 * @return => 검색된 게시글 리스트
	 */
	public ArrayList<Festival> searchFes(String category, String keyword, PageInfo pi, int userNo) {
		
        Connection conn = getConnection();
        
        ArrayList<Festival> list = new FesDao().searchFes(conn, category, keyword, pi, userNo);
        
        close(conn);
        
        return list;
	}

	/**
	 * 게시글 검색 리스트 전체 카운트
	 * @param category
	 * @param keyword
	 * @return
	 */
	public int selectSearchCount(String category, String keyword) {

		Connection conn = getConnection();
		
		int listCount 
			= new FesDao().selectSearchCount(conn, category, keyword);
		
		close(conn);
		
		return listCount;
	}

	/**
	 * 관리자페이지 전체글 조회용 서비스 메소드
	 * @param userNo
	 * @return => 조회된 게시글 리스트
	 */
	public ArrayList<Festival> adminSelectList(int userNo) {
		
		Connection conn = getConnection();
		
		ArrayList<Festival> list 
			= new FesDao().adminSelectList(conn, userNo);
		
		close(conn);
		
		return list;
	}

	/**
	 * 관리자페이지 첨부이미지 조회용 서비스 메소드
	 * @param fesNo
	 * @return => 조회된 첨부이미지 리스트
	 */
	public ArrayList<FestivalImage> getAdminImgList(int fesNo) {
		
		Connection conn = getConnection();
		
		ArrayList<FestivalImage> fiList 
			= new FesDao().getAdminImgList(conn, fesNo);
		
		close(conn);
		
		return fiList;
		
	}

	/**
	 * 관리자페이지 게시글 상태 전환용 서비스 메소드
	 * @param fesNo => 전환할 게시글 번호
	 * @param newStatus => 현재상태
	 * @return => 처리된 행의 갯수
	 */
	public int toggleStatus(int fesNo, String newStatus) {
		
	    Connection conn = getConnection();
	    int result = new FesDao().toggleStatus(conn, fesNo, newStatus);
	    
	    if (result > 0) {
	        commit(conn);
	    } else {
	        rollback(conn);
	    }
	    
	    close(conn);
	    return result;
	}

	/**
	 * 게시글 이미지 삭제용 서비스 메소드
	 * @param fesImgNo => 삭제할 이미지 번호
	 * @return => 처리된 행의 갯수
	 */
	public int deleteImg(int fesImgNo) {

        Connection conn = getConnection();
        int result = new FesDao().deleteImg(conn, fesImgNo);
	    if (result > 0) {
	        commit(conn);
	    } else {
	        rollback(conn);
	    }
	    
	    close(conn);
	    return result;
	}

	/**
	 * 관리자페이지 게시글 테이블 삭제용 서비스 메소드
	 * @param fesNo => 삭제할 게시글 번호
	 * @return => 처리된 행의 갯수
	 */
	public int deleteTable(int fesNo) {
		
	    Connection conn = getConnection();
	    int result = new FesDao().deleteTable(conn, fesNo);
	    
	    if (result > 0) {
	        commit(conn);
	    } else {
	        rollback(conn);
	    }
	    
	    close(conn);
	    return result;
	}
}
