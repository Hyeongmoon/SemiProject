<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList, com.fh.freeBoard.model.vo.FreeBoard, com.fh.common.model.vo.PageInfo" %>
    
    
<%
	ArrayList<FreeBoard> list =(ArrayList<FreeBoard>)request.getAttribute("list");

	PageInfo pi = (PageInfo)request.getAttribute("pi");
	int currentPage = pi.getCurrentPage();
	int listCount = pi.getListCount();
	int startPage = pi.getStartPage();
	int endPage = pi.getEndPage();
	int maxPage = pi.getMaxPage();
	int pageLimit = pi.getPageLimit();
	
	
	//최신순(기본)/오래된순으로 조회하기
	
	
	//정렬
	String sorting = "desc";
	if((String)request.getAttribute("sorting")!=null){
		
		sorting = (String)request.getAttribute("sorting");
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
	
	/* System.out.println("리스트뷰 sorting = "+sorting +" searchOption = "+ searchOption+" inputValue = " + inputValue+" currentPage =" + currentPage); */

	
%>   
 
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Document</title>
<style>


    /* -----전체 영역--------------------------------------------*/



    /*바깥테두리*/
    #container-fluid{ 
        max-width: 1200px;
            margin: auto;
        width:100%;
        margin-top:40px;
        padding: 20px;
    }
    /*각 div 영역 사이 공간 너비*/
    #container-fluid>div{
        
        width:100%;
    }



    /* -----해드 영역--------------------------------------------*/



    /*게시판 제목 배치, 사이즈, 상하가운데정렬*/
    .list-head>div>h1{
        display: inline; 
        font-size: 40px; 
        vertical-align: middle;
        font-weight:bold
    }
    
     .list-head h1:hover{
     	
     	color:gray;
     	cursor;
     }
    /*게시판 이름 옆, 게시글 총수 상하가운데정렬*/
    .list-head>div>span{
        vertical-align:-30px;
    }
    .list-head img{
        width:80px;
    }


    /* -----리스트 상단 영역---------------------------------------*/




    /*검색버튼 영역*/
   /*  .search-form{
        height:30px
    }
 */
   
/*    #search,#input-value,#sorting{
   		height:40px;
   }

 */
 
	 .search-form{
	 
		  float : right;
	 }

	#sorting{
		width:110px;
	}

    /* -----리스트 영역--------------------------------------------*/

    /* 테이블 게시글 사이 간격*/
    #board-table td{ 
        height:85px;     
    }
    /*테이블 영역 위아래구분선*/
    #board-table{   
        border:none;
        border-top: 4px solid gray;
        border-bottom: 4px solid gray;
        margin : auto;
        
                
    }
    /* 테이블 td들의 텍스트상하 가운데 정렬*/
    #board-table td{ 
        vertical-align: middle;
        
    }


    
    /* 테이블 영역*/

    /*텍스트가운데정렬*/
    .list-body>div,table{
        width:100%;
        box-sizing: border-box;
    }

    /* #board-table>tr>td{ */
    #board-table tr td:first-child{
        text-align: center;  
    }

    #board-table td{
        border-top: 1px solid gray;
    }

    
    

    /*작성자영역 너비*/
    #board-table>tbody>tr>td>div>span:nth-child(1){ 
        display : inline-block;
        max-width: 15%;
        width: 100%;
    }
    /*날짜영역 너비*/
    #board-table>tbody>tr>td>div>span:nth-child(2){ 
        display : inline-block;
        max-width: 15%;
        width:100%;
    }
    /*조회수영역 너비*/
    #board-table>tbody>tr>td>div>span:nth-child(3){ 
        display : inline-block;
        max-width:20%;
    }
    

    /* -----리스트 하단 영역---------------------------------------*/


    /* -----페이징바 영역-------------------------------------------*/


    
	   .hover-effect:hover {
            cursor: pointer; 
           
        }

 




    /* ---------------------------------------------------------------------------- */
</style>


