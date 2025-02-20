package com.fh.adminTool.Controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fh.festival.model.service.FesService;
import com.fh.festival.model.vo.FestivalImage;

/**
 * Servlet implementation class AdminFesDeleteController
 */
@WebServlet("/views/adminTool/deleteTable.fe")
public class AdminFesDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminFesDeleteController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int fesNo = Integer.parseInt(request.getParameter("fesNo"));
        String savePath = request.getServletContext().getRealPath("/resources/fesimg_upfiles/");
        
        // 게시글번호에 해당하는 파일리네임 정보를 ArrayList에 담기
        ArrayList<FestivalImage> delList = new ArrayList<>();
        
        delList = new FesService().selectImgList(fesNo);
        
        if(!delList.isEmpty()) {
        	for(FestivalImage fi : delList) {
        		new File(savePath + fi.getFesImgRename()).delete();
        	}
        }
        

        int result = new FesService().deleteTable(fesNo);
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(result > 0 ? "success" : "fail");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
