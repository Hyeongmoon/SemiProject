package com.fh.message.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fh.common.model.vo.PageInfo;
import com.fh.message.model.service.MessageService;
import com.fh.message.model.vo.Message;
import com.fh.user.model.vo.User;

/**
 * Servlet implementation class ReceivedMessageListController
 */
@WebServlet("/list.rme")
public class ReceivedMessageListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReceivedMessageListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 받은 메세지의 기준이될 로그인 유저의 userNo 뽑기
		HttpSession session = request.getSession();
		int userNo = ((User) (session.getAttribute("loginUser"))).getUserNo();
		

		// ---- 페이징 처리 ----
		int listCount; 	 // 현재 총 게시글 갯수 (삭제되지 않은 일반게시글의 갯수)
		int currentPage; // 현재 사용자가 보고자 하는 페이지 (즉, 사용자가 요청한 페이지)
		int pageLimit;   // 페이지 하단에 보여질 페이징바의 페이지 최대 갯수
		int boardLimit;	 // 한 페이지에 보여질 게시글의 최대 갯수 (몇개 단위씩 볼거냐)
		
		int maxPage; 	 // 가장 마지막 페이지가 몇번 페이지인지 (즉, 총 페이지 수)
		int startPage;	 // 페이지 하단에 보여질 페이징바의 시작수
		int endPage; 	 // 페이지 하단에 보여질 페이징바의 끝수
		
		// 받은 쪽지 갯수 가져오기
		listCount = new MessageService().selectReceivedMessageListCount(userNo);

		// 현재페이지 (쿼리스트링) 가져오기
		currentPage 
			= Integer.parseInt(request.getParameter("currentPage"));
		
		pageLimit = 5;
		boardLimit = 10;
		
		maxPage = (int)Math.ceil((double)listCount / boardLimit);
		startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
		endPage = startPage + pageLimit - 1;
		if(endPage > maxPage) {
			endPage = maxPage;
		}

		PageInfo pi = new PageInfo(listCount, currentPage,
								   pageLimit, boardLimit,
								   maxPage, startPage, endPage);
		// ---- 페이징 처리 ----
		
		// pi를 전달하면서 Service로 요청 후 결과 받기
		ArrayList<Message> list 
			= new MessageService().selecReceivedMessagetList(pi, userNo);
		
		// 응답데이터 보내기
		request.setAttribute("list", list);
		request.setAttribute("pi", pi);

		
		// 받은 메세지 리스트 조회 페이지에 포워딩 receivedMessageListView.jsp
		request.getRequestDispatcher("views/message/receivedMessageListView.jsp")
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
