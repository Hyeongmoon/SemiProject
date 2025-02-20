package com.fh.freeBoard.controller;

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
import com.fh.user.model.vo.User;
import com.oreilly.servlet.MultipartRequest;

/**
 * Servlet implementation class FreeBoardInsertController
 */
@WebServlet("/insert.free")
public class FreeBoardInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FreeBoardInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		
		//로그인 조건 걸기
		User loginUser = (User)request.getSession().getAttribute("loginUser");
		if(loginUser==null) { //로그인 하지 않았을시 화면 화면 돌리기
			request.getSession().setAttribute("alertMsg","로그인이 필요합니다.");
			response.sendRedirect(request.getContextPath()+"/list.free");
			return;
		}
		
				
		
		if(ServletFileUpload.isMultipartContent(request)) {
			
			
			int maxSize=10*1024*1024;
			String savePath=request.getServletContext().getRealPath("/resources/freeBoard_upfiles/"); 
			
			MultipartRequest  multiRequest = new MultipartRequest(request
															   ,savePath
															   ,maxSize
															   ,"UTF-8"
															   ,new MyFileRenamePolicy());
			
			//자유게시판 입력 내용 세팅
			int userNo = ((User)request.getSession().getAttribute("loginUser")).getUserNo();
			String freeTitle = multiRequest.getParameter("freeTitle");
			String freeContent = multiRequest.getParameter("freeContent");
			
			FreeBoard f = new FreeBoard();
			f.setUserNo(userNo);
			f.setFreeTitle(freeTitle);
			f.setFreeContent(freeContent);
			
			
			//업로드할 파일 세팅
			
			ArrayList<FreeBoardFile> list = new ArrayList<>();
			
			for(int i=0;i<10;i++) {
				String key = "upfile"+i;
			
				if(multiRequest.getOriginalFileName(key)!=null) {


					FreeBoardFile file = new FreeBoardFile();
					
					String originName = multiRequest.getOriginalFileName(key);  //원본명
					String changeName = multiRequest.getFilesystemName(key);	//수정명
					String filePath = "resources/freeBoard_upfiles/";
					
					file.setFreeFileName(originName);
					file.setFreeFileRename(changeName);
					file.setFreeFilePath(filePath);
					
					list.add(file);
				}
			}
			
			// f세팅 , list 세팅 (업로드파일이없다면 빈리스트)
			
			

			int result = new FreeBoardService().insertFreeBoard(f,list);
			
			if(result>0) {
				
				//완료시 리스트 화면띄어주기
				request.getSession().setAttribute("alertMsg", "자유게시판 작성에 성공했습니다!");
				response.sendRedirect(request.getContextPath()+"/list.free");
				
			}else {
				
				request.setAttribute("errorMsg", "자유게시판 작성에 실패했습니다.");
				request.getRequestDispatcher("views/common/errorPage.jsp;");
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
