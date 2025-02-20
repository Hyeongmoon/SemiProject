<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.fh.accompanyBoard.model.vo.Accompany" %>    
<%
	Accompany ac = (Accompany)request.getAttribute("ac");
	Accompany prevAc = (Accompany)request.getAttribute("prevAc");
	Accompany nextAc = (Accompany)request.getAttribute("nextAc");

	// 삭제요청 시 리다이렉트 처리하기위한 변수
	session.setAttribute("caller", "user");
	
	//직전에 보던 list 화면을 띄우기위한 변수
	int currentPage = (int)session.getAttribute("acCurrentPage");
	
	
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


    /* 전체영역 */
    .container-fluid>div>div{
        /* border: 1px solid red; */
    }
    .container-fluid>div>div>div{
        /* border: 1px solid blue; */
    }
    .container-fluid>div>div>div>div{
        /* border: 1px solid green; */
    }

    /* =========================================== */
    /* 제목영역 */
    .detail-head {
        display: flex;
        justify-content: space-between; /* 좌측과 우측 정렬 */
        align-items: center; /* 세로 방향으로 가운데 정렬 */   
    }


    .detail-head { /* 제목, 글쓴이,조회수,날짜 한줄로 정렬*/
        display: flex;
        width: 100%; /* 전체 너비 사용 */
    }
    .detail-title {
        flex-grow: 1; /* 공간을 차지하도록 설정 */
    }
    .writer-area {
        text-align: right; /* 내부 텍스트 오른쪽 정렬 */
    }

    .writer-area {
        padding-top: 20px; /* 원하는 만큼 위쪽에 공간 추가 */
        padding-right: 20px;
    }


     /* =========================================== */
    /* 게시글 영역 */
    .detail-content{  /* 게시글 내용 영역 위아래 선*/
        border:none;
        border-top: 4px solid black;
        border-bottom: 4px solid black;
        margin : auto;        
    }

  
    .detail-content>div{   /*게시글내용 위,아래 간격 */
        margin-top : 30px;
        
    }


    /* =========================================== */
    /* 댓글영역 */
    .comment-area,/*댓글부분 위 공백*/
    .insert-comment,/*댁글입력창 위 공백*/
    .page-area, /*페이징바위 공백*/
    .button-area {/* 버튼들 위 공백*/ 
        margin-top : 30px;
    }


    .comment-area,
    .insert-comment{ /*댓글 영역 너비 조절*/
        width:95%;
        margin:auto;
        margin-top : 30px;
    }
    .comment-area>table {
        table-layout: fixed; /* 테이블 레이아웃 고정 */
        width: 100%; 
    }
    .comment-area>table td:nth-child(1){
        width:10%;
    }
    .comment-area>table td:nth-child(2){
        width:80%;
    }
    .comment-area>table td:nth-child(3){
        width:10%;
    }

    .insert-comment{ /* 댓글 입력부분 */
        
        border-radius: 10px;        
        background-color: none;
        border: 3px solid gray;
        padding-left: 3%;
        padding-right:3%;
        height:40px;
        padding-top:5px;
        
        
    }
    .insert-comment input{ /* 댓글입력 input 태그*/
        background-color: none;
        border: none;
        width:85%;
    }
    .insert-comment button{  /* 댓글입력 버튼*/
        border-radius: 10px;
        width:10%;
        border:none;
    }

    /* =========================================== */

    /* 이전,다음 게시글 영역 */
    .page-area{ /* 페이징바 영역 위아래 구분선*/
        border-top: 3px solid black;
        border-bottom : 3px solid black;
    }
    .first-tr>td{ /* 페이징바 영역 가운데 구분선*/
        border-bottom: 1px solid black;
    }

