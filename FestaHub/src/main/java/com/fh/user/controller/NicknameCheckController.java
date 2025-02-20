package com.fh.user.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fh.user.model.service.UserService;

/**
 * Servlet implementation class NicknameCheckController
 */
@WebServlet("/nnc.fh")
public class NicknameCheckController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public NicknameCheckController() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String checkNickname = request.getParameter("checkNickname"); // 수정된 부분
        int count = 0;

        
        
        try {
            count = new UserService().nicknameCheck(checkNickname);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다.");
            return;
        }
        
        response.setContentType("text/plain; charset=UTF-8"); // text/plain으로 변경

        if (count > 0) {
            response.getWriter().print("NNNNN"); // 중복된 경우
        } else {
            response.getWriter().print("NNNNY"); // 사용 가능한 경우
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
