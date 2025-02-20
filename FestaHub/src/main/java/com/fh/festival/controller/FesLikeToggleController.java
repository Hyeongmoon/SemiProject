package com.fh.festival.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fh.festival.model.service.FesService;
import com.fh.festival.model.vo.FestivalLike;
import com.google.gson.Gson;

/**
 * Servlet implementation class FesLikeToggleController
 */
@WebServlet("/toggleLike.fe")
public class FesLikeToggleController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FesLikeToggleController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int fesNo = Integer.parseInt(request.getParameter("fesNo"));
        int userNo = Integer.parseInt(request.getParameter("userNo"));

        FestivalLike fl = new FesService().toggleLike(fesNo, userNo);

        // JSON 형식으로 응답 반환
        response.setContentType("application/json; charset=UTF-8");
        new Gson().toJson(fl, response.getWriter());
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
