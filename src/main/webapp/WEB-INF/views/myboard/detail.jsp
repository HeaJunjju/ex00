<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<%@ include file="../myinclude/myheader.jsp" %>
<style> 
	.txtBoxCmt, .txtBoxComment { 
	 overflow: hidden; resize: vertical; min-height: 100px; color: black; 
	} 
	/* 첨부파일 목록 표시 영역에 대한 CSS */
 
.fileUploadResult { width: 90%; background-color: lightgrey; margin: auto;} 
 
.fileUploadResult ul { display: flex; flex-flow: row; justify-content: center; align-items: center; } 
 
.fileUploadResult ul li { list-style: none; padding: 5px; align-content: center; text-align: center;} 
 
.fileUploadResult ul li img { height: 50px; width: auto; max-height: 100px; overflow: hidden; } 
	
	/* 다운로드 이미지 표시 영역에 대한 CSS */
 
.bigImageWrapper { 
position: absolute; 
 
display: none; 
 
justify-content: center; 
 
align-items: center; 
 
top:0%; 
 
width: 90%; 
 
height: 90%; 
 
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
 
height: 100%; max-width: 100%; width: auto; overflow: hidden; 
 } 

</style> 
<!-- favicon.ico 404에러시 코드 제일 위(또는 헤드 태그 안에)넣으면 에러 사라짐 -->
<link rel="shortcut icon" href="#">
<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <h3 class="page-header" style="white-space: nowrap;">Board - Detail: <c:out value="${board.bno}"/>번 게시물</h3>
        </div>
    </div>
