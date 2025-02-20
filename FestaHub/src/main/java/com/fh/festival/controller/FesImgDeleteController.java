package com.fh.festival.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fh.festival.model.service.FesService;

/**
 * Servlet implementation class FesImgDeleteController
 */
@WebServlet("/deleteImg.fe")
public class FesImgDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FesImgDeleteController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        int fesImgNo = Integer.parseInt(request.getParameter("fesImgNo"));
        String fesImgRename = request.getParameter("fesImgRename");
        String savePath = request.getServletContext().getRealPath("/resources/fesimg_upfiles/");

        // Service 호출
        int result = new FesService().deleteImg(fesImgNo);

        if (result > 0) {
            // 실제 파일 삭제
            File file = new File(savePath + fesImgRename);
            if (file.exists()) file.delete();
            response.getWriter().print("success");
        } else {
            response.getWriter().print("fail");
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
