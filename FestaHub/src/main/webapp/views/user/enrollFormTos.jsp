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
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: "Noto Sans KR", "Malgun Gothic", sans-serif;
        }

        body {
            background-color: #f5f5f5;
            
        }

        /* 메인 컨테이너 */
        .container {
            width: 760px;
            margin: 50px auto;
            background-color: white;
            border-radius: 10px;
            padding: 40px 60px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
        }

        h2 {
            text-align: center;
            margin-bottom: 30px;
            font-size: 24px;
            font-weight: 600;
        }
        /* 텍스트 영역 */
        textarea {
            width: 100%;
            height: 160px;
            border: 1px solid #d9d9d9;
            border-radius: 4px;
            padding: 12px;
            margin-top: 10px;
            background-color: #f9f9f9;
            font-size: 14px;
            line-height: 1.6;
            resize: none;
            overflow-y: scroll;
            
        }

        /* 체크박스 섹션 */
        .checkbox-container 	{
            display: flex;
            align-items: center;
            margin-top: 15px;
        }

        .checkbox-container input {
            margin-right: 8px;
            width: 20px;
            height: 20px;
        }

        .checkbox-container label {
            font-size: 14px;
            color: #333;
        }

        /* 버튼들 */
        .buttons {
            display: flex;
            justify-content: center;
            gap: 20px;
            margin-top: 30px;
        }

        .buttons button {
            width: 140px;
            height: 48px;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
        }

        .agree {
            background-color: #2c2f91;
            color: white;
        }

        .disagree {
            background-color: #b5b5b5;
            color: white;
        }

        /* 푸터 */
        .footer {
            background-color: #2c2f91;
            color: white;
            text-align: center;
            padding: 20px 0;
            margin-top: 50px;
        }

        .footer p {
            font-size: 14px;
        }
    </style>
</head>	
<body>
	<%@ include file="/views/common/navbar.jsp" %>
	
	
	<!-- 메인 컨테이너 -->
    <div class="container">
        <h2>약관동의</h2><br><br><br>
        <p>Festa Hub에 가입하기 위해 아래의 이용약관 및 개인정보 처리 방침에 동의해야합니다.</p>
        <br><br>
        <!-- 이용약관 동의 -->
        <label>
            <strong>이용약관 동의(필수)</strong>
            <br><textarea style="width : 800px;" readonly>
제1조 (목적)
본 약관은 Festa Hub가 제공하는 서비스의 이용과 관련하여 회원과 회사 간의 권리, 의무 및 책임사항을 규정하는 것을 목적으로 합니다.
...
            </textarea>
        </label>
        <div class="checkbox-container">
    <input type="checkbox" class="individual" id="terms" name="terms">
    <label for="terms">이용약관에 동의합니다.</label>
</div>
<br><br>
<!-- 개인정보 처리 방침 동의 -->
<label>
    <strong>개인정보 수집 및 이용에 대한 동의(필수)</strong>
    <br><textarea style="width:800px;" readonly>
1. 수집하는 개인정보 항목
회사는 회원가입, 서비스 제공을 위해 아래와 같은 개인정보를 수집합니다.
- 이름, 이메일, 전화번호 등
...
    </textarea>
</label>
<div class="checkbox-container">
    <input type="checkbox" class="individual" id="privacy" name="privacy">
    <label for="privacy">개인정보 수집 및 이용에 동의합니다.</label>
</div>

<div class="checkbox-container">
    <input type="checkbox" id="checkAll" name="checkAll" onclick="checkAll();">
    <label for="checkAll">모든 약관에 동의합니다.</label>
</div>
	<script>
	  // "모두 동의" 클릭 시 모든 개별 체크박스를 선택 또는 해제
	  function checkAll() {
	    const isChecked = $("#checkAll").is(":checked");
	    $(".individual").prop("checked", isChecked);
	  }
	
	  // 개별 약관 체크박스 상태가 바뀔 때 "모두 동의" 체크박스의 상태 변경
	  $(".individual").on("change", function () {
	    const allChecked = $("#terms").is(":checked") && $("#privacy").is(":checked");
	    $("#checkAll").prop("checked", allChecked);
	  });
	
	  // 다음 버튼 클릭 시 유효성 검사
	  function next() {
	    const ind1 = $("#terms").is(":checked");
	    const ind2 = $("#privacy").is(":checked");
	    
	    if (ind1 && ind2) {
	      location.href = "<%=contextPath%>/enrollform.fh";
	    } else {
	      alert("모든 약관에 동의해야 합니다.");
	    }
	  }
	</script>


	<div class="buttons">
        <button class="agree" onclick="next();">다음</button>
        <a href="<%=contextPath%>"><button class="disagree">이전</button></a>
    </div>


    <!-- 푸터 -->
    <div class="footer">
        <p>© 2024 Festa Hub. All rights reserved.</p>
    </div>
    </div>
    <%@ include file="/views/common/footer.jsp" %>
</body>

</html>