package com.fh.reviewBoard.controller;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.fh.accompanyBoard.model.service.AccompanyService;
import com.fh.accompanyBoard.model.vo.Accompany;
import com.fh.common.MyFileRenamePolicy;
import com.fh.reviewBoard.model.service.ReviewService;
import com.fh.reviewBoard.model.vo.Review;
import com.fh.reviewBoard.model.vo.ReviewFile;
import com.oreilly.servlet.MultipartRequest;

/**
 * Servlet implementation class ReviewUpdateController
 */
@WebServlet("/rvUpdate.fh")
public class ReviewUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReviewUpdateController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 공지사항 수정 요청 처리 (POST 방식)
				// 1) POST 방식이므로 인코딩 설정 먼저
				request.setCharacterEncoding("UTF-8");
				
				// 2) 요청 시 전달값을 뽑아서 변수 및 객체에 담기
				// 공지사항 글번호 : rn
				int rvNo = Integer.parseInt(request.getParameter("nno"));
			  
				// 바꿀 제목 : ReviewTitle
				String rvTitle = request.getParameter("rvTitle");
				// 바꿀 내용 : ReviewContent
				String rvContent = request.getParameter("rvContent");
				
				// Accompany 객체 하나로 가공하기
				Review r = new Review();
				r.setRvNo(rvNo);
				r.setRvTitle(rvTitle);
				r.setRvContent(rvContent);
				
				// 3) 전달값을 넘기면서 Service 로 요청 후 결과 받기
				int result = new ReviewService().updateReview(r);
				
				// 4) 처리된 결과를 가지고 사용자가 보게 될 응답페이지 지정
				if(result > 0) { // 성공
					
					// 일회성 알람 문구를 담아서 
					// 해당 게시글의 상세보기 페이지로 url 재요청
					request.getSession().setAttribute("alertMsg", "성공적으로 공연후기 게시글이 수정되었습니다.");
					response.sendRedirect(request.getContextPath() + "/RvDetail.b?rn=" + rvNo);
					
				} else { // 실패
					
					// 에러문구를 담아서 에러페이지로 포워딩
					request.setAttribute("errorMsg", "공연후기 게시글 수정에 실패했습니다.");
					request.getRequestDispatcher("views/common/errorPage.jsp")
													.forward(request, response);
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
