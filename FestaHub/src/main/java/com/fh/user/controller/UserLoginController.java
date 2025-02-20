package com.fh.user.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fh.user.model.service.UserService;
import com.fh.user.model.vo.User;

/**
 * Servlet implementation class UserLoginController
 */
@WebServlet("/login.fh")
public class UserLoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserLoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		String redirectURL = request.getParameter("redirectURL");
		
		String saveId = request.getParameter("saveId");
		
		if(saveId != null && saveId.equals("y")) {
			
			Cookie cookie = new Cookie("saveId", userId);
			cookie.setMaxAge(1 * 24 * 60 * 60); // 1일 (초단위)
			
			response.addCookie(cookie);
			
		} else {
			Cookie cookie = new Cookie("saveId", userId);
			cookie.setMaxAge(0);
			
			response.addCookie(cookie);
		}
		
		User u = new User();
		u.setUserId(userId);
		u.setUserPwd(userPwd);
		
		User loginUser = new UserService().loginUser(u);
		HttpSession session = request.getSession();
		
		if(loginUser == null) {
			
			session.setAttribute("alertMsg", "로그인에 실패했습니다. 다시 시도해주세요.");
			
	        if (redirectURL != null && !redirectURL.isEmpty()) {
	            response.sendRedirect(redirectURL);
	        } else {
	            response.sendRedirect(request.getContextPath());
	        }
			
		} else {

			session.setAttribute("loginUser", loginUser);

	        if (redirectURL != null && !redirectURL.isEmpty()) {
	            response.sendRedirect(redirectURL);
	        } else {
	            response.sendRedirect(request.getContextPath());
	        }
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