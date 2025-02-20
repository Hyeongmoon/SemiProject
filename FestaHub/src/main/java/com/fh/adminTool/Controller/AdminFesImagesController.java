package com.fh.adminTool.Controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fh.festival.model.service.FesService;
import com.fh.festival.model.vo.Festival;
import com.fh.festival.model.vo.FestivalImage;
import com.google.gson.Gson;

/**
 * Servlet implementation class AdminFesImagesController
 */
@WebServlet("/views/adminTool/getAdminFi.fe")
public class AdminFesImagesController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminFesImagesController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int fesNo 
			= Integer.parseInt(request.getParameter("fesNo"));
		// 첨부파일 상세조회 추가 필요
		ArrayList<FestivalImage> fiList = new FesService().getAdminImgList(fesNo);
		
		request.setAttribute("fiList", fiList);
		
//			request.getRequestDispatcher("adminFestivalDetailView.jsp").forward(request, response);
		
		// 응답데이터만 넘기기 (Ajax 요청이므로)
		// > GSON 이용하기
		response.setContentType("application/json; charset=UTF-8");
		new Gson().toJson(fiList, response.getWriter());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