<%-- 게시물 상세 표시 시작 --%>    
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading"> 
                    <div class="row">
                        <div class="col-md-3" style="white-space: nowrap; height: 45px; padding-top:11px;">
                            <strong style="font-size:18px;">${board.bwriter}님 작성</strong>
                        </div>
                        <div class="col-md-3" style="white-space: nowrap; height: 45px; padding-top:16px;">
                            <span class="text-primary" style="font-size: smaller; height: 45px; padding-top: 19px;">
                                <span>
                                    <span>등록일:&nbsp;</span>
                                    <strong><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${board.bregDate}"/></strong>
                                    <span>&nbsp;&nbsp;</span>                                   
                                </span>
                                <span>조회수:&nbsp;<strong><c:out value="${board.bviewsCnt}"/></strong></span>
                            </span>
                        </div>
                        <div class="col-md-6" style="height: 45px; padding-top:6px;"><!-- vertical-align: middle; -->
                            <div class="button-group pull-right">
                                <button type="button" id="btnToModify" data-oper="modify" class="btn btn-primary"><span>수정</span></button>
                                <button type="button" id="btnToList" data-oper="list" class="btn btn-info"><span>목록</span></button>
                            </div>
                        </div>
                    </div>
                </div><!-- /.panel-heading -->
                
                <div class="panel-body form-horizontal">
                    <div class="form-group">
                        <label class="col-sm-1 control-label" style="white-space: nowrap;">글제목</label>
                        <div class="col-sm-11">
                            <input class="form-control" value='<c:out value="${board.btitle}"/>' name="btitle" readonly="readonly"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-1 control-label" style="white-space: nowrap;">글내용</label>
                        <%-- <textarea>와 </textarea>는 한 줄에 작성되어야 필요없는 공백이 포함되지 않음 --%>
                        <div class="col-sm-11">
                            <textarea class="form-control" rows="3" name="bcontent" style="resize: none;" 
                                      readonly="readonly"><c:out value="${board.bcontent}"/></textarea>
                        </div>
                    </div>
                </div><!-- /.panel-body -->
            </div><!-- /.panel -->
        </div><!-- /.col-lg-12 -->
    </div><!-- /.row -->

    <form id="frmSendValue"><!-- 폼을 추가 -->
        <input type='hidden' name='bno' id="bno" value='<c:out value="${board.bno}"/>'>
        <input type='hidden' name='pageNum' value='${myBoardPagingDTO.pageNum}'>
        <input type='hidden' name='rowAmountPerPage' value='${myBoardPagingDTO.rowAmountPerPage}'>
        <input type='hidden' name='scope' value='${myBoardPagingDTO.scope}'>
        <input type='hidden' name='keyword' value='${myBoardPagingDTO.keyword}'>
    </form>
    <%-- 게시물 상세 표시 끝 --%>
    
    <div class="row"> 
	 <div class="col-lg-12"> 
		 <div class="panel panel-default"> 
			 <div class="panel-heading">파일첨부</div> 
			 <div class="panel-body"> 
				 <div class='form-group bigImageWrapper'> 
					 <div class='bigImage'> 
					 <%-- 이미지파일이 표시되는 DIV --%> 
					 </div> 
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
    
    <%-- 댓글 화면 표시 시작, 붙여넣기 후, 아래의 style 태그 내용은 <div id="page-wrapper"> 태그 위에 옮겨놓을 것 --%> 
	
	<div class="row"> 
	 <div class="col-lg-12"> 
	  <div class="panel panel-default" > 
	   <div class="panel-heading"> 
		  <p style="margin-bottom: 0px; font-size: 16px;"> 
			  <strong style="padding-top: 2px;"> 
				  <span>댓글&nbsp;<c:out value="${board.breplyCnt}"/>개</span> 
				  <span>&nbsp;</span> 
				  <button type="button" id="btnChgCmtReg" class="btn btn-info btn-sm">댓글 작성</button> 
				  <button type="button" id="btnRegCmt" class="btn btn-warning btn-sm" style="display:none">댓글 등록</button> 
				  <button type="button" id="btnCancelRegCmt" class="btn btn-warning btn-sm" style="display:none">취소</button> 
			  </strong> 
		  </p> 
	   </div> <!-- /.panel-heading -->
	   <div class="panel-body"> 
		   <!-- 댓글 입력창 시작 -->
		   <div class="form-group" style="margin-bottom: 5px;"> 
			   <textarea class="form-control txtBoxCmt" name="rcontent" 
			   placeholder="댓글 작성 시 상대방에 대한 배려와 책임을 담아 주세요.&#10;댓글작성을 원하시면,댓글 작성 버튼을 클릭해주세요." 
			   readonly="readonly" 
			   ></textarea> 
		   </div> 
		   <hr style="margin-top: 10px; margin-bottom: 10px;"> 
		   <!-- 댓글 입력창 끝 --> 
		   <ul class="chat"> 
		   <!-- 댓글 목록 표시 영역 --> 
		   
		   	<li class="left clearfix commentLi" data-bno="123456" data-rno="12"> 
			 <div> 
				 <div> 
					<span class="header info-rwriter"> 
					 <strong class="primary-font">user00</strong> 
					 <span>&nbsp;</span> 
					 <small class="text-muted">2018-01-01 13:13</small> 
					</span> 
				 	<p>앞으로 사용할 댓글 표시 기본 템플릿입니다.</p> 
				 </div> 
				 <div class="btnsComment" style="margin-bottom:10px"> 
					 <button type="button" style="display:in-block" class="btn btn-primary btn-xs btnChgReg">답글 작성</button> 
					 <button type="button" style="display:none" class="btn btn-warning btn-xs btnRegCmt">답글 등록</button> 
					 <hr class="txtBoxCmtHr" style="margin-top:10px; margin-bottom:10px"> 
					 <textarea class="form-control txtBoxCmtMod" name="rcontent" style="margin-bottom:10px" 
					 placeholder="답글 작성 시 상대방에 대한 배려와 책임을 담아 주세요.&#10;답글작성을 원하시면,답글 작성
					 
					버튼을 클릭해주세
					요." 
					 ></textarea> 
				 </div> 
			 </div> 
			</li> 
		   </ul> 
	   </div><!-- /.panel-body --> 
	   <div class="panel-footer" id="showCmtPagingNums"> 
	   <%-- 댓글 목록의 페이징 번호 표시 영역 --%> 
	   </div> 
	  </div><!-- /.panel -->
	 </div><!-- .col-lg-12 --> 
	</div><!-- .row : 댓글 화면 표시 끝 --> 
	<!-- 댓글 페이징 데이터 저장 form --> 
	<form id="frmCmtPagingValue"> 
	 <input type='hidden' name='pageNum' value='' /> 
	 <input type='hidden' name='rowAmountPerPage' value='' /> 
	</form> 
	 
</div><!-- /#page-wrapper -->



