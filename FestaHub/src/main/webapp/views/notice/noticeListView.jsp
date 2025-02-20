<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, com.fh.notice.model.vo.Notice,
				 com.fh.common.model.vo.PageInfo" %>
<%
	// request 로 부터 응답데이터인 list, pi 뽑기
	ArrayList<Notice> list = 
		(ArrayList<Notice>)request.getAttribute("list");
	PageInfo pi = (PageInfo)request.getAttribute("pi");
	
	// 페이징바 관련된 변수만 따로 셋팅
	int currentPage = pi.getCurrentPage();
	int startPage = pi.getStartPage();
	int endPage = pi.getEndPage();
	int maxPage = pi.getMaxPage();
    int pageLimit = pi.getPageLimit();
    
    // 세션에 currentPage 값 저장
    // (목록가기 할때 보고있던 currentPage 값으로 가기 위해)
    session.setAttribute("noCurrentPage", currentPage);
    
    
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
    /* -----전체 영역--------------------------------------------*/
    /*바깥테두리*/
    #container-fluid{ 
        /* 브라우저 창크기에 따라 자동 크기조절 */
        max-width:1400px;
        width:100%;
        /* border:1px dotted black; */ /*바깥 선*/
            
        margin:auto;
        margin-top:40px;
        padding: 20px;
    }
    /*각 div 영역 사이 공간 너비*/
    #container-fluid>div{
        
        width:100%;
    }
	/* -----해드 영역--------------------------------------------*/
    /*게시판 제목 배치, 사이즈, 상하가운데정렬*/
    .list-head>div>h1{
        display: inline; 
        font-size: 50px; 
        vertical-align: middle;
        font-weight: bold;
    }
    /*게시판 이름 옆, 게시글 총수 상하가운데정렬*/
    .list-head>div>span{
        vertical-align:-30px;
    }

    /* -----리스트 상단 영역---------------------------------------*/
    /*검색버튼 오른쪽정렬 */
    .search-area{  
        float : right;
    }

    /*검색버튼 영역*/
    .search-form{
        height:30px
    }

    /* -----리스트 영역--------------------------------------------*/
    /* 테이블 게시글 사이 간격*/
    #board-table td{ 
        height:85px;
        border-top: 1px solid gray;
        cursor: pointer;
    }
    /*테이블 영역 위아래구분선*/
    #board-table{   
        border:none;
        border-top: 4px solid gray;
        border-bottom: 4px solid gray;
        margin : auto;
                
    }
    /* 테이블 td들의 텍스트상하 가운데 정렬*/
    #board-table td{ 
        vertical-align: middle;
        
    }

   
    /* 테이블 영역*/
    /*텍스트가운데정렬*/
    .list-body>div,table{
        width:100%;
        box-sizing: border-box;
    }

    /* #board-table>tr>td{ */
    #board-table tr td:first-child{
        text-align: center;
        
    }

    /*작성자영역 너비*/
    #board-table>tbody>tr>td>div>span:nth-child(1){ 
        display : inline-block;
        max-width: 15%;
    }
    /*날짜영역 너비*/
    #board-table>tbody>tr>td>div>span:nth-child(2){ 
        display : inline-block;
        max-width: 15%;
    }
    /*조회수영역 너비*/
    #board-table>tbody>tr>td>div>span:nth-child(3){ 
        display : inline-block;
        max-width:20%;
    }
    

    /* -----리스트 하단 영역---------------------------------------*/


    /* -----페이징바 영역-------------------------------------------*/
    /* 페이지 네비게이션 스타일 */
    .pagination {
        justify-content: center;
    }

    .page-link {
        color: #2A253F !important;
        border-color: #493375 !important;
    }

    .page-link:hover {
        color: #fff !important;
        background-color: #493375 !important;
        border-color: #493375 !important;
    }
 


</style>