</style>    
</head>
<body>
	<!-- 모든 페이지 상단에 보여줘야 하는 페이지 include -->
	<%@ include file="../common/navbar.jsp" %>
    
    <div class="container-fluid">
      
        <div id="board-deatil">

            <!-- 상단 제목부분 -->
            <div>
                <div>동행구하기 게시판</div>
                <div class="detail-head">
                    <div class="detail-title">
                        <h1><%= ac.getAccomTitle() %></h1>
                    </div>
                    
                    <div class="writer-area">
                        <span><%= ac.getUserNickname() %> | </span>
                        <span>조회수:<%= ac.getAccomCount() %> | </span>
                        <span><%= ac.getAccomDate() %></span>
                    </div>
                </div>
                
            </div>
            
            
            <!-- 중간 게시글내용부분 -->
            <div class="detail-content">
                <div style="height : 300px;"><%= ac.getAccomContent() %></div>
                <div>
                    <span id="likeBtn" onclick="toggleLike()"
          				  style="cursor: pointer; font-size: 1.5em;"></span>
          			<span id="likeCount"></span>
                </div>
            </div>
            
            <!-- 다음글,이전글 부분 -->
            <div class="page-area">
                <table width="100%">
                    <tr class="first-tr">
                        <td width="5%">이전글</td>
                        <%if(prevAc != null) {%>
	                        <td width="85%">
	                        	<a href="<%= contextPath %>/detail.ac?nac=<%= prevAc.getAccomNo() %>" 
	                        	   style="text-decoration: none; color: black;">
	                        		<%= prevAc.getAccomTitle() %>
	                       		</a>
	                        </td>
	                        <td width="10%"><%= prevAc.getAccomDate() %></td>
                        <%} else { %>
		                        <td colspan="2">
		                        	이전글이 없습니다
		                        </td>
                        <% } %>
                    </tr>    
                    <tr>
                        <td>다음글</td>
                        <%if(nextAc != null) {%>
	                        <td>
	                        	<a href="<%= contextPath %>/detail.ac?nac=<%= nextAc.getAccomNo() %>" 
	                        	   style="text-decoration: none; color: black;">
	                        		<%= nextAc.getAccomTitle() %>
	                       		</a>
	                        </td>
	                        <td><%= nextAc.getAccomDate() %></td>
                        <%} else { %>
	                        <td colspan="2">
	                        	다음글이 없습니다
	                        </td>
                        <% } %>
                    </tr>
                </table>
            </div>
            
            
            <!-- 수정 삭제 목록 버튼 -->
            <!-- 수정 삭제는 작성자 또는 관리자만 보이게 처리 -->
            <div class="button-area">
                <div align="right">
                <% if((loginUser != null) 
					&& (loginUser.getUserNo() == Integer.parseInt(ac.getUserNo()))) { %>
                    <button onclick="location.href='<%= contextPath %>/updateForm.ac?nac=<%= ac.getAccomNo() %>'"
                            class="btn btn-sm btn-secondary">
                    	수정
                    </button>
                    <button onclick="location.href='<%= contextPath %>/delete.ac?nac=<%= ac.getAccomNo() %>'"
                            class="btn btn-sm btn-secondary">
                    	삭제
                    </button>
                <% } %>
                    <button onclick="location.href='<%= contextPath %>/list.ac?currentPage=<%= currentPage %>'"
                            class="btn btn-sm btn-secondary">
                    	목록
                    </button>
                </div>
            </div>
            <br>
            
        </div>
    </div>

    <script>
        $(function() { 

            // 좋아요 관련 기능 추가
			// 게시판번호, 유저번호 변수에 담기
	        const accomNo = <%= ac.getAccomNo() %>;
	        const userNo = <%= (loginUser != null) ? loginUser.getUserNo() : 0 %>;
	
	        // 좋아요 상태 초기 설정
	        likeStatus();
	
	        // 좋아요 버튼 클릭 이벤트
	        $("#likeBtn").click(function () {
	            if (userNo == 0) {
	                alert("로그인이 필요합니다.");
	                return;
	            }
	
	            $.ajax({
	                url: "<%= contextPath %>/toggleLike.ac",
	                type: "post",
	                data: { accomNo: accomNo, userNo: userNo },
	                success: function (result) {
	                    $("#likeCount").text(result.likeCount);
	                    $("#likeBtn").html(result.isLiked ? "❤️" : "🤍");
	                },
	                error: function () {
	                    alert("좋아요 요청 실패");
	                }
	            });
	        });
	
	        // 좋아요 상태 초기화 함수
	        function likeStatus() {
	            $.ajax({
	                url: "<%= contextPath %>/likeStatus.ac",
	                type: "get",
	                data: { accomNo: accomNo, userNo: userNo },
	                success: function (result) {
	                    $("#likeCount").text(result.likeCount);
	                    if (result.isLiked && userNo != 0) {
	                        $("#likeBtn").html("❤️");
	                    } else {
	                        $("#likeBtn").html("🤍");
	                    }
	                },
	                error: function () {
	                    console.log("좋아요 상태 초기화 실패");
	                }
	            });
	        }
        });

        
  
    </script>
    
	<!-- 모든 페이지 하단에 보여줘야 하는 페이지 include -->
	<%@ include file="../common/footer.jsp" %>        
        
</body>
</html>