package com.fh.adminTool.Controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fh.user.model.service.UserService;

@WebServlet("/adminTool.blacklistrfr")
public class AdminUserBlackListRfrController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	int userNo = Integer.parseInt(request.getParameter("userNo"));
        String reason = request.getParameter("reason");

        UserService userService = new UserService();
        boolean isRegistered = userService.registerBlackListReason(userNo, reason);
        String message;

        if (isRegistered) {
            String blackListRegDate = userService.getBlackListRegDate(userNo);
            message = "블랙리스트 사유가 등록되었습니다.";
            // JSON 형태로 응답 데이터 준비
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print("{\"message\": \"" + message + "\", \"blackListRegDate\": \"" + blackListRegDate + "\"}");
            out.flush();
        } else {
            message = "블랙리스트 사유 등록에 실패했습니다.";
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message);
        }
}
}
