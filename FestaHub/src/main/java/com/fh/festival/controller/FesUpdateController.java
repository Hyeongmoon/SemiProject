package com.fh.festival.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.fh.common.MyFileRenamePolicy;
import com.fh.festival.model.service.FesService;
import com.fh.festival.model.vo.Festival;
import com.fh.festival.model.vo.FestivalImage;
import com.oreilly.servlet.MultipartRequest;

/**
 * Servlet implementation class FesUpdateController
 */
@WebServlet("/update.fe")
public class FesUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FesUpdateController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		
		if(ServletFileUpload.isMultipartContent(request)) {

			int maxSize = 10 * 1024 * 1024;
			String savePath = request.getServletContext().getRealPath("/resources/fesimg_upfiles/");
			
			MultipartRequest multiRequest 
				= new MultipartRequest(request,
									   savePath,
									   maxSize,
									   "UTF-8",
									   new MyFileRenamePolicy());
			
			// 글번호
			int fesNo = Integer.parseInt(multiRequest.getParameter("fesNo"));
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
			f.setFesNo(fesNo);
			f.setFesTitle(fesTitle);
			f.setFesContent(fesContent);
			f.setFesDay(fesDay);
			f.setFesAddress(fesAddress);
			
			// 첨부파일 정보
			// 기존 첨부파일 리스트, 신규 첨부파일 리스트
			ArrayList<FestivalImage> originList = new ArrayList<>();
			ArrayList<FestivalImage> newList = new ArrayList<>();
			String filePath = "/resources/fesimg_upfiles/";
		       // 폼 데이터를 처리하는 로직 예제
	        String[] deletedImages = multiRequest.getParameterValues("deletedImages"); // 삭제 대기 이미지 목록
	        FesService fesService = new FesService();
			
			// 썸네일 수정이 있을 경우
			if(multiRequest.getOriginalFileName("thumbnail") != null) {
				
			    String fesImgName = multiRequest.getOriginalFileName("thumbnail");
			    String fesImgRename = multiRequest.getFilesystemName("thumbnail");
			    
			    FestivalImage newThumb = new FestivalImage();
			    newThumb.setFesImgName(fesImgName);
			    newThumb.setFesImgRename(fesImgRename);
			    newThumb.setFesImgPath(filePath);
			    newThumb.setFesImgThumb("Y");
			    newThumb.setFesNo(fesNo);

			    // 새로운 이미지들을 ArrayList<FestivalImage> newList에 저장해서 넘기기
			    newList.add(newThumb);
			    
			    // 기존 썸네일이 있을 경우
			    if(multiRequest.getParameter("originThumbNo") != null) {
			    	
			    	int originThumbNo = Integer.parseInt(multiRequest.getParameter("originThumbNo"));
			    	
			    	// DB에서 삭제할(상태값을 바꿀) 기존 썸네일을 originThumb 객체 생성 후 리스트에 담기
			    	FestivalImage originThumb = new FestivalImage();
			    	originThumb.setFesImgNo(originThumbNo);
			    	
			    	originList.add(originThumb);
			    	
			    	// 기존 썸네일 파일을 서버에서 삭제하기
			    	new File(savePath + multiRequest.getParameter("originThumbRename")).delete();
			    }
			}
			
			
			// 추가 이미지 수정이 있을 경우
			int index = 1;
			
			if (multiRequest.getOriginalFileName("images" + String.format("%02d", index)) != null) {
				
				while (multiRequest.getOriginalFileName("images" + String.format("%02d", index)) != null) {
					String fileNameKey = "images" + String.format("%02d", index);
					String fesImgName = multiRequest.getOriginalFileName(fileNameKey);
					String fesImgRename = multiRequest.getFilesystemName(fileNameKey);
					
					FestivalImage newImg = new FestivalImage();
					newImg.setFesImgName(fesImgName);
					newImg.setFesImgRename(fesImgRename);
					newImg.setFesImgPath(filePath);
					newImg.setFesImgThumb("N");
					newImg.setFesNo(fesNo);
					
					// 새로운 이미지들을 ArrayList<FestivalImage> newList에 저장해서 넘기기
					newList.add(newImg);
					
					index++;
				}
//				// 기존 추가이미지가 있을 경우
//				if(multiRequest.getParameterValues("originImgNo") != null) {
//					
//					// 기존 추가이미지 값들을 String 배열로 받아오기
//					String[] originImgNos = multiRequest.getParameterValues("originImgNo");
//					String[] originImgRenames = multiRequest.getParameterValues("originImgRename");
//					
//					// String 배열의 길이만큼 반복 돌리기
//					for (int i = 0; i < originImgNos.length; i++) {
//						
//						// DB에서 삭제할(상태값을 바꿀) 기존 추가이미지들을 originImg 객체 생성 후 리스트에 담기
//						FestivalImage originImg = new FestivalImage();
//						originImg.setFesImgNo(Integer.parseInt(originImgNos[i]));
//						
//						originList.add(originImg);
//						
//				    	// 기존 썸네일 파일을 서버에서 삭제하기
//				    	new File(savePath + originImgRenames[i]).delete();
//					}
//					
//				}
			}
			
	        // 삭제할 파일 처리
	        if (deletedImages != null) {
	            for (String imgInfo : deletedImages) {
	                String[] imgData = imgInfo.split(",");
	                int imgNo = Integer.parseInt(imgData[0]);
	                String imgRename = imgData[1];

	                // 실제 파일 삭제 처리
	                File fileToDelete = new File(savePath + imgRename);
	                if (fileToDelete.exists() && fileToDelete.delete()) {
	                    System.out.println("File " + imgRename + " deleted successfully.");
	                } else {
	                    System.out.println("Failed to delete file " + imgRename);
	                }

	                // DB 상태 변경 처리
	                int statusUpdated = fesService.deleteImg(imgNo);
	                if (statusUpdated == 0) {
	                    System.out.println("Failed to update status for imgNo: " + imgNo);
	                }
	            }
	        }
			
			
			// 썸네일 및 이미지 수정 있을 경우, 정보를 리스트(ArrayList<FestivalImage> newList)에 저장 후
			// 기존이미지들이 있을 경우, 기존 정보또한 별도의 리스트(ArrayList<FestivalImage> originList)에
			// 저장 및 파일 삭제를 진행
			// > 이 후 Dao에서 신규 이미지는 동일 게시글 번호에 추가를, 기존 이미지는 삭제(상태변경)을 진행
			int result = fesService.updateFes(f, newList, originList);
			
			if(result > 0) { // 성공
				
				request.getSession().setAttribute("alertMsg", "게시글 수정에 성공했습니다.");
				
				response.sendRedirect(request.getContextPath() + "/detail.fe?fesNo=" + fesNo);
				
			} else { // 실패
				
				request.setAttribute("errorMsg", "게시글 수정에 실패했습니다.");
				
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
