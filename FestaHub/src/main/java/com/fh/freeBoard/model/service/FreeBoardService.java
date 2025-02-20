package com.fh.freeBoard.model.service;

import static com.fh.common.JDBCTemplate.close;
import static com.fh.common.JDBCTemplate.commit;
import static com.fh.common.JDBCTemplate.getConnection;
import static com.fh.common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.ArrayList;

import com.fh.common.model.vo.PageInfo;
import com.fh.freeBoard.model.dao.FreeBoardDao;
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
public class FreeBoardService {

	
	
	/**
	 * 2024.10.22 14:50
	 * 자유게시판 전체 게시글수 조회용 서비스 메소드
	 * @return => 자유게시판 전체 개시글 수
	 */
	public int selectListCount() {
		
		Connection conn = getConnection();
		
		int result = new FreeBoardDao().selectListCount(conn);
		
		close(conn);
		return result;
	}
	
	/**
	 * 2024.10.23 01:42 김형문
	 * 제목으로 검색시 전체 게시글수
	 * @param inputValue => 검색할 제목
	 * @return => 전체 게시글수
	 */
	public int selectListCountTitle(String inputValue) {
		Connection conn = getConnection();
		
		int result = new FreeBoardDao().selectListCountTitle(conn,inputValue);
		
		close(conn);
		return result;
	}

	
	/**
	 * 2024.10.23 01:42 김형문
	 * 내용으로 검색시 전체 게시글수
	 * @param inputValue =>검색할 내용
	 * @return => 전체 게시글수
	 */
	public int selectListCountContent(String inputValue) {
		Connection conn = getConnection();
		
		int result = new FreeBoardDao().selectListCountContent(conn,inputValue);
		
		close(conn);
		return result;
	}

	
	/**
	 * 2024.10.23 01:42 김형문
	 * 작성자닉네임으로 검색시 전체 게시글수
	 * @param inputValue => 검색할 닉네임
	 * @return => 전체 게시글수 
	 */
	public int selectListCountWriter(String inputValue) {
		Connection conn = getConnection();
		
		int result = new FreeBoardDao().selectListCountWriter(conn,inputValue);
		
		close(conn);
		return result;
	}

	
	
	
	
	
	
	

	/**
	 * 2024.10.22 16:00
	 * 전체 게시글 조회용 서비스 메소드 
	 * @param pi=> 어느 구간을 보여줄지에 대한 정보
	 * @return => 구간별 보여질 자유게시판목록
	 */
	public ArrayList<FreeBoard> selectList(PageInfo pi) {
		
		Connection conn = getConnection();
		
		ArrayList<FreeBoard> list = new FreeBoardDao().selectList(conn,pi);
		
		close(conn);
		
		return list;
	}

	

