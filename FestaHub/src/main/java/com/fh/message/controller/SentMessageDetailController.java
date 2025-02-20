package com.fh.message.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fh.message.model.service.MessageService;
import com.fh.message.model.vo.Message;
import com.fh.user.model.vo.User;

/**
 * Servlet implementation class SentMessageDetailController
 */
@WebServlet("/detail.sme")
public class SentMessageDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SentMessageDetailController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 보낸 쪽지 상세보기 요청 처리
		// 쪽지 번호 nsme 뽑기
		int msgNo
			= Integer.parseInt(request.getParameter("nsme"));
		// 로그인한 유저번호 가져오기
		HttpSession session = request.getSession();
		User loginUser = (User) session.getAttribute("loginUser");
		int userNo = loginUser.getUserNo();
		
		// 해당 쪽지 정보 하나만 조회해오기
		Message msg
			= new MessageService().selectSentMessage(msgNo);
		// 이전 쪽지
		Message prevMsg =
			new MessageService().selectPreviousSentMessage(msgNo, userNo);
		// 다음 쪽지
		Message nextMsg =
			new MessageService().selectNextSentMessage(msgNo, userNo);
		
		// 조회해온 게시글 정보를 응답데이터로 넘기기
		request.setAttribute("msg", msg);
		request.setAttribute("prevMsg", prevMsg);
		request.setAttribute("nextMsg", nextMsg);

		// 받은 쪽지 상세보기 페이지 포워딩
		request.getRequestDispatcher("views/message/sentMessageDetailView.jsp")
														.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
