<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList, com.fh.accompanyBoard.model.vo.Accompany, com.fh.festival.model.vo.FestivalImage, com.fh.common.model.vo.PageInfo" %>
<%
	Accompany ac = (Accompany) request.getAttribute("ac");
	Accompany prevAc = (Accompany) request.getAttribute("prevAc");
	Accompany nextAc = (Accompany) request.getAttribute("nextAc");

	// 삭제요청 시 리다이렉트 처리하기위한 변수
	session.setAttribute("caller", "admin");
	
    int currentPage = (int) session.getAttribute("currentPage");
%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Festa Hub - Accompany Detail</title>
    <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link href="css/sb-admin-2.min.css" rel="stylesheet">
</head>

<body id="page-top">
    <!-- Page Wrapper -->
    <div id="wrapper">
        <%@ include file="sidebar.jsp" %> <!-- 사이드바 인클루드 -->

        <!-- Content Wrapper -->
        <div id="content-wrapper" class="d-flex flex-column">
            <!-- Main Content -->
            <div id="content">
                <!-- Page Content -->
                <div class="container-fluid">
                    <h1 class="h3 mb-4 text-gray-800">Festival Detail</h1>
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">Title:<%= ac.getAccomTitle() %></h6>
                        </div>
                        <div class="card-body">
                        	<p><strong>No:</strong><%= ac.getAccomNo() %></p>
                            <p><strong>Date:</strong><%= ac.getAccomDate() %></p>
                            <p><strong>UserNickname:</strong><%= ac.getUserNickname() %></p>
                            <p><strong>Count:</strong><%= ac.getAccomCount() %></p>
                            <p><strong>Like:</strong><%= ac.getLikeCount() %></p>
                            <p><strong>Content:</strong><%= ac.getAccomContent() %></p>
                                                        
                        </div>
                    </div>
                    
                    <!-- 글삭제 버튼 -->
				    <div class="text-right mb-4">
				        <a href="<%= contextPath %>/delete.ac?nac=<%= ac.getAccomNo() %>" class="btn btn-primary">글삭제</a>
				    </div>

                    
                    <!-- 댓글 작성 영역 -->
                    
                    
                    <!-- 이전글, 다음글 링크 -->
                    <div class="d-flex justify-content-between">
                        <% if (prevAc != null) { %>
                            <a href="adminDetail.ac?nac=<%= prevAc.getAccomNo() %>" class="btn btn-outline-primary">Previous: <%= prevAc.getAccomNo() %></a>
                        <% } else { %>
                            <span class="text-muted">No Previous Post</span>
                        <% } %>
                        
                        <a href="adminTool.accompany" class="btn btn-secondary">Back to List</a>

                        <% if (nextAc != null) { %>
                            <a href="adminDetail.ac?nac=<%= nextAc.getAccomNo() %>" class="btn btn-outline-primary">Next: <%= nextAc.getAccomNo() %></a>
                        <% } else { %>
                            <span class="text-muted">No Next Post</span>
                        <% } %>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- JavaScript -->
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>


</body>
</html>
