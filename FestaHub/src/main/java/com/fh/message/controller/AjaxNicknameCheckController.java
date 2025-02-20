package com.fh.message.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fh.message.model.service.MessageService;

/**
 * Servlet implementation class AjaxNicknameCheckController
 */
@WebServlet("/nicknameChenk.me")
public class AjaxNicknameCheckController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxNicknameCheckController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 쪽지 발송 시 닉네임 체크
		
		// 검사할 닉네임 : checkNickname
		String checkNickname = request.getParameter("checkNickname");
		
		// Service 로 전달값을 넘기면서 요청 후 결과 받기
		int count = new MessageService().nicknameCheck(checkNickname);
		
		// 처리 결과에 따라 응답데이터를 넘기기
		response.setContentType("text/html; charset=UTF-8");
		
		if(count > 0) { // 스테이터스가 y인 유저의 닉네임중에 중복되는 유저가있다면 유효한 닉네임
					
			// 사용 불가일 경우 "NNNNY" 을 응답데이터로 넘기기
			response.getWriter().print("NNNNY");
					
		} else { // 존재하는 아이디가 없을 경우 쪽지 발송 불가
			
			// 유효하지 않은 닉네임이므로 쪽지발송 불가능
			response.getWriter().print("NNNNN");
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
