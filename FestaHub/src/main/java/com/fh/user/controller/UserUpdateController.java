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

/**
 * Servlet implementation class UserUpdateController
 */
@WebServlet("/update.fh")
public class UserUpdateController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserUpdateController() {
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
        String userNickname = request.getParameter("userNickname");
        String userName = request.getParameter("userName");
        
        String phone = request.getParameter("phoneNo");
        
        // 이메일을 null로 초기화한 후 조건부로 설정
        String email1 = request.getParameter("email1");
        String email2 = request.getParameter("email2");
        String email = (email1 != null && email2 != null) ? email1 + "@" + email2 : null; // 이메일이 없을 경우 null로 설정
        
        String address = request.getParameter("address");

        User u = new User(userId, userPwd, userNickname, userName, phone, email, address);

        User afterUpdate = new UserService().updateUser(u);
        
        if(afterUpdate == null) {
            request.setAttribute("errorMsg", "회원정보 수정에 실패했습니다");
            RequestDispatcher view = request.getRequestDispatcher("views/common/errorPage.jsp");
            view.forward(request, response);
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("alertMsg", "성공적으로 회원정보를 수정했습니다.");
            session.setAttribute("loginUser", afterUpdate);
            response.sendRedirect(request.getContextPath() + "/mypage.fh");
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
