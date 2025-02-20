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
 * Servlet implementation class SendMessageController
 */
@WebServlet("/sendMsg.me")
public class SendMessageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendMessageController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 동행구하기 게시글 작성 요청 처리 (POST 방식)
		// 1) POST 방식 인코딩 설정
		request.setCharacterEncoding("UTF-8");
		
		
		// 2) 요청 시 전달값을 뽑아서 변수 및 객체에 담기
		HttpSession session = request.getSession();
		User loginUser = (User)session.getAttribute("loginUser");
		int senderNo = loginUser.getUserNo(); // 작성자 유저넘버
		String userNickname = request.getParameter("userNickname"); // 받는 사람 닉네임 db등록할 때 넘버로 조인
		String msgContent = request.getParameter("msgContent"); // 쪽지 내용
		
		// Message 객체로 가공
		Message me = new Message();
		me.setSenderNo(senderNo);
		me.setUserNickname(userNickname);
		me.setMsgContent(msgContent);
		
		// 3) Service 로 전달값을 넘기면서 요청 후 결과 받기
		int result = new MessageService().insertMessage(me);
		
		// 4) 처리된 결과에 따라 사용자가 보게될 응답페이지 지정
		if(result > 0) { // 성공
			
			// 일회성 알람 문구를 담아서 
			// 보낸 메세지 리스트뷰 페이지로 url 재요청
			request.getSession().setAttribute("alertMsg", "쪽지 발송을 성공했습니다.");
			response.sendRedirect(request.getContextPath() + "/list.sme?currentPage=1");
			
		} else { // 실패
			
			// 에러문구를 담아서 에러페이지로 포워딩
			request.setAttribute("errorMsg", "쪽지 발송을 실패했습니다.");
			request.getRequestDispatcher("views/common/errorPage.jsp")
											.forward(request, response);
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
