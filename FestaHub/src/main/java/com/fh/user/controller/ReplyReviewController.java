package com.fh.user.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fh.common.model.vo.PageInfo;
import com.fh.freeBoard.model.vo.FreeBoard;
import com.fh.reviewBoard.model.vo.ReviewComment;
import com.fh.user.model.service.UserService;
import com.fh.user.model.vo.User;

/**
 * Servlet implementation class MyPageListController
 */
@WebServlet("/rprv.fh")
public class ReplyReviewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReplyReviewController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int listCount;
		int currentPage;
		int pageLimit;
		int boardLimit;
		
		int maxPage;
		int startPage;
		int endPage;
		
		HttpSession session = request.getSession();
		User loginUser = (User)session.getAttribute("loginUser");
		int userNo = loginUser.getUserNo();
		
		listCount = new UserService().selectRpRvCount(userNo);				//총 게시글 수
		currentPage 
		= (request.getParameter("currentPage") != null ? Integer.parseInt(request.getParameter("currentPage")) : 1);
		
		pageLimit = 5;
		boardLimit = 10;
		
		maxPage = (int)Math.ceil((double)listCount / boardLimit);
		startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
		endPage = startPage + pageLimit - 1;
		if(endPage > maxPage) {
			
			endPage = maxPage;
		}
		
		
		PageInfo pi = new PageInfo(listCount,currentPage,pageLimit,boardLimit,maxPage,startPage,endPage);
		
		ArrayList<ReviewComment> list = new UserService().selectRpRv(pi, userNo);
		
		
		
		request.setAttribute("list", list);
		request.setAttribute("pi", pi);
		
		request.getRequestDispatcher("views/user/rpReview.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
