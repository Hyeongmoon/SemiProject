<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList, com.fh.festival.model.vo.Festival, com.fh.notice.model.vo.Notice"%>
<%
    ArrayList<Festival> fList = (ArrayList<Festival>)request.getAttribute("fList");
    ArrayList<Notice> nList = (ArrayList<Notice>)request.getAttribute("nList");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>Festa Hub</title>
<style>
    /* Banner CSS */
    .banner {
        position: relative;
        width: 100%;
        height: 500px;
        overflow: hidden;
        z-index: 1;
    }
    
    .carousel-inner, .carousel-item {
        height: 100%;
    }
    
    .carousel-item > div {
        height: 100%;
        width: 100%;
        background-size: cover;
        background-position: center 10%; /* 상단부터 배치 */
        z-index: 1;
    }

    .carousel-indicators {
        gap: 5px;
        z-index: 3;
        margin-bottom: 0px;
    }
    .carousel-indicators li {
        background-color: #493375 !important;
        width: 10px !important;
        height: 10px !important;
        border-radius: 50%;
        opacity: 0.5;
    }
    .carousel-indicators .active {
        opacity: 1;
    }
    
    .carousel-control-next, .carousel-control-prev {
		width: 5% !important;    
    }
    
    	/* 좌우 화살표 위치를 container-fluid 안으로 조정 */
	.carousel-control-prev {
	    left: calc(50% - min(600px, 50%)) !important; /* 최대 1200px까지 유지하고, 창 크기에 맞춰서 줄어듦 */
	}
	
	.carousel-control-next {
	    right: calc(50% - min(600px, 50%)) !important; /* 최대 1200px까지 유지하고, 창 크기에 맞춰서 줄어듦 */
	}
    
    .carousel-control-prev-icon,
    .carousel-control-next-icon {
        background-color: rgba(0, 0, 0, 0.5);
        padding: 15px;
    }
    .carousel-caption {
        position: absolute;
        bottom: 20px;
        left: 0 !important;
        right: 0 !important;
        background-color: rgba(0, 0, 0, 0.5);
        padding: 15px;
        color: #f0f0f5;
        width: 100%;
        z-index: 4;
        text-align: left !important;
    }
    .banner-title {
        font-size: 2.5rem;
        color: #f0f0f5;
        font-weight: bold;
        text-shadow:
            -2px -2px 0 #493375,
             2px -2px 0 #493375,
            -2px  2px 0 #493375,
             2px  2px 0 #493375,
            -3px -3px 0 #493375,
             3px -3px 0 #493375,
            -3px  3px 0 #493375,
             3px  3px 0 #493375;
    }
    .banner-item {
        color: #493375;
        font-weight: bold;
        margin: 5px;
        text-shadow: -1px -1px 0 #f0f0f5, 
                     1px -1px 0 #f0f0f5,
                    -1px 1px 0 #f0f0f5, 
                     1px 1px 0 #f0f0f5;
    }

    /* 리스트 박스 스타일 */
    .main-section {
        padding-top: 20px;
        padding-bottom: 20px;
        background-color: #ffffff;
        color: #333;
    }
    .list-section {
        display: flex;
        justify-content: space-between;
    }
    .list-box {
        width: 48%;
        background-color: #f0f0f5;
        padding: 15px;
        border-radius: 8px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        position: relative;
    }
    .list-box h3 {
        font-size: 1.3rem;
        font-weight: bold;
        color: #493375;
    }
    .list-item {
        padding: 5px 0;
        border-bottom: 1px solid #ccc;
        font-size: 0.9rem;
        display: flex;
        justify-content: space-between;
        cursor: pointer;
    }
    .list-item p {
        margin: 0;
        color: #2A253F;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
    }
    .date {
        font-size: 0.8rem;
        color: #777;
        margin-left: auto;
    }
    .more-btn {
        position: absolute;
        top: 15px;
        right: 15px;
        font-weight: bold;
        color: #493375;
        text-decoration: none;
    }
    .more-btn:hover {
        color: #493375;
    }

    /* 게시판 버튼 스타일 */
    .board-buttons {
        display: flex;
        justify-content: space-between;
        margin-top: 30px;
        width: 100%;
    }
    .board-buttons .board-btn {
        width: 32%;
        background-color: #f0f0f5;
        padding: 20px;
        text-align: center;
        border-radius: 8px;
        transition: transform 0.3s;
        cursor: pointer;
        display: flex;
	    flex-direction: column;
	    align-items: center;
	    justify-content: center; /* 버튼 내부의 이미지와 텍스트를 수직 중앙 정렬 */
    }
    .board-buttons .board-btn:hover {
        transform: translateY(-5px);
        background-color: #74B99A;
        color: white;
    }
    .board-btn img {
	    width: 90%; /* 부모 요소에 맞춰 크기를 조정 */
	    max-width: 120px; /* 최대 크기를 설정하여 너무 커지지 않도록 */
	    height: auto; /* 비율에 맞게 높이 자동 조정 */
	    margin-bottom: 10px;
	    object-fit: contain; /* 이미지 비율 유지 */
    }
    .board-btn h4 {
        font-size: 1.5rem;
        font-weight: bold;
        margin: 0;
    }
