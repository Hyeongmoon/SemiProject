<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입 - Festa Hub</title>
    <!-- Bootstrap 4.6.2 CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
	<style>
		#enrollFormSuccess{
			display: flex;
	        justify-content: center;
	        align-items: center;
		}
		.successlt{
			text-align : center;
			
		}
	</style>
</head>	
	<%@ include file="/views/common/navbar.jsp" %>
	<div id="enrollFormSuccess" name="enrollFormSuccess">
		<img src="<%=contextPath %>/resources/images/enrollFormSuccess.png" style="width : 750px; height : 650px;">
	</div>
	<div class="successlt">
	<h3>
		회원가입을 축하드립니다!
	</h3>
	<br>
	<p>
		지금 바로 다양한 공연정보와 볼거리가 있는 Festa Hub를 둘러보세요!  
	</p>
	<br>
	<div>
		<a class="btn btn-primary" href="<%=contextPath %>" role="button">둘러보기</a>
	</div>
	<br><br><br>
	</div>
    <%@ include file="/views/common/footer.jsp" %>
</body>

</html>