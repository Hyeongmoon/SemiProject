<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, com.fh.accompanyBoard.model.vo.Accompany,
				 com.fh.common.model.vo.PageInfo" %>    
<%
	// request 로 부터 응답데이터인 list, pi 뽑기
	ArrayList<Accompany> list = 
		(ArrayList<Accompany>)request.getAttribute("list");
	PageInfo pi = (PageInfo)request.getAttribute("pi");
	
	// 페이징바 관련된 변수만 따로 셋팅
	int currentPage = pi.getCurrentPage();
	int startPage = pi.getStartPage();
	int endPage = pi.getEndPage();
	int maxPage = pi.getMaxPage();
    int pageLimit = pi.getPageLimit();
    
    // 세션에 currentPage 값 저장
    // (목록가기 할때 보고있던 currentPage 값으로 가기 위해)
    session.setAttribute("acCurrentPage", currentPage);
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
        /* border:1px dotted black; */
            /*바깥 선*/
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
    #board-table tr{ 
        cursor: pointer;;
        
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


    /* ---------------------------------------------------------------------------- */
</style>


</head>
<body>

	<%@ include file="../common/navbar.jsp"  %>

    <div id="container-fluid">

        <div class="list-head" >
            <div>
                <img src="resources/images/accompanyLogo.png" alt="동행구하기 게시판이미지" width="100">
                <h1>동행구하기 게시판</h1>
                <span style="vertical-align:-30px;"><%= pi.getListCount() %></span>
            </div>

        </div>


        <div class="list-body">
            <!-- 리스트 상단검색버튼 부분 -->
            <div>
                <div class="search-area">
                    <form class="form-inline" method="get" action="<%= contextPath %>/search.ac">
			        <div class="input-group">
			            <div class="input-group-prepend">
			                <select class="custom-select" name="category">
			                    <option value="AC.ACCOM_TITLE" <%= "AC.ACCOM_TITLE".equals(request.getParameter("category")) ? "selected" : "" %>>글 제목</option>
			                    <option value="AC.ACCOM_CONTENT" <%= "AC.ACCOM_CONTENT".equals(request.getParameter("category")) ? "selected" : "" %>>글 내용</option>
			                    <option value="UI.USER_NICKNAME" <%= "UI.USER_NICKNAME".equals(request.getParameter("category")) ? "selected" : "" %>>작성자</option>
			                </select>
			            </div>
			            <input type="text" class="form-control" name="keyword" placeholder="검색어를 입력하세요" 
			            	   value="<%= request.getParameter("keyword") != null ? request.getParameter("keyword") : "" %>">
			            <div class="input-group-append">
			                <button type="submit" class="btn btn-primary">검색</button>
			            </div>
			        </div>
			    </form>
                </div>
            </div>
            <br><br>
            

  
            <!-- 바디부분 -->
            <div class="table-area">
                <table id="board-table" class="table-hover" >
                    <% if (list.isEmpty()){ %>
                    	<tr style="pointer-events: none;">
                    		<td colspan="3">
                    			존재하는 동행구하기 게시글이 없습니다.
                    		</td>
                    	</tr>
                    <%} else { %>
                    	<%for(Accompany ac : list) {%>
	                    <tr>
	                        <td><%= ac.getAccomNo() %></td>
	                        <td>
	                            <div><%= ac.getAccomTitle() %></div> 
	                            <div>
	                                <span><%= ac.getUserNickname() %> | </span>
	                                <span><%= ac.getAccomDate() %> | </span>
	                                <span>조회수 <%= ac.getAccomCount() %></span>
	                            </div>
	                        </td>
	
	                        <td> ❤️ <%= ac.getLikeCount() %></td>
	                    </tr>
						<%}%>
                    <%}%>
             
                </table>
            </div>

            <br>
            
            <!-- 
			로그인한 회원만 보여지는 글작성 버튼
			-->
			<% if(loginUser != null) { %>
				
				<div align="right">
					<a href="<%= contextPath %>/enrollForm.ac" 
					   class="btn btn-sm btn-secondary">
						글작성
					</a>
					
					<br><br>
				</div>
			
			<% } %>

        </div>
       <!-- 페이징바영역 -->
        <!-- 페이지 네비게이션 -->
        <% 
		    // category와 keyword 파라미터를 가져와 변수에 저장
		    String category = request.getParameter("category");
		    String keyword = request.getParameter("keyword");
		    String baseUrl = (category != null && keyword != null) 
		                     ? "search.ac?category=" + category + "&keyword=" + keyword 
		                     : "list.ac";
		    String pageParam = (category != null && keyword != null) ? "&currentPage=" : "?currentPage=";
		%>
        <div class="row">
		    <div class="col text-center">
		        <nav>
		            <ul class="pagination justify-content-center">
		                <% if(startPage > 1) { %>
		                    <!-- 처음 페이지로 이동 -->
		                    <li class="page-item">
		                        <a class="page-link" href="<%= contextPath %>/<%= baseUrl %><%= pageParam %>1">≪</a>
		                    </li>
		                    <!-- 이전 페이지 그룹으로 이동 -->
		                    <li class="page-item">
		                        <a class="page-link" href="<%= contextPath %>/<%= baseUrl %><%= pageParam %><%= startPage - 1 %>">＜</a>
		                    </li>
		                <% } %>
		
		                <% for(int p = startPage; p <= endPage; p++) { %>
		                    <% if(p != currentPage) { %>
		                        <li class="page-item">
		                            <a class="page-link" href="<%= contextPath %>/<%= baseUrl %><%= pageParam %><%= p %>"><%= p %></a>
		                        </li>
		                    <% } else { %>
		                        <li class="page-item active">
		                            <span class="page-link" style="background-color: #e0def3 !important; pointer-events: none;"><%= p %></span>
		                        </li>
		                    <% } %>
		                <% } %>
		
		                <% if(endPage < maxPage) { %>
		                    <!-- 다음 페이지 그룹으로 이동 -->
		                    <li class="page-item">
		                        <a class="page-link" href="<%= contextPath %>/<%= baseUrl %><%= pageParam %><%= endPage + 1 %>">＞</a>
		                    </li>
		                    <!-- 마지막 페이지로 이동 -->
		                    <li class="page-item">
		                        <a class="page-link" href="<%= contextPath %>/<%= baseUrl %><%= pageParam %><%= maxPage %>">≫</a>
		                    </li>
		                <% } %>
		            </ul>
		        </nav>
		    </div>
		</div>
        <!-- &lt;&lt; &gt;&gt; -->
        <!-- 페이지 네비게이션 끝 -->

    </div>
    
   	<script>
		$(function() {
			
			$("#board-table>tbody>tr").click(function() {
				
				let nac = $(this).children().eq(0).text();
				location.href = "<%= contextPath %>/detail.ac?nac=" + nac;
				
			});
			
		});
	</script>
    

	<%@ include file="../common/footer.jsp"  %>
    
</body>
</html>