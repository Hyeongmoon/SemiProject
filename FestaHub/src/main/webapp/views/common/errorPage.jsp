<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// request 로 부터 응답데이터인 에러 문구 (errorMsg) 뽑기
	String errorMsg = (String)request.getAttribute("errorMsg");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<!-- 모든 페이지 상단에 보여줘야 하는 페이지 include -->
	<%@ include file="navbar.jsp" %>
	
	<br><br>
	
	<h1 align="center" style="color : red;">
		<%= errorMsg %>
	</h1>
	
		<!-- 모든 페이지 하단에 보여줘야 하는 페이지 include -->
	<%@ include file="footer.jsp" %>

</body>
</html>


