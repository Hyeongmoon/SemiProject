<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	int fileLimit = 10;
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>

        /* 지울거 */
        div{
            /* border:1px dotted gold 구분선 */
        }


        /*전체*/
        #container-fluid{
            width:100%;
            margin-top:40px;
            padding:20px;
        }
        .insert-area{
            margin-top: 20px;
            margin-bottom:20px;
            border:4px solid lightgray;
            width:50%;
            /* height:80%; */
            border-radius:30px
        }
        .free-insert{
            width: 100%;
        }



        /* ==================================== */
        /* 해드부분 */
        
        .list-head{
            border-bottom: 3px solid black; 
            text-align:center;
        }
        
        .list-head>div>h1{
	        display: inline; 
	        font-size: 40px; 
	        vertical-align: middle;
	        font-weight:bold
	    }

	    .list-head img{
	        width:80px;
	    }

        /* ==================================== */
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
        /* ==================================== */
        /* 이미지 */
        .deleteBtn{}
        
        
        .deleteBtn:hover{
        	font-weight: bolder;
        }
        
        
        #fileArea img{
            width:60%;
            
        }
        /* ==================================== */
        /* 인풋파일 */
        /* ==================================== */
        /* 취소, 등록, 수정 버튼 */

        

    </style>
</head>
<body>
    
	<%@include file="../common/navbar.jsp" %>


    <div id="container-fluid">

		<!-- 게시판 제목 -->
		<div class="list-head" >
		    <div>
		        <img src="<%=contextPath%>/resources/images/noticeLogo.png" alt="게시판이미지" onclick="location.href='<%=contextPath%>/list.free'">
		        <h1>자유게시판</h1>
		        
		    </div>
		
		</div>

        <div class="free-insert">
            
            
            <div class="body" align="center">
                
                <div class="insert-area">
                    
                    <form id="insertForm"class="insert-form" action="<%=contextPath%>/insert.free" method="post" enctype="multipart/form-data">
                        
                        
                        <div class="free-title">
                            <input name="freeTitle"type="text" placeholder="제목" width="100%" required>
                        </div>
                        
                        <div class="free-content">
                            <textarea name="freeContent" placeholder="내용입력하세요" required></textarea>
                        </div>
                        
                        
                        
                       
                        
                        <div id="fileArea">
                        
                        	<!-- <img id="contentImg0"  alt="보여질 이미지0"> -->
                        	<!-- <input type="file" id="file i " name="upfile i " onchange="loadImg(this, i );"> -->
                        
                        </div>
                        
                        <div>
                        	<button type="button" id="fileAddBtn" onclick="fileAdd();" class="btn btn-info btn-small"> 파일추가+</button> 
                        </div>

						<br>                        
                        <div class="footer" align="center">
                    
			                <button type="submit" class="btn btn-lg btn-dark">등록/수정</button>
			                <button type="button" onclick="backList()" class="btn btn-secondary btn-lg ">취소</button>
			                        
                        </div>
                    </form> 
                </div>
                
                
                
                </div>
                
                
            
            
              
            
            
        </div>
    </div>
    
    <%@include file="../common/footer.jsp" %>
    
 <script>
	 
	
	// 파일추가 버튼 클릭 때마다, 파일, 이미지 태그 생성
	
	var i = 0;
	function fileAdd(){
	    if(i < <%=fileLimit%>){
	    	
	    	 let currentIndex = i;

	        let contentImgTag = "<img id='contentImg" + currentIndex + "' alt='image" + currentIndex + "'>";
	        let deleteBtnTag  = "<button type='button' id='deleteBtn" + currentIndex  + "' class='deleteBtn btn btn-sm  btn-danger'>x</button>";
	        let inputFileTag  = "<input type='file' id='file" + currentIndex + "' name='upfile" + currentIndex + "' style='display:none'><br>";
	        
	        $("#fileArea").append("<div id='fileSet" + currentIndex + "'>" + contentImgTag + deleteBtnTag + inputFileTag +"</div>");
	        
	       
	        
	        // 이벤트 위임 사용
	        $("#fileArea").on("click", "#contentImg" + currentIndex, function() {
	            clickEvent(currentIndex);
	        });

	        $("#fileArea").on("click", "#deleteBtn" + currentIndex, function() {
	            deleteFile(currentIndex);
	        });

	        $("#fileArea").on("change", "#file" + currentIndex, function() {
	            loadImg(this,currentIndex);
	        });

	        // 파일 선택
	        $("#file" + currentIndex).click();
	        
	        
	    }
	    i = i + 1; 

	    if(i == <%=fileLimit%>){
	        $("#fileAddBtn").css('display', 'none');
	    }
	}
	
		
	
	// 첨부파일이 추가될 때, 사라질 때 실행될 메소드
	/*
	function loadImg(inputFile, num){
		if(inputFile.files.length == 1){ // 첨부파일이 있을 때
			
			let fileInput = $(inputFile)[0];
		
			let reader = new FileReader();
			reader.readAsDataURL(fileInput.files[0]);
			reader.onload = function(e){
				$("#contentImg" + num).attr("src", e.target.result);
			}
		}else{ // 첨부파일이 없을 때
			deleteFile(num);
		}
	}
	*/
	//-> jquery전용으로 수정
	// 첨부파일이 추가될 때, 사라질 때 실행될 메소드
	function loadImg(inputFile, num) {
		
	    let fileInput = $(inputFile)[0]; // jQuery 객체를 DOM 요소로 변환

	    if (fileInput.files.length === 1) { // 첨부파일이 있을 때
	        let reader = new FileReader();
	        reader.readAsDataURL(fileInput.files[0]);
	        reader.onload = function(e) {
	            $("#contentImg" + num).attr("src", e.target.result);
	        }
	    } else { // 첨부파일이 없을 때
	        deleteFile(num);
	    }
	}
	
	
	

	// 이미지 태그 클릭 시, 파일첨부 클릭 이벤트 
	function clickEvent(num){
		
		$("#file" + num).click();
		
	}
	

	function deleteFile(num){ // 첨부파일이 있었을 때 이미지, 파일 태그 삭제 메소드
		
		$("#fileSet" + num).remove();
		
		for(let j = num + 1; j < i; j++){
			
			let currentIndex = j;
			
			$("#fileSet" + currentIndex).attr("id", "fileSet" + (currentIndex - 1)); 
			
			$("#contentImg" + currentIndex).attr({
				id: "contentImg" + (currentIndex - 1),
				alt: "이미지" + (currentIndex - 1)
			});	
			
			$("#contentImg" + (currentIndex - 1)).off("click").on("click", function(){ clickEvent(currentIndex - 1); });
		
			$("#deleteBtn" + currentIndex).attr({
				id: "deleteBtn" + (currentIndex - 1)
			});
			$("#deleteBtn" + (currentIndex - 1)).off("click").on("click", function(){ deleteFile(currentIndex - 1); });
			
			$("#file" + currentIndex).attr({
				id: "file" + (currentIndex - 1),
				name: "upfile" + (currentIndex - 1)
			});
			$("#file" + (currentIndex - 1)).off("change").on("change", function(){ loadImg(this, (currentIndex - 1)); });
				
			
			loadImg(  document.getElementById("file"+(currentIndex-1))  ,currentIndex-1);
		}

		
		i = i - 1;
		
		$("#fileAddBtn").css('display', ''); // 파일 추가 버튼이 사라졌다면, 다시 생성
		
		
	}
  
	function backList(){ // 리스트로 돌아가는 버튼
		location.href='<%=contextPath%>/list.free';
	}
</script>
</body>
</html>