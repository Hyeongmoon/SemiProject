<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList, com.fh.freeBoard.model.vo.FreeBoardFile, com.fh.freeBoard.model.vo.FreeBoard" %>
    
    
<%
	FreeBoard f = (FreeBoard)request.getAttribute("f");
	ArrayList<FreeBoardFile> files = (ArrayList<FreeBoardFile>)request.getAttribute("files");
	
	FreeBoard prevF = (FreeBoard)request.getAttribute("prevF");
	FreeBoard nextF = (FreeBoard)request.getAttribute("nextF");
	
	
	
	int currentPage = (Integer)request.getAttribute("currentPage");


	
	
	//정렬
	String sorting = "desc";
	if((String)request.getAttribute("sorting")!=null){
		
		sorting = (String)request.getAttribute("sorting");
		/* System.out.println("실행되나 ?"); */
	}
	
	
	//검색어 
	String searchOption = "";
	if((String)request.getAttribute("searchOption")!=null){
		searchOption = (String)request.getAttribute("searchOption");
	}
	
		
	//검색타입
	String inputValue = "";
	if((String)request.getAttribute("inputValue")!=null){
		
		inputValue = (String)request.getAttribute("inputValue");
	}
	
	
	String detailPath = request.getContextPath() + "/detail.free?currentPage=" + currentPage + "&sorting=" + 
							sorting + "&searchOption=" + searchOption + "&inputValue=" + inputValue;
	
	//최신순(기본)/오래된순으로 조회하기
 %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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



    .container-fluid{ /* 전체장 크기*/
		
        max-width:1100px;
        margin:auto;
        margin-top: 50px;
        padding:20px;
        
    }
 
    

    /* =========================================== */
    /* 제목영역 */
    .detail-head {
        /* display: flex; */
        /* justify-content: space-between; */ /* 좌측과 우측 정렬 */
        /* align-items: center; */ /* 세로 방향으로 가운데 정렬 */   
    }


    .detail-head { /* 제목, 글쓴이,조회수,날짜 한줄로 정렬*/
        /* display: flex; */
        width: 100%; /* 전체 너비 사용 */
    }
    .detail-title {
        flex-grow: 1; /* 공간을 차지하도록 설정 */
    }
    .writer-area {
       /*  text-align: right; */ /* 내부 텍스트 오른쪽 정렬 */
    }

    .writer-area {
      /*   padding-top: 20px; */ /* 원하는 만큼 위쪽에 공간 추가 */
    	padding-left: 20px; 
    	color : gray;
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

    .detail-content img{
        width:60%;
    }

	#pre-area{
		font-size:25px;
		white-space: pre-wrap;
		word-wrap: break-word; /* 단어 단위로 줄바꿈 */
		font-weight: 600;
	}
	
	#img-div img{
        margin-left: 20px; /* 이미지 사이 간격 */
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
    .insert-comment
    { /*댓글 영역 너비 조절*/
        width:95%;
        margin:auto;
        margin-top : 30px;
    }
    .comment-area>table {
        table-layout: fixed; /* 테이블 레이아웃 고정 */
        width: 100%; 
    }
    .comment-area>table td:nth-child(1){
        width:15%;
    }
    .comment-area>table td:nth-child(2){
        width:50%;
    }
    .comment-area>table td:nth-child(3){
        width:20%;
    }
    .comment-area>table td:nth-child(4){
        width:15%;
    }


    /* 댓글입력부분 */
    .insert-comment{
        max-width:100%;
        border: 2px solid black;
        text-align: left;
        margin:20px;
        padding: 10px;
        border-radius: 5px;
    }
    .comment-content>textarea,
    .insert-comment>input
    {
       border: none;
       width:100%;
    }
    .comment-content{
        display: flex;
        justify-content: center; 
        height: 90px;            /* 부모 높이 설정 (필요시 맞게 변경) */
        border-bottom: 2px solid black; 
    }
    .comment-button{
        float: right;
    }
    .comment-bottom{
       
        

    }


    /* =========================================== */

    /* 이전,다음 게시글 영역 */
    .page-area{ /* 페이징바 영역 위아래 구분선*/
        border-top: 3px solid black;
        border-bottom : 3px solid black;
        
    
    }
    
    .page-area>table{
    
    	margin-top: 10; /* 필요에 따라 조정 */
		 margin-bottom: 0;  /* 필요에 따라 조정 */
    }
    .first-tr>td{ /* 페이징바 영역 가운데 구분선*/
        border-bottom: 1px solid black;
        
    }
    
   
    
    
	.hover-effect:hover {
	     cursor: pointer; 
	    
	 }
    



