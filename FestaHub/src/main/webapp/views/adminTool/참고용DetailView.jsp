<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList, com.fh.festival.model.vo.Festival, com.fh.festival.model.vo.FestivalImage, com.fh.common.model.vo.PageInfo" %>
<%
    Festival f = (Festival) request.getAttribute("f");
    Festival prevFes = (Festival) request.getAttribute("prevFes");
    Festival nextFes = (Festival) request.getAttribute("nextFes");
    ArrayList<FestivalImage> fiList = (ArrayList<FestivalImage>) request.getAttribute("fiList");
    int currentPage = (int) session.getAttribute("currentPage");
%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Festa Hub - Festival Information Detail</title>
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
            
  	    	<%@ include file="topbar.jsp" %> <!-- 탑바 인클루드 -->
                <!-- Page Content -->
                <div class="container-fluid">
                    <h1 class="h3 mb-4 text-gray-800">Festival Detail</h1>
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary"><%= f.getFesTitle() %></h6>
                        </div>
                        <div class="card-body">
                            <p><strong>Date:</strong> <%= f.getFesDay() %></p>
                            <p><strong>Location:</strong> <%= f.getFesAddress() %></p>
                            <p><%= f.getFesContent() %></p>
                            <% if (!fiList.isEmpty()) { 
                                for (FestivalImage fi : fiList) { %>
                                    <img src="<%= contextPath + fi.getFesImgPath() + fi.getFesImgRename() %>" alt="Festival Image" class="img-thumbnail" style="width: 150px;">
                            <% } } else { %>
                                <p>No additional images.</p>
                            <% } %>
                        </div>
                    </div>
                    
                    <!-- 댓글 작성 영역 -->
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">Comments</h6>
                        </div>
                        <div class="card-body">
                            <div id="commentSection">
                                <textarea id="commentInput" class="form-control mb-2" rows="2" placeholder="Enter your comment"></textarea>
                                <button onclick="insertComment()" class="btn btn-primary">Add Comment</button>
                            </div>
                            <div id="commentList" class="mt-4"></div>
                        </div>
                    </div>
                    
                    <!-- 이전글, 다음글 링크 -->
                    <div class="d-flex justify-content-between">
                        <% if (prevFes != null) { %>
                            <a href="adminDetail.fe?fesNo=<%= prevFes.getFesNo() %>" class="btn btn-outline-primary">Previous: <%= prevFes.getFesTitle() %></a>
                        <% } else { %>
                            <span class="text-muted">No Previous Post</span>
                        <% } %>
                        
                        <a href="adminTool.festival" class="btn btn-secondary">Back to List</a>

                        <% if (nextFes != null) { %>
                            <a href="adminDetail.fe?fesNo=<%= nextFes.getFesNo() %>" class="btn btn-outline-primary">Next: <%= nextFes.getFesTitle() %></a>
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

    <script>
        $(document).ready(function() {
            loadComments();

            function loadComments() {
                $.ajax({
                    url: "<%= contextPath %>/clist.fe",
                    type: "GET",
                    dataType: "json",
                    data: { fesNo: <%= f.getFesNo() %> },
                    success: function(result) {
                        let resultStr = "";
                        const loginUserId = "<%= (loginUser != null) ? loginUser.getUserId() : "" %>";

                        for (let i in result) {
                            resultStr += "<div id='comment_" + result[i].fesCommNo + "' class='comment'>"
                                       +     "<p class='nickname'>" + (result[i].fesCommWriter ? result[i].fesCommWriter : "Anonymous") + "</p>"
                                       +     "<pre>" + (result[i].fesCommContent ? result[i].fesCommContent : "No Content") + "</pre>"
                                       +     "<p class='text-muted'>" + (result[i].fesCommDate ? result[i].fesCommDate : "No Date") + "</p>";
                            
                            // 댓글 작성자와 로그인 사용자가 동일한 경우에만 수정/삭제 버튼 표시
                            if (loginUserId == "admin") {
                                resultStr += "<div class='comment-buttons'>"
                                           +     "<button class='btn btn-sm btn-outline-secondary' onclick='updateComment(" + result[i].fesCommNo + ");'>수정</button>"
                                           +     "<button class='btn btn-sm btn-outline-danger' onclick='deleteComment(" + result[i].fesCommNo + ");'>삭제</button>"
                                           + "</div>";
                            }
                            resultStr += "</div>";
                        }

                        $("#commentList").html(resultStr); // 결과를 HTML 요소에 삽입
                    },
                    error: function() {
                        alert("Failed to load comments.");
                    }
                });
            }

            window.insertComment = function() {
                let content = $('#commentInput').val();
                if (content.trim() === "") {
                    alert("Please enter a comment.");
                    return;
                }
                $.ajax({
                    url: "<%= contextPath %>/cinsert.fe",
                    type: "POST",
                    data: { fesNo: <%= f.getFesNo() %>, content: content },
                    success: function() {
                        $('#commentInput').val("");
                        loadComments();
                    },
                    error: function() {
                        alert("Failed to add comment.");
                    }
                });
            }

            window.updateComment = function(commentId) {
                let newContent = prompt("Edit your comment:");
                if (newContent) {
                    $.ajax({
                        url: "<%= contextPath %>/cupdate.fe",
                        type: "POST",
                        data: { commentId: commentId, content: newContent },
                        success: function() {
                            loadComments();
                        },
                        error: function() {
                            alert("Failed to update comment.");
                        }
                    });
                }
            }

            window.deleteComment = function(commentId) {
                if (confirm("Are you sure you want to delete this comment?")) {
                    $.ajax({
                        url: "<%= contextPath %>/cdelete.fe",
                        type: "POST",
                        data: { commentId: commentId },
                        success: function() {
                            loadComments();
                        },
                        error: function() {
                            alert("Failed to delete comment.");
                        }
                    });
                }
            }
        });
    </script>
</body>
</html>
