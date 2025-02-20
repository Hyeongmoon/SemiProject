package com.fh.freeBoard.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.fh.common.MyFileRenamePolicy;
import com.fh.freeBoard.model.service.FreeBoardService;
import com.fh.freeBoard.model.vo.FreeBoard;
import com.fh.freeBoard.model.vo.FreeBoardFile;
import com.oreilly.servlet.MultipartRequest;

/**
 * Servlet implementation class FreeBoardUpdateController
 */
@WebServlet("/update.free")
public class FreeBoardUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FreeBoardUpdateController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		request.setCharacterEncoding("UTF-8");
		
		if(ServletFileUpload.isMultipartContent(request)) {
			
			int maxSize = 10*1024*1024;
			String savePath = request.getServletContext().getRealPath("/resources/freeBoard_upfiles/");
			MultipartRequest multiRequest = new MultipartRequest(request
																,savePath
																,maxSize
																,"UTF-8"
																,new MyFileRenamePolicy());
			
			
			
			//게시글 관련 f
			int freeNo = Integer.parseInt(multiRequest.getParameter("freeNo"));
			String freeTitle = multiRequest.getParameter("freeTitle");
			String freeConent = multiRequest.getParameter("freeContent");
			
			FreeBoard f = new FreeBoard();
			f.setFreeNo(freeNo);
			f.setFreeTitle(freeTitle);
			f.setFreeContent(freeConent);
			
			
			
			//첨부파일관련 files
			
			
			ArrayList<FreeBoardFile> deleteFiles = new ArrayList<>();;
			ArrayList<FreeBoardFile> updateFiles = new ArrayList<>();;
			ArrayList<FreeBoardFile> insertFiles = new ArrayList<>();;
			
			
			//기존 첨부파일의 갯수 
			int originCount = 0;
			if(multiRequest.getParameter("originCount")!=null) {
				originCount = Integer.parseInt(multiRequest.getParameter("originCount"));
				
			}
		
			
			//전체 파일수
			int fileLimit = 10;
			if(multiRequest.getParameter("fileLimit")!=null) {
				fileLimit = Integer.parseInt(multiRequest.getParameter("fileLimit")); 
			}
			
			//기존파일이 있는데, 수정시 그대로 둘지(0) or 삭제할지(1) 
			/* int deleteFile = 0; */
			
			
			//변수설정
			
			
			
			for(int i=0; i<fileLimit; i++) {
				
				
				 int deleteFile = (multiRequest.getParameter("deleteFile"+i)==null?0:Integer.parseInt(multiRequest.getParameter("deleteFile"+i)));
				/*
				 * if(multiRequest.getParameter("deleteFile"+i)!=null) { deleteFile =
				 * Integer.parseInt(multiRequest.getParameter("deleteFile"+i)); }
				 */
				
				
				if(i<originCount) { 
					
					
				//기존첨부파일이 있는경우	
					int freeBoardFileNo = Integer.parseInt(multiRequest.getParameter("originFreeFileNo"+i)); //기존첨부파일의 번호
					
					
					
					if(multiRequest.getOriginalFileName("reUpfile"+i)!=null) { 
					//새첨부 있는경우
						
						//기존파일삭제, 
						
						new File(savePath+multiRequest.getParameter("originFreeFileRename"+i)).delete(); //기존파일 삭제
							
						
						//db 수정
						
						
						//새로운 파일의 정보를 뽑아내서 객체로 가공
						
						String freeFileName = multiRequest.getOriginalFileName("reUpfile"+i);
						String freeFileRename = multiRequest.getFilesystemName("reUpfile"+i);
						
						
						FreeBoardFile file = new FreeBoardFile();
						
						file.setFreeFileNo(freeBoardFileNo);    //수정할 파일의 파일넘버 가공.
						file.setFreeFileName(freeFileName);	    //수정할 파일의 원본명
						file.setFreeFileRename(freeFileRename);	//수정할 파일의 수정명
						
								updateFiles.add(file);
	

						
					}else {
					//새첨부 없는경우
						//기존첨부파일o(새첨부파일x) 삭제(1)or그냥둘지(0)
						
						if(multiRequest.getParameter("deleteFile"+i)!=null) {
							deleteFile = Integer.parseInt(multiRequest.getParameter("deleteFile"+i));
						
						}
						
						
						
						
						if(deleteFile==1) {
							//기존파일삭제 ,
							new File(savePath+multiRequest.getParameter("originFreeFileRename"+i)).delete(); //기존파일 삭제
							
							
							// db 삭제
							
							
							FreeBoardFile file = new FreeBoardFile();
							
							file.setFreeFileNo(freeBoardFileNo); //삭제할 파일의 파일넘버 가공.
							
							deleteFiles.add(file);
							
							
							
						}else {
						}
					}
					
					
					
				}else {
				//기존첨부파일 없는경우
					
					
					if(multiRequest.getOriginalFileName("reUpfile"+i)!=null) { 
					//새첨부 있는경우
						//추가
						//새로운 파일의 정보를 뽑아내서 객체로 가공
						
						
						String filePath="resources/freeBoard_upfiles/";
						String freeFileName = multiRequest.getOriginalFileName("reUpfile"+i); 
						String freeFileRename = multiRequest.getFilesystemName("reUpfile"+i);
						
						
						FreeBoardFile file = new FreeBoardFile();
						
						
						file.setFreeNo(freeNo); //게시글번호
						file.setFreeFilePath(filePath); //저장 경로 
						file.setFreeFileName(freeFileName); //원본파일명
						file.setFreeFileRename(freeFileRename); //수정파일명
					
						
						insertFiles.add(file); //추가할 파일들에 추가
					}
					
				}
				
				
			}//for문
			
			

			
			
			int result = new FreeBoardService().updateFreeBoard(f,deleteFiles,updateFiles,insertFiles);
			
			if(result>0) {
				
				request.getSession().setAttribute("alertMsg", "게시글 수정에 성공했습니다.");
				
				response.sendRedirect(request.getContextPath()+"/detail.free?freeNo="+freeNo);
				
			}else {
				
				request.getSession().setAttribute("alertMsg", "게시글 수정에 실패했습니다.");
				
				response.sendRedirect(request.getContextPath()+"/detail.free?freeNo="+freeNo);
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
