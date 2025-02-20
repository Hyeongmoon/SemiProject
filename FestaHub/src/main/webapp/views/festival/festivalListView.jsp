<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList, com.fh.festival.model.vo.Festival, 
				com.fh.common.model.vo.PageInfo"%>
<%
	// request 로 부터 응답데이터인 list 를 뽑기
	ArrayList<Festival> list
		= (ArrayList<Festival>)request.getAttribute("list");
	
	PageInfo pi = (PageInfo)request.getAttribute("pi");
	
	int currentPage = pi.getCurrentPage();
	int startPage = pi.getStartPage();
	int endPage = pi.getEndPage();
	int maxPage = pi.getMaxPage();
	
	session.setAttribute("currentPage", currentPage);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Festa Hub - 공연 정보</title>

    <style>
        h2 {
            font-size: 2rem;
            font-weight: bold;
            color: #2A253F;
            margin-bottom: 20px;
        }

        /* 검색 영역 */
        .form-inline {
            display: flex;
            justify-content: flex-end;
            align-items: center;
            margin-bottom: 20px;
        }

        .input-group-prepend select {
            font-weight: bold;
            color: #2A253F;
            background-color: #f0f0f5;
        }

        /* 썸네일 카드 스타일 */
        .card {
            background-color: #f0f0f5 !important;
            color: #333 !important;
            border: none !important;
            border-radius: 10px !important;
            transition: transform 0.3s !important;
        }

        .card:hover {
            transform: translateY(-5px);
            background-color: #74B99A !important;
            color: white !important;
            cursor: pointer;
        }

        .card img {
            height: 300px;
            width: 100%;
            object-fit: contain; /* 이미지가 잘리지 않도록 비율 유지하며 컨테이너에 맞춤 */
            border-top-left-radius: 10px;
            border-top-right-radius: 10px;
        }

        .card-body {
            padding: 8px !important;
        }

        .card-title {
            font-size: 1rem;
            font-weight: bold;
            margin-bottom: 5px !important;
        }

        .card-text {
            font-size: 0.8rem;
            color: #777;
            margin-bottom: 2px !important;
        }
        
        .card:hover .card-text {
        	color: white !important;
        }

        /* 페이지 네비게이션 */
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

        /* 반응형 */
        @media (min-width: 1200px) {
            .col-lg-2-4 {
                flex: 0 0 20% !important;
                max-width: 20% !important;
            }
        }

        @media (max-width: 768px) {
            .form-inline {
                flex-direction: column !important;
                align-items: flex-start !important;
                 margin-bottom: 30px !important; /* 하단 여백 추가 */
            }

            #searchInput {
                width: 100% !important;
                margin-top: 10px !important;
            }

            .col-md-3 {
                flex: 0 0 50% !important;
                max-width: 50% !important;
            }
            
         	.col-md-6.text-right {
		        margin-top: 10px !important; /* 검색창 상단 간격 축소 */
		        position: static !important; /* 상단 위치에서 내려오도록 설정 */
		        text-align: left !important; /* 작은 화면에서 좌측 정렬 */
		    }
		}

        @media (max-width: 576px) {
            .col-md-3 {
                flex: 0 0 100% !important;
                max-width: 100% !important;
            }
        }

    </style>
