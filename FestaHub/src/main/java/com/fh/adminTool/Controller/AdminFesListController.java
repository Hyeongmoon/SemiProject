package com.fh.adminTool.Controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fh.common.model.vo.PageInfo;
import com.fh.festival.model.service.FesService;
import com.fh.festival.model.vo.Festival;
import com.fh.user.model.vo.User;

/**
 * Servlet implementation class AdminFesListController
 */
@WebServlet("/views/adminTool/adminTool.festival")
public class AdminFesListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminFesListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		int userNo = (request.getSession().getAttribute("loginUser") != null) 
	             ? ((User)request.getSession().getAttribute("loginUser")).getUserNo() 
	             : 0;
		
		ArrayList<Festival> list 
			= new FesService().adminSelectList(userNo);
		
		// 응답데이터로 보내기
		request.setAttribute("list", list);
		request.getRequestDispatcher("adminFestivalListView.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