</head>
<body>

	<%@ include file="../common/navbar.jsp"  %>

    <div id="container-fluid">

        <div class="list-head" >
            <div>
                <img src="resources/images/noticeLogo.png" alt="동행구하기 게시판이미지" width="100">
                <h1>공지사항 게시판</h1>
                <span style="vertical-align:-30px;"><%= pi.getListCount() %></span>
            </div>

        </div>
        <br>


        <div class="list-body">

            <!-- 바디부분 -->
            <div class="table-area">
                <table id="board-table" class="table-hover" >
                    
                    <tbody>
                    
                   	<% if(list.isEmpty()) { %>
						<!-- 리스트가 비어있을 경우 (조회된게 없을 경우) -->
						<tr>
							<td colspan="2">
								존재하는 공지사항이 없습니다.
							</td>
						</tr>
						
					<% } else { %>
						<!-- 조회된 데이터가 있을 경우 -->
						<% for(Notice n : list) { %>
							<tr>
	                            <td><%= n.getNoticeNo() %></td>
	                            <td>
	                                <div><%= n.getNoticeTitle() %></div>
	                                <div>
	                                    <span><%= n.getNoticeDate() %></span>
	                                    <span>조회수 : <%= n.getNoticeCount() %></span>
	                                </div>
	                            </td>
	                        </tr>			
						
						<% } %>
							
					<% } %>
                    </tbody>

                </table>
            </div>

            <br>
            
            
            
            
            <% if((loginUser != null) 
				&& (loginUser.getUserId().equals("admin"))) { %>
	            <div align="right">
	            
	                <a href="<%= contextPath %>/enrollForm.no" 
					   class="btn btn-sm btn-secondary">
						글작성
					</a>
	                
	            </div>
			<% } %>
			
        </div>


        <!-- 페이징바영역 -->
        <!-- 페이지 네비게이션 -->
        <div class="row">
            <div class="col text-center">
                <nav>
                    <ul class="pagination justify-content-center">
                    
                    	<!-- 1번 페이지일 경우에는 이전 버튼이 보이지 않게 -->
                        <% if(currentPage > pageLimit) { %>
	                        <li class="page-item">
	                            <a class="page-link" 
	                               href="<%= contextPath %>/list.no?currentPage=1">
	                            	&lt;&lt;
	                            </a>
	                        </li>
	                        <li class="page-item">
	                            <a class="page-link" href="<%= contextPath %>/list.no?currentPage=<%= (startPage - 1) %>">
	                            	&lt;
	                            </a>
	                        </li>
                        <% } %>

                        
                        <% for(int p = startPage; p <= endPage; p++) { %>
                        	<!-- 
                        		현재 출력하는 p번 페이지가 currentPage 와 일치하지 않는경우
                        		a태그의 href 속성이 작동되게 구현
                        	 -->
                        	<% if(p != currentPage ) { %>
                        		<li class="page-item">
                        			<a class="page-link" href="<%= contextPath %>/list.no?currentPage=<%= p %>">
                        				<%= p %>
                        			</a>
                        		</li>
                        	<% } else { %>
								<li class="page-item" style="list-style-type: none;">
								    <a class="page-link" href="#" style="background-color: #e0def3 !important; pointer-events: none;">
								        <%= p %>
								    </a>
								</li>
                        	<% } %>
                        <% } %>
                        <!-- 마지막 페이지가 아닐 경우에만 다음 버튼이 보이도록 작성 -->
                        <% if(currentPage < maxPage && endPage < maxPage) { %>
	                        <li class="page-item">
	                            <a class="page-link" 
	                               href="<%= contextPath %>/list.no?currentPage=<%= (endPage + 1) %>">
	                            	&gt;
	                            </a>
	                        </li>
	                        <li class="page-item">
	                            <a class="page-link" 
	                               href="<%= contextPath %>/list.no?currentPage=<%= maxPage %>">
	                            	&gt;&gt;
	                            </a>
	                        </li>
	                    <% } %>

                    </ul>
                </nav>
            </div>
        </div>
        <!-- 페이지 네비게이션 끝 -->
       
    </div>
    
   	<script>
		$(function() {
			
			$("#board-table>tbody>tr").click(function() {
				
				let nno = $(this).children().eq(0).text();
				
				location.href = "<%= contextPath %>/detail.no?nno=" + nno;
				
			});
			
		});
	</script>

	<%@ include file="../common/footer.jsp"  %>
    
</body>
</html>