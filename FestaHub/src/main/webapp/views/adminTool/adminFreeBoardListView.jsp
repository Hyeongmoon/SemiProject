<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList, com.fh.freeBoard.model.vo.FreeBoard, com.fh.common.model.vo.PageInfo" %>
<%
    ArrayList<FreeBoard> list = (ArrayList<FreeBoard>) request.getAttribute("list");
    PageInfo pi = (PageInfo) request.getAttribute("pi");
    int currentPage = pi.getCurrentPage();
    int startPage = pi.getStartPage();
    int endPage = pi.getEndPage();
    int maxPage = pi.getMaxPage();

    session.setAttribute("currentPage", currentPage);
%>
    
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Festa Hub - FreeBoard Information</title>

    <!-- Custom fonts for this template -->
    <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
        href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
        rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/sb-admin-2.min.css" rel="stylesheet">

    <!-- Custom styles for this page -->
    <link href="vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">

</head>

<body id="page-top">

    <!-- Page Wrapper -->
    <div id="wrapper">
	<%@ include file="sidebar.jsp" %> <!-- 사이드바 인클루드 -->

        <!-- Content Wrapper -->
        <div id="content-wrapper" class="d-flex flex-column">

            <!-- Main Content -->
            <div id="content">
				
	  	    	<%@ include file="topbar.jsp" %> <!-- 탑바 인클루드 -->

                <!-- Begin Page Content -->
                <div class="container-fluid">

                    <!-- Page Heading -->
                    <h1 class="h3 mb-2 text-gray-800">FreeBoard Table</h1>

                    <!-- DataTales Example -->
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">FreeBoard Data</h6>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                   <thead>
                                        <tr>
                                        	<th>FREE_NO</th>
								            <th>FREE_TITLE</th>
								            <th>FREE_CONTENT</th>
								            <th>DATE</th>
								            <th>LIKE</th>
								            <th>STATUS</th>
								            <th>USER_NO</th>
								            <th>COMMENT</th>
								            <th>LIKE</th>
								            <th>FILES</th>
								            <th>delete</th>
								            
                                        </tr>
                                      
                                 
                                    </thead>
								    <tbody>
								        <% if (list != null && !list.isEmpty()) { 
								            for (FreeBoard f : list) { %>
								                <tr freeNo="<%=f.getFreeNo() %>" >
            										<td><%= f.getFreeNo() %></td>
								                    <td><%= f.getFreeTitle() %></td>
								                    <td><%= f.getFreeContent() %></td>
								                    <td><%= f.getFreeDate()%></td>
								                    <td><%= f.getFreeCount()%></td>
								                    <td style="font-weight: bold;">
								                    
								                    	<%if(f.getFreeStatus().equals("Y")){ %>
									                    	<button type="button" class="btn btn-sm btn-primary" >Y</button>
								                    	<% }else{%>
								                    		<button type="button" class="btn btn-sm btn-secondary" >N</button>
								                    	<% } %>
									                    	
								                    	</button>
								                    </td>
													<td><%= f.getUserNo()%></td>
													<td><%= f.getFreeCommentCount()%></td>
													<td><%= f.getFreeLikeCount()%></td>
													<td><%= f.getFileCount()%></td>
													<td>
														<button type="button" class="deleteBtn btn btn-sm btn-outline-danger">삭제</button>
													</td>
													
								                </tr>
								                
								        <% } } else { %>
								            <tr>
								                <td colspan="11" class="text-center">조회된 게시글이 없습니다.</td>
								            </tr>
								        <% } %>
								    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <!-- 글쓰기 버튼 
				    <div class="text-right mb-4">
				        <a href="" class="btn btn-primary">글쓰기</a>
				    </div>
				    -->

                </div>
                <!-- /.container-fluid -->

            </div>
            <!-- End of Main Content -->

            <!-- End of Footer -->

        </div>
        <!-- End of Content Wrapper -->

    </div>
    <!-- End of Page Wrapper -->

    <!-- Bootstrap core JavaScript-->
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Core plugin JavaScript-->
    <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

    <!-- Custom scripts for all pages-->
    <script src="js/sb-admin-2.min.js"></script>

    <!-- Page level plugins -->
    <script src="vendor/datatables/jquery.dataTables.min.js"></script>
    <script src="vendor/datatables/dataTables.bootstrap4.min.js"></script>

    <!-- Page level custom scripts -->
    <script src="js/demo/datatables-demo.js"></script>
    
    <script>
    	$(function(){
    		
    		$("#dataTable .deleteBtn").on("click",function(){
    			deleteFree(this);
    		});
    		
    	});
    	
    	function deleteFree(trEl) {
    	    if (confirm("삭제하시겠습니까?")) {
    	      
    	        let freeNo = $(trEl).parent().parent().attr("freeNo"); 
    	        
    	        // location.href를 사용하여 페이지 이동
    	        location.href = "adminDelete.free?freeNo=" + freeNo;
    	    }
    	}
    </script>

</body>

</html>