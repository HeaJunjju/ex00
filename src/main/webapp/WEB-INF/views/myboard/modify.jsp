<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
 <c:set var="contextPath" value="${pageContext.request.contextPath}" />
 
<%@ include file="../myinclude/myheader.jsp" %>
		<div id="page-wrapper">
			<div class="row">
                <div class="col-lg-12">
                	<h3 class="page-header">User Board-Modify</h3>
                </div>
     		</div>
     		<div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h4>게시글 수정-삭제</h4> 
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                        <form id="frmModify" role="form" method="post">
							 <div class="form-group"> 
							 <label>글 번호</label> <input class="form-control" value='${board.bno}' name="bno" readonly="readonly" /> 
							 </div> 
							 
							 <div class="form-group"> 
							 <label>글 제목</label> <input class="form-control" value='<c:out value="${board.btitle}"/>' name="btitle"/> 
							 </div> 
							 
							 <div class="form-group"> 
							 <label>글 내용</label> <textarea class="form-control" rows="3" name="bcontent"><c:out value="${board.bcontent}"/></textarea>
							 </div> 
							 
							 <div class="form-group"> 
							 <label>작성자</label> 
							 <input class="form-control" value='<c:out value="${board.bwriter}"/>' 
							 name="bwriter" readonly="readonly"/> 
							 </div> 
							 
							 <div class="form-group"> 
							 <label>최종수정일</label> [등록일시: <fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" 
							 value="${board.bregDate}"/>] 
							 <input class="form-control" name="bmodDate" 
							 value='<fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" 
							 value="${board.bmodDate}"/>' 
							 disabled="disabled" /> 
							 </div> 
							 
							 <button type="button" id="btnModify" data-oper="modify" class="btn btn-default btn-frmModify" >수정</button> 
							 <button type="button" id="btnRemove" data-oper="remove" class="btn btn-danger btn-frmModify">삭제</button> 
							 <button type="button" id="btnList" data-oper="list" class="btn btn-info btn-frmModify">취소</button> 
							 
							 <%-- 추가 --%>
							<input type='hidden' name='pageNum' value='${myBoardPagingDTO.pageNum}'> 
							<input type='hidden' name='rowAmountPerPage' value='${myBoardPagingDTO.rowAmountPerPage}'> 
							<input type='hidden' name='scope' value='${myBoardPagingDTO.scope}'> 
 							<input type='hidden' name='keyword' value='${myBoardPagingDTO.keyword}'> 
 						</form>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <%-- 첨부파일 표시 --%>
			<div class="row"> 
			 <div class="col-lg-12"> 
			  <div class="panel panel-default"> 
			   <div class="panel-heading">파일첨부</div> 
			   <div class="panel-body" id="panelFileBody"> 
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
        
        <script> 
			//form의 수정/삭제/목록보기 버튼에 따른 동작 제어
			var frmModify = $("#frmModify"); 

			$('.btn-frmModify').on("click", function(e){ 
				//e.preventDefault(); //버튼 유형이 submit가 아니므로 설정할 필요 없음
				 var operation = $(this).data("oper"); //각 버튼의 data-xxx 속성에 설정된 값을 저장
				 
				 alert("operation: "+ operation); 
				 if(operation == "modify"){ //게시물 수정 요청
					var strFilesInputHidden = "";
					 
					//업로드 결과의 li 요소 선택하여 각각에 대하여 다음을 처리
					$(".fileUploadResult ul li").each(function(i, obj){ 
					 
						var objLi = $(obj); 
						 strFilesInputHidden 
							 += " <input type='hidden' name='attachFileList["+i+"].uuid' value='"+objLi.data("uuid")+"'>" 
							 + " <input type='hidden' name='attachFileList["+i+"].uploadPath' value='"+objLi.data("uploadpath")+"'>" 
							 + " <input type='hidden' name='attachFileList["+i+"].fileName' value='"+objLi.data("filename")+"'>" 
							 + " <input type='hidden' name='attachFileList["+i+"].fileType' value='"+ objLi.data("filetype")+"'>" ; 
					 }); 
					 console.log(strFilesInputHidden);//테스트 후, 주석처리할 것
					 frmModify.append(strFilesInputHidden); //form에 추가

					//.attr은 jquery 함수
					//id가 frmModify인 애의 action속성에 아래와 같은 값을 지정해라
					//form 태그의 action속성 주는 방법
				 	frmModify.attr("action", "${contextPath}/myboard/modify"); 
				 } else if(operation == "remove"){ //게시물 삭제 요청
				 	//frmModify.attr("action", "${contextPath}/myboard/delete");
					frmModify.attr("action", "${contextPath}/myboard/remove");
				 } else if(operation == "list"){ //게시물 목록 화면 요청
				 	//frmModify.attr("action","${contextPath}/myboard/list").attr("method","get"); 
				 	//frmModify.empty();
					var pageNumInput = $("input[name='pageNum']").clone(); 
					var rowAmountInput = $("input[name='rowAmountPerPage']").clone(); 
					var scopeInput = $("input[name='scope']").clone(); 				 
					var keywordInput = $("input[name='keyword']").clone(); 
 
					frmModify.empty(); //form의 모든 input을 삭제
					  
					frmModify.attr("action","${contextPath}/myboard/list").attr("method","get"); 
					frmModify.append(pageNumInput); 
					frmModify.append(rowAmountInput); 
					frmModify.append(scopeInput); 
					frmModify.append(keywordInput); 
				 } 
				 frmModify.submit() ; //요청 전송
			}); 
			
			var bnoValue = '<c:out value="${board.bno}"/>'; 
			
			//첨부파일 정보 표시 함수
			function showUploadedFiles(uploadResult){
				//서버로부터 결과내용이 전달되지 않으면, 함수종료.
				if(!uploadResult || uploadResult.length==0){
					return;
				}
				//ul 태그 변수화
				var fileUploadResult = $(".fileUploadResult ul");
				var str = "";
				
				$(uploadResult).each(function(i,obj){
					if(obj.fileType == "F"){
						//업로드 된 파일이름(uuid 포함된 이름, 삭제 시 필요): encodeURIComponent로 처리
						 
						var calledPathFileName = encodeURIComponent(obj.uploadPath + "/" + obj.uuid + "_" + obj.fileName); 
						 console.log("encodeURIComponent 처리된 파일이름: " + calledPathFileName); 

						//아이콘 이미지 및 uuid 없는 파일이름 표시
						 str += "<li data-filename='" + obj.fileName + "' data-uploadpath='" + obj.uploadPath + "'"
							 + " data-uuid='" + obj.uuid + "' data-filetype='F' style='height:25px;'>"
							 + " <img src='${contextPath}/resources/img/icon-attach.png'"
							 + " alt='No Icon' style='height: 18px; width: 18px;'>&nbsp;&nbsp;"
							 + obj.fileName 
							 + " &nbsp;<button type='button' class='btn btn-danger btn-xs' " 
							 + " data-filename='" + calledPathFileName + "' data-filetype='F'>X</button>" 
							 + "</li>"; 
					}else if(obj.fileType=="I"){
						//업로드 된 썸네일 파일이름(uuid 포함된 이름, 썸네일 표시, 삭제 시 필요): encodeURIComponent로 처리
						var thumbnailFilePath = 
						 encodeURIComponent(obj.uploadPath + "/s_" + obj.uuid + "_" + obj.fileName); 
						 console.log("encodeURIComponent 처리된 썸네일파일이름: " + thumbnailFilePath); 
						 
						//업로드 된 파일이름(uuid 포함된 이름, 삭제 시 필요)
						 
						//var originPathImageFileName = obj.uploadPath + "\\" + obj.uuid + "_" + obj.fileName;
						 
						//originPathImageFileName = originPathImageFileName.replace(new RegExp(/\\/g),"/");
						 
						//썸네일 이미지 및 uuid 없는 파일이름 표시
						 str += "<li data-filename='" + obj.fileName + "' data-uploadpath='" + obj.uploadPath + "'"
							 + " data-uuid='" + obj.uuid + "' data-filetype='I' style='height:25px;'>" 
							 + " <img src='${contextPath}/displayThumbnailFile?fileName=" + thumbnailFilePath + "'"
							 + " alt='No Icon' style='height: 18px; width: 18px;'>&nbsp;&nbsp;"
							 + obj.fileName 
							 
							//data-filename:경로포함 파일이름, data-filetype: 'I' 설정
							// + " <span data-filename='" + thumbnailFilePath + "' data-filetype='I'>[삭제]</span>"  아래의 버튼으로 교체
							 + " &nbsp;<button type='button' class='btn btn-danger btn-xs'" 
							 + " data-filename='" + thumbnailFilePath + "' data-filetype='I'>X</button>" 
							 + "</li>"; 
					}
				});
				//기존 페이지에 결과를 HTML로 추가
				fileUploadResult.append(str); 

			}
			
			//업로드 파일의 확장자 및 최대 파일 크기 검사 함수]
			function checkUploadfile(fileName, fileSize){
				// 확장자에 대한 정규식 및 최대 허용크기(1MB) 저장변수
				var maxSizePermittedForUploadFile = 5242880; 
				var regExpForFileExtention = /(.*)\.(exe|sh|zip|alz)$/i ; 
				
				//최대 허용 크기 제한 검사
				if (fileSize >= maxSizePermittedForUploadFile) { 
				 	alert("업로드 파일의 제한된 크기(5MB)를 초과했습니다."); 
				 
					return false; 
				} 
				//업로드파일의 확장자 검사:
				if (regExpForFileExtention.test(fileName)) { 
				 	alert("해당 종류(exe/sh/zip/alz)의 파일은 업로드할 수 없습니다."); 
				 
					return false; 
				}  
				return true; 
			}
			
			//빈 input 복사(input 초기화 시 사용됨)
			var cloneInputFile = $(".uploadDiv").clone() ; 
			//파일 업로드 처리: 버튼 클릭이 아닌, input의 내용이 바뀌면 업로드가 자동으로 수행되도록 수정
			//업로드 후, .uploadDive가 복사된 것으로 교체되어 input이 초기화되므로, 파일 수정이 되도록 이벤트 위임 방식을 사용
			$("#panelFileBody").on("change","input[type='file']", function(e){
				//$("input[type='file']").on("change", function(e) { //초기화를 val("")을 이용하는 경우,

				//Ajax 파일 전송 시에 사용되는 클래스
				var formData = new FormData(); 
				
				//uploadFiles 이름의 file 유형 input 요소를 변수에 저장
				var inputFiles = $("input[name='uploadFiles']"); 
				 console.log(inputFiles); 
				 
				//inputFiles에 저장된 파일들을 files 변수에 저장.
				//inputFiles[0]은 첫번째 input 요소를 의미. 단, input 요소가 하나만 있더라도 [0]을 명시해야 함.
				var files = inputFiles[0].files; 
				 console.log(files); 
				 
				//formdata 객체에파일추가
				for(var i = 0; i < files.length ; i++) { 
					//파일 확장자 및 최대크기검사 함수 실행 if 문(추가)
					if (!checkUploadfile(files[i].name, files[i].size)) { 
						return false; 
					} 
					 
					//uploadFiles 파라미터로 file 정보 추가
					formData.append("uploadFiles", files[i]); 
				 } 
				
				//url 키에 명시된 주소의 컨트롤러에게 formData 객체를 POST 방식으로 전송
				$.ajax({
					url: '${contextPath}/fileUploadAjaxAction',
					processData: false,
					contentType: false,
					data: formData, 
					type: 'post',
					dataType: 'json',
					success: function(uploadResult){
						//alert("첨부파일의 업로드가 정상적으로 완료되었습니다...");
						//파일이름이 선택된 기존 input을 초기화
						 $(".uploadDiv").html(cloneInputFile.html()); 
						//방법2: 브라우저 버전따라 실행이 않될 수 있으므로 복사한 것을 붙여넎는 것을 권장
						//$(".uploadDiv #inputFile").val("");
						showUploadedFiles(uploadResult); 
					}
				})

			});
				
			//파일 삭제(수정화면): 브라우저 표시 항목만 삭제.
			//$(".fileUploadResult").on("click","li span", function(e){
			$(".fileUploadResult").on("click","button", function(e){ 
			 
				//this: span 또는 button
				 console.log("파일삭제(화면에서 항목만 삭제)"); 
				 
				 
				if (confirm("이 파일을 삭제하시겠습니까?")){ 
				 
					var targetLi = $(this).closest("li"); 
					 targetLi.remove(); 
					 alert("파일이 삭제되었습니다"); 
				 } else { 
				 	alert("파일 삭제가 취소되었습니다.");
				 } 
			}); 
			
			$(document).ready(function(){
				$.ajax({
					url: '${contextPath}/myboard/getAttachList',
					data: {bno:bnoValue},
					type: 'get',
					dataType: 'json',
					success: function(fileList){
						showUploadedFiles(fileList);
					}
				})
			});

		</script> 
        
<%@ include file="../myinclude/myfooter.jsp" %>
 