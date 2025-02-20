package com.fh.freeBoard.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fh.common.model.vo.PageInfo;
import com.fh.freeBoard.model.service.FreeBoardService;
import com.fh.freeBoard.model.vo.FreeBoard;

/**
 * Servlet implementation class FreeBoardListSearch
 */
@WebServlet("/listSearch.free")
public class FreeBoardListSearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public  FreeBoardListSearchController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//나중에 추가해주기
		String sorting = "desc";
		if(request.getParameter("sorting")!=null) {
			sorting = request.getParameter("sorting");
		}
		
		
		String searchOption = "";
		if(request.getParameter("searchOption") !=null) {
			
			searchOption = request.getParameter("searchOption");
		}
		
		String inputValue = "";
		if(request.getParameter("inputValue") != null) {
			
			inputValue = request.getParameter("inputValue");
		}
		
		//페이징처리

		int listCount = 0;
		
		switch (searchOption) {
		
		case "FREE_TITLE" : listCount = new FreeBoardService().selectListCountTitle(inputValue); break;
		case "FREE_CONTENT" : listCount = new FreeBoardService().selectListCountContent(inputValue); break;
		case "USER_NICKNAME" : listCount = new FreeBoardService().selectListCountWriter(inputValue); break;
		
		}
		
						//총 게시글 수
		
		
		
		
		
		int currentPage=1;
		
		if(request.getParameter("currentPage")!=null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage")); // 뒤로가기시 보여질 페이징바 페이지번호
		}
		
		
		
		int pageLimit = 5;														//페이징바에 보여질 번호 수
		int boardLimit = 10;													//한페이지에 보여질 게시글 총수
		
		
		int maxPage = (int)Math.ceil(((double)listCount/boardLimit));           //가장 마지막 페이진 수
		int startPage = (currentPage-1)/pageLimit*pageLimit+1;					//페이지의 첫번째 페이징바수
		
		int endPage = startPage+pageLimit-1;									//현 페이지의 마지막 페이징바수
		if(endPage>maxPage) {
			endPage=maxPage;
		}
		
		
		
		PageInfo pi = new PageInfo(listCount,currentPage,pageLimit,boardLimit,maxPage,startPage,endPage);
		
		ArrayList<FreeBoard> list = null; 
		
		if(sorting.equals("desc")) {
			switch (searchOption) {
			case "FREE_TITLE" :  list =new FreeBoardService().selectListTitle(pi,inputValue); break;
						
			case "FREE_CONTENT" :  list =new FreeBoardService().selectListContent(pi,inputValue); break;
			
			case "USER_NICKNAME" :  list =new FreeBoardService().selectListWriter(pi,inputValue); 
			
			}
		}else {
			
			switch (searchOption) {
			case "FREE_TITLE" :  list =new FreeBoardService().selectListAscTitle(pi,inputValue); break;
			
			case "FREE_CONTENT" :  list =new FreeBoardService().selectListAscContent(pi,inputValue); break;
			
			case "USER_NICKNAME" :  list =new FreeBoardService().selectListAscWriter(pi,inputValue); 
			}
		}
		
		
				
		
		
		
		
		
		
		request.setAttribute("list", list);
		request.setAttribute("pi", pi);
		request.setAttribute("sorting", sorting);
		request.setAttribute("searchOption",searchOption);
		request.setAttribute("inputValue", inputValue);
		request.setAttribute("currentPage",currentPage);
		
		request.getRequestDispatcher("views/freeBoard/freeBoardListView.jsp").forward(request, response);
		// > 이 코드가 없으면 흰페이지만 떠야함
			
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
