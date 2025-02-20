package com.fh.message.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SendMessageFormController
 */
@WebServlet("/sendMsgForm.me")
public class SendMessageFormController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendMessageFormController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 로그인한 회원만 보내기 가능하게 처리
		HttpSession session = request.getSession();
		if(session.getAttribute("loginUser") != null) {
		
			// 동행구하기 게시글 작성 폼으로 포워딩
			request.getRequestDispatcher("views/message/sendMessageForm.jsp")
											.forward(request, response);
			
		} else {
			
			session.setAttribute("alertMsg", "로그인 한 회뭔만 이용 가능한 페이지 입니다.");			
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