<script>


var frmSendValue = $("#frmSendValue");

//게시물 수정 페이지로 이동: 폼의 값을 전송해서 이동하는 형태로 변경
$("#btnToModify").on("click", function(){
	//location.href='${contextPath}/myboard/modify?bno=<c:out value="${board.bno}"/>';  //주석처리
	frmSendValue.attr("action", "${contextPath}/myboard/modify");
	frmSendValue.attr("method", "get");
	frmSendValue.submit();
})

//게시물 목록 페이지로 이동: 폼의 값을 전송해서 이동하는 형태로 변경
$("#btnToList").on("click", function(){
	//location.href="${contextPath}/myboard/myBoardList";  //  주석처리
	frmSendValue.find("#bno").remove();//  목록화면 이동 시, bno 값 삭제
	frmSendValue.attr("action", "${contextPath}/myboard/list");
	frmSendValue.attr("method", "get");
	frmSendValue.submit();
})
</script>

<script type="text/javascript" src="${contextPath}/resources/js/mycomment.js"></script>

<script>
var bnoValue = '<c:out value="${board.bno}"/>';

var commentUL = $(".chat");
var frmCmtPagingValue = $("#frmCmtPagingValue");

//댓글 목록표시
function showCmtList(page){
	myCommentClsr.getCmtList(
		{bno: bnoValue, page: page||1},
		function(replyPagingCreator){//ajax에서 실행할 callback 함수
			console.log("서버로부터 전달된 pageNum(replyPagingCreator.myRelyPaging.pageNum): "
					+ replyPagingCreator.myReplyPaging.pageNum);
			frmCmtPagingValue.find("input[name='pageNum']").val(replyPagingCreator.myReplyPaging.pageNum);
			console.log("폼에 저장된 페이징번호 pageNum(): "
			 		+ frmCmtPagingValue.find("input[name='pageNum']").val()); 
			var str="";
			
			//댓글이 없으면 replyList가 null -> false -> 부정 -> true, 아래의 for문 실행 안됨.
			if(!replyPagingCreator.replyList){
				str += '<li class="left clearfix commentLi"'
					+  'style="text-align: center; background-color: ligthCyan;'
					+  'height: 35px;margin-bottom: 0px;padding-bottom:0px;'
					+  'padding-top:6px;border: 1px dotted;">'
					+  '<strong>등록된 댓글이 없습니다.</strong></li>';
				commentUL.html(str);
				return;
			}
			
			//답글 표시 for문(<ul></ul>내에 아래의 li 요소들이 for문에 의해서 생성되어 표시됨)
			for(var i=0, len=replyPagingCreator.replyList.length;i<len;i++){
				str +='<li class="left clearfix commentLi" data-bno="' + bnoValue 
				 + '" data-rno="'+replyPagingCreator.replyList[i].rno+'">'
				 + ' <div>'; 
				//댓글에 대한 답글 들여쓰기
				if(replyPagingCreator.replyList[i].lvl==1){//댓글 들여쓰기 안함
					str+='		<div>';
				}else if(replyPagingCreator.replyList[i].lvl==2){//답글 들여쓰기
					str+=' 		<div style="padding-left:25px">'; 
				}else if(replyPagingCreator.replyList[i].lvl==3){//답글의 답글 들여쓰기
					str+=' 		<div style="padding-left:50px">'; 
				}else if(replyPagingCreator.replyList[i].lvl==4){//답글의 답글 들여쓰기
					str+=' 		<div style="padding-left:75px">'; 
				}else{//답글의 답글 레벨이 5이상이면 동일한 들여쓰기
					str+=' 		<div style="padding-left:100px">'; 
				}
				str +=' <span class="header info-rwriter">'
					 + ' <strong class="primary-font">'; 
				if(replyPagingCreator.replyList[i].lvl>1){
					str +=' <i class="fa fa-reply fa-fw"></i>&nbsp;'; 
				}
				str += replyPagingCreator.replyList[i].rwriter 
				 + ' </strong>'
				 + ' <span>&nbsp;</span>'
				 + ' <small class="text-muted">'
//				 + replyPagingCreator.replyList[i].rmodDate //수정날짜가 정수값으로 표시
				 + '  수정일: '+ myCommentClsr.showDatetime(replyPagingCreator.replyList[i].rmodDate) 
				 + ' </small>'; 
				if(replyPagingCreator.replyList[i].lvl>1){
					 str +=' <small>&nbsp; 답글</small>'; 
				}
				str +=' </span>'
					 + ' <p data-bno=' + replyPagingCreator.replyList[i].bno 
					 + ' data-rno=' + replyPagingCreator.replyList[i].rno 
					 + ' data-rwriter=' + replyPagingCreator.replyList[i].rwriter 
					 + ' >'
					 + replyPagingCreator.replyList[i].rcontent + '</p>'
					 + ' </div>'; 
				str +=' <div class="btnsReply" style="margin-bottom:10px">'
					 + ' <button type="button" style="display:in-block" class="btn btn-primary btn-xs btnChgReplyReg"'
					 + ' ><span>답글 작성</span></button>'
					 + ' </div>'; 
				str +=' </div>'
					 + '</li>'; 
			}
			//UL 태그 내에 HTML 내용 표시
			commentUL.html(str); //html 사용 시, 새로운 내용으로 교체.
			//commentUL.append(str); //append 사용 시, 기존 내용 밑에 새로운 내용 추가 (페이징 대신 사용).

			//페이징번호 표시 함수 호출
			 showCmtPagingNums(replyPagingCreator.myReplyPaging.pageNum, 
			 replyPagingCreator.startPagingNum, 
			 replyPagingCreator.endPagingNum, 
			 replyPagingCreator.prev, 
			 replyPagingCreator.next, 
			 replyPagingCreator.lastPageNum);
		}
	);
}

