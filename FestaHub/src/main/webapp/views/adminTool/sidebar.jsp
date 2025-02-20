<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%

	// request.getContextPath() 를 통해 context path 값 알아내기
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	
    <!-- jQuery 3.7.1 -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

    <!-- Popper JS -->
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
	

    <!-- Custom fonts for this template-->
    <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
        href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
        rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="css/sb-admin-2.min.css" rel="stylesheet">
    
</head>
<body id="page-top">

        <!-- Sidebar -->
        <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

            <!-- Sidebar - Brand -->
            <a class="sidebar-brand d-flex align-items-center justify-content-center" href="admin.fh">
                <div class="sidebar-brand-icon rotate-n-15">
                    <i class="fas fa-laugh-wink"></i>
                </div>
                <div class="sidebar-brand-text mx-3">관리페이지</div>
            </a>

            <!-- Divider -->
            <hr class="sidebar-divider my-0">

            <!-- Nav Item - Dashboard -->
            <li class="nav-item active">
                <a class="nav-link" href="admin.fh">
                    <i class="fas fa-fw fa-tachometer-alt"></i>
                    <span>Dashboard</span></a>
            </li>

            <!-- Divider -->
            <hr class="sidebar-divider">

            <!-- Heading -->
            <div class="sidebar-heading">
                Management
            </div>

            <!-- Nav Item - Tables -->
            <li class="nav-item">
                <a class="nav-link" href="adminTool.user">
                    <i class="fas fa-fw fa-table"></i>
                    <span>회원관리</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="adminTool.notice">
                    <i class="fas fa-fw fa-table"></i>
                    <span>공지사항</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="adminTool.festival">
                    <i class="fas fa-fw fa-table"></i>
                    <span>공연정보</span></a>
            </li>
			<li class="nav-item">
                <a class="nav-link" href="adminTool.review">
                    <i class="fas fa-fw fa-table"></i>
                    <span>공연후기</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="adminTool.accompany">
                    <i class="fas fa-fw fa-table"></i>
                    <span>동행구하기</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="adminTool.free">
                    <i class="fas fa-fw fa-table"></i>
                    <span>자유게시판</span></a>
            </li>

            <!-- Divider -->
            <hr class="sidebar-divider d-none d-md-block">

            <!-- Sidebar Toggler (Sidebar) -->
            <div class="text-center d-none d-md-inline">
                <button class="rounded-circle border-0" id="sidebarToggle"></button>
            </div>

        </ul>
        <!-- End of Sidebar -->

</body>
</html>