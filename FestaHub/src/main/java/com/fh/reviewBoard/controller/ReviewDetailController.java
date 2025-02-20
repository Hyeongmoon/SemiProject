package com.fh.reviewBoard.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fh.reviewBoard.model.service.ReviewService;
import com.fh.reviewBoard.model.vo.Review;

/**
 * Servlet implementation class ReviewDetailController
 */
@WebServlet("/RvDetail.bo")
public class ReviewDetailController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReviewDetailController() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the review number from the request parameter
        String rvNoStr = request.getParameter("rvNo");
        int rvNo = 0;

        try {
            rvNo = Integer.parseInt(rvNoStr);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMsg", "유효하지 않은 리뷰 번호입니다.");
            request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
            return;
        }

        ReviewService rService = new ReviewService();
        int result = rService.increaseRvCount(rvNo);
        
        // Increase view count succeeded
        if (result > 0) {
            // Fetch the review details
            Review r = rService.selectReview(rvNo);
            
            // Check if the review exists
            if (r != null) {
                // Set the review information as a request attribute
                request.setAttribute("r", r);
                // Forward to the detail view
                request.getRequestDispatcher("views/review/reviewDetailView.jsp").forward(request, response);
            } else {
                // Handle case where review does not exist
                request.setAttribute("errorMsg", "해당 리뷰를 찾을 수 없습니다.");
                request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
            }
        } else {
            // Error handling for failing to increase view count
            request.setAttribute("errorMsg", "조회수 증가에 실패했습니다.");
            request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
