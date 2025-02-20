<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.fh.notice.model.vo.Notice" %>
<%
	// request 로 부터 n 을 뽑기
	Notice n = (Notice)request.getAttribute("n");
	// > 글번호, 글제목, 글내용, 작성자의 아이디, 작성일
	
	// 직전에 보던 list 화면을 띄우기위한 변수
	int currentPage = (int)session.getAttribute("noCurrentPage");
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


    /* -----전체 영역--------------------------------------------*/



    /*바깥테두리*/
    #container-fluid{ 
        /* 브라우저 창크기에 따라 자동 크기조절 */
        max-width:1400px;
        width:100%;
        /* border:1px dotted black; */  /*바깥 선*/
           
        margin:auto;
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
        font-size: 50px; 
        vertical-align: middle;
        font-weight: bold;
    }
    /*게시판 이름 옆, 게시글 총수 상하가운데정렬*/
    .list-head>div>span{
        vertical-align:-30px;
    }



    /* -----리스트 상단 영역---------------------------------------*/



    /*검색버튼 오른쪽정렬 */
    .search-area{  
        float : right;
    }

    /*검색버튼 영역*/
    .search-form{
        height:30px
    }

   




    /* -----리스트 영역--------------------------------------------*/

    /* 테이블 게시글 사이 간격*/
    #board-table td{ 
        height:85px;
        border-top: 1px solid gray;
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
    
    

    /*작성자영역 너비*/
    #board-table>tbody>tr>td>div>span:nth-child(1){ 
        display : inline-block;
        max-width: 15%;
    }
    /*날짜영역 너비*/
    #board-table>tbody>tr>td>div>span:nth-child(2){ 
        display : inline-block;
        max-width: 15%;
    }
    /*조회수영역 너비*/
    #board-table>tbody>tr>td>div>span:nth-child(3){ 
        display : inline-block;
        max-width:20%;
    }
    

    /* ==================================== */

    .insert-area{
        margin-top: 20px;
        margin-bottom:20px;
        border:4px solid lightgray;
        width:50%;
        /* height:80%; */
        border-radius:30px
    }

    /* 제목 */
    .insert-area input{ /*제목 인풋*/
        height:50px;
        margin-top:30px;

    }
    
    .insert-area input[type=text], /*제목인풋*/
    .insert-area textarea{ /*내용인풋*/
        width:80%; /*너비*/
        border:none; /*테두리제거*/
    }
    /* ==================================== */
    /* 내용 */
    .insert-area textarea{ /* 내용*/
        border-top: 3px solid lightgray;
        border-bottom: 3px solid lightgray;
        min-height: 200px; /* 최소 높이 */
        resize:none;
        
    }

    /* -----리스트 하단 영역---------------------------------------*/


    /* -----페이징바 영역-------------------------------------------*/


    





    

    /* ---------------------------------------------------------------------------- */
</style>


</head>
<body>

	<!-- 모든 페이지 상단에 보여줘야 하는 페이지 include -->
	<%@ include file="../common/navbar.jsp" %>

    <div id="container-fluid">

        <div class="list-head">
            <div>
                <img src="resources/images/noticeLogo.png" alt="공지사항 게시판이미지" width="100">
                <h1>공지사항 게시판</h1>
            </div>

        </div>
        
        <br>

        <div class="list-body">
 		
            <!-- 바디부분 -->
            <form id="update-form" 
                  action="<%= contextPath %>/update.no" method="post">
                <input type="hidden" name="nno" value="<%= n.getNoticeNo() %>">
                
                <div class="table-area">
                    <table id="board-table">
                        <tr>
                            <td>
                                <div class="body" align="center">
                    
                                    <div class="insert-area">
                                    
                                        <div class="notice-title">
                                            <input type="text"
                                            	   width="100%" required
                                            	   value="<%= n.getNoticeTitle() %>"
                                            	   name="noticeTitle">
                                        </div>
                                        
                                        <div class="notice-content">
                                            <textarea name="noticeContent" id="" 
                                            		  required><%= n.getNoticeContent() %></textarea>
                                        </div>
                                     
                                        <br><br>
                    
                                    </div>
                    
                                </div>
                            </td>
                            
                        </tr>
                    </table>
                </div>

                <br>
                
                <div align="right">
                <button type="submit" 
						class="btn btn-sm btn-secondary">
					수정
				</button>
				<button type="button"
						class="btn btn-sm btn-secondary"
						onclick="location.href='<%= contextPath %>/detail.no?nno=<%= n.getNoticeNo() %>'">
					취소
				</button>
				<button type="button"
						class="btn btn-sm btn-secondary"
						onclick="location.href='<%= contextPath %>/list.no?currentPage=<%= currentPage %>'">
					목록
				</button>
                </div>
            </form>

        </div>



        


    </div>


	<!-- 모든 페이지 하단에 보여줘야 하는 페이지 include -->
	<%@ include file="../common/footer.jsp" %>
    
</body>
</html>