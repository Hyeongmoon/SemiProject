<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList, com.fh.festival.model.vo.Festival, com.fh.common.model.vo.PageInfo" %>
<%
    ArrayList<Festival> list = (ArrayList<Festival>) request.getAttribute("list");
%>
    
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Festa Hub - Festival Information</title>

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
                    <div class="d-flex justify-content-between align-items-center mb-4">
                    <h1 class="h3 mb-2 text-gray-800">Festival Table</h1>
                    <!-- 글쓰기 버튼 -->
						<a href="<%= contextPath %>/enrollForm.fe" class="btn btn-primary" target="_blank">New Post</a>
					</div>

                    <!-- DataTales Example -->
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">Festival Data</h6>
                        </div>

                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                    <thead>
                                        <tr>
                                        	<th>FesNo</th>
								            <th>Thumbnail</th>
								            <th>Title</th>
								            <th>Day</th>
								            <th>Location</th>
								            <th>Nikname</th>
								            <th>UserId</th>
								            <th>WriteDate</th>
								            <th>Likes</th>
								            <th>Comments</th>
								            <th>Status</th>
								            <th>Content</th>
                                        </tr>
                                    </thead>
								    <tbody>
								        <% if (list != null && !list.isEmpty()) { 
								            for (Festival f : list) { %>
								                <tr>
					                	            <!-- 숨긴 열로 FES_NO 포함 -->
            										<td><%= f.getFesNo() %></td>
								                    <td>
								                        <% if (f.getTitleImg() != null) { %>
								                            <img src="<%= contextPath + f.getTitleImg() %>" alt="Thumbnail" style="width: 50px; height: auto;">
								                        <% } else { %>
								                            <span>No Image</span>
								                        <% } %>
								                    </td>
								                    <td><%= f.getFesTitle() %></td>
								                    <td><%= f.getFesDay() %></td>
								                    <td><%= f.getFesAddress() %></td>
								                    <td><%= f.getFesWriter() %></td>
								                    <td><%= f.getWriterId() %></td>
								                    <td><%= f.getFesDate() %></td>
								                    <td><%= f.getLikeCount() %></td>
								                    <td><%= f.getCommCount() %></td>
								                    <td style="font-weight: bold;"><%= f.getFesStatus() %></td>
								                    <td><%= f.getFesContent() %></td>
								                </tr>
								        <% } } else { %>
								            <tr>
								                <td colspan="11" class="text-center">No results found</td>
								            </tr>
								        <% } %>
								    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

                </div>
                <!-- /.container-fluid -->

            </div>
            <!-- End of Main Content -->

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
    
    <script>
	    $(document).ready(function() {
	    	const table = $('#dataTable').DataTable({
	            responsive: true,
	            paging: true,
	            ordering: true,
	            order: [[0, 'desc']],
	            language: {
	                emptyTable: "No results found"
	            },
	            columnDefs: [
	                { targets: [11], visible: false } // 10번(Content) 열 숨김
	            ]
	        });

	        // 상세 내용 생성 함수
	        function format(row, rowData) {
	            $.ajax({
	                url: 'getAdminFi.fe',
	                type: 'GET',
	                dataType: 'json',
	                data: { fesNo: rowData[0] },
	                success: function(fiList) {
	                    let imgStr = "";

	                    if (fiList.length > 0) {
	                        for (let i in fiList) {
	                            imgStr += '<div id="imgs_' + fiList[i].fesImgNo + '" class="comment">' +
	                                      ' <img src="/festahub' + fiList[i].fesImgPath + fiList[i].fesImgRename + 
	                                      '" alt="Festival Image" style="width: 100px; height: auto; margin: 5px;"><br>' +
	                                      ' <strong>- Img No:</strong> ' + fiList[i].fesImgNo + '<br>' +
	                                      ' <strong>- ImgName:</strong> ' + fiList[i].fesImgName + ' | ' +
	                                      ' <strong>ImgRename:</strong> ' + fiList[i].fesImgRename + '<br>' +
	                                      ' <strong>- ImgPath:</strong> ' + fiList[i].fesImgPath + '<br>' +
	                                      ' <strong>- isThumb:</strong> ' + fiList[i].fesImgThumb + ' | ' +
	                                      ' <strong> ImgStatus: ' + fiList[i].fesImgStatus + '</strong></div><br>';
	                        }
	                    } else {
	                        imgStr = '<p>No images available for this festival.</p>';
	                    }

	                    const detailsHtml = '<div class="details-content" style="display: none; padding:10px; border: 1px solid #ddd; background-color: #f9f9f9; cursor: pointer;">' +
	                                        '<strong>Festival No:</strong> ' + rowData[0] + '<br>' +
	                                        '<strong>Title:</strong> ' + rowData[2] + ' | ' +
	                                        '<strong>Day:</strong> ' + rowData[3] + ' | ' +
	                                        '<strong>Location:</strong> ' + rowData[4] + '<br>' +
	                                        '<strong>NickName:</strong> ' + rowData[5] + ' | ' +
	                                        '<strong>UserId:</strong> ' + rowData[6] + ' | ' +
	                                        '<strong>WriteDate:</strong> ' + rowData[7] + '<br>' +
	                                        '<strong>Content:</strong> ' + rowData[11] + '<br>' +
	                                        '<strong>Likes:</strong> ' + rowData[8] + ' | ' +
	                                        '<strong>Comments:</strong> ' + rowData[9] + ' | ' +
	                                        '<strong>Status: ' + rowData[10] + '</strong><br>' +
	                                        '<a href="/festahub/updateForm.fe?fesNo=' + rowData[0] + '" class="btn btn-primary mr-2" target="_blank">Update</a>' +
	                                        '<button class="btn btn-secondary" onclick="toggleStatus(' + rowData[0] + ', \'' + rowData[10] + '\')">' +
	                                        (rowData[10] === 'Y' ? 'Deactivate' : 'Activate') + '</button>&nbsp;&nbsp;' +
	                                        '<button class="btn btn-danger" onclick="deleteTable(' + rowData[0] + ')">DeleteTable</button><br>' +
	                                        imgStr +
	                                        '</div>';

	                    row.child(detailsHtml).show();
	                    row.child().find('.details-content').slideDown();

	                    // 상세정보 클릭 시 슬라이드로 닫기
	                    row.child().find('.details-content').on('click', function(e) {
	                        row.child().find('.details-content').slideUp(function() {
	                            row.child.hide();
	                            tr.removeClass('shown');
	                        });
	                        e.stopPropagation(); // 이벤트 전파를 중단해 상위 행의 클릭 이벤트 방지
	                    });
	                },
	                error: function() {
	                    alert("Failed to load festival images.");
	                }
	            });
	        }

	        // 테이블의 각 행에 클릭 이벤트 추가
	        $('#dataTable tbody').on('click', 'tr', function() {
	            const tr = $(this);
	            const row = table.row(tr);

	            if (row.child.isShown()) {
	                row.child().find('.details-content').slideUp(function() {
	                    row.child.hide();
	                    tr.removeClass('shown');
	                });
	            } else {
	                format(row, row.data());
	                tr.addClass('shown');
	            }
	        });
	    });

	
	    // toggleStatus 버튼 이벤트
	    function toggleStatus(fesNo, currentStatus) {
	        const newStatus = currentStatus === 'Y' ? 'N' : 'Y';
	        
	        $.ajax({
	            url: 'toggleStatus.fe',  // 서블릿 URL
	            type: 'POST',
	            data: { fesNo: fesNo, newStatus: newStatus },
	            success: function(response) {
	                if (response === 'success') {
	                    alert("Festival status has been updated to " + (newStatus === 'Y' ? 'active' : 'inactive') + ".");
	                    location.reload(); // 상태가 변경된 것을 반영하기 위해 페이지 새로고침
	                } else {
	                    alert("Failed to update festival status.");
	                }
	            },
	            error: function() {
	                alert("Error in status update request.");
	            }
	        });
	    }
	    
	    // deleteTable 버튼 이벤트
		function deleteTable(fesNo) {
		    // 경고 팝업을 띄워 사용자의 확인을 받습니다
		    if (confirm("경고: 이 페스티벌과 관련된 모든 데이터가 삭제됩니다. 계속하시겠습니까?")) {
		        // 확인 버튼을 누르면 삭제 요청을 진행
		        $.ajax({
		            url: 'deleteTable.fe',  // 서블릿 URL
		            type: 'POST',
		            data: { fesNo: fesNo },
		            success: function(response) {
		                if (response === 'success') {
		                    alert("This festival table has been deleted.");
		                    location.reload(); // 상태가 변경된 것을 반영하기 위해 페이지 새로고침
		                } else {
		                    alert("Failed to delete this festival table.");
		                }
		            },
		            error: function() {
		                alert("Error in delete request.");
		            }
		        });
		    } else {
		        // 취소 버튼을 누르면 삭제 요청을 취소
		        alert("삭제가 취소되었습니다.");
		    }
		}
    
    </script>

</body>

</html>