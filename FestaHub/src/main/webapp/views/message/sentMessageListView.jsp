<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, com.fh.message.model.vo.Message,
				 com.fh.common.model.vo.PageInfo" %>   
<%
	//request 로 부터 응답데이터인 list, pi 뽑기
	ArrayList<Message> list = 
		(ArrayList<Message>)request.getAttribute("list");
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

    .list-head>div>h1{
        display: inline; 
        font-size: 50px; 
        vertical-align: middle;
        font-weight: bold;
    }
    .list-body table tbody {
        cursor : pointer;
    }
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
    #board-table td{ /* 테이블 td들의 텍스트상하 가운데 정렬*/
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
                <img src="resources/images/messageLogo.png" alt="쪽지이미지" width="150">
                <h1>
                    보낸 쪽지
                </h1>
                <span style="vertical-align:-30px;"><%= pi.getListCount() %></span>
            </div>
        </div>
        <!-- 바디부분 -->
        <div class="list-body">
            <div class="table-area">
                <table id="board-table" class="table table-hover" >
                    <thead>
                        <tr>
                            <th>받는 사람</th>
                            <th>쪽지 내용</th>
                            <th>날짜</th>
                        </tr>
                    </thead>
                    <tbody>
                        
                        <% if(list.isEmpty()) { %>
					
							<tr>
								<td colspan="3">
									조회된 리스트가 없습니다.
								</td>
							</tr>
						
						<% } else { %>
						
							<% for(Message msg : list) { %>
								
								<tr>
									<td style="display: none;">
	                                    <input type="hidden"  
	                                           name="msgNo" value="<%= msg.getMsgNo() %>">
                                    </td>       
									<td><%= msg.getUserNickname() %></td>
									<td><%= msg.getMsgContent().length()<= 50 ?
											msg.getMsgContent() :
											msg.getMsgContent().substring(0, 50) + "..."%></td>
									<td><%= msg.getMsgDate() %></td>
								</tr>
							
							<% } %>	
								
						<% } %>
                        
                    </tbody>
                </table>
            </div>
             <!-- 
			로그인한 회원만 보여지는 보내기 버튼
			사실 로그인해야 쪽지에 접근가능하긴해서 필요없는거 같긴한데?
			-->
			<% if(loginUser != null) { %>
				
				<div align="right">
					<a href="<%= contextPath %>/sendMsgForm.me"
					   class="btn btn-sm btn-secondary">
						보내기
					</a>
				</div>
			
			<% } %>
        </div>
        <!-- 페이징바영역 -->
        <!-- 페이지 네비게이션 -->
        <div class="row">
            <div class="col text-center">
                <nav>
                    <ul class="pagination justify-content-center">
                    
                    	<!-- 1번 페이지일 경우에는 이전 버튼이 보이지 않게 -->
                        <% if(currentPage > pageLimit) { %>
	                        <li class="page-item">
	                            <a class="page-link" 
	                               href="<%= contextPath %>/list.sme?currentPage=1">
	                            	&lt;&lt;
	                            </a>
	                        </li>
	                        <li class="page-item">
	                            <a class="page-link" href="<%= contextPath %>/list.sme?currentPage=<%= (startPage - 1) %>">
	                            	&lt;
	                            </a>
	                        </li>
                        <% } %>

                        
                        <% for(int p = startPage; p <= endPage; p++) { %>
                        	<!-- 
                        		현재 출력하는 p번 페이지가 currentPage 와 일치하지 않는경우
                        		a태그의 href 속성이 작동되게 구현
                        	 -->
                        	<% if(p != currentPage ) { %>
                        		<li class="page-item">
                        			<a class="page-link" href="<%= contextPath %>/list.sme?currentPage=<%= p %>">
                        				<%= p %>
                        			</a>
                        		</li>
                        	<% } else { %>
								<li class="page-item" style="list-style-type: none;">
								    <a class="page-link" href="#" style="background-color: #e0def3 !important; pointer-events: none;">
								        <%= p %>
								    </a>
								</li>
                        	<% } %>
                        <% } %>
                        <!-- 마지막 페이지가 아닐 경우에만 다음 버튼이 보이도록 작성 -->
                        <% if(currentPage < maxPage && endPage < maxPage) { %>
	                        <li class="page-item">
	                            <a class="page-link" 
	                               href="<%= contextPath %>/list.sme?currentPage=<%= (endPage + 1) %>">
	                            	&gt;
	                            </a>
	                        </li>
	                        <li class="page-item">
	                            <a class="page-link" 
	                               href="<%= contextPath %>/list.sme?currentPage=<%= maxPage %>">
	                            	&gt;&gt;
	                            </a>
	                        </li>
	                    <% } %>

                    </ul>
                </nav>
            </div>
        </div>
        <!-- 페이지 네비게이션 끝 -->
    </div>
    
   	<script>
		$(function() {
			
			$("#board-table>tbody>tr").click(function() {
				
		        let nsme = $(this).find('input[name="msgNo"]').val(); 
		        location.href = "<%= contextPath %>/detail.sme?nsme=" + nsme;
		        
		    });
			
		});
	</script>
    
    <%@ include file="../common/footer.jsp"  %>
    
</body>
</html>