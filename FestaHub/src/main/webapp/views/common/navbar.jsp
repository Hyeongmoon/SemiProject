<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
    import="com.fh.user.model.vo.User, java.text.SimpleDateFormat, java.sql.Timestamp" %>
<%
	// session 으로 부터 응답데이터인 로그인한 회원의 정보 (loginUser)
	User loginUser = (User)session.getAttribute("loginUser");

	// request.getContextPath() 를 통해 context path 값 알아내기
	String contextPath = request.getContextPath();
	
	// System.out.println(contextPath);
	
	// session 으로부터 alertMsg 값을 뽑아내기
	String alertMsg = (String)session.getAttribute("alertMsg");
	
	Cookie[] cookies = request.getCookies();
	
	String saveId = "";
	
	if(cookies != null) {
		
		for(int i = 0; i < cookies.length; i++) {
			
			if(cookies[i].getName().equals("saveId")) {
				saveId = cookies[i].getValue();
				break;
			}
		}
	}
	
	SimpleDateFormat ymdhm = new SimpleDateFormat("yyyy.MM.dd. HH:mm");
	
	SimpleDateFormat ymd = new SimpleDateFormat("yyyy.MM.dd."); 
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Welcome to Festa Hub</title>

    <!-- jQuery 3.7.1 -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

    <!-- Popper JS -->
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>

    <!-- Bootstrap 4.6.2 JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>

    <!-- Bootstrap 4.6.2 CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">

    <style>

        @import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&family=Oregano&display=swap');

        body {
            font-family: "Noto Sans KR", sans-serif !important;
            background: white;
            color: #2A253F;
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: column;
            min-height: 100vh;
            z-index: 800;
        }
		
        .container-fluid, #container-fluid {
            max-width: 1200px !important;
            margin: auto;
        }
        
        .contents-line {
            max-width: 1000px;
            margin: auto;
        }
        
                /* 좌측 상단 게시판 제목 */
        h1 {
            font-size: 2.5rem !important;
            font-weight: bold !important;
            color: #2A253F;
            margin-bottom: 20px;
        }
        
        .festival-content {
		    background-color: #f0f0f5; /* 배경색 */
		    padding: 20px;
		    border-radius: 10px;
		    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1); /* 카드 그림자 */
		    margin-bottom: 20px;
		    color: #2A253F;
		}
		
		.festival-title {
		    font-size: 1.8rem !important;
		    font-weight: bold !important;
		    color: #493375 !important;
		    margin-bottom: 15px !important;
		}

        .navbar {
            background-color: #2A253F;
        }
        
        .navbar-brand {
            font-family: 'Oregano', cursive;
            font-size: 1.8rem !important;
            font-weight: bold;
            font-style: italic;
            color: #2A253F !important;
            letter-spacing: 2px;
            margin: 0;
            text-shadow: 
            -1.5px -1.5px 0 #E0DEF3, /* 왼쪽 위 외곽선 */
            1.5px  1.5px 0 #9e8bc5, /* 오른쪽 아래 외곽선 */
            1.5px -1.5px 0 #9e8bc5, /* 오른쪽 위 외곽선 */
            -1.5px  1.5px 0 #9e8bc5, /* 왼쪽 아래 외곽선 */
            1.5px  1.5px 3px rgb(255, 255, 255); /* 그림자 효과 */
            position: relative;
            line-height: 0 !important; /* 텍스트 줄 간격을 줄여서 높이 감소 */
        }
        
        .smallupper-text {
            font-size: 0.32em;
            position: absolute;
            top: -14px; /* 두 텍스트를 더 가까이 붙이기 위해 간격을 조정 */
            left: 0;
            width: 100%;
            text-align: center;
            color: #bcb5f4;
            text-transform: uppercase;
            font-weight: lighter;
            text-shadow: none;
            font-style: normal;
        }
        
        .small-text {
        	font-size: 0.7em;
        }

        .navbar-nav .nav-link {
            color: #E0DEF3 !important;
            font-weight: bold;
        }

        .navbar-nav .nav-link:hover {
            color: #74B99A !important;
        }
        #loginBtn {
            color: #2a253d;
            background-color: #e0def3;
            font-weight: bold;
        }

        #loginBtn:hover {
            background-color: #74B99A;
            border-color: #e0def3;
            color: #e0def3;
        }
        
        /* 버튼 스타일 수정 */
        .btn-primary {
            background-color: #2A253F !important;
            border: none !important;
            font-weight: bold !important;
        }

        .btn-primary:hover {
            background-color: #493375 !important;
        }

        .btn-secondary {
            background-color: #8b8b8b !important;
            border: none !important;
            font-weight: bold !important;
        }

        .btn-secondary:hover {
            background-color: #6b6b6b !important;
        }

        .btn-danger {
            background-color: red !important;
            border: none !important;
            font-weight: bold !important;
        }

        .btn-danger:hover {
            background-color: darkred !important;
        }

        /* 위로가기 버튼 수정 */
        #backToTop {
            position: fixed;
            bottom: 20px;
            right: 90px !important;
            background-color: #2A253F;
            color: #e0def3;
            border: none;
            padding: 15px;
            width: 50px;  /* 버튼 너비 */
            height: 50px; /* 버튼 높이 */
            border-radius: 50%; /* 원형 버튼 */
            cursor: pointer;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3); /* 그림자 효과 */
            z-index: 1000;
            display: flex;
            justify-content: center;
            align-items: center;
            font-size: 24px; /* 화살표 크기 */
		    opacity: 0; /* 초기 투명도 0으로 설정 */
		    transition: opacity 0.3s ease; /* 부드러운 전환 */
        }

        #backToTop:hover {
            background-color: #493375;
        }

        @media (min-width: 1200px) {
            #backToTop {
                right: calc((100% - 1200px) / 2 + 20px);
            }
        }
        
        #loginSlideMenu {
            position: fixed;
            right: -310px;
            top: 0;
            width: 300px;
            height: 100%;
            background: #2A253F;
            color: #e0def3;
            padding: 20px;
            box-shadow: -3px 0 5px rgba(0, 0, 0, 0.5);
            transition: right 0.3s ease;
            z-index: 950;
        }

        /* 마이페이지 메뉴가 열릴 때 배경 클릭을 감지하는 오버레이 */
        #overlay {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.5);
            display: none;  /* 기본은 숨김 */
            z-index: 900;  /* 메뉴 바로 뒤에 위치 */
        }
        
        .modal-backdrop {
		    z-index: 1000 !important;
		}
        
        .myPage a{
			text-decoration : none;
			color : #74B99A !important;
		}
		
		.toggle-content {
		    display: none;
		    padding-left: 15px;
		    margin-top: 10px;
		}
		
		.toggle-item {
		    cursor: pointer;
		}

        #loginSlideMenu > h4 {
            color: #bcb5f4;
        }

        #loginSlideMenu button[type=submit] {
            color: black;
            background-color: #bcb5f4 !important;
            font-weight: bold;
            border: none;
        }

        .btn-block, .btn-block:hover {
            background-color: #8b8b8b !important;
            border: none;
        }
        
        .text-light:hover {
        	text-decoration: none;
        	color: #74B99A !important;
        }
        
        .footer-terms {
            background-color: #1B2055;
            padding: 20px;
            color: #D1D2DD;
            text-align: left;
            font-size: 0.9rem;
            width: 100%;
        }

        .footer-terms a {
            color: #D1D2DD;
            text-decoration: none;
            margin-right: 10px;
        }

        .footer-info {
            background-color: #2C348E;
            padding: 20px;
            color: #898DBF;
            text-align: left;
            font-size: 0.9rem;
            width: 100%;
        }
        
        /* 페이지 네비게이션 스타일 */
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

	</style>
