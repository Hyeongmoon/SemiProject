<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, com.fh.festival.model.vo.*, com.fh.common.model.vo.PageInfo" %>
<%
	// 게시글 정보 객체
	Festival f = (Festival)request.getAttribute("f");

	// 이전글 정보 객체
	Festival prevFes = (Festival)request.getAttribute("prevFes");
	
	// 다음글 정보 객체
	Festival nextFes = (Festival)request.getAttribute("nextFes");

	// 첨부파일 정보 객체
	ArrayList<FestivalImage> fiList = (ArrayList<FestivalImage>)request.getAttribute("fiList");
	
	int currentPage = (session.getAttribute("currentPage") != null) ? (int)session.getAttribute("currentPage") : 1;
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Festa Hub - 공연 정보 상세페이지</title>
	<style>
        .board-name {
            font-size: 1.2rem;
            color: #beb7dd;
            font-weight: bold;
        }

		.festival-info {
		    font-size: 0.95rem;
		    color: #555;
		    margin-bottom: 15px;
		}

		.card img {
		    width: 100%; /* 너비를 일정하게 */
		    max-width: 1000px; /* 최대 너비 설정 */
		    height: auto; /* 높이를 자동 조정하여 비율 유지 */
		    object-fit: contain; /* 컨테이너에 맞춤 */
		    /* border-radius: 10px; */
		    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* 그림자 효과 */
		    /* margin-bottom: 20px; */
		    display: block;
		    margin-left: auto;
		    margin-right: auto; /* 이미지 가운데 정렬 */
		}
        
        .card-text {
		    font-family: 'Noto Sans KR', sans-serif; /* 원하는 폰트 */
		    font-size: 1rem; /* 글씨 크기 조정 */
		    line-height: 1.5; /* 줄 간격 조정 */
		    color: #2A253F; /* 글씨 색상 */
		    white-space: pre-wrap; /* 줄바꿈 유지 */
		}
		
		textarea {
		    resize: none !important;
		}

        /* 댓글 쓰기 영역 */
        .comment-form {
            margin-top: 20px;
            margin-bottom: 20px;
        }

        .comment-form .form-control {
            width: 100%;
            margin-bottom: 10px;
        }

        .comment-input {
            margin-bottom: 20px;
        }

        .comment-input .form-control {
            width: auto;
            flex-grow: 1;
        }

        .comment-input .btn {
            white-space: nowrap;
            font-weight: bold;
        }

        .comment-list {
            margin-top: 20px;
        }

        .comment {
            padding: 10px;
            border-bottom: 1px solid #eee;
            display: flex;
            justify-content: space-between;
            align-items: flex-start;
        }
        
        .comment pre {
		    font-family: "Noto Sans KR", sans-serif;
		    font-size: 1rem; /* 필요에 따라 크기 조정 */
		    white-space: pre-wrap; /* 줄 바꿈 유지 */
	        flex-basis: 70%; /* 댓글 내용은 70%의 가변 너비 */
    		overflow-wrap: break-word; /* 글이 너무 길면 줄 바꿈 */
		}

        .comment-buttons {
            flex-basis: 15%; /* 버튼 그룹을 15%로 고정 */
            display: flex;
            gap: 10px;
        }
        
        .comment-buttons button {
		    white-space: nowrap; /* 버튼 텍스트 줄바꿈 방지 */
		}

        .comment .nickname {
        	flex-basis: 15%; /* 닉네임은 15%의 고정 너비 */
            font-weight: bold;
        }
        
        .comment-input .nickname {
        	font-weight: bold;
        }

        .comment .text-muted {
            color: #777;
        }

        /* 버튼 영역 */
        .button-group {
            text-align: right;
            margin-top: 20px;
        }

        .button-group button {
            margin-left: 10px;
        }


        /* 반응형 설정 */
        @media (max-width: 768px) {
            .festival-info {
                font-size: 0.8rem;
            }

            .festival-title {
                font-size: 1.5rem;
            }
        }

        @media (max-width: 576px) {
            .container-fluid {
                padding: 10px;
            }

            .festival-title {
                font-size: 1.3rem;
            }
        }
	</style>