//댓글 목록에 표시할 페이징번호 생성 함수: replyPagingCreator로 부터 받아온 값들을 이용
function showCmtPagingNums(pageNum, startPagingNum, endPagingNum, prev, next, lastPageNum){
	if(endPagingNum>=lastPageNum){
		endPagingNum = lastPageNum;
	}
	
	var str = "<div class='text-center'>"
		 + " <ul class='pagination pagination-sm'>"; 

	//맨 앞으로
	if(prev){
		str +=" <li class='page-item'>"
			 + " <a class='page-link' href='"+1+"'>"
			 + " <span aria-hidden='true'>&laquo;</span>"
			 + " </a>"
			 + " </li>"; 
	}
	//이전 페이지
	if(prev){ 
		 str +=" <li class='page-item'>"
		 + " <a class='page-link' href='"+(startPagingNum -1)+"'>이전</a>"
		 + " </li>"; 
	} 

	//선택된 페이지 번호 Bootstrap의 색상표시
	for(var i=startPagingNum;i<=endPagingNum;i++){
		//active는 Bootstrap클래스 이름
		var active = ((pageNum==i)?"active":"");
		
		str +=" <li class='page-item "+active+"'>"
		 + " <a class='page-link' href='"+i+"'>"+i+"</a>"
		 + " </li>"; 
	}
	
	if(next){ 
		 str +=" <li class='page-item'>"
		 + " <a class='page-link' href='"+(endPagingNum + 1)+"'>다음</a>"
		 + " </li>"; 
	}
	
	//맨 마지막으로
	if(next){ 
		 str +=" <li class='page-item'>"
		 + " <a class='page-link' href='"+(lastPageNum)+"'>&raquo;</a>"
		 + " </li>"; 
	} 
	
	str +=" </ul>"
		 + "</div>"; 

	$("#showCmtPagingNums").html(str); //#showCmtPagingNums div에 표시

}

//선택된 페이징 번호의

//게시물목록 가져오는 함수: 이벤트전파이용
$("#showCmtPagingNums").on("click", "ul li a", function(e){
	e.preventDefault();
	
	var targetPageNum = $(this).attr("href");
	console.log("targetPageNum: "+targetPageNum);
	
	showCmtList(targetPageNum);
});

function chgBeforeCmtBtn(){
	
	$("#btnChgCmtReg").attr("style","display:in-block");
	$("#btnRegCmt").attr("style","display: none");
	$("#btnCancelRegCmt").attr("style", "display:none"); 
	$(".txtBoxCmt").val(""); 
	$(".txtBoxCmt").attr("readonly", true);
}

