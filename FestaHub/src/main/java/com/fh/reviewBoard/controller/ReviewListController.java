package com.fh.reviewBoard.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fh.common.model.vo.PageInfo;
import com.fh.reviewBoard.model.service.ReviewService;
import com.fh.reviewBoard.model.vo.Review;

/**
 * Servlet implementation class ReviewBoardListController
 */
@WebServlet("/rvlist.fh")
public class ReviewListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReviewListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	/**
	 *
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//일반게시판 목록 조회 기능 구현 ( + 페이징 처리)
		// * 페이징 처리 (Pagination)
		// 리스트 조회할 건수가 많을 때 한 페이지당 n개씩 끊어서 보여질 수 있게끔
		// 처리해주는 효과
		
		//-- 페이징 처리 ----
		// 필요한 변수
		//기본적으로 구할 수 있는 4개의 변수
		// + 그 4개의 변수응 통해 계산해서 도출해야 하는 3개의 변수
		// > 총 7개의 변수가 필요함!!
		int listCount; // 현재 총 게시글 갯스(삭제되지 않은 일반게시글의 갯수)
		int currentPage; // 현재 사용자가 보고자 하는 페이지 (즉,사용자가 요청한 페이지)
		int pageLimit; // 페이지 하단에 보여질 페이징바의 페이지 최대 갯수
		int boardLimit; //한 페이지에 보여질 게시글의 최대갯수 (몇개 단위씩 볼겨냐)
		
		int maxPage; // 가장 마지막 페이지가 몇번 페이지인지 (즉,총 페이지 수)
		int startPage; //페이지 하단에 보여질 페이징바의 시작수
		int endPage; //페이지 하단에 보여질 페이징바의 끝수
		
		listCount = new ReviewService().selectListCount();
		
		currentPage = Integer.parseInt(request.getParameter("currentPage"));
		
		pageLimit = 10;
		
		boardLimit = 10;
		
		maxPage = (int)Math.ceil((double)listCount / boardLimit);
		
		startPage = (currentPage - 1) /pageLimit * pageLimit + 1;
		
		endPage = startPage + pageLimit - 1;
		
		if(endPage > maxPage) {
			
			endPage = maxPage;
		}
		
		PageInfo pi = new PageInfo(listCount, currentPage,pageLimit, boardLimit,maxPage,startPage,endPage);
		
		
		ArrayList<Review> list 
		           = new ReviewService().selectList(pi);
		
		
		request.setAttribute("list", list);
		request.setAttribute("pi", pi);

		
		request.getRequestDispatcher("views/review/reviewListView.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
