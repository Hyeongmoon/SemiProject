package com.fh.adminTool.Controller;

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
import com.fh.user.model.vo.User;

/**
 * Servlet implementation class AdmintFreeListController
 */
@WebServlet("/views/adminTool/adminTool.free")
public class AdmintFreeListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdmintFreeListController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		if(request.getSession().getAttribute("loginUser")==null) {
			
			request.setAttribute("alertMsg", "잘못된 접근입니다.");
			response.sendRedirect(request.getContextPath()+"/list.free");
		}else {
			User loginUser = (User)request.getSession().getAttribute("loginUser");
			
			if( loginUser.getUserNo() != 1) {
				
				request.setAttribute("alertMsg", "잘못된 접근입니다.");
				response.sendRedirect(request.getContextPath()+"/list.free");
				
				
				
			}else {
				//페이징처리
				
				int listCount = new FreeBoardService().selectListCount();				//총 게시글 수
				int currentPage=1;
				
				if(request.getParameter("currentPage")!=null) {
					currentPage = Integer.parseInt(request.getParameter("currentPage")); //보여질 페이징바 페이지번호
				}
				
				
				
				int pageLimit = 10;														//페이징바에 보여질 번호 수
				int boardLimit = 1000;													//한페이지에 보여질 게시글 총수
				
				
				int maxPage = (int)Math.ceil(((double)listCount/boardLimit));           //가장 마지막 페이진 수
				int startPage = (currentPage-1)/pageLimit*pageLimit+1;					//페이지의 첫번째 페이징바수
				
				int endPage = startPage+pageLimit-1;									//현 페이지의 마지막 페이징바수
				if(endPage>maxPage) {
					endPage=maxPage;
				}
				
				PageInfo pi = new PageInfo(listCount,currentPage,pageLimit,boardLimit,maxPage,startPage,endPage);
				
				
				
				
				ArrayList<FreeBoard> list = new FreeBoardService().adminSelctList(pi);
				
				request.setAttribute("list",list);
				request.setAttribute("pi", pi);
				request.getRequestDispatcher("adminFreeBoardListView.jsp").forward(request, response);
				
				
				
				
			}
			
			
			
			
			
			
			
			
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
