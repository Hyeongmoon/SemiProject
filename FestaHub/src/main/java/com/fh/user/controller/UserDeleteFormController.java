package com.fh.user.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fh.user.model.service.UserService;
import com.fh.user.model.vo.User;

@WebServlet("/deleteform.fh")
public class UserDeleteFormController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public UserDeleteFormController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("loginUser") == null) {
            session = request.getSession();
            session.setAttribute("alertMsg", "로그인 후 이용 가능한 서비스입니다.");
            response.sendRedirect(request.getContextPath() + "/views/common/navbar.jsp");
            return;
        }

       
        String userPwd = request.getParameter("userPwd");
        User loginUser = (User) session.getAttribute("loginUser");
        String userId = loginUser.getUserId();
        
        
        User u = new User();
        u.setUserId(userId);
        u.setUserPwd(userPwd);

        int result = new UserService().deleteUser(u);

        if (result > 0) {
            session.setAttribute("alertMsg", "회원탈퇴되었습니다. 그동안 이용해 주셔서 감사합니다.");
            session.removeAttribute("loginUser");
            response.sendRedirect(request.getContextPath());
        } else {
            request.setAttribute("errorMsg", "회원 탈퇴에 실패했습니다.");
            RequestDispatcher view = request.getRequestDispatcher("views/common/errorPage.jsp");
            view.forward(request, response);
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}