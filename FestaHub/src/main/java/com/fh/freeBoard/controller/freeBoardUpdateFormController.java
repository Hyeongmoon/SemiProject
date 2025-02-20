package com.fh.freeBoard.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fh.freeBoard.model.service.FreeBoardService;
import com.fh.freeBoard.model.vo.FreeBoard;
import com.fh.freeBoard.model.vo.FreeBoardFile;
import com.fh.user.model.vo.User;

/**
 * Servlet implementation class freeBoardUpdateFormController
 */
@WebServlet("/updateForm.free")
public class freeBoardUpdateFormController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public freeBoardUpdateFormController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User loginUser = (User)request.getSession().getAttribute("loginUser");
		int userNo = Integer.parseInt(request.getParameter("userNo")); // 접속한 회원과 글쓴이가 같은지 체크
		int freeNo = Integer.parseInt(request.getParameter("freeNo"));
		

		
		if(loginUser!=null && loginUser.getUserNo() == userNo) {//로그인한 회원과 글 작성자와 같을경우만 폼화면 띄어주기
			
			FreeBoard f = new FreeBoardService().selectFreeBoard(freeNo);
			ArrayList<FreeBoardFile> files = new FreeBoardService().selectFreeBoardFile(freeNo); 
			
			request.setAttribute("f", f);
			request.setAttribute("files", files);
			
			request.getRequestDispatcher("views/freeBoard/freeBoardUpdateForm.jsp").forward(request, response);

			
			
		}else { //로그인 안했거나, 글작성자와 다를경우
			
			request.getSession().setAttribute("alertMsg", "잘못된 접근입니다.");
			response.sendRedirect(request.getContextPath()+"/list.free");
		}
		
		//로그인한 회원이, 작성자와 같은경우 
		//기존 상세보기 실행해서, 정보랑 같이 넘겨주기
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
