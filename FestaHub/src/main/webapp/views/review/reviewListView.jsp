<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, com.fh.reviewBoard.model.vo.Review, com.fh.common.model.vo.PageInfo" %>
<% ArrayList<Review> list = (ArrayList<Review>)request.getAttribute("list");
   PageInfo pi = (PageInfo)request.getAttribute("pi");
   
   //페이징바 관련된 변수만 따로 셋팅하기
   int currentPage = pi.getCurrentPage();
   int startPage = pi.getStartPage();
   int endPage = pi.getEndPage();
   int maxPage = pi.getMaxPage();
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
        /*  */
        border:1px dotted black;    /*바깥 선*/
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
        font-size: 70px; 
        vertical-align: middle;
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

    th,td{
            border-top: 1px solid #ddd;
            padding: 10px;
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

  /* 별점 스타일 */
  .star-rating {
        display: inline-block;
        margin-right: 5px; /* 조회수와의 간격을 좁게 설정 */
    }

    .star-rating span {
        font-size: 20px;
        color: #ccc;
        cursor: pointer;
        margin-right: px; /* 별점 간의 간격을 좁게 설정 */
    }

    .star-rating span:last-child {
        margin-right: 0; /* 마지막 별의 오른쪽 여백 제거 */
    }

    .star-rating .selected {
        color: gold;
    }
    

    /* -----리스트 하단 영역---------------------------------------*/


    /* -----페이징바 영역-------------------------------------------*/


    


 




    /* ---------------------------------------------------------------------------- */
</style>


</head>
<body>

	<%@ include file="../common/navbar.jsp" %>

    <div id="container-fluid">

        <div class="list-head" >
            <div>
                <img src="rv.png" alt="게시판이미지" width="150">
                <h1>공연후기 게시판</h1>
                <span style="vertical-align:-30px;">111</span>
            </div>

        </div>
        
        <div class="list-body">
            <!-- 리스트 상단검색버튼 부분 -->
            <div>
                <div class="search-area">
                    <form class="search-form" action="">

                        <select name="" id="">
                            <option value="">제목</option>
                            <option value="">내용</option>
                            <option value="">작성자</option>
                        </select>
                        <input type="search">
                        <button>검색</button>
                    </form>
                </div>
                
                <div>
                    <select name="" id="">
                        <option value="">최신순</option>
                        <option value="">오래된순</option>
                    </select>
                </div>
                
            </div>
            


            <!-- 바디부분 -->
            <div class="table-area">
                <table id="board-table" class="table-hover" >
                   <tbody>
                    <!-- 반복될부분 -->
                    <%for(Review r : list) {%>
  					<tr data-rvno="<%= r.getRvNo() %>">
                          <td><%= r.getRvNo() %>
                        	  
                        </td>
                         
                        <td>
                            <div><b><%= r.getRvTitle() %></b></div>
                            <div>
                                <span><%= r.getUserNickname() %></span>
                                <span><%= r.getRvDate() %></span>
                                <span><%= r.getRvCount() %></span>
                            </div>
                        </td>
                        <td>&#x1f495;<b><%= r.getReviewLike() %></b></td>
                        <td>&#x1F4AC;<b><%= r.getRvCommentNo() %></b></td>
                        <!-- 별점 영역 추가 -->
						<td>
						    <div class="star-rating">
						        <% 
						            int rating = r.getRvRating(); // 리뷰 객체에서 RV_RATING 값 가져오기
						            for (int i = 1; i <= 5; i++) {
						                if (i <= rating) { 
						        %>
						            <span class="star filled" data-value="<%= i %>">&#9733;</span> <!-- 채워진 별 -->
						        <% 
						                } else { 
						        %>
						            <span class="star" data-value="<%= i %>">&#9734;</span> <!-- 빈 별 -->
						        <% 
						                }
						            }
						        %>
						    </div>
						</td>
                    </tr>
  					 <%} %>
  					 </tbody>
                </table>
            </div>

            <br><br>
            <div>
            <!-- 페이징바 -->
		<div align="center" class="paging-area">

			<!-- 
				1번 페이지일 경우에는 이전 버튼을 아예 안보이게 
				즉, 1번 페이지가 아닐 경우에만 이전 버튼이 보여지게끔!!	
			-->
			<% if(currentPage != 1) { %>
			
				<button onclick="location.href = '<%= contextPath %>/rvlist.fh?currentPage=<%= currentPage - 1 %>';">
					&lt;
				</button>
				
			<% } %>

			<% for(int p = startPage; p <= endPage; p++) { %>
				
				
				<% if(p != currentPage) { %>
				
					<button onclick="location.href = '<%= contextPath %>/rvlist.fh?currentPage=<%= p %>';">
						<%= p %>
					</button>
				
				<% } else { %>
				
					<button disabled>
						<%= p %>
					</button>
				
				<% } %>
			
			<% } %>
			
			<!-- 
				마지막 페이지가 아닐 경우에만 다음 버튼이 보여지게끔!!
			-->
			<% if(currentPage != maxPage) { %>
			
				<button onclick="location.href = '<%= contextPath %>/rvlist.fh?currentPage=<%= currentPage + 1 %>';">
					&gt;
				</button>
				
			<% } %>
            </div>
            
            <div align="right">
                <button onclick="insertReview();" >글작성</button>
            </div>

		<script>
			$(function() {
			 $(".table-area>table>tbody>tr").click(function() {
			     // data-rvno 속성에서 rvNo 값 가져오기
			     let rvNo = $(this).data("rvno");
			     
			     // rvNo를 사용하여 상세 페이지로 이동
			     location.href = "<%= contextPath %>/RvDetail.bo?rvNo=" + rvNo;
			 });
	    </script>


        </div>
        
        <br><br>
        


        <!-- 페이징바영역 
        <div align="center">

            <div> << < 1 2 3 4 5 > >> </div>

        </div>
        -->
        


    </div>

    <script>
    	
	    function insertReview(){
			
			location.href = "<%= contextPath %>/rvEnrollform.fh";
				
		}
    
        
		$(function() {
			
			$(".table-area>table>tbody>tr").click(function() {
				// $(this) : 클릭을 당한 tr 요소
				// $(this).children() : [td, td, td, td, td, td]
				// $(this).children().eq(0) : <td>xx(글번호)</td>
				// $(this).children().eq(0).text() : 글번호
				// > 글번호를 변수에 담아두기
				let rvNo = $(this).children().eq(0).text();
				
				location.href = "<%= contextPath %>/RvDetail.bo?rvNo=" + rvNo;
				
			});
			
		});
    </script>
    

	<%@ include file="../common/footer.jsp" %>

    
</body>
</html>