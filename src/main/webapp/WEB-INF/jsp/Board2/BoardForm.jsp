<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
	<head>
	<script src="<c:url value="/js/jquery-1.11.2.min.js"/>"></script>
	<script>
		function fn_formSubmit(){
			var form1 = document.form1;
			var str;
			if((str=form1.brdWriter.value).trim() == ""){
				alert("작성자를 입력해주세요.");
				form1.brdWriter.focus();
				return ;
			}
			if((str =form1.brdTitle.value).trim() == ""){
				alert("작성자를 입력해주세요.");
				form1.brdTitle.focus();
				return ;		
			}
			if((str = form1.brdMemo.value).trim() == ""){
				alert("작성자를 입력해주세요.");
				form1.brdMemo.focus();
				return ;
			}
			
			form1.submit();
		}
	</script>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>board2</title>
	</head>
	<body>
		<form name="form1" action="board2Save" method="post" enctype="multipart/form-data">
			<table border="1" style="width:600px">
				<caption>게시판</caption>
				<colgroup>
					<col width='15%' />
					<col width='*%' />
				</colgroup>
				<tbody>
					<tr>
						<td>작성자</td> 
						<td><input type="text" id ="brdWriter1" name="brdWriter" size="20" maxlength="20" value="<c:out value="${boardInfo.brdWriter}"/>"></td> 
					</tr>
					<tr>
						<td>제목</td> 
						<td><input type="text" name="brdTitle" size="70" maxlength="250" value="<c:out value="${boardInfo.brdTitle}"/>"></td> 
					</tr>
					<tr>
						<td>내용</td> 
						<td><textarea name="brdMemo" rows="5" cols="60"><c:out value="${boardInfo.brdMemo}"/></textarea></td> 
					</tr>
					<tr>
						<td>첨부</td>
						<td>
							<c:forEach var="fileList" items="${fileList}" varStatus="status">
								<input type="checkbox" name="fileNo" value="<c:out value="${fileList.fileNo }"/>" />
								<a href="fileDownload?fileName=<c:out value="${fileList.fileName}"/>&downName=<c:out value="${fileList.realName}"/>"><c:out value="${fileList.fileName }" /> </a> <c:out value="${fileList.size2String() }" /> <br />
							</c:forEach>
							<input type="file" name="uploadfile" multiple="" />
						</td>
					</tr>
				</tbody>
			</table>    
			<input type="hidden" name="brdNo" value="<c:out value="${boardInfo.brdNo}"/>">
			<input type="hidden" id="bgNo" name="bgNo" value="<c:out value="${bgNo}"/>">
			<a href="#" onclick="fn_formSubmit()">저장</a>
		</form>	
	</body>
</html>
