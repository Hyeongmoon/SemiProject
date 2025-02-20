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
        /* Reset margin and padding for html and body */
        body{
            margin: 0;
            padding: 0;
            width: 100%;
            height: 100%;
            border: 1px solid lightgray;
        }
        body {
            font-family: "Noto Sans KR", "Malgun Gothic", sans-serif;
            background: #f8f9fa;
            color: #333;
        }


        .container {
            max-width: 600px;
            margin: auto;
        }

        .form-section {
            background-color: white;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
        }
		

        .email{
            width:500px;
        }
        input#email_domain.form_w200{
            border-radius: 20;
        }
        .fixed-input {
	        background-color: #e9ecef; /* 비활성화된 느낌 */
	        pointer-events: none; /* 클릭 차단 */
	        text-align: center;
        }

        /* 스피너 제거 스타일 */
        .no-spinner {
            -moz-appearance: textfield; /* Firefox */
        }

        .no-spinner::-webkit-inner-spin-button,
        .no-spinner::-webkit-outer-spin-button {
            -webkit-appearance: none; /* Chrome, Safari, Edge */
            margin: 0; /* 기본 여백 제거 */
        }
        .dash {
        display: inline-block;
        margin: 0 8px; /* 좌우 여백 조절 */
        line-height: 1; /* 수직 가운데 정렬 */
   		}
    	.p {
    		color : red;
    	}
    </style>
</head>

