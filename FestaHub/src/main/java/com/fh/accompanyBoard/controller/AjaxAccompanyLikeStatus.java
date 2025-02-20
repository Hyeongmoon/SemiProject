package com.fh.accompanyBoard.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fh.accompanyBoard.model.service.AccompanyService;
import com.fh.accompanyBoard.model.vo.AccompanyLike;
import com.google.gson.Gson;

/**
 * Servlet implementation class AjaxAccompanyLikeStatus
 */
@WebServlet("/likeStatus.ac")
public class AjaxAccompanyLikeStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxAccompanyLikeStatus() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int accomNo = Integer.parseInt(request.getParameter("accomNo"));
        int userNo = Integer.parseInt(request.getParameter("userNo"));

        AccompanyLike al = new AccompanyService().selectLikeInfo(accomNo, userNo);

        // JSON 형식으로 응답 반환
        response.setContentType("application/json; charset=UTF-8");
        new Gson().toJson(al, response.getWriter());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
