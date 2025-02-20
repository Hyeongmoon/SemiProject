package com.fh.festival.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fh.festival.model.service.FesService;

/**
 * Servlet implementation class FesDeleteController
 */
@WebServlet("/delete.fe")
public class FesDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FesDeleteController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int fesNo = Integer.parseInt(request.getParameter("fesNo"));
		
		int result = new FesService().deleteFes(fesNo);

		if(result > 0) { // 성공
			
			request.getSession().setAttribute("alertMsg", "성공적으로 게시글이 삭제되었습니다.");
			
			response.sendRedirect(request.getContextPath() + "/list.fe?currentPage=1");
			
		} else { // 실패
			
			// 에러문구를 담아서 에러페이지로 포워딩
			request.setAttribute("errorMsg", "게시글 삭제에 실패했습니다.");
			
			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
			
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
