package com.fh.freeBoard.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fh.freeBoard.model.service.FreeBoardService;
import com.fh.freeBoard.model.vo.FreeBoardComment;

/**
 * Servlet implementation class AjaxCommentUpdateController
 */
@WebServlet("/updateComment.free")
public class AjaxCommentUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxCommentUpdateController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		
		//int userNo = Integer.parseInt(request.getParameter("userNo"));
		//접근 제한을 걸기위한 변수
		
		int freeCommNo = Integer.parseInt(request.getParameter("freeCommentNo"));
		String freeCommContent =request.getParameter("freeCommentContent"); 
		
		FreeBoardComment fc = new FreeBoardComment();
		fc.setFreeCommNo(freeCommNo);
		fc.setFreeCommContent(freeCommContent);
		
	
		
		int result = new FreeBoardService().updateFreeBoardComment(fc);
		
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(result);
		
	
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