</head>
<body>
	<script>
	
		$(function() {
			
			let saveId = "<%= saveId %>"; // "admin" / ""
			
			if(saveId != "") { // 아이디를 저장하고 싶다면
				$("#userId").val(saveId);
				$("#saveId").attr("checked", true);
			}
		});
		// 1회성 alert 문구 띄우기
		let msg = "<%= alertMsg %>"; 
		// "성공적으로 로그아웃이 되었습니다." / "null"
		
		if(msg != "null") {
			// 띄워줄 alert 문구가 있을 경우
			
			alert(msg);
			
			<%
				session.removeAttribute("alertMsg");
			%>
		}
	
	</script>
	

    <!-- Navigation bar -->
    <nav class="navbar navbar-expand-lg navbar-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="<%= contextPath %>">
                <span class="smallupper-text">CONCERT & FESTIVAL</span>
                Festa Hub
            </a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="<%= contextPath %>/list.no?currentPage=1">공지사항</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<%= contextPath %>/list.fe?currentPage=1">공연정보</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<%= contextPath %>/rvlist.fh?currentPage=1">공연후기</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<%= contextPath %>/list.ac?currentPage=1">동행구하기</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<%= contextPath %>/list.free">자유게시판</a>
                    </li>
                    &nbsp;&nbsp;
					<% if(loginUser == null) { %>
	
	        			<!-- case1. 로그인 전에 보여질 화면 -->
	                    <li class="nav-item">
	                        <button class="btn" id="loginBtn">&nbsp;로그인&nbsp;</button>
	                    </li>
                    <% } else { %>
                    	<!-- case2. 로그인 후에 보여질 화면 -->
	                    <li class="nav-item">
	                        <button class="btn" id="loginBtn"><%= loginUser.getUserNickname() %>님</button>
	                    </li>
                    <% } %>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Back to Top Button -->
    <button id="backToTop">&#8679;</button>
    
            
    
    <!-- Side bar -->
    <aside id="loginSlideMenu">
    	<% if(loginUser == null) { %>
	
	        <!-- case1. 로그인 전에 보여질 화면 -->
	        <!-- 
	        	로그인 요청 시
	        	http://localhost:8888/festhub/login.me
	        -->
	        <h4>로그인</h4>
	        <form id="login-form" 
	        	  action="<%= contextPath %>/login.fh" method="post">
	        	<input type="hidden" name="redirectURL" id="redirectURL">
	            <div class="form-group">
	                <label for="userId">아이디</label>
	                <input type="text" class="form-control" id="userId"
	                	   name="userId" placeholder="아이디" required>
	            </div>
	            <div class="form-group">
	                <label for="userPwd">비밀번호</label>
	                <input type="password" class="form-control" id="userPwd"
	                	   placeholder="비밀번호" name="userPwd" required>
	            </div>
                <div>
                    <input type="checkbox" id="saveId"
                			   name="saveId" value="y">
                    <label for="saveId">아이디 저장</label>
                </div>
	            <button type="submit" class="btn btn-primary btn-block mt-3">로그인</button>
	            <div class="mt-3">
	                <a href="#" class="text-light" onclick="$('#findInfoModal').modal('show'); return false;">아이디 / 비밀번호 찾기</a>
	            </div>
	            <div class="mt-3">
	                <a href="<%= contextPath %>/tos.fh" class="text-light">회원가입</a>
	            </div>
	        </form>
	        <button id="closeSlideMenu" class="btn btn-secondary btn-block mt-3">닫기</button>
		<% } else { %>
		
			<!-- case2. 로그인 후에 보여질 화면(마이페이지) -->
			<!--Start of Tawk.to Script-->
			<script type="text/javascript">
			var Tawk_API=Tawk_API||{}, Tawk_LoadStart=new Date();
			(function(){
			var s1=document.createElement("script"),s0=document.getElementsByTagName("script")[0];
			s1.async=true;
			s1.src='https://embed.tawk.to/6724699c2480f5b4f59728e5/1ibj3p3lr';
			s1.charset='UTF-8';
			s1.setAttribute('crossorigin','*');
			s0.parentNode.insertBefore(s1,s0);
			})();
			</script>
			<!--End of Tawk.to Script-->
			<h4>마이페이지</h4>
			<div class="myPage">
				<a href="<%=contextPath%>/mypage.fh">내정보 수정</a>
			</div>
            <div class="myPage">
            	<a href="<%=contextPath%>/delete.fh">회원탈퇴</a>
            </div>
            <br>
			<h4>게시물 & 댓글</h4>
			<div class="toggle-item" data-target="#dibPosts">내가 찜한 게시글</div>
			<div id="dibPosts" class="toggle-content">
			    <div class="myPage"><a href="<%=contextPath %>/dibfree.fh">자유게시판</a></div>
			    <div class="myPage"><a href="<%=contextPath %>/dibfes.fh">공연정보</a></div>
			    <div class="myPage"><a href="<%=contextPath %>/dibac.fh">동행구하기</a></div>
			</div>
			
			<div class="toggle-item" data-target="#writtenPosts">내가 쓴 게시글</div>
			<div id="writtenPosts" class="toggle-content">
			    <div class="myPage"><a href="<%=contextPath %>/wrfree.fh">자유게시판</a></div>
			    <div class="myPage"><a href="<%=contextPath %>/wrrv.fh">공연후기</a></div>
			    <div class="myPage"><a href="<%=contextPath %>/wrac.fh">동행구하기</a></div>
			</div>
			
			<div class="toggle-item" data-target="#writtenComments">내가 쓴 댓글</div>
			<div id="writtenComments" class="toggle-content">
			    <div class="myPage"><a href="<%=contextPath %>/rpfree.fh">자유게시판</a></div>
			    <div class="myPage"><a href="<%=contextPath %>/rpfes.fh">공연정보</a></div>
			</div>
            <br>
            <h4>쪽지함</h4>
            <div class="myPage">
            	<a href="<%= contextPath %>/sendMsgForm.me">쪽지 보내기</a>
            </div>
            <div class="myPage">
            	<a href="<%= contextPath %>/list.rme?currentPage=1">받은 쪽지</a>
            </div>
            <div class="myPage">
            	<a href="<%= contextPath %>/list.sme?currentPage=1">보낸 쪽지</a>
            </div>
            <div>
            	<button type="button" class="btn btn-secondary btn-block mt-3"
            	onclick="openPopup('https://tawk.to/chat/6724699c2480f5b4f59728e5/1ibj3p3lr')">
				    문의하기
				</button>
            </div>
            <div class="myPage" id="closeSlideMenu">
            	<button type="button" class="btn btn-secondary btn-block mt-3">
                닫기
            	</button>
            </div>
            <!-- 로그아웃 버튼 추가 -->
            <div class="myPageLogout">
            	<a href="<%= contextPath %>/logout.fh" 
               class="btn btn-secondary btn-block mt-3">
                로그아웃
            </a>
            </div>
            <!-- 관리자 페이지 버튼 추가 -->
            <% if(loginUser.getUserId().equals("admin")) { %>
            <div class="myPage">
	          	<a href="<%= contextPath %>/views/adminTool/admin.fh" 
	               class="btn btn-secondary btn-block mt-3" target="_blank">
	                관리자 페이지
	            </a>
            </div>
            <% } %>
			
		<% } %>
		
    </aside>
    <!-- Overlay -->
    <div id="overlay"></div>
    
    <!-- 아이디 / 비밀번호 찾기 모달 -->
	<div class="modal fade" id="findInfoModal" tabindex="-1" role="dialog" aria-labelledby="findInfoLabel" aria-hidden="true">
	    <div class="modal-dialog" role="document">
	        <div class="modal-content">
	            <div class="modal-header">
	                <h5 class="modal-title" id="findInfoLabel">아이디 / 비밀번호 찾기</h5>
	                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	                    <span aria-hidden="true">&times;</span>
	                </button>
	            </div>
	            <div class="modal-body">
	                <!-- 아이디 찾기 폼 -->
	                <form id="findIdForm">
	                    <h6>아이디 찾기</h6>
	                    <div class="form-group">
	                        <label for="nickname">닉네임</label>
	                        <input type="text" class="form-control" id="nickname" name="nickname" required>
	                    </div>
	                    <div class="form-group">
	                        <label for="emailForId">이메일 주소</label>
	                        <input type="email" class="form-control" id="emailForId" name="email" required>
	                    </div>
	                    <button type="button" class="btn btn-primary" onclick="findId()">아이디 찾기</button>
	                </form>
	                
	                <hr>
	
	                <!-- 비밀번호 찾기 폼 -->
	                <form id="findPwdForm">
	                    <h6>비밀번호 찾기</h6>
	                    <div class="form-group">
	                        <label for="userId">아이디</label>
	                        <input type="text" class="form-control" id="userId" name="userId" required>
	                    </div>
	                    <div class="form-group">
	                        <label for="emailForPwd">이메일 주소</label>
	                        <input type="email" class="form-control" id="emailForPwd" name="email" required>
	                    </div>
	                    <button type="button" class="btn btn-primary" onclick="findPassword()">비밀번호 힌트 확인</button>
	                </form>
	            </div>
	        </div>
	    </div>
	</div>
	
    <script>
        // 로그인 버튼 클릭 시 슬라이드 메뉴 표시
        $("#loginBtn").click(function () {
            $("#loginSlideMenu").css("right", "0");
            $("#overlay").fadeIn();  // 오버레이 표시
        });

        // 슬라이드 메뉴 닫기 버튼
        $("#closeSlideMenu").click(function () {
            $("#loginSlideMenu").css("right", "-310px");
            $("#overlay").fadeOut();  // 오버레이 숨기기
        });

        // 오버레이 클릭 시 메뉴와 오버레이 숨기기
        $("#overlay").click(function () {
            $("#loginSlideMenu").css("right", "-310px");  // 메뉴 닫기
            $("#overlay").fadeOut();  // 오버레이 숨기기
        });

        // 메뉴 외부를 클릭했을 때만 닫히도록 설정 (메뉴 내부 클릭 시 이벤트 전파 막기)
        $("#loginSlideMenu").click(function (e) {
            e.stopPropagation();  // 이벤트가 오버레이로 전달되지 않도록 차단
        });

        
     	// 현재 브라우저의 URL을 가져와 서블릿 경로와 쿼리 스트링을 저장
	    $(document).ready(function() {
	        $('#redirectURL').val(window.location.pathname + window.location.search);
	        
	        $(".toggle-item").click(function () {
	            let target = $(this).data("target");
	            
	            // 모든 toggle-content 요소를 닫고 현재 클릭한 항목의 타겟만 토글
	            $(".toggle-content").not(target).slideUp(200);
	            $(target).slideToggle(200);
	        });
	        
	        // 초기 로딩 시 스크롤 위치에 따라 버튼 표시 여부 결정
	        if ($(window).scrollTop() > 250) {
	            $('#backToTop').css('opacity', '0.7'); // 초기 로딩 시 버튼이 노출될 높이에 있을 경우
	        }

	        // 스크롤 이벤트로 버튼 표시/숨김
	        $(window).scroll(function () {
	            if ($(this).scrollTop() > 250) {
	                $('#backToTop').css('opacity', '0.7');
	            } else {
	                $('#backToTop').css('opacity', '0');
	            }
	        });

	        // 위로가기 버튼 클릭 시 맨 위로 스크롤
	        $('#backToTop').click(function () {
	            $('html, body').animate({ scrollTop: 0 }, 300);
	            return false;
	        });
	    });
     	
	 	// 아이디 찾기
	    function findId() {
	        const nickname = $('#nickname').val();
	        const email = $('#emailForId').val();

	        $.ajax({
	            url: '<%= contextPath %>/findId.fh',
	            type: 'POST',
	            data: { nickname, email },
	            success: function(response) {
	                alert(response ? "아이디: " + response : "아이디를 찾을 수 없습니다.");
	            }
	        });
	    }

	    // 비밀번호 힌트 찾기
	    function findPassword() {
	        const userId = $('#userId').val();
	        const email = $('#emailForPwd').val();

	        $.ajax({
	            url: '<%= contextPath %>/findPwd.fh',
	            type: 'POST',
	            data: { userId, email },
	            success: function(response) {
	                alert(response ? "비밀번호 힌트: " + response : "비밀번호를 찾을 수 없습니다.");
	            }
	        });
	    }
	    
	    // 팝업으로 열기
	    function openPopup(url) {
	        window.open(url, 'popupWindow', 'width=300,height=600,resizable=yes,scrollbars=yes');
	    }
     	
     	
    </script>

</body>
</html>



