package com.fh.festival.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fh.festival.model.service.FesService;
import com.fh.festival.model.vo.FestivalComment;
import com.fh.user.model.vo.User;

/**
 * Servlet implementation class fesCommentInsertController
 */
@WebServlet("/cinsert.fe")
public class FesCommentInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FesCommentInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 댓글 작성 요청 처리 (POST)
		// > ajax는 기본적으로 post 방식이어도 UTF-8로 요청이 들어옴
		
		// 요청 시 전달값 뽑기
		// - 참조게시글 번호 : fesNo
		int fesNo = Integer.parseInt(request.getParameter("fesNo"));
		// - 댓글 내용 : commContent
		String fesCommContent = request.getParameter("fesCommContent");
		
		// + 작성자(현재 로그인한 회원)의 회원번호 얻어오기
		int fesCommWriter 
			= ((User)request.getSession().getAttribute("loginUser")).getUserNo();
		
		// Reply 타입의 객체에 담기
		FestivalComment fc = new FestivalComment();
		fc.setFesNo(fesNo);
		fc.setFesCommContent(fesCommContent);
		fc.setFesCommWriter(fesCommWriter + "");
		
		int result = new FesService().insertComm(fc);
		
		// result를 응답데이터로 곧바로 넘겨보기
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(result); // 1(성공) 또는 0(실패)
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
