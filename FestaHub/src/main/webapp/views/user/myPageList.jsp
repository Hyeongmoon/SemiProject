<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.fh.freeBoard.model.vo.FreeBoard,
				 com.fh.festival.model.vo.Festival,
				 com.fh.accompanyBoard.model.vo.Accompany,
				 com.fh.reviewBoard.model.vo.Review,
				 com.fh.common.model.vo.PageInfo" %>
<%
	// request 로 부터 응답데이터인
	// list, pi 를 뽑아내기
	ArrayList<FreeBoard> frlist = (ArrayList<FreeBoard>)request.getAttribute("list");
	ArrayList<Festival> felist = (ArrayList<Festival>)request.getAttribute("list");
	ArrayList<Accompany> aclist = (ArrayList<Accompany>)request.getAttribute("list");
	ArrayList<Review> rvlist = (ArrayList<Review>)request.getAttribute("list");
	

	PageInfo pi = (PageInfo)request.getAttribute("pi");

	// 페이징바 관련된 변수만 따로 셋팅하기
	int currentPage = pi.getCurrentPage();
	int startPage = pi.getStartPage();
	int endPage = pi.getEndPage();
	int maxPage = pi.getMaxPage();
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시글 & 댓글</title>

    <!-- Bootstrap 4.6.2 CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <style>
        body {
            background-color: #f5f5f5;
        }
        .container {
            width: 760px;
            margin: 50px auto;
            background-color: white;
            border-radius: 10px;
            padding: 40px 60px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
        }
        .title, p {
            text-align: center;
        }
        .boardandreply {
            display: flex;
            width: 100%;
            height: 10%;
            border: 1px solid lightgray;
        }
        .myboardandreply {
            flex: 1;
            display: flex;
            justify-content: center;
            align-items: center;
            border: 1px solid #ddd;
            background-color: #ffffff;
            cursor: pointer;
        }
        .myboardandreply:not(:last-child) {
            border-right: none;
        }
        .myboardandreply.active {
            background-color: #493375;
            color: white;
        }
        .myboardandreply:hover {
            background-color: lightgray;
        }
        .content-container {
            padding: 20px;
            border: 1px solid lightgray;
            height: 90%;
            overflow-y: auto;
        }
        .content {
            display: none;
            margin-top: 10px;
        }
        .filter-container {
            display: flex;
            justify-content: stretch;
            margin: 0;
            border: 1px solid lightgray;
        }
        .filter-container button {
            flex: 1;
            background-color: #493375;
            color: white;
            border: none;
            padding: 10px;
            cursor: pointer;
            transition: background-color 0.3s, transform 0.3s;
            border-right: 1px solid lightgray;
        }
        .filter-container button:last-child {
            border-right: none;
        }
        .filter-container button:hover {
            background-color: #6a5e9b;
            transform: translateY(-2px);
        }
        .filter-container button.active {
            background-color: #6a5e9b;
        }
        .pagination {
            justify-content: center;
            margin-top: 20px;
        }
        .pagination li {
            margin: 0 5px;
        }
        .pagination a {
            color: #493375;
            text-decoration: none;
            padding: 10px 15px;
            border: 1px solid #ddd;
            border-radius: 5px;
            transition: background-color 0.3s;
        }
        .pagination a:hover {
            background-color: #6a5e9b;
            color: white;
        }
        .pagination .active a {
            background-color: #493375;
            color: white;
            border: 1px solid #493375;
        }
    </style>
</head>
<body>
    <%@ include file="/views/common/navbar.jsp" %>

    <div class="container">
        <h2 class="title">게시물 & 댓글</h2>
        <br><hr>

		
        <div class="boardandreply">
            <div class="myboardandreply active" id="b1" onclick="showContent('wishlist', this)">
                <p>내가 찜한 게시물</p>
            </div>
            <div class="myboardandreply" id="b2" onclick="showContent('myPosts', this)">
                <p>내가 쓴 게시물</p>
            </div>
            <div class="myboardandreply" id="b3" onclick="showContent('myComments', this)">
                <p>내가 쓴 댓글</p>
            </div>
        </div>

        <div class="filter-container" id="filterContainer">
            <button onclick="filterPosts('후기')">공연후기</button>
            <button onclick="filterPosts('동행')">동행구하기</button>
            <button onclick="filterPosts('정보')" style="display: inline-block;">공연정보</button>
            <button onclick="filterPosts('자유')">자유게시판</button>
        </div>

        <div class="content-container">
        
