<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int currentPage = (int)session.getAttribute("currentPage");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Festa Hub - 공연정보 등록</title>
	<style>
        h2 {
            font-size: 2rem !important;
            font-weight: bold !important;
            color: #2A253F !important;
            margin-bottom: 20px !important;
        }

        label {
            font-weight: bold;
            margin-top: 10px;
        }

        .thumbnail-preview, .image-preview {
            margin-top: 10px;
            display: flex;
            flex-wrap: wrap;
            gap: 10px;
        }
        
        .thumbnail-container, .image-container {
            position: relative;
            display: inline-block;
        }

        .thumbnail-preview img, .image-preview img {
            max-width: 100px;
            max-height: 100px;
            object-fit: contain;
            display: block;
        }

        .thumbnail-container .file-name, .image-container .file-name {
            text-align: center;
            font-size: 12px;
            margin-top: 5px;
            color: #333;
        }

        .thumbnail-container .remove-btn, .image-container .remove-btn {
            position: absolute;
            top: 0px;
            right: 0px;
            background-color: red;
            color: white;
            border: none;
            width: 20px;
            height: 20px;
            cursor: pointer;
        }

        .form-control {
            margin-bottom: 10px;
        }

        .btn-add {
            margin-top: 10px;
        }
        
        /* X 버튼 위치 조정 */
	    .remove-image-btn {
	        position: absolute;
	        top: 5px;
	        right: 5px;
	        background-color: red;
	        color: white;
	        border: none;
     	        border-radius: 3px;
	        cursor: pointer;
	    }
	
	    /* 추가 이미지 입력 폼 스타일 */
	    .image-upload {
	        position: relative;
	        margin-top: 10px;
	    }
	
	    /* + 버튼 위치 조정 */
	    #add-image-btn {
	        display: block;
	        margin: 10px 0;
	    }

        /* 반응형 */
        @media (max-width: 768px) {
            .container {
                padding: 10px;
            }

            h2 {
                font-size: 1.5rem;
            }
        }
	</style>
</head>
<body>
	
