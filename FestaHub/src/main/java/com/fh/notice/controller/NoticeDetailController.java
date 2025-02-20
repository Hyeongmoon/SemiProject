package com.fh.notice.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fh.notice.model.service.NoticeService;
import com.fh.notice.model.vo.Notice;

/**
 * Servlet implementation class NoticeDetailController
 */
@WebServlet("/detail.no")
public class NoticeDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeDetailController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 글번호 먼저 뽑기 (nno)
		int noticeNo 
			= Integer.parseInt(request.getParameter("nno"));
		
		NoticeService nService = new NoticeService();
		
		// 조회수 증가
		int result = nService.increaseCount(noticeNo);
		
		// 조회수 증가에 성공했다면, 상세조회 데이터 가져오기
		if(result > 0) { // 성공
			
			// 게시글 상세조회 요청 다녀오기
			Notice n = nService.selectNotice(noticeNo);
			// 이전게시글
			Notice prevn = nService.selectPreviousNotice(noticeNo);
			// 다음게시글
			Notice nextn = nService.selectNextNotice(noticeNo);
					
			// 조회해온 게시글과 첨부파일 정보를 응답데이터로 넘겨서
			// 응답페이지인 boardDetailView.jsp 로 포워딩
			request.setAttribute("n", n);
			request.setAttribute("prevn", prevn);
			request.setAttribute("nextn", nextn);
			
			// 상세조회 페이지로 포워딩
			request.getRequestDispatcher("views/notice/noticeDetailView.jsp")
												.forward(request, response);
			
		} else { // 실패
			
			// 에러문구를 담아서 에러페이지로 포워딩
			request.setAttribute("errorMsg", "게시글 상세조회에 실패했습니다.");
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
