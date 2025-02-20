package com.fh.accompanyBoard.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fh.accompanyBoard.model.service.AccompanyService;
import com.fh.accompanyBoard.model.vo.Accompany;

/**
 * Servlet implementation class AccompanyUpdateFormController
 */
@WebServlet("/updateForm.ac")
public class AccompanyUpdateFormController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccompanyUpdateFormController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 해당 공지사항 게시글의 글번호 (nac) 먼저 뽑기
		int accomNo 
			= Integer.parseInt(request.getParameter("nac")); 

		Accompany ac 
			= new AccompanyService().selectAccompany(accomNo);

		// 응답데이터로 넘기기
		request.setAttribute("ac", ac);
		
		// 공지사항 수정하기 페이지 포워딩
		// (views 의 notice 폴더의 noticeUpdateForm.jsp)
		request.getRequestDispatcher("views/accompanyBoard/accompanyUpdateForm.jsp")
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
