package com.fh.user.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fh.user.model.service.UserService;

/**
 * Servlet implementation class FinalCheckController
 */
@WebServlet("/fc.fh")
public class FinalCheckController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FinalCheckController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String fcId = request.getParameter("fcId");
		int count1 = new UserService().fcIdcheck(fcId);
		
		String fcPwd1 = request.getParameter("fcPwd1");
		int count2 = new UserService().fcPwdcheck1(fcPwd1);
		
		String fcNickname = request.getParameter("fcNickname");
		int count3 = new UserService().fcNicknamecheck(fcNickname);
		
		String fcName = request.getParameter("fcName");
		int count4 = new UserService().fcNamecheck(fcName);
		
		response.setContentType("text/html; charset=UTF-8");
		
		if	(count1 > 0 && count2 > 0 && count3 > 0 && count4 > 0) {
				response.getWriter().print("NNNNN");
		} else {
			response.getWriter().print("NNNNY");
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
