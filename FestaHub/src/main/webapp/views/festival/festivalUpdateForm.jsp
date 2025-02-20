<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.ArrayList, com.fh.festival.model.vo.*, java.text.SimpleDateFormat"%>
<%
	Festival f = (Festival)request.getAttribute("f");

	ArrayList<FestivalImage> fiList = (ArrayList<FestivalImage>)request.getAttribute("fiList");
	
    // Timestamp 형식을 datetime-local에 맞게 변환
    SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
    String fesDayFormatted = (f.getFesDay() != null) ? datetimeFormat.format(f.getFesDay()) : "";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Festa Hub - 공연정보 수정하기</title>
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
	<!-- 상단바 -->
	<%@ include file="../common/navbar.jsp" %>
	
	<!-- 메인컨텐츠 내용 -->
    <div class="container-fluid my-4">
        <div class="contents-line">
            <div class="festival-content">
                <h2 class="festival-title">공연 정보 수정</h2>

                <form action="<%= contextPath %>/update.fe" method="post" enctype="multipart/form-data">
                    <!-- 게시글 번호를 hidden으로 넘기기 -->
                    <input type="hidden" name="fesNo" value="<%= f.getFesNo() %>">
                    
                    <!-- 공연 제목 입력 -->
                    <label for="fesTitle">공연 제목(*)</label>
                    <input type="text" id="fesTitle" name="fesTitle" class="form-control" value="<%= f.getFesTitle() %>" required>

                    <!-- 공연 날짜 및 시간 입력 -->
                    <label for="fesDay">공연 날짜(*)</label>
                    <input type="datetime-local" id="fesDay" name="fesDay" class="form-control" value="<%= fesDayFormatted %>" required>

                    <!-- 공연 장소 입력 -->
                    <label for="fesAddress">공연 장소</label>
                    <input type="text" id="fesAddress" name="fesAddress" class="form-control" value="<%= f.getFesAddress() %>">
                    
                    <!-- 썸네일 미리보기 및 수정 -->
                    <label for="thumbnail">썸네일 수정<span class="small-text" style="color: red;"> ※ 썸네일은 한장만 첨부하실 수 있습니다.(추가 등록 시 기존 썸네일 삭제)</span></label>
                    <% for(FestivalImage fi : fiList) { %>
                    	<% if(fi.getFesImgThumb().equals("Y")) { %>
                    		<input type="hidden" name="originThumbNo" value="<%= fi.getFesImgNo() %>">
                    		<input type="hidden" name="originThumbRename" value="<%= fi.getFesImgRename() %>">
                    	<% } %>
                    <% } %>
                    <input type="file" id="thumbnail" name="thumbnail" 
                    	   class="form-control" accept="image/*">
                    <div class="thumbnail-preview">
                        <% for(FestivalImage fi : fiList) { %>
                            <% if(fi.getFesImgThumb().equals("Y")) { %>
                                <div class="thumbnail-container">
                                    <img src="<%= contextPath + fi.getFesImgPath() + fi.getFesImgRename() %>" alt="썸네일 이미지">
                                    <p class="file-name"><%= fi.getFesImgName() %></p>
                                </div>
                            <% } %>
                        <% } %>
                    </div>

                    <!-- 추가 이미지 수정 영역 -->
                    <label>기타 이미지 수정<span class="small-text" style="color: red;"> ※ 기존 첨부파일은 개별 삭제하셔야 합니다.</span></label>
                   	<!-- 기존 추가 이미지가 있는 경우 -->
					<% if(!fiList.isEmpty()) { %>
                         <div class="image-preview" style="display: flex; gap: 10px; margin-top: 10px;">
	                        <% for(FestivalImage fi : fiList) { %>
	                            <% if(fi.getFesImgThumb().equals("N")) { %>
	                            	<div class="image-container">
	                                 	<img src="<%= contextPath + fi.getFesImgPath() + fi.getFesImgRename() %>" alt="추가 이미지"
	                                 		 style="max-width: 100px; max-height: 100px;">
	                                 	<button type="button" class="remove-btn" data-imgno="<%= fi.getFesImgNo() %>"
	                                 			data-imgrename="<%= fi.getFesImgRename() %>">X</button>
	                                   	<p class="file-name" style="text-align: center; font-size: 12px; margin-top: 5px; color: #333;"><%= fi.getFesImgName() %></p>
                                   	</div>
	                            <% } %>
	                        <% } %>
                         </div>
                    <% } %>
                    <div id="image-upload-area">
		                <div class="image-upload">
					        <input type="file" name="images01" class="form-control" accept="image/*"> <!-- 첫 번째 이미지는 images01로 설정 -->
					        <div class="image-preview" style="margin-top: 10px;"></div> <!-- 개별 미리보기 영역 -->
					    </div>
                    </div>
                    <button type="button" id="add-image-btn" class="btn btn-secondary">+</button>

                    <!-- 게시글 내용 -->
                    <label for="fesContent">게시글 내용(*)</label>
                    <textarea id="fesContent" name="fesContent" class="form-control" rows="10" style="resize: none;" required><%= f.getFesContent() %></textarea>

                    <!-- 버튼 영역 -->
                    <div class="d-flex justify-content-between mt-4">
                        <a href="<%= contextPath %>/detail.fe?fesNo=<%= f.getFesNo() %>" class="btn btn-secondary">뒤로가기</a>
                        <button type="submit" class="btn btn-primary">수정하기</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script>
	    // 삭제할 이미지 정보를 저장할 배열
        $(document).ready(function () {
            let imageIndex = 2; // 기존 추가 이미지의 마지막 인덱스 다음 번호로 시작
            
            // 썸네일 미리보기 기능
            $('#thumbnail').on('change', function () {
                const thumbnailPreview = $('.thumbnail-preview').empty();
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
                const fieldName = 'images' + String(imageIndex).padStart(2, '0');
                const newImageUpload = $(
                    '<div class="image-upload">' +
                        '<input type="file" name="' + fieldName + '" class="form-control" accept="image/*">' +
                        '<button type="button" class="remove-image-btn">X</button>' +
                        '<div class="image-preview" style="margin-top: 10px;"></div>' +
                    '</div>'
                );

                // 이미지 파일 선택 시 미리보기
                newImageUpload.find('input[type="file"]').on('change', function () {
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

                // X 버튼 클릭 시 필드 및 미리보기 삭제
                newImageUpload.find('.remove-image-btn').on('click', function () {
                    newImageUpload.remove();
                    updateImageFieldNames();
                });

                $('#image-upload-area').append(newImageUpload);
                imageIndex++;
            });

            // 입력 필드의 이름을 순서대로 다시 설정
            function updateImageFieldNames() {
                imageIndex = 1;
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
	        
	        // 썸네일 및 추가 이미지에서 삭제 버튼 클릭 시 실행되는 함수
	        function markForDeletion(imgNo, imgRename) {
	            const existingInput = $('#delete-' + imgNo);

	            if (existingInput.length === 0) {
	                // 이미지가 삭제 대기 목록에 없으면 추가
	                $('<input>').attr({
	                    type: 'hidden',
	                    name: 'deletedImages',
	                    id: 'delete-' + imgNo,
	                    value: imgNo + ',' + imgRename
	                }).appendTo('form');
	                alert("삭제 대기 목록에 추가되었습니다.");
	            } else {
	                // 이미지가 삭제 대기 목록에 있으면 제거
	                existingInput.remove();
	                alert("삭제 대기 목록에서 제거되었습니다.");
	            }
	        }

	        // remove-btn 클릭 시 삭제 대기 목록에 추가
	        $('.remove-btn').on('click', function () {
	            const imgNo = $(this).data('imgno');
	            const imgRename = $(this).data('imgrename');
	            markForDeletion(imgNo, imgRename); // 삭제 대기 목록에 추가

	            // 해당 이미지를 미리보기에서 숨김 처리
	            $(this).closest('.thumbnail-container, .image-container').hide();
	        });
        });
        
    </script>
	
	<!-- 하단바 -->
	<%@ include file="../common/footer.jsp" %>

</body>
</html>