</head>
<body>
	<!-- 상단바 -->
	<%@ include file="../common/navbar.jsp" %>
	
	<!-- 메인컨텐츠 내용 -->
    <div class="container-fluid my-4">
    	<div class="contents-line">
	        <div class="festival-content">
	        <br>
	        <!-- 공연 정보 상단 -->
	        <h4 class="board-name">공연 정보</h4>
	        <h2 class="festival-title"><%= f.getFesTitle() %></h2>
	        <div class="festival-info">
	            <p style="font-size: large;">공연 날짜: <%= ymdhm.format(f.getFesDay()) %></p>
	            <p style="font-size: large;">공연 장소: <%= f.getFesAddress() %></p>
	            <p><%= f.getFesWriter() %> | <%= ymdhm.format(f.getFesDate()) %> | 조회 <%= f.getFesCount() %></p>
	            <p>
	            	<span id="likeBtn" onclick="toggleLike()"
          				  style="cursor: pointer; font-size: 1.5em;">🤍</span>
          			<span id="likeCount"></span> | 
          			<span id="commentIcon" 
          				  style="cursor: pointer; font-size: 1.5em;">💬</span> 
	            	<span id="commentCount">0</span>
	            </p>
	        </div>
	
	        <!-- 내용란 -->
            <!-- 게시글 내용 영역 -->
            <div class="card">
            <% if(fiList.isEmpty()) { %>
            	<pre class="card-text"><%= f.getFesContent() %></pre>
            <% } else { %>
            	<% for(FestivalImage fi : fiList) { %>
            		<% if(fi.getFesImgThumb().equals("Y")) { %>
		                <img src="<%= contextPath %><%= fi.getFesImgPath() + fi.getFesImgRename() %>" alt="<%= fi.getFesImgName() %>">
		                <pre class="card-text"><%= f.getFesContent() %></pre>
		            <% } %>
		        <% } %>
              	<% for(FestivalImage fi : fiList) { %>
            		<% if(fi.getFesImgThumb().equals("N")) { %>
            			<!-- 추가 이미지 영역 -->
	                    <img src="<%= contextPath %><%= fi.getFesImgPath() + fi.getFesImgRename() %>" alt="<%= fi.getFesImgName() %>">
	                <% } %>
	            <% } %>
	        <% } %>
            </div>
            <br>

            <!-- 지도 API 적용 영역 -->
            <a href="https://map.kakao.com/link/search/<%= f.getFesAddress() %>" style="color: inherit; font-size: 15px; text-decoration: none;" target="_blank">공연 장소(지도로 연결): <%= f.getFesAddress() %></a>
            <div id="map" style="width: 100%; height: 400px; margin-top: 20px; background-color: #f0f0f0;">
                <!-- 지도 API 연동 시 여기에 지도가 표시됩니다 -->
            </div>
        </div>

        <!-- 댓글 쓰기 영역 -->
        <div id="commentSection" class="comment-form">
            <h5>댓글 쓰기</h5>
            <!-- 댓글 입력창 영역 -->
            <% if(loginUser != null) { %>
	            <div class="comment-input d-flex align-items-center">
	                <p class="nickname mr-3"><%= loginUser.getUserNickname() %></p>
	                <textarea class="form-control mr-2"
	                		  id="fesCommContent"
	                		  rows="2" 
	                		  placeholder="댓글을 입력하세요"></textarea>
	                <button class="btn btn-primary"
	                		onclick="insertComm();">등록</button>
	            </div>
	        <% } else { %>
	            <div class="comment-input d-flex align-items-center">
	                <p class="nickname mr-3">닉네임</p>
	                <textarea class="form-control mr-2"
	                		  rows="2" onclick="commLogin();" 
	                		  placeholder="로그인 후 이용 가능합니다."
	                		  readonly></textarea>
	                <button class="btn btn-primary"
	                		disabled>등록</button>
	            </div>
	        <% } %>
        </div>

        <!-- 댓글 리스트 영역 -->
        <div class="comment-list">
        </div>

        <!-- 이전/다음 게시글 영역 -->
		<!-- 다음 글 -->
		<div class="row">
		    <div class="col-12">
		        <% if (nextFes != null) { %>
		            <a href="<%= contextPath %>/detail.fe?fesNo=<%= nextFes.getFesNo() %>" 
		               style="display: block; padding: 10px 0; color: #333; text-decoration: none; border-bottom: 1px solid #e0e0e0;">
		                다음 글 : <%= (nextFes.getFesTitle().length() <= 12) ? nextFes.getFesTitle() : nextFes.getFesTitle().substring(0, 12) + "..." %> | 
		                <%= ymdhm.format(nextFes.getFesDay()) %> | <%= (nextFes.getFesAddress().length() <= 12) ? nextFes.getFesAddress() : nextFes.getFesAddress().substring(0, 12) + "..." %>
		            </a>
		        <% } else { %>
		            <span style="display: block; padding: 10px 0; color: #aaa; border-bottom: 1px solid #e0e0e0;">
		                다음 글이 없습니다.
		            </span>
		        <% } %>
		    </div>
		</div>
		
		<!-- 이전 글 -->
		<div class="row mb-2">
		    <div class="col-12">
		        <% if (prevFes != null) { %>
		            <a href="<%= contextPath %>/detail.fe?fesNo=<%= prevFes.getFesNo() %>" 
		               style="display: block; padding: 10px 0; color: #333; text-decoration: none; border-bottom: 1px solid #e0e0e0;">
		                이전 글 : <%= (prevFes.getFesTitle().length() <= 12) ? prevFes.getFesTitle() : prevFes.getFesTitle().substring(0, 12) + "..." %> | 
		                <%= ymdhm.format(prevFes.getFesDay()) %> | <%= (prevFes.getFesAddress().length() <= 12) ? prevFes.getFesAddress() : prevFes.getFesAddress().substring(0, 12) + "..." %>
		            </a>
		        <% } else { %>
		            <span style="display: block; padding: 10px 0; color: #aaa; border-bottom: 1px solid #e0e0e0;">
		                이전 글이 없습니다.
		            </span>
		        <% } %>
		    </div>
		</div>

		<!-- 수정/삭제/목록보기/뒤로가기 버튼 -->
		<div class="button-group d-flex justify-content-between">
		    <!-- 뒤로가기 버튼 -->
		    <a href="javascript:history.back()" class="btn btn-secondary">뒤로가기</a>
		    
		    <!-- 오른쪽 버튼들: 수정, 삭제, 목록보기 -->
		    <div>
		        <% if (loginUser != null && loginUser.getUserId().equals(f.getWriterId())) { %>
		            <a href="<%= contextPath %>/updateForm.fe?fesNo=<%= f.getFesNo() %>" class="btn btn-secondary">수정</a>
		            &nbsp;
		            <a href="<%= contextPath %>/delete.fe?fesNo=<%= f.getFesNo() %>" class="btn btn-danger">삭제</a>
		            &nbsp;
		        <% } %>
		        <a href="<%= contextPath %>/list.fe?currentPage=<%= currentPage %>" class="btn btn-primary">목록보기</a>
		    </div>
		</div>

        <br>
    </div>
    </div>
	
	<!-- 지도API -->
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=214a50b68214979974c9cc8888357475&libraries=services,clusterer,drawing"></script>
	<script>
		// 마커를 클릭하면 장소명을 표출할 인포윈도우 입니다
		var infowindow = new kakao.maps.InfoWindow({zIndex:1});
	
		var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
		    mapOption = {
		        center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
		        level: 3 // 지도의 확대 레벨
		    };  
	
		// 지도를 생성합니다    
		var map = new kakao.maps.Map(mapContainer, mapOption); 
	
		// 장소 검색 객체를 생성합니다
		var ps = new kakao.maps.services.Places(); 
	
		// 키워드로 장소를 검색합니다
		ps.keywordSearch('<%= f.getFesAddress() %>', placesSearchCB);
		
        // 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
        var zoomControl = new kakao.maps.ZoomControl();
        map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
	
		// 키워드 검색 완료 시 호출되는 콜백함수 입니다
		function placesSearchCB (data, status, pagination) {
		    if (status === kakao.maps.services.Status.OK) {
	
		        // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
		        // LatLngBounds 객체에 좌표를 추가합니다
		        var bounds = new kakao.maps.LatLngBounds();
	
		       // for (var i=0; i<data.length; i++) {
		       //     displayMarker(data[i]);    
		       //     bounds.extend(new kakao.maps.LatLng(data[i].y, data[i].x));
		       // }       
		       
	            displayMarker(data[0]);    
	            bounds.extend(new kakao.maps.LatLng(data[0].y, data[0].x));
	
		        // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
		        map.setBounds(bounds);
		    } 
		}
	
		// 지도에 마커를 표시하는 함수입니다
		function displayMarker(place) {
		    
		    // 마커를 생성하고 지도에 표시합니다
		    var marker = new kakao.maps.Marker({
		        map: map,
		        position: new kakao.maps.LatLng(place.y, place.x) 
		    });
		    
	        infowindow.setContent('<div style="padding:5px;font-size:12px; cursor: pointer; color: inherit; text-decoration: none;" onclick="window.open(\'' + place.place_url + '\', \'_blank\')">' + place.place_name + '</div>');
	        infowindow.open(map, marker);
		}
		
		
	</script>
	
	<!-- 추가 스크립트 -->
    <script>
        // 댓글 영역
        $(function() {
        	
            // 로그인 상태에 따른 유저 ID 변수 설정
            const loginUserId = "<%= (loginUser != null) ? loginUser.getUserId() : "" %>";
			
			// 댓글 목록 조회 요청 ajax로 다녀오기
			selectCommList();
			
			// 실시간 댓글 조회 효과
			// setInterval(selectCommList, 1000); > 수정 기능과 충돌로 해당 기능 홀딩
			
			// 좋아요 관련 기능 추가
			// 게시판번호, 유저번호 변수에 담기
	        const fesNo = <%= f.getFesNo() %>;
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
	                url: "<%= contextPath %>/toggleLike.fe",
	                type: "post",
	                data: { fesNo: fesNo, userNo: userNo },
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
	                url: "<%= contextPath %>/likeStatus.fe",
	                type: "get",
	                data: { fesNo: fesNo, userNo: userNo },
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
	        
		    // 댓글 아이콘 클릭 시 댓글 리스트로 스크롤 이동
		    $("#commentIcon").click(function() {
		        $('html, body').animate({
		            scrollTop: $("#commentSection").offset().top
		        }, 300); // 스크롤 속도 설정 (300ms)
		    });
			
		});
        
		function insertComm() {
			
			// 사용자가 입력한 댓글 내용 변수에 담기
			let fesCommContent = $("#fesCommContent").val();
			
			$.ajax({
				url : "<%= contextPath %>/cinsert.fe",
				type : "post",
				data : {
					fesNo : <%= f.getFesNo() %>,
					fesCommContent : fesCommContent					
				},
				success : function(result) {
					
					if(result > 0) { // 성공
						
						// 댓글 작성 성공 => 갱신된 댓글 리스트를 재조회
						selectCommList();
						
						// 댓글 작성용 textarea 요소 초기화
						$("#fesCommContent").val("");
						
					} else { // 실패
						
						alert("댓글 작성에 실패했습니다.")
					
						$("#fesCommContent").val("");
						
					}
					
				},
				error : function() {
					console.log("댓글 작성용 ajax 통신 실패");
				}
				
			});
			
		}
		
		function selectCommList() {
			
			$.ajax({
				url : "<%= contextPath %>/clist.fe",
				type : "get",
				data : {fesNo : <%= f.getFesNo() %>},
				success : function(result) {
					//console.log(result);
					// [{}, {}, {}, ...]
					
					let resultStr = "";
					const loginUserId = "<%= (loginUser != null) ? loginUser.getUserId() : "" %>";
					
					// 댓글 수 업데이트
			        $("#commentCount").text(result.length);
					
					// for(let i = 0; i < result.length; i++)
		            for (let i in result) {
		                resultStr += "<div id='comment_"+ result[i].fesCommNo + "' class='comment'>"
		                           +     "<p class='nickname'>" + result[i].fesCommWriter + "</p>"
		                           +     "<pre>" + result[i].fesCommContent + "</pre>"
		                           +     "<p class='text-muted'>" + result[i].fesCommDate + "</p>";
		                
		                // 댓글 작성자와 로그인 사용자가 동일한 경우에만 수정/삭제 버튼 표시
		                if (result[i].CommWriterId == loginUserId) {
		                    resultStr += "<div class='comment-buttons'>"
		                               +     "<button class='btn btn-sm btn-outline-secondary' onclick='updateComm(" + result[i].fesCommNo + ");'>수정</button>"
		                               +     "<button class='btn btn-sm btn-outline-danger' onclick='deleteComm(" + result[i].fesCommNo + ");'>삭제</button>"
		                               + "</div>";
		                }
		                
		                resultStr += "</div>";  // 댓글 블록 종료
		            }

		            $(".comment-list").html(resultStr);
		        },
				error : function() {
					console.log("댓글리스트 조회용 ajax 통신 실패");
				}
				
			});
		}
		
		// 댓글 원본 내용을 저장할 전역 변수
		let originalCommentContent = {};

		// 댓글 수정
		function updateComm(fesCommNo) {
		    let commentElement = $("#comment_" + fesCommNo);
		    let contentElement = commentElement.find("pre");

		    // 원래 댓글 내용을 전역 변수에 저장
		    let originalContent = contentElement.text();
		    originalCommentContent[fesCommNo] = originalContent;

		    // textarea로 변환하여 수정 모드 활성화
		    contentElement.replaceWith("<textarea id='editCommContent_" + fesCommNo + "' class='form-control'>" + originalContent + "</textarea>");

		    // 버튼 변경
		    commentElement.find(".comment-buttons").html(
		        "<button class='btn btn-sm btn-success' onclick='saveComm(" + fesCommNo + ")'>저장</button>" +
		        "<button class='btn btn-sm btn-secondary' onclick='cancelEdit(" + fesCommNo + ")'>취소</button>"
		    );
		}

		// 댓글 수정 취소
		function cancelEdit(fesCommNo) {
		    // 전역 변수에 저장된 원본 내용 복원
		    const originalContent = originalCommentContent[fesCommNo];

		    // textarea를 pre 태그로 복원
		    $("#editCommContent_" + fesCommNo).replaceWith("<pre>" + originalContent + "</pre>");

		    // 원래 버튼들로 변경
		    $("#comment_" + fesCommNo + " .comment-buttons").html(
		        "<button class='btn btn-sm btn-outline-secondary' onclick='updateComm(" + fesCommNo + ")'>수정</button>" +
		        "<button class='btn btn-sm btn-outline-danger' onclick='deleteComm(" + fesCommNo + ")'>삭제</button>"
		    );

		    // 사용 후 전역 변수에서 제거
		    delete originalCommentContent[fesCommNo];
		}
		
		// 수정된 댓글 저장
		function saveComm(fesCommNo) {
		    let editedContent = $("#editCommContent_" + fesCommNo).val();

		    $.ajax({
		        url: "<%= contextPath %>/cupdate.fe",
		        type: "post",
		        data: {
		            fesCommNo: fesCommNo,
		            fesCommContent: editedContent
		        },
		        success: function(result) {
		            if (result > 0) {
		                selectCommList(); // 댓글 리스트 새로고침
		            } else {
		                alert("댓글 수정에 실패했습니다.");
		            }
		        },
		        error: function() {
		            console.log("댓글 수정 ajax 통신 실패");
		        }
		    });
		}

		// 댓글 삭제
		function deleteComm(fesCommNo) {
		    if (confirm("정말 삭제하시겠습니까?")) {
		        $.ajax({
		            url: "<%= contextPath %>/cdelete.fe",
		            type: "post",
		            data: { fesCommNo: fesCommNo },
		            success: function(result) {
		                if (result > 0) {
		                    selectCommList(); // 댓글 리스트 새로고침
		                } else {
		                    alert("댓글 삭제에 실패했습니다.");
		                }
		            },
		            error: function() {
		                console.log("댓글 삭제 ajax 통신 실패");
		            }
		        });
		    }
		}
		
      function commLogin() {
            $("#loginSlideMenu").css("right", "0");
            $("#overlay").fadeIn();  // 오버레이 표시
      }
    </script>
	
	<!-- 하단바 -->
	<%@ include file="../common/footer.jsp" %>

</body>
</html>