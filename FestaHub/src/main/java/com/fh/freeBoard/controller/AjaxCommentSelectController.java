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
import com.google.gson.Gson;

/**
 * Servlet implementation class AjaxCommentSelectController
 */
@WebServlet("/selectComment.free")
public class AjaxCommentSelectController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxCommentSelectController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int freeCommNo = Integer.parseInt(request.getParameter("freeCommNo"));
		
		
		
		
		
		
		FreeBoardComment fc = new FreeBoardService().selectFreeBoardComment(freeCommNo);
		

		
		response.setContentType("application/json;charset=UTF-8");
		
		new Gson().toJson(fc,response.getWriter());
	
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
