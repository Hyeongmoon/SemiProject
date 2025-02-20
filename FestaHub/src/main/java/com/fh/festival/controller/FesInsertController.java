package com.fh.festival.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.fh.common.MyFileRenamePolicy;
import com.fh.festival.model.service.FesService;
import com.fh.festival.model.vo.Festival;
import com.fh.festival.model.vo.FestivalImage;
import com.oreilly.servlet.MultipartRequest;

/**
 * Servlet implementation class FesInsertController
 */
@WebServlet("/insert.fe")
public class FesInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FesInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		
		// multipart/form-data 형식의 요청인지 먼저 검사 후 로직 진행
		if(ServletFileUpload.isMultipartContent(request)) {
			
			// 1_1. 전송 용량 제한
			int maxSize = 30 * 1024 * 1024;
			
			// 1_2. 실제로 저장할 서버의 물리적인 경로
			String savePath = request.getServletContext().getRealPath("/resources/fesimg_upfiles/");
			
			// 2. MultipartRequest 객체 생성하기
			// > 넘어온 파일명 수정, 그 파일이 서버로 업로드, 
			// MultipartRequest 객체 생성 (request 의 내용이 옮겨짐)
			MultipartRequest multiRequest 
				= new MultipartRequest(request,
									   savePath,
									   maxSize,
									   "UTF-8",
									   new MyFileRenamePolicy());
			// 3. MultipartRequest 객체로 부터 요청 시 전달값 뽑기
			// 요청 시 전달값
			
			// 회원번호
			String fesWriter = multiRequest.getParameter("userNo");
			// 공연제목
			String fesTitle = multiRequest.getParameter("fesTitle");
			// 공연내용
			String fesContent = multiRequest.getParameter("fesContent");
			// 공연날짜 스트링으로 받기
			String fesDayS = multiRequest.getParameter("fesDay").replace("T", " ");  // 'T'를 ' '로 변환
			// 날짜 타입 변환
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Timestamp fesDay = null;
			try {
			    fesDay = new Timestamp(sdf.parse(fesDayS).getTime());
				} catch (Exception e) {
					e.printStackTrace();
				}
			// 공연장소
			String fesAddress = multiRequest.getParameter("fesAddress");
			
			// > Festival 타입의 VO로 가공
			Festival f = new Festival();
			f.setFesTitle(fesTitle);
			f.setFesContent(fesContent);
			f.setFesDay(fesDay);
			f.setFesAddress(fesAddress);
			f.setFesWriter(fesWriter);
			
			// 첨부파일 정보
			// 썸네일(thumbnail), 이미지들(images)
			
			ArrayList<FestivalImage> fiList = new ArrayList<>();
			String filePath = "/resources/fesimg_upfiles/";
			// 썸네일 처리
			if (multiRequest.getOriginalFileName("thumbnail") != null) {
			    String fesImgName = multiRequest.getOriginalFileName("thumbnail");
			    String fesImgRename = multiRequest.getFilesystemName("thumbnail");

			    FestivalImage thumbImg = new FestivalImage();
			    thumbImg.setFesImgName(fesImgName);
			    thumbImg.setFesImgRename(fesImgRename);
			    thumbImg.setFesImgPath(filePath);
			    thumbImg.setFesImgThumb("Y");

			    fiList.add(thumbImg);
			}

			// 추가 이미지들 처리
	        int index = 1;
	        while (multiRequest.getOriginalFileName("images" + String.format("%02d", index)) != null) {
	            String fileNameKey = "images" + String.format("%02d", index);
	            String fesImgName = multiRequest.getOriginalFileName(fileNameKey);
	            String fesImgRename = multiRequest.getFilesystemName(fileNameKey);

            	FestivalImage fi = new FestivalImage();
	            fi.setFesImgName(fesImgName);
	            fi.setFesImgRename(fesImgRename);
	            fi.setFesImgPath(filePath);
	            fi.setFesImgThumb("N");
                fiList.add(fi);
                
	            index++;
	        }

			// FesService에 f와 fiList 전달
			int result = new FesService().insertFes(f, fiList);
			
			// 처리된 결과를 가지고 사용자가 보게 될 응답페이지를 지정
			if (result > 0) { // 성공
				
				request.getSession().setAttribute("alertMsg", "성공적으로 게시글이 등록되었습니다.");
				
				response.sendRedirect(request.getContextPath() + "/list.fe?currentPage=1");
				
			} else { // 실패
				
				request.setAttribute("errorMsg", "게시글 등록에 실패했습니다.");
				
				request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
				
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