</style>
</head>
<body>

<%@ include file="views/common/navbar.jsp" %>

<!-- Banner Section -->
<div id="festivalCarousel" class="carousel slide banner" data-ride="carousel">
    <ol class="carousel-indicators">
        <% for (int i = 0; i < fList.size(); i++) { %>
            <li data-target="#festivalCarousel" data-slide-to="<%= i %>" class="<%= i == 0 ? "active" : "" %>"></li>
        <% } %>
    </ol>

    <div class="carousel-inner">
        <% for (int i = 0; i < fList.size(); i++) { Festival f = fList.get(i); %>
            <div class="carousel-item <%= i == 0 ? "active" : "" %>"
            	 style="cursor: pointer;" 
             	 onclick="location.href='<%= contextPath %>/detail.fe?fesNo=<%= f.getFesNo() %>'">
                <div style="background-image: url('<%= contextPath + f.getTitleImg() %>');">
                    <div class="carousel-caption">
                    	<div class="container-fluid">
                        <h2 class="banner-title"><%= f.getFesTitle() %></h2>
                        <p class="banner-item"><%= ymdhm.format(f.getFesDay()) %></p>
						<p class="banner-item"><%= f.getFesAddress() %></p>
						</div>
                    </div>
                </div>
            </div>
        <% } %>
    </div>
	
    <a class="carousel-control-prev" href="#festivalCarousel" role="button" data-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="sr-only">이전</span>
    </a>
    <a class="carousel-control-next" href="#festivalCarousel" role="button" data-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="sr-only">다음</span>
    </a>
</div>

<!-- Main Section -->
<div class="main-section">
	<div class="container-fluid">
    <div class="list-section">
        <!-- 공연정보 리스트 -->
        <div class="list-box">
            <h3><a href="<%= contextPath %>/list.fe?currentPage=1" style="text-decoration: none; color: #493375;">공연정보</a></h3>
            <a href="<%= contextPath %>/list.fe?currentPage=1" class="more-btn">더보기</a>
            <% for(Festival f : fList) { %>
                <div class="list-item" onclick="location.href='<%= contextPath %>/detail.fe?fesNo=<%= f.getFesNo() %>'">
                    <p><strong><%= f.getFesTitle().length() > 25 ? f.getFesTitle().substring(0, 25) + "..." : f.getFesTitle() %></strong></p>
                    <p class="date"><%= ymdhm.format(f.getFesDay()) %> | <%= f.getFesAddress() %></p>
                </div>
            <% } %>
        </div>

        <!-- 공지사항 리스트 -->
        <div class="list-box">
            <h3><a href="<%= contextPath %>/list.no?currentPage=1" style="text-decoration: none; color: #493375;">공지사항</a></h3>
            <a href="<%= contextPath %>/list.no?currentPage=1" class="more-btn">더보기</a>
            <% for(Notice n : nList) { %>
                <div class="list-item" onclick="location.href='<%= contextPath %>/detail.no?nno=<%= n.getNoticeNo() %>'">
                    <p><strong><%= n.getNoticeTitle().length() > 20 ? n.getNoticeTitle().substring(0, 20) + "..." : n.getNoticeTitle() %></strong></p>
                    <p class="date"><%= ymd.format(n.getNoticeDate()) %></p>
                </div>
            <% } %>
        </div>
    </div>

    <!-- 게시판 버튼 -->
    <div class="board-buttons">
        <div class="board-btn" onclick="location.href='<%= contextPath %>/rvlist.fh?currentPage=1'">
            <img src="<%= contextPath %>/resources/images/reviewLogo.png" alt="공연후기">
            <h4>공연후기</h4>
        </div>
        <div class="board-btn" onclick="location.href='<%= contextPath %>/list.ac?currentPage=1'">
            <img src="<%= contextPath %>/resources/images/accompanyLogo.png" alt="동행구하기">
            <h4>동행구하기</h4>
        </div>
        <div class="board-btn" onclick="location.href='<%= contextPath %>/list.free'">
            <img src="<%= contextPath %>/resources/images/noticeLogo.png" alt="자유게시판">
            <h4>자유게시판</h4>
        </div>
    </div>
    </div>
</div>

<%@ include file="views/common/footer.jsp" %>

</body>
</html>
