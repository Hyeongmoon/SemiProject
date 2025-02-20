package com.fh.festival.controller;

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

/**
 * Servlet implementation class FesUpdateFormController
 */
@WebServlet("/updateForm.fe")
public class FesUpdateFormController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FesUpdateFormController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		FesService fService = new FesService();
		
		int fesNo 
			= Integer.parseInt(request.getParameter("fesNo"));
		
		Festival f = fService.selectFes(fesNo);
		
		ArrayList<FestivalImage> fiList = fService.selectImgList(fesNo);
		
		request.setAttribute("f", f);
		request.setAttribute("fiList", fiList);
		
		request.getRequestDispatcher("views/festival/festivalUpdateForm.jsp").forward(request, response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
