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
	

	PageInfo pi = (PageInfo)request.getAttribute("pi");
	int currentPage = pi.getCurrentPage();
	int listCount = pi.getListCount();
	int startPage = pi.getStartPage();
	int endPage = pi.getEndPage();
	int maxPage = pi.getMaxPage();
	int pageLimit = pi.getPageLimit();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>내가 찜한 게시물(자유게시판)</title>

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
        .content-container {
            padding: 20px;
            border: 1px solid lightgray;
            height: 90%;
            overflow-y: auto;
        }
        .list-area {
            width: 100%;
            text-align: center;
            border-collapse: collapse;
            border-spacing: 0;
            margin-top: 20px;
        }
        .list-area th, .list-area td {
            padding: 12px;
            border: 1px solid #ddd;
            font-size: 14px;
            vertical-align: middle;
            text-align: center;
        }
        .list-area th {
            background-color: #493375;
            color: white;
            font-weight: bold;
        }
        .list-area td {
            background-color: #ffffff;
            color: #333;
            text-align: center;
        }
        .list-area .title-cell {
            text-align: left;
        }
        .list-area tr:hover td {
            background-color: #f0f0f5;
            cursor: pointer;
        }
        .pagination {
            justify-content: center;
            margin-top: 20px;
        }
        .pagination button {
            color: #493375;
            border: 1px solid #ddd;
            padding: 5px 10px;
            border-radius: 5px;
            margin: 0 5px;
            background-color: white;
            cursor: pointer;
        }
        .pagination button:hover {
            background-color: #6a5e9b;
            color: white;
        }
        .pagination button.active {
            background-color: #493375;
            color: white;
            border: none;
        }
    </style>
</head>
<body>
    <%@ include file="/views/common/navbar.jsp" %>

    <div class="container">
        <h2 class="title" style="font-size: 30px; color: #fff; background-color: #493375; padding: 15px 20px; border-radius: 10px; text-shadow: 2px 2px 5px rgba(0, 0, 0, 0.3); margin-bottom: 20px; border: 2px solid #6a5e9b; animation: pulse 1.5s infinite;">내가 찜한 게시물(자유게시판)</h2>
        <br><hr>
        <div class="content-container">
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
                            <td colspan="3">조회된 리스트가 없습니다.</td>
                        </tr>
                    <% } else { %>
                        <% for(FreeBoard f : frlist) { %>
                            <tr class="freeBoard">
                                <td><%= f.getFreeNo() %></td>
                                <td class="title-cell"><b><%= f.getFreeTitle() %></b></td>
                                <td><%= f.getFreeDate() %></td>
                            </tr>
                        <% } %>
                    <% } %>
                </tbody>
            </table>
            
            <!-- 페이징바 -->
            <div align="center" class="pagination">
                <% if(currentPage != 1) { %>
                    <button onclick="location.href = '<%= request.getContextPath() %>/dibfree.fh?currentPage=<%= currentPage - 1 %>';">&lt;</button>
                <% } %>

                <% for(int p = startPage; p <= endPage; p++) { %>
                    <% if(p != currentPage) { %>
                        <button onclick="location.href = '<%= request.getContextPath() %>/dibfree.fh?currentPage=<%= p %>';"><%= p %></button>
                    <% } else { %>
                        <button disabled class="active"><%= p %></button>
                    <% } %>
                <% } %>

                <% if(currentPage != maxPage) { %>
                    <button onclick="location.href = '<%= request.getContextPath() %>/dibfree.fh?currentPage=<%= currentPage + 1 %>';">&gt;</button>
                <% } %>
            </div>
        </div>
    </div>
    
    <script>
        $(function(){
            $(".freeBoard").click(function(){
                let freeNo = $(this).children().eq(0).text();
                location.href="<%=contextPath%>/detail.free?freeNo="+freeNo;
            });    
        });
    </script>
    <%@ include file="/views/common/footer.jsp" %>
</body>
</html>

