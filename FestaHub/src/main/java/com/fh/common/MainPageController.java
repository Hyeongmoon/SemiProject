package com.fh.common;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fh.common.model.vo.PageInfo;
import com.fh.festival.model.service.FesService;
import com.fh.festival.model.vo.Festival;
import com.fh.notice.model.service.NoticeService;
import com.fh.notice.model.vo.Notice;
import com.fh.user.model.vo.User;

/**
 * Servlet implementation class MainPageController
 */
@WebServlet("/main.fh")
public class MainPageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainPageController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int listCount; 	 // 현재 총 게시글 갯수 (삭제되지 않은 일반게시글의 갯수)
		int currentPage; // 현재 사용자가 보고자 하는 페이지 (즉, 사용자가 요청한 페이지)
		int pageLimit;   // 페이지 하단에 보여질 페이징바의 페이지 최대 갯수
		int boardLimit;	 // 한 페이지에 보여질 게시글의 최대 갯수 (몇개 단위씩 볼거냐)
		
		int maxPage; 	 // 가장 마지막 페이지가 몇번 페이지인지 (즉, 총 페이지 수)
		int startPage;	 // 페이지 하단에 보여질 페이징바의 시작수
		int endPage; 	 // 페이지 하단에 보여질 페이징바의 끝수
		
		// * listCount : 총 게시글 갯수
		listCount = new FesService().selectListCount();
		
		// * currentPage : 현재 사용자가 요청한 페이지
        currentPage = 1;
		
		// * pageLimit : 페이지 하단에 보여질 페이징바의 페이지 최대 갯수
		pageLimit = 5; 
		
		// * boardLimit : 한 페이지에 보여질 게시글의 최대 갯수 (게시글 몇개 단위씩 보여질건지)
		boardLimit = 5;
		
		// * maxPage : 가장 마지막 페이지가 몇번 페이지인지 (즉, 총 페이지 수)
		maxPage = (int)Math.ceil((double)listCount / boardLimit);
		
		// * startPage : 페이지 하단에 보여질 페이징바의 시작수
		startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
		
		// * endPage : 페이지 하단에 보여질 페이징바의 끝수
		endPage = startPage + pageLimit - 1;
		
		if(endPage > maxPage) {
			
			endPage = maxPage;
		}
		
		PageInfo fPi = new PageInfo(listCount, currentPage,
				   pageLimit, boardLimit,
				   maxPage, startPage, endPage);
		
		
		
		int userNo = (request.getSession().getAttribute("loginUser") != null) 
	             ? ((User)request.getSession().getAttribute("loginUser")).getUserNo() 
	             : 0;
		
		ArrayList<Festival> fList 
			= new FesService().selectList(fPi, userNo);
		
		// 응답데이터로 보내기
		request.setAttribute("fList", fList);
		
		listCount = new NoticeService().selectListCount();
		maxPage = (int)Math.ceil((double)listCount / boardLimit);
		startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
		endPage = startPage + pageLimit - 1;
		if(endPage > maxPage) {
			endPage = maxPage;
		}

		PageInfo nPi = new PageInfo(listCount, currentPage,
								   pageLimit, boardLimit,
								   maxPage, startPage, endPage);
		
		ArrayList<Notice> nList 
			= new NoticeService().selectList(nPi);
		
		// 응답데이터 보내기
		request.setAttribute("nList", nList);
		
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
