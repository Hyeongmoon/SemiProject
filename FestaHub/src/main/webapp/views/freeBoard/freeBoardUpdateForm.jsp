<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
    import="com.fh.freeBoard.model.vo.FreeBoard,java.util.ArrayList,com.fh.freeBoard.model.vo.FreeBoardFile"%>
    
<!DOCTYPE html>

<%
	FreeBoard f = (FreeBoard)request.getAttribute("f");
	ArrayList<FreeBoardFile> files =(ArrayList<FreeBoardFile>)request.getAttribute("files");

%>
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
            max-width:1100px;
            width:100%;
            margin:auto;
            margin-top:40px;
            padding:20px;
        }
        .update-area{
            margin-top: 20px;
            margin-bottom:20px;
            border:4px solid lightgray;
            width:50%;
            /* height:80%; */
            border-radius:30px
        }
        .free-update{
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
        .update-area input{ /*제목 인풋*/
            height:50px;
            margin-top:30px;

        }
        
        .update-area input[type=text], /*제목인풋*/
        .update-area textarea{ /*내용인풋*/
            width:80%; /*너비*/
            border:none; /*테두리제거*/
        }
        /* ==================================== */
        /* 내용 */
        .update-area textarea{ /* 내용*/
            border-top: 3px solid lightgray;
            border-bottom: 3px solid lightgray;
            min-height: 200px; /* 최소 높이 */
            resize:none;
            
        }
        /* ==================================== */
        /* 이미지 */
        .files img{
	        display: block;
	        width: 70%; /* 선택적: 전체 너비를 차지하도록 설정 */
		}    
</style>


    </style>
</head>
<body>
    
	<%@include file="../common/navbar.jsp" %>


    <div id="container-fluid">
        
        <div class="free-update">
            		<!-- 게시판 제목 -->
		<div class="list-head" >
		    <div>
		        <img src="<%=contextPath%>/resources/images/noticeLogo.png" alt="게시판이미지" onclick="location.href='<%=contextPath%>/list.free'">
		        <h1>자유게시판</h1>
		        
		    </div>
		
		</div>
            
            <div class="body" align="center">
                
                <div class="update-area">
                    
                    <form id="updateForm"class="update-form" action="<%=contextPath%>/update.free?freeNo=<%=f.getFreeNo() %>" method="post" enctype="multipart/form-data">
                        
                        
                        <div class="free-title">
                            <input name="freeTitle"type="text" value="<%=f.getFreeTitle() %>" width="100%" required>
                        </div>
                        
                        <div class="free-content">
                            <textarea name="freeContent"  required><%=f.getFreeContent()%></textarea>
                        </div>
                        
                        <div class="free-img">
                        	
                        			
                        	
	                        
	                             
	                         
                        
                       	</div>
                       	
                       	<!-- 총 첨부할 수 -->
                       	<%int fileLimit = 10; %>
                       	
                       	
                       	<input type="hidden" name="fileLimit" value="<%=fileLimit %>" > <!-- 총 업로드할 파일수 -->
                       
                        <div class="files">
                        
                        	
	                        <% if(files!=null){ %> <!-- 첨부파일이 있었던 경우 (기존사이즈만큼 update, 나머지는 insert)-->
	                        						<!-- 만약,,,기존파일을.... 빈파일로 둘경우(그냥유지인데)...삭제하고싶으면?...?... ? --> 
	                        						<!-- 새로운 첨부파일이 없는경우, -->
	                        						<!-- 기존파일사이즈 만큼 반복 (if새첨부 else 새첨부x (if0 else 1) -->
	                  			
	                  			
	                  			
	                        	<% for(int i=0;i<files.size();i++){ %>
	                        	
	                        		
	                        		<!-- 기존 첨부파일들 정보 보여주기, -->
	                        		
	                        		<input type="hidden" name="deleteFile<%=i %>" id="deleteFile<%=i %>" value="0"><!--기존 첨부파일 존재, 빈파일로 둘경우0 , 삭제하는경우(이미지변경)1 그냥두냐or 삭제하냐-->
	                        		 
 	                        		<input type="hidden" name="originFreeFileNo<%=i%>" value="<%=files.get(i).getFreeFileNo() %>">
	                        		<input type="hidden" name="originFreeFileRename<%=i%>" value="<%=files.get(i).getFreeFileRename() %>"> <!-- 수정or삭제시 서버에서 삭제하기위해서 -->
	                        		<input type="hidden" name="imgPart" value="<%=contextPath%>/<%=files.get(i).getFreeFilePath()+files.get(i).getFreeFileRename()%>">
	                        		
	                        		<img id="contentImg<%=i %>" src="<%=contextPath%>/<%=files.get(i).getFreeFilePath()+files.get(i).getFreeFileRename()%>" alt="img<%=i%>">
	                        		
	                        		
	                        		<input type="hidden" id="deleteFile<%=i%> name="deleteFile<%=i %>" value="0"> 
	                        		<input type="file" id="file<%=i %>" name="reUpfile<%=i %>" onclick="loadImg(this,<%=i%>);" onchange="loadImg(this,<%=i%>);" style="display:none;">
	                        		
	                        		<!-- 기존첨부파일이 있는경우- 새첨부파일 있는경우 - 수정 -->
	                        		<!--                        새첨부파일 없는경우 - 0 그대로 두거나, (건들지 않은경우) -->
	                        		<!--											1  삭제          (건든 경우)       -->
	                        		
	                        		
	                       		<% } %>
	                       		
	                       		
	                       		
	                       		<% for (int i=files.size();i<fileLimit;i++){ %>
	                       			
	                       			<img id="contentImg<%=i %>" src="" alt="img<%=i%>" style="display:none;">
	                       			
	                        		<input type="file" id="file<%=i %>" name="reUpfile<%=i %>" onchange="loadImg(this,<%=i%>);" style="display:none;">
	                        		
	                        		
	                       			
	                       		
	                       		<% } %>
	                       		<input type="hidden" name="originCount" value="<%=files.size()%>"> 
	                       	
	                       		
	                       		
	                       		<!-- 기존 첨부파일이 없는경우 -->
							<% }else{ %>  <!-- 기존 첨부파일이 없는 경우(파일이 존재하면 다 추가.-->
								<%for(int i=0;i<fileLimit;i++) {%>
								
									
									<img id="contentImg<%=i %>" src="" alt="image<%=i%>" style="display:none;">
									
	                        		<input type="file" id="file<%=i %>" name="reUpfile<%=i %>" onchange="loadImg(this,<%=i%>);" style="display:none;">
									
							
							
								<%} %>
								<input type="hidden" name="originCount" value="0"> 
							<% } %>

                        </div>
                        
                        <div>
                        	<button type="button"onclick="plusFile();" class="btn btn-info btn-small">파일추가+</button>
                        </div>

                          
						
						
			                
						
						                       
                    </form> 
                </div>
                
                <div>
					<button type="submit" onclick="submitForm()" class="btn btn-primary">등록/수정</button>
			    	<button type="button" onclick="backDetail()" class="btn btn-secondary">취소</button>
			    </div> 
			
                
				 <div class="footer" align="center">
                   
            
            
              
            
            	</div>
        	</div>
    	</div>
    </div>
    <br>
    
    
    
    <script>
    
    	
    	var i = <%=(files!=null ? files.size() : 0 )%>;
    	
    	function plusFile(){
    		let currentIndex = i;
    		$("#contentImg"+i).css("display","");
    		
    		i=i+1;
    		
    		$("#contentImg"+currentIndex).click();
    	}
    	
    
    
        //버튼 클리시 form 제출      	
	 	function submitForm() {
		     document.getElementById("updateForm").submit(); //form태그내에 submit 버튼 누른것과 같은 기능
		}
         
		function backDetail(){
        	location.href='<%=contextPath%>/detail.free?freeNo=<%=f.getFreeNo()%>';
        	
        }

        
		
        //첨부파일 값이 변경될때 실행될 메소드
    	//첨부된 파일을, 이미지 태그로 미리보기 기능
   		function loadImg(inputFile,num){
    		
        	if(inputFile.files.length==1){ //추가된 파일이 있을때
        		
        		//첨부된 파일의 경로를 얻어와서, 
        		let reader = new FileReader();
        		reader.readAsDataUrl
        		
        		reader.readAsDataURL(inputFile.files[0]);
        		
        		reader.onload = function(e){
        			
        			<%for(int i=0;i<fileLimit;i++ ){%>
        			
        				if(num == <%=i%>){
        					
	        				$("#contentImg<%=i%>").attr("src",e.target.result);
	        				$("#deleteFile<%=i%>").val("0");
	        				
        				}
        			<%}%>

        		}
        	}else{ //파일이 없을때
        		
        		
        		<%for(int i=0;i<fileLimit;i++ ){%>
    			
					if(num == <%=i%>){
						
	    				$("#contentImg<%=i%>").removeAttr("src"); 
	    				$("#deleteFile<%=i%>").val("1"); 
	    				
	    			
	    				
					}
				<%}%>
				/* 
        		switch(num){
        		case 0:$("#contentImg0").removeAttr("src"); $("#deleteFile0").val("1");  console.log($("#deleteFile0").val());break;
        		case 1:$("#contentImg1").removeAttr("src");break;
        		case 2:$("#contentImg2").removeAttr("src");break;
        		case 3:$("#contentImg3").removeAttr("src");
        		} */
        	}

    	}
        
        function deleteImg(inputFile,num){
        	
        }
        
        
        //이미지 태그 클릭시, 파일첨부 클릭 이벤트 
        $(function(){
        	
        	<%for(int i=0; i<fileLimit; i++){%>
        	
	        	$("#contentImg<%=i%>").click(function(){
	        		
	        		$("#file<%=i%>").click();		
	        		
	        	});
	        	
        	<%}%>
        });
        
        
    
    </script>
    
    
    <%@include file="../common/footer.jsp" %>
</body>
</html>