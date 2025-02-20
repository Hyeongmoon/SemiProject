package com.fh.festival.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fh.festival.model.service.FesService;
import com.fh.festival.model.vo.FestivalComment;
import com.google.gson.Gson;

/**
 * Servlet implementation class fesCommentListController
 */
@WebServlet("/clist.fe")
public class FesCommentListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FesCommentListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int fesNo = Integer.parseInt(request.getParameter("fesNo"));
		
		ArrayList<FestivalComment> fcList 
			= new FesService().selectCommList(fesNo);
	
		// 응답데이터만 넘기기 (Ajax 요청이므로)
		// > GSON 이용하기
		response.setContentType("application/json; charset=UTF-8");
		new Gson().toJson(fcList, response.getWriter());

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