<%--댓글 작성 버튼 클릭 - 댓글 등록 버튼으로 변경, 댓글 입력창 활성화--%>
$("#btnChgCmtReg").on("click", function(){ 
	chgBeforeCmtRepBtns();
	chgBeforeCmtBtn(); 
	chgBeforeReplyBtn();
	 
	 $(this).attr("style", "display:none"); 
	 $("#btnRegCmt").attr("style", "display:in-block;margin-right:2px"); 
	 $("#btnCancelRegCmt").attr("style", "display:in-block"); 
	 $(".txtBoxCmt").attr("readonly", false); 
}); 
<%--댓글등록 취소 클릭 --%>
$("#btnCancelRegCmt").on("click", function(){ 
	 
	if(!confirm("댓글 입력을 취소하시겠습니까?")){ 
	 
	return ; 
	 } 
	 chgBeforeCmtBtn(); 
}); 
 
<%--댓글등록 버튼 클릭 이벤트 처리 --%>
$("#btnRegCmt").on("click", function(){ 
	 var loginUser = "user9"; 
	 
	var txtBoxCmt = $(".txtBoxCmt").val(); 
	 
	var comment = { bno: bnoValue, 
	 rcontent: txtBoxCmt, 
	 rwriter: loginUser}; 
	 
	 console.log("댓글등록: 서버전송 객체내용: " + comment); 
	 
	 myCommentClsr.registerCmt( 
	 comment, 
	 
		function(serverResult){ 
			 $(".txtBoxCmt").val(""); 
			 chgBeforeCmtBtn(); 
			 
			 alert("댓글이 등록되었습니다"); 
			 showCmtList(-1); <%--댓글이 추가된 맨 마지막 페이지 표시--%>
		 } 
	); 
});

<%--답글 관련 화면 상태 초기화--%>
function chgBeforeReplyBtn(){ 
 $(".btnRegReply").remove(); 
 $(".btnCancelRegReply").remove(); 
 $(".txtBoxReply").remove(); 
 $(".btnChgReplyReg").attr("style", "display:in-block"); 
} 

<%--답글 작성 버튼 클릭 이벤트--%>
<%--JSP 코드에 없는 생성된 요소, 이벤트 전파 사용 --%>
$(".chat").on("click", ".commentLi .btnChgReplyReg" ,function(){ 
 $("p").attr("style", "display:in-block;"); 
 
 chgBeforeCmtBtn(); 
 chgBeforeReplyBtn(); 
 chgBeforeCmtRepBtns();
 
 var strTxtBoxReply = 
 
	"<textarea class='form-control txtBoxReply' name='rcontent' style='margin-bottom:10px;'"
	 + " placeholder='답글 작성 시 상대방에 대한 배려와 책임을 담아 주세요.&#10;답글작성을 원하시면,답글 작성 버튼을 클릭해주세요.'"
	 + "></textarea>"
	 + "<button type='button' class='btn btn-warning btn-xs btnRegReply'>답글 등록</button>"
	 + "<button type='button' class='btn btn-danger btn-xs btnCancelRegReply' style='margin-left:4px;'>취소</button>"; 
 
 $(this).after(strTxtBoxReply); 
 $(this).attr("style", "display:none"); 
});
<%--답글등록 취소 클릭--%>
$(".chat").on("click", ".commentLi .btnCancelRegReply" ,function(){ 
 
if(!confirm("답글 입력을 취소하시겠습니까?")){ 
 
return ; 
 } 
 chgBeforeReplyBtn(); 
}); 
<%--답글 등록 버튼 클릭 이벤트 처리: 답글이 달린 댓글이 있는 페이지 표시--%>
$(".chat").on("click", ".commentLi .btnRegReply" ,function(){ 
 
var loginUser = "test8"; 
var pageNum = frmCmtPagingValue.find("input[name='pageNum']").val(); 
console.log("답글 추가가 발생된 댓글 페이지 번호: "+ pageNum); 


var txtBoxReply = $(this).prev().val(); 
console.log("txtBoxReply: " + txtBoxReply); 


var prnoVal = $(this).closest("li").data("rno"); 
console.log("prnoVal: " + prnoVal); 


var reply = { bno: bnoValue, 
rcontent: txtBoxReply, 
rwriter: loginUser, 
prno: prnoVal }; 

console.log("답글등록: 서버전송 객체내용: " + reply); 

myCommentClsr.registerReply( 
reply, 

function(serverResult){ 
alert("답글이 등록되었습니다"); 
showCmtList(pageNum);<%--댓글이 추가된

페이지 표시--%>
} 
); 
}) 

