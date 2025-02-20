package com.fh.adminTool.Controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fh.user.model.vo.User;

/**
 * Servlet implementation class AdminToolController
 */
@WebServlet("/views/adminTool/admin.fh")
public class AdminToolController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminToolController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		User loginUser = (User)request.getSession().getAttribute("loginUser");
		String redirectURL = request.getParameter("redirectURL");
		
		if(loginUser != null && loginUser.getUserId().equals("admin")) {
			
			request.getRequestDispatcher("adminTool.jsp").forward(request, response);
		} else {

			request.getSession().setAttribute("alertMsg", "잘못된 접근입니다.");
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
