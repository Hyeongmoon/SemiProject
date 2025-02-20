<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.fh.reviewBoard.model.vo.Review" %>
<%
	Review r = (Review)request.getAttribute("r");

	
	session.setAttribute("caller", "user");
	
	// 현재 페이지 가져오기; 없으면 기본값으로 설정
	Integer currentPageInteger = (Integer) session.getAttribute("noCurrentPage");
	int currentPage = (currentPageInteger != null) ? currentPageInteger.intValue() : 1;
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>

<!-- bootstrap -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
<!-- bootstrap -->
<style>


    /* 전체영역 */
    .container-fluid>div>div{
        /* border: 1px solid red; */
    }
    .container-fluid>div>div>div{
        /* border: 1px solid blue; */
    }
    .container-fluid>div>div>div>div{
        /* border: 1px solid green; */
    }


	/* 전체장 크기*/
	/*
    .container-fluid{ 
        border:1px dotted gray;
        max-width:1400px;
        margin:auto;
        margin-top: 50px;
        padding:20px;
    }
	*/
    

    /* =========================================== */
    /* 제목영역 */
    .detail-head {
        display: flex;
        justify-content: space-between; /* 좌측과 우측 정렬 */
        align-items: center; /* 세로 방향으로 가운데 정렬 */   
    }


    .detail-head { /* 제목, 글쓴이,조회수,날짜 한줄로 정렬*/
        display: flex;
        width: 100%; /* 전체 너비 사용 */
    }
    .detail-title {
        flex-grow: 1; /* 공간을 차지하도록 설정 */
    }
    .writer-area {
        text-align: right; /* 내부 텍스트 오른쪽 정렬 */
    }

    .writer-area {
        padding-top: 20px; /* 원하는 만큼 위쪽에 공간 추가 */
        padding-right: 20px;
    }


     /* =========================================== */
    /* 게시글 영역 */
    .detail-content{  /* 게시글 내용 영역 위아래 선*/
        border:none;
        border-top: 4px solid black;
        border-bottom: 4px solid black;
        margin : auto;        
    }

  
    .detail-content>div{   /*게시글내용 위,아래 간격 */
        margin-top : 30px;
    }


    /* =========================================== */
    /* 댓글영역 */
    .comment-area,/*댓글부분 위 공백*/
    .insert-comment,/*댁글입력창 위 공백*/
    .page-area, /*페이징바위 공백*/
    .button-area {/* 버튼들 위 공백*/ 
        margin-top : 30px;
    }



    .comment-area,
    .insert-comment{ /*댓글 영역 너비 조절*/
        width:95%;
        margin:auto;
        margin-top : 30px;
    }
    .comment-area>table {
        table-layout: fixed; /* 테이블 레이아웃 고정 */
        width: 100%; 
    }
    .comment-area>table td:nth-child(1){
        width:10%;
    }
    .comment-area>table td:nth-child(2){
        width:80%;
    }
    .comment-area>table td:nth-child(3){
        width:10%;
    }


    
    .insert-comment{ /* 댓글 입력부분 */
        
        border-radius: 10px;        
        background-color: none;
        border: 3px solid gray;
        padding-left: 3%;
        padding-right:3%;
        height:40px;
        padding-top:5px;
        
        
    }
    .insert-comment input{ /* 댓글입력 input 태그*/
        background-color: none;
        border: none;
        width:85%;
    }
    .insert-comment button{  /* 댓글입력 버튼*/
        border-radius: 10px;
        width:10%;
        border:none;
    }



    /* =========================================== */

    /* 이전,다음 게시글 영역 */
    .page-area{ /* 페이징바 영역 위아래 구분선*/
        border-top: 3px solid black;
        border-bottom : 3px solid black;
    }
    .first-tr>td{ /* 페이징바 영역 가운데 구분선*/
        border-bottom: 1px solid black;
    }



</style>    
</head>
<body>

	<!-- 모든 페이지 상단에 보여줘야 하는 페이지 include -->
	<%@ include file="../common/navbar.jsp" %>
    
    <div class="container-fluid">
      
        <div id="board-deatil">

            <!-- 상단 제목부분 -->
            <div>
                <div>공연후기 게시판</div>
                <div class="detail-head">
                    <div class="detail-title">
                        <h1><%= r.getRvTitle() %></h1>
                    </div>
                    
                    <div class="writer-area">
                        <span>admin | </span>
                        <span>조회수:<%= r.getRvCount() %> | </span>
                        <span><%= r.getRvDate() %></span>
                    </div>
                </div>
            </div>
            
            <!-- 중간 게시글내용부분 -->
            <div class="detail-content">
                <div style="height : 300px;"><%= r.getRvContent() %></div>
            </div>
            
              
     
            <!-- 수정 삭제 목록 버튼 -->
            <!-- 수정 삭제는 관리자만 보이게 처리 -->
           <div class="button-area">
			    <div align="right">
			        <% if((loginUser != null) && (loginUser.getUserId().equals("admin"))) { %>
			            <!-- 수정 버튼 -->
			            <button onclick="location.href='<%= contextPath %>/rvUpdate.fh?nno=<%= r.getRvNo() %>'"
			                    class="btn btn-sm btn-secondary">
			                수정
			            </button>
			            <!-- 삭제 버튼 -->
			            <button onclick="location.href='<%= contextPath %>/rvDelete.fh?nno=<%= r.getRvNo() %>'"
			                    class="btn btn-sm btn-secondary">
			                삭제
			            </button>
			        <% } %>
			        <!-- 목록 버튼 -->
			        <button onclick="location.href='<%= contextPath %>/rvlist.fh?currentPage=<%= currentPage %>'"
			                class="btn btn-sm btn-secondary">
			            목록
			        </button>
			    </div>
			</div>
        </div>
    </div>
        
	<!-- 모든 페이지 하단에 보여줘야 하는 페이지 include -->
	<%@ include file="../common/footer.jsp" %>
        
    </body>
    </html>