package com.fh.user.controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fh.user.model.service.UserService;
import com.fh.user.model.vo.User;

/**
 * Servlet implementation class MemberInsertController
 */
@WebServlet("/insert.fh")
public class UserInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		request.setCharacterEncoding("UTF-8");
		
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("pwd1");
		String userNickname = request.getParameter("userNickname");
		String userName = request.getParameter("userName");
		String birthDateParam = request.getParameter("birthDate");
		Date birthDate = null;
		if (birthDateParam != null && !birthDateParam.isEmpty()) {
		    try {
		        birthDate = Date.valueOf(birthDateParam);
		        // birthDate를 사용한 추가 로직
		    } catch (IllegalArgumentException e) {
		        // 잘못된 날짜 형식 처리
		        System.out.println("유효하지 않은 날짜 형식입니다: " + birthDateParam);
		    }
		} else {
		    // birthDate 파라미터가 null 또는 비어있을 때 처리
		    System.out.println("생년월일이 제공되지 않았습니다.");
		    birthDate = null; // null 값 설정
		}
		
		String phone = request.getParameter("phoneNo");
		
		String email = "";
		if(request.getParameter("email1") != null && request.getParameter("email2") != null) {
			email =  request.getParameter("email1") + "@" + request.getParameter("email2");
		}
		
		
		String address = request.getParameter("address");
		
		User u = new User(userId,  userPwd, userNickname, userName, birthDate, phone, email,
				address);
		
		int result = new UserService().insertUser(u);
		
		if(result > 0) {
			HttpSession session = request.getSession();
			session.setAttribute("alertMsg", "회원가입에 성공했습니다.");
			
			response.sendRedirect(request.getContextPath() + "/views/user/enrollFormSuccess.jsp");
		} else {
			request.setAttribute("errorMsg", "회원가입에 실패했습니다.");
			
			RequestDispatcher view = request.getRequestDispatcher("views/common/errorPage.jsp");
		
			view.forward(request, response);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	}