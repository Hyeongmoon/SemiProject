package com.fh.adminTool.Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import com.fh.user.model.service.UserService;

@WebServlet("/adminTool.userstatus")
public class AdminUserBlackListManagerController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 요청으로부터 회원 번호와 상태 값을 받기
        int userNo = Integer.parseInt(request.getParameter("userNo"));
        String status = request.getParameter("status");

        // 2. 상태 값 유효성 검증
        if (!status.equals("Y") && !status.equals("N")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid status value");
            return;
        }

        // 3. UserService를 호출하여 상태를 업데이트
        UserService userService = new UserService();
        boolean result = userService.updateUserStatus(userNo, status);

        // 4. JSON 객체를 만들어 AJAX 요청에 응답하기
        JSONObject jsonResponse = new JSONObject();
        if (result) {
            jsonResponse.put("message", "회원 상태가 성공적으로 변경되었습니다.");
            // 추가: 업데이트된 사용자 정보 (예: 상태) 필요 시 여기서 가져올 수 있음
        } else {
            jsonResponse.put("message", "회원 상태 변경에 실패했습니다.");
        }

        // 5. 응답으로 JSON 데이터를 전송
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(jsonResponse.toJSONString());
    }
}