<!------------------------------------------------------------------------------------------------------>
		    <div class="content" id="wishlist" style="display: block;">
		        <ul class="post-list">
		            
		        </ul>
		    </div>
<!------------------------------------------------------------------------------------------------------>
		    <div class="content" id="myPosts">
		        <ul class="post-list">
		            <table align="center" class="list-area">
				         <thead>
				            <tr>
				               <th width="80">글번호</th>
				               <th width="340">제목</th>
				               <th width="110">작성일</th>
				            </tr>
				         </thead>
				         <tbody>
				            <% if(frlist.isEmpty()) { %>
				               <tr>
				                  <td colspan="6">
				                     조회된 리스트가 없습니다.
				                  </td>
				               </tr>
				            <% } else { %>
				               <% for(FreeBoard fb : frlist) { %>
				                  <tr>
				                     <td><%= fb.getFreeNo() %></td>
				                     <td><%= fb.getFreeTitle() %></td>
				                     <td><%= fb.getFreeDate() %></td>
				                  </tr>
				               <% } %>  
				            <% } %>
				         </tbody>
			      </table>
		        </ul>
		    </div>
<!------------------------------------------------------------------------------------------------------>
		    <div class="content" id="myComments">
		        <ul class="post-list">
		            
		        </ul>
		    </div>
<!------------------------------------------------------------------------------------------------------>
		<!-- 페이징바 -->
		<div align="center" class="paging-area">

			<!-- 
				1번 페이지일 경우에는 이전 버튼을 아예 안보이게 
				즉, 1번 페이지가 아닐 경우에만 이전 버튼이 보여지게끔!!	
			-->
			<% if(currentPage != 1) { %>
			
				<button onclick="location.href = '<%= contextPath %>/mylist.fh?currentPage=<%= currentPage - 1 %>';">
					&lt;
				</button>
				
			<% } %>

			<% for(int p = startPage; p <= endPage; p++) { %>
				
				<!-- 
					현재 출력해야하는 p 번 페이지가 currentPage 와 일치하지 않는 경우
					onclick 속성이 제대로 동작하게끔!!
				-->
				<% if(p != currentPage) { %>
				
					<button onclick="location.href = '<%= contextPath %>/mylist.fh?currentPage=<%= p %>';">
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
			
				<button onclick="location.href = '<%= contextPath %>/mylist.fh?currentPage=<%= currentPage + 1 %>';">
					&gt;
				</button>
				
			<% } %>

		</div>

			   

    </div>

	    <script>
	    function showContent(contentId, tabElement) {
	        // 모든 콘텐츠 숨기기
	        document.querySelectorAll('.content').forEach(content => content.style.display = 'none');
	        document.getElementById(contentId).style.display = 'block';
	
	        // 모든 탭에서 'active' 클래스 제거 후, 선택된 탭에 추가
	        document.querySelectorAll('.myboardandreply').forEach(tab => tab.classList.remove('active'));
	        tabElement.classList.add('active');
	
	        // "내가 쓴 게시물" 탭인지 확인하고 필터 조정
	        if (contentId === 'myPosts') {
	            document.querySelector('button[onclick="filterPosts(\'정보\')"]').style.display = 'none';
	        } else {
	            document.querySelector('button[onclick="filterPosts(\'정보\')"]').style.display = 'inline-block';
	        }
	    }
	    
	    function filterPosts(category) {
	        // 각 콘텐츠를 확인하여 해당 카테고리와 일치하는 경우에만 표시
	        document.querySelectorAll('.content:visible .post-list li').forEach(post => {
	            post.style.display = post.getAttribute('data-category') === category ? 'block' : 'none';
	        });
	
	        // 모든 콘텐츠에서 보여줄 게시물만 필터링
	        document.querySelectorAll('.content').forEach(content => {
	            const posts = content.querySelectorAll('.post-list li');
	            const hasVisiblePosts = Array.from(posts).some(post => post.style.display === 'block');
	            content.style.display = hasVisiblePosts ? 'block' : 'none';
	        });
	    }
	    
	    function changePage(page) {
	        console.log(`페이지 ${page}로 이동`);
	    }
	</script>

</body>
</html>
