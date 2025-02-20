<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.ArrayList, com.fh.user.model.vo.User, com.fh.common.model.vo.PageInfo" %>
<%
    ArrayList<User> list = (ArrayList<User>) request.getAttribute("list");
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
    <title>Festa Hub - 회원 정보</title>
    <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link href="css/sb-admin-2.min.css" rel="stylesheet">
    <link href="vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
</head>

<body id="page-top">
    <div id="wrapper">
        <%@ include file="sidebar.jsp" %> <!-- 사이드바 인클루드 -->
        <div id="content-wrapper" class="d-flex flex-column">
            <div id="content">
                <%@ include file="topbar.jsp" %> <!-- 탑바 인클루드 -->

                <div class="container-fluid">
                    <h1 class="h3 mb-2 text-gray-800">회원 테이블</h1>
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">회원 정보</h6>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                    <thead>
                                        <tr>
                                            <th>회원번호</th>
                                            <th>아이디</th>
                                            <th>닉네임</th>
                                            <th>이름</th>
                                            <th>생년월일</th>
                                            <th>휴대전화번호</th>
                                            <th>이메일</th>
                                            <th>주소</th>
                                            <th>블랙리스트 사유등록일</th>
                                            <th>블랙리스트 등록사유</th>
                                            <th>회원가입일</th>
                                            <th>회원정보수정일</th>
                                            <th>회원 상태</th>
                                            <th>관리</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <% if (list != null && !list.isEmpty()) { 
                                            for (User u : list) { %>
                                                <tr id="user-<%= u.getUserNo() %>">
                                                    <td><%= u.getUserNo() %></td>
                                                    <td><%= u.getUserId() %></td>
                                                    <td><%= u.getUserNickname() %></td>
                                                    <td><%= u.getUserName() %></td>
                                                    <td><%= u.getBirthDate() %></td>
                                                    <td><%= u.getPhone() %></td>
                                                    <td><%= u.getEmail() %></td>
                                                    <td><%= u.getAddress() %></td>
                                                    <td class="black-list-reg-date-cell">
                                                        <%= u.getBlackListReg() != null ? u.getBlackListReg() : "없음" %>
                                                    </td>
                                                    <td>
												    <textarea id="blckaListRfr_<%= u.getUserNo() %>" style="height: 150px; width: 100%;" name="blckaListRfr" placeholder="없음">
												        <%= u.getBlackListRfr() != null ? u.getBlackListRfr() : "" %>
												    </textarea>
												    <button type="button" class="btn btn-secondary btn-block mt-3" 
												            onclick="registerBlackListReason('<%= u.getUserNo() %>', this)"
												            <%= u.getStatus().equals("Y") ? "disabled" : "" %>>등록</button>
												</td>
	

                                                    <td><%= u.getRegDate() %></td>
                                                    <td><%= u.getUpdateDate() %></td>
                                                    <td class="status-cell"><%= u.getStatus().equals("Y") ? "Y" : "N" %></td>
                                                    <td>
                                                        <button class="btn btn-danger btn-block mt-3" onclick="updateStatus('<%= u.getUserNo() %>', 'N')">블랙리스트 등록</button>
                                                        <button class="btn btn-secondary btn-block mt-3" onclick="updateStatus('<%= u.getUserNo() %>', 'Y')">블랙리스트 해제</button>
                                                    </td>
                                                </tr>
                                        <% } } else { %>
                                            <tr>
                                                <td colspan="14" class="text-center">No results found</td>
                                            </tr>
                                        <% } %>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>

                <%-- 페이징 처리 부분 --%>
                <div class="row">
                    <div class="col text-center">
                        <nav>
                            <ul class="pagination justify-content-center">
                                <% if (startPage > 1) { %>
                                    <li class="page-item"><a class="page-link" href="?currentPage=1">First</a></li>
                                    <li class="page-item"><a class="page-link" href="?currentPage=<%= startPage - 1 %>">Prev</a></li>
                                <% } 
                                for (int p = startPage; p <= endPage; p++) {
                                    if (p != currentPage) { %>
                                        <li class="page-item"><a class="page-link" href="?currentPage=<%= p %>"><%= p %></a></li>
                                    <% } else { %>
                                        <li class="page-item active"><span class="page-link"><%= p %></span></li>
                                    <% }
                                }
                                if (endPage < maxPage) { %>
                                    <li class="page-item"><a class="page-link" href="?currentPage=<%= endPage + 1 %>">Next</a></li>
                                    <li class="page-item"><a class="page-link" href="?currentPage=<%= maxPage %>">Last</a></li>
                                <% } %>
                            </ul>
                        </nav>
                    </div>
                </div>
                <%-- 페이징 처리 부분 끝 --%>
            </div>
        </div>
    </div>

    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    <script>
    function updateStatus(userNo, status) {
        if (confirm("정말로 상태를 변경하시겠습니까?")) {
            $.ajax({
                url: '<%=contextPath%>/adminTool.userstatus',
                type: 'POST',
                data: { userNo: userNo, status: status },
                dataType: 'json', // 응답 데이터 형식 명시
                success: function(res) {
                    alert(res.message); // 메시지 표시

                    // 상태 업데이트
                    var userRow = $('#user-' + userNo);
                    var statusCell = userRow.find('.status-cell'); // 회원 상태 셀

                    // 상태에 따라 셀 텍스트 및 버튼 활성화/비활성화
                    if (status === 'N') {
                        statusCell.text('N'); // 블랙리스트 상태
                        userRow.find('.btn-danger').prop('disabled', true);  // 블랙리스트 등록 버튼 비활성화
                        userRow.find('.btn-secondary').prop('disabled', false); // 블랙리스트 해제 버튼 활성화
                    } else {
                        statusCell.text('Y'); // 활성 상태
                        userRow.find('.btn-danger').prop('disabled', false); // 블랙리스트 등록 버튼 활성화
                        userRow.find('.btn-secondary').prop('disabled', true);  // 블랙리스트 해제 버튼 비활성화
                    }
                },
                error: function() {
                    alert("상태 변경에 실패했습니다. 관리자에게 문의하세요.");
                }
            });
        }
    }

    function registerBlackListReason(userNo, button) {
        var reason = document.getElementById("blckaListRfr_" + userNo).value;

        if (reason.trim() === "") {
            alert("사유를 입력해 주세요.");
            return;
        }

        $.ajax({
            url: '<%=contextPath%>/adminTool.blacklistrfr',
            type: 'POST',
            data: { userNo: userNo, reason: reason },
            dataType: 'json',
            success: function(res) {
                alert(res.message); // 메시지 표시

                // 상태 업데이트 및 블랙리스트 등록일 업데이트
                var userRow = $('#user-' + userNo);
                var blackListRegCell = userRow.find('.black-list-reg-date-cell'); // 등록일 셀

                // 등록일을 업데이트
                blackListRegCell.text(res.blackListRegDate); // 등록일 업데이트
            },
            error: function() {
                alert("사유 등록에 실패했습니다. 관리자에게 문의하세요.");
            }
        });
    }
    </script>
</body>
</html>
