<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList, com.fh.notice.model.vo.Notice, com.fh.festival.model.vo.FestivalImage, com.fh.common.model.vo.PageInfo" %>
<%
	Notice n = (Notice) request.getAttribute("n");
	Notice prevn = (Notice) request.getAttribute("prevn");
	Notice nextn = (Notice) request.getAttribute("nextn");
	
	// 삭제요청 시 리다이렉트 처리하기위한 변수
	session.setAttribute("caller", "admin");
	
    int currentPage = (int) session.getAttribute("currentPage");
%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Festa Hub - Notice Detail</title>
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
                    <h1 class="h3 mb-4 text-gray-800">Notice Detail</h1>
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">Title:<%= n.getNoticeTitle() %></h6>
                        </div>
                        <div class="card-body">
                        	<p><strong>No:</strong> <%= n.getNoticeNo() %></p>
                            <p><strong>Date:</strong> <%= n.getNoticeDate() %></p>
                            <p><strong>Count:</strong><%= n.getNoticeCount() %></p>
                            <p><strong>Content:</strong><%= n.getNoticeContent() %></p>
                        </div>
                    </div>
                    
                    <!-- 글삭제 버튼 -->
				    <div class="text-right mb-4">
				        <a href="<%= contextPath %>/delete.no?nno=<%= n.getNoticeNo() %>" class="btn btn-primary">글삭제</a>
				    </div>

                    
                    <!-- 댓글 작성 영역 -->
                    
                    
                    <!-- 이전글, 다음글 링크 -->
                    <div class="d-flex justify-content-between">
                        <% if (prevn != null) { %>
                            <a href="adminDetail.no?nno=<%= prevn.getNoticeNo() %>" class="btn btn-outline-primary">Previous: <%= prevn.getNoticeNo() %></a>
                        <% } else { %>
                            <span class="text-muted">No Previous Post</span>
                        <% } %>
                        
                        <a href="adminTool.notice" class="btn btn-secondary">Back to List</a>

                        <% if (nextn != null) { %>
                            <a href="adminDetail.no?nno=<%= nextn.getNoticeNo() %>" class="btn btn-outline-primary">Next: <%= nextn.getNoticeNo() %></a>
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