	/**
	 * 2024.10.23.15:00 형문
	 * 자유게시판 조회수 증가용 서비스 메소드
	 * @param freeNo=>증가시킬 게시글 번호
	 * @return => 처리된 행의 갯수
	 */
	public int increaseCount(int freeNo) {
		
		Connection conn = getConnection();
		
		int result = new FreeBoardDao().increaseCount(conn,freeNo);
		
		if(result>0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	
	
	/**
	 * 2024.10.23 13:30 김형문
	 * 자유 게시판 글작성 서비스 메소드
	 * @param f => 업로드할 자유게시판 게시글 정보
	 * @param list => 업로드할 자유게시판 파일들 정보
	 * @return => 처리된 행의 갯수
	 */
	public int insertFreeBoard(FreeBoard f, ArrayList<FreeBoardFile> list) {
		Connection conn = getConnection();
		
		int result1 = new FreeBoardDao().insertFreeBoard(conn,f);

		int result2 = 1;
		//업로드한 파일이 있을경우에만 실행
		
		result2 = new FreeBoardDao().insertFreeBoardFileList(conn,list);
		
		if(result1>0 && result2>0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		return result1*result2;
	}
	
	
	
	
	/**
	 * 2024.10.23.15:15 김형문
	 * 자유게시판 게시글 상세조회용 서비스메소드
	 * @param freeNo
	 * @return=>조회할 게시글의 정보
	 */
	public FreeBoard selectFreeBoard(int freeNo) {

		Connection conn = getConnection();
		
		FreeBoard f = new FreeBoardDao().selectFreeBoard(conn,freeNo);
		
		close(conn);
		
		return f;
		
	}

	/**
	 * 2024.10.23 15:50 김형문
	 * 자유게시판 게시글 첨부파일 조회용 서비스 메소드
	 * @param freeNo=>조회할 첨부파일이 있는 게시글 번호
	 * @return=> 조회된 첨부파일들
	 */
	public ArrayList<FreeBoardFile> selectFreeBoardFile(int freeNo) {

		
		Connection conn = getConnection();
		
		ArrayList<FreeBoardFile> files = new FreeBoardDao().selectFreeBoardFile(conn,freeNo);
		
		close(conn);
		
		return files;
		
	}

	/**
	 * 2024.10.24 11:04 김형문
	 * 자유게시판 삭제(update) 서비스 메소드
	 * @param userNo => 로그인한 유저 번호
	 * @param freeNo => 삭제할 게시글 번호
	 * @return => 처리된 행의 갯수
	 */
	public int deleteFreeBoard(int userNo, int freeNo) {
		Connection conn = getConnection();
		
		int result = new FreeBoardDao().deleteFreeBoard(conn,userNo,freeNo);
		
		if(result>0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);

		return result;
	}

	
	
	/**
	 * 2024.10.25 17:40 김형문
	 * 자유게시판 댓글 입력 서비스 메소드
	 * @param fc => 추가할 댓글에 대한 정보
	 * @return => 처리된 행의갯수
	 */
	public int insertComment(FreeBoardComment fc) {
		Connection conn = getConnection();
		
		int result = new FreeBoardDao().insertComment(conn,fc);
		
		if(result>0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		return result;
	}

	/**
	 * 2024.10.25.18:00 김형문
	 * @param freeNo=> 댓글 조회할 자유게시판게시글의 번호
	 * @return => 댓글리스트
	 */
	public ArrayList<FreeBoardComment> selectCommentList(int freeNo) {
		
		Connection conn = getConnection();
		
		ArrayList<FreeBoardComment> list = new FreeBoardDao().selectCommentList(conn,freeNo);
		
		close(conn);
		
		return list;
	}

	/**
	 * 2024.10.28 김형문
	 * @param freeCommNo=>삭제할 댓글의 수
	 * @return => 처리된 행의 갯수
	 */
	public int deleteComment(int freeCommNo) {
		
		Connection conn = getConnection();
		
		int result = new FreeBoardDao().deleteComment(conn,freeCommNo);
					 
		if(result>0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		return result;
	}

	public FreeBoardComment selectFreeBoardComment(int freeCommNo) {
		
		Connection conn = getConnection();
		
		FreeBoardComment fc = new FreeBoardDao().selectFreeBoardComment(conn,freeCommNo);
		
		close(conn);
		
		return fc;
	}

	/**
	 * 2024.10.29 16:11 화요일 김형문
	 * 댓글 수정용 서비스 메소드
	 * @param fc => 수정할 댓글의 내용, 댓글 번호
	 * @return => 처리된 행의 갯수
	 */
	public int updateFreeBoardComment(FreeBoardComment fc) {
		
		Connection conn = getConnection();
		
		int result = new FreeBoardDao().updateFreeBoardComment(conn,fc);
		
		
		if(result>0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
		
		
	}

	/**
	 * 2024.10.30.12:50 김형문
	 * 좋아요를 눌렀는지 안눌렀는지 확인 서비스 메소드
	 * @param fl=>게시글번호와, 로그인유저번호
	 * @return=>1:눌렀음 0:안누름
	 */
	public int selectLike(FreeBoardLike fl) {
		
		Connection conn = getConnection();
		
		int result = new FreeBoardDao().selectLike(conn,fl);
		
		close(conn);
		
		return result;
				
			

		
	}

	/**
	 * 2024.10.30 14:00 김형문
	 * 좋아요 설정 서비스 메소드
	 * @param fl=>좋아요테이블에 추가할 정보
	 * @return => 처리된 행의 갯수
	 */
	public int insertLike(FreeBoardLike fl) {
		
		Connection conn = getConnection();
		
		int result = new FreeBoardDao().insertLike(conn,fl);
		
		if(result>0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		
		return result;
		
	}

	/**
	 * 2024.10.30 14:40 김형문
	 * 좋아요 해제 서비스 메소드
	 * @param fl => 좋아요 테이블에서 삭제할 정보
	 * @return => 처리된 행의 갯수
	 */
	public int deleteLike(FreeBoardLike fl) {
		
		Connection conn = getConnection();
		
		int result = new FreeBoardDao().deleteLike(conn,fl);
		
		if(result>0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}

	
	
	/**
	 * 2024.10.30 15:40 김형문
	 * @param freeNo => 좋아요수를 조회할 게시글 번호
	 * @return => 총 좋아요 수
	 */
	public int selectLikeCount(int freeNo) {
		
		Connection conn = getConnection();
		
		int result = new FreeBoardDao().selectLikeCount(conn,freeNo);
		
		close(conn);
		
		return result;
		
	}

	/**
	 * 2024.10.30 23:00 김형문
	 * 자유게시판 리스트 역순 조회 서비스 메소드
	 * @param pi => 페이징바 처리 정보
	 * @return => 역순으로 조회된 리스트
	 */
	public ArrayList<FreeBoard> selectListAsc(PageInfo pi) {
		
		Connection conn = getConnection();
		
		ArrayList<FreeBoard> list = new FreeBoardDao().selectListAsc(conn,pi);
		
		close(conn);
		
		return list;
	}

	/**
	 * 2024.10.30 00:30 김형문
	 * 제목으로 검색용 서비스 메소드
	 * @param pi => 페이징바 처리 정보
	 * @param inputValue => 검색할 제목
	 * @return
	 */
	public ArrayList<FreeBoard> selectListTitle(PageInfo pi, String inputValue) {
		Connection conn = getConnection();
		
		ArrayList<FreeBoard> list = new FreeBoardDao().selectListTitle(conn,pi,inputValue);
			
		close(conn);
			
		return list;

	}

	/**
	 * 2024.10.30 00:30 김형문
	 * 글내용 검색용 서비스 메소드
	 * @param pi=> 페이징바 처리 정보
	 * @param inputValue => 검색할 글내용
	 * @return
	 */
	public ArrayList<FreeBoard> selectListContent(PageInfo pi, String inputValue) {
		Connection conn = getConnection();
		
		ArrayList<FreeBoard> list = new FreeBoardDao().selectListContent(conn,pi,inputValue);
			
		close(conn);
			
		return list;

	}

	/**
	 * 2024.10.30 00:30 김형문
	 * 닉네임 검색용 서비스 메소드
	 * @param pi=> 페이징바 처리 정보
	 * @param inputValue => 검색할 닉네임
	 * @return
	 */
	public ArrayList<FreeBoard> selectListWriter(PageInfo pi, String inputValue) {
		Connection conn = getConnection();
		
		ArrayList<FreeBoard> list = new FreeBoardDao().selectListWriter(conn,pi,inputValue);
			
		close(conn);
			
		return list;

	}



	
	////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * 2024.10.30 00:30 김형문
	 * 제목으로 오래된순 검색용  서비스 메소드
	 * @param pi => 페이징바 처리 정보
	 * @param inputValue => 검색할 제목
	 * @return
	 */
	public ArrayList<FreeBoard> selectListAscTitle(PageInfo pi, String inputValue) {
		Connection conn = getConnection();
		
		ArrayList<FreeBoard> list = new FreeBoardDao().selectListAscTitle(conn,pi,inputValue);
			
		close(conn);
			
		return list;

	}

	/**
	 * 2024.10.30 00:30 김형문
	 * 글내용 오래된순  검색용 서비스 메소드
	 * @param pi=> 페이징바 처리 정보
	 * @param inputValue => 검색할 글내용
	 * @return
	 */
	public ArrayList<FreeBoard> selectListAscContent(PageInfo pi, String inputValue) {
		Connection conn = getConnection();
		
		ArrayList<FreeBoard> list = new FreeBoardDao().selectListAscContent(conn,pi,inputValue);
			
		close(conn);
			
		return list;

	}

	/**
	 * 2024.10.30 00:30 김형문
	 * 닉네임 오래된순  검색용 서비스 메소드
	 * @param pi=> 페이징바 처리 정보
	 * @param inputValue => 검색할 닉네임
	 * @return
	 */
	public ArrayList<FreeBoard> selectListAscWriter(PageInfo pi, String inputValue) {
		Connection conn = getConnection();
		
		ArrayList<FreeBoard> list = new FreeBoardDao().selectListAscWriter(conn,pi,inputValue);
			
		close(conn);
			
		return list;

	}

	
////////////////////////////////////////////////////////////////////////////////////////////	
	
	
	/**
	 * 2024.10.31 14:46 김형문
	 * 상세조회시 이전게시글 조회용 서비스 메소드
	 * @param freeNo => 현재게시글
	 * @param searchOption => 검색된 결과인지 아닌지 체크
	 * @param inputValue => 리스트의 검색키워드
	 * @param sorting => 리스트의 정렬
	 * @return
	 */
	public FreeBoard selectPrevFile(int freeNo, String searchOption, String inputValue, String sorting) {
		
		Connection conn = getConnection();
		
		FreeBoard prevF = new FreeBoardDao().selectPrevFile(conn,freeNo,searchOption,inputValue,sorting);
				
		close(conn);
		
		return prevF;
	}

	/**
	 * 2024.10.31 14:46 김형문
	 * 상세조회시 다음게시글 조회용 서비스 메소드
	 * @param freeNo=> 현재게시글
	 * @param searchOption => 검색된 결과인지 아닌지 체크
	 * @param inputValue => 리스트의 검색키워드
	 * @param sorting => 리스트의 정렬
	 * @return
	 */
	public FreeBoard selectNextFile(int freeNo, String searchOption, String inputValue, String sorting) {
		
		Connection conn = getConnection();
		
		FreeBoard nextF = new FreeBoardDao().selectNextFile(conn,freeNo,searchOption,inputValue,sorting);
				
		close(conn);
		return nextF;
		
		
	}

	
////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	
	/**
	 * 2024.11.01 금요일 15:34 김형문
	 * @param f => 수정할 게시글 정보
	 * @param deleteFiles => 삭제할 파일
	 * @param updatdFiles => 업데이트할 파일
	 * @param insertFiles => 새로 추가할 파일
	 * @return => 처리된 행의 갯수
	 */
	public int updateFreeBoard(FreeBoard f, ArrayList<FreeBoardFile> deleteFiles, ArrayList<FreeBoardFile> updateFiles,
			ArrayList<FreeBoardFile> insertFiles) {
		
		
		
		Connection conn = getConnection();
		
		int result = new FreeBoardDao().updateFreeBoard(conn,f);
		
		int result1 = 1;
		if(deleteFiles!=null) {
			result1 = new FreeBoardDao().deleteFreeBoardFiles(conn,deleteFiles);
		}
		
		int result2 = 1;
		if(updateFiles!=null) {
			result2 = new FreeBoardDao().updateFreeBoardFiles(conn,updateFiles);
		}
		
		int result3 = 1;
		if(insertFiles!=null) {
			result3 = new FreeBoardDao().insertNewFreeBoardFiles(conn,insertFiles);
		}
		
		if(result*result1*result2*result3==1) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		
		return result*result1*result2*result3;
	}

	/**
	 * 관리자 페이지 리스트 조회용 서비스 메소드
	 * 
	 * @return => 모든 리스트 페이지
	 */
	public ArrayList<FreeBoard> adminSelctList(PageInfo pi) {
		
		Connection conn = getConnection();
		
		ArrayList<FreeBoard> list = new FreeBoardDao().adminSelctList(conn,pi);
		
		close(conn);
		return list;
	}

	/**
	 * 관리자 페이지, 게시글 (좋아요,댓글,좋아요) 삭제 서비스 메소드
	 * @param freeNo => 삭제할 게시글 번호
	 * @return => 처리된 행의 갯수
	 */
	public int adminDeleteFreeBoard(int freeNo) {
		
		Connection conn=getConnection();
		
		int result2= new FreeBoardDao().adminDeleteFreeBoardComment(conn,freeNo);
		int result3= new FreeBoardDao().adminDeleteFreeBoardLike(conn,freeNo);
		int result4= new FreeBoardDao().adminDeleteFreeBoardFile(conn,freeNo);
		
		int result1 = new FreeBoardDao().adminDeleteFreeBoard(conn,freeNo);
		
		if(result1>0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		close(conn);
		
		return result1;
	}



	
////////////////////////////////////////////////////////////////////////////////////////////
	


	

}//클래스























