package com.fh.accompanyBoard.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fh.accompanyBoard.model.service.AccompanyService;
import com.fh.user.model.vo.User;

/**
 * Servlet implementation class AccompanyDeleteController
 */
@WebServlet("/delete.ac")
public class AccompanyDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccompanyDeleteController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 세션에서 로그인된 사용자 정보를 가져옴
	    //HttpSession session = request.getSession();
	    User loginUser = (User) request.getSession().getAttribute("loginUser");
	    String caller = (String) request.getSession().getAttribute("caller"); // 어느 view단에서 삭제 메소드를 호출했는지확인
 
	    if (loginUser == null) {
	    	request.getSession().setAttribute("alertMsg", "로그인한 사용자만 이용 가능한 기능입니다.");
	        response.sendRedirect(request.getContextPath());
	        return;  // 메서드 중단
	    }
		
		// 게시글 삭제 요청 처리 (GET 방식)
		// 글번호 먼저 뽑기 (nac)
		int accomNo 
			= Integer.parseInt(request.getParameter("nac"));
										
		// 글번호를 넘기면서 삭제 요청 처리
		int result 
			= new AccompanyService().deleteAccompany(accomNo);
		
		// 처리 결과에 따른 응답페이지 지정
		if(result > 0) { // 성공
			
			// 일회성 알람문구를 담아서
			// 공지사항 리스트 페이지로 url 재요청
			request.getSession().setAttribute("alertMsg", "성공적으로 게시글이 삭제되었습니다.");
			
			// caller 값에 따라 다른 경로로 리다이렉트
	        if ("admin".equals(caller)) {
	        	response.sendRedirect(request.getContextPath() + "/views/adminTool/adminTool.accompany");
	        } else {
	        	response.sendRedirect(request.getContextPath() + "/list.ac?currentPage=1");
	        }
	     	// 호출 후 세션에서 caller 값 제거
	        request.getSession().removeAttribute("caller");
			
		} else { // 실패
			
			// 에러문구를 담아서 에러페이지로 포워딩
			request.setAttribute("errorMsg", "동행구하기 게시글 삭제에 실패했습니다.");
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