</body>
	<!-- 상단바 -->
	<%@ include file="../common/navbar.jsp" %>

	<!-- 메인 컨텐츠 공간 -->
	    <div class="container-fluid my-4">
	    <div class="contents-line">
	    <div class="festival-content">
        <h2 class="festival-title">공연 정보 등록</h2>

		<form action="<%= contextPath %>/insert.fe" method="post"
			  enctype="multipart/form-data">
			  
			<!-- 작성자 (현재 로그인한 회원) 의 번호를 hidden 으로 넘기기 -->
			<input type="hidden" name="userNo" 
				   value="<%= loginUser.getUserNo() %>">
				   
	        <!-- 공연 제목 입력 -->
	        <label for="title">공연 제목(*)</label>
	        <input type="text" id="fesTitle" name="fesTitle" class="form-control" placeholder="공연 제목을 입력하세요" required>
	
	        <!-- 공연 날짜 및 시간 입력 -->
	        <label for="date">공연 날짜(*)</label>
	        <input type="datetime-local" id="fesDay" name="fesDay" class="form-control" required>
	
	        <!-- 공연 장소 입력 -->
	        <label for="location">공연 장소</label>
	        <input type="text" id="fesAddress" name="fesAddress" class="form-control" placeholder="공연 장소를 입력하세요">
	
	        <!-- 썸네일 추가 영역 -->
	        <label for="thumbnail">썸네일</label>
	        <input type="file" id="thumbnail" name="thumbnail" class="form-control" accept="image/*">
	        <div class="thumbnail-preview"></div>
	
			<!-- 이미지 추가 영역 -->
			<label>기타 이미지<span class="small-text" style="color: red;"> ※ 최대 30mb 첨부 가능</span></label>
			<div id="image-upload-area">
			    <div class="image-upload">
			        <input type="file" name="images01" class="form-control" accept="image/*"> <!-- 첫 번째 이미지는 images01로 설정 -->
			        <div class="image-preview" style="margin-top: 10px;"></div> <!-- 개별 미리보기 영역 -->
			    </div>
			</div>
			<button type="button" id="add-image-btn" class="btn btn-secondary">+</button>
	
	        <!-- 게시글 내용 -->
	        <label for="content">게시글 내용(*)</label>
	        <textarea id="fesContent" name="fesContent" class="form-control" rows="10" style="resize: none;" placeholder="게시글 내용을 입력하세요" required></textarea>
	
	        <!-- 버튼 영역 -->
	        <div class="d-flex justify-content-between mt-4">
	            <a href="<%= contextPath %>/list.fe?currentPage=<%= currentPage %>" class="btn btn-secondary">돌아가기</a>
	            <button type="submit" class="btn btn-primary">등록하기</button>
	        </div>
		</form>
	</div>
	</div>
    </div>

	<script>
	    $(document).ready(function () {
	        let imageIndex = 2; // 두 번째 이미지 필드부터 시작 (첫 번째 필드는 images01로 설정됨)
	        
		 	// 썸네일 미리보기 기능 유지
	        $('#thumbnail').on('change', function () {
	            const thumbnailPreview = $('.thumbnail-preview').empty(); // 기존 썸네일 삭제
	            const file = this.files[0];
	            const reader = new FileReader();

	            reader.onload = function (e) {
	                const container = $('<div>', { class: 'thumbnail-container' });
	                const img = $('<img>', { src: e.target.result });
	                const fileName = $('<p>', { class: 'file-name', text: file.name });

	                container.append(img, fileName);
	                thumbnailPreview.append(container);
	            };

	            reader.readAsDataURL(file);
	        });

	        // 추가 이미지 업로드 필드 생성 및 미리보기 기능
		    $('#add-image-btn').on('click', function () {
		        const fieldName = 'images' + String(imageIndex).padStart(2, '0'); // 예: images02, images03 등으로 설정
		        const newImageUpload = $(
		            '<div class="image-upload">' +
		                '<input type="file" name="' + fieldName + '" class="form-control" accept="image/*">' +
		                '<button type="button" class="remove-image-btn">X</button>' +
		                '<div class="image-preview" style="margin-top: 10px;"></div>' + // 개별 미리보기 영역
		            '</div>'
		        );
		
		        // 이미지 파일 선택 시 미리보기
		        newImageUpload.find('input[type="file"]').on('change', function () {
		            const previewContainer = $(this).siblings('.image-preview');
		            previewContainer.empty(); // 기존 미리보기 초기화
		            const file = this.files[0];
		
		            if (file) {
		                const reader = new FileReader();
		                reader.onload = function (e) {
		                    const img = $('<img>', {
		                        src: e.target.result,
		                        style: 'max-width: 100px; max-height: 100px; display: block;'
		                    });
		                    const fileName = $('<p>', {
		                        class: 'file-name',
		                        text: file.name,
		                        style: 'text-align: center; font-size: 12px; margin-top: 5px; color: #333;'
		                    });
		                    previewContainer.append(img, fileName);
		                };
		                reader.readAsDataURL(file);
		            }
		        });
		
		        // X 버튼 클릭 시 필드 및 미리보기 삭제 후 이름 다시 설정
		        newImageUpload.find('.remove-image-btn').on('click', function () {
		            newImageUpload.remove();
		            updateImageFieldNames(); // 남아 있는 입력 필드의 이름을 다시 설정
		        });
		
		        $('#image-upload-area').append(newImageUpload);
		        imageIndex++; // 다음 이미지 필드를 위한 인덱스 증가
		    });
		
		    // 입력 필드의 이름을 순서대로 다시 설정
		    function updateImageFieldNames() {
		        imageIndex = 1; // 이미지 인덱스 초기화
		        $('#image-upload-area .image-upload').each(function () {
		            const newFieldName = 'images' + String(imageIndex).padStart(2, '0');
		            $(this).find('input[type="file"]').attr('name', newFieldName);
		            imageIndex++;
		        });
		    }

	        // 초기 이미지 업로드 필드에 미리보기 기능 추가
	        $('#image-upload-area .image-upload input[type="file"]').on('change', function () {
	            const previewContainer = $(this).siblings('.image-preview');
	            previewContainer.empty();
	            const file = this.files[0];

	            if (file) {
	                const reader = new FileReader();
	                reader.onload = function (e) {
	                    const img = $('<img>', { 
	                        src: e.target.result, 
	                        style: 'max-width: 100px; max-height: 100px; display: block;' 
	                    });
	                    const fileName = $('<p>', { 
	                        class: 'file-name', 
	                        text: file.name, 
	                        style: 'text-align: center; font-size: 12px; margin-top: 5px; color: #333;' 
	                    });
	                    previewContainer.append(img, fileName);
	                };
	                reader.readAsDataURL(file);
	            }
	        });
	    });
	</script>

	<!-- 하단바 -->
	<%@ include file="../common/footer.jsp" %>
</html>