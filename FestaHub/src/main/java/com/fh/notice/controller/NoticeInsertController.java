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
 * Servlet implementation class NoticeInsertController
 */
@WebServlet("/insert.no")
public class NoticeInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 공지사항 작성 요청 처리 (POST 방식)
		
		// 1) POST 방식이므로 인코딩 설정 먼저
		request.setCharacterEncoding("UTF-8");
		
		// 2) 요청 시 전달값을 뽑아서 변수 및 객체에 담기
		// 작성자의 회원번호 : userNo
//		String userNo = request.getParameter("userNo");
		// 공지사항 제목 : noticeTitle
		String noticeTitle = request.getParameter("noticeTitle");
		// 공지사항 내용 : noticeContent
		String noticeContent = request.getParameter("noticeContent");
		
		// Notice 객체로 가공하기!!
		Notice n = new Notice();
//		n.setNoticeWriter(userNo);
		n.setNoticeTitle(noticeTitle);
		n.setNoticeContent(noticeContent);
		
		// 3) Service 로 전달값을 넘기면서 요청 후 결과 받기
		int result = new NoticeService().insertNotice(n);
		
		// 4) 처리된 결과에 따라 사용자가 보게될 응답페이지 지정
		if(result > 0) { // 성공
			
			// 일회성 알람 문구를 담아서 
			// 공지사항 리스트 페이지로 url 재요청
			request.getSession().setAttribute("alertMsg", "성공적으로 공지사항이 등록되었습니다.");
			response.sendRedirect(request.getContextPath() + "/list.no?currentPage=1");
			
		} else { // 실패
			
			// 에러문구를 담아서 에러페이지로 포워딩
			request.setAttribute("errorMsg", "공지사항 등록에 실패했습니다.");
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
