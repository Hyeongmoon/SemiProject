package com.fh.accompanyBoard.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fh.accompanyBoard.model.service.AccompanyService;
import com.fh.accompanyBoard.model.vo.Accompany;
import com.fh.notice.model.vo.Notice;

/**
 * Servlet implementation class AccompanyDetailController
 */
@WebServlet("/detail.ac")
public class AccompanyDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccompanyDetailController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 동행구하기 게시글 상세보기 요청 처리
		// 게시글번호 nac 뽑기
		int accomNo 
			= Integer.parseInt(request.getParameter("nac")); // "1" --> 1
		
		AccompanyService aService = new AccompanyService();
		
		// 조회수 증가
		int result 
			= aService.increaseCount(accomNo);
		
		// 조회수 증가 성공 시 해당 게시글 한건만 불러와서 응답데이터로 넘겨줘야 함!!
		if(result > 0) { // 조회수 증가에 성공했을 경우
			
			// 해당 게시글 정보를 담아서 상세보기 페이지로 포워딩
			
			// 해당 게시글 정보 하나만 조회해오기
			Accompany ac =
				aService.selectAccompany(accomNo);
			// 이전게시글
			Accompany prevAc = 
				aService.selectPreviousAccompany(accomNo);
			// 다음게시글
			Accompany nextAc = 
				aService.selectNextAccompany(accomNo);
			
			// 조회해온 게시글 정보를 응답데이터로 넘기기
			request.setAttribute("ac", ac);
			request.setAttribute("prevAc", prevAc);
			request.setAttribute("nextAc", nextAc);
			

			// 동행구하기게시글 상세보기 페이지 포워딩
			request.getRequestDispatcher("views/accompanyBoard/accompanyDetailView.jsp")
															.forward(request, response);
			
		} else { // 조회수 증가에 실패했을 경우
			
			// 에러문구를 담아서 에러페이지로 포워딩
			
			request.setAttribute("errorMsg", "동행구하기 게시글 상세조회에 실패했습니다.");
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