<%--댓글/답글 수정-삭제-취소-입력창 삭제 함수--%>
function chgBeforeCmtRepBtns(){ 
 $("p").attr("style","display:in-block;"); 
 
 $(".btnModCmt").remove(); 
 $(".btnDelCmt").remove(); 
 $(".btnCancelCmt").remove(); 
 $(".txtBoxMod").remove(); 
}

<%--댓글-답글 수정/삭제 화면 요소 표시: p 태그 클릭 이벤트 --%>
$(".chat").on("click", ".commentLi p", function(){ 
 
 chgBeforeCmtBtn();<%--댓글 등록 상태 초기화--%>
 chgBeforeReplyBtn()<%--다른 답글 등록 상태 초기화--%>
 chgBeforeCmtRepBtns(); <%--다른 답글/댓글 수정 상태 초기화--%>
 $(this).parents("li").find(".btnChgReplyReg").attr("style", "display:none"); 
 
var rcontent = $(this).text(); 
 console.log("선택된 댓글내용: " + rcontent); 
 
 
var strTxtBoxReply = 
 
"<textarea class='form-control txtBoxMod' name='rcontent' style='margin-bottom:10px;'"
 + " placeholder='답글 작성 시 상대방에 대한 배려와 책임을 담아 주세요.&#10;답글작성을 원하시면,답글 작성 버튼을 클릭해주세요.'"
 + "></textarea>"
 + "<button type='button' class='btn btn-warning btn-sm btnModCmt'>수정</button> "
 + "<button type='button' class='btn btn-danger btn-sm btnDelCmt'>삭제</button>"
 + "<button type='button' class='btn btn-info btn-sm btnCancelCmt' style='margin-left: 4px;'>취소</button>"; 
 
 $(this).after(strTxtBoxReply); 
 $(".txtBoxMod").val(rcontent); 
 $(this).attr("style", "display:none"); 
}); 
<%--댓글-답글 수정/삭제의 취소 버튼 클릭 이벤트 --%>
 
$(".chat").on("click", ".commentLi .btnCancelCmt", function(){ 
 chgBeforeCmtBtn(); 
 chgBeforeReplyBtn() 
 chgBeforeCmtRepBtns() 
}); 
<%-- 댓글-답글 수정 처리: 수정 버튼 클릭 이벤트 --%>
$(".chat").on("click", ".commentLi .btnModCmt", function(){ 
 
<%--작성자 변수에 저장--%>
 
 
var rwriterVal = $(this).siblings("p").data("rwriter");
 
var pageNum = frmCmtPagingValue.find("input[name='pageNum']").val(); 
 console.log("답글 추가가 발생된 댓글 페이지 번호: "+ pageNum); 
 
 
var txtBoxComment = $(this).prev().val(); 
 console.log("txtBoxComment: " + txtBoxComment); 
 
 
var rnoVal = $(this).closest("li").data("rno"); 
 console.log("rnoVal: " + rnoVal); 
 
 
var comment = { bno: bnoValue, 
 rno: rnoVal, 
 rcontent: txtBoxComment, 
 rwriter: rwriterVal }; 
 
 console.log("답글등록: 서버전송 객체내용: " + comment); 
 
 myCommentClsr.modifyCmtReply( 
 comment, 
 
function(serverResult){ 
 alert("수정되었습니다"); 
 
 showCmtList(pageNum); <%-- 답글이 추가된 페이지 표시 --%>
 } 
 ); 
}); 

