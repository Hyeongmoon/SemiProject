package com.fh.adminTool.Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fh.freeBoard.model.service.FreeBoardService;
import com.fh.user.model.vo.User;

/**
 * Servlet implementation class AdminFreeDeleteController
 */


@WebServlet("/views/adminTool/adminDelete.free")
public class AdminFreeDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminFreeDeleteController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
	
		User loginUser = ((User)request.getSession().getAttribute("loginUser"));
		
		
		
		if(loginUser!=null && loginUser.getUserNo() == 1) {
			
			
			
			int freeNo = Integer.parseInt(request.getParameter("freeNo"));
			
			int result = new FreeBoardService().adminDeleteFreeBoard(freeNo);
			
			if(result>0) {
				
				//서버에 있는 파일 삭제해주는 구문 필요 !!!
				
				request.setAttribute("alertMsg","삭제에 성공했습니다.");
				
				
			}else {
				request.setAttribute("errorMsg","삭제에 실패했습니다.");
			}
			
			response.sendRedirect("adminTool.free");
			
		}else {
			
		
			request.setAttribute("alertMsg", "잘못된 접근입니다.");
			request.getRequestDispatcher("adminFreeBoardListView.jsp").forward(request, response);			
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
