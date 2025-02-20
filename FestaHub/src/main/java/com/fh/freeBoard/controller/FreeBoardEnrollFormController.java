package com.fh.freeBoard.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fh.user.model.vo.User;

/**
 * Servlet implementation class FreeBoardEnrollFormController
 */
@WebServlet("/enrollForm.free")
public class FreeBoardEnrollFormController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FreeBoardEnrollFormController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		
		//로그인을 했을경우만 넘어가게 조건 걸기

		User loginUser = (User)request.getSession().getAttribute("loginUser");
		if(loginUser==null) {
			request.getSession().setAttribute("alertMsg","로그인이 필요합니다.");
			response.sendRedirect(request.getContextPath()+"/list.free");
			return;
		}
		
		
		
		//게시판 작성 페이지 띄어주기
		request.getRequestDispatcher("views/freeBoard/freeBoardEnrollForm.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
