package com.fh.freeBoard.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fh.freeBoard.model.service.FreeBoardService;
import com.fh.freeBoard.model.vo.FreeBoardComment;
import com.fh.user.model.vo.User;

/**
 * Servlet implementation class AjaxCommentInsertController
 */
@WebServlet("/commentInsert.free")
public class AjaxCommentInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxCommentInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int freeNo = Integer.parseInt(request.getParameter("freeNo"));
		int userNo = ((User)request.getSession().getAttribute("loginUser")).getUserNo();
		String freeCommContent = request.getParameter("comment");

		
		FreeBoardComment fc = new FreeBoardComment();
		fc.setFreeNo(freeNo);
		fc.setUserNo(userNo);
		fc.setFreeCommContent(freeCommContent);
		
		int result = new FreeBoardService().insertComment(fc);
		
		response.setContentType("text/html;charset=UTF-8");
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