</head>
<body>
	
	

	 <%@ include file="../common/navbar.jsp" %> 
	
	
    <div id="container-fluid">
		
		<!-- 게시판 제목 -->
        <div class="list-head" >
            <div >
                <img src="<%=contextPath%>/resources/images/noticeLogo.png" alt="게시판이미지" >
                <h1 class="hover-effect" onclick="location.href='<%=contextPath%>/list.free'">자유게시판</h1>
                <span ><%=listCount %></span>
            </div>

        </div>
		
		
		
        
        <div>
            <!-- 리스트 상단검색버튼 부분 -->
            <div>
                <div class="search-area">
                    <form class="search-form form-inline" action="<%=contextPath %>/listSearch.free">

                        <select name="searchOption" id="search" class="custom-select cursor ">
                       
                            <option value="FREE_TITLE">제목</option>
                            <option value="FREE_CONTENT">내용</option>
                            <option value="USER_NICKNAME">작성자</option>
                        </select>
                        <input type="text" id="input-Value" name="inputValue" required class="form-control">
                        <button type=submit class="btn btn-secondary">검색</button>
                    </form>
                </div>

                <div>
                    <select name="sorting" id="sorting" class="custom-select">
                        <option value="desc">최신순</option>
                        <option value="asc" >오래된순</option>
                    </select>
                </div>
                
            </div>
        </div>
	
		
	<script>
		$(function(){
		
			/* 제목,작성자,내용으로 검색시 그대로 남겨두기 */
			$("#search>option").each(function(){
				
				if($(this).val()=="<%=searchOption%>"){
					$(this).attr("selected",true);
				}
			});
			
			
			//검색키워드 값 남겨두기
			$("#input-Value").val("<%=inputValue%>");
			
			//최신순/오래된순 남겨두기
			$("#sorting>option").each(function(){
				if($(this).val()=="<%=sorting%>"){
					$(this).attr("selected",true);
				}
			});
			
		});
		
		/* 현재 sorting,optionType,inputValue  태그에 표시 */
	</script>






	<script>
		
		/* 일반리스트, 검색 리스트 오래된 순으로 조회 */
		$(function(){
			
			$("#sorting").change(function(){
				
				<%-- alert("실행1"+"<%=searchOption%>"); --%>
				
				if(<%=searchOption.equals("")%>){ // 일반리스트
					
					/* alert("실행2"); */
					if(this.value=="asc"){
						/* alert("실행2-1"); //오래된순 */
						location.href="<%=contextPath%>/list.free?sorting=asc&currentPage=1";
						
					}else{
							
					/* 	alert("실행2-2"); */
						location.href="<%=contextPath%>/list.free?currentPage=1";
					}
				
				}else{  //검색리스트
					
					/* alert("실행3 검색리스트"); */
					if(this.value=="asc"){ //역순으로 asc
						
						/* alert("실행3-1 오래된순 asc"); //오래된순 */ 
						location.href="<%=contextPath%>/listSearch.free?sorting=asc&currentPage=1&searchOption=<%=searchOption%>&inputValue=<%=inputValue%>";
						
					}else{ //desc
						
						/* alert("실행3-2 최신순 desc"); */
						location.href="<%=contextPath%>/listSearch.free?currentPage=1&searchOption=<%=searchOption%>&inputValue=<%=inputValue%>";
					}
				}
			
		});
		
		
			
			
		});
	
	
		
	</script>
			
			
			
		<div class="list-body">	
	
            <!-- 바디부분 -->
            <div class="table-area">
                <table id="board-table" class="table-hover" >
                    
                    <!-- 반복될부분 -->
                    <%for(FreeBoard f : list){ %>
                    <tr class="freeBoard">
                        <td><%=f.getFreeNo() %></td>
                        <td class="hover-effect">
                            <div><b><%=f.getFreeTitle() %></b></div> 
                            <div>
                                <span><%=f.getUserNickName() %></span>
                                <span><%=f.getFreeDate() %></span>
                                <span>조회수 <%=f.getFreeCount() %></span>
                            </div>
                        </td>


						<!-- 좋아요 영역 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->
                        <td >
                         	<span class="like-icon hover-effect" data-free-no="<%=f.getFreeNo()%>">🤍</span>
                         	<span class="like-count"><%=f.getFreeLikeCount() %></span>
                         </td>
                        <td>
                        	&#x1F4AC;
                        	<b><%=f.getFreeCommentCount() %></b> 
                        </td>
                    </tr>
					<%} %>

                </table>
            </div>

            <br>
            
            
            <!-- 로그인을 했을 경우만 !! 보이게 -->
            
           
            <div align="right">
            	
            	<%if(loginUser != null){ %>
            
                <button class="btn btn-secondary" onclick="location.href='<%=contextPath%>/enrollForm.free'">글작성</button>
                
                <%} %>
            </div>


        </div>


		<script>
			//게시글 클릭시, 게시글 번호 넘기면서 상세보기
			$(function(){

				/* $(".freeBoard td:eq(2)").click(function() { } 동적으로 추가된 요소는 이렇게 불가*/
				$(".freeBoard").on("click", "td:eq(1)", function() { //동적으로 추가된 요소기 때문에//인덱스는 0부터..
					
					let freeNo = $(this).closest("tr").children().eq(0).text();
				
				 	<%-- location.href="<%=contextPath%>/detail.free?freeNo="+freeNo;  --%>
			   		location.href="<%=contextPath%>/detail.free?freeNo="+freeNo+"&currentPage=<%=currentPage%>&searchOption=<%=searchOption%>&inputValue=<%=inputValue%>&sorting=<%=sorting%>";
			   		
				});	
			});
			
		
		</script>


		<br>
		
        <!-- 페이징바영역 -->
        
		<!-- 페이지 네비게이션 -->
		<%if(searchOption.equals("")){ %> <!-- 일반페이지리스트일경우 -->		
		
	        <div class="row">
	            <div class="col text-center">
	                <nav>
	                    <ul class="pagination justify-content-center">
	                        <% if(currentPage > pageLimit) { %> <!-- 1번 페이지일 경우에는 이전 버튼이 보이지 않게 -->
		                        <li class="page-item">
		                            <a class="page-link" href="<%= contextPath %>/list.free?currentPage=1&sorting=<%=sorting%>">&lt;&lt;</a>
		                        </li>
		                        <li class="page-item">
		                            <a class="page-link" href="<%= contextPath %>/list.free?currentPage=<%= (startPage - 1) %>&sorting=<%=sorting%>">&lt;</a>
		                        </li>
	                        <% } %>
	
	                        
	                        <% for(int p = startPage; p <= endPage; p++) { %>
	                        	<!-- 
	                        		현재 출력하는 p번 페이지가 currentPage 와 일치하지 않는경우
	                        		a태그의 href 속성이 작동되게 구현
	                        	 -->
	                        	<% if(p != currentPage ) { %>
	                        		<li class="page-item">
	                        			<a class="page-link" href="<%= contextPath %>/list.free?currentPage=<%= p %>&sorting=<%=sorting%>"><%= p %></a>
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
		                               href="<%= contextPath %>/list.free?currentPage=<%= (endPage + 1) %>&sorting=<%=sorting%>">
		                            	&gt;
		                            </a>
		                        </li>
		                        <li class="page-item">
		                            <a class="page-link" 
		                               href="<%= contextPath %>/list.free?currentPage=<%= maxPage %>&sorting=<%=sorting%>">
		                            	&gt;&gt;
		                            </a>
		                        </li>
		                    <% } %>
	
	                    </ul>
	                </nav>
	            </div>
	        </div>
	        <!-- 페이지 네비게이션 끝 -->
        
        <% }else { %>  <!-- 검색결과 리스트일경우 -->
        
	         <div class="row">
		            <div class="col text-center">
		                <nav>
		                    <ul class="pagination justify-content-center">
		                        <% if(currentPage > pageLimit) { %> <!-- 1번 페이지일 경우에는 이전 버튼이 보이지 않게 -->
			                        <li class="page-item">
			                            <a class="page-link" href="<%= contextPath %>/listSearch.free?currentPage=1&searchOption=<%=searchOption%>&inputValue=<%=inputValue %>&sorting=<%=sorting%>">&lt;&lt;</a>
			                            
			                            
			                            
			                        </li>
			                        <li class="page-item">
			                            <a class="page-link" href="<%= contextPath %>/listSearch.free?currentPage=<%= (startPage - 1) %>&searchOption=<%=searchOption%>&inputValue=<%=inputValue %>&sorting=<%=sorting%>">&lt;</a>
			                        </li>
		                        <% } %>
		
		                        
		                        <% for(int p = startPage; p <= endPage; p++) { %>
		                        	<!-- 
		                        		현재 출력하는 p번 페이지가 currentPage 와 일치하지 않는경우
		                        		a태그의 href 속성이 작동되게 구현
		                        	 -->
		                        	<% if(p != currentPage ) { %>
		                        		<li class="page-item">
		                        			<a class="page-link" href="<%= contextPath %>/listSearch.free?currentPage=<%= p %>&searchOption=<%=searchOption%>&inputValue=<%=inputValue %>&sorting=<%=sorting%>"><%= p %></a>
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
			                               href="<%= contextPath %>/listSearch.free?currentPage=<%= (endPage + 1) %>&searchOption=<%=searchOption%>&inputValue=<%=inputValue %>&sorting=<%=sorting%>">
			                            	&gt;
			                            </a>
			                        </li>
			                        <li class="page-item">
			                            <a class="page-link" 
			                               href="<%= contextPath %>/listSearch.free?currentPage=<%= maxPage %>&searchOption=<%=searchOption%>&inputValue=<%=inputValue %>&sorting=<%=sorting%>">
			                            	&gt;&gt;
			                            </a>
			                        </li>
			                    <% } %>
		
		                    </ul>
		                </nav>
		            </div>
		        </div>
        
        
        <%} %>


    </div>

	 
 

	<%@ include file="../common/footer.jsp" %>



	<!-- 테이블의 각 TR 접근 ... -->
	<script>
	
		$(function(){
			
			$(".freeBoard").each(function(){  //각 요소마다 게시글번호 뽑아내기
			
				let freeNo = $(this).find(".like-icon").data("free-no");	
			
				//console.log(freeNo);
				selectLike(freeNo,this);
			});
	
		});
		
		
		function selectLike(freeNo,tr){
			
			$.ajax({
				url:"<%=contextPath%>/selectLike.free", 
				type:"get",
				data:{freeNo:freeNo},
				success:function(result){
						
					if(result>0){  /* 좋아요 테이블 - 게시글번호, 내유저번호  */
						/* 아이콘색깔 빨갛게, 클릭가능 삭제메소드 */		
						
					/*	$("#like-icon").html("&#x2764;&#xFE0F;");
						$("#like-icon").off("click");  //클릭속성이 중첩되기때문에 제거필요 
						$("#like-icon").click(()=>deleteLike(freeNo));
					*/	
						$(tr).find(".like-icon").off("click").html("&#x2764;&#xFE0F;").click(()=>deleteLike(freeNo,tr));		
						
					}else{
						/*아이콘 색깔 하얗게, 클릭가능 입력메소드*/
					/*	$("#like-icon").html("&#x1F90D;");
						$("#like-icon").off("click"); 
						$("#like-icon").click(()=>insertLike(freeNo));
					*/	
						$(tr).find(".like-icon").off("click").html("&#x1F90D;").click(()=>insertLike(freeNo,tr));
						
					}

				},

				error:function(){
					console.log("ajax통신 실패 : 좋아요 아이콘 조회");
				}

			});

		};
		
		
		/* 좋아요 설정 */
		function insertLike(freeNo,tr){
			//console.log(freeNo);
			
			$.ajax({
				url:"<%=contextPath%>/insertLike.free",
				type:"get",
				data:{freeNo:freeNo},
				success:function(){
					console.log("ajax 좋아요설정 성공");
					selectLike(freeNo,tr);
					selectLikeCount(freeNo,tr);
				},
				error:function(){
					console.log("ajax 좋아요설정 실패");
				}
			})
			
			/* selectLike(); 비동기적으로 실행되기때문에 ajax 구문보다 먼저 실행될수 있음*/
			
		};
		
		/* 좋아요 해제 */
		function deleteLike(freeNo,tr){
			//console.log(freeNo);
			
			$.ajax({
				url:"<%=contextPath%>/deleteLike.free",
				type:"get",
				data:{freeNo:freeNo},
				success:function(){
					console.log("ajax 좋아요해제 성공");
					selectLike(freeNo,tr);
					selectLikeCount(freeNo,tr);
				},
				error:function(){
					console.log("ajax 좋아요해제 실패");
				}
			})
			
			
		};
		
		/* 좋아요 수 */
		function selectLikeCount(freeNo,tr){
			
			/* let freeNo = freeNo; */
			
			$.ajax({
				url:"<%=contextPath%>/selectLikeCount.free",
				type:"get",
				data:{freeNo:freeNo},
				success:function(likeCount){
					
				    $(tr).find(".like-count").html(likeCount);
					console.log("ajax 좋아요수 조회 성공");
				},
				error:function(){
					console.log("ajax 좋아요수 조회 실패");
				}
			});
		}    
		
	
	</script>


</body>
</html>