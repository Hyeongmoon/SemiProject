package com.fh.notice.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fh.notice.model.service.NoticeService;
import com.fh.notice.model.vo.Notice;
import com.fh.user.model.vo.User;

/**
 * Servlet implementation class NoticeUpdateFormController
 */
@WebServlet("/updateForm.no")
public class NoticeUpdateFormController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeUpdateFormController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 해당 공지사항 게시글의 글번호 (nno) 먼저 뽑기
		int noticeNo 
			= Integer.parseInt(request.getParameter("nno"));
		
		Notice n 
			= new NoticeService().selectNotice(noticeNo);
		
		// 로그인한 회원 이면서 admin 만 보여지게 처리
		HttpSession session = request.getSession();
		if(session.getAttribute("loginUser") != null 
				&& ((User)(session.getAttribute("loginUser"))).getUserId().equals("admin")) {
		
			request.setAttribute("n", n);
			request.getRequestDispatcher("views/notice/noticeUpdateForm.jsp")
												.forward(request, response);
			
		} else {
			
			session.setAttribute("alertMsg", "관리자만 이용 가능한 페이지 입니다.");
			response.sendRedirect(request.getContextPath());
			
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
