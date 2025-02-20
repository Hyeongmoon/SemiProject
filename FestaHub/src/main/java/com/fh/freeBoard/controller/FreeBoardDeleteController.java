package com.fh.freeBoard.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fh.freeBoard.model.service.FreeBoardService;
import com.fh.user.model.vo.User;

/**
 * Servlet implementation class FreeBoardDeleteController
 */
@WebServlet("/delete.free")
public class FreeBoardDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FreeBoardDeleteController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		
		
		if ((User)request.getSession().getAttribute("loginUser") != null) {
			

			User loginUser = (User)request.getSession().getAttribute("loginUser");
			int freeNo = Integer.parseInt(request.getParameter("freeNo"));
			int userNo = Integer.parseInt(request.getParameter("userNo"));//로그인유저아이디(글작성자와,로그인유저아이디 체크용)
			
			if(loginUser.getUserNo() == userNo) {
				
				
				
				
				
				int result = new FreeBoardService().deleteFreeBoard(userNo,freeNo);
				
				if(result > 0) {
					
					request.getSession().setAttribute("alertMsg","게시글이 삭제되었습니다.");
					
				}else {
					
					request.getSession().setAttribute("alertMsg","게시글 삭제에 실패했습니다.");
					
				}
				
				response.sendRedirect(request.getContextPath()+"/list.free");
				
			}else {
				
				request.getSession().setAttribute("alertMsg", "잘못된 접근입니다.");
				response.sendRedirect(request.getContextPath()+"/list.free");
			}
			
		
		}else {
			
			request.getSession().setAttribute("alertMsg", "잘못된 접근입니다.");
			response.sendRedirect(request.getContextPath()+"/list.free");
		}
		

		
			
			
	
		
		

		
		
		
		
		//처음에 하지말고 , 게시글 번호랑, 로그인유저 번호(작성자번호) 두개를 보내면 ?
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