</style>    
</head>
<body>
    
    <%@include file="../common/navbar.jsp" %>
    
    <div class="container-fluid">
      
        <div id="board-deatil">

            <!-- 상단 제목부분 -->
            <div>
                <div>자유게시판</div>
                <div class="detail-head">
                    <div class="detail-title">
                        <h1><%=f.getFreeTitle() %></h1>
                    </div>
                    
                    <div class="writer-area">
                        <span> <%=f.getUserNickName() %> </span> &nbsp;&nbsp;
                        <span> 조회수:<%=f.getFreeCount() %> </span>&nbsp;&nbsp;
                        <span> <%=f.getFreeDate() %> </span>
                    </div>
                </div>
                
            </div>
            
            
            
            
            <!-- 중간 게시글내용부분 -->
            <div class="detail-content">
                <div>
					<pre id="pre-area"><%=f.getFreeContent() %></pre>                    

					<div id="img-div">
					
						<%if(!files.isEmpty()){ %>

							<% for(FreeBoardFile ff : files){ %>
								
			                    <img src="<%=contextPath%>/<%=ff.getFreeFilePath()+ff.getFreeFileRename()%>" alt="이미지">
			                <%}%>    
						<%}%>
                    </div>
                </div>
                
                
                
                <!-- 좋아요/댓글 부분 -->
                
                <div>
                    <span id="freeBoard-like">
                    	<span class="hover-effect" id="like-icon">&#x2764;&#xFE0F; &#x1F90D;</span>
						<span id="like-count"><%=f.getFreeLikeCount() %></span>
					</span>
                    <span id="freeBoard-comment">
                    	<span id="comment-icon"> &#x1F4AC; </span>
                    	<span id="comment-count"> <%=f.getFreeCommentCount() %> </span>
                    </span>
                </div>
            </div>
            
            
            
            
            
            
            
            
            <!-- 댓글부분 -->
            
            <div class="comment-area">
                <table class="comment-table table  ">
                   
                    
                </table>    
            </div>

            


            <!-- 댓글 입력 부분 -->


			<% if(loginUser==null){%>
			
            <div class="insert-comment">
                <input type="text" placeholder="로그인이 필요합니다." disabled>
            </div>
            
			<% }else{ %>

            <div class="insert-comment" >
                
                <div class="coment-writer"><b><%=loginUser.getUserNickname() %></b></div>
                <div class="comment-content">
                    <textarea name="freeComment" id="free-comment" placeholder="댓글을 입력하세요" oninput="updateCount()" maxlength="300" style="resize: none"></textarea>
                </div>
                <div class="comment-bottom">
                    <div class="comment-button">
                    	
                    	<button class="btn btn-sm btn-info" onClick="inserComment()">등록</button>
                    										
                    </div>
                    <div id="charCount">0/1000</div>
                </div>
                
            </div>
            
            
            <script>
            
            /* 댓글창에 글자수 알림/ 알림제한 (순수 자바구문으로 해보기)*/
            
            	function updateCount(){
            		const textarea = document.getElementById("free-comment");
            		const charCount = document.getElementById("charCount");
            		const currentLength = textarea.value.length;
            		const maxLength = textarea.getAttribute("maxlength")
            		
            		charCount.innerHTML = currentLength+"/"+maxLength;
            	}
            
            
            </script>
            
            <% } %>
            
            <!-- 다음글,이전글 부분 -->
            
            
            <div class="page-area">
                
                <table width="100%" class="table table-hover hover-effect">
                	
                	
                	<% if(prevF!=null){%>
	                    <tr onclick="location.href='<%=detailPath%>&freeNo=<%=prevF.getFreeNo()%>'">
	                    	
	                        <td>이전글</td>
	                        <td><%=prevF.getFreeNo() %></td>
	                        <td><%=prevF.getFreeTitle() %>  💬<%=prevF.getFreeCommentCount() %></td>
	                        <td><%=prevF.getUserNickName() %></td>
	                        <td><%=prevF.getFreeDate() %></td>
	                    </tr>
					<%}%>
					<%if(nextF != null){ %>
	                    <tr onclick="location.href='<%=detailPath%>&freeNo=<%=nextF.getFreeNo()%>'">
	                        <td>다음글</td>
	                        <td><%=nextF.getFreeNo() %></td>
	                        <td><%=nextF.getFreeTitle() %>  💬<%=nextF.getFreeCommentCount() %></td>
	                        <td><%=nextF.getUserNickName() %></td>
	                        <td><%=nextF.getFreeDate() %></td>
	                    </tr>
                    <%} %>
                </table>
            
            </div>
 
            
            
            
            <!-- 수정 삭제 목록 버튼 -->
            <div class="button-area">
                <div align="right">
                
                	
                	<%if(loginUser!=null && loginUser.getUserNo() == f.getUserNo()){ %>
                	
						<button onclick="updateForm()" class="btn btn-info">수정</button>
						<button onclick="remove()" class="btn btn-danger">삭제</button>
						
                	<%} %>
                
                    
                    
                    <%if(searchOption.equals("")){ %> <!-- 일반리스트로 돌아가기 --> 
                    
                    	<button onclick="location.href='<%=contextPath%>/list.free?currentPage=<%=currentPage %>&sorting=<%=sorting%>'" class="btn btn-secondary">목록</button>
                    	
                    <%}else{ %> <!-- 검색시 리스트로 돌아가기 -->
                    
	                    <button onclick="location.href='<%=contextPath%>/listSearch.free?currentPage=<%=currentPage%>&sorting=<%=sorting%>&inputValue=<%=inputValue%>&searchOption=<%=searchOption%>'">목록</button>
	                    			
                    <%} %>
                </div>
            </div>
                
            
        </div>
    </div>
    
    
    
    
	<script>
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	/* 좋아요 관련 */
	
	
	$(function(){ /* 화면이 다 깔리고 실행 */
		
		/* 좋아요 수 , 내가 눌렀는지 안눌렀는지 확인 */
	
		//console.log("실행됨1");
		
		selectLike(); /* 좋아요 아이콘  */
		selectLikeCount();   /* 좋아요 수 */ 
		
	});
	
	
	
	/* 좋아요 눌렀는지 조회 */
	function selectLike(){
		
		

			
		let freeNo = <%=f.getFreeNo()%>;  /* 게시글번호 */

		/* 
			&#x2764;&#xFE0F;  빨간하트
			&#x1F90D;		  흰하트
		*/
		$.ajax({
			url:"<%=contextPath%>/selectLike.free", 
			type:"post",
			data:{
				freeNo:freeNo,
			},
			success:function(result){
				
				
				if(result>0){  /* 좋아요 테이블 - 게시글번호, 내유저번호  */
					/* 아이콘색깔 빨갛게, 클릭가능 삭제메소드 */		
					
				/*	$("#like-icon").html("&#x2764;&#xFE0F;");
					$("#like-icon").off("click");  //클릭속성이 중첩되기때문에 제거필요 
					$("#like-icon").click(()=>deleteLike(freeNo));
				*/	
					$("#like-icon").off("click").html("&#x2764;&#xFE0F;").click(()=>deleteLike(freeNo));
					
					
				}else{
					/*아이콘 색깔 하얗게, 클릭가능 입력메소드*/
				/*	$("#like-icon").html("&#x1F90D;");
					$("#like-icon").off("click"); 
					$("#like-icon").click(()=>insertLike(freeNo));
				*/	
					$("#like-icon").off("click").html("&#x1F90D;").click(()=>insertLike(freeNo));
					
				}
				
			},
			error:function(){
				console.log("ajax통신 실패 : 좋아요 아이콘 조회")
			}
		});
		
		
	};
	
	/* 좋아요 설정 */
	function insertLike(freeNo){
		//console.log("좋아요 설정");
		
		$.ajax({
			url:"<%=contextPath%>/insertLike.free",
			type:"get",
			data:{freeNo:freeNo},
			success:function(){
				console.log("ajax 좋아요설정 성공");
				selectLike();
				selectLikeCount();
			},
			error:function(){
				console.log("ajax 좋아요설정 실패");
			}
		})
		
		/* selectLike(); 비동기적으로 실행되기때문에 ajax 구문보다 먼저 실행될수 있음*/
		
	};
	
	/* 좋아요 해제 */
	function deleteLike(freeNo){
		//console.log("좋아요 해재");
		
		$.ajax({
			url:"<%=contextPath%>/deleteLike.free",
			type:"get",
			data:{freeNo:freeNo},
			success:function(){
				console.log("ajax 좋아요해제 성공");
				selectLike();
				selectLikeCount();
			},
			error:function(){
				console.log("ajax 좋아요해제 실패");
			}
		})
		
		
	};
	
	
	/* 좋아요 수 */
	function selectLikeCount(){
		
		let freeNo = <%=f.getFreeNo()%>;
		
		$.ajax({
			url:"<%=contextPath%>/selectLikeCount.free",
			type:"get",
			data:{freeNo:freeNo},
			success:function(likeCount){
				
				$("#like-count").html(likeCount);
				console.log("ajax 좋아요수 조회 성공");
			},
			error:function(){
				console.log("ajax 좋아요수 조회 실패");
			}
		});
	}
	
	

	
		/* 댓글관련 */
		let setIntervalComment; //setInterval 전역변수로 설정(종료했다 다시 실행하기 위해)
		
	 	$(function(){ //페이지가 로드될때 댓글리스트 + 댓글 실시간 업데이트
			selectCommentList();
			setIntervalComment = setInterval(selectCommentList,1000);  //
		})  
	
	
		
		
		
		/* 댓글작성 ajax  */
		function inserComment(){
			
			
			let comment = $("#free-comment").val();
			let freeNo = <%=f.getFreeNo()%>;
			
			
			$.ajax({
				
				url:"<%=contextPath%>/commentInsert.free",
				type:"post",
				data:{
					freeNo : freeNo,
					comment : comment
	
				},
				success:function(result){
					if(result>0){
						selectCommentList(); 
						//console.log("댓글 인서트 됨") /* 됨 */
						$("#free-comment").val("");
					}else{
						alert("댓글작성용 ajax 통신 실패!!");
						$("#free-comment").val("");
					}
				},
				/* error : function(){} */
			});
			
		}
	
		
		
	 // 댓글리스트 조회용 ajax
		function selectCommentList() {
			
		    $.ajax({
		        url: "<%=contextPath%>/commentList.free",
		        type: "get",
		        data: { freeNo: <%=f.getFreeNo()%> },
		        success: function(result) {
		            let resultStr = "";
		            const loginUserNo = <%=(loginUser != null) ? loginUser.getUserNo() : -1%>;
		           
		            for (let i in result) {
		               
		                resultStr += "<tr>"
		                           + "<td><b>" + result[i].userNickName + "</b></td>"
		                           + "<td class='commentContent'>" + result[i].freeCommContent + "</td>"
		                          
		               
		                if (loginUserNo == result[i].userNo) {
		                    resultStr += "<td>"+
		                    			 "<button class='update-comment btn btn-sm ' freeCommNo='"+result[i].freeCommNo+"'>수정</button>"+
		                    			 "<button class='delete-comment btn btn-sm ' freeCommNo='"+result[i].freeCommNo+"'>삭제</button>"+
		                    			 "</td>";
		                }else{ resultStr += "<td></td>";}
			
						resultStr += "<td>" + result[i].freeCommDate + "</td>";
		                          
		                resultStr += "</tr>";
		            }
		            $(".comment-table").html(resultStr);
		           
		            $("#comment-count").html(result.length);
		           
		           
		        },
		        error: function() {
		            console.log("댓글리스트조회용 ajax 통신실패!!");
		        }
		    });
		    
		    
            
        	
        
        
        
		}
		
		
		
		
		
		  
		
		//댓글 수정 버튼 클릭시 AJAX 
		$(document).on('click', '.update-comment', function() { //댓글 수정버튼 클릭시

		    //이전에 띄운 댓글수정창 삭제구문 필요
		    $(".insert-comment").closest("tr").remove();

		    // setInterval 잠시 종료 (끝나면 다시 실행)
		    if (setIntervalComment) {
		        clearInterval(setIntervalComment);
		    }

		    let freeCommNo = $(this).attr("freeCommNo"); //수정할 댓글 번호 뽑아내기

		    let updateForm = `
		        <tr>
		            <td colspan='4'>
		                <div class="insert-comment">
		                    <div class="coment-writer"><b><%if(loginUser!=null){%><%=loginUser.getUserNickname()%><%}else{%>로그인이 필요합니다.<%}%></b></div>
		                    <div class="comment-content">
		                        <textarea name="freeComment" id="free-comment1" class="comment-content-area" style="resize: none" maxLength="1000" oninput="updateCharCount(this)"></textarea>
		                    </div>
		                    <div class="comment-bottom">
		                        <div class="comment-button">
		                            <button  onClick="updateComment(this)" class="btn btn-info btn-sm">등록</button>
		                            <button  onClick="selectCommentList()" class="btn btn-secondary btn-sm">취소</button>
		                        </div>
		                        <div id="charCount1">0/1000</div>
		                    </div>
		                </div>
		            </td>
		        </tr>`;

		    $(this).closest("tr").after(updateForm); // 댓글수정 클릭시 입력창 띄어주기
		    selectComment(freeCommNo, this); //기존의 댓글 내용 조회해서, textArea 에 넣어주기 
		});

		// 댓글 수정 창 글자 수 업데이트 함수
		function updateCharCount(textarea) {
		    const charCount = document.getElementById("charCount1");
		    const currentLength = textarea.value.length;
		    const maxLength = textarea.getAttribute("maxlength");
		    
		    charCount.innerHTML = currentLength + "/" + maxLength;
		}
		
		
		
	 	function updateComment(element){ //댓글 업데이트
				
			let commentContent =  $(element).closest("tr").find(".comment-content-area").val();		
			let commentNo =  $(element).closest("tr").find(".comment-content-area").attr("freeCommNo");
			//let commUserNo = $(element).closest("tr").find(".comment-content-area").attr("userNo"); 
			
	 		//console.log(commentContent);
	 		//console.log(commentNo);	
	 		//console.log(commentUserNo);
	 		
	 		
			
			$.ajax({url:"<%=contextPath%>/updateComment.free",
					type:"post",
					data:{
						freeCommentContent:commentContent,
						freeCommentNo:commentNo,
						//userNo:commUserNo
					},
					success:function(result){
						if(result>0){
							alert("댓글이 수정됐습니다.");
							selectCommentList();
							setInterval(selectCommentList,1000);
						}else{
							alert("댓글수정에 실패했습니다.");
						}
						
					},
					error:function(){
						console.log("ajax 댓글수정 연결 실패");
					}
			})
			 
			
		}; 
		 
		
		  
		 
		 
		function selectComment(freeCommNo,element){//댓글 한개 조회-> 수정창에 댓글 내용 띄어주기
		
			$.ajax({url:"<%=contextPath%>/selectComment.free",
					type:"post",
					data:{freeCommNo:freeCommNo},
					success:function(result){
						
	
						
						$(element).closest("tr").next().find(".comment-content-area").val(result.freeCommContent);
						//버튼 요소의 가장 가까운 tr요소의 다음 tr중에서 .comment-content인 놈을 찾아라
						$(element).closest("tr").next().find(".comment-content-area").attr("freeCommNo",result.freeCommNo);
						$(element).closest("tr").next().find(".comment-content-area").attr("userNo",result.userNo);
						
						
					},
					error:function(){
						console.log("ajax 댓글한개조회 실패")
					}
			})
		}
		
	
		 
		// 댓글 삭제 ajax 
		
		$(document).on('click', '.delete-comment',function(){
			
			let freeCommNo = $(this).attr("freeCommNo");
			//console.log(freeCommNo); 
			
			if(confirm("댓글을 삭제하시겠습니까?")){
				$.ajax({
					
					url : "<%=contextPath%>/deleteComment.free",
					type:"get",
					data:{freeCommNo:freeCommNo},
					success: function(){
						selectCommentList();
						alert("댓글이 삭제되었습니다.")
						
					},
					error:function(){
						console.log("ajax 댓글삭제 실패");
					}
				})
			}
								
		});
		/* 댓글관련 */
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    	 <!-- 게시글 관련 -->
    
    
		function remove(){ //게시글 삭제 버튼 클릭시 
			if(confirm("삭제하시겠습니까?")){
				location.href="<%=contextPath%>/delete.free?freeNo=<%=f.getFreeNo()%>&userNo=<%=f.getUserNo()%>";
			}
		}
		function updateForm(){ // 게시글 수정 버튼 클릭스
			if(confirm("수정하시겠습니까?")){
				location.href="<%=contextPath%>/updateForm.free?freeNo=<%=f.getFreeNo()%>&userNo=<%=f.getUserNo()%>";
			}																							
		}    
		<!-- 게스글 관련 -->
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    </script> 
    
    <%@ include file="../common/footer.jsp"%>  <!-- 바보구간 --> 
        
        
    </body>
</html>