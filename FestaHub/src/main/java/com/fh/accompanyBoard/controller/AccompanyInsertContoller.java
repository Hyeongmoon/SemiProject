package com.fh.accompanyBoard.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fh.accompanyBoard.model.service.AccompanyService;
import com.fh.accompanyBoard.model.vo.Accompany;

/**
 * Servlet implementation class AccompanyInsertContoller
 */
@WebServlet("/insert.ac")
public class AccompanyInsertContoller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccompanyInsertContoller() {
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
		String userNo = request.getParameter("userNo");
		String accomTitle = request.getParameter("accomTitle");
		String accomContent = request.getParameter("accomContent");
		
		// Accompany 객체로 가공하기!!
		Accompany ac = new Accompany();
		ac.setUserNo(userNo);
		ac.setAccomTitle(accomTitle);
		ac.setAccomContent(accomContent);
		
		// 3) Service 로 전달값을 넘기면서 요청 후 결과 받기
		int result = new AccompanyService().insertAccompany(ac);
		
		// 4) 처리된 결과에 따라 사용자가 보게될 응답페이지 지정
		if(result > 0) { // 성공
			
			// 일회성 알람 문구를 담아서 
			// 공지사항 리스트 페이지로 url 재요청
			request.getSession().setAttribute("alertMsg", "성공적으로 동행구하기 게시글이 등록되었습니다.");
			response.sendRedirect(request.getContextPath() + "/list.ac?currentPage=1");
			
		} else { // 실패
			
			// 에러문구를 담아서 에러페이지로 포워딩
			request.setAttribute("errorMsg", "동행구하기 게시글 등록에 실패했습니다.");
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
