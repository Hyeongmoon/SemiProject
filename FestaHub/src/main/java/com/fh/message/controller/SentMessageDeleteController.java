package com.fh.message.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fh.message.model.service.MessageService;

/**
 * Servlet implementation class SentMessageDeleteController
 */
@WebServlet("/delete.sme")
public class SentMessageDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SentMessageDeleteController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 받은 쪽지 삭제 요청 처리 (GET 방식)
		// 글번호 먼저 뽑기 (nsme)
		int msgNo 
			= Integer.parseInt(request.getParameter("nsme"));
										
		// 글번호를 넘기면서 삭제 요청 처리
		int result 
			= new MessageService().deleteSentMessage(msgNo);
		
		// 처리 결과에 따른 응답페이지 지정
		if(result > 0) { // 성공
			
			// 일회성 알람문구를 담아서
			// 공지사항 리스트 페이지로 url 재요청
			request.getSession().setAttribute("alertMsg", "성공적으로 보낸 쪽지가 삭제되었습니다.");
			response.sendRedirect(request.getContextPath() + "/list.sme?currentPage=1");
			
		} else { // 실패
			
			// 에러문구를 담아서 에러페이지로 포워딩
			request.setAttribute("errorMsg", "받은 쪽지 삭제에 실패했습니다.");
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
