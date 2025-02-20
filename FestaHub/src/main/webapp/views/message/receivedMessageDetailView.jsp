<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.fh.message.model.vo.Message" %>    
<%

	Message msg = (Message)request.getAttribute("msg");
	Message prevMsg = (Message)request.getAttribute("prevMsg");
	Message nextMsg = (Message)request.getAttribute("nextMsg");


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
    /* --표시선(지울거)----------------------------------------------------------------*/
    /*
    #outer>div{
        border:3px dotted lightgray;
    }
    */
    #board-outer>div>div{
        /* border:2px dotted red; */
    }
    #board-outer>div>div>div{
        /* border:1px dotted yellow */
    }
    /* ---------------------------------------------------------------------------- */
    #board-outer{
        width:1400px;
        /* border:1px dotted black;    바깥 선 */
        margin:auto;
        margin-top:40px;
    }
    #board-outer>div{
        margin: 20px;
    }
    #board-table td{ /* 테이블 행 간격*/
        height:85px;
    }
    #board-table{   /*테이블 위아래구분선*/
        border:none;
        border-top: 4px solid gray;
        border-bottom: 4px solid gray;
    }
    #board-table td{ 
        vertical-align: middle;
        padding: 10px;
    }
    #board-table>tbody>tr>td>div>span:nth-child(1){ /*글쓴이위치 너비*/
        display : inline-block;
        width:80px;
    }
    #board-table>tbody>tr>td>div>span:nth-child(2){ /*날짜위치 너비*/
        display : inline-block;
        width: 100px;
    }
    #board-table>tbody>tr>td>div>span:nth-child(3){ /*조회수위치 너비*/
        display : inline-block;
        width:80px;
    }
    /* ---------------------------------------------------------------------------- */
</style>
</head>
<body>
	<%@ include file="../common/navbar.jsp"  %>
	
    <div id="board-outer">
        <!-- 헤드 -->
        <div class="list-head">
            <div>
                <h1 style="display: inline; font-size: 70px; vertical-align: middle;">
                    받은 쪽지
                </h1>
            </div>
        </div>
        <div >
            <div style="float: right;">
                  
                <span>보낸 사람 : <%= msg.getUserNickname() %></span>
                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                <span>보낸 날짜 : <%= msg.getMsgDate() %></span>
            </div>
        </div>
        <br>
        <!-- 바디부분 -->
        <div class="list-body">
            <div class="table-area">
                <table id="board-table" class="table" >
                    <tbody>
                        <tr height="500" >
                            <td style="vertical-align: text-top;">
                                <p>
                                    <%= msg.getMsgContent() %>
                                </p>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <br><br>
            
            <div class="table-area">
                <table id="board-table" class="table table-hover" >
                    <tbody>
                    <% if(prevMsg != null) { %>
                        <tr>
                            <td>
                                이전글 : 
                                <a href="<%= contextPath %>/detail.rme?nrme=<%= prevMsg.getMsgNo() %>"
                                   style="text-decoration: none; color: black;">
                                	<%= prevMsg.getMsgContent() %>
                                </a>
                            </td>
                        </tr>
                  	<% } else { %>
                        <tr>
                            <td>이전글 : 이전글이 없습니다</td>
                        </tr>
                    <% }  %>    
                    <% if(nextMsg != null) { %>    
                        <tr>
                            <td>
                                다음글 : 
                                <a href="<%= contextPath %>/detail.rme?nrme=<%= nextMsg.getMsgNo() %>"
                                   style="text-decoration: none; color: black;">
                                	<%= nextMsg.getMsgContent() %>
                               	</a>
                            </td>
                        </tr>
                    <% } else { %>   
                        <tr>
                            <td>다음글 : 다음글이 없습니다</td>
                        </tr>
                    <% }  %>
                    </tbody>
                </table>
            </div>

            <!-- 수정 삭제 목록 버튼 -->
            <!-- 수정 삭제는 작성자 또는 관리자만 보이게 처리 -->
            <div class="button-area">
                <div align="right">
                <% if((loginUser != null) 
					&& (loginUser.getUserNo() == msg.getReceiverNo())) { %>
                    <button onclick="location.href='<%= contextPath %>/delete.rme?nrme=<%= msg.getMsgNo() %>'"
                            class="btn btn-sm btn-secondary">
                    	삭제
                    </button>
                <% } %>
                    <button onclick="location.href='<%= contextPath %>/list.rme?currentPage=1'"
                            class="btn btn-sm btn-secondary">
					    목록
					</button>
                </div>
            </div>
        </div>
    </div>
    
    <%@ include file="../common/footer.jsp"  %>
</body>
</html>