<body>
	
	<%@ include file="/views/common/navbar.jsp" %>
   
    <!-- Registration Form -->
	<div class="container">
    <div class="container-fluid">
	    <div class="form-section">
	        <h3 class="text-center mb-4">회원정보 입력</h3>
	        <form id="enroll-form" action="<%=contextPath %>/insert.fh" method="post">
	            <div class="userid">
	                <label for="userId">* 아이디</label>
	                <div class="input-group" style="width: 330px;">
	                    <input type="text" class="form-control" id="userId" name="userId" placeholder="아이디">
	                    <div class="input-group-append">
	                        <button onclick="idCheck();" class="btn btn-secondary" type="button">중복확인</button>
	                    </div>
	                </div>
	            </div>
	
	            <hr>
	            <div class="password">
	                <label for="password">* 비밀번호</label>
	                <input type="password" class="form-control" id="password" name="pwd1" placeholder="비밀번호" style="width:250px;" oninput="invalidPwd()">
	                <span id="invalidPwd" name="invalidPwd">&nbsp;&nbsp;</span>
	                <p class="p">
	                    공백을 제외한 숫자, 영문(대소문자), 특수문자를 포함한 8~20자
	                </p>
	            </div>
	            <hr>
	            <div class="passwordcheck">
	                <label for="passwordConfirm">* 비밀번호 확인</label>
	                <input type="password" class="form-control" id="passwordConfirm" name="pwd2" placeholder="비밀번호 확인" style="width:250px;" oninput="pwCheck()">
	            </div>
	            <span id='pwdConfirm' name="pwdConfirm">&nbsp;&nbsp;</span>
	            <hr>
	            <div class="usernickname">
	                <label for="nickname">* 닉네임</label>
	                <input type="text" class="form-control" id="nickname" name="userNickname" placeholder="닉네임" style="width:200px;" oninput="nicknameCheck()">
	            </div>
				<span id='nicknameConfirm' name="nicknameConfirm">&nbsp;&nbsp;</span>
	            <hr>
	            <div class="username">
	                <label for="name">* 이름</label>
	                <input type="text" class="form-control" id="name" name="userName" placeholder="이름" style="width:200px;">
	            </div>
				<hr>
				<div class="email">
				    <label for="email">* 이메일</label><br>
				    <input type="hidden" id="fullEmail" name="fullEmail" required>
				    <div class="form-row">
				        <div class="col">
				            <input type="text" id="email_id" class="form-control" name="email1">
				        </div>
				        <span class="mt-2">@</span>
				        <div class="col">
				            <input type="text" id="email_domain" class="form-control" name="email2">
				        </div>
				        <div class="form-row">
				        <div class="col">
				            <select id="email_domain_select" class="form-control" onchange="updateEmailDomain()">
				                <option value="직접입력">직접입력</option>
				                <option value="naver.com">naver.com</option>
				                <option value="gmail.com">gmail.com</option>
				                <option value="hanmail.net">hanmail.net</option>
				                <option value="nate.com">nate.com</option>
				            </select>
				        </div>
				    </div>
				    </div>
				</div>
	            <hr>
	            <div class="birthdate">
	                <label class="d-block mb-1">생년월일</label>
	                <div class="form-row flex-grow-1">
	                    <div class="col">
	                        <select class="form-control" id="year" onchange="setBirthDate()">
	                            <option value="" disabled selected>년도</option>
	                        </select>
	                    </div>
	                    <div class="col">
	                        <select class="form-control" id="month" onchange="setBirthDate()">
	                            <option value="" disabled selected>월</option>
	                        </select>
	                    </div>
	                    <div class="col">
	                        <select class="form-control" id="day" onchange="setBirthDate()">
	                            <option value="" disabled selected>일</option>
	                        </select>
	                    </div>
	                </div>
	
	                <!-- 생년월일 -->
	                <input type="hidden" id="birthDate" name="birthDate">
	            </div>
			
	            <hr>
	            <div>
	                <label for="phone">휴대전화번호</label><br>
	                <div class="form-row" style="display: flex; align-items: center;">
	                    <div class="col">
	                        <input type="tel" id="phoneNo" name="phoneNo" class="form-control" maxlength="11" placeholder="예: 01012345678">
	                    </div>
	                </div>
	            </div>
	            <hr>
	            <div class="form-group">
                    <label for="address">주소</label>
                    <input type="text" class="form-control" id="address" name="address" onclick="addressApi();">
                </div>
	            <hr>
	            <div class="form-group d-flex justify-content-center">
				    <div class="me-2"> 
				        <button type="submit" class="btn btn-primary" onclick="fc(event);" style="width: 140px; height:48px;">다음</button>
				    </div>
				    <div>
				        <a href="<%=contextPath%>/tos.fh">
				            <button type="button" class="btn btn-secondary" style="width: 140px; height:48px;">이전</button>
				        </a>
				    </div>
				</div>
	        </form>
	    </div>
	</div>
	
															<!-- 스크립트 부분 -->
	
	<script>
		// 아이디 중복확인
		function idCheck() {
			
	        let $userId = $("#enroll-form input[name=userId]");
	        
	        $.ajax({
	            url: "<%=contextPath %>/idcheck.fh",
	            type: "get",
	            data: {checkId: $userId.val()},
	            success: function(result) {
	                if (result == "NNNNN") {
	                    alert("이미 사용중이거나 탈퇴한 회원의 아이디 입니다.");
	                    $userId.focus();
	                } else {
	                    if (confirm("사용 가능한 아이디 입니다. 사용하시겠습니까?")) {
	                        $userId.attr("readonly", true);
	                        $("#enroll-form button[type=submit]").removeAttr("disabled");
	                    } else {
	                        $userId.focus();
	                    }
	                }
	            },
	            error: function() {
	            }
	        });
	    }
		
		function nicknameCheck() {
	        let $userNickname = $(".usernickname input[name=userNickname]").val();
	        
	        $.ajax({
	            url: "<%=contextPath %>/nnc.fh",
	            type: "get",
	            data: { checkNickname: $userNickname },
	            success: function(result) {
	                if (result === "NNNNY") {
	                    $('#nicknameConfirm').text('사용가능한 닉네임입니다.').css('color', 'green');
	                } else {
	                    $('#nicknameConfirm').text('중복된 닉네임입니다.').css('color', 'red');
	                    alert("닉네임이 중복되었습니다. 다른 닉네임을 입력해 주세요.");
	                    $(".usernickname input[name=userNickname]").val(""); // 닉네임 입력란 비우기
	                    $(".usernickname input[name=userNickname]").focus(); // 입력란에 포커스
	                }
	            },
	            error: function() {
	                $('#nicknameConfirm').text('닉네임 확인 중 오류가 발생했습니다.').css('color', 'red');
	            }
	        });
	    }
		
		// 비밀번호 일치 확인
		function pwCheck() {
            const pwd1 = $("input[name=pwd1]").val();
            const pwd2 = $("input[name=pwd2]").val();

            if (pwd1 === "" && pwd2 === "") {
                $('#pwdConfirm').html('&nbsp;&nbsp;'); // 공백 유지
            } else if (pwd1 === pwd2) {
                $('#pwdConfirm').text('비밀번호가 일치합니다.').css('color', 'green');
            } else {
                $('#pwdConfirm').text('비밀번호가 일치하지 않습니다.').css('color', 'red');
            }
        }

		// 비밀번호 유효성 검사
        function invalidPwd() {
            const pwd1 = $("input[name=pwd1]").val();

            if (pwd1.length < 8 || pwd1.length > 20) {
                $('#invalidPwd').text("8자리 ~ 20자리 이내로 입력해주세요.").css('color', 'red');
            } else if (pwd1.search(/\s/) != -1) {
                $('#invalidPwd').text("비밀번호는 공백 없이 입력해주세요.").css('color', 'red');
            } else if (!/[0-9]/.test(pwd1) || !/[a-zA-Z]/.test(pwd1) || !/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/.test(pwd1)) {
                $('#invalidPwd').text("영문, 숫자, 특수문자를 혼합하여 입력해주세요.").css('color', 'red');
            } else {
                $('#invalidPwd').text("사용가능한 비밀번호입니다.").css('color', 'green');
            }
        }
		
        // 비밀번호 유효성 확인 함수
        function isPasswordValid(password) {
            const lengthValid = password.length >= 8 && password.length <= 20;
            const noSpaces = !/\s/.test(password);
            const hasNumber = /[0-9]/.test(password);
            const hasLetter = /[a-zA-Z]/.test(password);
            const hasSpecialChar = /[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/.test(password);

            return lengthValid && noSpaces && hasNumber && hasLetter && hasSpecialChar;
        }
		
		// 회원가입 전 조건 검사
		function fc(event) {
			
		    event.preventDefault(); // 기본 폼 제출 방지
	    	
	        let $userId = $(".input-group input[name=userId]");
	        let $pwd1 = $(".password input[name=pwd1]");
	        let $userNickname = $(".usernickname input[name=userNickname]");
	        let $userName = $(".username input[name=userName]");
	        let $email1 = $(".email input[name=email1]");
	        let $email2 = $(".email input[name=email2]");
	        
	        // 1. 아이디 입력 여부 확인
	        if (!$userId.val()) {
	            alert("아이디를 입력해 주세요.");
	            return; // 제출 중단
	        }
	
	        // 2. 아이디 중복 체크 여부 확인
	        if ($userId.prop("readonly") !== true) {
	            alert("아이디 중복 체크를 먼저 해주세요.");
	            return; // 제출 중단
	        }
	
	        // 3. 필수 입력 사항 확인
	        if (!$pwd1.val() || !$userNickname.val() || !$userName.val()) {
	            alert("모든 필수 입력 사항을 기재해 주세요.");
	            return; // 제출 중단
	        }
	
	        // 4. 비밀번호 유효성 확인
	        if (!isPasswordValid($pwd1.val())) {	
	        	return; // 비밀번호가 유효하지 않으면 중단
	        	
	        }
	        
	        // 4. 이메일 입력 여부 확인
	        if(!$email1.val() || !$email2.val()){
	        	alert("이메일을 입력해주세요")
	        	return; // 이메일 값이 없으면 제출 중단
	        }
	
	        // 5. Ajax로 서버에 데이터 전송
	        $.ajax({
	            url: "<%=contextPath%>/fc.fh",
	            type: "get",
	            data: {
	                fcId: $userId.val(),
	                fcPwd1: $pwd1.val(),
	                fcNickname: $userNickname.val(),
	                fcName: $userName.val()
	            },
	            success: function(result) {
	                if (result == "NNNNY") {
	                    // 모든 검사가 통과했으면 폼을 제출
	                    $("#enroll-form").off("submit").submit(); // 기존 submit 핸들러를 제거한 후 폼 제출
	                } else {
	                    alert("필수 입력 사항이 잘못되었습니다."); // 실패 시 경고
	                }
	            },
	            error: function() {
	                alert("서버와 통신 중 오류가 발생했습니다.");
	            }
	        });
	        
	    }
		
		function updateEmailDomain() {
	        const select = document.getElementById("email_domain_select");
	        const domainInput = document.getElementById("email_domain");
	
	        if (select.value === "직접입력") {
	            // 입력값을 도메인 입력 필드에 반영
	            domainInput.value = ""; // 입력 필드 비우기
	            domainInput.focus(); // 포커스 이동
	        } else {
	            // 선택한 도메인을 입력 필드에 반영
	            domainInput.value = select.value;
	        }
	    }
	
	    // 직접입력 필드와 드롭다운 선택이 서로 연동되도록 하는 추가 이벤트
	    document.getElementById("email_domain").addEventListener("input", function() {
	        const select = document.getElementById("email_domain_select");
	        if (this.value) {
	            select.value = "직접입력"; // 입력값이 있을 때 드롭다운을 "직접입력"으로 설정
	        } else {
	            select.selectedIndex = 0; // 입력 필드가 비어 있을 때 드롭다운 기본값으로 설정
	        }
	    });
		
	    function combineFields() {
	        const $userId = $(".input-group input[name=userId]");
	        const $pwd1 = $(".password input[name=pwd1]");
	        const $userNickname = $(".usernickname input[name=userNickname]");
	        const $userName = $(".username input[name=userName]");
	        const $email1 = $(".email input[name=email1]");
	        const $email2 = $(".email input[name=email2]");
	        
	        // 필수 입력 사항 확인
	        if (!$userId.val() || !$pwd1.val() || !$userNickname.val() || !$userName.val() || !$email1.val() || !$email2.val()) {
	            alert("모든 필수 입력 사항을 기재해 주세요.");
	            return false;  // 제출 중단
	        }

	        // 이메일 결합
	        const emailId = document.getElementById('email_id').value;
	        const emailDomain = document.getElementById('email_domain').value;
	        document.getElementById('fullEmail').value = emailId + '@' + emailDomain;

	        setBirthDate();
	        setPhone();
	        return true;  // 모든 검증 통과 시 true 반환
	    }
	    
	    function setBirthDate() {
	        const year = document.getElementById('year').value;
	        const month = String(document.getElementById('month').value).padStart(2, '0');
	        const day = String(document.getElementById('day').value).padStart(2, '0');
	        if (year && month && day) {
	            document.getElementById('birthDate').value = year + '-' + month + '-' + day;
	        }
	    }
	
	    window.onload = function() {
	        const birthDate = document.getElementById('birthDate').value;
	        if (birthDate) {
	            const [year, month, day] = birthDate.split('-');
	            document.getElementById('year').value = year;
	            document.getElementById('month').value = Number(month);
	            document.getElementById('day').value = Number(day);
	        }
	    };
	    
	    const yearSelect = document.getElementById('year');
	    for (let year = 1900; year <= 2100; year++) {
	        const option = document.createElement('option');
	        option.value = year;
	        option.textContent = year;
	        yearSelect.appendChild(option);
	    }
		const monthSelect = document.getElementById('month');
		        for (let month = 1; month <= 12; month++) {
		            const option = document.createElement('option');
		            option.value = month;
		            option.textContent = month;
		            monthSelect.appendChild(option);
		        }
		const daySelect = document.getElementById('day');
		        for (let day = 1; day <= 31; day++) {
		            const option = document.createElement('option');
		            option.value = day;
		            option.textContent = day;
		            daySelect.appendChild(option);
	    }
	
	    function setPhone() {
	        const phone = document.getElementById('phoneNo').value;
	        document.getElementById('fullEmail').value = phone;
	    }
	
	    function setEmail() {
	        const emailId = document.getElementById('email_id').value;
	        const emailDomain = document.getElementById('email_domain').value;
	        document.getElementById('fullEmail').value = emailId + "@" + emailDomain;
	    }
	    
	    
	</script>
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
			<script>
			function addressApi(){
				new daum.Postcode({
			        oncomplete: function(data) {
			        	$("#address").val(data.roadAddress).prop('readonly', true);
			            
			        }
			    }).open();	
			}
			</script>
	
	
	</div>
    <%@ include file="/views/common/footer.jsp" %>
</body>

</html>