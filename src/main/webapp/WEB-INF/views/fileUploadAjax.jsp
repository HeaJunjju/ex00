<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>File Upload Ajax Page</title>
<%-- 파일 업로드 표시 역역에 대한 CSS --%>
 
<style>
.fileUploadResult { width: 100%; background-color: lightgrey; } 
.fileUploadResult ul { display: flex; flex-flow: row; justify-content: center; align-items: center; } 
.fileUploadResult ul li { list-style: none; padding: 5px; } 
.fileUploadResult ul li img { height: 50px; width: auto; max-height: 100px; overflow: hidden" } 
</style>
 
<%-- 다운로드 이미지 표시 영역에 대한 CSS --%>
 <style>
  
  
  
  
  
.bigImageWrapper { 
	position: absolute; 
	display: none; 
	justify-content: center; 
	align-items: center; 
	top:0%; 
	width: 100%; 
	height: 100%; 
	background-color: lightgray; 
	z-index: 100; 
 } 
 
.bigImage { 
	position: relative; 
	display:flex; 
	justify-content: center; 
	align-items: center; 
 } 
 
.bigImage img { 
	height: 100%; max-width: 100%; width: auto; overflow: hidden
 } 
</style>
 
 

</head>
<body>
 <h1>Upload With Ajax</h1> 
 <div class='bigImageWrapper'> 
	 <div class='bigImage'> 
	 <!-- 이미지파일이 표시되는 DIV --> 
	 </div> 
 </div>
 
 <div class="uploadDiv"> 
  <input id="inputFile" type="file" name="uploadFiles" multiple><br>
 </div> 
 <button id="btnFileUpload">File Upload With Ajax</button>
 <div class="fileUploadResult"> 
  <ul> 
   <%-- 업로드 후 결과처리 로직이 표시될 영역  --%> 
  </ul> 
 </div>
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
 
 <script>
 //업로드 파일의 확장자 및 최대 파일 크기 검사 함수
 function checkUploadfile(fileName, fileSize){
	// 확장자에 대한 정규식 및 최대 허용크기(1MB) 저장변수
	var maxSizePermittedForUploadFile = 1048576;
	var regExpForFileExtention = /(.*)\.(exe|sh|zip|alz)$/i;
	 //var regExpForFileExtention = new RegExp("(.*)\.(exe|sh|zip|alz)$", "i"); 
	 
	 //최대 허용 크기 제한 검사
	 if(fileSize >= maxSizePermittedForUploadFile){
		 alert("업로드 파일의 제한된 크기(1MB)를 초과했습니다.");
		 return false;
	 }
	 
	//업로드파일의 확장자 검사: 
	if(regExpForFileExtention.test(fileName)){
		alert("해당 종류(exe/sh/zip/alz)의 파일은 업로드할 수 없습니다.");
		return false;
	}
	return true;
 }
 
//Ajax: 이미지가 아닌 파일에 대한 화면처리(첨부아이콘과 파일이름 표시) 함수  
//서버로부터 전달받은 JSON 데이터가 매개변수로 사용됨. 
function showUploadedFiles(jsonArrayUploadResult){
	console.log(jsonArrayUploadResult);
	
	//ul 태그 변수화
	var fileUploadResult = $(".fileUploadResult ul");
	
	var str="";
	//전달받은 배열형식 데이터 각각에 대하여
	$(jsonArrayUploadResult).each(function(i, obj){
		//파일이 이미지가 아닌 경우
		if(obj.fileType=="F"){
			var calledPathFileName = encodeURIComponent(obj.uploadPath+"/"+ obj.uuid +"_"+obj.fileName); 
			 
			console.log(calledPathFileName); 

			//아이콘 이미지 및 파일이름(원본파일이름) 표시
			str += "<li>"
				 + " <a href='${contextPath}/fileDownloadAjax?fileName="+calledPathFileName+"'>"
				 +  "<img src='${contextPath}/resources/img/icon-attach.png'"      
				 +  "  alt='No Icon' height='20px' width='20px'><br>" 
			     +   obj.fileName
			     + "</a><br>"
			   //data-filename:경로포함 파일이름, data-filetype: 'F' 설정
				 + " <span data-filename='"+calledPathFileName+"' data-filetype='F'><button>삭제</button></span>" 
			     +  "</li>"; 
		}else if(obj.fileType=="I"){
			//이미지파일인 경우 파일이름만 표시(임시)
			 //str += "<li>" + obj.fileName + "</li>"; 
		//경로명이 포함된 썸네일 파일이름 저장: encodeURIComponent로 처리
 
		var calledPathThumbnail = encodeURIComponent(obj.uploadPath 
		 + "/s_" + obj.uuid + "_" + obj.fileName); 

			 
		console.log(calledPathThumbnail); 
		 
		var originPathImageFileName = obj.uploadPath+ "\\"+obj.uuid +"_"+obj.fileName; 
		 
		originPathImageFileName = originPathImageFileName.replace(new RegExp(/\\/g),"/"); 
 
		    //썸네일이미지 및 파일이름 표시 
		    //str += "<li><img src='/DisplayThumbnailFile?fileName="+fileCallPath+"'><li>";     
		var thumbnailCallPath = encodeURIComponent(obj.uploadPath 
				 + "/s_" + obj.uuid + "_" + obj.fileName); 
				 
				 //썸네일이미지 및 파일이름 표시
				 //str += "<li><img src='/DisplayThumbnailFile?fileName="+fileCallPath+"'><li>"; 
				 str += "<li>" 
				 + " <a href=\"javascript:showImage('"+originPathImageFileName+"')\">" 
				 + " <img src='${contextPath}/displayThumbnailFile?fileName="+thumbnailCallPath+"'" 
				 + " alt='No Icon' height='50px' width='50px'><br>" 
				 + obj.fileName 
				 + "</a><br>"
				//data-filename:경로포함 파일이름, data-filetype: 'I' 설정
				 + " <span data-filename='"+calledPathThumbnail+"' data-filetype='I'><button>삭제</button></span>" 
				 + "</li>"; 

		}
	});
	//기존 페이지에 결과를 HTML로 추가
	fileUploadResult.append(str);
}
 
//input 초기화를 위해 div 요소의 비어있는 input 요소를 복사해서저장함.  
var cloneInputFile = $(".uploadDiv").clone() ; 
 
  //파일업로드 처리
  $("#btnFileUpload").on("click", function(e){
	  //Ajax 파일 전송 시에 사용되는 클래스
	  var formData = new FormData();
	  //uploadFiles 이름의 input요소를 선택하여 변수에 저장
	  var inputFiles = $("input[name='uploadFiles']");
	  
	  console.log(inputFiles);
	//inputFiles에 저장된 파일들을 files 변수에 저장. 
	//[0]은 첫번째 input 요소를 의미. 단, input 요소가 하나만 있더라도 [0]을 명시해야 함.
	  var files = inputFiles[0].files;
	  
	  console.log(files);
	  
	  //formdata 객체에 파일 추가
	  for(var i=0;i<files.length;i++){
		//파일 확장자 및 최대크기검사 함수 실행 if 문(추가) 
		if(!checkUploadfile(files[i].name, files[i].size)){
			return false;
		}
		  
		  //uploadFiles 파라미터로 file 정보 추가
		  formData.append("uploadFiles", files[i]);
	  }
	  
	  //url키에 명시된 주소의 컨트롤러에게 formData객체를 POST 방식으로 전송.
	  $.ajax({
		  url: '${contextPath}/fileUploadAjaxAction',
		  processData: false,
		  contentType: false,
		  data: formData, 
		  type: 'POST',
		  dataType: 'json',
		  success: function(uploadResult){
			  alert("첨부파일의 업로드가 정상적으로 완료되었습니다...");
			  
			  //파일이름이 선택된 기존 input을 초기화 
			  $(".uploadDiv").html(cloneInputFile.html());
			  //$(".uploadDiv #inputFile").val("");//버전따라 실행이 안될 수 있으므로 복사한 것을 붙여넣는 것을 권장
			  showUploadedFiles(uploadResult);
		  }
	  })
  });
  
//이미지 표시
//display 속성의 flex 옵션을 이용하면, 
//이미지가 웹브라우저의 중앙에 표시됩니다.
function showImage(calledPathImagefileName){ 
 
	 $(".bigImageWrapper").css("display","flex").show(); 
	 
	 $(".bigImage") 
	 .html("<img src='${contextPath}/displayThumbnailFile?fileName="+encodeURI(calledPathImagefileName)+"'>") 
	 .animate({width:'100%', height: '100%'}, 1000); 
 } 
 
//DIV 클릭 이벤트처리: 클릭 시 1초 후에 이미지 사라짐.
 $(".bigImageWrapper").on("click", function(e){ 
 	$(".bigImage").animate({width:'0%', height: '0%'}, 1000); 
 
	//setTimeout(() => {$(this).hide();}, 1000);
 
	 setTimeout(function () { 
	 	$(".bigImageWrapper").hide(); 
	 }, 1000); 
 }); 
 
//파일 삭제:이벤트 위임을 이용하여, 서버에서 파일 삭제, 관련된 브라우저의 항목도 삭제.
 $(".fileUploadResult").on("click","span", function(e){ 
 
 
	//$(this).parent().remove();
 
	//this: span
 
	var targetFileName = $(this).data("filename"); 
 
	var targetFileType = $(this).data("filetype"); 
 	console.log(targetFileName); 
 	console.log(targetFileType); 
 
 
	//var targetLi = $(this).closest("li");
 
	var parentLi = $(this).parent(); 
 
 
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
				 
				//targetLi.remove();
				 parentLi.remove(); 
			 } else { 
			 	alert("오류: 파일 삭제 실패."); 
			 } 
		} 
	 }); //End $.ajax
 
 }); 

 </script>
</body>
</html>