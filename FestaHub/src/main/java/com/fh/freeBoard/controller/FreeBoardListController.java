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
 * Servlet implementation class FreeBoardListController
 */
@WebServlet("/list.free")
public class FreeBoardListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FreeBoardListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
						
				
	
		String sorting = "desc";
		if(request.getParameter("sorting")!=null) {
			sorting = request.getParameter("sorting");
		}

				
		//페이징처리
				
		int listCount = new FreeBoardService().selectListCount();				//총 게시글 수
		int currentPage=1;
		
		if(request.getParameter("currentPage")!=null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage")); //보여질 페이징바 페이지번호
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
			list = new FreeBoardService().selectList(pi);
		}else {
			list = new FreeBoardService().selectListAsc(pi);
		}
		
		
		
		
		
		request.setAttribute("list", list);
		request.setAttribute("pi", pi);
		request.setAttribute("sorting", sorting);
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
