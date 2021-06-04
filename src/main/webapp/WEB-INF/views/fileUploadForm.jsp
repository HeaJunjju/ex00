<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> 

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="contextPath" value="${pageContext.request.contextPath}" /> 

<!DOCTYPE html> 
<html> 
<head> 
<meta charset="UTF-8"> 
<title>File Upload Form Page</title> 
</head> 
<body> 
	<!-- 다중파일 업로드 방법1: form 방식의 파일업로드 --> 
	<form action="${contextPath}/fileUploadFormAction" method="post" enctype="multipart/form-data"> <input id="inputFile" type="file" name="uploadFiles" multiple> 
	<!-- input 끝에 multiple쓰면 파일 선택시 ctrl키 누르고 한번에 여러개 고를 수 있음
	만약 파일업로드 개수를 제한하고 싶으면 input에 multiple 쓰지말고 원하는 수 만큼 input input input ..이렇게 쓰기 -->
	<button>Submit</button> 
	</form> 
</body> 
</html>