</head>
<body>
	<!-- 상단바 -->
	<%@ include file="../common/navbar.jsp" %>
	
	<!-- 메인 컨텐츠 공간 -->
    <div class="container-fluid">
        <div class="row">
            <!-- 좌측 상단 게시판 이름 -->
            <div class="col-md-6" style="display: flex; align-items: center;">
            	<img src="<%=contextPath%>/resources/images/festivalLogo.png" alt="게시판이미지" style="height: 100px; margin-right: 10px;">
                <h1 style="margin: 0; position: relative; top: 10px;">공연 정보 <span style="font-size: 0.5em; color: gray;"><%= request.getAttribute("listCount") %></span></h1>
            </div>

			<!-- 검색 영역 -->
			<div class="col-md-6 text-right" style="margin: 0; position: relative; top: 40px;">
			    <form class="form-inline" method="get" action="<%= contextPath %>/search.fe">
			        <div class="input-group">
			            <div class="input-group-prepend">
			                <select class="custom-select" name="category">
			                    <option value="FES_TITLE" <%= "FES_TITLE".equals(request.getParameter("category")) ? "selected" : "" %>>공연 제목</option>
			                    <option value="FES_ADDRESS" <%= "FES_ADDRESS".equals(request.getParameter("category")) ? "selected" : "" %>>공연 장소</option>
			                </select>
			            </div>
			            <input type="text" class="form-control" name="keyword" placeholder="검색어를 입력하세요" value="<%= request.getParameter("keyword") != null ? request.getParameter("keyword") : "" %>">
			            <div class="input-group-append">
			                <button type="submit" class="btn btn-primary">검색</button>
			            </div>
			        </div>
			    </form>
			</div>
        </div>

        <!-- 공연 정보 썸네일 게시판 영역 -->
            <!-- 5열 썸네일 게시판 생성 -->
        	<% if(list.isEmpty()) { %>
				<div style="display: flex; justify-content: center; align-items: center; height: 100px;">
				    <p style="text-align: center; margin: 0;">조회된 리스트가 없습니다.</p>
				</div>
			<% } else { %>
		        <div class="row list-area">
				<% for(Festival f : list) { %>
		            <div class="col-lg-2-4 col-md-3 mb-4">
		                <div class="card">
		                	<input type="hidden" value="<%= f.getFesNo() %>">
		                	
		                	<% if(f.getTitleImg() != null) { %>
		                    	<img src="<%= contextPath + f.getTitleImg() %>" class="card-img-top" alt="공연 썸네일">
		                    <% } %>
		                    
		                    <div class="card-body">
		                        <h5 class="card-title"><%= (f.getFesTitle().length() <= 12) ? f.getFesTitle() : f.getFesTitle().substring(0, 12) + "..." %> </h5>
		                        <p class="card-text">공연 날짜: <%= ymdhm.format(f.getFesDay()) %></p>
		                        <p class="card-text">공연 장소: <%= (f.getFesAddress().length() <= 10) ? f.getFesAddress() : f.getFesAddress().substring(0, 10) + "..." %></p>
		                        <p class="card-text"><%= f.getFesWriter() %> | <%= ymd.format(f.getFesDate()) %> | 조회 <%= f.getFesCount() %></p>
		                        <p class="card-text"><span><%= f.isLiked() ? "❤️" : "🤍" %> <%= f.getLikeCount() %></span> | 💬 <%= f.getCommCount() %> </p>
		                    </div>
		                </div>
		            </div>
		        <% } %>
				</div>
		    <% } %>
		    
        <!-- 글쓰기 버튼 -->
        <div class="row">
            <div class="col text-right mb-3">
		        <% if(loginUser != null && loginUser.getUserId().equals("admin")) { %>
		        	<a href="<%= contextPath %>/enrollForm.fe" class="btn btn-primary">글쓰기</a>
				<% } %>
            </div>
        </div>

		<!-- 페이지 네비게이션 -->
		<% 
		    // category와 keyword 파라미터를 가져와 변수에 저장
		    String category = request.getParameter("category");
		    String keyword = request.getParameter("keyword");
		    String baseUrl = (category != null && keyword != null) 
		                     ? "search.fe?category=" + category + "&keyword=" + keyword 
		                     : "list.fe";
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
        
    </div>

    <script>
        // 검색 버튼 클릭 시 검색 처리
        $("#searchSubmit").submit(function (e) {
            e.preventDefault();
            var category = $("#searchCategory").val();
            var keyword = $("#searchInput").val();
            alert("검색 카테고리: " + category + ", 검색어: " + keyword);
            // 실제 검색 처리 로직은 여기에 작성
        });
        
		$(function() {
			
			$(".card").click(function() {
				
				let fesNo = $(this).children().eq(0).val();
				
				location.href = "<%= contextPath %>/detail.fe?fesNo=" + fesNo;
				
			});
			
		});
        
    </script>
	
	<!-- 하단바 -->
	<%@ include file="../common/footer.jsp" %>
	
</body>
</html>