<%--댓글-답글 삭제 처리: 삭제 버튼 클릭 이벤트 --%>
$(".chat").on("click", ".commentLi .btnDelCmt", function(){ 
 
 
<%--작성자 변수에 저장--%>
 
var rwriterVal = $(this).siblings("p").data("rwriter");
 
var delConfirm = confirm('삭제하시겠습니까?'); 
 
if(!delConfirm){ 
 alert('삭제가 취소되었습니다.'); 
 
return ; 
 } 
 
var pageNum = frmCmtPagingValue.find("input[name='pageNum']").val(); 
 console.log("답글 삭제가 발생된 댓글 페이지 번호: "+ pageNum); 
 
var rnoVal = $(this).closest("li").data("rno"); 
 console.log("rnoVal: " + rnoVal); 
 
var myComment = { bno: bnoValue, 
 rno: rnoVal, 
 rwriter: rwriterVal }; 
 console.log("답글삭제: 서버전송 객체내용: " + myComment); 
 myCommentClsr.removeCmtReply( 
 myComment, 
 
function(serverResult){<%--서버에서 댓글저장 성공 시, 브라우저에서 실행될 콜백함수--%>
 
 alert("삭제되었습니다."); 
 showCmtList(pageNum); 
 } 
 ); 
}); 


$(document).ready(function(){//페이지 로딩 시 함수 실행  전체 JavaScript 내용 중 제일 마지막에 위치해야 함
	 showCmtList(1); 
	 
}); 
//댓글 목록표시 테스트	
/* myCommentClsr.getCmtList(
  {bno:bnoValue, page:1},   //1번째 bnoAndPage의 인자값(JS객체)
  function(replyPagingCreator){  //2번째 callback 인자값: 성공 시 실행되는 함수(익명블록)
      for (var i = 0, len = replyPagingCreator.replyList.length || 0 ; i < len; i++){
          console.log(replyPagindlfegCreator.replyList[i]);
      }
  }
);  //주석처리  */ 
/* 
//댓글등록  테스트
myCommentClsr.registerCmt(
  {bno:bnoValue, rcontent:"JS-클로저-댓글입력테스트입니다.", rwriter: "user7"} , //1-comment인자값 
  function(result){    //2번째 callback 인자값: 성공 시 실행되는 함수
      alert("myCommentClsr.registerCmt()처리결과 " + result);
  }
);
 */

/* 
//답글등록 테스트
myCommentClsr.registerReply(
  {bno: bnoValue, prno: 21, rcontent: "JS-클로저-댓글의 답글입력테스트입니다.", rwriter: "user10"}, 
  function(result){ //2번째 callback 인자값: 성공시 실행되는 함수
      alert("myCommentClsr.registerReply()처리결과 " + result); //익명블록 형태의 콜백함수
  }
);
 */

/* 
//댓글-답글 조회 테스트
myCommentClsr.getCmtReply(
  {bno: bnoValue, rno: 21 },  function(data){
      console.log(data);
  }
);

 */
/* 
//댓글-답글 수정 테스트 
myCommentClsr.modifyCmtReply(
  {bno : bnoValue, rno : 21, rcontent: "JS클로저에 의한 댓글 수정======="}	,
  function(modifyResult) {
      alert(modifyResult + "- ajax 처리 완료");
  }
);
 */

/*
//댓글-답글 삭제 테스트(cdelFlg=1로 변경만 함)
myCommentClsr.setCmtReplyDeleted(
  {bno : bnoValue, rno : 21, rwriter: "user7"},
  function(deleteResult) {
      console.log(deleteResult);
      if (deleteResult === "댓글 삭제 성공") {
          alert(deleteResult + ": 댓글이 삭제되었습니다.(BysetCmtReplyDeleted)");
		}
  },
  function(err) {
      alert("오류로 인한 댓글 삭제 작업 취소...");
	}
);
*/
/* 
//댓글-답글 삭제 테스트(실제 삭제 발생)
myCommentClsr.removeCmtReply(
  {bno : bnoValue, rno : 24, rwriter: "user10"},
  function(deleteResult) {
      console.log(deleteResult);
      if (deleteResult === "댓글 삭제 성공") {
          alert(deleteResult + ": 댓글이 삭제되었습니다(ByRemoveCmtReply).");
		}
  },
  function(err) {
      alert("오류로 인한 댓글 삭제 작업 취소...");
	}
); 
 */
</script>

