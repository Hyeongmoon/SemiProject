package com.fh.freeBoard.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fh.freeBoard.model.service.FreeBoardService;
import com.fh.freeBoard.model.vo.FreeBoard;
import com.fh.freeBoard.model.vo.FreeBoardFile;

/**
 * Servlet implementation class FreeBoardDetailController
 */
@WebServlet("/detail.free")
public class FreeBoardDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FreeBoardDetailController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int currentPage = 1;
		if(request.getParameter("currentPage")!=null) {
			currentPage=Integer.parseInt(request.getParameter("currentPage"));
		}
		
		
		String sorting = "desc";
		if(request.getParameter("sorting")!=null) {
			sorting = request.getParameter("sorting");
		}
		
		
		String searchOption = "";
		if(request.getParameter("searchOption")!=null) {
			
			searchOption = request.getParameter("searchOption");
		}
		
		String inputValue = "";
		if(request.getParameter("inputValue") != null) {
			
			inputValue = request.getParameter("inputValue");
		}
		
		//db 에서 정보를 받아서 , 정보실어서 화면 띄우기
		
		int freeNo = Integer.parseInt(request.getParameter("freeNo"));
		
		FreeBoardService fService = new FreeBoardService();
		
		
		
		FreeBoard prevF =new FreeBoardService().selectPrevFile(freeNo,searchOption,inputValue,sorting);
		FreeBoard nextF = new FreeBoardService().selectNextFile(freeNo,searchOption,inputValue,sorting);
		
		
		
		
		
		//조회수 증가시키기
		int result = fService.increaseCount(freeNo);
		
		
		
		if(result>0) {
			FreeBoard f = fService.selectFreeBoard(freeNo);
			
			
			ArrayList<FreeBoardFile> files = fService.selectFreeBoardFile(freeNo);
			
			request.setAttribute("f",f);
			request.setAttribute("files",files);
			request.setAttribute("currentPage",currentPage);
			request.setAttribute("searchOption",searchOption);
			request.setAttribute("inputValue",inputValue);
			request.setAttribute("sorting", sorting);
			request.setAttribute("prevF", prevF);
			request.setAttribute("nextF", nextF);
			
			
			
			request.getRequestDispatcher("views/freeBoard/freeBoardDetailView.jsp").forward(request, response);
		}else {
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
