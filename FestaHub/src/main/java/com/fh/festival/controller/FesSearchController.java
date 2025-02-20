package com.fh.festival.controller;

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
import com.fh.user.model.vo.User;

/**
 * Servlet implementation class FesSearchController
 */
@WebServlet("/search.fe")
public class FesSearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FesSearchController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        String category = request.getParameter("category");
        String keyword = request.getParameter("keyword");
        
		int listCount; 	 // 현재 총 게시글 갯수 (삭제되지 않은 일반게시글의 갯수)
		int currentPage; // 현재 사용자가 보고자 하는 페이지 (즉, 사용자가 요청한 페이지)
		int pageLimit;   // 페이지 하단에 보여질 페이징바의 페이지 최대 갯수
		int boardLimit;	 // 한 페이지에 보여질 게시글의 최대 갯수 (몇개 단위씩 볼거냐)
		
		int maxPage; 	 // 가장 마지막 페이지가 몇번 페이지인지 (즉, 총 페이지 수)
		int startPage;	 // 페이지 하단에 보여질 페이징바의 시작수
		int endPage; 	 // 페이지 하단에 보여질 페이징바의 끝수
		
		listCount = new FesService().selectSearchCount(category, keyword);
        currentPage = (request.getParameter("currentPage") != null) 
        		? Integer.parseInt(request.getParameter("currentPage")) : 1;
//		Integer sessionCurrentPage = (Integer) request.getSession().getAttribute("currentPage");
//		currentPage = (sessionCurrentPage != null) ? sessionCurrentPage : 1;
		pageLimit = 5; 
		boardLimit = 10;
		maxPage = (int)Math.ceil((double)listCount / boardLimit);
		startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
		endPage = startPage + pageLimit - 1;
		
		if(endPage > maxPage) {
			
			endPage = maxPage;
		}
		
		PageInfo pi = new PageInfo(listCount, currentPage,
				   pageLimit, boardLimit,
				   maxPage, startPage, endPage);
		
		int userNo = (request.getSession().getAttribute("loginUser") != null) 
	             ? ((User)request.getSession().getAttribute("loginUser")).getUserNo() 
	             : 0;

        ArrayList<Festival> list = new FesService().searchFes(category, keyword, pi, userNo);

        request.setAttribute("listCount", listCount);
        request.setAttribute("list", list);
        request.setAttribute("pi", pi);
        request.getRequestDispatcher("views/festival/festivalListView.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