<%-- 첨부파일 처리 관련 자바스크립트 --%> 
<script type="text/javascript"> 
function showImage(calledPathImagefileName){ 
	 
	 //display 속성의 flex 옵션을 이용하면, 이미지가 웹브라우저의 중앙에 표시됩니다. 
		$(".bigImageWrapper").css("display","flex").show(); 
		 
		 $(".bigImage") 
		 .html("<img src='${contextPath}/displayThumbnailFile?fileName="+calledPathImagefileName+"'>") 
		 .animate({width:'100%', height: '100%'}, 0); 
	} 
		 
	//이미지 사라짐(이미지(DIV) 클릭 시). 
	$(".bigImageWrapper").on("click", function(e){ 
		 $(".bigImage").animate({width:'0%', height: '0%'}, 0); 
		 setTimeout(function () { 
		 	$(".bigImageWrapper").hide(); 
		 }, 0); 
	}); 

//페이지 로딩 시, 첨부파일 정보를 가져와서 표시
 
//$(document).ready(function(){ 
$(function(){ 
	var bno = '<c:out value="${board.bno}"/>'; 
	 $.getJSON( 
	 "/ex00/myboard/getAttachList", 
	 {bno: bno}, 
	 //콜백함수
	 
	function(jsonArrayUploadResult){ 
	 
		if (!jsonArrayUploadResult || jsonArrayUploadResult.length == 0){ 
		 
			return ; 
	 	} 
	 
		 console.log(jsonArrayUploadResult); 
		 
		var str = ""; 
		 //각 첨부파일 항목 처리 후, 표시할 HTML 작성 함수
		 
		 $(jsonArrayUploadResult).each(function(i, obj){ //obj: BoardAttachFileVO의 JSON 데이터
		 
		 //파일이 이미지가 아닌 경우
		 
			if(obj.fileType=="F"){ 
				 str += "<li data-filename='"+obj.fileName+"' data-uploadpath='"+obj.uploadPath 
				 + "' data-uuid='"+obj.uuid+"' data-filetype='"+obj.fileType+"'>" 
				 + " <div>" 
				 + " <span>"+ obj.fileName+"</span><br/>" 
				 + " <img src='${contextPath}/resources/img/icon-attach.png'" 
				 + " alt='No Icon' height='15px' width='15px'><br>" 
				 + " </div>" 
				 + "</li>"; 
			 } else if (obj.fileType == "I"){ 
			 
				var calledPathThumbnail = encodeURIComponent(obj.uploadPath + "/s_" 
				 + obj.uuid + "_" + obj.fileName); 
				 
				 console.log(calledPathThumbnail); 
				 str += "<li data-filename='"+obj.fileName+"' data-uploadpath='"+obj.uploadPath 
				 + "' data-uuid='"+obj.uuid+"' data-filetype='"+obj.fileType+"'>" 
				 + " <div>" 
				 + " <span> "+ obj.fileName+"</span><br/>" 
				 + " <img src='${contextPath}/displayThumbnailFile?fileName="+calledPathThumbnail+"'" 
				 + " alt='No ThumbNail' height='50px' width='50px'><br>" 
				 + " </div>" 
				 + "</li>"; 
		 
		 	} 
	 	}); //각 항목 처리함수 끝
		 //작성된 항목을 표시
		 $(".fileUploadResult ul").html(str); 
	 }//콜백함수 끝
	 );//.getJSON 함수 끝
});//자동로딩 끝

//이미지 다운로드 및 표시 함수

//페이지 로딩 시, 첨부파일 정보를 가져와서 표시
//$(document).ready(function(){

	
// 첨부파일 다운로드
$(".fileUploadResult ul").on("click","li", function(e){ 
	// alert("첨부파일 다운로드 시작");
		 
	var objLi = $(this); 
		 
		 
	var downloadedPathFileName = encodeURIComponent(objLi.data("uploadpath")+"/" + objLi.data("uuid")+"_" + objLi.data("filename")); 
		 
		 
	if(objLi.data("filetype") == "I"){ 
		 
		//이미지파일일 경우, 원본이미지를 확대표시
		 showImage(downloadedPathFileName.replace(new RegExp(/\\/g),"/")); 
		 
	} else if(objLi.data("filetype") == "F") { 
		 
		//이미지가 이닐 경우, 다운로드 수행
		 self.location ="${contextPath}/fileDownloadAjax?fileName="+downloadedPathFileName; 
	} 
});

</script> 


<%@ include file="../myinclude/myfooter.jsp" %>
