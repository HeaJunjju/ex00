<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
 <c:set var="contextPath" value="${pageContext.request.contextPath}" />
 
<%@ include file="../myinclude/myheader.jsp" %>

<style> 
/* 파일 업로드 표시 역역에 대한 CSS */ 
.fileUploadResult { width: 100%; background-color: lightgrey; } 
.fileUploadResult ul { display: flex; flex-flow: row; justify-content: center; align-items: center; } 
.fileUploadResult ul li { list-style: none; padding: 5px; margin: 5px } 
.fileUploadResult ul li img { height: 30px; width: auto; max-height: 50px; overflow: hidden" } 
 
</style> 


		<div id="page-wrapper">
			<div class="row">
                <div class="col-lg-12">
                	<h3 class="page-header">Board-Register</h3>
                </div>
     		</div>
     		<div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h4>게시글 등록</h4>
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <form role="form" action="${contextPath}/myboard/register" method="post" > 
							 <div class="form-group"> 
							 <label>제목</label> <input class="form-control" name="btitle"> 
							 </div> 
							 
							 <div class="form-group"> 
							 <label>내용</label> <textarea class="form-control" rows="3" name="bcontent"></textarea> 
							 </div> 
							 
							 <div class="form-group"> 
							 <label>작성자</label> <input class="form-control" name="bwriter"> 
							 </div> 
							 <button type="submit" class="btn btn-primary">등록</button> 
							 <button type="button" data-oper="list" class="btn btn-warning" 
							 onClick="location.href='${contextPath}/myboard/list'">취소
							 </button> 
							</form>  
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row"> 
				 <div class="col-lg-12"> 
				 	<div class="panel panel-default"> 
				 	<div class="panel-heading">파일첨부</div> 
				 	<div class="panel-body"> 
				 		<div class="form-group uploadDiv"> 
				 			<input id="inputFile" type="file" name="uploadFiles" multiple><br> 
				 		</div> 
					 	<div class="form-group fileUploadResult"> 
					 		<ul> 
							<!-- 업로드 후 결과처리 로직이 표시될 영역 --> 
							</ul> 
					 	</div> 
				 	</div><!-- /.panel-body --> 
				 	</div><!-- /.panel --> 
				 </div><!-- /.col-lg-12 --> 
			</div><!-- /.row --> 
            
            
 		</div>
        <!-- /#page-wrapper -->
<%@ include file="../myinclude/myfooter.jsp" %>
 
 <script> 
	 //업로드 파일의 확장자 및 최대 파일 크기 검사 함수
	function checkUploadfile(fileName, fileSize) { 
	 
	 
		// 최대 허용크기(5MB)와 확장자에 대한 정규식 저장변수들 선언
		 
		var maxSizePermittedForUploadFile = 5242880; 
		 
		var regExpForFileExtention = /(.*)\.(exe|sh|zip|alz)$/i ; 
		 
		//최대 허용 크기(5MB) 제한 검사
		 
		if (fileSize >= maxSizePermittedForUploadFile) { 
		 	alert("업로드 파일의 제한된 크기(5MB)를 초과했습니다."); 
		 
			return false; 
		} 
		 
		 
		//업로드파일의 확장자 검사:
		 
		if (regExpForFileExtention.test(fileName)) { 
		 	alert("해당 종류(exe/sh/zip/alz)의 파일은 업로드 할 수 없습니다."); 
		 
			return false; 
		 } 
		 
		 return true; 
	 } 
	 
	//파일업로드
	 $(".uploadDiv #inputFile").on("change", function(e) {
	 
		//Ajax 파일 전송 시에 사용되는 클래스
		 
		var formData = new FormData(); 
		 
		 
		var inputFiles = $("input[name='uploadFiles']"); 
		 
		 
		//inputFiles에 저장된 파일들을 files 변수에 저장
		 
		//[0]은 첫번째 input 요소를 의미합니다.
		 
		var files = inputFiles[0].files; 
		 
		 
		//formData 객체에 파일추가
		 
		for(var i = 0; i < files.length ; i++) { 
		 
		 
		//파일 확장자 및 최대크기검사 함수 실행 if 문
		 
		if (!checkUploadfile(files[i].name, files[i].size)) { 
		 
		//파일제한사항을 충족하지 않으면, 업로드 중지
		 
		return false; 
		 } 
		 
		//uploadFiles 파라미터로 file 정보 추가
		 formData.append("uploadFiles", files[i]); 
		 } 
		 
		 
		//url의 컨트롤러에게 formData 객체를 POST 방식으로 전송.
		 $.ajax({ 
			 url: '${contextPath}/fileUploadAjaxAction', 
			 processData: false, //contentType에 설정된 형식으로 데이터처리 않함
			 contentType: false, //contentType을 지정않함
			 data: formData, 
			 type: 'POST', 
			 dataType: 'json', 
			 success: function(uploadResult){ 
			 alert("첨부파일의 업로드가 정상적으로 완료되었습니다..."); 
			 
			//파일이름이 선택된 기존 input을 초기화
			 $(".uploadDiv #inputFile").val(""); 
			 showUploadedFiles(uploadResult); //뒤에서 추가됨
		 	} 
		 })// End .ajax 
	}); //End click upload function 
	
	//Ajax: 업로드 결과 표시 함수
	// - 아이콘틀릭 시, 이미지가 아닌 파일 다운로드
	// - 썸네일클릭 시, 원본 이미지 표시
	//함수추가: 서버로부터 전달받은 JSON 데이터가 매개변수로 사용됨.
	function showUploadedFiles(jsonArrayUploadResult) { 
	 
		//서버로 부터 결과내용이 전달되지 않으면, 함수 종료.
		 
		if(!jsonArrayUploadResult || jsonArrayUploadResult.length == 0){ 
		 
			return ; 
		} 
		console.log(jsonArrayUploadResult); 
		 
		//ul 태그 변수화
		 
		var fileUploadResult = $(".fileUploadResult ul");  
		var str = ""; 
		 
		//전달받은 배열형식 데이터 각각에 대하여
		 $(jsonArrayUploadResult).each(function(i, obj) { 
		 
			//파일이 이미지가 아닌 경우
			 
			if (obj.fileType == "F") { 
			 
			 
				var calledPathFileName = encodeURIComponent(obj.uploadPath+"/"+ obj.uuid +"_"+obj.fileName); 
				 console.log(calledPathFileName); 
				 
				 
				//아이콘 이미지 및 파일이름(원본파일이름) 표시
				 str += "<li data-filename='" + obj.fileName + "' data-uploadpath='" + obj.uploadPath
				 + "' data-uuid='" + obj.uuid + "' data-filetype='" + obj.fileType + "'>"
				 + " <div>"
				 + " <span> "+ obj.fileName+"</span><br/>"
				 + " <img src='${contextPath}/resources/img/icon-attach.png'"
				 + " alt='No Icon' height='15px' width='15px'><br>"
				 
				//data-filename:경로포함 파일이름, data-filetype: 'F' 설정
				 + " <span data-filename='" + calledPathFileName + "' data-filetype='F'>"
				 + " <button>삭제</button>"
				 + " </span>"
				 + " </div>"
				 + "</li>"; 
			 } else if (obj.fileType == "I") { 
			 
				//경로명이 포함된 썸네일 파일이름 저장: encodeURIComponent로 처리
				 
				var calledPathThumbnail = encodeURIComponent(obj.uploadPath 
				 + "/s_" + obj.uuid + "_" + obj.fileName); 
				console.log(calledPathThumbnail); 
				 
				//썸네일이미지 및 파일이름 표시
				 str += "<li data-filename='" + obj.fileName + "' data-uploadpath='" + obj.uploadPath 
				 + "' data-uuid='" + obj.uuid + "' data-filetype='" + obj.fileType + "'>"
				 + " <div>"
				 + " <span> "+ obj.fileName+"</span><br>"
				 + " <img src='${contextPath}/displayThumbnailFile?fileName="+calledPathThumbnail+"'"
				 + " alt='No ThumbNail' height='50px' width='50px'><br>"
				 
				//data-filename:경로포함 파일이름, data-filetype: 'I' 설정
				 + " <span data-filename='"+calledPathThumbnail+"' data-filetype='I'>"
				 + " <button>삭제</button>"
				 + " </span>"
				 + " </div>"
				 + "</li>"; 
			 } 
		 }); 
		 
		//기존 페이지에 결과를 HTML로 추가
		 fileUploadResult.append(str); 
	 } 
	
	//파일 삭제:이벤트 위임을 이용하여, 서버에서 파일 삭제, 관련된 브라우저의 항목도 삭제.
	 $(".fileUploadResult").on("click","span", function(e){ 
	 
	 
		//this: span
		 
		var targetFileName = $(this).data("filename"); 
		 
		var targetFileType = $(this).data("filetype"); 
		 console.log(targetFileName); 
		 console.log(targetFileType); 
		 
		 
		//var parentLi = $(this).parent();
		 
		var targetLi = $(this).closest("li"); 
		 
		 $.ajax({ 
			 url: '${contextPath}/deleteUploadedFile', 
			 data: {fileName: targetFileName, fileType: targetFileType}, 
			 dataType:'text', 
			 type: 'POST', 
			 success: function(result){ 
			 
				if (result == "success"){ 
				 	alert("파일이 정상적으로 삭제되었습니다."); 
				 
					//이벤트 위임을 이용하여, 삭제된 파일의 항목을 브라우저의 HTML 문서에서 삭제합니다.
					 
					//$(this).parent().remove();(X)
					 
					//$(this).closest("li").remove();(X)
					 
					//parentLi.remove();
					 targetLi.remove(); 
				 
				 } else { 
				 	alert("오류: 파일 삭제 실패."); 
				 } 
			 } 
		 }); //End $.ajax
	 
	 }); 
	 
	//게시물+첨부파일 정보 등록
	 $("button[type='submit']").on("click", function(e){ 
	 
		//submit 버튼의 원래 동작 막음
		 e.preventDefault(); 
		 
		 
		var formObj = $("form[role='form']"); 
		 
		var strAttachFilesInputHidden = ""; 
		//업로드 결과의 li 요소 선택하여 각각에 대하여 다음을 처리
		 $(".fileUploadResult ul li").each(function(i, obj){ 
		 
		 
			var objLi = $(obj); 
			 strAttachFilesInputHidden 
			 += " <input type='hidden' name='attachFileList["+i+"].uuid' value='"+objLi.data("uuid")+"'>"
			 + " <input type='hidden' name='attachFileList["+i+"].uploadPath' value='"+objLi.data("uploadpath")+"'>"
			 + " <input type='hidden' name='attachFileList["+i+"].fileName' value='"+objLi.data("filename")+"'>"
			 + " <input type='hidden' name='attachFileList["+i+"].fileType' value='"+ objLi.data("filetype")+"'>" ; 
		 }); 
		 
		 alert(strAttachFilesInputHidden); //테스트 후, 주석처리할 것
		 formObj.append(strAttachFilesInputHidden); 
		 formObj.submit(); 
	 
	 }); 



</script> 
 
 