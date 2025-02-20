<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 탈퇴</title>

<!-- Bootstrap 4.6.2 CSS -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<style>
    body {
        background-color: #f5f5f5;
    }
    .container {
        width: 760px;
        margin: 50px auto;
        background-color: white;
        border-radius: 10px;
        padding: 40px 60px;
        box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
    }
    h2, p {
        text-align: center;
    }
    #deleteUser {
        width: 300px;
        height: 50px;
        border-radius: 5px;
    }
    .submit {
        width: 150px;
        margin: 20px auto;
        text-align: center;
    }
</style>
</head>
<body>
    <%@ include file="/views/common/navbar.jsp" %>
    
    <div class="container">
        <h2>회원탈퇴</h2>
        <p>아래에 현재 사용 중인 비밀번호를 입력하시면 회원탈퇴 처리가 완료됩니다.</p>
        
        <form action="<%= request.getContextPath() %>/deleteform.fh" method="post">
            <div align="center">
                <input type="password" id="deleteUser" name="userPwd" placeholder="비밀번호" style="text-align:center;" required>
            </div>
            <div class="submit">
                <button type="submit" class="btn btn-sm btn-danger">탈퇴하기</button>
            </div>
        </form>
    </div>

    <%@ include file="/views/common/footer.jsp" %>
</body>
</html>
