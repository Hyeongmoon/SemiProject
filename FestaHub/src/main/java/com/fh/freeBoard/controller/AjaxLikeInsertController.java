package com.fh.freeBoard.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fh.freeBoard.model.service.FreeBoardService;
import com.fh.freeBoard.model.vo.FreeBoardLike;
import com.fh.user.model.vo.User;

/**
 * Servlet implementation class AjaxLikeInsertController
 */
@WebServlet("/insertLike.free")
public class AjaxLikeInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxLikeInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User loginUser = (User)request.getSession().getAttribute("loginUser");
		int result = 0;
		
		if(loginUser!=null) { //로그인한경우만
			
			int freeNo = Integer.parseInt(request.getParameter("freeNo")); //게시글번호
			int userNo = loginUser.getUserNo();								//로그인유저번호
			
			FreeBoardLike fl = new FreeBoardLike(userNo,freeNo);
			
			result = new FreeBoardService().insertLike(fl);
			
		}
		
		//성공시 result = 1
		//실패시 result = 0
		
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(result);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
