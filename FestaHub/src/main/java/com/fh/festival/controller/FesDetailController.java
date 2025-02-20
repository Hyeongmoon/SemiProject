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
 * Servlet implementation class FesDetailController
 */
@WebServlet("/detail.fe")
public class FesDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FesDetailController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int fesNo 
			= Integer.parseInt(request.getParameter("fesNo"));
		
		FesService fService = new FesService();
		
		// 조회수 증가
		int result = fService.increaseCount(fesNo);
		
		if(result > 0) { // 성공
			
			// 게시글 상세조회
			Festival f = fService.selectFes(fesNo);
			
			// 이전글, 다음글 조회
		    Festival prevFes = fService.getPrevFes(fesNo);
		    Festival nextFes = fService.getNextFes(fesNo);
			
			// 첨부파일 상세조회 추가 필요
			ArrayList<FestivalImage> fiList = fService.selectImgList(fesNo);
			
			request.setAttribute("f", f);
			request.setAttribute("prevFes", prevFes);
			request.setAttribute("nextFes", nextFes);
			
			request.setAttribute("fiList", fiList);
			
			
			request.getRequestDispatcher("views/festival/festivalDetailView.jsp").forward(request, response);
		} else {
			request.setAttribute("errorMsg", "게시글 상세조회에 실패했습니다.");
			
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
